package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import com.cavetale.mytems.MytemsPlugin;
import com.destroystokyo.paper.event.entity.ThrownEggHatchEvent;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.text;

@Getter @RequiredArgsConstructor
public final class MobCatcher implements Mytem, Listener {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;
    private static ConfigurationSection config;
    @Setter private Delegate delegate;

    @FunctionalInterface
    public interface Delegate {
        void onPlayerRightClick(MobCatcher mobCatcher, PlayerInteractEvent event, Player player, ItemStack item);
    }

    @Override
    public void enable() {
        if (config == null) {
            InputStream in = MytemsPlugin.getInstance().getResource("pocketmob.yml");
            InputStreamReader reader = new InputStreamReader(in);
            config = YamlConfiguration.loadConfiguration(reader);
        }
        String name = key == Mytems.FISH_CATCHER
            ? "Aquatic Catcher"
            : toCamelCase(" ", key);
        displayName = text(name, PocketMobTag.COLOR_FG);
        prototype = new ItemStack(key.material).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.itemName(displayName);
        List<Component> lore = new ArrayList<>();
        for (String line : config.getString(key.id).split("\n")) {
            lore.add(text(line, PocketMobTag.COLOR_BG));
        }
        meta.lore(lore);
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
        if (delegate != null) delegate.onPlayerRightClick(this, event, player, item);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    public void onThrownEggHatch(ThrownEggHatchEvent event) {
        Egg egg = event.getEgg();
        ItemStack itemStack = egg.getItem();
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        if (mytems.category == MytemsCategory.MOB_CATCHERS || mytems.category == MytemsCategory.POCKET_MOB) {
            event.setHatching(false);
        }
    }
}
