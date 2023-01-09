package pl.lotto.numbergenerator;


import java.time.LocalDateTime;

public interface NumberGeneratorRepository {

        DrawnNumbers save(DrawnNumbers drawnNumbers);

        DrawnNumbers findDrawnNumbersByDrawDate(LocalDateTime drawDate);
}
