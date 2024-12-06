package com.cavetale.mytems.item.gem;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;

@Data
@EqualsAndHashCode(callSuper = true)
public final class GemTag extends MytemTag {
    protected int foobar;

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && foobar == 0;
    }

    @Override
    public void load(Mytems mytems, ItemStack itemStack) {
        super.load(mytems, itemStack);
        // TODO
    }

    @Override
    public void store(Mytems mytems, ItemStack itemStack) {
        super.store(mytems, itemStack);
        // TODO
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof GemTag otherGemTag
            && otherGemTag.foobar == foobar;
    }
}
