package pl.lotto.numberGenerator;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Document("drawnNumbers")
public record DrawnNumbers(
        @Id
        String drawId,
        LocalDateTime drawDate,
        List<Integer> drawNumbers
) {
    public DrawnNumbers(LocalDateTime drawDate, List<Integer> drawNumbers) {
        this(null, drawDate, drawNumbers);
    }
}
