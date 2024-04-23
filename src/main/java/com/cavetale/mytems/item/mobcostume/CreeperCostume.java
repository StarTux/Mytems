package com.cavetale.mytems.item.mobcostume;

import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter @RequiredArgsConstructor
public abstract class CreeperCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor COLOR = TextColor.color(0x62D153);
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
                return "Creeper Camo";
            }

            @Override
            public String getDescription() {
                return "Creepers will not attack you but will allow you to pick them up";
            }

            @Override
            public void onEntityTargetPlayer(EntityTargetEvent event, Player player) {
                if (event.isCancelled()) return;
                if (event.getEntity().getType() == EntityType.CREEPER) {
                    event.setCancelled(true);
                }
            }

            @Override
            public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player) {
                if (player.getGameMode() == GameMode.SPECTATOR) return;
                if (!(event.getRightClicked() instanceof Creeper creeper)) return;
                if (!player.getPassengers().isEmpty()) return;
                if (creeper.isInsideVehicle()) return;
                if (!PlayerEntityAbilityQuery.Action.PICKUP.query(player, creeper)) return;
                player.addPassenger(creeper);
                player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1.0f, 1.0f);
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Creeper Costume";
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

    public static final class CreeperHelmet extends CreeperCostume {
        public CreeperHelmet(final Mytems key) {
            super(key, new ItemStack(key.material), text("Creeper Mask", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Trick or treat! The trick is that you explode.", c -> c.color(GRAY));
        }
    }

    public static final class CreeperChestplate extends CreeperCostume {
        public CreeperChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Creeper Pajama Top", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Unofficial creeper apparel, kid size.", c -> c.color(GRAY));
        }
    }

    public static final class CreeperLeggings extends CreeperCostume {
        public CreeperLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Creeper Pajama Bottom", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Wash at 40°C.", c -> c.color(GRAY));
        }
    }

    public static final class CreeperBoots extends CreeperCostume {
        public CreeperBoots(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Creeper Slippers", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Only suitable for indoor wearing.", c -> c.color(GRAY));
        }
    }
}
