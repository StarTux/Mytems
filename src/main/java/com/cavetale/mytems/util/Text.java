package com.cavetale.mytems.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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
                lastColors = org.bukkit.ChatColor.getLastColors(lineStr);
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

    public static List<BaseComponent[]> toBaseComponents(List<String> in) {
        List<BaseComponent[]> out = new ArrayList<>(in.size());
        for (String ins : in) {
            out.add(toBaseComponents(ins));
        }
        return out;
    }

    public static BaseComponent[] toBaseComponents(String in) {
        BaseComponent[] bs = new BaseComponent[1];
        bs[0] = new TextComponent(in);
        return bs;
    }

    public static List<BaseComponent[]> toBaseComponents(List<String> in, Consumer<ComponentBuilder> lineCallback) {
        List<BaseComponent[]> result = new ArrayList<>(in.size());
        for (String line : in) {
            ComponentBuilder cb = new ComponentBuilder(line);
            lineCallback.accept(cb);
            result.add(cb.create());
        }
        return result;
    }

    public static List<BaseComponent[]> wrapLore(String txt) {
        List<String> lines = wrapMultiline(txt, ITEM_LORE_WIDTH);
        List<BaseComponent[]> comps = toBaseComponents(lines);
        return comps;
    }

    public static List<BaseComponent[]> wrapLore(String txt, Consumer<ComponentBuilder> lineCallback) {
        List<String> lines = wrapMultiline(txt, ITEM_LORE_WIDTH);
        List<BaseComponent[]> comps = toBaseComponents(lines, lineCallback);
        return comps;
    }

    public static String colorize(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static HoverEvent hover(String msg) {
        BaseComponent[] lore = TextComponent.fromLegacyText(msg);
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, lore);
    }

    public static HoverEvent hover(BaseComponent[] lore) {
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, lore);
    }

    public static ClickEvent click(String cmd) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd);
    }

    public static ComponentBuilder builder(String txt) {
        return new ComponentBuilder(txt);
    }
}
