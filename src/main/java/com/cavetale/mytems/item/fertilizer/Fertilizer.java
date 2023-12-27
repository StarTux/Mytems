package com.cavetale.mytems.item.fertilizer;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Items;
import com.destroystokyo.paper.MaterialSetTag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class Fertilizer implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private List<FertilizerGrowth> growList = new ArrayList<>();
    protected static MaterialSetTag flowerGrowables;

    @Override
    public void enable() {
        this.displayName = text("Fertilizer", GREEN);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(new Component[] {
                            displayName,
                            join(noSeparators(),
                                 Mytems.SHIFT_KEY.component,
                                 Mytems.MOUSE_RIGHT.component,
                                 text(" Open menu", GRAY)),
                            join(noSeparators(),
                                 Mytems.MOUSE_RIGHT.component,
                                 text(" Fertilize", GRAY)),
                        }));
                key.markItemMeta(meta);
            });
        flowerGrowables = new MaterialSetTag(MytemsPlugin.namespacedKey("fertilizer_flower_growables"),
                                             Material.DIRT,
                                             Material.GRASS_BLOCK,
                                             Material.PODZOL,
                                             Material.COARSE_DIRT);
        growList.add(new FertilizerFlower(Material.SHORT_GRASS));
        growList.add(new FertilizerBisectedFlower(Material.TALL_GRASS));
        growList.add(new FertilizerFlower(Material.FERN));
        growList.add(new FertilizerBisectedFlower(Material.LARGE_FERN));
        for (Material mat : Tag.SMALL_FLOWERS.getValues()) {
            growList.add(new FertilizerFlower(mat));
        }
        for (Material mat : Tag.TALL_FLOWERS.getValues()) {
            growList.add(new FertilizerBisectedFlower(mat));
        }
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (player.isSneaking()) {
            openGui(player);
        } else if (event.hasBlock()) {
            if (fertilize(player, event.getClickedBlock()) && player.getGameMode() != GameMode.CREATIVE) {
                item.subtract(1);
            }
        }
    }

    private void openGui(Player player) {
        PlayerData playerData = playerData(player);
        final int size = 6 * 9;
        GuiOverlay.Builder builder = GuiOverlay.BLANK.builder(size, DARK_GREEN)
            .title(displayName);
        Gui gui = new Gui().size(size);
        for (int i = 0; i < growList.size(); i += 1) {
            FertilizerGrowth growth = growList.get(i);
            boolean value = playerData.grow[i];
            if (value) builder.highlightSlot(i, YELLOW);
            final int index = i;
            gui.setItem(i, growth.getIcon(), click -> {
                    if (!click.isLeftClick()) return;
                    playerData.grow[index] = !value;
                    openGui(player);
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                });
        }
        gui.title(builder.build());
        gui.open(player);
    }

    private boolean fertilize(Player player, Block clickedBlock) {
        if (clickedBlock.getType() != Material.GRASS_BLOCK) return false;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, clickedBlock)) return false;
        PlayerData playerData = playerData(player);
        List<FertilizerGrowth> selectedGrowList = new ArrayList<>(growList.size());
        for (int i = 0; i < growList.size(); i += 1) {
            if (playerData.grow[i]) selectedGrowList.add(growList.get(i));
        }
        if (selectedGrowList.isEmpty()) return false;
        Collections.shuffle(selectedGrowList);
        final int radius = 5;
        final int radius2 = radius * radius;
        int growIndex = 0;
        for (int dy = -radius; dy <= radius; dy += 1) {
            for (int dz = -radius; dz <= radius; dz += 1) {
                for (int dx = -radius; dx <= radius; dx += 1) {
                    if (dx * dx + dy * dy + dz * dz > radius2) continue;
                    if (ThreadLocalRandom.current().nextInt(4) != 0) continue;
                    Block block = clickedBlock.getRelative(dx, dy, dz);
                    FertilizerGrowth growth = selectedGrowList.get(growIndex);
                    if (growth.canGrow(player, block)) {
                        growth.grow(player, block);
                        growIndex += 1;
                        if (growIndex >= selectedGrowList.size()) growIndex = 0;
                    }
                }
            }
        }
        return true;
    }

    private PlayerData playerData(Player player) {
        return MytemsPlugin.getInstance().getSessions().of(player).getFavorites().getOrSet(PlayerData.class, PlayerData::new);
    }

    private final class PlayerData {
        final boolean[] grow = new boolean[growList.size()];

    }
}
