package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Attr;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * Easter equipment.
 */
@RequiredArgsConstructor @Getter
public abstract class EasterGear implements GearItem {
    protected final Mytems key;
    private List<BaseComponent[]> baseLore;
    private BaseComponent[] displayName;
    private ItemStack prototype;
    private static EasterItemSet easterItemSet;
    ChatColor pinkChatColor = ChatColor.of("#FF69B4");
    Color pinkColor = Color.fromRGB(255, 105, 180);

    @Override
    public final void enable() {
        displayName = fancify(getRawDisplayName(), false);
        if (getSerialized() != null) {
            prototype = Items.deserialize(getSerialized());
        } else {
            prototype = new ItemStack(key.material);
        }
        ItemMeta meta = prototype.getItemMeta();
        baseLore = Text.wrapLore(Text.colorize("\n\n" + getDescription()), cb -> cb.color(pinkChatColor).italic(false));
        updateItemLore(meta);
        if (meta instanceof Repairable) {
            ((Repairable) meta).setRepairCost(9999);
            meta.setUnbreakable(true);
        }
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(pinkColor);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
        }
        process(meta);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    protected final BaseComponent[] fancify(String in, boolean bold) {
        int len = in.length();
        ComponentBuilder cb = new ComponentBuilder();
        cb.append("").italic(false);
        if (bold) cb.bold(true);
        for (int i = 0; i < len; i += 1) {
            cb.append(in.substring(i, i + 1)).color(ChatColor.of(new java.awt.Color(255, 105 + i + i, 180 + i)));
        }
        return cb.create();
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
                String description = "(" + need + ") " + setBonus.getDescription();
                lore.addAll(Text.toBaseComponents(Text.wrapLines(description, Text.ITEM_LORE_WIDTH),
                                                  cb -> cb.color(count >= need ? pinkChatColor : ChatColor.DARK_GRAY).italic(false)));
            }
        }
        meta.setLoreComponents(lore);
    }

    @Override
    public final ItemStack getItem() {
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
            EquipmentSlot slot = EquipmentSlot.HEAD;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, UUID.fromString("faa92578-e897-488a-8fab-5894c5709aae"), key.id, 3.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, UUID.fromString("8738a874-3d4a-46af-b70f-6a54abad23f7"), key.id, 3.0, slot);
        }
    }

    public static final class Chestplate extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Chestplate";

        public Chestplate(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlot slot = EquipmentSlot.CHEST;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, UUID.fromString("fd152947-66c8-48d1-a91a-4d4942719476"), key.id, 8.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, UUID.fromString("81ae88f5-3ea3-41ef-9198-53c98700cd8e"), key.id, 3.0, slot);
        }
    }

    public static final class Leggings extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Leggings";

        public Leggings(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlot slot = EquipmentSlot.LEGS;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, UUID.fromString("b0fd7a5d-e955-4a96-b3f3-ff09ea928ec5"), key.id, 6.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, UUID.fromString("08d2a467-c2d1-42ae-85d0-9da8571d1f70"), key.id, 3.0, slot);
        }
    }

    public static final class Boots extends EasterGear {
        @Getter private final String rawDisplayName = "Easter Boots";

        public Boots(final Mytems key) {
            super(key);
        }

        @Override
        protected void process(ItemMeta meta) {
            EquipmentSlot slot = EquipmentSlot.FEET;
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR, UUID.fromString("cbf6b90d-2531-49fa-9af3-550688d04a8e"), key.id, 3.0, slot);
            Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, UUID.fromString("ade6e795-7252-4be4-b24f-63cb4e224e3c"), key.id, 3.0, slot);
        }
    }

    public static final class EasterItemSet implements ItemSet {
        @Getter private final List<SetBonus> setBonuses = Arrays
            .asList(new JumpBoost(2), new BunnyHop(4));

        @Getter @RequiredArgsConstructor
        public static final class JumpBoost implements SetBonus {
            private final int requiredItemCount;
            private final String name = "Jump Boost";
            private final String description = "Get Jump Boost and take no fall damage";

            @Override
            public void tick(LivingEntity living) {
                if (!(living instanceof Player)) return;
                Player player = (Player) living;
                int duration = 20 + 19;
                int amplifier = 2;
                PotionEffectType type = PotionEffectType.JUMP;
                PotionEffect potionEffect = player.getPotionEffect(type);
                if (potionEffect != null) {
                    if (potionEffect.getAmplifier() > amplifier) return;
                    if (potionEffect.getDuration() > duration) return;
                }
                potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }

            @Override
            public void onPlayerDamage(EntityDamageEvent event, Player player) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                }
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class BunnyHop implements SetBonus {
            static final int COOLDOWN = 3;
            private final int requiredItemCount;
            private final String name = "Bunny Hop";
            private final String description = "Jump like the Easter Bunny! (" + COOLDOWN + "s cooldown)";

            @Override
            public void onPlayerJump(PlayerJumpEvent event, Player player) {
                if (event.isCancelled()) return;
                if (player.isSneaking()) return;
                Session session = MytemsPlugin.getInstance().getSessions().of(player);
                if (session.getCooldownInTicks("easter.jump") > 0) return;
                Vector direction = player.getLocation().getDirection().setY(0);
                if (direction.length() < 0.1) return;
                session.setCooldown("easter.jump", COOLDOWN * 20);
                Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> {
                        player.setVelocity(player.getVelocity().add(direction.normalize().multiply(1.0).setY(0.3)));
                    });
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, SoundCategory.PLAYERS, 0.7f, 2.0f);
            }
        }
    }
}