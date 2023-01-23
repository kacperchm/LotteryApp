package pl.lotto.resultChecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ResultDto(
        String ticketID,
        List<Integer> playerNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate,
        List<Integer> winningNumbers,
        int correctNumbers,
        String message
) {
}
