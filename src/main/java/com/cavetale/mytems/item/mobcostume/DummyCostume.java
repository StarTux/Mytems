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
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter @RequiredArgsConstructor
/**
 * An empty costume suitable for copy and paste purposes.
 */
public abstract class DummyCostume implements GearItem {
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
                return "Dummy";
            }

            @Override
            public String getDescription() {
                return "TODO";
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Dummy Costume";
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

    public static final class DummyHelmet extends DummyCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Dummy",
                UUID.fromString(""),
                "",
                "");

        public DummyHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Dummy Helmet"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("TODO", c -> c.color(GRAY));
        }
    }

    public static final class DummyChestplate extends DummyCostume {
        public DummyChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), text("Dummy Chestplate"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("TODO", c -> c.color(GRAY));
        }
    }

    public static final class DummyLeggings extends DummyCostume {
        public DummyLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), text("Dummy Leggings"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("TODO", c -> c.color(GRAY));
        }
    }

    public static final class DummyBoots extends DummyCostume {
        public DummyBoots(final Mytems key) {
            super(key, leatherArmor(key.material, BLACK), text("Dummy Boots"));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("TODO", c -> c.color(GRAY));
        }
    }
}
