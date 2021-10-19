package com.cavetale.mytems.item.music;

import com.cavetale.core.event.player.PluginPlayerEvent.Detail;
import com.cavetale.core.event.player.PluginPlayerEvent;
import com.cavetale.core.font.DefaultFont;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.event.music.PlayerBeatEvent;
import com.cavetale.mytems.event.music.PlayerCloseMusicalInstrumentEvent;
import com.cavetale.mytems.event.music.PlayerMelodyCompleteEvent;
import com.cavetale.mytems.event.music.PlayerOpenMusicalInstrumentEvent;
import com.cavetale.mytems.item.font.Glyph;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public final class MusicalInstrument implements Mytem {
    protected static final int HERO_OFFSET = 4 * 9;
    protected static final int HERO_POINTER = 4;
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
    private static final NamespacedKey SHARP_KEY = new NamespacedKey(MytemsPlugin.getInstance(), "sharp");
    private static final NamespacedKey FLAT_KEY = new NamespacedKey(MytemsPlugin.getInstance(), "flat");

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
        public final Touch natural;
        public final Touch sharp;
        public final Touch flat;

        Button(final int x, final Note.Tone tone, final int octave) {
            this.x = x;
            this.tone = tone;
            this.octave = octave;
            this.natural = Touch.of(tone, Semitone.NATURAL, octave);
            this.sharp = tone.isSharpable()
                ? Touch.of(tone, Semitone.SHARP, octave)
                : null;
            this.flat = natural.bukkitNote.flattened().getTone().isSharpable()
                ? Touch.of(tone, Semitone.FLAT, octave)
                : null;
        }
    }

    private static final class GuiPrivateData {
        protected Map<Note.Tone, Semitone> semitones = new EnumMap<>(Note.Tone.class);
        protected Hero hero;

        public Semitone semitoneOf(Note.Tone tone) {
            return semitones.computeIfAbsent(tone, t -> Semitone.NATURAL);
        }

        public Touch touchOf(Note.Tone tone, int octave) {
            return Touch.of(tone, semitoneOf(tone), octave);
        }

        public Touch touchOf(Button button) {
            return touchOf(button.tone, button.octave);
        }

        protected void stopHero() {
            if (hero == null) return;
            if (hero.replay != null) {
                hero.replay.stop();
                hero.replay = null;
            }
            if (hero.task != null) {
                hero.task.cancel();
                hero.task = null;
            }
            hero = null;
        }

        protected void load(ItemStack item) {
            PersistentDataContainer tag = item.getItemMeta().getPersistentDataContainer();
            String sharpString = Tags.getString(tag, SHARP_KEY);
            String flatString = Tags.getString(tag, FLAT_KEY);
            if (sharpString != null) {
                for (int i = 0; i < sharpString.length(); i += 1) {
                    String c = sharpString.substring(i, i + 1);
                    try {
                        Note.Tone tone = Note.Tone.valueOf(c);
                        semitones.put(tone, Semitone.SHARP);
                    } catch (IllegalArgumentException iae) {
                        MytemsPlugin.getInstance().getLogger()
                            .log(Level.SEVERE, "load sharp: " + c, iae);
                        return;
                    }
                }
            }
            if (flatString != null) {
                for (int i = 0; i < flatString.length(); i += 1) {
                    String c = flatString.substring(i, i + 1);
                    try {
                        Note.Tone tone = Note.Tone.valueOf(c);
                        semitones.put(tone, Semitone.FLAT);
                    } catch (IllegalArgumentException iae) {
                        MytemsPlugin.getInstance().getLogger()
                            .log(Level.SEVERE, "load flat: " + c, iae);
                        return;
                    }
                }
            }
        }

        protected void save(ItemStack item) {
            StringBuilder sharpString = new StringBuilder();
            StringBuilder flatString = new StringBuilder();
            for (Note.Tone tone : Note.Tone.values()) {
                Semitone semitone = semitones.get(tone);
                if (semitone == Semitone.SHARP) {
                    sharpString.append(tone.name());
                } else if (semitone == Semitone.FLAT) {
                    flatString.append(tone.name());
                }
            }
            item.editMeta(meta -> {
                    PersistentDataContainer tag = meta.getPersistentDataContainer();
                    if (sharpString.isEmpty()) {
                        tag.remove(SHARP_KEY);
                    } else {
                        Tags.set(tag, SHARP_KEY, sharpString.toString());
                    }
                    if (flatString.isEmpty()) {
                        tag.remove(FLAT_KEY);
                    } else {
                        Tags.set(tag, FLAT_KEY, flatString.toString());
                    }
                });
        }
    }

    @RequiredArgsConstructor
    private static final class Hero {
        protected final Melody melody;
        protected MelodyReplay replay;
        protected Beat[] grid;
        protected BukkitTask task;
        protected long lastTick;
        protected int score;
        protected int maxScore;
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
        openGui(player, item);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (Gui.of(player) != null) return;
        openGui(player, item);
    }

    protected void openGui(Player player, ItemStack instrumentItemStack) {
        PlayerOpenMusicalInstrumentEvent openEvent = new PlayerOpenMusicalInstrumentEvent(player, type);
        openEvent.callEvent();
        GuiPrivateData privateData = new GuiPrivateData();
        privateData.load(instrumentItemStack);
        if (openEvent.isHeroMode()) {
            privateData.hero = new Hero(openEvent.getHeroMelody());
            for (Beat beat : privateData.hero.melody.getBeats()) {
                if (beat.ticks > 0) privateData.hero.maxScore += 1;
            }
        }
        final int size = (openEvent.isHeroMode() ? 6 : 4) * 9;
        Component guiDisplayName = Component.text().color(NamedTextColor.WHITE)
            .append(key.component)
            .append(Component.text(displayNameString))
            .append(Component.text(NOTE1 + NOTE2, NamedTextColor.GRAY))
            .build();
        Gui gui = new Gui()
            .size(size)
            .title(DefaultFont.guiBlankOverlay(size, COLOR, guiDisplayName));
        buildGui(gui, privateData);
        if (openEvent.isHeroMode()) {
            gui.setItem(5 * 9 + HERO_POINTER, Mytems.ARROW_UP.createIcon());
            privateData.hero.grid = new Beat[10];
            privateData.hero.replay = new MelodyReplay(privateData.hero.melody, beat -> {
                    if (beat.ticks == 0) return true;
                    privateData.hero.grid[9] = beat;
                    return true;
            });
            privateData.hero.task = Bukkit.getScheduler().runTaskTimer(MytemsPlugin.getInstance(), () -> {
                    if (privateData.hero.lastTick == 0) {
                        privateData.hero.replay.start();
                    }
                    long now = System.currentTimeMillis();
                    if (now - privateData.hero.lastTick < 50L * 4L) return;
                    privateData.hero.lastTick = now;
                    int noteCount = 0;
                    for (int i = 0; i < privateData.hero.grid.length; i += 1) {
                        Beat beat = privateData.hero.grid[i];
                        privateData.hero.grid[i] = null;
                        if (beat == null) continue;
                        noteCount += 1;
                        if (i < 9) {
                            gui.setItem(HERO_OFFSET + i, null);
                            if (i < 8 && beat.semitone != Semitone.NATURAL) {
                                gui.setItem(HERO_OFFSET + i + 1, null);
                            }
                        }
                        if (i == 0) {
                            PlayerBeatEvent.Action.MISS_BEAT.call(player, type, privateData.hero.melody, beat);
                            updateHeroScore(0, gui, privateData);
                            updateHeroIcon(gui, Mytems.EMPTY_HEART.createIcon(List.of(Component.text("You Missed", NamedTextColor.DARK_RED))));
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 2.0f, 0.5f);
                        } else {
                            privateData.hero.grid[i - 1] = beat;
                            gui.setItem(HERO_OFFSET + i - 1, TONE_MYTEMS_MAP.get(beat.tone).createIcon());
                            if (i < 9) {
                                if (beat.semitone == Semitone.SHARP) {
                                    gui.setItem(HERO_OFFSET + i, Mytems.MUSICAL_SHARP.createIcon());
                                } else if (beat.semitone == Semitone.FLAT) {
                                    gui.setItem(HERO_OFFSET + i, Mytems.MUSICAL_FLAT.createIcon());
                                }
                            }
                        }
                    }
                    if (noteCount == 0 && privateData.hero.replay.didStop()) {
                        new PlayerMelodyCompleteEvent(player, type, privateData.hero.melody,
                                                      privateData.hero.score, privateData.hero.maxScore).callEvent();
                        updateHeroIcon(gui, null);
                        privateData.stopHero();
                    }
                }, 80L, 1L);
        }
        gui.onClose(evt -> {
                privateData.save(instrumentItemStack);
                new PlayerCloseMusicalInstrumentEvent(player, type).callEvent();
                privateData.stopHero();
            });
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
        Touch touch = privateData.touchOf(button.tone, button.octave);
        text.add(Component.text(touch.toString(), COLOR));
        if (privateData.hero == null) {
            // Shorten this in hero mode!
            if (button.flat != null) {
                if (privateData.semitones.get(button.tone) == Semitone.FLAT) {
                    text.add(Component.text(button.tone.name() + Semitone.NATURAL.symbol, COLOR)
                             .append(Component.text(" Shift", NamedTextColor.GRAY)));
                } else {
                    text.add(Component.text(button.tone.name() + Semitone.FLAT.symbol, COLOR)
                             .append(Component.text(" Shift", NamedTextColor.GRAY)));
                }
            }
            if (button.sharp != null) {
                if (privateData.semitones.get(button.tone) == Semitone.SHARP) {
                    text.add(Component.text(button.tone.name() + Semitone.NATURAL.symbol, COLOR)
                             .append(Component.text(" Right", NamedTextColor.GRAY)));
                } else {
                    text.add(Component.text(button.tone.name() + Semitone.SHARP.symbol, COLOR)
                             .append(Component.text(" Right", NamedTextColor.GRAY)));
                }
            }
            text.add(Component.empty());
            text.add(Component.text("Interval")
                     .append(Component.text(" Number Key", NamedTextColor.GRAY)));
            text.add(Component.text(Semitone.SHARP.symbol + " Interval")
                     .append(Component.text(" F", NamedTextColor.GRAY)));
            text.add(Component.text(Semitone.FLAT.symbol + " Interval")
                     .append(Component.text(" Q", NamedTextColor.GRAY)));
        }
        ItemStack icon = Items.text(TONE_MYTEMS_MAP.get(button.tone).createIcon(), text);
        return icon;
    }

    protected ItemStack makeSemitoneButton(Button button, GuiPrivateData privateData) {
        Semitone semitone = privateData.semitoneOf(button.tone);
        List<Component> text = new ArrayList<>();
        Touch touch = privateData.touchOf(button.tone, button.octave);
        text.add(Component.text(touch.toString(), COLOR));
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
        final Touch touch;
        final boolean left = event.isLeftClick();
        final boolean right = event.isRightClick();
        final boolean shift = event.isShiftClick();
        final boolean isNumber = event.getClick() == ClickType.NUMBER_KEY;
        if (isNumber) {
            int index = button.ordinal() + event.getHotbarButton();
            Button[] allButtons = Button.values();
            Button theButton = allButtons[index % allButtons.length];
            touch = privateData.touchOf(theButton);
        } else if (left && !right && !shift) {
            touch = privateData.touchOf(button);
        } else if (left && !right && shift) {
            touch = privateData.semitones.get(button.tone) == Semitone.FLAT
                ? button.natural
                : button.flat;
        } else if (!left && right && !shift) {
            touch = privateData.semitones.get(button.tone) == Semitone.SHARP
                ? button.natural
                : button.sharp;
        } else {
            touch = null;
        }
        if (touch == null) return;
        ComponentLike actionBar = Component.text()
            .append(key.component)
            .append(TONE_MYTEMS_MAP.get(touch.tone).component)
            .append(touch.semitone != Semitone.NATURAL ? touch.semitone.mytems.component : Component.empty());
        player.sendActionBar(actionBar);
        player.playNote(player.getLocation(), type.instrument, touch.bukkitNote);
        for (Entity nearby : player.getNearbyEntities(16.0, 16.0, 16.0)) {
            if (nearby instanceof Player) {
                ((Player) nearby).playNote(player.getLocation(), type.instrument, touch.bukkitNote);
            }
        }
        Location particleLoc = player.getEyeLocation();
        particleLoc.add(particleLoc.getDirection().normalize().multiply(0.5));
        particleLoc.getWorld().spawnParticle(Particle.NOTE, particleLoc, 1, 0.125, 0.125, 0.125, 0.0);
        PluginPlayerEvent.Name.PLAY_NOTE.ultimate(MytemsPlugin.getInstance(), player)
            .detail(Detail.NOTE, touch.bukkitNote)
            .detail(Detail.INSTRUMENT, type.instrument)
            .call();
        if (privateData.hero != null) {
            for (int i = 0; i < privateData.hero.grid.length; i += 1) {
                Beat beat = privateData.hero.grid[i];
                if (beat == null) continue;
                if (beat.countsAs(touch)) {
                    privateData.hero.grid[i] = null;
                    if (i <= HERO_POINTER) {
                        PlayerBeatEvent.Action.HIT_BEAT.call(player, type, privateData.hero.melody, beat);
                        updateHeroScore(1, gui, privateData);
                        updateHeroIcon(gui, Mytems.STAR.createIcon(List.of(Component.text("Perfect", NamedTextColor.GOLD))));
                    } else {
                        PlayerBeatEvent.Action.PLAY_BEAT_EARLY.call(player, type, privateData.hero.melody, beat);
                        updateHeroScore(1, gui, privateData);
                        updateHeroIcon(gui, Mytems.OK.createIcon(List.of(Component.text("Correct", NamedTextColor.GREEN))));
                    }
                    if (i < 9) {
                        gui.setItem(HERO_OFFSET + i, null);
                        if (i < 8 && beat.semitone != Semitone.NATURAL) {
                            gui.setItem(HERO_OFFSET + i + 1, null);
                        }
                    }
                    return;
                }
            }
            PlayerBeatEvent.Action.PLAY_OUT_OF_TUNE.call(player, type, privateData.hero.melody);
            updateHeroScore(-1, gui, privateData);
            updateHeroIcon(gui, Mytems.NO.createIcon(List.of(Component.text("Wrong!", NamedTextColor.DARK_RED))));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 2.0f, 0.5f);
        }
    }

    protected void updateHeroScore(int sum, Gui gui, GuiPrivateData privateData) {
        privateData.hero.score = Math.max(0, privateData.hero.score + sum);
        List<ItemStack> items = Glyph.toItemStacks("" + privateData.hero.score);
        int offset = gui.getSize() - 1;
        for (int i = 0; i < 4; i += 1) {
            gui.setItem(offset - i, (i < items.size()
                                     ? items.get(items.size() - 1 - i)
                                     : (ItemStack) null));
        }
    }

    protected void updateHeroIcon(Gui gui, ItemStack heroIcon) {
        gui.setItem(gui.getSize() - 9, heroIcon);
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

    protected static final class MusicalInstrumentTag {
        protected String flat;
        protected String sharp;

        public boolean isEmpty() {
            return flat == null && sharp == null;
        }
    }

    @Override
    public String serializeTag(ItemStack itemStack) {
        MusicalInstrumentTag result = new MusicalInstrumentTag();
        PersistentDataContainer tag = itemStack.getItemMeta().getPersistentDataContainer();
        result.sharp = Tags.getString(tag, SHARP_KEY);
        result.flat = Tags.getString(tag, FLAT_KEY);
        return result.isEmpty() ? null : Json.serialize(result);
    }

    @Override
    public ItemStack deserializeTag(String serialized, Player player) {
        ItemStack result = createItemStack();
        MusicalInstrumentTag tag = Json.deserialize(serialized, MusicalInstrumentTag.class);
        if (!tag.isEmpty()) {
            result.editMeta(meta -> {
                    PersistentDataContainer tag2 = meta.getPersistentDataContainer();
                    if (tag.sharp != null) {
                        Tags.set(tag2, SHARP_KEY, tag.sharp);
                    }
                    if (tag.flat != null) {
                        Tags.set(tag2, FLAT_KEY, tag.flat);
                    }
                });
        }
        return result;
    }
}
