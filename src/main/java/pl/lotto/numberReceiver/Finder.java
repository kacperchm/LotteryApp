package pl.lotto.numberReceiver;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

class Finder {
    LocalDateTime findFirstSaturday(LocalDateTime dateOfCreationTicket) {
        return dateOfCreationTicket.with(next(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    LocalDateTime findLastSaturday(LocalDateTime lotteryDate) {
        return lotteryDate.with(previousOrSame(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }
}
