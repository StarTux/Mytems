package com.cavetale.mytems.item.finder;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.structure.Structure;
import com.cavetale.core.structure.Structures;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
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
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Finder implements Mytem {
    private final Mytems key;
    private final FinderType type;
    private ItemStack prototype;
    private Component displayName;
    private final Set<FoundType> findable = EnumSet.noneOf(FoundType.class);

    public Finder(final Mytems key) {
        this.key = key;
        this.type = requireNonNull(FinderType.of(key));
        this.displayName = type.displayName;
    }

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> txt = new ArrayList<>();
                txt.add(displayName);
                for (FoundType foundType : FoundType.values()) {
                    if (foundType.type.ordinal() <= this.type.ordinal()) {
                        findable.add(foundType);
                    }
                }
                txt.addAll(Text.wrapLore(tiny(type.description), c -> c.color(GRAY)));
                txt.add(empty());
                txt.add(textOfChildren(text(tiny("range "), GRAY), text(type.range + " blocks", type.color)));
                txt.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Find Structures", GRAY)));
                Items.text(meta, txt);
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
        Session session = sessionOf(player);
        long cooldown = session.getCooldownInTicks(key.id);
        Location location = player.getLocation();
        if (cooldown > 0) {
            long seconds = (cooldown - 1L) / 20L + 1;
            player.sendActionBar(text("Cooldown " + seconds + "s", RED));
            player.playSound(location, Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1.0f, 1.0f);
            return;
        }
        session.setCooldown(key.id, 20);
        final World world = player.getWorld();
        Cuboid cuboid = new Cuboid(location.getBlockX() - type.range,
                                   world.getMinHeight(),
                                   location.getBlockZ() - type.range,
                                   location.getBlockX() + type.range,
                                   world.getMaxHeight(),
                                   location.getBlockZ() + type.range);
        List<Found> foundList = new ArrayList<>();
        for (Structure structure : Structures.get().getStructuresWithin(world, cuboid)) {
            FoundType foundType = FoundType.of(structure.getKey());
            if (foundType == null) {
                plugin().getLogger().warning("[Finder] Unknown structure: " + structure.getKey());
                continue;
            }
            if (!findable.contains(foundType)) continue;
            Found found = new Found(foundType, structure);
            if (foundList.contains(found)) continue;
            foundList.add(found);
        }
        if (foundList.isEmpty()) {
            player.sendActionBar(text("No structures nearby", RED));
            player.playSound(location, Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1.0f, 1.0f);
            return;
        }
        Collections.sort(foundList, (a, b) -> {
                int t = Integer.compare(b.type().type.ordinal(),
                                        a.type().type.ordinal());
                if (t != 0) return t;
                return a.type().name().compareTo(b.type().name());
            });
        final int size = 9 * (1 + Math.min(5, (foundList.size() - 1) / 9 + 1));
        Gui gui = new Gui().size(size);
        GuiOverlay.Builder builder = GuiOverlay.BLANK.builder(size, type.color)
            .layer(GuiOverlay.TITLE_BAR, GRAY)
            .title(displayName);
        List<Component> info = new ArrayList<>();
        info.add(textOfChildren(type.displayName, text(" can find:")));
        for (FoundType foundType : findable) {
            info.add(text("\u2022 " + foundType.displayName, foundType.type.color));
        }
        gui.setItem(4, key.createIcon(info));
        int nextSlot = 0;
        for (Found found : foundList) {
            final int slot = 9 + (nextSlot++);
            ItemStack icon = found.type().iconSupplier.get();
            icon.editMeta(meta -> {
                    Items.text(meta, List.of(text(found.type().displayName),
                                             textOfChildren(Mytems.MOUSE_LEFT, text(" Locate", GRAY))));
                    meta.addItemFlags(ItemFlag.values());
                });
            gui.setItem(slot, icon, click -> {
                    if (!click.isLeftClick()) return;
                    item.editMeta(m -> {
                            if (m instanceof CompassMeta meta) {
                                Location lodestone = found.structure().getBoundingBox().getCenter().toCenterLocation(world);
                                lodestone.setY(0.0);
                                meta.setLodestone(lodestone);
                                meta.setLodestoneTracked(false);
                            }
                        });
                    player.sendActionBar(textOfChildren(displayName, text(" points toward " + found.type().displayName)));
                    player.playSound(location, Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
                });
            if (found.type().type != FinderType.NORMAL) {
                builder.highlightSlot(slot, found.type().type.color);
            }
        }
        gui.title(builder.build());
        gui.open(player);
        player.playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.MASTER, 1.0f, 1.25f);
    }
}
