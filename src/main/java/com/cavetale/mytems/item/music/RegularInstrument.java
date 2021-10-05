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
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class RegularInstrument implements Mytem {
    public static final String FLAT = "\u266D";
    public static final String SHARP = "\u266F";
    public static final String NOTE1 = "\u266B";
    public static final String NOTE2 = "\u266A";
    @Getter private final Mytems key;
    private String displayNameString;
    @Getter private Component displayName;
    private List<Component> lore;
    private ItemStack prototype;
    private Instrument instrument;
    private static final TextColor COLOR = TextColor.color(0x6A5ACD);
    private InstrumentType type;

    @RequiredArgsConstructor
    private enum InstrumentType {
        BANJO(Mytems.BANJO, Instrument.BANJO),
        BIT_BOY(Mytems.BIT_BOY, Instrument.BIT),
        COW_BELL(Mytems.COW_BELL, Instrument.COW_BELL),
        ELECTRIC_GUITAR(Mytems.ELECTRIC_GUITAR, Instrument.BASS_GUITAR),
        ELECTRIC_PIANO(Mytems.ELECTRIC_PIANO, Instrument.PIANO),
        GUITAR(Mytems.GUITAR, Instrument.GUITAR),
        MUSICAL_BELL(Mytems.MUSICAL_BELL, Instrument.BELL),
        PAN_FLUTE(Mytems.PAN_FLUTE, Instrument.FLUTE),
        POCKET_PIANO(Mytems.POCKET_PIANO, Instrument.PIANO),
        RAINBOW_XYLOPHONE(Mytems.RAINBOW_XYLOPHONE, Instrument.XYLOPHONE),
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
        public final Note natural;
        public final Note sharp;
        public final Note flat;

        Button(final int x, final Note.Tone tone, final int octave) {
            this.x = x;
            this.tone = tone;
            this.octave = octave;
            this.natural = Note.natural(octave, tone);
            this.sharp = tone.isSharpable() ? Note.sharp(octave, tone) : null;
            this.flat = natural.flattened().getTone().isSharpable() ? Note.flat(octave, tone) : null;
        }
    }

    private static final class GuiPrivateData {
        boolean sharp;
        boolean flat;
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
            .append(Component.text(NOTE1 + " "))
            .append(key.component)
            .append(Component.text(displayNameString + " " + NOTE2))
            .build();
        Gui gui = new Gui()
            .size(size)
            .title(DefaultFont.guiBlankOverlay(size, COLOR, guiDisplayName));
        GuiPrivateData privateData = new GuiPrivateData();
        for (Button button : Button.values()) {
            List<Component> text = new ArrayList<>();
            text.add(Component.text(button.tone.name(), COLOR));
            if (button.flat != null) {
                text.add(Component.text(button.tone.name() + FLAT, COLOR)
                         .append(Component.text(" Shift", NamedTextColor.GRAY)));
            }
            if (button.sharp != null) {
                text.add(Component.text(button.tone.name() + SHARP, COLOR)
                         .append(Component.text(" Right", NamedTextColor.GRAY)));
            }
            text.add(Component.empty());
            text.add(Component.text("Interval")
                     .append(Component.text(" Number Key", NamedTextColor.GRAY)));
            text.add(Component.text(SHARP + " Interval")
                     .append(Component.text(" F", NamedTextColor.GRAY)));
            text.add(Component.text(FLAT + " Interval ")
                     .append(Component.text(" Q", NamedTextColor.GRAY)));
            ItemStack icon = Items.text(key.createIcon(), text);
            icon.setAmount(button.ordinal() + 1);
            final int x = button.octave == 0
                ? 7 - button.x
                : 1 + button.x;
            final int y = 1 + 1 - button.octave;
            gui.setItem(x, y, icon, click -> {
                    if (click.getClick() == ClickType.DROP) {
                        privateData.sharp = false;
                        privateData.flat = true;
                        player.sendActionBar(Component.text(FLAT, NamedTextColor.GOLD));
                        return;
                    } else if (click.getClick() == ClickType.SWAP_OFFHAND) {
                        privateData.sharp = true;
                        privateData.flat = false;
                        player.sendActionBar(Component.text(SHARP, NamedTextColor.GOLD));
                        return;
                    }
                    final Note note;
                    final boolean sharp;
                    final boolean flat;
                    final boolean left = click.isLeftClick();
                    final boolean right = click.isRightClick();
                    final boolean shift = click.isShiftClick();
                    final boolean isNumber = click.getClick() == ClickType.NUMBER_KEY;
                    if (isNumber) {
                        sharp = privateData.sharp;
                        flat = privateData.flat;
                        int index = button.ordinal() + click.getHotbarButton();
                        Button[] allButtons = Button.values();
                        Button theButton = allButtons[index % allButtons.length];
                        if (sharp && !flat) {
                            note = theButton.sharp;
                        } else if (flat && !sharp) {
                            note = theButton.flat;
                        } else {
                            note = theButton.natural;
                        }
                    } else if (left && !right && !shift) {
                        note = button.natural;
                        sharp = false;
                        flat = false;
                    } else if (left && !right && shift) {
                        note = button.flat;
                        sharp = false;
                        flat = true;
                    } else if (!left && right && !shift) {
                        note = button.sharp;
                        sharp = true;
                        flat = false;
                    } else {
                        note = null;
                        sharp = false;
                        flat = false;
                    }
                    privateData.sharp = false;
                    privateData.flat = false;
                    if (note == null) return;
                    String noteName = note.getTone().name()
                        + (sharp ? SHARP : "")
                        + (flat ? FLAT : "");
                    player.sendActionBar(Component.text(noteName, NamedTextColor.GOLD));
                    player.playNote(player.getLocation(), type.instrument, note);
                    for (Entity nearby : player.getNearbyEntities(16.0, 16.0, 16.0)) {
                        if (nearby instanceof Player) {
                            ((Player) nearby).playNote(player.getLocation(), type.instrument, note);
                        }
                    }
                    Location particleLoc = player.getEyeLocation();
                    particleLoc.add(particleLoc.getDirection().normalize().multiply(0.5));
                    particleLoc.getWorld().spawnParticle(Particle.NOTE, particleLoc, 1, 0.125, 0.125, 0.125, 0.0);
                    PluginPlayerEvent.Name.PLAY_NOTE.ultimate(MytemsPlugin.getInstance(), player)
                        .detail(Detail.NOTE, note)
                        .detail(Detail.INSTRUMENT, type.instrument)
                        .call();
                });
        }
        gui.open(player);
    }
}
