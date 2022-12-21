package pl.lotto.numberreceiver;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import static java.time.temporal.TemporalAdjusters.next;

class DrawDateGenerator {
    LocalDateTime findFirstSaturday(LocalDateTime dateOfCreationTicket) {
        return dateOfCreationTicket.with(next(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0);
    }
}
