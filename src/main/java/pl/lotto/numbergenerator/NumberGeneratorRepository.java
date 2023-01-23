package pl.lotto.numbergenerator;


import java.time.LocalDateTime;
import java.util.Optional;

public interface NumberGeneratorRepository {

        DrawnNumbers save(DrawnNumbers drawnNumbers);

        Optional<DrawnNumbers> findDrawnNumbersByDrawDate(LocalDateTime drawDate);
}
