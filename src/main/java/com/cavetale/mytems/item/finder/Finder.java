package com.cavetale.mytems.item.finder;

import com.cavetale.core.event.structure.PlayerDiscoverStructureEvent;
import com.cavetale.core.structure.Structure;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Finder implements Mytem, Listener {
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
        Bukkit.getPluginManager().registerEvents(this, plugin());
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

    /**
     * Attempt to give finder xp for the given structure to a Finder
     * item.  This will check if the item is a Finder and can receive
     * xp for the structure in question.
     *
     * @return true if xp were given, false otherwise.
     */
    private boolean giveFinderXp(Player player, Structure structure, FoundType foundType, ItemStack item) {
        if (!key.isItem(item)) return false;
        final FinderTag tag = serializeTag(item);
        if (!tag.getFindableStructures().contains(foundType)) {
            return false;
        }
        if (tag.addXpAndNotify(player, foundType.getXp())) {
            tag.store(item);
        }
        player.sendMessage(textOfChildren(key, text("You discovered this ", GRAY),
                                          foundType.getChatIcon(),
                                          text(foundType.getDisplayName(), foundType.getRequiredTier().getColor())));
        player.playSound(player.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1f, 2f);
        plugin().getLogger().info("[" + key + "] " + player.getName() + " discovered " + foundType + " " + structure.getWorldName() + "/" + structure.getInternalId());
        return true;
    }

    /**
     * When a player discovers a structure. try to xp to any Finder
     * item in their inventory, preferrably in their hands.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onPlayerDiscoverStructure(PlayerDiscoverStructureEvent event) {
        final Structure structure = event.getStructure();
        final FoundType foundType = FoundType.of(structure.getKey());
        if (foundType == null || foundType.isDisabled()) {
            return;
        }
        final Player player = event.getPlayer();
        if (giveFinderXp(player, structure, foundType, player.getInventory().getItemInMainHand())) {
            return;
        }
        if (giveFinderXp(player, structure, foundType, player.getInventory().getItemInOffHand())) {
            return;
        }
        for (int i = 0; i < player.getInventory().getSize(); i += 1) {
            if (giveFinderXp(player, structure, foundType, player.getInventory().getItem(i))) {
                return;
            }
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
