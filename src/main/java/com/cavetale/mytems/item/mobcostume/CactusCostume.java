package com.cavetale.mytems.item.mobcostume;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter @RequiredArgsConstructor
public abstract class CactusCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor CACTUS_GREEN = color(0x527D26);

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
                return "Desert Plant";
            }

            @Override
            public String getDescription() {
                return "Cacti don't hurt, you have Thorns III and never get hungry in the desert";
            }

            @Override
            public void onPlayerDamage(EntityDamageEvent event, Player player) {
                if (event.getCause() != EntityDamageEvent.DamageCause.CONTACT) return;
                if (!(event instanceof EntityDamageByBlockEvent event2)) return;
                if (event2.getDamager().getType() != Material.CACTUS) return;
                event.setCancelled(true);
            }

            @Override
            public void onFoodLevelChange(FoodLevelChangeEvent event, Player player) {
                if (event.getFoodLevel() < player.getFoodLevel()
                    && player.getLocation().getBlock().getBiome().name().contains("DESERT")) {
                    event.setCancelled(true);
                }
            }

            @Override
            /**
             * Simulate thorns.
             */
            public void onDefendingDamageCalculation(DamageCalculationEvent event) {
                if (event.isBlocking()) return;
                LivingEntity target = event.getTarget();
                LivingEntity attacker = event.getAttacker();
                if (target == null || attacker == null) return;
                int totalDamage = 0;
                Random random = ThreadLocalRandom.current();
                for (int i = 0; i < 4; i += 1) {
                    if (random.nextDouble() < 0.45) {
                        totalDamage += 1 + random.nextInt(4);
                    }
                }
                if (totalDamage <= 0) return;
                final double damage = Math.min(4.0, (double) totalDamage);
                event.addPostDamageAction(true, () -> {
                        if (attacker.isDead()) return;
                        attacker.damage(damage);
                        if (attacker instanceof Player attackerPlayer) {
                            attackerPlayer.playSound(attackerPlayer.getLocation(), Sound.ENCHANT_THORNS_HIT, 1.0f, 1.0f);
                        }
                        Vector v = target.getLocation().toVector().subtract(attacker.getLocation().toVector());
                        attacker.knockback(1.0f, v.getX(), v.getZ());
                    });
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Cactus Costume";
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

    @Override
    /**
     * This override is a vestige from when we added "Thorns III" to
     * the lore.
     */
    public final List<Component> getBaseLore() {
        return Text.wrapLore(getBaseDescription(), c -> c.color(GRAY));
    }

    protected abstract String getBaseDescription();

    public static final class CactusHelmet extends CactusCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Cactus",
                UUID.fromString("c1f3eebc-85de-4ad6-9cb3-3a29e3730e3f"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmY1ODViNDFjYTVhMWI0YWMyNmY1NTY3NjBlZDExMzA3Yzk0ZjhmOGExYWRlNjE1YmQxMmNlMDc0ZjQ3OTMifX19",
                "");

        public CactusHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Cactus Helmet", CACTUS_GREEN));
        }

        @Override
        protected String getBaseDescription() {
            return "This headwear sure is looking sharp.";
        }
    }

    public static final class CactusChestplate extends CactusCostume {
        public CactusChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, CACTUS_GREEN), text("Cactus Chestplate", CACTUS_GREEN));
        }

        @Override
        protected String getBaseDescription() {
            return "This top can feel a little prickly.";
        }
    }

    public static final class CactusLeggings extends CactusCostume {
        public CactusLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, CACTUS_GREEN), text("Cactus Pants", CACTUS_GREEN));
        }

        @Override
        protected String getBaseDescription() {
            return "Stick out in public with these fancy pants.";
        }
    }

    public static final class CactusBoots extends CactusCostume {
        public CactusBoots(final Mytems key) {
            super(key, leatherArmor(key.material, CACTUS_GREEN), text("Cactus Boots", CACTUS_GREEN));
        }

        @Override
        protected String getBaseDescription() {
            return "The shoes ran out of puns.";
        }
    }
}
