package com.cavetale.mytems.item.music;

import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.core.font.DefaultFont;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Text;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.WeatherType;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public final class HyruleInstrument implements Mytem {
    @Getter private final Mytems key;
    @Getter private Component displayName;
    private List<Component> lore;
    private ItemStack prototype;
    private Instrument instrument;
    private static final TextColor GOLD = TextColor.color(0xFFD700);
    private InstrumentType type;

    @RequiredArgsConstructor
    private enum InstrumentType {
        BANJO(Mytems.GOLDEN_BANJO, Instrument.BANJO),
        OCARINA(Mytems.OCARINA_OF_CHIME, Instrument.FLUTE);

        public final Mytems mytems;
        public final Instrument instrument;
    }

    @Override
    public void enable() {
        for (InstrumentType it : InstrumentType.values()) {
            if (it.mytems == key) {
                type = it;
                break;
            }
        }
        Objects.requireNonNull(type, "type=null");
        displayName = Component.text().content(Text.toCamelCase(key, " ")).color(GOLD)
            .decoration(TextDecoration.ITALIC, false).build();
        lore = Arrays.asList(Component.text()
                             .append(Component.text("Right-click", NamedTextColor.GREEN))
                             .append(Component.text(" to play the " + type.name().toLowerCase() + "!", NamedTextColor.GRAY))
                             .build());
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                meta.displayName(displayName);
                meta.lore(lore);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @RequiredArgsConstructor
    enum Button {
        UP(5, 0, Mytems.ARROW_UP, Note.Tone.D, 1),
        LEFT(4, 1, Mytems.ARROW_LEFT, Note.Tone.B, 1),
        RIGHT(6, 1, Mytems.ARROW_RIGHT, Note.Tone.A, 1),
        DOWN(5, 2, Mytems.ARROW_DOWN, Note.Tone.F, 0),
        A(3, 3, Mytems.OK, Note.Tone.D, 0);

        public final int x;
        public final int y;
        public final Mytems mytems;
        public final Note.Tone tone;
        public final int octave;

        public Note natural() {
            return Note.natural(octave, tone);
        }
    }

    enum Melody {
        LULLABY(Button.LEFT, Button.UP, Button.RIGHT, Button.LEFT, Button.UP, Button.RIGHT),
        HORSE(Button.UP, Button.LEFT, Button.RIGHT, Button.UP, Button.LEFT, Button.RIGHT),
        WOODS(Button.DOWN, Button.RIGHT, Button.LEFT, Button.DOWN, Button.RIGHT, Button.LEFT),
        SUN(Button.RIGHT, Button.DOWN, Button.UP, Button.RIGHT, Button.DOWN, Button.UP),
        TIME(Button.RIGHT, Button.A, Button.DOWN, Button.RIGHT, Button.A, Button.DOWN),
        RAIN(Button.A, Button.DOWN, Button.UP, Button.A, Button.DOWN, Button.UP);

        private Button[] buttons;

        Melody(final Button... buttons) {
            this.buttons = buttons;
        }

        int play(Button button, int currentIndex) {
            if (currentIndex < 0 || currentIndex >= buttons.length) return 0;
            if (currentIndex > 0 && buttons[currentIndex] == button) return currentIndex + 1;
            if (buttons[0] == button) return 1;
            return 0;
        }
    }

    @RequiredArgsConstructor
    static class Progress {
        private final Melody melody;
        private int nextNote = 0;
    }

    static class Session {
        private final Map<Melody, Progress> melodies = new EnumMap<>(Melody.class);
    }

    static String toString(Note note) {
        return note.getTone().toString()
            + (note.getOctave() == 1 ? "'" : "")
            + (note.isSharped() ? "#" : "");
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        final int size = 5 * 9;
        Gui gui = new Gui()
            .size(size)
            .title(Component.text()
                   .append(DefaultFont.guiBlankOverlay(size, GOLD))
                   .append(key.component)
                   .append(displayName)
                   .build());
        for (Button button : Button.values()) {
            ItemStack icon = button.mytems.createItemStack();
            List<Component> tooltip = Arrays.asList(new Component[] {
                    Component.text()
                    .append(Component.text(toString(button.natural().flattened()), GOLD))
                    .append(Component.text(" Shift", NamedTextColor.GRAY))
                    .build(),
                    Component.text()
                    .append(Component.text(toString(button.natural().sharped()), GOLD))
                    .append(Component.text(" Right-click", NamedTextColor.GRAY))
                    .build(),
                    Component.text()
                    .append(Component.text(toString(button.natural().sharped().sharped()), GOLD))
                    .append(Component.text(" Shift+Right-click", NamedTextColor.GRAY))
                    .build(),
                });
            icon.editMeta(meta -> {
                    meta.displayName(Component.text(button.tone.name(), GOLD));
                    meta.lore(tooltip);
                });
            gui.setItem(button.x, button.y, icon, click -> {
                    Note note;
                    boolean isMelody = true;
                    if (click.isLeftClick()) {
                        note = button.natural();
                        if (click.isShiftClick()) {
                            note = note.flattened();
                            isMelody = false;
                        }
                    } else if (click.isRightClick()) {
                        isMelody = false;
                        note = button.natural().sharped();
                        if (click.isShiftClick()) {
                            note = note.sharped();
                        }
                    } else {
                        return;
                    }
                    player.playNote(player.getLocation(), type.instrument, note);
                    for (Entity nearby : player.getNearbyEntities(16.0, 16.0, 16.0)) {
                        if (nearby instanceof Player) {
                            ((Player) nearby).playNote(player.getLocation(), type.instrument, note);
                        }
                    }
                    Location particleLoc = player.getEyeLocation();
                    particleLoc.add(particleLoc.getDirection().normalize().multiply(0.5));
                    particleLoc.getWorld().spawnParticle(Particle.NOTE, particleLoc, 1, 0.125, 0.125, 0.125, 0.0);
                    if (isMelody) {
                        Session session = MytemsPlugin.getInstance().getSessions().of(player).getFavorites()
                            .getOrSet(Session.class, Session::new);
                        MELODIES: for (Melody melody : Melody.values()) {
                            Progress progress = session.melodies.computeIfAbsent(melody, s -> new Progress(s));
                            progress.nextNote = melody.play(button, progress.nextNote);
                            if (progress.nextNote >= progress.melody.buttons.length) {
                                MytemsPlugin.getInstance().getSessions().of(player).getFavorites().clear(Session.class);
                                Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(),
                                                              () -> onMelodyComplete(player, melody));
                                break MELODIES;
                            }
                        }
                    } else {
                        MytemsPlugin.getInstance().getSessions().of(player).getFavorites().clear(Session.class);
                    }
                });
        }
        gui.onClose(evt -> {
                MytemsPlugin.getInstance().getSessions().of(player).getFavorites().clear(Session.class);
            });
        gui.open(player);
    }

    void onMelodyComplete(Player player, Melody melody) {
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.5f, 2.0f);
        switch (melody) {
        case LULLABY: {
            PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 0, false, false, false);
            player.addPotionEffect(effect);
            break;
        }
        case HORSE:
            for (Entity entity : player.getNearbyEntities(16.0, 16.0, 16.0)) {
                if (entity instanceof Horse) {
                    Horse horse = (Horse) entity;
                    if (horse.isTamed() && player.equals(horse.getOwner())) {
                        horse.getPathfinder().moveTo(player);
                    }
                }
            }
            break;
        case WOODS:
            player.spawnParticle(Particle.BLOCK_DUST, player.getEyeLocation(), 32, 0.6, 0.6, 0.6, 0.0,
                                 Material.OAK_LEAVES.createBlockData());
            for (Entity entity : player.getNearbyEntities(16.0, 16.0, 16.0)) {
                if (entity instanceof Animals && !(entity instanceof AbstractHorse)) {
                    Animals animals = (Animals) entity;
                    if (PlayerEntityAbilityQuery.Action.GIMMICK.query(player, animals)) {
                        animals.getPathfinder().moveTo(player);
                    }
                }
            }
            break;
        case SUN:
            if (!player.isPlayerTimeRelative() || player.getPlayerTimeOffset() != 0L) {
                player.resetPlayerTime();
            } else {
                player.setPlayerTime(12000L, true);
            }
            break;
        case TIME:
            player.spawnParticle(Particle.BLOCK_DUST, player.getEyeLocation(), 32, 0.6, 0.6, 0.6, 0.0,
                                 Material.COBWEB.createBlockData());
            break;
        case RAIN:
            if (player.getPlayerWeather() == null) {
                player.setPlayerWeather(player.getWorld().hasStorm()
                                        ? WeatherType.CLEAR
                                        : WeatherType.DOWNFALL);
            } else {
                switch (player.getPlayerWeather()) {
                case CLEAR:
                    player.setPlayerWeather(WeatherType.DOWNFALL);
                    break;
                case DOWNFALL: default:
                    player.setPlayerWeather(WeatherType.CLEAR);
                    break;
                }
            }
            break;
        default: break;
        }
    }
}
