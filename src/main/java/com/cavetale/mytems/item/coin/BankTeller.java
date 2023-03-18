package com.cavetale.mytems.item.coin;

import com.cavetale.core.event.item.PlayerReceiveItemsEvent;
import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.money.Money;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.*;

public final class BankTeller {
    public static void open(Player player) {
        final int size = 5 * 9;
        Gui gui = new Gui().size(size);
        GuiOverlay.Builder builder = GuiOverlay.BLANK.builder(size, color(0xAAAAFF))
            .layer(GuiOverlay.TITLE_BAR, color(0xFFFF00))
            .title(text("Bank Teller", BLACK, BOLD));
        int index = 0;
        int y = 2;
        int dx = 0;
        for (Denomination deno : Denomination.values()) {
            if (!deno.regularCurrency) continue;
            final ItemStack coinItem = deno.mytems.getMytem().createItemStack();
            final int x = dx++ + 2;
            Consumer<InventoryClickEvent> clickHandler = click -> {
                if (!click.isLeftClick()) return;
                int stackSize = gui.getItem(x + y * 9).getAmount();
                double amount = (double) (deno.value * stackSize);
                String msg = "Withdraw " + stackSize + " " + toCamelCase(" ", deno) + " Coin" + (stackSize == 1 ? "" : "s");
                if (!Money.get().take(player.getUniqueId(), amount, MytemsPlugin.getInstance(), msg)) {
                    player.sendMessage(join(noSeparators(), text("You do not have ", RED), Coin.format(amount)));
                    fail(player);
                } else {
                    PlayerReceiveItemsEvent event = new PlayerReceiveItemsEvent(player, List.of(deno.mytems.createItemStack(stackSize)));
                    event.giveItems();
                    event.callEvent();
                    event.dropItems();
                    player.sendMessage(join(noSeparators(), text("You withdrew "), Coin.format(amount)));
                    click(player);
                }
            };
            gui.setItem(x, y, coinItem, clickHandler);
            ItemStack plusItem = Mytems.PLUS_BUTTON.createIcon(List.of(join(noSeparators(), text("More "), deno.mytems),
                                                                       join(noSeparators(), Mytems.MOUSE_LEFT, text(" +1", GRAY)),
                                                                       join(noSeparators(), Mytems.SHIFT_KEY, Mytems.MOUSE_LEFT, text(" +10", GRAY))));
            gui.setItem(x, y - 1, plusItem, click -> {
                    if (!click.isLeftClick()) return;
                    ItemStack theCoinItem = gui.getItem(x + y * 9);
                    if (theCoinItem.getAmount() < 64) {
                        theCoinItem.setAmount(Math.min(64, theCoinItem.getAmount() + (click.isShiftClick() ? 10 : 1)));
                        gui.setItem(x, y, theCoinItem, clickHandler);
                        click(player);
                    } else {
                        fail(player);
                    }
                });
            ItemStack minusItem = Mytems.MINUS_BUTTON.createIcon(List.of(join(noSeparators(), text("Fewer "), deno.mytems),
                                                                         join(noSeparators(), Mytems.MOUSE_LEFT, text(" -1", GRAY)),
                                                                         join(noSeparators(), Mytems.SHIFT_KEY, Mytems.MOUSE_LEFT, text(" -10", GRAY))));
            gui.setItem(x, y + 1, minusItem, click -> {
                    if (!click.isLeftClick()) return;
                    ItemStack theCoinItem = gui.getItem(x + y * 9);
                    if (theCoinItem.getAmount() > 1) {
                        theCoinItem.setAmount(Math.max(1, theCoinItem.getAmount() - (click.isShiftClick() ? 10 : 1)));
                        gui.setItem(x, y, theCoinItem, clickHandler);
                        click(player);
                    } else {
                        fail(player);
                    }
                });
        }
        gui.title(builder.build());
        gui.open(player);
    }

    private static void fail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.MASTER, 0.5f, 0.5f);
    }

    private static void click(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 1.0f);
    }

    private BankTeller() { }
}
