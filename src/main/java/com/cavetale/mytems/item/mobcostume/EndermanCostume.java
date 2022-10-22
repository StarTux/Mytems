package com.cavetale.mytems.item.mobcostume;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import static com.cavetale.mytems.util.Collision.collidesWithBlock;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter @RequiredArgsConstructor
public abstract class EndermanCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor COLOR = TextColor.color(0x161616);
    protected static final TextColor EYES = TextColor.color(0xE079FA);
    private static final SetBonus SET_BONUS = new SetBonus() {
            @Override
            public ItemSet getItemSet() {
                return ITEM_SET;
            }

            @Override
            public int getRequiredItemCount() {
                return 4;
            }

            @Override
            public String getName() {
                return "Enderport";
            }

            @Override
            public String getDescription() {
                return "Endermen will not attack you, and you warp away from projectiles";
            }

            @Override
            public void onEntityTargetPlayer(EntityTargetEvent event, Player player) {
                if (event.isCancelled()) return;
                if (event.getEntity().getType() == EntityType.ENDERMAN) {
                    event.setCancelled(true);
                }
            }

            @Override
            public void onProjectileCollidePlayer(ProjectileCollideEvent event, Player player) {
                if (event.isCancelled()) return;
                if (player.isGliding()) return;
                if (player.getGameMode() == GameMode.SPECTATOR) return;
                final Random random = ThreadLocalRandom.current();
                final int radius = 8;
                final Location from = player.getLocation();
                final Vector fromv = from.toVector();
                final World world = player.getWorld();
                for (int i = 0; i < 100; i += 1) {
                    final int dx = random.nextInt(radius) - random.nextInt(radius);
                    final int dy = random.nextInt(radius) - random.nextInt(radius);
                    final int dz = random.nextInt(radius) - random.nextInt(radius);
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    final Block targetBlock = player.getLocation().getBlock().getRelative(dx, dy, dz);
                    final Location to = targetBlock.getLocation().add(0.5, 0.0, 0.5);
                    final BoundingBox bb = player.getBoundingBox().shift(to.toVector().subtract(fromv));
                    if (collidesWithBlock(world, bb)) continue;
                    bb.expandDirectional(0.0, -0.625, 0.0);
                    if (!collidesWithBlock(world, bb)) continue;
                    to.setYaw(from.getYaw());
                    to.setPitch(from.getPitch());
                    if (player.teleport(to)) {
                        world.playSound(from, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        world.playSound(to, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        event.setCancelled(true);
                    }
                    break;
                }
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Enderman Costume";
            }

            @Override
            public List<SetBonus> getSetBonuses() {
                return List.of(SET_BONUS);
            }
        };

    @Override
    public final void enable() {
        prototype.editMeta(meta -> {
                Items.text(meta, createTooltip());
                key.markItemMeta(meta);
            });
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public final void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public final ItemSet getItemSet() {
        return ITEM_SET;
    }

    public static final class EndermanHelmet extends EndermanCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Enderman",
                UUID.fromString("0de98464-1274-4dd6-bba8-000000370efa"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1OWJiMGE3YTMyOTY1YjNkOTBkOGVhZmE4OTlkMTgzNWY0MjQ1MDllYWRkNGU2YjcwOWFkYTUwYjljZiJ9fX0=",
                "");

        public EndermanHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Enderman Mask", EYES));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Comes with glowing eyes. Batteries not included.", c -> c.color(GRAY));
        }
    }

    public static final class EndermanChestplate extends EndermanCostume {
        public EndermanChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Enderman Turtleneck", EYES));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("It has potential as a tactical garment.", c -> c.color(GRAY));
        }
    }

    public static final class EndermanLeggings extends EndermanCostume {
        public EndermanLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Enderman Stilts", EYES));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Walk on stilts to become as tall as an Enderman.", c -> c.color(GRAY));
        }
    }

    public static final class EndermanBoots extends EndermanCostume {
        public EndermanBoots(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Enderman Shoes", EYES));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Made of fine pigskin, these shoes are a fashion statement.", c -> c.color(GRAY));
        }
    }
}
