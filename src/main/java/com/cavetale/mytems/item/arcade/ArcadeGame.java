package com.cavetale.mytems.item.arcade;

import com.cavetale.mytems.Mytems;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public enum ArcadeGame {
    TIC_TAC_TOE(Mytems.CAVEBOY_TICTACTOE, ArcadeTicTacToe::start, text("Tic Tac Toe", LIGHT_PURPLE)),
    TNT_SWEEPER(Mytems.CAVEBOY_TNTSWEEP, ArcadeTNTSweeper::start, text("TNT Sweeper", BLUE)),
    MEMORY(Mytems.CAVEBOY_MEMORY, ArcadeMemory::start, text("Memory", GRAY)),
    GEMS(Mytems.CAVEBOY_GEMS, ArcadeGems::start, text("Gems", AQUA)),
    ;

    public final Mytems mytems;
    public final Consumer<Player> startMethod;
    public final Component displayName;

    public static ArcadeGame of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
