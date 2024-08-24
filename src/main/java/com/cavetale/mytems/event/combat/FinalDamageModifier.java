package com.cavetale.mytems.event.combat;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * This modifier uses the absorption damage as its base, in the hopes
 * that modifying it will not interfere with anything.
 */
@Data
public final class FinalDamageModifier {
    private double base = 0.0;
    private final List<FlatDamage> flatDamages = new ArrayList<>();

    public void addFlatDamage(double value, String name) {
        flatDamages.add(new FlatDamage(value, name));
    }

    /**
     * Applied after vanilla armor.
     */
    public double computeResult() {
        double result = base;
        for (FlatDamage flatDamage : flatDamages) {
            result += flatDamage.value();
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(fmt(base));
        for (var it : flatDamages) {
            sb.append(" " + fmt(it.value()) + "+" + it.name());
        }
        return sb.toString();
    }

    private static String fmt(double in) {
        return String.format("%.2f", in);
    }

    private record FlatDamage(double value, String name) { }
}
