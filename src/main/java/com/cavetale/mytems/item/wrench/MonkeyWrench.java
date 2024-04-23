package com.cavetale.mytems.item.wrench;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.core.font.VanillaPaintings;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Art;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class MonkeyWrench implements Mytem {
    private final Mytems key;
    private final Component displayName = text("Monkey Wrench", AQUA);
    private final ItemStack prototype;

    public MonkeyWrench(final Mytems key) {
        this.key = key;
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text("Modify blocks without", GRAY),
                                      text("picking them up.", GRAY),
                                      empty(),
                                      textOfChildren(Mytems.MOUSE_LEFT, text(" Switch mode", GRAY)),
                                      textOfChildren(Mytems.MOUSE_RIGHT, text(" Change block", GRAY))));
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
    }

    @Override
    public void enable() { }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        event.setCancelled(true);
        final Block block = event.getClickedBlock();
        assert block != null;
        BlockData blockData = block.getBlockData();
        List<WrenchEdit> editList = new ArrayList<>();
        for (WrenchEdit edit : WrenchEdit.values()) {
            if (edit.canEdit(player, block, blockData)) {
                editList.add(edit);
            }
        }
        if (editList.isEmpty()) {
            soundFail(player);
            player.sendActionBar(empty());
            return;
        }
        WrenchSession session = sessionOf(player).getFavorites().getOrSet(WrenchSession.class, WrenchSession::new);
        final int newIndex;
        if (session.edit == null || !editList.contains(session.edit)) {
            session.edit = editList.get(0);
            newIndex = 0;
        } else {
            final int index = editList.indexOf(session.edit);
            assert index >= 0;
            newIndex = index < editList.size() - 1
                ? index + 1
                : 0;
            session.edit = editList.get(newIndex);
        }
        player.sendActionBar(textOfChildren(text((newIndex + 1) + "/" + editList.size() + " ", GRAY),
                                            session.edit.getDisplayName()));
        soundSwitch(player);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        event.setCancelled(true);
        if (cancelOnce != null && player.getUniqueId().equals(cancelOnce)) {
            cancelOnce = null;
            return;
        }
        WrenchSession session = sessionOf(player).getFavorites().getOrSet(WrenchSession.class, WrenchSession::new);
        if (session.ticks == player.getTicksLived()) {
            return;
        }
        session.ticks = player.getTicksLived();
        final Block block = event.getClickedBlock();
        assert block != null;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            soundFail(player);
            player.sendActionBar(empty());
            return;
        }
        BlockData blockData = block.getBlockData();
        List<WrenchEdit> editList = new ArrayList<>();
        for (WrenchEdit edit : WrenchEdit.values()) {
            if (edit.canEdit(player, block, blockData)) {
                editList.add(edit);
            }
        }
        if (editList.isEmpty()) {
            soundFail(player);
            player.sendActionBar(empty());
            return;
        }
        if (session.edit == null || !editList.contains(session.edit)) {
            session.edit = editList.get(0);
        }
        Component result = session.edit.edit(player, block, blockData, event);
        if (result != null) {
            new PlayerChangeBlockEvent(player, block, blockData, item).callEvent();
            if (blockData instanceof Rail) {
                // Rails do not like changes if their old state is
                // invalid.  Or both; not sure.
                block.setType(Material.AIR, false);
            }
            block.setBlockData(blockData, false);
            player.sendActionBar(textOfChildren(session.edit.getDisplayName(), space(), result));
            soundUse(player);
        } else {
            player.sendActionBar(text("FAIL", DARK_RED));
            soundFail(player);
        }
    }

    private UUID cancelOnce;

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        if (player.isSneaking()) return;
        if (event.getRightClicked() instanceof ItemFrame itemFrame) {
            if (!PlayerEntityAbilityQuery.Action.INVENTORY.query(player, itemFrame)) return;
            event.setCancelled(true);
            boolean visible = !itemFrame.isVisible();
            itemFrame.setVisible(visible);
            soundUse(player);
            player.sendActionBar(visible
                                 ? textOfChildren(Mytems.EYES, text("Visible", BLUE))
                                 : textOfChildren(Mytems.BLIND_EYE, text("Invisible", BLUE)));
        } else if (event.getRightClicked() instanceof Painting painting) {
            if (!PlayerEntityAbilityQuery.Action.INVENTORY.query(player, painting)) return;
            event.setCancelled(true);
            // Painting interactions are still followed by the
            // PlayerInteractEvent even if cancelled.
            cancelOnce = player.getUniqueId();
            Art art = painting.getArt();
            List<Art> arts = List.of(Art.values());
            int index = arts.indexOf(art);
            for (int i = 1; i < arts.size(); i += 1) {
                index += 1;
                if (index >= arts.size()) index = 0;
                Art newArt = arts.get(index);
                if (painting.setArt(newArt)) {
                    soundUse(player);
                    player.sendActionBar(textOfChildren(VanillaPaintings.componentOf(newArt),
                                                        text(toCamelCase(" ", newArt), BLUE)));
                    return;
                }
            }
            soundFail(player);
        }
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    private void soundUse(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, SoundCategory.MASTER, 1.0f, 0.75f);
    }

    private void soundSwitch(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE, SoundCategory.MASTER, 1.0f, 0.60f);
    }

    private void soundFail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_HIT, SoundCategory.MASTER, 1.0f, 0.75f);
    }
}
