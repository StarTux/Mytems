package com.cavetale.mytems.item.coin;

import com.cavetale.core.connect.ServerCategory;
import com.cavetale.core.event.item.PlayerAbsorbItemEvent;
import com.cavetale.core.money.Money;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.util.Tags;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
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
        this.rawDisplayName = Text.toCamelCase(denomination.name()) + " Coin";
        this.displayName = text(rawDisplayName, denomination.color);
    }

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(new Component[] {
                            displayName,
                            text("Worth " + denomination.value + " Coins", denomination.color),
                            Component.empty(),
                            join(noSeparators(),
                                 text("pick up ", denomination.color),
                                 text("Deposit to", GRAY)),
                            text("your account", GRAY),
                        }));
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
            message = amount == 1
                ? "Pick up " + rawDisplayName
                : "Pick up " + amount + " " + rawDisplayName + "s";
        }
        Money.get().give(player.getUniqueId(),
                         (double) value,
                         MytemsPlugin.getInstance(),
                         message);
    }

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###.00", new DecimalFormatSymbols(Locale.US));

    static {
        MONEY_FORMAT.setParseBigDecimal(true);
    }

    public static Component format(double amount) {
        String format = MONEY_FORMAT.format(amount);
        if (format.endsWith(".00")) format = format.substring(0, format.length() - 3);
        if (format.isEmpty()) format = "0";
        Denomination denomination = Denomination.COPPER;
        for (Denomination deno : Denomination.values()) {
            if ((double) deno.value <= amount) {
                denomination = deno;
            }
        }
        return join(noSeparators(), denomination.mytems.component, text(format))
            .color(denomination.color)
            .hoverEvent(showText(join(separator(newline()),
                                      text(format + " Coins", denomination.color),
                                      text("/money", GRAY))))
            .clickEvent(runCommand("/money"));
    }
}
