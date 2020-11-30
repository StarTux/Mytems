package com.cavetale.mytems;

import com.cavetale.core.command.CommandContext;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
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
        rootNode.addChild("give").arguments("<player> <mytem>")
            .description("Give an item to a player")
            .senderCaller(this::give)
            .completer(this::giveComplete);
        rootNode.addChild("serialize").denyTabCompletion()
            .description("Serialize the item in your hand")
            .playerCaller(this::serialize);
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

    boolean give(CommandSender sender, String[] args) {
        if (args.length != 2) return false;
        String targetArg = args[0];
        String mytemArg = args[1];
        Player target = Bukkit.getPlayer(targetArg);
        if (target == null) throw new CommandWarn("Player not found: " + targetArg);
        Mytems mytems = Mytems.forId(mytemArg);
        if (mytems == null) throw new CommandWarn("Mytem not found: " + mytemArg);
        Mytem mytem = plugin.getMytem(mytems);
        ItemStack item = mytem.getItem();
        boolean success = target.getInventory().addItem(item).isEmpty();
        if (!success) {
            throw new CommandWarn("Could not add item to inventory: " + mytems + ", " + target.getName());
        }
        ComponentBuilder cb = new ComponentBuilder("").color(ChatColor.YELLOW);
        cb.append(mytem.getDisplayName());
        cb.append(" given to " + target.getName()).color(ChatColor.YELLOW);
        if (sender instanceof Player) {
            sender.sendMessage(cb.create());
        } else {
            sender.sendMessage(BaseComponent.toLegacyText(cb.create()));
        }
        return true;
    }

    boolean serialize(Player player, String[] args) {
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
}
