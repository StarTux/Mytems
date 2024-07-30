package com.cavetale.mytems.item.finder;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.structure.Structure;
import com.cavetale.core.structure.Structures;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemMenu;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Value
public final class FoundMenu {
    private static final int SIZE = 9 * 6;
    private final Player player;
    private final ItemStack item;
    private final FinderTag tag;

    public void open() {
        final FinderTier tier = tag.getUpgradableItemTier();
        final Mytems mytems = tier.getMytems();
        final int range = tag.getRange();
        final Location location = player.getLocation();
        final World world = location.getWorld();
        final Vec3i playerVector = Vec3i.of(location);
        final Cuboid cuboid = new Cuboid(playerVector.x - range,
                                         world.getMinHeight(),
                                         playerVector.z - range,
                                         playerVector.x + range,
                                         world.getMaxHeight(),
                                         playerVector.z + range);
        final List<Found> foundList = find(world, cuboid, playerVector);
        Gui gui = new Gui()
            .size(SIZE)
            .title(textOfChildren(mytems, mytems.getMytem().getDisplayName()))
            .layer(GuiOverlay.BLANK, tier.getColor())
            .layer(GuiOverlay.TITLE_BAR, GRAY);
        for (int i = 0; i < foundList.size() && i < SIZE; i += 1) {
            final int slot = i;
            final Found found = foundList.get(i);
            ItemStack icon = found.type().getIcon();
            icon.editMeta(meta -> {
                    tooltip(meta, List.of(text(found.type().getDisplayName(), found.type().getRequiredTier().getColor()),
                                          textOfChildren(text(tiny("distance ca"), GRAY), text(" " + found.distance() + " blocks", WHITE)),
                                          (found.structure().isDiscovered()
                                           ? textOfChildren(Mytems.CROSSED_CHECKBOX, text(" already discovered", RED))
                                           : textOfChildren(Mytems.CHECKED_CHECKBOX, text(" undiscovered", GREEN))),
                                          textOfChildren(Mytems.MOUSE_LEFT, text(" Locate", GRAY))));
                    meta.addItemFlags(ItemFlag.values());
                });
            gui.setItem(slot, icon, click -> {
                    if (!click.isLeftClick()) return;
                    item.editMeta(CompassMeta.class, meta -> {
                            Location lodestone = found.vector().toCenterLocation(world);
                            lodestone.setY(location.getY());
                            meta.setLodestone(lodestone);
                            meta.setLodestoneTracked(false);
                        });
                    final Component message = textOfChildren(mytems, text(tiny(" points toward ") + found.type().getDisplayName(), GRAY));
                    player.sendActionBar(message);
                    player.sendMessage(message);
                    player.playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1f, 2f);
                });
            gui.highlight(slot, found.type().getRequiredTier().getColor());
        }
        if (foundList.isEmpty()) {
            gui.setItem(4, 3, Mytems.NO.createIcon(List.of(text("Nothing found!", color(0xFF0000)))), null);
            player.playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1.0f, 0.5f);
        } else {
            player.playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1.0f, 1.25f);
        }
        gui.setItem(Gui.OUTSIDE, null, click -> new UpgradableItemMenu(player, item, tag).open());
        gui.open(player);
    }

    public List<Found> find(World world, Cuboid cuboid, Vec3i center) {
        final List<Found> result = new ArrayList<>();
        final List<FoundType> findable = tag.getFindableStructures();
        for (Structure structure : Structures.get().getStructuresWithin(world, cuboid)) {
            FoundType foundType = FoundType.of(structure.getKey());
            if (foundType == null) {
                plugin().getLogger().warning("[Finder] Unknown structure: " + structure.getKey());
                continue;
            }
            if (!findable.contains(foundType)) continue;
            int dist = structure.getBoundingBox().getCenter().maxHorizontalDistance(center);
            Found found = new Found(structure, foundType, structure.getBoundingBox().getCenter(), dist);
            if (result.contains(found)) continue;
            result.add(found);
        }
        Collections.sort(result, Comparator.comparing(Found::distance));
        return result;
    }
}
