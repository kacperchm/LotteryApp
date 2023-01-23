package pl.lotto.numbergenerator;

import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;
import pl.lotto.util.Finder;
import pl.lotto.util.mapper.DrawnNumbersMapper;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NumberGeneratorFacade {

    static ArrayList<LotteryTicketDto> drawsArchive = new ArrayList<>();
    private NumberGenerator numberGenerator;
    private NumberGeneratorRepository repository;
    private LocalDateTime now;


    public NumberGeneratorFacade(NumberGenerator numberGenerator, NumberGeneratorRepository repository, LocalDateTime now) {
        this.numberGenerator = numberGenerator;
        this.repository = repository;
        this.now = now;
    }

    //TODO: fix this method. Use scheduler (Spring)

    DrawnNumbersDto generateWonNumbers() {

        LocalDateTime drawDate = Finder.findFirstSaturday(now);

        List<Integer> drawNumbers = numberGenerator.generateNumber();
        DrawnNumbers savedDrawnNumbers = repository.save(new DrawnNumbers(UUID.randomUUID().toString(), drawDate, drawNumbers));
        return new DrawnNumbersDto(savedDrawnNumbers.drawId(), savedDrawnNumbers.drawDate(), savedDrawnNumbers.drawNumbers());
    }

    public DrawnNumbersDto retrieveWonNumbers(LocalDateTime dateTime) {
        if (!(dateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) && dateTime.getHour() == 20 && dateTime.getMinute() == 0)) {
            return new DrawnNumbersDto(null, null, Collections.emptyList());
        }
        DrawnNumbers drawnNumbers = repository.findDrawnNumbersByDrawDate(dateTime)
                .orElseThrow(() -> new RuntimeException("Draw numbers not found"));
        return DrawnNumbersMapper.mapToDrawnNumbersDto(drawnNumbers);
    }
}
