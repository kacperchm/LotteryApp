package pl.lotto.numberReceiver.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record LotteryTicketDto(
        String ticketID,
        List<Integer> lotteryNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate
) {
}

