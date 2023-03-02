package pl.lotto.numberGenerator;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("drawnNumbers")
public record DrawnNumbers(
        @Id
        String drawId,
        LocalDateTime drawDate,
        List<Integer> drawNumbers
) {
}
