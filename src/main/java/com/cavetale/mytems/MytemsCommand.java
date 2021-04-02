package com.cavetale.mytems;

import com.cavetale.core.command.CommandContext;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Text;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class MytemsCommand implements TabExecutor {
    private final MytemsPlugin plugin;
    private CommandNode rootNode;

    public void enable() {
        rootNode = new CommandNode("mytems");
        rootNode.addChild("list").denyTabCompletion()
            .description("Show some info on all mytems")
            .senderCaller(this::list);
        rootNode.addChild("give").arguments("<player> <mytem> [amount]")
            .description("Give an item to a player")
            .senderCaller(this::give)
            .completer(this::giveComplete);
        rootNode.addChild("base64").denyTabCompletion()
            .description("Serialize the item in your hand to base64")
            .playerCaller(this::base64);
        rootNode.addChild("equipment")
            .description("Display player equipment")
            .senderCaller(this::equipment);
        rootNode.addChild("session")
            .description("Display session info")
            .senderCaller(this::session);
        rootNode.addChild("serialize").denyTabCompletion()
            .description("Serialize the mytem in your hand")
            .playerCaller(this::serialize);
        rootNode.addChild("fixall").denyTabCompletion()
            .description("Fix all player inventories")
            .senderCaller(this::fixall);
        plugin.getCommand("mytems").setExecutor(this);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return rootNode.call(sender, command, alias, args);
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return rootNode.complete(sender, command, alias, args);
    }

    boolean list(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        for (Mytems mytems : Mytems.values()) {
            Mytem mytem = mytems.getMytem();
            sender.sendMessage(Text.builder(mytems.ordinal() + ") " + mytems.id + " ")
                               .append(mytem.getDisplayName()).create());
        }
        return true;
    }

    boolean give(CommandSender sender, String[] args) {
        if (args.length != 2 && args.length != 3) return false;
        String targetArg = args[0];
        String mytemArg = args[1];
        String amountArg = args.length >= 3 ? args[2] : null;
        Player target = Bukkit.getPlayer(targetArg);
        if (target == null) throw new CommandWarn("Player not found: " + targetArg);
        Mytems mytems = Mytems.forId(mytemArg);
        if (mytems == null) throw new CommandWarn("Mytem not found: " + mytemArg);
        int amount = 1;
        if (amountArg != null) {
            try {
                amount = Integer.parseInt(amountArg);
            } catch (NumberFormatException nfe) {
                amount = -1;
            }
        }
        if (amount < 1) throw new CommandWarn("Invalid amount: " + amountArg);
        Mytem mytem = plugin.getMytem(mytems);
        ItemStack item = mytem.getItem();
        item.setAmount(amount);
        int retain = 0;
        for (ItemStack drop : target.getInventory().addItem(item).values()) {
            retain += drop.getAmount();
        }
        if (retain >= amount) {
            throw new CommandWarn("Could not add item to inventory: " + mytems + ", " + target.getName());
        }
        ComponentBuilder cb = new ComponentBuilder("").color(ChatColor.YELLOW);
        cb.append("" + (amount - retain)).color(ChatColor.WHITE);
        cb.append("x").color(ChatColor.DARK_GRAY);
        cb.append("").reset();
        cb.append(mytem.getDisplayName());
        cb.append(" given to " + target.getName()).color(ChatColor.YELLOW);
        if (sender instanceof Player) {
            sender.sendMessage(cb.create());
        } else {
            sender.sendMessage(BaseComponent.toLegacyText(cb.create()));
        }
        return true;
    }

    boolean base64(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getAmount() <= 0) {
            throw new CommandWarn("There's no item in your main hand!");
        }
        byte[] bytes = itemStack.serializeAsBytes();
        String string = Base64.getEncoder().encodeToString(bytes);
        ComponentBuilder cb = new ComponentBuilder("Base64: ")
            .color(ChatColor.GRAY)
            .append(string)
            .color(ChatColor.WHITE)
            .insertion(string);
        player.sendMessage(cb.create());
        plugin.getLogger().info("Serialize " + itemStack.getType() + ": " + string);
        return true;
    }

    List<String> giveComplete(CommandContext context, CommandNode node, String[] args) {
        if (args.length <= 1) return null; // <player>
        if (args.length > 2) return Collections.emptyList();
        String arg = args[args.length - 1];
        return Stream.of(Mytems.values())
            .map(m -> m.id)
            .filter(s -> s.contains(arg))
            .collect(Collectors.toList());
    }

    boolean equipment(CommandSender sender, String[] args) {
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

    boolean session(CommandSender sender, String[] args) {
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

    boolean serialize(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getAmount() == 0) {
            throw new CommandWarn("No item in your hand!");
        }
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) {
            throw new CommandWarn("No Mytem in your hand!");
        }
        String serialized = mytems.serializeItem(itemStack);
        player.sendMessage(ChatColor.YELLOW + "Serialized item in hand: " + ChatColor.RESET + serialized);
        return true;
    }

    boolean fixall(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        plugin.fixAllPlayerInventoriesLater();
        sender.sendMessage(ChatColor.YELLOW + "Fixing all player inventories");
        return true;
    }
}
