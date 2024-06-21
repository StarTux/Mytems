package com.cavetale.mytems;

import com.cavetale.core.command.AbstractCommand;
import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandContext;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.core.item.ItemKinds;
import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.custom.NetheriteParityGui;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.item.axis.CuboidOutline;
import com.cavetale.mytems.item.coin.BankTeller;
import com.cavetale.mytems.item.font.Glyph;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Blocks;
import com.cavetale.mytems.util.Collision;
import com.cavetale.mytems.util.Entities;
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
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;
import static net.kyori.adventure.text.event.HoverEvent.showText;
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
        rootNode.addChild("placeblock").arguments("<mytems> <facing> <small>")
            .description("Place a Mytem as a block")
            .completers(CommandArgCompleter.enumLowerList(Mytems.class),
                        CommandArgCompleter.enumLowerList(BlockFace.class),
                        CommandArgCompleter.BOOLEAN)
            .playerCaller(this::placeBlock);
        rootNode.addChild("placeitemdisplay").arguments("<mytems> transient")
            .description("Place a Mytem as display item")
            .completers(CommandArgCompleter.enumLowerList(Mytems.class),
                        CommandArgCompleter.list("transient", "center"),
                        CommandArgCompleter.REPEAT)
            .playerCaller(this::placeItemDisplay);
        rootNode.addChild("glyph").arguments("<text...>")
            .description("Turn text into glyphs")
            .senderCaller(this::glyph);
        rootNode.addChild("animation").arguments("<mytem>")
            .description("Print animation frames")
            .completers(CommandArgCompleter.enumLowerList(Mytems.class))
            .senderCaller(this::animation);
        // Serialize
        CommandNode serializeNode = rootNode.addChild("serialize")
            .description("Item serialization commands");
        serializeNode.addChild("test").denyTabCompletion()
            .description("Test serialization on hand")
            .playerCaller(this::serializeTest);
        serializeNode.addChild("minecraft").denyTabCompletion()
            .description("Serialize to Minecraft JSON")
            .playerCaller(this::serializeMinecraft);
        serializeNode.addChild("component").denyTabCompletion()
            .description("Serialize to Component String")
            .playerCaller(this::serializeComponent);
        serializeNode.addChild("mytems").denyTabCompletion()
            .description("Serialize hand via mytems")
            .playerCaller(this::serializeMytems);
        serializeNode.addChild("base64").denyTabCompletion()
            .description("Serialize hand via base64")
            .playerCaller(this::serializeBase64);
        serializeNode.addChild("skull").denyTabCompletion()
            .description("Serialize player head in hand")
            .playerCaller(this::serializeSkull);
        serializeNode.addChild("java").arguments("<item>")
            .completers(CommandArgCompleter.enumLowerList(Mytems.class))
            .description("Serialize item to Java")
            .senderCaller(this::serializeJava);
        // Item
        itemNode = rootNode.addChild("item")
            .description("Item specific commands");
        // Damage Calculation
        CommandNode damageCalcNode = rootNode.addChild("damagecalc")
            .description("Damage calculation commands");
        damageCalcNode.addChild("toggledebug").denyTabCompletion()
            .description("Toggle player debug spam")
            .playerCaller(this::damageCalcToggleDebug);
        // Bank Teller
        rootNode.addChild("atm").arguments("<player>")
            .description("Open the bank teller")
            .senderCaller(this::bankTeller);
        // Custom
        rootNode.addChild("netheriteparity").arguments("[player]")
            .description("Open the Netherite Parity crafting GUI")
            .completers(CommandArgCompleter.NULL)
            .senderCaller(this::netheriteParity);
        rootNode.addChild("collisiontest").denyTabCompletion()
            .description("Test block collisions")
            .playerCaller(this::collisionTest);
        rootNode.addChild("worldedithighlight").arguments("<hexcolor>")
            .description("Highlight your WorldEdit selection")
            .alias("wehl")
            .playerCaller(this::worldEditHighlight);
        // Upgradable
        final CommandNode upgradableNode = rootNode.addChild("upgradable")
            .description("Upgradable item commands");
        upgradableNode.addChild("addxp").arguments("<xp>")
            .completers(CommandArgCompleter.integer(i -> i > 0))
            .description("Add xp to upgradable item in hand")
            .playerCaller(this::upgradableAddXp);
        upgradableNode.addChild("setlevel").arguments("<level>")
            .completers(CommandArgCompleter.integer(i -> i >= 0))
            .description("Set the level of the upgradable item in hand")
            .playerCaller(this::upgradableSetLevel);
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
                           space(),
                           text(mytems.id, DARK_GRAY).insertion(mytems.id)));
        }
        sender.sendMessage(join(separator(newline()), lines));
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
            Component component = empty().color(YELLOW)
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
        sender.sendMessage(text("Fixing all player inventories", YELLOW));
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

    protected boolean animation(CommandSender sender, String[] args) {
        if (args.length != 1) return false;
        Mytems mytems = CommandArgCompleter.requireEnum(Mytems.class, args[0]);
        if (mytems.character == (char) 0) throw new CommandWarn(mytems + " does not have a chat character!");
        sender.sendMessage(text(mytems + " has " + mytems.getAnimationFrameCount() + " animation frames:", AQUA));
        sender.sendMessage(join(separator(space()), mytems.getAnimationFrames()));
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
        player.sendMessage(text("Testing: " + serialized, YELLOW));
        for (int i = 0; i < 3; i += 1) {
            ItemStack itemStack2 = Mytems.deserializeItem(serialized);
            String serialized2 = mytems.serializeItem(itemStack2);
            boolean sameItem = Objects.equals(itemStack, itemStack2);
            boolean sameText = Objects.equals(serialized, serialized2);
            if (sameItem && sameText) {
                player.sendMessage(textOfChildren(text("Step " + (i + 1) + ": ", YELLOW),
                                                  text("OK", GREEN)));
            } else {
                player.sendMessage(textOfChildren(text("Step " + (i + 1) + ":", YELLOW),
                                                  text(" sameItem=" + sameItem + " sameText=" + sameText
                                                       + " serial=" + serialized2, RED)));
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

    private void serializeComponent(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.isEmpty()) {
            throw new CommandWarn("No item in your hand!");
        }
        final String serialized = itemStack.getType().getKey()
            + itemStack.getItemMeta().getAsComponentString();
        player.sendMessage(textOfChildren(text("Component string of item: ", YELLOW),
                                          text(serialized))
                           .insertion(serialized));
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
        player.sendMessage(textOfChildren(text("Serialized item in hand: ", YELLOW),
                                          text(serialized)));
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

    protected boolean serializeSkull(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() != Material.PLAYER_HEAD) {
            throw new CommandWarn("There's no player head in your main hand!");
        }
        Skull skull = Skull.of(itemStack);
        player.sendMessage(text(Json.serialize(skull), AQUA)
                           .hoverEvent(showText(text("" + skull.getTexture(), YELLOW)))
                           .insertion("" + skull.getTexture()));
        plugin.getLogger().info("Serialize Head: " + Json.prettyPrint(skull));
        plugin.getDataFolder().mkdirs();
        File file = new File(plugin.getDataFolder(), "skull.json");
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
        final Location location = player.getLocation();
        final ItemDisplay itemDisplay = Blocks.place(mytems, location, id -> { });
        if (itemDisplay == null) throw new CommandWarn("Item Display could not be spawned!");
        player.sendMessage(textOfChildren(text("Placed ", YELLOW), mytems,
                                          text(" at" + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ(), GRAY))
                           .hoverEvent(showText(text(itemDisplay.getUniqueId().toString(), GRAY)))
                           .insertion(itemDisplay.getUniqueId().toString()));
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

    private boolean bankTeller(CommandSender sender, String[] args) {
        if (args.length != 1) return false;
        Player player = CommandArgCompleter.requirePlayer(args[0]);
        BankTeller.open(player);
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

    private boolean netheriteParity(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                throw new CommandWarn("[mytems:netheriteparity] player expected");
            }
            new NetheriteParityGui(player).open();
            sender.sendMessage(text("Netherite parity crafting GUI opened"));
            return true;
        } else if (args.length == 1) {
            Player target = CommandArgCompleter.requirePlayer(args[0]);
            new NetheriteParityGui(target).open();
            sender.sendMessage(text("Netherite parity crafting GUI opened for " + target.getName(), YELLOW));
            return true;
        } else {
            return false;
        }
    }

    protected boolean placeItemDisplay(Player player, String[] args) {
        if (args.length < 1) return false;
        Mytems mytems = Mytems.forId(args[0]);
        if (mytems == null) throw new CommandWarn("Invalid id: " + args[0]);
        boolean transientEntity = false;
        boolean doCenter = false;
        for (int i = 1; i < args.length; i += 1) {
            switch (args[i]) {
            case "transient": transientEntity = true; break;
            case "center": doCenter = true; break;
            default: throw new CommandWarn("Unknown flag: " + args[i]);
            }
        }
        Location location = player.getLocation();
        if (doCenter) {
            location.setX(Math.floor(location.getX()) + 0.5);
            location.setY(Math.floor(location.getY()) + 0.5);
            location.setZ(Math.floor(location.getZ()) + 0.5);
        }
        ItemDisplay is = Blocks.place(mytems, location, e -> { });
        if (transientEntity) {
            is.setPersistent(false);
            Entities.setTransient(is);
        }
        if (is == null) throw new CommandWarn("Item display could not be spawned!");
        player.sendMessage(textOfChildren(text("Placed ", YELLOW), mytems,
                                          text(" at" + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ(), GRAY))
                           .hoverEvent(showText(text(is.getUniqueId().toString(), GRAY)))
                           .clickEvent(suggestCommand("/kill " + is.getUniqueId().toString()))
                           .insertion(is.getUniqueId().toString()));
        return true;
    }

    public CommandNode registerItemCommand(Mytems key) {
        return itemNode.addChild(key.id);
    }

    private static String dToString(double d) {
        String result = "" + d;
        return result.endsWith(".0")
            ? "" + ((int) d)
            : result;
    }

    private static String bbToString(BoundingBox bb) {
        return "(" + dToString(bb.getMinX()) + " " + dToString(bb.getMinY()) + " " + dToString(bb.getMinZ())
            + "|" + dToString(bb.getMaxX()) + " " + dToString(bb.getMaxY()) + " " + dToString(bb.getMaxZ()) + ")";
    }

    protected void collisionTest(Player player) {
        BoundingBox bb = player.getBoundingBox();
        List<Block> blocks = Collision.getCollidingBlocks(player.getWorld(), bb);
        if (blocks.isEmpty()) {
            player.sendMessage(text("Nothing collides with your BB: " + bbToString(bb), GREEN));
            return;
        }
        player.sendMessage(text(blocks.size() + " collision(s) with your BB: " + bbToString(bb), YELLOW));
        for (Block block : blocks) {
            List<Component> line = new ArrayList<>();
            line.add(text(block.getX() + " " + block.getY() + " " + block.getY(), YELLOW));
            line.add(text("(" + block.getType().getKey().getKey() + ")", GRAY));
            for (BoundingBox box : block.getCollisionShape().getBoundingBoxes()) {
                if (box.overlaps(bb)) line.add(text("" + bbToString(box), RED));
            }
            player.sendMessage(join(separator(space()), line));
        }
    }

    private boolean worldEditHighlight(Player player, String[] args) {
        if (args.length > 1) return false;
        final var outline = new CuboidOutline(player.getWorld(), Cuboid.requireSelectionOf(player));
        final Color glowColor = args.length >= 1
            ? Color.fromRGB(TextColor.fromHexString(args[0]).value())
            : null;
        outline.showOnlyTo(player);
        outline.spawn();
        outline.removeLater(100L);
        if (glowColor != null) {
            outline.glow(glowColor);
        }
        player.sendMessage(text("Outlining " + outline.getCuboid(), YELLOW));
        return true;
    }

    private boolean upgradableAddXp(Player player, String[] args) {
        if (args.length != 1) return false;
        final int xp = CommandArgCompleter.requireInt(args[0], i -> i > 0);
        final ItemStack item = player.getInventory().getItemInMainHand();
        final Mytems mytems = Mytems.forItem(item);
        if (mytems == null) {
            throw new CommandWarn("There is no Mytem in your hand");
        }
        final MytemTag tag = mytems.getMytem().serializeTag(item);
        if (!(tag instanceof UpgradableItemTag upgradable)) {
            throw new CommandWarn(mytems + " is not an upgradable Mytem!");
        }
        if (!upgradable.addXp(xp)) {
            throw new CommandWarn("Adding xp was rejected");
        }
        upgradable.store(item);
        player.sendMessage(textOfChildren(text("Added " + xp + " xp to ", YELLOW),
                                          ItemKinds.chatDescription(item)));
        return true;
    }

    private boolean upgradableSetLevel(Player player, String[] args) {
        if (args.length != 1) return false;
        final int level = CommandArgCompleter.requireInt(args[0], i -> i >= 0);
        final ItemStack item = player.getInventory().getItemInMainHand();
        final Mytems mytems = Mytems.forItem(item);
        if (mytems == null) {
            throw new CommandWarn("There is no Mytem in your hand");
        }
        final MytemTag tag = mytems.getMytem().serializeTag(item);
        if (!(tag instanceof UpgradableItemTag upgradable)) {
            throw new CommandWarn(mytems + " is not an upgradable Mytem!");
        }
        upgradable.setLevel(level);
        upgradable.store(item);
        player.sendMessage(textOfChildren(text("Set level of ", YELLOW),
                                          ItemKinds.chatDescription(item),
                                          text(" to " + level, YELLOW)));
        return true;
    }
}
