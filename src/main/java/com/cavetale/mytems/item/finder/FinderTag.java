package com.cavetale.mytems.item.finder;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemMenu;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import com.cavetale.mytems.session.Session;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.STRIKETHROUGH;

public abstract class FinderTag extends UpgradableItemTag {
    public static final class Structure extends FinderTag {
        @Override
        public FinderTier getUpgradableItemTier() {
            return FinderTier.STRUCTURE;
        }
    }

    public static final class Secret extends FinderTag {
        @Override
        public FinderTier getUpgradableItemTier() {
            return FinderTier.SECRET;
        }
    }

    public static final class Mystic extends FinderTag {
        @Override
        public FinderTier getUpgradableItemTier() {
            return FinderTier.MYSTIC;
        }
    }

    public static final class Master extends FinderTag {
        @Override
        public FinderTier getUpgradableItemTier() {
            return FinderTier.MASTER;
        }
    }

    @Override
    public abstract FinderTier getUpgradableItemTier();

    @Override
    public final FinderItem getUpgradableItem() {
        return FinderItem.finderItem();
    }

    @Override
    public final List<Component> getTooltipDescription() {
        return List.of(text("Search structures", GRAY),
                       text("with this Finder.", GRAY),
                       empty(),
                       textOfChildren(text("Once located, ", GRAY), Mytems.MOUSE_LEFT),
                       text("the structure to", GRAY),
                       text("earn " + tiny("xp") + " and " + tiny("lv") + " up.", GRAY),
                       empty(),
                       textOfChildren(text(tiny("range"), GRAY), text(" " + getRange(), WHITE)));
    }

    @Override
    public final void onMenuCreated(UpgradableItemMenu menu) {
        menu.getGui().setItem(0, Mytems.MAGNIFYING_GLASS.createIcon(List.of(text("New Search...", GRAY))), click -> {
                if (!click.isLeftClick()) return;
                final Player player = menu.getPlayer();
                final Session session = Session.of(player);
                final long cooldown = session.getCooldown(Mytems.STRUCTURE_FINDER).toSeconds();
                if (cooldown > 0) {
                    player.sendActionBar(text("Cooldown " + cooldown + "s", RED));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1f, 1f);
                    return;
                }
                session.cooldown(Mytems.STRUCTURE_FINDER).duration(Duration.ofSeconds(5)).icon(getUpgradableItemTier().getMytems());
                new FoundMenu(player, menu.getItemStack(), this).open();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            });
        final ItemStack infoItem = Mytems.INFO_BUTTON.createIcon(getFindableStructuresInfo());
        menu.getGui().setItem(8, infoItem);
    }

    @Override
    public final boolean shouldAutoPlaceArrows() {
        return true;
    }

    public final List<FoundType> getFindableStructures() {
        final List<FoundType> result = new ArrayList<>();
        for (FoundType foundType : FoundType.values()) {
            if (foundType.isDisabled()) {
                continue;
            }
            final FinderStat requiredStat = foundType.getRequiredStat();
            if (requiredStat == null || getEffectiveUpgradeLevel(requiredStat) > 0) {
                result.add(foundType);
            }
        }
        return result;
    }

    public final int getRange() {
        final int level = getUpgradeLevel(FinderStat.RANGE);
        if (level == 0) {
            return 100;
        }
        return ((FinderStat.RangeLevel) FinderStat.RANGE.getLevel(level)).getRange();
    }

    public final List<Component> getFindableStructuresInfo() {
        List<Component> result = new ArrayList<>();
        final List<FoundType> findable = getFindableStructures();
        final FinderTier thisTier = getUpgradableItemTier();
        result.add(text(thisTier.getDisplayName() + " can locate:", thisTier.getColor()));
        for (FoundType foundType : FoundType.values()) {
            if (foundType.isDisabled()) {
                continue;
            }
            final FinderTier tier = foundType.getRequiredTier();
            if (findable.contains(foundType)) {
                result.add(textOfChildren(tier.getMytems(), space(), text(foundType.getDisplayName(), tier.getColor())));
            } else {
                result.add(textOfChildren(tier.getMytems(), space(), text(foundType.getDisplayName(), tier.getColor(), STRIKETHROUGH)));
            }
        }
        return result;
    }
}
