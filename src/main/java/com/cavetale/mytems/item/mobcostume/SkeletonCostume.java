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
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter @RequiredArgsConstructor
public abstract class SkeletonCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor COLOR = TextColor.color(0xBCBCBC);
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
                return "Skeleton Party";
            }

            @Override
            public String getDescription() {
                return "Skeletons will not attack you, and you can ride spiders";
            }

            @Override
            public void onEntityTargetPlayer(EntityTargetEvent event, Player player) {
                if (event.isCancelled()) return;
                if (event.getEntity() instanceof AbstractSkeleton) {
                    event.setCancelled(true);
                }
            }

            @Override
            public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player) {
                if (player.getGameMode() == GameMode.SPECTATOR) return;
                if (!(event.getRightClicked() instanceof Spider spider)) return;
                if (!spider.getPassengers().isEmpty()) return;
                if (!PlayerEntityAbilityQuery.Action.MOUNT.query(player, spider)) return;
                spider.addPassenger(player);
                player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1.0f, 1.0f);
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Skeleton Costume";
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

    public static final class SkeletonHelmet extends SkeletonCostume {
        public SkeletonHelmet(final Mytems key) {
            super(key, new ItemStack(key.material), text("Skeleton Mask", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Did you know that you have a skeleton skull inside your head?", c -> c.color(GRAY));
        }
    }

    public static final class SkeletonChestplate extends SkeletonCostume {
        public SkeletonChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Ribcage Costume", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Did you know that there are 206 bones in the human body?", c -> c.color(GRAY));
        }
    }

    public static final class SkeletonLeggings extends SkeletonCostume {
        public SkeletonLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Skeleton Legs", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Break a leg bone!", c -> c.color(GRAY));
        }
    }

    public static final class SkeletonBoots extends SkeletonCostume {
        public SkeletonBoots(final Mytems key) {
            super(key, leatherArmor(key.material, COLOR), text("Skeleton Feet", COLOR));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Each toe is fully articulated.", c -> c.color(GRAY));
        }
    }
}
