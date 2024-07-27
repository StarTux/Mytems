package com.cavetale.mytems.item.finder;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.structure.Structure;
import com.cavetale.core.structure.Structures;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Finder implements Mytem {
    private final Mytems key;
    private final FinderTier tier;
    private ItemStack prototype;
    private Component displayName;

    public Finder(final Mytems key) {
        this.key = key;
        this.tier = requireNonNull(FinderTier.of(key));
    }

    @Override
    public void enable() {
        this.displayName = tier.getTitle();
        this.prototype = new ItemStack(key.material);
        tier.createTag().store(prototype);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public FinderTag serializeTag(ItemStack itemStack) {
        FinderTag tag = tier.createTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        FinderTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        onClick(player, item, event.getClickedBlock());
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        onClick(player, item, event.getClickedBlock());
    }

    private void onClick(Player player, ItemStack item, Block block) {
        final Structure structure = Structures.get().getStructureAt(block);
        if (structure == null || structure.isDiscovered()) {
            return;
        }
        final FoundType foundType = FoundType.of(structure.getKey());
        if (foundType == null || foundType.isDisabled()) {
            return;
        }
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            return;
        }
        final FinderTag tag = serializeTag(item);
        if (!tag.getFindableStructures().contains(foundType)) {
            return;
        }
        if (!tag.addXpAndNotify(player, foundType.getXp())) {
            return;
        }
        structure.setDiscovered(true);
        tag.store(item);
        player.sendMessage(textOfChildren(key, text(" You discovered this ", GRAY),
                                          foundType.getChatIcon(),
                                          text(foundType.getDisplayName(), foundType.getRequiredTier().getColor())));
        plugin().getLogger().info(player.getName() + " discovered " + foundType + " at " + structure.getBoundingBox().getCenter());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
