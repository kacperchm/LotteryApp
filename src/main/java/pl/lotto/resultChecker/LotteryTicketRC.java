package pl.lotto.resultChecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LotteryTicketRC(
        String ticketID,
        List<Integer> lotteryNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate
) {
}

