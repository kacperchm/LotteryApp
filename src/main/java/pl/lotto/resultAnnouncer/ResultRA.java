package pl.lotto.resultAnnouncer;

import java.time.LocalDateTime;
import java.util.List;

public record ResultRA(
        String ticketID,
        List<Integer> playerNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate,
        List<Integer> winningNumbers,
        int correctNumbers,
        String message
) {
}
