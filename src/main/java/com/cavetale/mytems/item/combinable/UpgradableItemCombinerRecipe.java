package com.cavetale.mytems.item.combinable;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItem;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import com.cavetale.mytems.item.upgradable.UpgradableItemTier;
import java.util.ArrayList;
import java.util.List;
import lombok.Value;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.item.hastypickaxe.HastyPickaxeItem.hastyPickaxeItem;

@Value
public final class UpgradableItemCombinerRecipe implements ItemCombinerRecipe {
    private final Mytems inputMytems;
    private final Mytems outputMytems;
    private static List<UpgradableItemCombinerRecipe> all;

    @Override
    public boolean doesAcceptInput1(ItemStack input1) {
        return input1 != null
            && input1.getAmount() == 1
            && Mytems.forItem(input1) == inputMytems;

    }

    @Override
    public boolean doesAcceptInput2(ItemStack input2) {
        return input2 != null
            && input2.getAmount() == 1
            && Mytems.forItem(input2) == inputMytems;
    }

    @Override
    public ItemStack combine(ItemStack input1, ItemStack input2) {
        final ItemStack result = outputMytems.createItemStack();
        final MytemTag inputTag1 = inputMytems.getMytem().serializeTag(input1);
        final MytemTag inputTag2 = inputMytems.getMytem().serializeTag(input2);
        final MytemTag resultTag = outputMytems.getMytem().serializeTag(result);
        if (inputTag1 instanceof UpgradableItemTag upgradable1
            && inputTag2 instanceof UpgradableItemTag upgradable2
            && resultTag instanceof UpgradableItemTag upgradable3) {
            upgradable3.setTotalXp(upgradable1.getTotalXp() + upgradable2.getTotalXp());
            upgradable3.store(result);
        }
        return result;
    }

    @Override
    public String toString() {
        return inputMytems + " -> " + outputMytems;
    }

    public static List<UpgradableItemCombinerRecipe> getAll() {
        if (all != null) {
            return all;
        }
        all = new ArrayList<>();
        addChain(hastyPickaxeItem());
        addChain(Mytems.MOBSLAYER, Mytems.MOBSLAYER2, Mytems.MOBSLAYER3);
        addChain(Mytems.BINGO_BUKKIT, Mytems.GOLD_BINGO_BUKKIT, Mytems.DIAMOND_BINGO_BUKKIT);
        addChain(Mytems.COLORFALL_HOURGLASS, Mytems.MOONLIGHT_HOURGLASS);
        addChain(Mytems.COLORFALL_HOURGLASS, Mytems.ATMOSPHERE_HOURGLASS);
        addChain(Mytems.COLORFALL_HOURGLASS, Mytems.CLIMATE_HOURGLASS);
        addChain(Mytems.STRUCTURE_FINDER, Mytems.SECRET_FINDER, Mytems.MYSTIC_FINDER);
        addChain(Mytems.DEFLECTOR_SHIELD, Mytems.RETURN_SHIELD, Mytems.VENGEANCE_SHIELD);
        addChain(Mytems.COPPER_SPLEEF_SHOVEL, Mytems.IRON_SPLEEF_SHOVEL, Mytems.GOLDEN_SPLEEF_SHOVEL);
        addChain(Mytems.IRON_SCYTHE, Mytems.GOLDEN_SCYTHE);
        addChain(Mytems.EMPTY_WATERING_CAN, Mytems.EMPTY_GOLDEN_WATERING_CAN);
        all = List.copyOf(all);
        return all;
    }

    private static void addChain(Mytems... mytems) {
        for (int i = 0; i < mytems.length - 1; i += 1) {
            all.add(new UpgradableItemCombinerRecipe(mytems[i], mytems[i + 1]));
        }
    }

    private static void addChain(UpgradableItem upgradable) {
        List<? extends UpgradableItemTier> tierList = upgradable.getTiers();
        for (int i = 0; i < tierList.size() - 1; i += 1) {
            all.add(new UpgradableItemCombinerRecipe(tierList.get(i).getMytems(), tierList.get(i + 1).getMytems()));
        }
    }
}
