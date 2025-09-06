package com.cavetale.mytems.item.dune;

import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.EntityAttribute;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import io.papermc.paper.datacomponent.DataComponentTypes;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.text;
import static io.papermc.paper.datacomponent.item.ItemEnchantments.itemEnchantments;
import static io.papermc.paper.datacomponent.item.TooltipDisplay.tooltipDisplay;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public abstract class DuneItem implements GearItem {
    protected final Mytems key;
    private List<Component> baseLore;
    private Component displayName;
    private ItemStack prototype;
    private static DuneItemSet duneItemSet;

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName());
        prototype = getBaseItem();
        baseLore = Text.wrapLore2("\n\n" + getDescription(), DuneItem::fancify);
        text(prototype, createTooltip());
        prototype.setData(DataComponentTypes.REPAIR_COST, 9999);
        prototype.setData(DataComponentTypes.UNBREAKABLE);
        prototype.setData(DataComponentTypes.ENCHANTMENTS, itemEnchantments().add(Enchantment.PROTECTION, 5));
        prototype.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltipDisplay().addHiddenComponents(DataComponentTypes.UNBREAKABLE));
        key.markItemStack(prototype);
    }

    protected static final Component fancify(String in) {
        int len = in.length();
        TextComponent.Builder cb = text();
        for (int i = 0; i < len; i += 1) {
            int red = 128 + (i * 127) / len;
            cb.append(text(in.substring(i, i + 1)).color(TextColor.color(red, 128, 0)));
        }
        return cb.build();
    }

    abstract ItemStack getBaseItem();

    abstract String getRawDisplayName();

    abstract String getDescription();

    @Override
    public final ItemSet getItemSet() {
        if (duneItemSet == null) duneItemSet = new DuneItemSet();
        return duneItemSet;
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    public static final class Helmet extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Helmet";
        @Getter private final String description = "An ancient guardian once wore this mighty headpiece.";

        public Helmet(final Mytems key) {
            super(key);
        }

        @Override
        public ItemStack getBaseItem() {
            final ItemStack result = Skull.create(null,
                                                  UUID.fromString("dc285131-7605-45a6-9b24-a44b3c7b5600"),
                                                  "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI3YjJlMzM3Y2ZkZTk3ZDE2NmVkMGY1MDRiNThhYmFkZmYyOWU2ZGM4ZWFjMmRmYTQwMTUyMmJhNjY4ZmI5MiJ9fX0=");
            result.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
                           Material.NETHERITE_HELMET.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS));
            return result;
        }
    }

    public static final class Chestplate extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Chestplate";
        @Getter private final String description = "An ancient guardian once wore this breastplate.";

        public Chestplate(final Mytems key) {
            super(key);
        }

        @Override
        public ItemStack getBaseItem() {
            final ItemStack result = new ItemStack(key.getMaterial());
            result.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
                           Material.NETHERITE_CHESTPLATE.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS));
            return result;
        }
    }

    public static final class Leggings extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Leggings";
        @Getter private final String description = "An ancient guardian once wore these leggings.";

        public Leggings(final Mytems key) {
            super(key);
        }

        @Override
        public ItemStack getBaseItem() {
            final ItemStack result = new ItemStack(key.getMaterial());
            result.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
                           Material.NETHERITE_LEGGINGS.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS));
            return result;
        }
    }

    public static final class Boots extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Boots";
        @Getter private final String description = "An ancient guardian once wore these golden boots.";

        public Boots(final Mytems key) {
            super(key);
        }

        @Override
        public ItemStack getBaseItem() {
            final ItemStack result = new ItemStack(key.getMaterial());
            result.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
                           Material.NETHERITE_BOOTS.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS));
            return result;
        }
    }

    public static final class Weapon extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Digger";
        @Getter private final String description = "What ancient ruins might this golden shovel uncover?";

        public Weapon(final Mytems key) {
            super(key);
        }

        @Override
        public ItemStack getBaseItem() {
            final ItemStack result = new ItemStack(key.getMaterial());
            result.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
                           Material.NETHERITE_SWORD.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS));
            return result;
        }
    }

    @Getter
    public static final class DuneItemSet implements ItemSet {
        private final String name = "Dune";
        private final List<SetBonus> setBonuses = List.of(new FlameThorns(this, 2),
                                                          new SandSpeed(this, 4));

        @Getter @RequiredArgsConstructor
        public static final class SandSpeed implements SetBonus {
            private final DuneItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Sand Speed";
            private final String description = "Gain extra speed while walking on sand";
            private final EntityAttribute speedAttribute = new EntityAttribute(Attribute.MOVEMENT_SPEED,
                                                                               "dune_sand_speed",
                                                                               0.5, Operation.ADD_SCALAR);
            private final List<EntityAttribute> entityAttributes = List.of(speedAttribute);

            private static boolean isOnSand(Location location) {
                Block block = location.add(0, -0.125, 0).getBlock();
                for (int i = 0; i < 3; i += 1) {
                    if (block.getType().name().contains("SAND")) return true;
                    if (!block.getCollisionShape().getBoundingBoxes().isEmpty()) return false;
                    block = block.getRelative(0, -1, 0);
                }
                return false;
            }

            @Override
            public void tick(LivingEntity living, int has) {
                if (isOnSand(living.getLocation())) {
                    speedAttribute.add(living);
                    if (living.getTicksLived() % 4 == 0 && living instanceof Player player) {
                        player.getWorld().spawnParticle(Particle.SOUL, player.getLocation().add(0, 0.125, 0), 1, 0.25, 0, 0.25, 0);
                    }
                } else {
                    speedAttribute.remove(living);
                }
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class FlameThorns implements SetBonus {
            private final DuneItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Flame Thorns";
            private final String description = "40% chance to set attackers on fire";

            @Override
            public void onPlayerDamageByEntity(EntityDamageByEntityEvent event, Player player, Entity damager) {
                if (event.isCancelled()) return;
                if (event.getCause() != EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) return;
                if (ThreadLocalRandom.current().nextDouble() > 0.4) return;
                final int seconds = 30;
                if (!PlayerEntityAbilityQuery.Action.IGNITE.query(player, damager)) return;
                EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(player, damager, (float) seconds);
                if (!combustEvent.callEvent()) {
                    return;
                }
                damager.setFireTicks(Math.max(damager.getFireTicks(), seconds * 20));
            }
        }
    }
}
