package com.cavetale.mytems.event.combat;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public final class BaseDamageModifier {
    private double base = 0.0;
    private final List<RawBonus> rawBoni = new ArrayList<>();
    private final List<FactorBonus> factorBoni = new ArrayList<>();
    private final List<FlatBonus> flatBoni = new ArrayList<>();

    public void addRawBonus(double value, String name) {
        rawBoni.add(new RawBonus(value, name));
    }

    public void addFactorBonus(double value, String name) {
        factorBoni.add(new FactorBonus(value, name));
    }

    public void addFlatBonus(double value, String name) {
        flatBoni.add(new FlatBonus(value, name));
    }

    public double computeResult() {
        double rawAddend = 0.0;
        for (RawBonus rawBonus : rawBoni) {
            rawAddend += rawBonus.value();
        }
        double factor = 1.0;
        for (FactorBonus factorBonus : factorBoni) {
            factor += factorBonus.value();
        }
        double addend = 0.0;
        for (FlatBonus flatBonus : flatBoni) {
            addend += flatBonus.value();
        }
        return Math.max(0, Math.max(0, (base + rawAddend) * factor) + addend);
    }

    private static String fmt(double in) {
        return String.format("%.2f", in);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(fmt(base));
        for (var it : rawBoni) {
            sb.append(" r" + fmt(it.value()) + "|" + it.name());
        }
        for (var it : factorBoni) {
            sb.append(" " + fmt(it.value()) + "%" + it.name());
        }
        for (var it : flatBoni) {
            sb.append(" " + fmt(it.value()) + "+" + it.name());
        }
        return sb.toString();
    }

    private record RawBonus(double value, String name) { }
    private record FactorBonus(double value, String name) { }
    private record FlatBonus(double value, String name) { }
}
