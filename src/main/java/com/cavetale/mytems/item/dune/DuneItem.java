package com.cavetale.mytems.item.dune;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.EntityAttribute;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.gear.Slot;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor @Getter
public abstract class DuneItem implements GearItem {
    protected final Mytems key;
    private List<Component> baseLore;
    private Component displayName;
    private ItemStack prototype;
    private static DuneItemSet duneItemSet;

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName(), false);
        prototype = Items.deserialize(getSerialized());
        ItemMeta meta = prototype.getItemMeta();
        baseLore = Text.wrapLore("\n\n" + getDescription());
        updateItemLore(meta);
        if (meta instanceof Repairable) {
            ((Repairable) meta).setRepairCost(9999);
            meta.setUnbreakable(true);
        }
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    protected final Component fancify(String in, boolean bold) {
        int len = in.length();
        Component component = Component.empty().decoration(TextDecoration.ITALIC, false);
        if (bold) component = component.decorate(TextDecoration.BOLD);
        for (int i = 0; i < len; i += 1) {
            int red = 128 + (i * 127) / len;
            component = component.append(Component.text(in.substring(i, i + 1)).color(TextColor.color(red, 128, 0)));
        }
        return component;
    }

    abstract String getSerialized();

    abstract String getRawDisplayName();

    abstract String getDescription();

    @Override
    public final ItemSet getItemSet() {
        if (duneItemSet == null) duneItemSet = new DuneItemSet();
        return duneItemSet;
    }

    @Override
    public final void updateItemLore(ItemMeta meta, Player player, Equipment equipment, Slot slot) {
        meta.displayName(displayName);
        List<Component> lore = new ArrayList<>(baseLore);
        ItemSet itemSet = getItemSet();
        List<SetBonus> setBonuses = itemSet.getSetBonuses();
        if (!setBonuses.isEmpty()) {
            int count = equipment == null ? 0 : equipment.countSetItems(itemSet);
            lore.add(Component.empty());
            lore.add(fancify("Set Bonus [" + count + "]", slot != null));
            for (SetBonus setBonus : itemSet.getSetBonuses()) {
                int need = setBonus.getRequiredItemCount();
                String description = "(" + need + ") " + setBonus.getDescription();
                TextColor color = count >= need ? NamedTextColor.YELLOW : NamedTextColor.DARK_GRAY;
                lore.addAll(Text.wrapLore(description, c -> c.color(color)));
            }
        }
        meta.lore(lore);
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    public static final class Helmet extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Helmet";
        @Getter private final String serialized = "H4sIAAAAAAAAALVSy04TURj+6UWnRTZGgpoY60QTF4QUCjUQTWwolylMK6UXZjbkMHPaOe25NGfOgCPhSdwaVy59Bp/CV/AZ8EwRCxqMLpzV/DPf7b/kAdIwXUUKdbAMieAA+fsGpIgPs4xw7EnUU2sjimIsDwOM/DykFern4M4G9wLEFcNchXnQMhesBxNWj0h8OJJCYU9p5RSk6TGFLFwAH1+Rl2KQYOgf4PeuwX/F5OBuRSlJjiKFbeGTHtHdJKlSBmTqiGGY6WOOJfEWkGRC3oJbFSYirl4bMH7SkGuMsERqPAKAaci021ZVv2XOv316BhA9Atgxzs9nv2jFfSoUZJJxGDDz03jsMzfJec0RLoPMXft8qETUDzgOw/8X6ckNkSbekIfbPgmTNV/GXD41iUKUeOZaD9EQz5ueoEKaa2ZfUN+cNxV+q3RVjTgubGPKsDLPcpDZFRInDWTg4W8KPyjmGZRuVpconqhXeAFxj+gb06TyX5L6EZI+QbwguIf/wexERy+ogIQLmpSH/P4worRxoiemizdS6GUogsMcGAkhknh8YVMGZDuIRhg+47hWdA+Con9Qo15slXXd2i/ShjUYvbB4Jz5at8oW0/+3K+XdePUKdkWh7gp1SrXA5XvREesUd0tNirebix5rH9tdq+QMatR+Z5ecJXfotoYlt7qxVGedob3lLNrVJqm3gsBhm0OXOXGj215yt+xlt7s5sFmTOa29E7vVjm1WC+oDZ9ll1opNaqu9g+Kr5K5Slp9s7OvzvcXj7MbH908/7Lw87SS9QXY9Ockp+A7vzTP3KAQAAA==";
        @Getter private final String description = "\n"
            + ChatColor.YELLOW + "An ancient guardian once wore this mighty headpiece.";

        public Helmet(final Mytems key) {
            super(key);
        }
    }

    public static final class Chestplate extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Chestplate";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ2SzW7TQBDH/42dNHHpBSGVwgGzErfSCxKqciJqekDi44DKNZraE2dhvRutx9AK9S14AzjyQH0I+gxhnYSGCoUCe1vp9/+Y0SRAhK0hCb1lX2lngeRuFy2d436pLWeextIvnMnZjrIJVzI1JJwgEip6uHVkswlZKdlKlQCIF9rdlXasPY+m3glnEvxbiMwHgzYW4IMVGJh3DWP+gN+5ht/I1PbEM73XtlgyMXq4PRDx+qQWfulyPdZh7qZ5q4v4jXGC9nzMLravuFdUMnZ+WQdb9jrbJ186H6H3esqeZL48YAvx8fHzYbOL2eW3R8Djz2h9/zKbfb0ICXOr7WsGHXQGpautPEuxeDc0ebimyUhcXUwsV9V/dNpZY7Vq1122i9AZUkkFN58Em7muwk2c/TQ6+KS0kNGZ6o/JVLynMmecV33VXJHaU8KnEn7D2nJ6eHVR6ryH+IXz3MTEuPeby1KmzvFkfYKns1XCwKZkMx1OM4ie/qWoqMnnmmzqbMb/EPYxVE9loqv9IMIG2ofN0jbwA/5VSchhAwAA";
        @Getter private final String description = ""
            + ChatColor.YELLOW + "An ancient guardian once wore this breastplate.";

        public Chestplate(final Mytems key) {
            super(key);
        }
    }

    public static final class Leggings extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Leggings";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ1Sz0sbQRT+zK4xWRVBkFroISx4E2+C5BZMD4LWg9hLkfDcfZmMzs6E2bdaEf8j/7T21ns6m1ij0kbodb73/XjfmwSIsNonoa/sS+0skGy30NA5PhbacuZpKF3lTM52YFgpbVWZIBJSbax9ttmIrBRsJTwC8Yy5MdSeB2PvhDMJmhEic2MCvozZwFbArmrMLBxLFmGVvfRM1yHPMxajjc2eiNeXlfCJy/VQh63qZI0W4jPjBHFYomxh/XnsCxWMdcWWvc72yBfOR2ifjtmTTPsAVhGfnx/1a4fJz8cd4ETQ+PFrMuFQVfxXgWavcJWVQGlisfWHV8yBuEqNLJflf4T4t9Q8TigLzT4VpLhWTbCS63Js6O6PyMG3+1T4u6TdtF9Z7hw/HT3dTbWQ0VnaHZIpeTfNnHE+jNW/I324aCM+dp5b00N8mqu8JT5cYH+O9myHbKbDD1pg4OkuGLyMpiryuSbbcTbj95kvDG9Dxo6MdLn3Pg1LWD6si1vCb17XRLMpAwAA";
        @Getter private final String description = "\n"
            + ChatColor.YELLOW + "An ancient guardian once wore these leggings.";

        public Leggings(final Mytems key) {
            super(key);
        }
    }

    public static final class Boots extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Boots";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ2SzW4TMRSFTzOTNEnpBqGWnwXBEruqGyQWWTUQFiB+FqhsWEQ3M3cmBo8d2XeACvWNeBGehBW8QvA0paGKGn688rW+c+7x1e0DCXbGJPSGfdDOAv2bXbR0jr1KW848FTIsncnZTqbOSegjESp7uPbEZjOyUrFtHoH2UnZrJSu058ncO+FMonULiflg0MYSvLsCI/OuYcwG/MYl/I9McLWZhDlzfs6k60xtp57pvbblBdPD9ZGI19Na+IXLdaHjWJrftbpIXxsnSAtm6WL3AntJFWP/t2GxZa+zQ/KV8wl6r+bsSc5GC+wgPT5+Oo63dPH9y33gzlu0fnxbLL4+iw3OrHYvGXTQGVWutnLUxfJsDnLviiATcXU5sxzCf0Tav8JqPVyCzpgqKrkp+tjOdZgbOvll9OCz0kJGZ2pYkAl8oDJnnFdD1ayYOlDCnyRW49ry4FGzbuq0h/S589x0SHF7zeBcoU43mXs6WZmP7IBspuPeRtHDvxSVNflckx04m/E/NPsYow9kpsNhFGEL7cfNvLbwEyXJu/J5AwAA";
        @Getter private final String description = ""
            + ChatColor.YELLOW + "An ancient guardian once wore these golden boots.";

        public Boots(final Mytems key) {
            super(key);
        }
    }

    public static final class Weapon extends DuneItem {
        @Getter private final String rawDisplayName = "Dune Digger";
        @Getter private final String serialized = "H4sIAAAAAAAAAJWQMU8DMQyFXe5a2sAOA8Mpc2eQujBwbIgRBlQhk/pyFjmnSnIVFep/J1VRWzGA8Pr5+T0/BVDAWY0JnyhE9gKgLsdwwgu46FjIBGzSzHq3IHmNrV+RU1AktAWMauzQEuRRcLrguHS4HkP5iB3B9cunTvSR9EzXvVBVs7UU9FRzQsdGzxp0kabaeOdDXto66M18AuWDDzTON0u4Otz4KdzM4eZAn1tMFYphkvSLRcB1tjiOFnqWWHVs2//pOFW9mFxGuP1bN4HzezEtSupyvqjyb8WuYUVNw9vUZl1A4VYuoxHs2CS2GJZCMe7R8BupXt4C4TuL3bMSYADDO99LGsAXImUfsdUBAAA=";
        @Getter private final String description = ""
            + ChatColor.YELLOW + "What ancient ruins might this golden shovel uncover?";

        public Weapon(final Mytems key) {
            super(key);
        }
    }

    @Getter
    public static final class DuneItemSet implements ItemSet {
        private final String name = "Dune";
        private final List<SetBonus> setBonuses = Arrays.asList(new FlameThorns(2), new SandSpeed(4));

        @Getter @RequiredArgsConstructor
        public static final class SandSpeed implements SetBonus {
            private final int requiredItemCount;
            private final String name = "Sand Speed";
            private final String description = "Gain extra speed while walking on sand";
            private final List<EntityAttribute> entityAttributes = Arrays
                .asList(new EntityAttribute(Attribute.GENERIC_MOVEMENT_SPEED,
                                            UUID.fromString("51f94678-9053-4e51-a916-306925f592a6"),
                                            "duneSandSpeed",
                                            0.5, Operation.ADD_SCALAR));

            /**
             * Stored in Session.favorites.
             */
            static final class Favorite {
                long onSandUntil = 0;
                boolean onSand;
                int ticks;
            }

            @Override public List<EntityAttribute> getEntityAttributes(LivingEntity living) {
                if (!(living instanceof Player)) return null;
                Player player = (Player) living;
                final boolean onSand;
                long now = System.currentTimeMillis();
                Favorite favorite = MytemsPlugin.getInstance().getSessions().of(player).getFavorites().getOrSet(Favorite.class, Favorite::new);
                if (player.getLocation().add(0, -0.125, 0).getBlock().getType() == Material.SAND) {
                    onSand = true;
                    favorite.onSandUntil = now + 1000L;
                    favorite.onSand = true;
                } else {
                    long until = favorite.onSandUntil;
                    onSand = until >= now;
                    favorite.onSand = false;
                }
                return onSand ? entityAttributes : null;
            }

            @Override public void tick(LivingEntity living) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                Favorite favorite = MytemsPlugin.getInstance().getSessions().of(player).getFavorites().getOrSet(Favorite.class, Favorite::new);
                if (!favorite.onSand) return;
                if ((favorite.ticks++ % 2) != 0) return;
                player.getWorld().spawnParticle(Particle.SOUL, player.getLocation().add(0, 0.125, 0), 1, 0, 0, 0, 0);
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class FlameThorns implements SetBonus {
            private final int requiredItemCount;
            private final String name = "Flame Thorns";
            private final String description = "40% chance to set attackers on fire";

            @Override
            public void onPlayerDamageByEntity(EntityDamageByEntityEvent event, Player player, Entity damager) {
                if (event.isCancelled()) return;
                if (event.getCause() != EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) return;
                if (ThreadLocalRandom.current().nextDouble() > 0.4) return;
                int duration = 20 * 30;
                EntityCombustEvent combustEvent = new EntityCombustEvent(damager, duration);
                Bukkit.getPluginManager().callEvent(combustEvent);
                if (combustEvent.isCancelled()) return;
                damager.setFireTicks(Math.max(damager.getFireTicks(), duration));
            }
        }
    }
}
