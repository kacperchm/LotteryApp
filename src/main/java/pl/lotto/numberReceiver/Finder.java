package pl.lotto.numberReceiver;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

class Finder {
    LocalDateTime findFirstSaturday(LocalDateTime dateOfCreationTicket) {
        return dateOfCreationTicket.with(nextOrSame(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }
}
