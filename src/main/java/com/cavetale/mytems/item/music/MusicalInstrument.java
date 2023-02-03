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
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note.Tone;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitTask;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public final class MusicalInstrument implements Mytem {
    protected static final int HERO_OFFSET = 4 * 9 + 1;
    protected static final int HERO_CLOCK = 5 * 9;
    protected static final int GRID_TIME = 20 * 6;
    public static final String NOTE1 = "\u266A";
    public static final String NOTE2 = "\u266B";
    @Getter private final Mytems key;
    private String displayNameString;
    @Getter private Component displayName;
    private List<Component> tooltip;
    private ItemStack prototype;
    private Instrument instrument;
    private static final TextColor COLOR = TextColor.color(0xFFAA88);
    private static final TextColor BG = TextColor.color(0x6A5ACD);
    private InstrumentType type;
    private static final Map<Tone, Mytems> TONE_MYTEMS_MAP = Map
        .of(Tone.A, Mytems.LETTER_A,
            Tone.B, Mytems.LETTER_B,
            Tone.C, Mytems.LETTER_C,
            Tone.D, Mytems.LETTER_D,
            Tone.E, Mytems.LETTER_E,
            Tone.F, Mytems.LETTER_F,
            Tone.G, Mytems.LETTER_G);
    protected static final NamespacedKey SHARP_KEY = new NamespacedKey(MytemsPlugin.getInstance(), "sharp");
    protected static final NamespacedKey FLAT_KEY = new NamespacedKey(MytemsPlugin.getInstance(), "flat");

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
        this.displayName = text(displayNameString, COLOR);
        tooltip = List.of(displayName,
                          join(noSeparators(),
                               Mytems.MOUSE_RIGHT,
                               text(" Play the " + Text.toCamelCase(type, " ") + "!", GRAY)));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, tooltip);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    enum Button {
        G(0, Tone.G, 0),
        A(1, Tone.A, 0),
        B(2, Tone.B, 0),
        C(3, Tone.C, 0),
        D(4, Tone.D, 0),
        E(5, Tone.E, 0),
        F(6, Tone.F, 0),
        G1(0, Tone.G, 1),
        A1(1, Tone.A, 1),
        B1(2, Tone.B, 1),
        C1(3, Tone.C, 1),
        D1(4, Tone.D, 1),
        E1(5, Tone.E, 1),
        F1(6, Tone.F, 1);

        public final int x;
        public final Tone tone;
        public final int octave;
        public final Touch natural;
        public final Touch sharp;
        public final Touch flat;

        Button(final int x, final Tone tone, final int octave) {
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

    private final class GuiPrivateData {
        protected Map<Tone, Semitone> semitones = new EnumMap<>(Tone.class);
        protected Hero hero;

        public Semitone semitoneOf(Tone tone) {
            return semitones.computeIfAbsent(tone, t -> Semitone.NATURAL);
        }

        public Touch touchOf(Tone tone, int octave) {
            return Touch.of(tone, semitoneOf(tone), octave);
        }

        public Touch touchOf(Button button) {
            return touchOf(button.tone, button.octave);
        }

        protected void stopHero() {
            if (hero == null) return;
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
                        Tone tone = Tone.valueOf(c);
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
                        Tone tone = Tone.valueOf(c);
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
            for (Tone tone : Tone.values()) {
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
                    updateLore(meta, sharpString.toString(), flatString.toString());
                });
        }
    }

    @RequiredArgsConstructor
    private static final class Hero {
        protected final Melody melody;
        protected Beat[] grid = new Beat[3];
        protected int gridCount = 0; // beats in grid
        protected int gridIndex; // 0-3
        protected int gridTime;
        protected int melodyIndex = 0;
        protected BukkitTask task;
        protected long lastTick = 0L;
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
        if (player.isSneaking()) return;
        openGui(player, item);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (Gui.of(player) != null) return;
        if (player.isSneaking()) return;
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
        Component guiDisplayName = text()
            .append(key.component)
            .append(displayName)
            .append(text(NOTE1 + NOTE2, GRAY))
            .build();
        Gui gui = new Gui()
            .size(size)
            .title(DefaultFont.guiBlankOverlay(size, BG, guiDisplayName));
        buildGui(gui, privateData);
        if (openEvent.isHeroMode()) {
            updateHeroScore(0, gui, privateData);
            gui.setItem(HERO_CLOCK, new ItemStack(Material.CLOCK, 64));
            privateData.hero.task = Bukkit.getScheduler().runTaskTimer(MytemsPlugin.getInstance(), () -> {
                    if (privateData.hero.lastTick == 0L) {
                        progressHeroGrid(player, gui, privateData);
                    }
                    long now = System.currentTimeMillis();
                    privateData.hero.lastTick = now;
                    privateData.hero.gridTime += 1;
                    if (privateData.hero.gridTime > GRID_TIME) {
                        PlayerBeatEvent.Action.MISS_BEAT.call(player, type, privateData.hero.melody);
                        updateHeroScore(0, gui, privateData);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 2.0f, 0.5f);
                        progressHeroGrid(player, gui, privateData);
                        return;
                    }
                    final int itemAmount = Math.max(1, 64 - ((privateData.hero.gridTime * 64) / GRID_TIME));
                    gui.getInventory().getItem(HERO_CLOCK).setAmount(itemAmount);
                }, 80L, 1L);
        }
        gui.onClose(evt -> {
                privateData.save(instrumentItemStack);
                new PlayerCloseMusicalInstrumentEvent(player, type).callEvent();
                privateData.stopHero();
            });
        gui.open(player);
    }

    protected void progressHeroGrid(Player player, Gui gui, GuiPrivateData privateData) {
        privateData.hero.melodyIndex += privateData.hero.gridCount;
        privateData.hero.gridTime = 0;
        privateData.hero.gridIndex = 0;
        privateData.hero.gridCount = 0;
        if (privateData.hero.melodyIndex >= privateData.hero.melody.getBeats().size()) {
            new PlayerMelodyCompleteEvent(player, type, privateData.hero.melody,
                                          privateData.hero.score, privateData.hero.maxScore).callEvent();
            privateData.stopHero();
            return;
        }
        gui.setItem(HERO_OFFSET - 1, Mytems.ARROW_RIGHT.createIcon(List.of(text("Play these notes", GRAY),
                                                                           text("in order.", GRAY))));
        for (int i = 0; i < 3; i += 1) {
            int index = privateData.hero.melodyIndex + i;
            Beat beat = index < privateData.hero.melody.getBeats().size()
                ? privateData.hero.melody.getBeats().get(index)
                : null;
            if (i == 2
                && beat != null
                && beat.tone == Tone.G
                && privateData.hero.grid[0] != null
                && privateData.hero.grid[0].tone == Tone.F
                && privateData.hero.grid[1] != null
                && privateData.hero.grid[1].tone == Tone.A) {
                beat = null;
            }
            privateData.hero.grid[i] = beat;
            if (beat == null) {
                gui.setItem(HERO_OFFSET + i * 3, null);
                gui.setItem(HERO_OFFSET + i * 3 + 1, null);
            } else {
                privateData.hero.gridCount += 1;
                beat = beat.cooked(privateData.hero.melody);
                gui.setItem(HERO_OFFSET + i * 3,
                            TONE_MYTEMS_MAP.get(beat.tone).createIcon(List.of(text(beat.toString()))));
                gui.setItem(HERO_OFFSET + i * 3 + 1,
                            beat.semitone != null && beat.semitone != Semitone.NATURAL
                            ? beat.semitone.mytems.createIcon()
                            : null);
            }
        }
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
        Touch touch = privateData.touchOf(button.tone, button.octave);
        if (privateData.hero != null) {
            // Shorten this in hero mode!
            return Items.text(TONE_MYTEMS_MAP.get(button.tone).createIcon(),
                              List.of(text(touch.toString(), COLOR)));
        }
        List<Component> text = new ArrayList<>();
        text.add(join(noSeparators(),
                      text(touch.toString(), COLOR),
                      space(), Mytems.MOUSE_LEFT));
        if (button.flat != null) {
            if (privateData.semitones.get(button.tone) == Semitone.FLAT) {
                text.add(join(noSeparators(),
                              text(button.tone.name() + Semitone.NATURAL.symbol, COLOR),
                              empty(), Mytems.SHIFT_KEY, Mytems.MOUSE_LEFT));
            } else {
                text.add(join(noSeparators(),
                              text(button.tone.name() + Semitone.FLAT.symbol, COLOR),
                              empty(), Mytems.SHIFT_KEY, Mytems.MOUSE_LEFT));
            }
        }
        if (button.sharp != null) {
            if (privateData.semitones.get(button.tone) == Semitone.SHARP) {
                text.add(join(noSeparators(),
                              text(button.tone.name() + Semitone.NATURAL.symbol, COLOR),
                              space(), Mytems.MOUSE_RIGHT));
            } else {
                text.add(join(noSeparators(),
                              text(button.tone.name() + Semitone.SHARP.symbol, COLOR),
                              space(), Mytems.MOUSE_RIGHT));
            }
        }
        text.add(text("Keyboard", COLOR)
                 .append(text(" 1...9", GRAY)));
        return Items.text(TONE_MYTEMS_MAP.get(button.tone).createIcon(), text);
    }

    protected ItemStack makeSemitoneButton(Button button, GuiPrivateData privateData) {
        Semitone semitone = privateData.semitoneOf(button.tone);
        List<Component> text = new ArrayList<>();
        Touch touch = privateData.touchOf(button.tone, button.octave);
        text.add(text(touch.toString(), COLOR));
        text.add(join(noSeparators(),
                      text("Sharpen ", COLOR),
                      Mytems.MOUSE_LEFT));
        text.add(join(noSeparators(),
                      text("Flatten", COLOR),
                      Mytems.MOUSE_RIGHT));
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
        ComponentLike actionBar = text()
            .append(key.component)
            .append(TONE_MYTEMS_MAP.get(touch.tone).component)
            .append(touch.semitone != Semitone.NATURAL ? touch.semitone.mytems.component : empty());
        player.sendActionBar(actionBar);
        player.getWorld().playSound(player.getLocation(), Sounds.of(type.instrument).sound, SoundCategory.PLAYERS,
                                    1.0f, Notes.of(touch.bukkitNote).pitch);
        Location particleLoc = player.getEyeLocation();
        particleLoc.add(particleLoc.getDirection().normalize().multiply(0.5));
        particleLoc.getWorld().spawnParticle(Particle.NOTE, particleLoc, 1, 0.125, 0.125, 0.125, 0.0);
        PluginPlayerEvent.Name.PLAY_NOTE.make(MytemsPlugin.getInstance(), player)
            .detail(Detail.NOTE, touch.bukkitNote)
            .detail(Detail.INSTRUMENT, type.instrument)
            .callEvent();
        new PlayerPlayInstrumentEvent(player, type, touch).callEvent();
        if (privateData.hero != null) {
            Beat beat = privateData.hero.grid[privateData.hero.gridIndex];
            if (beat != null && beat.countsAs(privateData.hero.melody, touch)) {
                PlayerBeatEvent.Action.HIT_BEAT.call(player, type, privateData.hero.melody, beat);
                updateHeroScore(1, gui, privateData);
                gui.setItem(HERO_OFFSET + privateData.hero.gridIndex * 3, null);
                gui.setItem(HERO_OFFSET + privateData.hero.gridIndex * 3 + 1, null);
                privateData.hero.gridIndex += 1;
                if (privateData.hero.gridIndex >= 3 || privateData.hero.grid[privateData.hero.gridIndex] == null) {
                    progressHeroGrid(player, gui, privateData);
                }
            } else {
                PlayerBeatEvent.Action.PLAY_OUT_OF_TUNE.call(player, type, privateData.hero.melody);
                updateHeroScore(-1, gui, privateData);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 2.0f, 0.5f);
            }
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

    @Override
    public MusicalInstrumentTag serializeTag(ItemStack itemStack) {
        MusicalInstrumentTag musicalInstrumentTag = new MusicalInstrumentTag();
        musicalInstrumentTag.load(itemStack, this);
        return musicalInstrumentTag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        MusicalInstrumentTag musicalInstrumentTag = Json.deserialize(serialized, MusicalInstrumentTag.class,
                                                                     MusicalInstrumentTag::new);
        musicalInstrumentTag.store(itemStack, this);
        return itemStack;
    }

    protected void updateLore(ItemMeta meta, String sharp, String flat) {
        List<Component> line = new ArrayList<>();
        if (sharp != null) {
            for (int i = 0; i < sharp.length(); i += 1) {
                try {
                    Tone tone = Tone.valueOf(sharp.substring(i, i + 1));
                    line.add(join(noSeparators(),
                                  TONE_MYTEMS_MAP.get(tone).component,
                                  Semitone.SHARP.mytems.component));
                } catch (IllegalArgumentException iae) { }
            }
        }
        if (flat != null) {
            for (int i = 0; i < flat.length(); i += 1) {
                try {
                    Tone tone = Tone.valueOf(flat.substring(i, i + 1));
                    line.add(join(noSeparators(),
                                  TONE_MYTEMS_MAP.get(tone).component,
                                  Semitone.FLAT.mytems.component));
                } catch (IllegalArgumentException iae) { }
            }
        }
        List<Component> text = new ArrayList<>(tooltip);
        if (!line.isEmpty()) {
            text.add(join(separator(space()), line));
        }
        Items.text(meta, text);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
