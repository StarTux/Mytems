package com.cavetale.mytems.item.hookshot;

import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Favorite;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Collision;
import com.cavetale.mytems.util.Entities;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;

public final class HookshotShot implements Favorite {
    private final Mytems mytems;
    private final HookshotType type;
    private final Player player;
    private final Session session;
    private final Location originalHandLocation;
    private final Vector direction;
    // Magic numbers
    private final double maxRange = 16.0;
    private final int chainDisplayCount = 31;
    private final double extensionSpeed = 0.75; // blocks per tick
    private final double playerPullSpeed = 1.0;
    private final double entityPullSpeed = 1.0;
    private final double retractSpeed = 2.0;
    // State
    private Phase phase = Phase.INIT;
    private Location handLocation; // player
    private double currentRange;
    private Location hookLocation; // hook
    private Vector desiredPlayerVelocity;
    private Vector desiredEntityVelocity;
    private Entity pulledEntity;
    private Vector relativeHookLocation;
    // Entities
    private ItemDisplay hookDisplay;
    private final List<ItemDisplay> chainDisplays = new ArrayList<>();

    private BukkitTask task;

    public HookshotShot(final Mytems mytems, final HookshotType type, final Player player, final Session session) {
        this.mytems = mytems;
        this.type = type;
        this.player = player;
        this.session = session;
        this.direction = player.getLocation().getDirection();
        this.originalHandLocation = computeHandLocation();
    }

    @Override
    public void onSessionDisable(Session theSession) {
        stop();
    }

    public void start() {
        handLocation = computeHandLocation();
        hookLocation = handLocation.clone();
        session.getFavorites().set(this);
        task = Bukkit.getScheduler().runTaskTimer(mytemsPlugin(), this::tick, 1L, 1L);
        final World world = handLocation.getWorld();
        final float hookScale = 0.25f;
        final Transformation hookTransformation = new Transformation(new Vector3f(0f, 0f, 0f), // translation
                                                                     new AxisAngle4f(0f, 0f, 0f, 0f),
                                                                     new Vector3f(hookScale, hookScale, hookScale),
                                                                     new AxisAngle4f(0f, 0f, 0f, 0f));
        hookDisplay = world.spawn(handLocation, ItemDisplay.class, e -> {
                e.setPersistent(false);
                Entities.setTransient(e);
                e.setItemStack(new ItemStack(Material.STRUCTURE_VOID));
                e.setBillboard(ItemDisplay.Billboard.CENTER);
                e.setTransformation(hookTransformation);
            });
        final float chainScale = 0.083f;
        final Transformation chainTransformation = new Transformation(new Vector3f(0f, 0f, 0f), // translation
                                                                      new AxisAngle4f(0f, 0f, 0f, 0f),
                                                                      new Vector3f(chainScale, chainScale, chainScale),
                                                                      new AxisAngle4f(0f, 0f, 0f, 0f));
        for (int i = 0; i < chainDisplayCount; i += 1) {
            final ItemDisplay chainDisplay = world.spawn(handLocation, ItemDisplay.class, e -> {
                    e.setPersistent(false);
                    Entities.setTransient(e);
                    e.setItemStack(new ItemStack(Material.ENDER_PEARL));
                    e.setBillboard(ItemDisplay.Billboard.CENTER);
                    e.setTransformation(chainTransformation);
                });
            chainDisplays.add(chainDisplay);
        }
        phase = Phase.EXTEND;
    }

    public void stop() {
        session.getFavorites().removeInstance(this);
        if (task != null) {
            task.cancel();
            task = null;
        }
        clearEntities();
    }

    private void clearEntities() {
        hookDisplay.remove();
        hookDisplay = null;
        for (ItemDisplay it : chainDisplays) {
            it.remove();
        }
        chainDisplays.clear();
    }

    private void tick() {
        if (!checkValidity()) {
            stop();
            return;
        }
        switch (phase) {
        case EXTEND:
            tickExtend();
            break;
        case PULL_PLAYER:
            tickPullPlayer();
            break;
        case PULL_ENTITY:
            tickPullEntity();
            break;
        case RETRACT:
            tickRetract();
            break;
        default:
            stop();
            throw new IllegalStateException("phase:" + phase
                                            + " mytems:" + mytems
                                            + " session:" + session.getName());
        }
    }

    private boolean checkValidity() {
        return player.isValid()
            && player.isOnline()
            && !player.isDead()
            && player.getWorld().equals(handLocation.getWorld())
            && player.getLocation().distance(hookLocation) < maxRange * 2.0
            && Mytems.forItem(player.getInventory().getItemInMainHand()) == mytems;
    }

    private void tickExtend() {
        final double step = 1.0 / 8.0;
        double part;
        boolean didHitBlock = false;
        boolean isAttached = false;
        boolean didHitEntity = false;
        Block hitBlock = null;
        for (part = 0.0; part < extensionSpeed; part += step) {
            if (part > extensionSpeed) part = extensionSpeed;
            final Vector vector = direction.clone().normalize().multiply(currentRange + part);
            hookLocation = originalHandLocation.clone().add(vector);
            final BoundingBox bb = BoundingBox.of(hookLocation, step, step, step);
            List<Block> collidingBlocks = Collision.getCollidingBlocks(hookLocation.getWorld(), bb);
            didHitBlock = !collidingBlocks.isEmpty();
            for (Block block : collidingBlocks) {
                hitBlock = block;
                if (type.getAttachedMaterials().contains(block.getType())) {
                    isAttached = true;
                    break;
                }
            }
            if (didHitBlock) break;
            for (Entity entity : hookLocation.getWorld().getNearbyEntities(bb)) {
                if (!(entity instanceof LivingEntity) && !(entity instanceof Item)) continue;
                if (entity instanceof Player) continue;
                if (!PlayerEntityAbilityQuery.Action.MOVE.query(player, entity)) {
                    continue;
                }
                pulledEntity = entity;
                didHitEntity = true;
                break;
            }
            if (didHitEntity) break;
        }
        currentRange += part;
        handLocation = computeHandLocation();
        updateEntities();
        // New Phase
        if (didHitBlock) {
            if (isAttached) {
                phase = Phase.PULL_PLAYER;
                player.playSound(player.getLocation(), hitBlock.getBlockSoundGroup().getPlaceSound(), 2f, 1f);
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, SoundCategory.NEUTRAL, 1f, 2f);
            } else {
                phase = Phase.RETRACT;
                player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_ATTACH, SoundCategory.NEUTRAL, 1f, 2f);
            }
        } else if (didHitEntity) {
            relativeHookLocation = hookLocation.toVector().subtract(pulledEntity.getLocation().toVector());
            phase = Phase.PULL_ENTITY;
            hookLocation.getWorld().playSound(hookLocation, Sound.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 1f, 2f);
        } else if (currentRange >= maxRange) {
            phase = Phase.RETRACT;
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_ATTACH, SoundCategory.NEUTRAL, 1f, 2f);
        } else {
            hookLocation.getWorld().playSound(hookLocation, Sound.BLOCK_CHAIN_PLACE, SoundCategory.NEUTRAL, 1f, 2f);
        }
    }

    private void updateEntities() {
        hookDisplay.teleport(hookLocation);
        for (int i = 0; i < chainDisplayCount; i += 1) {
            final ItemDisplay chainDisplay = chainDisplays.get(i);
            final double fraction = (double) (i + 1) / (double) (chainDisplayCount + 1);
            final Vector vector = handLocation.toVector().multiply(1.0 - fraction)
                .add(hookLocation.toVector().multiply(fraction));
            chainDisplay.teleport(vector.toLocation(handLocation.getWorld()));
        }
    }

    private void tickPullPlayer() {
        if (desiredPlayerVelocity != null) {
            final double diff = desiredPlayerVelocity.clone().subtract(player.getVelocity()).length();
            if (Math.abs(diff) > desiredPlayerVelocity.length() * 0.5) {
                stop();
                return;
            }
        }
        handLocation = computeHandLocation();
        updateEntities();
        Vector vector = hookLocation.toVector().subtract(handLocation.toVector());
        if (vector.length() < 1.0) {
            stop();
            return;
        }
        desiredPlayerVelocity = vector.normalize().multiply(playerPullSpeed);
        player.setVelocity(desiredPlayerVelocity);
        hookLocation.getWorld().playSound(hookLocation, Sound.BLOCK_CHAIN_BREAK, SoundCategory.NEUTRAL, 1f, 2f);
    }

    private void tickPullEntity() {
        if (pulledEntity == null || pulledEntity.isDead()) {
            stop();
            return;
        }
        if (desiredEntityVelocity != null) {
            final double diff = desiredEntityVelocity.clone().subtract(pulledEntity.getVelocity()).length();
            if (Math.abs(diff) > desiredEntityVelocity.length() * 0.5) {
                stop();
                return;
            }
        }
        hookLocation = pulledEntity.getLocation().add(relativeHookLocation);
        updateEntities();
        Vector vector = handLocation.toVector().subtract(pulledEntity.getLocation().toVector());
        if (vector.length() < 1.0) {
            stop();
            return;
        }
        desiredEntityVelocity = vector.normalize().multiply(entityPullSpeed);
        pulledEntity.setVelocity(desiredEntityVelocity);
        hookLocation.getWorld().playSound(hookLocation, Sound.BLOCK_CHAIN_BREAK, SoundCategory.NEUTRAL, 1f, 2f);
    }

    private void tickRetract() {
        Vector vector = handLocation.toVector().subtract(hookLocation.toVector());
        if (vector.length() < 1.0) {
            stop();
            return;
        }
        hookLocation = hookLocation.add(vector.normalize().multiply(retractSpeed));
        updateEntities();
        hookLocation.getWorld().playSound(hookLocation, Sound.BLOCK_CHAIN_BREAK, SoundCategory.NEUTRAL, 1f, 2f);
    }

    private Location computeHandLocation() {
        return player.getLocation().add(0.0, player.getHeight() * 0.5, 0.0);
    }

    public enum Phase {
        INIT,
        EXTEND,
        RETRACT,
        PULL_PLAYER,
        PULL_ENTITY,
        ;
    }
}
