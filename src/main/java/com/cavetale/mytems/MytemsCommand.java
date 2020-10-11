package com.cavetale.mytems;

import com.cavetale.core.command.CommandNode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class MytemsCommand implements TabExecutor {
    private final MytemsPlugin plugin;
    private CommandNode rootNode;

    public void enable() {
        rootNode = new CommandNode("mytems");
        rootNode.addChild("give")
            .description("Give yourself an item")
            .playerCaller(this::give);
        plugin.getCommand("mytems").setExecutor(this);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return rootNode.call(sender, command, alias, args);
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return null;
    }

    boolean give(Player player, String[] args) {
        player.getInventory().addItem(plugin.drAculaStaff.create());
        player.sendMessage("Item given: Dr Acula's Staff");
        return true;
    }
}
