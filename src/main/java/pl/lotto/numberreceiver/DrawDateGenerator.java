package pl.lotto.numberreceiver;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.*;

public class DrawDateGenerator {
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
