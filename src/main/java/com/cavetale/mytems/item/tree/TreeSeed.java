package com.cavetale.mytems.item.tree;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class TreeSeed implements Mytem {
    private static final TextColor TREE_GREEN = color(0x288b22);
    private final Mytems key;
    private final CustomTreeType customTreeType;
    private ItemStack prototype;
    private Component displayName;
    @Setter private Consumer<PlayerInteractEvent> rightClickHandler;

    public TreeSeed(final Mytems mytems) {
        this.key = mytems;
        this.customTreeType = CustomTreeType.ofSeed(mytems);
    }

    @Override
    public void enable() {
        this.displayName = text(Text.toCamelCase(key, " "), TREE_GREEN);
        prototype = new ItemStack(key.material);
        List<Component> text = new ArrayList<>();
        text.add(displayName);
        String treeName = Text.toCamelCase(customTreeType, " ");
        String description = "Plant on a patch of tilled soil"
            + " to grow into a magnificent " + treeName + " tree.";
        description = Unicode.subscript(description.toLowerCase());
        text.addAll(Text.wrapLore(description,
                                  s -> s.color(GRAY)));
        prototype.editMeta(meta -> {
                tooltip(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (rightClickHandler != null) {
            rightClickHandler.accept(event);
        }
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
