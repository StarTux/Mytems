package com.cavetale.mytems.item.finder;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.structure.Structure;
import com.cavetale.core.structure.Structures;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Text;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
public final class Finder implements Mytem {
    private final Mytems key;
    private final FinderType type;
    private ItemStack prototype;
    private Component displayName;
    private final Set<FoundType> findable = EnumSet.noneOf(FoundType.class);
    private static final Duration COOLDOWN = Duration.ofSeconds(5);

    public Finder(final Mytems key) {
        this.key = key;
        this.type = requireNonNull(FinderType.of(key));
        this.displayName = text(type.displayName, type.color);
    }

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> txt = new ArrayList<>();
                txt.add(displayName);
                for (FoundType foundType : FoundType.values()) {
                    if (foundType.isDisabled()) continue;
                    if (foundType.type.ordinal() <= this.type.ordinal()) {
                        findable.add(foundType);
                    }
                }
                txt.addAll(Text.wrapLore(tiny(type.description), c -> c.color(GRAY)));
                txt.add(empty());
                txt.add(textOfChildren(text(tiny("range "), GRAY), text(type.range + " blocks", type.color)));
                txt.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Find Structures", GRAY)));
                tooltip(meta, txt);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        event.setUseInteractedBlock(Event.Result.DENY);
        final Session session = sessionOf(player);
        final long cooldown = session.getCooldown(Mytems.STRUCTURE_FINDER).toSeconds();
        final Location location = player.getLocation();
        if (cooldown > 0) {
            player.sendActionBar(text("Cooldown " + cooldown + "s", RED));
            player.playSound(location, Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1.0f, 1.0f);
            return;
        }
        session.cooldown(Mytems.STRUCTURE_FINDER).duration(COOLDOWN).icon(key);
        final World world = player.getWorld();
        final Cuboid cuboid = new Cuboid(location.getBlockX() - type.range,
                                         world.getMinHeight(),
                                         location.getBlockZ() - type.range,
                                         location.getBlockX() + type.range,
                                         world.getMaxHeight(),
                                         location.getBlockZ() + type.range);
        final Vec3i playerVector = Vec3i.of(location);
        final List<Found> foundList = new ArrayList<>();
        for (Structure structure : Structures.get().getStructuresWithin(world, cuboid)) {
            FoundType foundType = FoundType.of(structure.getKey());
            if (foundType == null) {
                plugin().getLogger().warning("[Finder] Unknown structure: " + structure.getKey());
                continue;
            }
            if (foundType.isDisabled()) continue;
            if (!findable.contains(foundType)) continue;
            int dist = structure.getBoundingBox().getCenter().maxHorizontalDistance(playerVector);
            Found found = new Found(foundType, structure.getBoundingBox().getCenter(), dist);
            if (foundList.contains(found)) continue;
            foundList.add(found);
        }
        Collections.sort(foundList, (a, b) -> Integer.compare(a.distance(), b.distance()));
        final int size = 9 * (1 + Math.min(5, (foundList.size() - 1) / 9 + 1));
        Gui gui = new Gui().size(size);
        GuiOverlay.Builder builder = GuiOverlay.BLANK.builder(size, type.color)
            .layer(GuiOverlay.TITLE_BAR, GRAY)
            .title(displayName);
        List<Component> info = new ArrayList<>();
        info.add(text(type.displayName + " can locate:", type.color));
        for (FoundType foundType : FoundType.values()) {
            if (foundType.isDisabled()) continue;
            if (findable.contains(foundType)) {
                info.add(textOfChildren(key, space(), text(foundType.displayName, foundType.type.color)));
            } else {
                info.add(textOfChildren(foundType.type.mytems, space(), text(foundType.displayName, foundType.type.color, STRIKETHROUGH)));
            }
        }
        gui.setItem(4, key.createIcon(info));
        int nextSlot = 0;
        for (Found found : foundList) {
            final int slot = foundList.size() < 9
                ? 9 + 4 - (foundList.size() / 2) + (nextSlot++)
                : 9 + (nextSlot++);
            ItemStack icon = found.type().iconSupplier.get();
            icon.editMeta(meta -> {
                    tooltip(meta, List.of(text(found.type().displayName, found.type().type.color),
                                             textOfChildren(text(tiny("distance ca"), GRAY), text(" " + found.distance() + " blocks", WHITE)),
                                             textOfChildren(Mytems.MOUSE_LEFT, text(" Locate", GRAY))));
                    meta.addItemFlags(ItemFlag.values());
                });
            gui.setItem(slot, icon, click -> {
                    if (!click.isLeftClick()) return;
                    item.editMeta(m -> {
                            if (m instanceof CompassMeta meta) {
                                Location lodestone = found.vector().toCenterLocation(world);
                                lodestone.setY(location.getY());
                                meta.setLodestone(lodestone);
                                meta.setLodestoneTracked(false);
                                meta.displayName(text(found.type().displayName, type.color));
                            }
                        });
                    gui.setItem(4, tooltip(item.clone(), info));
                    player.sendActionBar(textOfChildren(displayName, text(" points toward " + found.type().displayName)));
                    player.playSound(location, Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
                });
            builder.highlightSlot(slot, found.type().type.color);
        }
        if (foundList.isEmpty()) {
            gui.setItem(9 + 4, Mytems.NO.createIcon(List.of(text("Nothing found!", color(0xFF0000)))));
        }
        gui.title(builder.build());
        gui.open(player);
        player.playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1.0f, 1.25f);
    }
}
