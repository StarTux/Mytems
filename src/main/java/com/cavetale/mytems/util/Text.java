package com.cavetale.mytems.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
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
            out.add(Component.text(ins).decoration(TextDecoration.ITALIC, false));
        }
        return out;
    }

    public static List<Component> toComponents(List<String> in, Function<Component, Component> lineCallback) {
        List<Component> result = new ArrayList<>(in.size());
        for (String line : in) {
            Component component = Component.text(line).decoration(TextDecoration.ITALIC, false);
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
}
