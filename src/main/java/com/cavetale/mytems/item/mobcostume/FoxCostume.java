package com.cavetale.mytems.item.mobcostume;

import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.item.music.PlayerPlayInstrumentEvent;
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
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.LeatherArmor.leatherArmor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter @RequiredArgsConstructor
public abstract class FoxCostume implements GearItem {
    protected final Mytems key;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected static final TextColor FOX_ORANGE = color(0xE27C21);

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
                return "Foxtrot";
            }


            @Override
            public String getDescription() {
                return "Sweet berry bush does not hurt you and playing an instrument puts nearby foxes to sleep";
            }

            @Override
            public void onPlayerDamage(EntityDamageEvent event, Player player) {
                if (event.getCause() != EntityDamageEvent.DamageCause.CONTACT) return;
                if (!(event instanceof EntityDamageByBlockEvent event2)) return;
                if (event2.getDamager().getType() != Material.SWEET_BERRY_BUSH) return;
                event.setCancelled(true);
            }

            @Override
            public void onPlayerPlayInstrument(PlayerPlayInstrumentEvent event, Player player) {
                Random random = ThreadLocalRandom.current();
                if (random.nextInt(4) != 0) return;
                final double dist = 24.0;
                for (Fox fox : player.getLocation().getNearbyEntitiesByType(Fox.class, dist, dist, dist)) {
                    if (random.nextBoolean()) continue;
                    if (!PlayerEntityAbilityQuery.Action.MOVE.query(player, fox)) continue;
                    fox.setSleeping(true);
                }
            }
        };
    private static final ItemSet ITEM_SET = new ItemSet() {
            @Override
            public String getName() {
                return "Fox Costume";
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

    public static final class FoxHelmet extends FoxCostume {
        @SuppressWarnings("LineLength")
        private static final Skull SKULL = Skull
            .of("Fox",
                UUID.fromString("be3da345-7181-438f-a70f-fccbbf8296d9"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYwMDhiMzM4ZjBhNTM0ZjkzNWQzYThiOWIxMjg5ZDNlZjAyMGZmZjJjODUzMThhYWI4NzU0Nzk0MTBjYzM0NiJ9fX0=",
                "");

        public FoxHelmet(final Mytems key) {
            super(key, SKULL.create(), text("Fox Mask", FOX_ORANGE));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Can't let you do that, Starfox.", c -> c.color(GRAY));
        }
    }

    public static final class FoxChestplate extends FoxCostume {
        public FoxChestplate(final Mytems key) {
            super(key, leatherArmor(key.material, FOX_ORANGE), text("Fox Fur", FOX_ORANGE));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Do a barrel roll!", c -> c.color(GRAY));
        }
    }

    public static final class FoxLeggings extends FoxCostume {
        public FoxLeggings(final Mytems key) {
            super(key, leatherArmor(key.material, FOX_ORANGE), text("Fox Feet", FOX_ORANGE));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("Never give up. Trust your instincts.", c -> c.color(GRAY));
        }
    }

    public static final class FoxBoots extends FoxCostume {
        public FoxBoots(final Mytems key) {
            super(key, leatherArmor(key.material, FOX_ORANGE), text("Fox Loafers", FOX_ORANGE));
        }

        @Override
        public List<Component> getBaseLore() {
            return Text.wrapLore("It's quiet. Too quiet.", c -> c.color(GRAY));
        }
    }
}
