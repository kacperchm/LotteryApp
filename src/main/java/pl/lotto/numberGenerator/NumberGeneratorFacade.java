package pl.lotto.numberGenerator;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;

@Component
public class NumberGeneratorFacade {

    static ArrayList<LotteryTicketDto> drawsArchive = new ArrayList<>();
    private NumberGenerator numberGenerator;
    private NumberGeneratorRepository repository;

    private Clock clock;

    private Finder finder;


    public NumberGeneratorFacade(NumberGenerator numberGenerator, NumberGeneratorRepository repository, Clock clock) {
        this.numberGenerator = numberGenerator;
        this.repository = repository;
        this.clock = clock;
        this.finder = new Finder();

    }

    //TODO: fix this method. Use scheduler (Spring)

    public DrawnNumbersDto generateWonNumbers() {
        LocalDateTime now = LocalDateTime.now(clock); // 11.02 20:00
        LocalDateTime drawDate = finder.findFirstSaturday(now);
        if (repository.existsByDrawDate(drawDate)) {
            throw new RuntimeException("numbers already generated for " + drawDate);
        }
        List<Integer> drawNumbers = numberGenerator.generateNumber();
        DrawnNumbers savedDrawnNumbers = repository.save(new DrawnNumbers(UUID.randomUUID().toString(), drawDate, drawNumbers));
        return new DrawnNumbersDto(savedDrawnNumbers.drawId(), savedDrawnNumbers.drawDate(), savedDrawnNumbers.drawNumbers());
    }

    public DrawnNumbersDto retrieveWonNumbers(LocalDateTime dateTime) {
        DrawnNumbers drawnNumbers = repository.findDrawnNumbersByDrawDate(dateTime)
                .orElseThrow(() -> new DrawNumberNotFoundException("Draw numbers not found"));
        return DrawnNumbersMapper.mapToDrawnNumbersDto(drawnNumbers);
    }
}
