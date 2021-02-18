package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.item.ItemMarker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public final class WeddingRing implements Mytem {
    private final MytemsPlugin plugin;
    public static final Mytems KEY = Mytems.WEDDING_RING;
    private final String serialized = "H4sIAAAAAAAAAE2Pu06DUByH/71FpLMOToYXEKGtaRNjeguFFLAUaOliTstpgZ4DDQUrNe4uxsEX8BF8HFcnFzdfQLo5fr/L8LEAJaj2UIJsHG/9KARgzxgo+i6cUD/Eixgtk9aGoAzHdx5GLgulBK1YOHL97SFmoKwhiuH0kUvwQ8K1OCkiLg7PDT9ccU/AAjtep4TouxDHOdzG0QbHiY+3x8AcHmmMtywAFBio2IikGD5wpvCzqce7U4UsMrmRsznmiS4Hmys5tLN5V27INO8H7cYwa/7b1hM0qRNHVLxZOErn1OaHokHwwLhcUOve2bdFXVIFXXKEWbAWtGC91/Yd4phq3aGWqAZrXpv09465EvSeR1TJ2qmBQbTJSFBNqzajo5pmGtQJFF/vdajjK83llL8GqEJRdnOJMvP2Ln199n9e45ff54ub79wLKt0oDZMC/AFVGpZFbAEAAA==";
    private final String description = ""
        + "Use this on your partner to get married."
        + "\n\n"
        + "Both players need to use the wedding ring and have 5 hearts in /friends."
        + "\n\n"
        + "On Valentine's Day alone, marriage is possible with fewer than 5 hearts.";
    @Getter private BaseComponent[] displayName;
    private final ChatColor pink = ChatColor.of("#FF69B3");
    private ItemStack prototype;

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        prototype = Items.deserialize(serialized);
        ItemMeta meta = prototype.getItemMeta();
        displayName = Text.builder("Wedding Ring").color(pink).italic(false).create();
        meta.setDisplayNameComponent(displayName);
        meta.setLoreComponents(Text.toBaseComponents(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH),
                                                     cb -> cb.color(pink).italic(false)));
        prototype.setItemMeta(meta);
        ItemMarker.setId(prototype, KEY.id);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }
}
