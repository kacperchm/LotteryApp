package pl.lotto.numberreceiver;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import static java.time.temporal.TemporalAdjusters.next;

public record LotteryTicketDto(
        String ticketID,
        List<Integer> lotteryNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate
) {
//
//    public static LocalDateTime findFirstSaturday(LocalDateTime date) {
//        while (date.getNano() != 0) {
//            int nanoSetter = 0 - date.getNano();
//            date = date.plusNanos(nanoSetter);
//        }
//
//        while (date.getSecond() != 0) {
//            int secondSetter = 0 - date.getSecond();
//            date = date.plusSeconds(secondSetter);
//        }
//
//        while (date.getMinute() != 0) {
//            int minuteSetter = 0 - date.getMinute();
//            date = date.plusMinutes(minuteSetter);
//        }
//
//        while (date.getHour() != 20) {
//            int hourSetter = 20 - date.getHour();
//            date = date.plusHours(hourSetter);
//        }
//
//        while (date.getDayOfWeek()!= DayOfWeek.SATURDAY) {
//            date = date.plusDays(1);
//        }
//
//        return date;
//    }


}

