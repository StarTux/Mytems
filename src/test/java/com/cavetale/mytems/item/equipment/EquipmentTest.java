package com.cavetale.mytems.item.equipment;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.junit.Test;

/**
 * Rewrite the EquipmentType and corresponding Mytems enum.
 */
public final class EquipmentTest {
    @Test
    public void test() {
        File folder = new File("target/equipment");
        folder.mkdirs();
        try (PrintStream outMytems = new PrintStream(new File(folder, "Mytems.java"));
             PrintStream outEquipment = new PrintStream(new File(folder, "EquipmentType.java"))) {
            for (EquipmentType type : EquipmentType.values()) {
                final String name = type.name();
                final Mytems mytems = type.mytems;
                final Material material = mytems.material;
                final int textureId = mytems.customModelData;
                final MytemsCategory category = mytems.category;
                String mytemsLine = "    " + type.name() + "(EquipmentItem::new"
                    + ", " + material.name()
                    + ", " + textureId
                    + ", " + category.name() + "),";
                outMytems.println(mytemsLine);
                final EquipmentVariant variant = type.variant;
                final EquipmentItemSet itemSet = type.itemSet;
                final EquipmentElement element = type.element;
                final String elementString = element != null
                    ? "EquipmentElement." + element
                    : "(EquipmentElement) null";
                final String colorString;
                if (type.textColor == null) {
                    colorString = "(TextColor) null";
                } else if (type.textColor instanceof NamedTextColor namedTextColor) {
                    colorString = namedTextColor.toString().toUpperCase();
                } else {
                    colorString = "color(0x" + Integer.toHexString(type.textColor.value()).toUpperCase() + ")";
                }
                final String displayName = type.displayName;
                String equipmentLine = "    " + type.name() + "(Mytems." + mytems.name()
                    + ", EquipmentVariant." + variant
                    + ", EquipmentItemSet." + itemSet
                    + ", " + elementString
                    + ", " + colorString
                    + ", \"" + displayName + "\"),";
                outEquipment.println(equipmentLine);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
