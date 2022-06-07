package com.cavetale.mytems;

import com.cavetale.core.command.AbstractCommand;
import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandContext;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.item.font.Glyph;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Blocks;
import com.cavetale.mytems.util.JavaItem;
import com.cavetale.mytems.util.Skull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public final class MytemsCommand extends AbstractCommand<MytemsPlugin> {
    @Getter protected CommandNode itemNode;

    protected MytemsCommand(final MytemsPlugin plugin) {
        super(plugin, "mytems");
    }

    @Override
    protected void onEnable() {
        rootNode.addChild("list").arguments("[tag]")
            .completableList(Stream.of(MytemsTag.values())
                             .map(MytemsTag::name)
                             .collect(Collectors.toList()))
            .description("Show some info on all mytems")
            .senderCaller(this::list);
        rootNode.addChild("give").arguments("<player> <mytem> [amount]")
            .description("Give an item to a player")
            .senderCaller(this::give)
            .completer(this::giveComplete);
        rootNode.addChild("equipment")
            .description("Display player equipment")
            .senderCaller(this::equipment);
        rootNode.addChild("session")
            .description("Display session info")
            .senderCaller(this::session);
        rootNode.addChild("fixall").denyTabCompletion()
            .description("Fix all player inventories")
            .senderCaller(this::fixall);
        rootNode.addChild("placeblock").arguments("<mytems> <facing>")
            .description("Place a Mytem as a block")
            .completers(CommandArgCompleter.enumLowerList(Mytems.class),
                        CommandArgCompleter.enumLowerList(BlockFace.class))
            .playerCaller(this::placeBlock);
        rootNode.addChild("glyph").arguments("<text...>")
            .description("Turn text into glyphs")
            .senderCaller(this::glyph);
        // Serialize
        CommandNode serializeNode = rootNode.addChild("serialize")
            .description("Item serialization commands");
        serializeNode.addChild("test").denyTabCompletion()
            .description("Test serialization on hand")
            .playerCaller(this::serializeTest);
        serializeNode.addChild("minecraft").denyTabCompletion()
            .description("Serialize to Minecraft JSON")
            .playerCaller(this::serializeMinecraft);
        serializeNode.addChild("mytems").denyTabCompletion()
            .description("Serialize hand via mytems")
            .playerCaller(this::serializeMytems);
        serializeNode.addChild("base64").denyTabCompletion()
            .description("Serialize hand via base64")
            .playerCaller(this::serializeBase64);
        serializeNode.addChild("head").denyTabCompletion()
            .description("Serialize player head in hand")
            .playerCaller(this::serializeHead);
        serializeNode.addChild("java").arguments("item")
            .completers(CommandArgCompleter.enumLowerList(Mytems.class))
            .description("Serialize item to Java")
            .senderCaller(this::serializeJava);
        itemNode = rootNode.addChild("item")
            .description("Item specific commands");
        // Damage Calculation
        CommandNode damageCalcNode = rootNode.addChild("damagecalc")
            .description("Damage calculation commands");
        damageCalcNode.addChild("toggledebug").denyTabCompletion()
            .description("Toggle player debug spam")
            .playerCaller(this::damageCalcToggleDebug);
    }

    protected boolean list(CommandSender sender, String[] args) {
        if (args.length > 1) return false;
        List<Component> lines = new ArrayList<>();
        MytemsTag tag = null;
        if (args.length >= 1) {
            tag = MytemsTag.of(args[0]);
            if (tag == null) throw new CommandWarn("Unknown tag: " + args[0]);
        }
        for (Mytems mytems : Mytems.values()) {
            if (tag != null && !tag.isTagged(mytems)) continue;
            lines.add(join(noSeparators(),
                           text(mytems.ordinal() + ") ", GRAY),
                           mytems.component.insertion(GsonComponentSerializer.gson().serialize(mytems.component)),
                           mytems.getMytem().getDisplayName(),
                           Component.space(),
                           text(mytems.id, DARK_GRAY).insertion(mytems.id)));
        }
        sender.sendMessage(join(separator(Component.newline()), lines));
        return true;
    }

    protected boolean give(CommandSender sender, String[] args) {
        if (args.length != 2 && args.length != 3) return false;
        String targetArg = args[0];
        String mytemArg = args[1];
        String amountArg = args.length >= 3 ? args[2] : null;
        Player target = Bukkit.getPlayer(targetArg);
        if (target == null) throw new CommandWarn("Player not found: " + targetArg);
        List<Mytems> mytemsList;
        if (mytemArg.startsWith("#")) {
            MytemsTag tag = MytemsTag.of(mytemArg.substring(1));
            if (tag == null) throw new CommandWarn("Invalid Mytems tag: " + mytemArg);
            mytemsList = tag.getMytems();
        } else {
            Mytems mytems = Mytems.forId(mytemArg);
            if (mytems == null) throw new CommandWarn("Mytem not found: " + mytemArg);
            mytemsList = Arrays.asList(mytems);
        }
        int amount = 1;
        if (amountArg != null) {
            try {
                amount = Integer.parseInt(amountArg);
            } catch (NumberFormatException nfe) {
                amount = -1;
            }
        }
        if (amount < 1) throw new CommandWarn("Invalid amount: " + amountArg);
        for (Mytems mytems : mytemsList) {
            ItemStack item = mytems.createItemStack();
            item.setAmount(amount);
            int retain = 0;
            for (ItemStack drop : target.getInventory().addItem(item).values()) {
                retain += drop.getAmount();
            }
            if (retain >= amount) {
                throw new CommandWarn("Could not add item to inventory: " + mytems + ", " + target.getName());
            }
            Component component = Component.empty().color(YELLOW)
                .append(text("" + (amount - retain)).color(WHITE))
                .append(text("x").color(DARK_GRAY))
                .append(mytems.component)
                .append(mytems.getMytem().getDisplayName())
                .append(text(" given to " + target.getName()).color(YELLOW));
            sender.sendMessage(component);
        }
        return true;
    }


    protected boolean equipment(CommandSender sender, String[] args) {
        Player target;
        if (args.length == 0 && sender instanceof Player) {
            target = (Player) sender;
        } else if (args.length == 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) throw new CommandWarn("Player not found: " + args[0]);
        } else {
            return false;
        }
        Equipment equipment = plugin.sessions.of(target).getEquipment();
        sender.sendMessage("Items " + equipment.getItems()); // Equipped::toString
        sender.sendMessage("Sets " + equipment.getItemSets().entrySet().stream()
                           .map(e -> e.getKey().getName() + ":" + e.getValue())
                           .collect(Collectors.joining(", ")));
        sender.sendMessage("Bonuses " + equipment.getSetBonuses().stream()
                           .map(b -> "(" + b.getRequiredItemCount() + ")" + b.getName())
                           .collect(Collectors.joining(", ")));
        sender.sendMessage("Attrs " + equipment.getEntityAttributes());
        return true;
    }

    protected boolean session(CommandSender sender, String[] args) {
        int index = 0;
        for (Session session : plugin.sessions.all()) {
            UUID uuid = session.getUuid();
            String name = session.getName();
            boolean online = session.getPlayer() != null;
            int i = index++;
            sender.sendMessage(i + ") " + uuid + " " + name + " " + online);
        }
        return true;
    }

    protected boolean fixall(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        plugin.fixAllPlayerInventoriesLater();
        sender.sendMessage(ChatColor.YELLOW + "Fixing all player inventories");
        return true;
    }

    protected boolean glyph(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        String original = String.join(" ", args);
        StringBuilder result = new StringBuilder();
        List<Component> preview = new ArrayList<>();
        for (int i = 0; i < original.length(); i += 1) {
            char c = original.charAt(i);
            Glyph glyph = c == ' ' ? null : Glyph.toGlyph(c);
            result.append(glyph != null
                          ? ":" + glyph.mytems.name().toLowerCase() + ":"
                          : "" + c);
            preview.add(glyph != null
                        ? glyph.mytems.component
                        : text(c));
        }
        String output = result.toString();
        sender.sendMessage(text("Preview ", YELLOW)
                           .append(join(noSeparators(),
                                        preview)
                                   .color(WHITE)
                                   .insertion(output)));
        sender.sendMessage(text("Raw ", YELLOW)
                           .append(text(output, WHITE).insertion(output)));
        return true;
    }

    protected boolean serializeTest(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new CommandWarn("No item in your hand!");
        }
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) {
            throw new CommandWarn("No Mytem in your hand!");
        }
        itemStack = itemStack.clone();
        String serialized = mytems.serializeItem(itemStack);
        player.sendMessage(ChatColor.YELLOW + "Testing: " + serialized);
        for (int i = 0; i < 3; i += 1) {
            ItemStack itemStack2 = Mytems.deserializeItem(serialized);
            String serialized2 = mytems.serializeItem(itemStack2);
            boolean sameItem = Objects.equals(itemStack, itemStack2);
            boolean sameText = Objects.equals(serialized, serialized2);
            if (sameItem && sameText) {
                player.sendMessage(ChatColor.YELLOW + "Step " + (i + 1) + ": " + ChatColor.GREEN + "OK");
            } else {
                player.sendMessage(ChatColor.YELLOW + "Step " + (i + 1) + ":" + ChatColor.RED
                                   + " sameItem=" + sameItem + " sameText=" + sameText
                                   + " serial=" + serialized2);
            }
            if (itemStack2 == null || serialized2 == null) break;
            itemStack = itemStack2;
            serialized = serialized2;
        }
        return true;
    }

    protected boolean serializeMinecraft(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new CommandWarn("No item in your hand!");
        }
        String serialized = itemStack.getType().getKey()
            + (itemStack.hasItemMeta()
               ? itemStack.getItemMeta().getAsString()
               : "");
        player.sendMessage(join(noSeparators(),
                                text("Serialized item: ", YELLOW),
                                text(serialized).insertion(serialized)));
        return true;
    }

    protected boolean serializeMytems(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new CommandWarn("No item in your hand!");
        }
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) {
            throw new CommandWarn("No Mytem in your hand!");
        }
        String serialized = mytems.serializeItem(itemStack);
        player.sendMessage(ChatColor.YELLOW + "Serialized item in hand: " + ChatColor.RESET + serialized);
        plugin.getLogger().info(serialized);
        return true;
    }

    protected boolean serializeBase64(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new CommandWarn("There's no item in your main hand!");
        }
        byte[] bytes = itemStack.serializeAsBytes();
        String string = Base64.getEncoder().encodeToString(bytes);
        Component component = text("Base64: ").color(GRAY).insertion(string)
            .append(text(string).color(WHITE));
        player.sendMessage(component);
        plugin.getLogger().info("Serialize " + itemStack.getType() + ": " + string);
        plugin.getDataFolder().mkdirs();
        File file = new File(plugin.getDataFolder(), "base64.txt");
        try {
            Files.write(file.toPath(), string.getBytes());
        } catch (IOException ioe) {
            plugin.getLogger().log(Level.SEVERE, "Writing " + file, ioe);
        }
        return true;
    }

    protected boolean serializeHead(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() != Material.PLAYER_HEAD) {
            throw new CommandWarn("There's no player head in your main hand!");
        }
        Skull skull = Skull.of(itemStack);
        player.sendMessage(Json.serialize(skull));
        plugin.getLogger().info("Serialize Head: " + Json.serialize(skull));
        plugin.getDataFolder().mkdirs();
        File file = new File(plugin.getDataFolder(), "head.json");
        Json.save(file, skull, true);
        return true;
    }

    protected boolean serializeJava(CommandSender sender, String[] args) {
        ItemStack itemStack;
        if (args.length >= 1) {
            String serialized = String.join(" ", args);
            itemStack = Mytems.deserializeItem(serialized);
            if (itemStack == null) {
                throw new CommandWarn("Invalid item: " + serialized);
            }
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            itemStack = player.getInventory().getItemInMainHand();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                throw new CommandWarn("There's no item in your main hand!");
            }
        } else {
            return false;
        }
        List<String> lines = JavaItem.serializeToLines(itemStack);
        String string = String.join("\n", lines);
        sender.sendMessage(text(string, YELLOW));
        plugin.getLogger().info("Serialize " + itemStack.getType() + ": " + string);
        plugin.getDataFolder().mkdirs();
        File file = new File(plugin.getDataFolder(), "Item.java");
        try {
            Files.write(file.toPath(), (string + "\n").getBytes());
        } catch (IOException ioe) {
            plugin.getLogger().log(Level.SEVERE, "Writing " + file, ioe);
        }
        return true;
    }

    protected boolean placeBlock(Player player, String[] args) {
        if (args.length != 2) return false;
        Mytems mytems = Mytems.forId(args[0]);
        if (mytems == null) throw new CommandWarn("Invalid id: " + args[0]);
        BlockFace blockFace;
        try {
            blockFace = BlockFace.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new CommandWarn("Invalid block face: " + args[1]);
        }
        Block block = player.getLocation().getBlock();
        player.sendMessage(text("Spawning ", YELLOW)
                           .append(mytems.getMytem().getDisplayName())
                           .append(text(" as block at"
                                        + " " + block.getX()
                                        + " " + block.getY()
                                        + " " + block.getZ())));
        Blocks.place(mytems, block, blockFace);
        return true;
    }

    protected boolean damageCalcToggleDebug(Player player, String[] args) {
        if (args.length != 0) return false;
        if (plugin.getEventListener().getDamageCalculationDebugPlayers().contains(player.getUniqueId())) {
            plugin.getEventListener().getDamageCalculationDebugPlayers().remove(player.getUniqueId());
            player.sendMessage(text("Damage calculation debug spam disabled", YELLOW));
        } else {
            plugin.getEventListener().getDamageCalculationDebugPlayers().add(player.getUniqueId());
            player.sendMessage(text("Damage calculation debug spam enabled", AQUA));
        }
        return true;
    }

    protected List<String> giveComplete(CommandContext context, CommandNode node, String[] args) {
        if (args.length <= 1) return null; // <player>
        if (args.length > 2) return Collections.emptyList();
        String arg = args[args.length - 1];
        return Stream.concat(Stream.of(Mytems.values()).map(m -> m.id),
                             Stream.of(MytemsTag.values()).map(t -> "#" + t.name()))
            .filter(s -> s.contains(arg))
            .collect(Collectors.toList());
    }

    public CommandNode registerItemCommand(Mytems key) {
        return itemNode.addChild(key.id);
    }
}
