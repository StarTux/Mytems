package com.cavetale.mytems.item.music;

import com.cavetale.core.event.player.PluginPlayerEvent.Detail;
import com.cavetale.core.event.player.PluginPlayerEvent;
import com.cavetale.core.font.DefaultFont;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class RegularInstrument implements Mytem {
    public static final String NOTE1 = "\u266A";
    public static final String NOTE2 = "\u266B";
    @Getter private final Mytems key;
    private String displayNameString;
    @Getter private Component displayName;
    private List<Component> lore;
    private ItemStack prototype;
    private Instrument instrument;
    private static final TextColor COLOR = TextColor.color(0x6A5ACD);
    private InstrumentType type;
    private static final Map<Note.Tone, Mytems> TONE_MYTEMS_MAP = Map
        .of(Note.Tone.A, Mytems.LETTER_A,
            Note.Tone.B, Mytems.LETTER_B,
            Note.Tone.C, Mytems.LETTER_C,
            Note.Tone.D, Mytems.LETTER_D,
            Note.Tone.E, Mytems.LETTER_E,
            Note.Tone.F, Mytems.LETTER_F,
            Note.Tone.G, Mytems.LETTER_G);

    @RequiredArgsConstructor
    private enum InstrumentType {
        ANGELIC_HARP(Mytems.ANGELIC_HARP, Instrument.PIANO),
        BANJO(Mytems.BANJO, Instrument.BANJO),
        BIT_BOY(Mytems.BIT_BOY, Instrument.BIT),
        CLICKS_AND_STICKS(Mytems.CLICKS_AND_STICKS, Instrument.STICKS),
        COW_BELL(Mytems.COW_BELL, Instrument.COW_BELL),
        ELECTRIC_GUITAR(Mytems.ELECTRIC_GUITAR, Instrument.BASS_GUITAR),
        ELECTRIC_PIANO(Mytems.ELECTRIC_PIANO, Instrument.PLING),
        GUITAR(Mytems.GUITAR, Instrument.GUITAR),
        IRON_XYLOPHONE(Mytems.IRON_XYLOPHONE, Instrument.IRON_XYLOPHONE),
        MUSICAL_BELL(Mytems.MUSICAL_BELL, Instrument.BELL),
        PAN_FLUTE(Mytems.PAN_FLUTE, Instrument.FLUTE),
        POCKET_PIANO(Mytems.POCKET_PIANO, Instrument.PIANO),
        RAINBOW_XYLOPHONE(Mytems.RAINBOW_XYLOPHONE, Instrument.XYLOPHONE),
        SNARE_DRUM(Mytems.SNARE_DRUM, Instrument.SNARE_DRUM),
        TRIANGLE(Mytems.TRIANGLE, Instrument.CHIME),
        WOODEN_DRUM(Mytems.WOODEN_DRUM, Instrument.BASS_DRUM),
        WOODEN_HORN(Mytems.WOODEN_HORN, Instrument.DIDGERIDOO),
        WOODEN_LUTE(Mytems.WOODEN_LUTE, Instrument.GUITAR),
        WOODEN_OCARINA(Mytems.WOODEN_OCARINA, Instrument.FLUTE);

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
        this.displayNameString = Text.toCamelCase(key, " ");
        this.displayName = Component.text(displayNameString, COLOR);
        List<Component> text = List.of(displayName,
                                       Component.text("Right-click", NamedTextColor.GREEN)
                                       .append(Component.text(" to play", NamedTextColor.GRAY)));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    enum Button {
        G(0, Note.Tone.G, 0),
        A(1, Note.Tone.A, 0),
        B(2, Note.Tone.B, 0),
        C(3, Note.Tone.C, 0),
        D(4, Note.Tone.D, 0),
        E(5, Note.Tone.E, 0),
        F(6, Note.Tone.F, 0),
        G1(0, Note.Tone.G, 1),
        A1(1, Note.Tone.A, 1),
        B1(2, Note.Tone.B, 1),
        C1(3, Note.Tone.C, 1),
        D1(4, Note.Tone.D, 1),
        E1(5, Note.Tone.E, 1),
        F1(6, Note.Tone.F, 1);

        public final int x;
        public final Note.Tone tone;
        public final int octave;
        public final RealNote natural;
        public final RealNote sharp;
        public final RealNote flat;

        Button(final int x, final Note.Tone tone, final int octave) {
            this.x = x;
            this.tone = tone;
            this.octave = octave;
            this.natural = RealNote.of(tone, Semitone.NATURAL, octave);
            this.sharp = tone.isSharpable()
                ? RealNote.of(tone, Semitone.SHARP, octave)
                : null;
            this.flat = natural.bukkitNote.flattened().getTone().isSharpable()
                ? RealNote.of(tone, Semitone.FLAT, octave)
                : null;
        }
    }

    @RequiredArgsConstructor
    private enum Semitone {
        NATURAL("", Mytems.INVISIBLE_ITEM) {
            @Override public Note apply(Note in) {
                return in;
            }
        },
        SHARP("\u266F", Mytems.MUSICAL_SHARP) {
            @Override public Note apply(Note in) {
                return in.sharped();
            }
        },
        FLAT("\u266D", Mytems.MUSICAL_FLAT) {
            @Override public Note apply(Note in) {
                return in.flattened();
            }
        };

        public final String symbol;
        public final Mytems mytems;
        public abstract Note apply(Note in);
    }

    private static final class RealNote {
        public final Note.Tone tone;
        public final Semitone semitone;
        public final Note bukkitNote;
        public final String displayString;

        private RealNote(final Note.Tone tone, final Semitone semitone, final Note bukkitNote) {
            this.tone = tone;
            this.semitone = semitone;
            this.bukkitNote = bukkitNote;
            this.displayString = tone.name() + semitone.symbol;
        }

        public static RealNote of(Note.Tone tone, Semitone semitone, int octave) {
            Note bukkitNote = semitone.apply(Note.natural(octave, tone));
            return bukkitNote.getTone() == tone || bukkitNote.isSharped()
                ? new RealNote(tone, semitone, bukkitNote)
                : new RealNote(bukkitNote.getTone(), Semitone.NATURAL, bukkitNote);
        }
    }

    private static final class GuiPrivateData {
        protected Map<Note.Tone, Semitone> semitones = new EnumMap<>(Note.Tone.class);

        public Semitone semitoneOf(Note.Tone tone) {
            return semitones.computeIfAbsent(tone, t -> Semitone.NATURAL);
        }

        public RealNote realNoteOf(Note.Tone tone, int octave) {
            return RealNote.of(tone, semitoneOf(tone), octave);
        }

        public RealNote realNoteOf(Button button) {
            return realNoteOf(button.tone, button.octave);
        }
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        if (Gui.of(player) != null) return;
        openGui(player);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (Gui.of(player) != null) return;
        openGui(player);
    }

    protected void openGui(Player player) {
        final int size = 4 * 9;
        Component guiDisplayName = Component.text().color(NamedTextColor.WHITE)
            .append(key.component)
            .append(Component.text(displayNameString))
            .append(Component.text(NOTE1 + NOTE2, NamedTextColor.GRAY))
            .build();
        Gui gui = new Gui()
            .size(size)
            .title(DefaultFont.guiBlankOverlay(size, COLOR, guiDisplayName));
        GuiPrivateData privateData = new GuiPrivateData();
        buildGui(gui, privateData);
        gui.open(player);
    }

    protected void buildGui(Gui gui, GuiPrivateData privateData) {
        for (Button button : Button.values()) {
            final int x = button.octave == 0
                ? 7 - button.x
                : 1 + button.x;
            final int y = 1 + 1 - button.octave;
            ItemStack icon = makeNoteButton(button, privateData);
            gui.setItem(x, y, icon, click -> onClickButton(click, button, gui, privateData));
            final int y2 = y == 1 ? 0 : 3;
            ItemStack semiIcon = makeSemitoneButton(button, privateData);
            gui.setItem(x, y2, semiIcon, click -> onClickSemitone(click, button, gui, privateData));
        }
    }

    protected ItemStack makeNoteButton(Button button, GuiPrivateData privateData) {
        List<Component> text = new ArrayList<>();
        RealNote realNote = privateData.realNoteOf(button.tone, button.octave);
        text.add(Component.text(realNote.displayString, COLOR));
        if (button.flat != null) {
            text.add(Component.text(button.tone.name() + Semitone.FLAT.symbol, COLOR)
                     .append(Component.text(" Shift", NamedTextColor.GRAY)));
        }
        if (button.sharp != null) {
            text.add(Component.text(button.tone.name() + Semitone.SHARP.symbol, COLOR)
                     .append(Component.text(" Right", NamedTextColor.GRAY)));
        }
        text.add(Component.empty());
        text.add(Component.text("Interval")
                 .append(Component.text(" Number Key", NamedTextColor.GRAY)));
        text.add(Component.text(Semitone.SHARP.symbol + " Interval")
                 .append(Component.text(" F", NamedTextColor.GRAY)));
        text.add(Component.text(Semitone.FLAT.symbol + " Interval")
                 .append(Component.text(" Q", NamedTextColor.GRAY)));
        ItemStack icon = Items.text(TONE_MYTEMS_MAP.get(button.tone).createIcon(), text);
        return icon;
    }

    protected ItemStack makeSemitoneButton(Button button, GuiPrivateData privateData) {
        Semitone semitone = privateData.semitoneOf(button.tone);
        List<Component> text = new ArrayList<>();
        RealNote realNote = privateData.realNoteOf(button.tone, button.octave);
        text.add(Component.text(realNote.displayString, COLOR));
        text.add(Component.text("Sharpen", COLOR)
                 .append(Component.text(" Left", NamedTextColor.GRAY)));
        text.add(Component.text("Flatten", COLOR)
                 .append(Component.text(" Right", NamedTextColor.GRAY)));
        ItemStack icon = Items.text(semitone.mytems.createIcon(), text);
        return icon;
    }

    protected void onClickButton(InventoryClickEvent event, Button button, Gui gui, GuiPrivateData privateData) {
        if (event.getClick() == ClickType.DOUBLE_CLICK) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        final RealNote note;
        final boolean left = event.isLeftClick();
        final boolean right = event.isRightClick();
        final boolean shift = event.isShiftClick();
        final boolean isNumber = event.getClick() == ClickType.NUMBER_KEY;
        if (isNumber) {
            int index = button.ordinal() + event.getHotbarButton();
            Button[] allButtons = Button.values();
            Button theButton = allButtons[index % allButtons.length];
            note = privateData.realNoteOf(theButton);
        } else if (left && !right && !shift) {
            note = privateData.realNoteOf(button);
        } else if (left && !right && shift) {
            note = button.flat;
        } else if (!left && right && !shift) {
            note = button.sharp;
        } else {
            note = null;
        }
        if (note == null) return;
        ComponentLike actionBar = Component.text()
            .append(key.component)
            .append(TONE_MYTEMS_MAP.get(note.tone).component)
            .append(note.semitone != Semitone.NATURAL ? note.semitone.mytems.component : Component.empty());
        player.sendActionBar(actionBar);
        player.playNote(player.getLocation(), type.instrument, note.bukkitNote);
        for (Entity nearby : player.getNearbyEntities(16.0, 16.0, 16.0)) {
            if (nearby instanceof Player) {
                ((Player) nearby).playNote(player.getLocation(), type.instrument, note.bukkitNote);
            }
        }
        Location particleLoc = player.getEyeLocation();
        particleLoc.add(particleLoc.getDirection().normalize().multiply(0.5));
        particleLoc.getWorld().spawnParticle(Particle.NOTE, particleLoc, 1, 0.125, 0.125, 0.125, 0.0);
        PluginPlayerEvent.Name.PLAY_NOTE.ultimate(MytemsPlugin.getInstance(), player)
            .detail(Detail.NOTE, note.bukkitNote)
            .detail(Detail.INSTRUMENT, type.instrument)
            .call();
    }

    protected void onClickSemitone(InventoryClickEvent event, Button button, Gui gui, GuiPrivateData privateData) {
        if (event.getClick() == ClickType.DOUBLE_CLICK) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        final int interval;
        if (event.isLeftClick()) {
            interval = +1;
        } else if (event.isRightClick()) {
            interval = -1;
        } else {
            return;
        }
        Semitone semitone = privateData.semitoneOf(button.tone);
        int index = (semitone.ordinal() + interval) % 3;
        if (index < 0) index += 3;
        Semitone newSemitone = Semitone.values()[index];
        privateData.semitones.put(button.tone, newSemitone);
        buildGui(gui, privateData);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
    }
}
