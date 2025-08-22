package com.cavetale.mytems.farming;

public interface CropTag {
    static CropYield cropYield(int amount) {
        return new CropYield(amount, amount);
    }

    static CropYield cropYield(int min, int max) {
        return new CropYield(min, max);
    }

    static GrowthTime growthTime(int days) {
        return new GrowthTime(days);
    }

    static RegrowthTime regrowthTime(int days) {
        return new RegrowthTime(days);
    }

    CropTag PERENNIAL = new CropTag() { };

    record CropYield(int min, int max) implements CropTag { }

    record GrowthTime(int days) implements CropTag { }

    record RegrowthTime(int days) implements CropTag { }
}
