package com.cavetale.mytems.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

/**
 * Convenience functions to format text. Primarily for item tooltips.
 */
public final class Text {
    public static final int ITEM_LORE_WIDTH = 30;

    private Text() { }

    public static List<String> wrapLine(String what, int maxLineLength) {
        String[] words = what.split("\\s+");
        List<String> lines = new ArrayList<>();
        if (words.length == 0) return lines;
        StringBuilder line = new StringBuilder(words[0]);
        int lineLength = ChatColor.stripColor(words[0]).length();
        String lastColors = "";
        for (int i = 1; i < words.length; ++i) {
            String word = words[i];
            int wordLength = ChatColor.stripColor(word).length();
            if (lineLength + wordLength + 1 > maxLineLength) {
                String lineStr = lastColors + line.toString();
                lines.add(lineStr);
                lastColors = ChatColor.getLastColors(lineStr);
                line = new StringBuilder(word);
                lineLength = wordLength;
            } else {
                line.append(" ");
                line.append(word);
                lineLength += wordLength + 1;
            }
        }
        if (line.length() > 0) lines.add(lastColors + line.toString());
        return lines;
    }

    public static List<String> wrapLines(String what, int maxLineLength) {
        return Stream.of(what.split("\n"))
            .map(line -> wrapLine(line, maxLineLength))
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    public static List<String> wrapMultiline(String input, int maxLineLength) {
        return flatten(Stream.of(input.split("\n\n"))
                       .map(par -> wrapLines(par, maxLineLength))
                       .collect(Collectors.toList()));
    }

    public static List<String> flatten(List<List<String>> list) {
        List<String> result = new ArrayList<>();
        if (list.isEmpty()) return result;
        result.addAll(list.get(0));
        for (int i = 1; i < list.size(); i += 1) {
            result.add("");
            result.addAll(list.get(i));
        }
        return result;
    }

    public static List<Component> toComponents(List<String> in) {
        List<Component> out = new ArrayList<>(in.size());
        for (String ins : in) {
            out.add(Component.text(ins));
        }
        return out;
    }

    public static List<Component> toComponents(List<String> in, Function<Component, Component> lineCallback) {
        List<Component> result = new ArrayList<>(in.size());
        for (String line : in) {
            Component component = Component.text(line);
            component = lineCallback.apply(component);
            result.add(component);
        }
        return result;
    }

    public static List<Component> wrapLore(String txt) {
        List<String> lines = wrapMultiline(txt, ITEM_LORE_WIDTH);
        List<Component> comps = toComponents(lines);
        return comps;
    }

    public static List<Component> wrapLore(String txt, Function<Component, Component> lineCallback) {
        List<String> lines = wrapMultiline(txt, ITEM_LORE_WIDTH);
        List<Component> comps = toComponents(lines, lineCallback);
        return comps;
    }

    public static String colorize(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static String toCamelCase(String in) {
        if (in == null) return "";
        return in.substring(0, 1).toUpperCase()
            + in.substring(1).toLowerCase();
    }

    public static String toCamelCase(Enum en, String glue) {
        String[] toks = en.name().split("_");
        for (int i = 0; i < toks.length; i += 1) {
            toks[i] = toCamelCase(toks[i]);
        }
        return String.join(glue, toks);
    }

    public static String toCamelCase(Enum en) {
        return toCamelCase(en, " ");
    }

    private static int clampRGB(int val) {
        return Math.min(255, Math.max(0, val));
    }

    public static Component gradient(String name, TextColor... colors) {
        if (colors == null) throw new IllegalArgumentException("colors = null");
        final int len = name.length();
        TextComponent.Builder comps = Component.text();
        for (int i = 0; i < len; i += 1) {
            String d = "" + name.charAt(i);
            double percentage = (double) i / (double) (len - 1);
            double colorPercentage = percentage * (double) (colors.length - 1);
            int colorIndexA = (int) Math.floor(colorPercentage);
            int colorIndexB = (int) Math.ceil(colorPercentage);
            TextColor colorA = colors[colorIndexA];
            TextColor colorB = colors[colorIndexB];
            double percentageA = (double) colorIndexA / (double) (colors.length - 1);
            double percentageB = (double) colorIndexB / (double) (colors.length - 1);
            double progressAB = colorIndexA == colorIndexB
                ? 0
                : (percentage - percentageA) / (percentageB - percentageA);
            double progressBA = 1.0 - progressAB;
            double r = (double) colorA.red()   * progressBA + colorB.red()   * progressAB;
            double g = (double) colorA.green() * progressBA + colorB.green() * progressAB;
            double b = (double) colorA.blue()  * progressBA + colorB.blue()  * progressAB;
            TextColor color = TextColor.color(clampRGB((int) Math.round(r)),
                                              clampRGB((int) Math.round(g)),
                                              clampRGB((int) Math.round(b)));
            comps.append(Component.text(d, color));
        }
        return comps.build();
    }
}
