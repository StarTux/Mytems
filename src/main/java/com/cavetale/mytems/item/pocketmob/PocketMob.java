package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Json;
import com.cavetale.mytems.util.Text;
import io.papermc.paper.event.block.BlockPreDispenseEvent;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.cavetale.mytems.util.Items.tooltip;

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
        displayName = Component.text(Text.toCamelCase(key, " "));
        prototype = new ItemStack(key.material).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        tooltip(meta, List.of(displayName));
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
        Bukkit.getPluginManager().registerEvents(this, MytemsPlugin.getInstance());
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
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
        if (event.getRightClicked() instanceof ItemFrame) return;
        event.setCancelled(true);
    }

    @Override
    public PocketMobTag serializeTag(ItemStack itemStack) {
        PocketMobTag tag = new PocketMobTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        PocketMobTag tag = Json.deserialize(serialized, PocketMobTag.class, PocketMobTag::new);
        tag.store(key, itemStack);
        return itemStack;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    void onBlockPreDispense(BlockPreDispenseEvent event) {
        ItemStack itemStack = event.getItemStack();
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null || mytems.category != MytemsCategory.POCKET_MOB) return;
        event.setCancelled(true);
        if (delegate != null) delegate.onBlockPreDispense(this, event);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }
}
