package com.cavetale.mytems.item.mobcostume;

import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter @RequiredArgsConstructor
public abstract class ChickenCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor COLOR = TextColor.color(0xC19343);
    protected static final TextColor FEATHERS = TextColor.color(0xE2E2E2);
    protected static final TextColor FEET = TextColor.color(0xE0BB69);
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
                return "Farmhand";
            }

            @Override
            public String getDescription() {
                return "You can pick up farm animals, carrying chickens slows your fall";
            }

            @Override
            public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player) {
                if (player.getGameMode() == GameMode.SPECTATOR) return;
                Entity entity = event.getRightClicked();
                if (!(isFarmAnimal(entity))) return;
                if (!player.getPassengers().isEmpty()) return;
                if (entity.isInsideVehicle()) return;
                if (!PlayerEntityAbilityQuery.Action.PICKUP.query(player, entity)) return;
                player.addPassenger(entity);
                player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1.0f, 1.0f);
                player.sendActionBar(text("Sneak to Put Down"));
            }

            @Override
            public void onPlayerMove(PlayerMoveEvent event, Player player) {
                if (player.getGameMode() == GameMode.SPECTATOR) return;
                if (player.isInsideVehicle()) return;
                for (Entity entity : player.getPassengers()) {
                    if (entity.getType() == EntityType.CHICKEN) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 10, 1, true, false, false));
                    }
                }
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Chicken Costume";
            }

            @Override
            public List<SetBonus> getSetBonuses() {
                return List.of(SET_BONUS);
            }
        };

    @Override
    public final void enable() {
        prototype.editMeta(meta -> {
                tooltip(meta, createTooltip());
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

    public static final class ChickenHelmet extends ChickenCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Chicken Mask",
                UUID.fromString("3ec599c3-9fe9-4741-8b83-c4caec2aaa0e"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ4YzczZGJhMjE5NTE3NjZmMTQ0YmRlMmMwZjAyODI2MDYwM2FjOTQ1NGRlM2Q2NzE3MmQ3ZThlY2ZkZmY2OCJ9fX0=",
                "");

        public ChickenHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Chicken Mask", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Kind of looks like a duck.", c -> c.color(GRAY));
        }
    }

    public static final class ChickenChestplate extends ChickenCostume {
        public ChickenChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, FEATHERS), text("Chicken Body", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Feathers made of plastic, no wings.", c -> c.color(GRAY));
        }
    }

    public static final class ChickenLeggings extends ChickenCostume {
        public ChickenLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, FEET), text("Chicken Pants", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Don't chicken out!", c -> c.color(GRAY));
        }
    }

    public static final class ChickenBoots extends ChickenCostume {
        public ChickenBoots(final Mytems key) {
            super(key, leatherArmor(key.material, FEET), text("Chicken Feet", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Only walk at chicken speed.", c -> c.color(GRAY));
        }
    }

    private static boolean isFarmAnimal(Entity entity) {
        if (entity == null) return false;
        switch (entity.getType()) {
        case CHICKEN:
        case COW:
        case GOAT:
        case PIG:
        case SHEEP:
        case MUSHROOM_COW:
        case RABBIT:
            return true;
        default: return false;
        }
    }
}
