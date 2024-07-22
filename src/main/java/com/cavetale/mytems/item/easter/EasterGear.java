package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Attr;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import static com.cavetale.mytems.util.Items.deserialize;
import static com.cavetale.mytems.util.Items.tooltip;

/**
 * Easter equipment.
 */
@RequiredArgsConstructor @Getter
public abstract class EasterGear implements GearItem {
    protected final Mytems key;
    private List<Component> baseLore;
    private Component displayName;
    private ItemStack prototype;
    private static EasterItemSet easterItemSet;
    static final TextColor PINK_CHAT_COLOR = TextColor.color(0xFF69B4);
    static final Color PINK_COLOR = Color.fromRGB(255, 105, 180);

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName());
        if (getSerialized() != null) {
            prototype = deserialize(getSerialized());
        } else {
            prototype = new ItemStack(key.material);
        }
        baseLore = Text.wrapLore("\n\n" + getDescription(), cb -> cb.color(PINK_CHAT_COLOR));
        prototype.editMeta(meta -> {
                tooltip(meta, createTooltip());
                if (meta instanceof Repairable) {
                    ((Repairable) meta).setRepairCost(9999);
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                if (meta instanceof LeatherArmorMeta) {
                    ((LeatherArmorMeta) meta).setColor(PINK_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                }
                process(meta);
                key.markItemMeta(meta);
            });
    }

    protected final Component fancify(String in) {
        int len = in.length();
        TextComponent.Builder cb = Component.text();
        for (int i = 0; i < len; i += 1) {
            cb.append(Component.text(in.substring(i, i + 1), TextColor.color(255, 105 + i + i, 180 + i)));
        }
        return cb.build();
    }

    /**
     * Get the serialized String or null if the Mytems material is to
     * be used.
     */
    protected String getSerialized() {
        return null;
    }

    protected abstract String getRawDisplayName();

    /**
     * Get the lore description.
     */
    protected String getDescription() {
        return "This is the gear of a true warrior bunny. Or the bunny warrior. Either expression is valid.";
    }

    protected abstract void process(ItemMeta meta);

    @Override
    public final ItemSet getItemSet() {
        if (easterItemSet == null) easterItemSet = new EasterItemSet();
        return easterItemSet;
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    public static final class Helmet extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Helmet";
        @Getter private final String serialized = "H4sIAAAAAAAAAE2PS07CQByH/7wiljUuXBniAQoNGEhMVCAwDZ1SHi1DTMxAB2mZDqQPoTWewRu48wbGa3gEE8/gASw7l9/vsfgkgByUOjSkJvMDZysApPMiZB0byp4j2NKnq7C14zRm/sOaUVuCXEgfJTixneAYFyGPqceg/FwJ2SGstCpDR2wu7iIh4soLSCCNNxHn+l4wP4Whv90xP3RYcArF4yHyWSABQKYIBZPyiMEHi1V5PlvL9kzlyxg1Up6MZa4jd3eFhBkv2qiBvLTv3zYGcfPfth5Sq86Joq7nwogWnikPlBFn/VF16U2fNAslZGKvyYTUSW3k4E43xjWjiq3ugbiGgr2RhxOjqvXIXktUh1iGQpJuVbewq09slyScY9fkWgclmmvUsTWV5221uZrJ1wAlyCI7Fcl/37yTrx4KXj9/3s7ufy9TNyi0t5EIM/AH+4LNoW8BAAA=";

        public Helmet(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlotGroup slot = EquipmentSlotGroup.HEAD;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "easter_helmet_armor", 3.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "easter_helmet_armor_toughness", 3.0, slot);
        }
    }

    public static final class Chestplate extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Chestplate";

        public Chestplate(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlotGroup slot = EquipmentSlotGroup.CHEST;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "easter_chestplate_armor", 8.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "easter_chestplate_armor_toughness", 3.0, slot);
        }
    }

    public static final class Leggings extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Leggings";

        public Leggings(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlotGroup slot = EquipmentSlotGroup.LEGS;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "easter_leggings_armor", 6.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "easter_leggings_armor_toughness", 3.0, slot);
        }
    }

    public static final class Boots extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Boots";

        public Boots(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlotGroup slot = EquipmentSlotGroup.FEET;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "easter_boots_armor", 3.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "easter_boots_armor_toughness", 3.0, slot);
        }
    }

    @Getter
    public static final class EasterItemSet implements ItemSet {
        private final String name = "Easter";
        private final List<SetBonus> setBonuses = List.of(new JumpBoost(this, 2),
                                                          new BunnyHop(this, 4));

        @Getter @RequiredArgsConstructor
        public static final class JumpBoost implements SetBonus {
            private final EasterItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Jump Boost";
            private final String description = "Get Jump Boost and take no fall damage";

            @Override
            public void tick(LivingEntity living, int has) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 20 + 19;
                int amplifier = 2;
                PotionEffectType type = PotionEffectType.JUMP_BOOST;
                PotionEffect potionEffect = player.getPotionEffect(type);
                if (potionEffect != null) {
                    if (potionEffect.getAmplifier() > amplifier) return;
                    if (potionEffect.getAmplifier() == amplifier && potionEffect.getDuration() >= duration) return;
                }
                potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }

            @Override
            public void onPlayerDamage(EntityDamageEvent event, Player player) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                    Particle.DustOptions dust = new Particle.DustOptions(PINK_COLOR, 2.0f);
                    player.getWorld().spawnParticle(Particle.DUST, player.getLocation(), 16, 0.35, 0.1, 0.35, 0.2, dust);
                }
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class BunnyHop implements SetBonus {
            static final Duration COOLDOWN = Duration.ofSeconds(3);
            private final EasterItemSet itemSet;
            private final int requiredItemCount;
            private final String name = "Bunny Hop";
            private final String description = "Run and jump like the Easter Bunny! (" + COOLDOWN.toSeconds() + "s cooldown)";

            @Override
            public void onPlayerJump(PlayerJumpEvent event, Player player) {
                if (event.isCancelled()) return;
                if (player.isSneaking()) return;
                if (!player.isSprinting()) return;
                Session session = MytemsPlugin.getInstance().getSessions().of(player);
                if (session.isOnCooldown(Mytems.EASTER_HELMET)) return;
                Vector direction = player.getLocation().getDirection().setY(0);
                if (direction.length() < 0.1) return;
                session.cooldown(Mytems.EASTER_HELMET).duration(COOLDOWN);
                Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> {
                        player.setVelocity(player.getVelocity().add(direction.normalize().multiply(1.0).setY(0.3)));
                    });
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, SoundCategory.PLAYERS, 1.5f, 1.5f);
                Particle.DustOptions dust = new Particle.DustOptions(PINK_COLOR, 2.0f);
                player.getWorld().spawnParticle(Particle.DUST, player.getLocation(), 16, 0.35, 0.1, 0.35, 0.2, dust);
            }
        }
    }
}
