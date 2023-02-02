package pl.lotto.resultChecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Builder
record Result(
        String ticketID,
        List<Integer> playerNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate,
        List<Integer> winningNumbers,
        int correctNumbers,
        String message
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return correctNumbers == result.correctNumbers && Objects.equals(ticketID, result.ticketID) && Objects.equals(playerNumbers, result.playerNumbers) && Objects.equals(creationTicketDate, result.creationTicketDate) && Objects.equals(drawDate, result.drawDate) && Objects.equals(winningNumbers, result.winningNumbers) && Objects.equals(message, result.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID, playerNumbers, creationTicketDate, drawDate, winningNumbers, correctNumbers, message);
    }
}
