package com.cavetale.mytems.item.dwarven;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor @Getter
public abstract class DwarvenItem implements GearItem {
    protected final Mytems key;
    private List<BaseComponent[]> baseLore;
    private BaseComponent[] displayName;
    private ItemStack prototype;
    private static DwarvenItemSet dwarvenItemSet;

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName(), false);
        prototype = Items.deserialize(getSerialized());
        ItemMeta meta = prototype.getItemMeta();
        baseLore = Text.wrapLore(Text.colorize("\n\n" + getDescription()));
        updateItemLore(meta);
        if (meta instanceof Repairable) {
            ((Repairable) meta).setRepairCost(9999);
            meta.setUnbreakable(true);
        }
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    protected final BaseComponent[] fancify(String in, boolean bold) {
        int len = in.length();
        ComponentBuilder cb = new ComponentBuilder();
        cb.append("").italic(false);
        for (int i = 0; i < len; i += 1) {
            int red = 64 + (i * 191) / len;
            cb.append(in.substring(i, i + 1)).color(ChatColor.of(new java.awt.Color(red, red / 2, 0)));
            if (bold) cb.bold(true);
        }
        return cb.create();
    }

    abstract String getSerialized();

    abstract String getRawDisplayName();

    abstract String getDescription();

    @Override
    public final ItemSet getItemSet() {
        if (dwarvenItemSet == null) dwarvenItemSet = new DwarvenItemSet();
        return dwarvenItemSet;
    }

    @Override
    public final void updateItemLore(ItemMeta meta, Player player, Equipment equipment, Equipment.Slot slot) {
        meta.setDisplayNameComponent(displayName);
        List<BaseComponent[]> lore = new ArrayList<>(baseLore);
        ItemSet itemSet = getItemSet();
        List<SetBonus> setBonuses = itemSet.getSetBonuses();
        if (!setBonuses.isEmpty()) {
            int count = equipment == null ? 0 : equipment.countSetItems(itemSet);
            lore.add(Text.toBaseComponents(""));
            lore.add(fancify("Set Bonus [" + count + "]", slot != null));
            for (SetBonus setBonus : itemSet.getSetBonuses()) {
                int need = setBonus.getRequiredItemCount();
                String description = count >= need
                    ? (ChatColor.GOLD + "(" + need + ") " + ChatColor.GOLD
                       + setBonus.getDescription().replace(ChatColor.RESET.toString(), ChatColor.BLUE.toString()))
                    : (ChatColor.DARK_GRAY + "(" + need + ") " + ChatColor.GRAY
                       + setBonus.getDescription().replace(ChatColor.RESET.toString(), ChatColor.GRAY.toString()));
                lore.addAll(Text.toBaseComponents(Text.wrapLines(description, Text.ITEM_LORE_WIDTH)));
            }
        }
        meta.setLoreComponents(lore);
    }

    @Override
    public final ItemStack getItem() {
        return prototype.clone();
    }

    public static final class Helmet extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Helmet";
        @Getter private final String serialized = "H4sIAAAAAAAAAI1Sy07bQBS9JIE6pmyqPtmUek1ReAVBK5WItOCUOC0Q57FBE/smnjDjicZjwKn4gv5G11U/gH9od1X/oP2Ljnk0IITKLDy+mnPOPffMmABZmCwTRVyUERUhgPnUgAz14RGnIXqSdNXagJEE5X6AxDchq0gvD/ffhl5AQsUxVJEJWuacNT1idRiJ1P5ACoWe0tIZyLJDBuNwjnw2QnapxNuBD68YuYkx4Z5Po9ShATmHcISVTxZVhFHPWusSFuGs5QkmpLVm9QTzrVlL4bHSVfmIyEMMZ7aQcVTWSR5y20KikQ4D0zdELljWCRRvbyBJMmrwTsge+jM0nFEBat7rO/K4YEr7ikMfZU8Kvc9pdh4elJSStBMrrAqfdqm+sTT5zOXgUz0MUVJvjkgupAFT//Bnx09GOV4DZiFfG6Ak6uz6ASYhV6/bZf2Xg/PlpZ/8z1/rutMuEwpy6VOYgIkS1+7UunGBuzTy+Jr+noh7QYhRdMPRi1sc7atLyt29ffmPNxPM3YOYsdqRbqSLD1JoYUUxyoORBh9LPItzzIBxl7AY4RsmlUK7GRT8ZoV5iV3U9d5ugdXs/mDFDt2ks2EXba7Pt0rF7WT1CnZZkcYyay1Wgnb4Me5wt7C9uMNwa2fe4/VDh7vUGXrzDt+hreHBsL3ZWnbKraS65y21yw6tNuyl6tBl7X5pobap94bTd8pe0lqo61pjeYVW+25Q7Tu01S8d1xp12m3Or6b5ZGw/Tef719M/b56/n/jMX/1YOn35W88F4xtpImPwF03NFF31AwAA";
        @Getter private final String description = "\n"
            + "&6Forged in the molten underground.";

        public Helmet(final Mytems key) {
            super(key);
        }
    }

    public static final class Chestplate extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Chestplate";
        @Getter private final String serialized = "H4sIAAAAAAAAAJVRy04CMRQ9MoDDqBtjfK1I18alC3ShEU1MfCx8bEmduQxNOi25c/ER4+/5EX6NBUVCgES7aZue1+1JgAgrbS36gbg03gHJdoyKybBTGEcp6660DHvXSXtUSt9qoQSR6LyB1TOX9rSTgpyUCYLUN3N3wny0upROn71QKkG+gsg+WdQw49E1TIuBGxPgHEyC5cyUIdtrjOq1LgiHb8qItiZVra62Je2p1FvPqqVybzO1p4ReJNzaz5qfyDVPf4dT7w1ULz1TPBwIuzNCP0z1joPFJqxfJybnnnPKmsY1pRf0cfRHXuGthGwDlxHn7MO+H9gR6m1d6JxCPjSwfiLC5nEgdOUz0zWhxmEVlfATt9YLaqPe6qifFEFBjpsYrQiNmz6xllHnwAqq9/cX7XCqfgOQjvQ/PzbGf7qWkyM26b7mwnOMtV/nOc9Y5B//1/9l7L85ZXDnB3nPUVnOBNmawnVkDASWUDsdhljCF+mNHWb4AgAA";
        @Getter private final String description = ""
            + "&6Forged in the molten underground.";

        public Chestplate(final Mytems key) {
            super(key);
        }
    }

    public static final class Leggings extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Leggings";
        @Getter private final String serialized = "H4sIAAAAAAAAAI2SzU4CMRDHR3bBBeRijF8n7NlwNJF4kIgmJqgHP04mpO4OpUm3JbMDSgyv4DP5Gr6N5cvVKIm9tJ3+/jP/dloBCKDaliwfkDLtLEBlN4KCTmAn1RZjkj1uanK2a1ApbVVWgYClKsPGuY370nKKln3QJ5rr9nPdk5EZdwfkGGP2yQsQmJGBIszJvZzsacLV4FYO/sFUYD3R2cDIcQThtUwRjl+FZml0LJo9aTI8FLEzjkRTKGcScSgYX9jv2s+SRmjrncXVxKQMYccRRtPrwP6vNAudmMDR6hIkx3mJC0cKk7q2de6j1538U5c6w97Z0CZIipyfG14dQKktU6nQ+4MybLaYST8NGa9convat3DaiMLyHWoKLZKOG5JSRxHUvvjZ8bcO/wADKN8MkCTP/gNAFcL7+8u2X4UwH/Gs/sfbo690axxD6H9HVoJSK/VW+XR3wS2NbP/If+eGqm8xy345OljhqMtLyf+9va/wFi04WIPi2TSyBp/vbngIBgMAAA==";
        @Getter private final String description = "\n"
            + "&6Forged in the molten underground.";

        public Leggings(final Mytems key) {
            super(key);
        }
    }

    public static final class Boots extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Boots";
        @Getter private final String serialized = "H4sIAAAAAAAAAK2RSU8CQRCFC2ZAFkmMMW4n0mfC0QPxIIomJi6JilfSzNQMnfR0k5oCJYY/6q+xB0QkoPHgqbf3vnrVVQHwoNqRLJ+RUmUNQOWwBHkVwl6iDAYkI24psqbXt5bTCngs4zJsX5pgIA0naLJLR5mbjpemvpYp94ZkGQN25Dx4eqyhAHPl0VIZKcKfhd9ybNBUYCtU6VDLSQn8O5kgnLwJxVKrQLQiqVNsiMBqS6IlYqtD0RCMr+xOnRdJYzT186wvMS2Df2MJS1kvcLzG+DSJ6W98kpMl/8pSjGFdmToP0PlO/+hLrGYXa2RCpJisW5vO7UGxIxMZo8sHZdhtM5Pqjxhvbagi5YaXTSHvPuFRWwY/QuQiFNuJA/BZ1tWssfL9EEnybNAAVfC73euO2/lzAQQz/PvDzuI3azEaJBU0JSWWSlD7KrzhGf6rPC/K76/wn+woHhhM07UcByu6Hi+EADkoXGQhcvABD4Uy9+wCAAA=";
        @Getter private final String description = ""
            + "&6Forged in the molten underground.";

        public Boots(final Mytems key) {
            super(key);
        }
    }

    public static final class Weapon extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarf Axe";
        @Getter private final String serialized = "H4sIAAAAAAAAAI2QMU/DMBCFX5NS0sAEC2KKPFcMDAwRCyIwIUZWdNiXxIpjV7aBVKj/HSMqdaiQuOV00vve3b0SyHHSUKQX9kE7C5QXBTKtcDZqy9JTG2vtnX2liUvkkboci4ZG6hipShwrHdaGNgXmzzQyrr+EjmS0FHVLJvBKSGecF7XonFFiJSJPMU3NJ/m2uptYbJeYPznPBX6uuTzgd4DY4uZvb0+bvfej8x2rStsq9skft//kRmci2+rdKvadd6lfJXqJ0wcre7JxZBtDmc7MfjM632c0WCeHN5JDhtx8mPTIgST05NeWQ9hJFsAMR/dpTZzhG8dOomeKAQAA";
        @Getter private final String description = ""
            + "&6Forged in the molten underground.";

        public Weapon(final Mytems key) {
            super(key);
        }
    }

    public static final class DwarvenItemSet implements ItemSet {
        @Getter private final List<SetBonus> setBonuses = Arrays
            .asList(new NightVision(2), new MiningSpeed(4));

        @Getter @RequiredArgsConstructor
        public static final class MiningSpeed implements SetBonus {
            private final int requiredItemCount;
            private final String name = "Mining Speed";
            private final String description = "Get the Haste effect";

            @Override
            public void tick(LivingEntity living) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 20 + 19;
                int amplifier = 0;
                PotionEffectType type = PotionEffectType.FAST_DIGGING;
                PotionEffect potionEffect = player.getPotionEffect(type);
                if (potionEffect != null) {
                    if (potionEffect.getAmplifier() > amplifier) return;
                    if (potionEffect.getDuration() > duration) return;
                }
                potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class NightVision implements SetBonus {
            private final int requiredItemCount;
            private final String name = "Night Vision";
            private final String description = "See in the dark";

            @Override
            public void tick(LivingEntity living) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 200 + 19;
                int amplifier = 0;
                PotionEffectType type = PotionEffectType.NIGHT_VISION;
                PotionEffect potionEffect = player.getPotionEffect(type);
                if (potionEffect != null) {
                    if (potionEffect.getAmplifier() > amplifier) return;
                    if (potionEffect.getDuration() > duration) return;
                }
                potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }
        }
    }
}
