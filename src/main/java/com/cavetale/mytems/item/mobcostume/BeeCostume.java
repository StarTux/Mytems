package com.cavetale.mytems.item.mobcostume;

import com.cavetale.core.font.VanillaItems;
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
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Beehive;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter @RequiredArgsConstructor
public abstract class BeeCostume implements GearItem {
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
                return "Beethoscope";
            }

            @Override
            public String getDescription() {
                return "Bees will not attack you.  Click a beehive to see who lives there.";
            }

            @Override
            public void onEntityTargetPlayer(EntityTargetEvent event, Player player) {
                if (event.isCancelled()) return;
                if (event.getEntity().getType() == EntityType.BEE) {
                    event.setCancelled(true);
                }
            }

            @Override
            public void onPlayerInteract(PlayerInteractEvent event, Player player) {
                if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
                if (event.getHand() != EquipmentSlot.HAND) return;
                if (!(event.getClickedBlock().getState() instanceof Beehive beehive)) return;
                int beeCount = beehive.getEntityCount();
                if (beeCount == 0) {
                    player.sendActionBar(text("There are no bees in this hive", RED));
                    player.playSound(event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5),
                                     Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 0.2f, 0.5f);
                } else {
                    Component msg = textOfChildren(VanillaItems.componentOf(event.getClickedBlock().getType()),
                                                   text(" Bees in this hive:", GRAY));
                    for (int i = 0; i < beeCount; i += 1) {
                        msg = msg.append(textOfChildren(space(), Mytems.BEE_FACE));
                        Bukkit.getScheduler().runTaskLater(plugin(), () -> {
                                player.playSound(event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5),
                                                 Sound.BLOCK_BEEHIVE_ENTER, SoundCategory.MASTER, 0.2f, 1.0f);
                            }, (long) i * 3L);
                    }
                    player.sendActionBar(msg);
                }
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Bee Costume";
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

    public static final class BeeHelmet extends BeeCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Bee",
                UUID.fromString("7f1cdcd4-4247-4bab-8ef7-34da446d4dc4"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQyMGM5YzQzZTA5NTg4MGRjZDJlMjgxYzgxZjQ3YjE2M2I0NzhmNThhNTg0YmI2MWY5M2U2ZTEwYTE1NWYzMSJ9fX0=",
                "");

        public BeeHelmet(final Mytems key) {
            super(key, SKULL.create(), beeColor("Bee Hat"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("The eyes are almost see-through", c -> c.color(GRAY));
        }
    }

    public static final class BeeChestplate extends BeeCostume {
        public BeeChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, color(0xFFFF00)), beeColor("Bee Vest"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Nice and fluffy, with mild pollen stains.", c -> c.color(GRAY));
        }
    }

    public static final class BeeLeggings extends BeeCostume {
        public BeeLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), beeColor("The Bee's Knees"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("With extra padding.  The honey stans will wash right out.", c -> c.color(GRAY));
        }
    }

    public static final class BeeBoots extends BeeCostume {
        public BeeBoots(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), beeColor("Bee Shoes"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("All bees wear them.", c -> c.color(GRAY));
        }
    }

    private static Component beeColor(String in) {
        int len = in.length();
        TextComponent.Builder component = Component.text();
        for (int i = 0; i < len; i += 1) {
            component.append(text(in.substring(i, i + 1), (i % 2 == 0
                                                           ? color(0xFFFF00)
                                                           : GRAY)));
        }
        return component.asComponent();
    }
}
