package pl.lotto.numberReceiver;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record LotteryTicket(
        String ticketID,
        List<Integer> lotteryNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate
) {
}

