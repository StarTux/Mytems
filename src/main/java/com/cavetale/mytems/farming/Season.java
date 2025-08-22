package com.cavetale.mytems.farming;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import lombok.Getter;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;

@Getter
public enum Season {
    SPRING,
    SUMMER,
    FALL,
    WINTER,
    ;

    private static Season currentSeason = Season.SPRING;
    private static Instant nextWeekStart = Instant.EPOCH;
    private final String displayName = toCamelCase(" ", this);

    private static void computeSeason() {
        final LocalDate startDate = LocalDate.of(2025, 1, 1);
        final LocalDate currentDate = LocalDate.now();
        final long weeks = ChronoUnit.WEEKS.between(startDate, currentDate);
        currentSeason = Season.values()[(int) (weeks % 4L)];
        final LocalDate nextWeekStartDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        nextWeekStart = nextWeekStartDate.atStartOfDay(ZoneId.of("UTC-11")).toInstant();
        mytemsPlugin().getLogger().info("[Season] New season: " + currentSeason + " until " + nextWeekStart);
    }

    public static Season getCurrentSeason(Instant now) {
        if (now.isAfter(nextWeekStart)) {
            computeSeason();
        }
        return currentSeason;
    }

    public static Instant getNextWeekStart(Instant now) {
        if (now.isAfter(nextWeekStart)) {
            computeSeason();
        }
        return nextWeekStart;
    }
}
