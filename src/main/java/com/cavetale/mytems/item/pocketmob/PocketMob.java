package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.MytemsTag;
import com.cavetale.mytems.util.Json;
import com.cavetale.mytems.util.Text;
import io.papermc.paper.event.block.BlockPreDispenseEvent;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public final class PocketMob implements Mytem, Listener {
    private final Mytems key;
    private final PocketMobType pocketMobType;
    private final EntityType entityType;
    private Component displayName;
    private ItemStack prototype;
    @Setter private Delegate delegate;

    public interface Delegate {
        void onPlayerRightClick(PocketMob pocketMob, PlayerInteractEvent event, Player player, ItemStack item);

        void onBlockPreDispense(PocketMob pocketMob, BlockPreDispenseEvent event);
    }

    public PocketMob(final Mytems key) {
        this.key = key;
        this.pocketMobType = Objects.requireNonNull(PocketMobType.of(key),
                                                    "PocketMob EntityType undefined: " + key);
        this.entityType = pocketMobType.entityType;
    }

    @Override
    public void enable() {
        displayName = Component.text(Text.toCamelCase(key, " ")).decoration(TextDecoration.ITALIC, false);
        prototype = new ItemStack(key.material).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
        Bukkit.getPluginManager().registerEvents(this, MytemsPlugin.getInstance());
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return MytemPersistenceFlag.DISPLAY_NAME.toSet();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (delegate != null) {
            delegate.onPlayerRightClick(this, event, player, item);
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    public String getEntityTypeName() {
        return Text.toCamelCase(entityType, " ");
    }

    @Override
    public String serializeTag(ItemStack itemStack) {
        PocketMobTag tag = new PocketMobTag();
        tag.load(itemStack, this);
        return tag.isEmpty() ? null : Json.serialize(tag);
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        PocketMobTag tag = Json.deserialize(serialized, PocketMobTag.class);
        tag.store(itemStack, this);
        return itemStack;
    }

    @Override
    public ItemStack deserializeTag(String serialized, Player player) {
        return deserializeTag(serialized);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    void onBlockPreDispense(BlockPreDispenseEvent event) {
        ItemStack itemStack = event.getItemStack();
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null || !MytemsTag.POCKET_MOB.isTagged(mytems)) return;
        event.setCancelled(true);
        if (delegate != null) delegate.onBlockPreDispense(this, event);
    }
}
