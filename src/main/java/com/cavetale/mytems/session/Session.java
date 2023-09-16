package com.cavetale.mytems.session;

import com.cavetale.core.event.hud.PlayerHudEvent;
import com.cavetale.core.event.hud.PlayerHudPriority;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.Equipped;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.gear.Slot;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;

@Getter
public final class Session {
    protected final MytemsPlugin plugin;
    protected final UUID uuid;
    protected final String name;
    protected final List<Cooldown> cooldowns = new ArrayList<>();
    public static final long MILLIS_PER_TICK = 50L;
    private int equipmentUpdateTicks = 0;
    protected final Equipment equipment = new Equipment(); // Updated every tick
    protected Flying flying = new Flying(this);
    protected Attributes attributes = new Attributes(this);
    protected final Favorites favorites = new Favorites();
    private boolean hidingPlayers;

    public Session(final MytemsPlugin plugin, final Player player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Session enable(Player player) {
        attributes.enable();
        updateEquipment(player);
        return this;
    }

    public void disable(Player player) {
        flying.disable(player);
        attributes.disable(player);
    }

    public void tick(Player player) {
        if (equipmentUpdateTicks > 0) {
            equipmentUpdateTicks -= 1;
            if (equipmentUpdateTicks == 0) {
                updateEquipment(player);
            }
        }
        flying.tick(player);
        for (SetBonus setBonus : equipment.getSetBonuses()) {
            int has = equipment.countSetItems(setBonus.getItemSet());
            setBonus.tick(player, has);
            for (var it : setBonus.getPotionEffects(has)) {
                player.addPotionEffect(it);
            }
        }
    }

    /**
     * Update all GearItems in the player's inventory and apply or
     * remove effects as needed.
     */
    private void updateEquipment(Player player) {
        equipment.clear();
        equipment.loadPlayer(player);
        if (!equipment.isEmpty()) {
            for (Equipped equipped : equipment.getItems()) {
                Items.text(equipped.itemStack, equipped.gearItem.createTooltip(equipment, equipped));
            }
        }
        PlayerInventory inventory = player.getInventory();
        int held = inventory.getHeldItemSlot();
        for (int i = 0; i < 36; i += 1) {
            if (i == held) continue;
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) updateLooseItem(itemStack);
        }
        // Update invalid equipment slots (this boots in hand)
        for (Slot slot : Slot.values()) {
            if (equipment.containsSlot(slot)) continue;
            ItemStack itemStack = inventory.getItem(slot.bukkitEquipmentSlot);
            if (itemStack != null) updateLooseItem(itemStack);
        }
        attributes.update(player);
    }

    private void updateLooseItem(ItemStack itemStack) {
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        if (!(mytems.getMytem() instanceof GearItem gearItem)) return;
        Items.text(itemStack, gearItem.createTooltip());
    }

    public void equipmentDidChange() {
        equipmentUpdateTicks = 1;
    }

    protected void onPlayerHud(PlayerHudEvent event) {
        if (cooldowns.isEmpty()) return;
        cleanCooldowns();
        if (cooldowns.isEmpty()) return;
        List<Component> lines = new ArrayList<>();
        lines.add(text(tiny("cooldowns"), GRAY));
        List<Component> line = new ArrayList<>();
        int total = 0;
        int lineLength = 0;
        for (Cooldown cooldown : cooldowns) {
            String text = subscript("" + cooldown.getDuration().toSeconds());
            int length = (text.length() * 2 / 3) + 1;
            int addLineLength = length + (lineLength == 0 ? 0 : 1);
            if (lineLength + addLineLength > Text.ITEM_LORE_WIDTH) {
                lines.add(join(separator(space()), line));
                line.clear();
                lineLength = 0;
            }
            line.add(textOfChildren(cooldown.getIcon(), text(text, GRAY)));
            lineLength += addLineLength;
            total += 1;
        }
        if (!line.isEmpty()) {
            lines.add(join(separator(space()), line));
        }
        if (total == 0) return;
        event.sidebar(PlayerHudPriority.LOWEST, lines);
    }

    public void setHidingPlayers(boolean value) {
        if (hidingPlayers == value) return;
        Player player = getPlayer();
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.equals(player)) continue;
            if (value) {
                player.hidePlayer(plugin, other);
            } else {
                player.showPlayer(plugin, other);
            }
        }
        this.hidingPlayers = value;
    }

    private void cleanCooldowns() {
        long now = System.currentTimeMillis();
        cooldowns.removeIf(it -> it.end < now);
    }

    public Cooldown cooldown(Mytems mytems) {
        for (Cooldown cooldown : cooldowns) {
            if (cooldown.mytems == mytems) return cooldown;
        }
        Cooldown cooldown = new Cooldown(mytems);
        cooldowns.add(cooldown);
        return cooldown;
    }

    public Duration getCooldown(Mytems mytems) {
        cleanCooldowns();
        for (Cooldown cooldown : cooldowns) {
            if (cooldown.mytems == mytems) return cooldown.getDuration();
        }
        return Duration.ZERO;
    }

    public boolean isOnCooldown(Mytems mytems) {
        for (Cooldown cooldown : cooldowns) {
            if (cooldown.mytems == mytems) {
                return cooldown.end >= System.currentTimeMillis();
            }
        }
        return false;
    }

    public static Session of(Player player) {
        return sessionOf(player);
    }
}
