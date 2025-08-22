package com.cavetale.mytems.farming;

import java.time.Duration;
import lombok.Getter;

@Getter
public enum WateringNeed {
    NONE(0),
    THIRSTY(1),      // Daily or every other day
    MODERATE(2),     // Every 2–3 days
    HARDY(4)         // Every 4–5 days or less
    ;

    private final int days;
    private final Duration drinkInterval;

    WateringNeed(final int days) {
        this.days = days;
        this.drinkInterval = Duration.ofHours(days);
    }
}
