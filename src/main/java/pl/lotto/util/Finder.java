package pl.lotto.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.*;

public class Finder {
    public static LocalDateTime findFirstSaturday(LocalDateTime dateOfCreationTicket) {
        return dateOfCreationTicket.with(next(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0);
        }

    public static LocalDateTime findLastSaturday(LocalDateTime lotteryDate) {
        return lotteryDate.with(previousOrSame(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0);
        }
    }
