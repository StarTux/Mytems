package com.cavetale.mytems.item.dwarven;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.mytems.util.Items.deserialize;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;

@RequiredArgsConstructor @Getter
public abstract class DwarvenItem implements GearItem {
    protected final Mytems key;
    private List<Component> baseLore;
    private Component displayName;
    private ItemStack prototype;
    private static DwarvenItemSet dwarvenItemSet;

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName());
        prototype = deserialize(getSerialized());
        baseLore = Text.wrapLore2("\n\n" + getDescription(), DwarvenItem::fancify);
        prototype.editMeta(meta -> {
                tooltip(meta, createTooltip());
                if (meta instanceof Repairable) {
                    ((Repairable) meta).setRepairCost(9999);
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                key.markItemMeta(meta);
            });
    }

    protected static final Component fancify(String in) {
        int len = in.length();
        TextComponent.Builder cb = text();
        for (int i = 0; i < len; i += 1) {
            int red = 64 + (i * 191) / len;
            cb.append(text(in.substring(i, i + 1), TextColor.color(red, red / 2, 0)));
        }
        return cb.build();
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
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    public static final class Helmet extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Helmet";
        @Getter private final String serialized = "H4sIAAAAAAAAAI1Sy07bQBS9JIE6pmyqPtmUek1ReAVBK5WItOCUOC0Q57FBE/smnjDjicZjwKn4gv5G11U/gH9od1X/oP2Ljnk0IITKLDy+mnPOPffMmABZmCwTRVyUERUhgPnUgAz14RGnIXqSdNXagJEE5X6AxDchq0gvD/ffhl5AQsUxVJEJWuacNT1idRiJ1P5ACoWe0tIZyLJDBuNwjnw2QnapxNuBD68YuYkx4Z5Po9ShATmHcISVTxZVhFHPWusSFuGs5QkmpLVm9QTzrVlL4bHSVfmIyEMMZ7aQcVTWSR5y20KikQ4D0zdELljWCRRvbyBJMmrwTsge+jM0nFEBat7rO/K4YEr7ikMfZU8Kvc9pdh4elJSStBMrrAqfdqm+sTT5zOXgUz0MUVJvjkgupAFT//Bnx09GOV4DZiFfG6Ak6uz6ASYhV6/bZf2Xg/PlpZ/8z1/rutMuEwpy6VOYgIkS1+7UunGBuzTy+Jr+noh7QYhRdMPRi1sc7atLyt29ffmPNxPM3YOYsdqRbqSLD1JoYUUxyoORBh9LPItzzIBxl7AY4RsmlUK7GRT8ZoV5iV3U9d5ugdXs/mDFDt2ks2EXba7Pt0rF7WT1CnZZkcYyay1Wgnb4Me5wt7C9uMNwa2fe4/VDh7vUGXrzDt+hreHBsL3ZWnbKraS65y21yw6tNuyl6tBl7X5pobap94bTd8pe0lqo61pjeYVW+25Q7Tu01S8d1xp12m3Or6b5ZGw/Tef719M/b56/n/jMX/1YOn35W88F4xtpImPwF03NFF31AwAA";
        @Getter private final String description = "\n"
            + "Forged in the molten underground.";

        public Helmet(final Mytems key) {
            super(key);
        }
    }

    public static final class Chestplate extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Chestplate";
        @Getter private final String serialized = "H4sIAAAAAAAAAJVRy04CMRQ9MoDDqBtjfK1I18alC3ShEU1MfCx8bEmduQxNOi25c/ER4+/5EX6NBUVCgES7aZue1+1JgAgrbS36gbg03gHJdoyKybBTGEcp6660DHvXSXtUSt9qoQSR6LyB1TOX9rSTgpyUCYLUN3N3wny0upROn71QKkG+gsg+WdQw49E1TIuBGxPgHEyC5cyUIdtrjOq1LgiHb8qItiZVra62Je2p1FvPqqVybzO1p4ReJNzaz5qfyDVPf4dT7w1ULz1TPBwIuzNCP0z1joPFJqxfJybnnnPKmsY1pRf0cfRHXuGthGwDlxHn7MO+H9gR6m1d6JxCPjSwfiLC5nEgdOUz0zWhxmEVlfATt9YLaqPe6qifFEFBjpsYrQiNmz6xllHnwAqq9/cX7XCqfgOQjvQ/PzbGf7qWkyM26b7mwnOMtV/nOc9Y5B//1/9l7L85ZXDnB3nPUVnOBNmawnVkDASWUDsdhljCF+mNHWb4AgAA";
        @Getter private final String description = ""
            + "Forged in the molten underground.";

        public Chestplate(final Mytems key) {
            super(key);
        }
    }

    public static final class Leggings extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Leggings";
        @Getter private final String serialized = "H4sIAAAAAAAAAI2SzU4CMRDHR3bBBeRijF8n7NlwNJF4kIgmJqgHP04mpO4OpUm3JbMDSgyv4DP5Gr6N5cvVKIm9tJ3+/jP/dloBCKDaliwfkDLtLEBlN4KCTmAn1RZjkj1uanK2a1ApbVVWgYClKsPGuY370nKKln3QJ5rr9nPdk5EZdwfkGGP2yQsQmJGBIszJvZzsacLV4FYO/sFUYD3R2cDIcQThtUwRjl+FZml0LJo9aTI8FLEzjkRTKGcScSgYX9jv2s+SRmjrncXVxKQMYccRRtPrwP6vNAudmMDR6hIkx3mJC0cKk7q2de6j1538U5c6w97Z0CZIipyfG14dQKktU6nQ+4MybLaYST8NGa9convat3DaiMLyHWoKLZKOG5JSRxHUvvjZ8bcO/wADKN8MkCTP/gNAFcL7+8u2X4UwH/Gs/sfbo690axxD6H9HVoJSK/VW+XR3wS2NbP/If+eGqm8xy345OljhqMtLyf+9va/wFi04WIPi2TSyBp/vbngIBgMAAA==";
        @Getter private final String description = "\n"
            + "Forged in the molten underground.";

        public Leggings(final Mytems key) {
            super(key);
        }
    }

    public static final class Boots extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarven Boots";
        @Getter private final String serialized = "H4sIAAAAAAAAAK2RSU8CQRCFC2ZAFkmMMW4n0mfC0QPxIIomJi6JilfSzNQMnfR0k5oCJYY/6q+xB0QkoPHgqbf3vnrVVQHwoNqRLJ+RUmUNQOWwBHkVwl6iDAYkI24psqbXt5bTCngs4zJsX5pgIA0naLJLR5mbjpemvpYp94ZkGQN25Dx4eqyhAHPl0VIZKcKfhd9ybNBUYCtU6VDLSQn8O5kgnLwJxVKrQLQiqVNsiMBqS6IlYqtD0RCMr+xOnRdJYzT186wvMS2Df2MJS1kvcLzG+DSJ6W98kpMl/8pSjGFdmToP0PlO/+hLrGYXa2RCpJisW5vO7UGxIxMZo8sHZdhtM5Pqjxhvbagi5YaXTSHvPuFRWwY/QuQiFNuJA/BZ1tWssfL9EEnybNAAVfC73euO2/lzAQQz/PvDzuI3azEaJBU0JSWWSlD7KrzhGf6rPC/K76/wn+woHhhM07UcByu6Hi+EADkoXGQhcvABD4Uy9+wCAAA=";
        @Getter private final String description = ""
            + "Forged in the molten underground.";

        public Boots(final Mytems key) {
            super(key);
        }
    }

    public static final class Weapon extends DwarvenItem {
        @Getter private final String rawDisplayName = "Dwarf Axe";
        @Getter private final String serialized = "H4sIAAAAAAAAAI2QMU/DMBCFX5NS0sAEC2KKPFcMDAwRCyIwIUZWdNiXxIpjV7aBVKj/HSMqdaiQuOV00vve3b0SyHHSUKQX9kE7C5QXBTKtcDZqy9JTG2vtnX2liUvkkboci4ZG6hipShwrHdaGNgXmzzQyrr+EjmS0FHVLJvBKSGecF7XonFFiJSJPMU3NJ/m2uptYbJeYPznPBX6uuTzgd4DY4uZvb0+bvfej8x2rStsq9skft//kRmci2+rdKvadd6lfJXqJ0wcre7JxZBtDmc7MfjM632c0WCeHN5JDhtx8mPTIgST05NeWQ9hJFsAMR/dpTZzhG8dOomeKAQAA";
        @Getter private final String description = ""
            + "Forged in the molten underground.";

        public Weapon(final Mytems key) {
            super(key);
        }
    }

    @Getter
    public static final class DwarvenItemSet implements ItemSet {
        private final String name = "Dwarven";
        private final List<SetBonus> setBonuses = List.of(new NightVision(this, 2),
                                                          new MiningSpeed(this, 4));

        @Getter @RequiredArgsConstructor
        public static final class MiningSpeed implements SetBonus {
            private final DwarvenItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Mining Speed";
            private final String description = "Get the Haste effect";

            @Override
            public void tick(LivingEntity living, int has) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 20 + 19;
                int amplifier = 0;
                PotionEffectType type = PotionEffectType.HASTE;
                PotionEffect potionEffect = player.getPotionEffect(type);
                if (potionEffect != null) {
                    if (potionEffect.getAmplifier() > amplifier) return;
                    if (potionEffect.getAmplifier() == amplifier && potionEffect.getDuration() >= duration) return;
                }
                potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class NightVision implements SetBonus {
            private final DwarvenItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Night Vision";
            private final String description = "See in the dark";

            @Override
            public void tick(LivingEntity living, int has) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 200 + 19;
                int amplifier = 0;
                PotionEffectType type = PotionEffectType.NIGHT_VISION;
                PotionEffect potionEffect = player.getPotionEffect(type);
                if (potionEffect != null) {
                    if (potionEffect.getAmplifier() > amplifier) return;
                    if (potionEffect.getAmplifier() == amplifier && potionEffect.getDuration() >= duration) return;
                }
                potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }
        }
    }
}
