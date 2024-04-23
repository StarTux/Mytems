package com.cavetale.mytems.item.mobcostume;

import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.BlockColor;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static java.awt.Color.HSBtoRGB;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter @RequiredArgsConstructor
public abstract class SheepCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;

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
                return "Rainbow Sheep";
            }

            @Override
            public String getDescription() {
                return "Change Colors and get bonus drops from shearing sheep";
            }

            @Override
            public void tick(LivingEntity living, int has) {
                final Color color = getCurrentColor();
                for (EquipmentSlot slot : List.of(EquipmentSlot.HEAD,
                                                  EquipmentSlot.CHEST,
                                                  EquipmentSlot.LEGS,
                                                  EquipmentSlot.FEET)) {
                    ItemStack item = living.getEquipment().getItem(slot);
                    Mytems mytems = Mytems.forItem(item);
                    if (mytems == null) continue;
                    if (mytems.category != MytemsCategory.SHEEP_COSTUME) continue;
                    if (item.getType() != Material.PLAYER_HEAD) {
                        updateLeatherColor(item, color);
                    }
                }
            }

            @Override
            public void onPlayerShearEntity(PlayerShearEntityEvent event, Player player) {
                if (event.isCancelled()) return;
                if (!(event.getEntity() instanceof Sheep sheep)) return;
                if (sheep.isSheared()) return;
                if (!sheep.getWorld().getGameRuleValue(GameRule.DO_MOB_LOOT)) return;
                BlockColor blockColor = BlockColor.of(sheep.getColor());
                Material woolMaterial = blockColor.getMaterial(BlockColor.Suffix.WOOL);
                ItemStack drop = new ItemStack(woolMaterial);
                sheep.getWorld().dropItem(sheep.getEyeLocation(), drop);
                player.sendActionBar(textOfChildren(text("Bonus ", blockColor.textColor),
                                                    VanillaItems.componentOf(woolMaterial),
                                                    text(blockColor.niceName + " Wool", blockColor.textColor)));
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Sheep Costume";
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
        ItemStack result = prototype.clone();
        if (result.getType() != Material.PLAYER_HEAD) {
            updateLeatherColor(result, getCurrentColor());
        }
        return result;
    }

    @Override
    public final void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public final ItemSet getItemSet() {
        return ITEM_SET;
    }

    public static final class SheepHelmet extends SheepCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Sheep",
                UUID.fromString("8e51166f-aa4a-4fed-96ea-d560a653c87a"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdlMmJmNTM5YzhlMGFkNDY4ZTUyN2I0N2NmM2EyMWQ0ZTlhY2UxODcyM2E3MjI0OWI3ZDYzZTg1ZmI4YWUwIn19fQ==",
                "");

        public SheepHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Sheep Mask"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Don't look so sheepish", c -> c.color(GRAY));
        }
    }

    public static final class SheepChestplate extends SheepCostume {
        public SheepChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, color(0xFFFF00)), text("Sheep Sweater"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("100% wool", c -> c.color(GRAY));
        }
    }

    public static final class SheepLeggings extends SheepCostume {
        public SheepLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), text("Sheep Pants"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("The finest cashmere", c -> c.color(GRAY));
        }
    }

    public static final class SheepBoots extends SheepCostume {
        public SheepBoots(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), text("Sheep Socks"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Custom knit to keep your feet warm", c -> c.color(GRAY));
        }
    }

    public static Color getCurrentColor() {
        final long period = 5_000L;
        final float hue = (float) (System.currentTimeMillis() % period) / (float) period;
        final int rgb = HSBtoRGB(hue, 1.0f, 0.75f) & 0xFFFFFF;
        return Color.fromRGB(rgb);
    }

    public static void updateLeatherColor(ItemStack item, Color color) {
        item.editMeta(m -> {
                if (m instanceof LeatherArmorMeta meta) {
                    meta.setColor(color);
                }
            });
    }
}
