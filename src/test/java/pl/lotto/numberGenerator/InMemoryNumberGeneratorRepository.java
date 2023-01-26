package pl.lotto.numberGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryNumberGeneratorRepository implements NumberGeneratorRepository {

    List<DrawnNumbers> drawnNumbersDB = new ArrayList<>();

    @Override
    public DrawnNumbers save(DrawnNumbers drawnNumbers) {
        drawnNumbersDB.add(drawnNumbers);
        return drawnNumbers;
    }

    @Override
    public DrawnNumbers findDrawnNumbersByDrawDate(LocalDateTime drawDate) {
            return drawnNumbersDB.stream()
                    .filter(drawnNumbers -> drawnNumbers.drawDate().equals(drawDate))
                    .findFirst().orElse( new DrawnNumbers(null,null, Collections.emptyList()))
        ;
    }
}
