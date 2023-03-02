package pl.lotto.numberGenerator;

import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        LocalDateTime drawDate = finder.findFirstSaturday(LocalDateTime.now(clock));
        List<Integer> drawNumbers = numberGenerator.generateNumber();
        DrawnNumbers savedDrawnNumbers = repository.save(new DrawnNumbers(UUID.randomUUID().toString(), drawDate, drawNumbers));
        return new DrawnNumbersDto(savedDrawnNumbers.drawId(), savedDrawnNumbers.drawDate(), savedDrawnNumbers.drawNumbers());
    }

    public DrawnNumbersDto retrieveWonNumbers(LocalDateTime dateTime) {
//        if (!(dateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) && dateTime.getHour() == 20 && dateTime.getMinute() == 0)) {
//            return new DrawnNumbersDto(null, null, Collections.emptyList());
//        }
        DrawnNumbers drawnNumbers = repository.findDrawnNumbersByDrawDate(dateTime)
                .orElseThrow(() -> new DrawNumberNotFoundException("Draw numbers not found"));
        return DrawnNumbersMapper.mapToDrawnNumbersDto(drawnNumbers);
    }
}
