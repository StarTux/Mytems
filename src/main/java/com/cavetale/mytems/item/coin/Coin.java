package com.cavetale.mytems.item.coin;

import com.cavetale.core.connect.ServerCategory;
import com.cavetale.core.event.item.PlayerAbsorbItemEvent;
import com.cavetale.core.money.Money;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.util.Tags;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Coin implements Mytem {
    private final Mytems key;
    private final Denomination denomination;
    private final String rawDisplayName;
    private final Component displayName;
    private ItemStack prototype;

    public Coin(final Mytems mytems) {
        this.key = mytems;
        this.denomination = Denomination.of(key);
        this.rawDisplayName = toCamelCase(" ", denomination.mytems);
        this.displayName = text(rawDisplayName, denomination.color);
    }

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text("Worth " + formatHelper(denomination.value) + " Coins", denomination.color),
                                      empty(),
                                      textOfChildren(Mytems.MOUSE_RIGHT, text(" or pick up", GRAY)),
                                      textOfChildren(text("to deposit ", GRAY), key, text(" to", GRAY)),
                                      text("your account", GRAY)));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) {
            return;
        }
        if (!ServerCategory.current().isSurvival()) return;
        Item item = event.getItem();
        if (item.isDead()) return;
        event.setCancelled(true);
        ItemStack itemStack = item.getItemStack();
        if (itemStack.getAmount() < 1) return;
        new PlayerAbsorbItemEvent(player, item, itemStack.getAmount()).callEvent();
        item.remove();
        int amount = itemStack.getAmount();
        int value = denomination.value * amount;
        String message = Tags.getString(itemStack.getItemMeta().getPersistentDataContainer(),
                                        MytemsPlugin.namespacedKey("message"));
        if (message == null) {
            message = "Pick up " + rawDisplayName;
        }
        Money.get().give(player.getUniqueId(),
                         (double) value,
                         MytemsPlugin.getInstance(),
                         message);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack itemStack) {
        if (player.isSneaking()) return;
        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) {
            return;
        }
        if (!ServerCategory.current().isSurvival()) return;
        event.setCancelled(true);
        event.setUseItemInHand(Event.Result.DENY);
        event.setUseInteractedBlock(Event.Result.DENY);
        if (itemStack.getAmount() < 1) return;
        itemStack.subtract(1);
        Money.get().give(player.getUniqueId(),
                         (double) denomination.value,
                         MytemsPlugin.getInstance(),
                         "Use " + rawDisplayName);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 1.0f, 1.0f);
    }

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###.00", new DecimalFormatSymbols(Locale.US));

    static {
        MONEY_FORMAT.setParseBigDecimal(true);
    }

    private static String formatHelper(double amount) {
        String format = MONEY_FORMAT.format(amount);
        if (format.endsWith(".00")) format = format.substring(0, format.length() - 3);
        if (format.isEmpty()) format = "0";
        return format;
    }

    public static Component format(double amount) {
        final Denomination denomination = Denomination.ofAmount(amount);
        final String format = formatHelper(amount);
        final TextColor color = amount >= 0 ? denomination.color : DARK_RED;
        return textOfChildren(denomination.mytems.component, text(format, color));
    }

    public static Component formatAnimated(double amount) {
        final Denomination denomination = Denomination.ofAmount(amount);
        final String format = formatHelper(amount);
        final TextColor color = amount >= 0 ? denomination.color : DARK_RED;
        return textOfChildren(denomination.mytems.getCurrentAnimationFrame(), text(format, color));
    }

    public static ClickEvent clickEvent() {
        return runCommand("/money");
    }

    public static HoverEvent hoverEvent(double amount) {
        final Denomination denomination = Denomination.ofAmount(amount);
        final String format = formatHelper(amount);
        final TextColor color = amount >= 0 ? denomination.color : DARK_RED;
        return showText(join(separator(newline()),
                             text(format + " Coins", color),
                             text("/money", GRAY)));
    }
}
