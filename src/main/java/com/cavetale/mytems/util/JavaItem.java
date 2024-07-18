package com.cavetale.mytems.util;

import com.cavetale.core.util.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Utility class to turn an ItemStack into Java code.
 */
public final class JavaItem {
    private JavaItem() { }

    public static List<String> serializeToLines(ItemStack itemStack) {
        List<String> lines = new ArrayList<>();
        Material material = itemStack.getType();
        int amount = itemStack.getAmount();
        lines.add(amount > 1
                  ? String.format("ItemStack itemStack = new ItemStack(Material.%s, %d);", material.name(), amount)
                  : String.format("ItemStack itemStack = new ItemStack(Material.%s);", material.name()));
        List<String> metaLines = serializeToLines(itemStack.getItemMeta());
        if (!metaLines.isEmpty()) {
            lines.add("itemStack.editMeta(meta -> {");
            for (String metaLine : metaLines) {
                lines.add("    " + metaLine);
            }
            lines.add("    });");
        }
        return lines;
    }

    public static List<String> serializeToLines(ItemMeta meta) {
        List<String> lines = new ArrayList<>();
        lines.add("// " + meta.getClass().getName());
        // Enchantments
        for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
            @SuppressWarnings("deprecation") // There is NO alternative!
            final String name = entry.getKey().getName();
            lines.add(String.format("meta.addEnchant(Enchantment.%s, %d, true);", name, entry.getValue()));
        }
        // Attributes
        var attributes = meta.getAttributeModifiers();
        if (attributes != null && !attributes.isEmpty()) {
            for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
                Attribute attribute = entry.getKey();
                AttributeModifier mod = entry.getValue();
                List<String> argList = new ArrayList<>();
                argList.add(serialize(mod.getUniqueId()));
                argList.add(quote(mod.getName()));
                argList.add("" + mod.getAmount());
                argList.add("AttributeModifier.Operation." + mod.getOperation());
                if (mod.getSlotGroup() != null) {
                    argList.add("EquipmentSlotGroup." + mod.getSlotGroup());
                }
                lines.add(String.format("meta.addAttributeModifier(Attribute.%s,", attribute.name()));
                lines.addAll(sandwich("    new AttributeModifier(", "        ", argList, ",", "));"));
            }
        }
        if (meta.isUnbreakable()) {
            lines.add("meta.setUnbreakable(true);");
        }
        if (meta.hasCustomModelData()) {
            lines.add(String.format("meta.setCustomModelData(%d);", meta.getCustomModelData()));
        }
        // Flags
        Set<ItemFlag> flags = meta.getItemFlags();
        if (flags.size() == ItemFlag.values().length) {
            lines.add("meta.addItemFlags(ItemFlag.values());");
        } else if (!flags.isEmpty()) {
            List<String> flagList = flags.stream().map(f -> "ItemFlag." + f).collect(Collectors.toList());
            lines.addAll(sandwich("meta.addItemFlags(", "    ", flagList, ",", ");"));
        }
        // Special Metas
        if (meta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) meta;
            Skull skull = Skull.of(skullMeta);
            if (skull.getTexture() != null) {
                lines.add("Skull.apply((SkullMeta) meta,");
                lines.add("    " + (skull.getName() != null ? quote(skull.getName()) : "null") + ",");
                lines.add("    " + (skull.getUuid() != null ? serialize(skull.getUuid()) : "null") + ",");
                lines.add("    " + quote(skull.getTexture()) + ",");
                lines.add("    " + (skull.getSignature() != null ? quote(skull.getSignature()) : "null") + ");");
            }
        }
        if (meta instanceof BannerMeta bannerMeta) {
            lines.add("BannerMeta bannerMeta = (BannerMeta) meta;");
            List<String> patternList = new ArrayList<>();
            for (Pattern pattern : bannerMeta.getPatterns()) {
                DyeColor patternColor = pattern.getColor();
                PatternType patternType = pattern.getPattern();
                patternList.add(String.format("new Pattern(DyeColor.%s, PatternType.%s)",
                                              patternColor.name(),
                                              patternType.name()));
            }
            lines.addAll(sandwich("bannerMeta.setPatterns(List.of(", "    ", patternList, ",", "));"));
        }
        if (meta instanceof BlockStateMeta) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            lines.add("// " + blockStateMeta.getBlockState().getClass().getName());
            if (blockStateMeta.getBlockState() instanceof Banner) {
                Banner banner = (Banner) blockStateMeta.getBlockState();
                lines.add("BlockStateMeta blockStateMeta = (BlockStateMeta) meta;");
                lines.add("Banner banner = (Banner) blockStateMeta.getBlockState();");
                lines.add("banner.setBaseColor(DyeColor." + banner.getBaseColor() + ");");
                List<String> patternList = new ArrayList<>();
                for (Pattern pattern : banner.getPatterns()) {
                    patternList.add(String.format("new Pattern(DyeColor.%s, PatternType.%s)",
                                                  pattern.getColor().name(),
                                                  pattern.getPattern().name()));
                }
                lines.addAll(sandwich("banner.setPatterns(List.of(", "    ", patternList, ",", "));"));
                lines.add("blockStateMeta.setBlockState(banner);");
            }
        }
        if (meta instanceof LeatherArmorMeta) {
            lines.add(String.format("((LeatherArmorMeta) meta).setColor(Color.fromRGB(0x%s));",
                                    Integer.toHexString(((LeatherArmorMeta) meta).getColor().asRGB())));
        }
        // DisplayName
        if (meta.displayName() != null) {
            lines.addAll(sandwich("meta.displayName(", serializeToLines(meta.displayName()), ");"));
        }
        // Lore
        List<Component> lore = meta.lore();
        if (lore != null && !lore.isEmpty()) {
            List<String> loreLines = new ArrayList<>();
            for (int i = 0; i < lore.size(); i += 1) {
                Component component = lore.get(i);
                loreLines.addAll(i < lore.size() - 1
                                 ? sandwich("", serializeToLines(component), ",")
                                 : serializeToLines(component));
            }
            lines.addAll(sandwich("meta.lore(List.of(", loreLines, "));"));
        }
        return lines;
    }

    /**
     * Return a single Component statement, __without__ trailing
     * semicolon.
     */
    public static List<String> serializeToLines(Component component) {
        if (Component.empty().equals(component)) {
            return new ArrayList<>(List.of("Component.empty()"));
        }
        if (Component.newline().equals(component)) {
            return new ArrayList<>(List.of("Component.newline()"));
        }
        if (Component.space().equals(component)) {
            return new ArrayList<>(List.of("Component.space()"));
        }
        if (!(component instanceof TextComponent)) {
            return new ArrayList<>(List.of("\"" + component.getClass().getName() + "\""));
        }
        TextComponent textComponent = (TextComponent) component;
        String text = textComponent.content();
        TextColor color = textComponent.color();
        List<String> decorations = new ArrayList<>();
        List<String> negatedDecorations = new ArrayList<>();
        for (Map.Entry<TextDecoration, TextDecoration.State> entry : textComponent.decorations().entrySet()) {
            if (entry.getValue() == TextDecoration.State.TRUE) {
                decorations.add(serialize(entry.getKey()));
            } else if (entry.getValue() == TextDecoration.State.FALSE) {
                negatedDecorations.add(serialize(entry.getKey()));
            }
        }
        List<String> lines = new ArrayList<>();
        String result = color == null && decorations.isEmpty()
            ? String.format("Component.text(%s)", quote(text))
            : (String.format("Component.text(%s, %s%s)",
                             quote(text),
                             color != null ? serialize(color) : "null",
                             decorations.isEmpty() ? "" : ", " + String.join(", ", decorations)));
        for (String decoration : negatedDecorations) {
            result += String.format(".decoration(%s, false)", decoration);
        }
        lines.add(result);
        for (Component child : component.children()) {
            lines.addAll(sandwich(".append(", serializeToLines(child), ")"));
        }
        return lines;
    }

    public static String serialize(TextColor textColor) {
        return textColor instanceof NamedTextColor
            ? "NamedTextColor." + textColor.toString().toUpperCase()
            : String.format("TextColor.color(0x%s)", Integer.toHexString(textColor.value()));
    }

    public static String serialize(TextDecoration textDecoration) {
        return "TextDecoration." + textDecoration.name();
    }

    public static List<String> sandwich(String prefix, List<String> lines, String suffix) {
        if (lines.isEmpty()) return new ArrayList<>(List.of(prefix + suffix));
        lines.set(0, prefix + lines.get(0));
        int last = lines.size() - 1;
        lines.set(last, lines.get(last) + suffix);
        return lines;
    }

    public static List<String> sandwich(String firstPrefix, String prefix,
                                        List<String> in,
                                        String suffix, String lastSuffix) {
        List<String> lines = new ArrayList<>(in);
        int last = lines.size() - 1;
        for (int i = 0; i <= last; i += 1) {
            String line = lines.get(i);
            if (i == 0) {
                line = firstPrefix + line;
            }
            if (i == last) {
                line = line + lastSuffix;
            }
            if (i > 0) {
                line = prefix + line;
            }
            if (i < last) {
                line = line + suffix;
            }
            lines.set(i, line);
        }
        return lines;
    }

    public static String quote(String in) {
        String tail = in;
        List<String> list = new ArrayList<>();
        do {
            int index = tail.indexOf('\u00a7');
            if (index < 0) {
                list.add(Json.serialize(tail));
                tail = "";
                continue;
            }
            if (index > 0) {
                list.add(Json.serialize(tail.substring(0, index)));
            }
            if (tail.length() >= 2) {
                ChatColor chatColor = ChatColor.getByChar(tail.charAt(1));
                list.add(chatColor != null
                         ? "ChatColor." + chatColor.name()
                         : Json.serialize("\\u00a7" + tail.substring(1, 2)));
                tail = tail.substring(2);
            } else {
                list.add(Json.serialize("\\u00a7"));
                tail = "";
            }
        } while (!tail.isEmpty());
        if (list.size() > 2 && !list.get(0).startsWith("\"") && !list.get(1).startsWith("\"")) {
            return "\"\" + " + String.join(" + ", list);
        }
        return String.join(" + ", list);
    }

    public static String serialize(UUID uuid) {
        return "UUID.fromString(" + quote(uuid.toString()) + ")";
    }
}
