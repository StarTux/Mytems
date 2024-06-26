package com.cavetale.mytems.item.swampy;

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
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.mytems.util.Items.deserialize;
import static com.cavetale.mytems.util.Items.tooltip;

@RequiredArgsConstructor @Getter
public abstract class SwampyItem implements GearItem {
    protected final Mytems key;
    private List<Component> baseLore;
    private Component displayName;
    private ItemStack prototype;
    private static SwampyItemSet swampyItemSet;

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName());
        prototype = deserialize(getSerialized());
        baseLore = Text.wrapLore("\n\n" + getDescription(), c -> c.color(NamedTextColor.DARK_GREEN));
        prototype.editMeta(meta -> {
                tooltip(meta, createTooltip());
                if (meta instanceof Repairable repairable) {
                    repairable.setRepairCost(9999);
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                if (meta instanceof LeatherArmorMeta) {
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                }
                key.markItemMeta(meta);
            });
    }

    protected final Component fancify(String in) {
        int len = in.length();
        TextComponent.Builder cb = Component.text();
        for (int i = 0; i < len; i += 1) {
            int green = 64 + (i * 64) / len;
            cb.append(Component.text(in.substring(i, i + 1), TextColor.color(0, green, 0)));
        }
        return cb.build();
    }

    abstract String getSerialized();

    abstract String getRawDisplayName();

    abstract String getDescription();

    @Override
    public final ItemSet getItemSet() {
        if (swampyItemSet == null) swampyItemSet = new SwampyItemSet();
        return swampyItemSet;
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    public static final class Helmet extends SwampyItem {
        @Getter private final String rawDisplayName = "Swampy Helmet";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ1TTU8TQRh+aYu2i3gwMRrjgWzijZBSpAaMJA0Fu5t2V+kHbA1pht237bQzu3V2FtwS7h5M+BEeNfHiH/Bq4i/wYrx7846zgnwY0cTbTN7na959VgNIw1SZSNJCEdLAB9BuZyFFPbjJqY+uIF25PGIkRtHpI/E0SEvSy8G1Nd/tE19y9GWoAUDmmDVNnkekQ7pd6lMZpyHNdpmaTsCJ6EgEA3QlZdhRR5kcA/8UNnkC0/4ymxIYjqggF4YZyMGNkpSC7kQSa4FHu1Q9KAmWykKmzgIJmSR/FqZPYRbhCNM99FFQd44IHog05OwRHosrLkxBptk0yonD0be39wAeHkLq+7ujo5dflewfBa6UeBD5EpLN/t361gVmRwZRr+9jGP5HiMulzsfR4KpHw+Rr/uKtPNvXJb6Q+rJe3yN8FM9UkHGU+qxOJWHU1Ze7hIU4q7sBC4SCeUQMOz2B6OsH2znIVAOB2Z/qd8+0fqcfbEPxnBNHxsIZRod4uU9PkFg5wNIZT3VCoj+j8qvOzf2bqoFWH0aM2XtqN+ryRARqrZJimINsohmpKiUVmcjCZIuwCOE9xma+vdXPe1smc2OjqO6Nep7ZxmD0wPBb8c6qUTS4mldKxWq8dA67KMnmInMWzH7bfxrt8Fa+urDBsLIx7/LmrjMu3a81hnln7BbssrHgFDaow82+xVvcKjQXrYKzZ5XNQa1sDWuDtbG1adJaw5l3BiZvb1q83WixdqM0thses8vr3H68zp3YXOpu5R8lDUkZXtKPN4fVLx8/Vz68/vTqzvX9lWby502uJgWYgB9jb8ap7AMAAA==";
        @Getter private final String description = "Smells like rotten plants.";

        public Helmet(final Mytems key) {
            super(key);
        }
    }

    public static final class Chestplate extends SwampyItem {
        @Getter private final String rawDisplayName = "Swampy Chestplate";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ1SO08bQRD+8J3N+YAmEo+CwjopHaKMhDtiU0QCUiDTRMQa7sbnjfd2rb1xEiuhpeC38NfSpXf2bMVOIjBSutHO99qZiYEAW10SumZXKmuA+CBCTWU4LJTh1NFA2ppJhuz66ZBLGWsSjhEI5U1sn5l0SEYKNlLG8GoL8u7Y2U+citLc96VUpTUBAv1Ze1QdC1i8rjcxt45ppEy+7IVo4tWpiFO3E+ELm6mB8rkr41qE8EpbQX2eMsLOEndJBWMnZ8NOpcfkCusCNN+P2ZHMvwxsIez13nUri9mPx9dA+ydweT+bPfhphE8KNE4LOzHiKRFe8N7/i9oXO8mHhsvyP1I8L7XK48eFRpcKyrlSjbGZqdKvbfpb5O2Hb4nwV0naydUXKsbTVme52eQoUUJapUl7QLrkoyS12joPzciN+rljNsndTRPhuXUcze0OV3r/0u9u8OYPt4K1Lltajdb45I6m3gEnK56/EmHT8gH9mR2/SA38IqoXfPy+B2yg3qkms4FfB4Naye4CAAA=";
        @Getter private final String description = "Smells like rotten plants.";

        public Chestplate(final Mytems key) {
            super(key);
        }
    }

    public static final class Leggings extends SwampyItem {
        @Getter private final String rawDisplayName = "Swampy Leggings";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ1SS28TMRD+mt2ETUovFaJVLgRL3KqekJByQK0aDkgFDlU5Ek03E8fEa0f2BIhK/hF/jTPH4DwgVCiA8MXj0fcaa1pAhv0eCb3lEI13QOu4QM0M0K6M4zLQULqWSUYc+pa1Nk7HFjIh3cT9F64ckZOKnaRm0lpTH22pk+DfcynGcj+Vsiy9qyGzHyzqWMMf3IH/FTN1N4FpnIJsMDmaODwXCeZmKvzKD8zQpGmWgWoF8ivrBXnKHgsc/IS9popxtFXV7DiY8pRC5UOG5psJB5LVjwD7yK+vX/ZSlS++fnmSrojs0C8W39rJYCV1cEeggcZ55adOzo6xPn8O8nhHkL74qR45jvE/Ih3tkNqGKzbhMjR6VJHm5aOFewMTJ5ZmP4Se3yojZE2pukOykU9U6a0PqqsGFMZ9HZidOlHCnyT1rj5SNZl1LjfLouZN5Jc+cLEyav+mtaGpOZ7u9NGBZr84VGxt7Fgz5sR69o+stFrCrpMGS9t6quYZ6isg3n1+COyhfrH8kz18B9A/J24UAwAA";
        @Getter private final String description = "Smells like rotten plants.";

        public Leggings(final Mytems key) {
            super(key);
        }
    }

    public static final class Boots extends SwampyItem {
        @Getter private final String rawDisplayName = "Swampy Boots";
        @Getter private final String serialized = "H4sIAAAAAAAAAJ1SzU7bQBD+wE5JTKNKSJQeeogs9YZQT0iN1AOQHpD6c0D0UoE1scfOkvWutZ7QRoUn6Kv0QfoslfoM6TpQCLSA1Nvuzvc3sxMBAVYHJPSRXa2sAaJnbSyrDBulMpw6yqWvmWTELhlaK3WEQKjo4PEbk47ISMmmeQRaF7xuxpWMklqcytgFCPSp9tUQF+Un+aVYTlorU1wBWpeA9crZE05FaU78UZqjNX/BovtqEzN0TONF+RAdrO2ITzWcCL+zmcqVb7kJvtxGeKCtIMyZpY3uFew9lYxuwYadSrfIldY31PlQsSOZDwtYRXh4uD9oHGa/vr8Aim8I1n7MZi9/etl/CjzaKe3ECJrZ32+9cYOZiJ0UI8N1/R8h7pZajONvAyqp4EY1wkqm6krT9I/I609fY+EvEvfjg89UVtPebrMS8WashLRK477/1Zo349Rq6zwqIzdOCsds4vOjDsK31nF77vT8Wuo2/fwI2wtGJWtd97Qa890+haOpd8Cra55fD2HT8/H9fm49SA3Qmr/g+OwpsITWXjOUJfwG8A6XuiIDAAA=";
        @Getter private final String description = "Smells like rotten plants.";

        public Boots(final Mytems key) {
            super(key);
        }
    }

    public static final class Weapon extends SwampyItem {
        @Getter private final String rawDisplayName = "Swampy Trident";
        @Getter private final String serialized = "H4sIAAAAAAAAAIWQvU/DQAzFXdLSJLCwMTBEN1eMSGRhoGyIBdQFVchK3PRU3110Zz5OqP87VwXRCgnh9b2f37NLgAxO5ii4IB+0swDleQ5HuoUzoy01HldSi9ctWSkhE+wyOJ6jwY4gTQnTVoeeMeYwfkBDcPP8qYQ+RNXq8R1NH6ungVYzpQVZN6peIQeaqcax88nXot+8dJ7Iqu2ygPG985TDrtnFftlvfLuEq4MoQ8yhYr2hv3M6jzElwPWe806EbJUOsBIu/0cLOL2zzTq5TToplLuWw7tybfqE2vSfjN84CRMYlCm7iCzxR8i+hSKs0feWQjhgYASTW/dqZQRfYbz5oZ0BAAA=";
        @Getter private final String description = "Smells like rotten plants.";

        public Weapon(final Mytems key) {
            super(key);
        }
    }

    @Getter
    public static final class SwampyItemSet implements ItemSet {
        private final String name = "Swampy";
        private final List<SetBonus> setBonuses = List.of(new PotionDuration(this, 2),
                                                          new DolphinGrace(this, 4));

        @Getter @RequiredArgsConstructor
        public static final class PotionDuration implements SetBonus {
            private final SwampyItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Potion Duration";
            private final String description = "Consumed potions last twice as long";

            @Override
            public void onPlayerPotionEffect(EntityPotionEffectEvent event, Player player) {
                if (event.isCancelled()) return;
                if (event.getCause() != EntityPotionEffectEvent.Cause.POTION_DRINK) return;
                switch (event.getAction()) {
                case ADDED: case CHANGED: break;
                default: return;
                }
                PotionEffect effect = event.getNewEffect();
                event.setCancelled(true);
                player.addPotionEffect(new PotionEffect(effect.getType(),
                                                        effect.getDuration() * 2,
                                                        effect.getAmplifier(),
                                                        effect.isAmbient(),
                                                        effect.hasParticles(),
                                                        effect.hasIcon()));
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class DolphinGrace implements SetBonus {
            private final SwampyItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Dolphin's Grace";
            private final String description = "Get the Dolphin's Grace effect";

            @Override
            public void tick(LivingEntity living, int has) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 20 + 19;
                int amplifier = 0;
                PotionEffectType type = PotionEffectType.DOLPHINS_GRACE;
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
