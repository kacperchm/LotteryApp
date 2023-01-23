package pl.lotto.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DrawnNumbers(
        String drawId,
        LocalDateTime drawDate,
        List<Integer> drawNumbers
) {
    public DrawnNumbers(LocalDateTime drawDate, List<Integer> drawNumbers) {
        this(null, drawDate, drawNumbers);
    }
}
