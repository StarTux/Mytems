package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class WeddingRing implements Mytem {
    @Getter private final Mytems key;
    private final String serialized = "H4sIAAAAAAAAAE2Pu06DUByH/71FpLMOToYXEKGtaRNjeguFFLAUaOliTstpgZ4DDQUrNe4uxsEX8BF8HFcnFzdfQLo5fr/L8LEAJaj2UIJsHG/9KARgzxgo+i6cUD/Eixgtk9aGoAzHdx5GLgulBK1YOHL97SFmoKwhiuH0kUvwQ8K1OCkiLg7PDT9ccU/AAjtep4TouxDHOdzG0QbHiY+3x8AcHmmMtywAFBio2IikGD5wpvCzqce7U4UsMrmRsznmiS4Hmys5tLN5V27INO8H7cYwa/7b1hM0qRNHVLxZOErn1OaHokHwwLhcUOve2bdFXVIFXXKEWbAWtGC91/Yd4phq3aGWqAZrXpv09465EvSeR1TJ2qmBQbTJSFBNqzajo5pmGtQJFF/vdajjK83llL8GqEJRdnOJMvP2Ln199n9e45ff54ub79wLKt0oDZMC/AFVGpZFbAEAAA==";
    private final String description = ""
        + "Use this on your partner to get married."
        + "\n\n"
        + "Both players need to use the wedding ring and have 5 hearts in /friends."
        + "\n\n"
        + "On Valentine's Day alone, marriage is possible with fewer than 5 hearts.";
    @Getter private Component displayName;
    private final TextColor pink = TextColor.color(0xFF69B3);
    private ItemStack prototype;

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        displayName = Component.text("Wedding Ring", pink);
        prototype.editMeta(meta -> {
                meta.itemName(displayName);
                meta.lore(Text.wrapLore(description, cb -> cb.color(pink)));
                key.markItemMeta(meta);
            });
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
