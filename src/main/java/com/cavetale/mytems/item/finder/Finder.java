package com.cavetale.mytems.item.finder;

import com.cavetale.core.structure.Structure;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
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
        tier.createTag().store(key, prototype);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
            });
        FinderListener.enableOnce();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public FinderTag serializeTag(ItemStack itemStack) {
        FinderTag tag = tier.createTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        FinderTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null && !tag.isEmpty()) {
            tag.store(key, itemStack);
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
    protected boolean giveFinderXp(Player player, Structure structure, FoundType foundType, ItemStack item) {
        if (!key.isItem(item)) return false;
        final FinderTag tag = serializeTag(item);
        if (!tag.getFindableStructures().contains(foundType)) {
            return false;
        }
        if (tag.addXpAndNotify(player, foundType.getXp())) {
            tag.store(key, item);
        }
        player.sendMessage(textOfChildren(key, text("You discovered this ", GRAY),
                                          foundType.getChatIcon(),
                                          text(foundType.getDisplayName(), foundType.getRequiredTier().getColor())));
        player.playSound(player.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1f, 2f);
        plugin().getLogger().info("[" + key + "] " + player.getName() + " discovered " + foundType + " " + structure.getWorldName() + "/" + structure.getInternalId());
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
