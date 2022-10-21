package com.cavetale.mytems.item.mobcostume;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter @RequiredArgsConstructor
public abstract class SpiderCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor COLOR = TextColor.color(0x4F453C);
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
                return "Spiderkin";
            }

            @Override
            public String getDescription() {
                return "Arthropods will not attack you and you climb up walls";
            }

            @Override
            public void onEntityTargetPlayer(EntityTargetEvent event, Player player) {
                if (event.isCancelled()) return;
                if (event.getEntity() instanceof LivingEntity living && living.getCategory() == EntityCategory.ARTHROPOD) {
                    event.setCancelled(true);
                }
            }

            private static boolean isTouchingWalls(Player player) {
                final double expand = 0.0625;
                BoundingBox bb = player.getBoundingBox().expand(expand, 0.0, expand);
                final Vector min = bb.getMin();
                final Vector max = bb.getMax();
                final int ax = min.getBlockX();
                final int ay = min.getBlockY() - 1; // be nice to walls
                final int az = min.getBlockZ();
                final int bx = max.getBlockX();
                final int by = max.getBlockY();
                final int bz = max.getBlockZ();
                int bs = 0;
                int bbs = 0;
                for (int y = ay; y <= by; y += 1) {
                    for (int z = az; z <= bz; z += 1) {
                        for (int x = ax; x <= bx; x += 1) {
                            Block block = player.getWorld().getBlockAt(x, y, z);
                            bs += 1;
                            for (BoundingBox box : block.getCollisionShape().getBoundingBoxes()) {
                                bbs += 1;
                                if (bb.overlaps(box.shift((double) x, (double) y, (double) z))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public void onPlayerMove(PlayerMoveEvent event, Player player) {
                if (player.isGliding()) return;
                if (player.isFlying()) return;
                if (player.isInsideVehicle()) return;
                if (!isTouchingWalls(player)) return;
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 0, true, false, false));
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Spider Costume";
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

    public static final class SpiderHelmet extends SpiderCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Spider Mask",
                UUID.fromString("8bdb71d0-4724-48b2-9344-000000e79480"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==",
                "");

        public SpiderHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Spider Mask", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Comes with eight eyes plus one replacement eye.", c -> c.color(GRAY));
        }
    }

    public static final class SpiderChestplate extends SpiderCostume {
        public SpiderChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Spider Body", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Made out of cardboard, hand-painted by StarTux.", c -> c.color(GRAY));
        }
    }

    public static final class SpiderLeggings extends SpiderCostume {
        public SpiderLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Spider Bottom", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Also sold in blue.", c -> c.color(GRAY));
        }
    }

    public static final class SpiderBoots extends SpiderCostume {
        public SpiderBoots(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Spider Boots", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("How did Spooder-Man make his costume?", c -> c.color(GRAY));
        }
    }
}
