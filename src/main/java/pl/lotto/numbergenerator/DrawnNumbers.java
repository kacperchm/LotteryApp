package pl.lotto.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DrawnNumbers(
        Long drawId,
        LocalDateTime drawDate,
        List<Integer> drawNumbers
) {
}
