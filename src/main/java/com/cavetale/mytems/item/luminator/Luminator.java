package com.cavetale.mytems.item.luminator;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Luminator implements Mytem {
    private final Mytems key;
    private final Component displayName;
    private final ItemStack prototype;

    public Luminator(final Mytems key) {
        this.key = key;
        this.displayName = text("Luminator", YELLOW);
        this.prototype = new ItemStack(key.material);
    }

    @Override
    public void enable() {
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName,
                                         text("Draw illumination from", GRAY),
                                         text("the world and place", GRAY),
                                         text("invisible light sources", GRAY),
                                         empty(),
                                         join(noSeparators(), Mytems.MOUSE_LEFT, text(" Choose light level", GRAY)),
                                         join(noSeparators(), Mytems.MOUSE_RIGHT, text(" Draw/place light", GRAY))));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public LuminatorTag serializeTag(ItemStack itemStack) {
        LuminatorTag tag = new LuminatorTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        LuminatorTag tag = Json.deserialize(serialized, LuminatorTag.class);
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        final Block block = event.getClickedBlock();
        if (draw(player, block, item)) {
            return;
        }
        LuminatorTag tag = new LuminatorTag();
        tag.load(item);
        if (tag.light == 0) return;
    }

    private boolean draw(Player player, Block block, ItemStack item) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return false;
        int drawableLight = getDrawableLight(block);
        if (drawableLight == 0) return false;
        return false;
    }

    private int getDrawableLight(Block block) {
        final BlockData data = block.getBlockData();
        if (data instanceof Campfire campfire) {
            return campfire.isLit() ? 15 : 0;
        }
        return 0;
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
    }
}
