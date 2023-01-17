package pl.lotto.numbergenerator;

import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.LotteryTicket;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.next;

public class NumberGeneratorFacade {

    static ArrayList<LotteryTicketDto> drawsArchive  = new ArrayList<>();
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

        LocalDateTime drawDate = now
                .with(next(DayOfWeek.SATURDAY))
                .withHour(20)
                .withMinute(0);

        List<Integer> drawNumbers = numberGenerator.generateNumber();
        DrawnNumbers savedDrawnNumbers = repository.save(new DrawnNumbers(1L, drawDate,drawNumbers));
        return new DrawnNumbersDto(savedDrawnNumbers.drawId(), savedDrawnNumbers.drawDate(),savedDrawnNumbers.drawNumbers());
    }

    public DrawnNumbersDto retrieveWonNumbers(LocalDateTime dateTime) {
        if(dateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) && dateTime.getHour() == 20 && dateTime.getMinute() == 0) {
            DrawnNumbers drawnNumbers = repository.findDrawnNumbersByDrawDate(dateTime);
            if(!drawnNumbers.drawNumbers().isEmpty()) {
                return DrawnNumbersMapper.mapToDrawnNumbersDto(drawnNumbers);
            }else {
                return new DrawnNumbersDto(null,null,Collections.emptyList());
            }
        }else {
            return new DrawnNumbersDto(null,null,Collections.emptyList());
        }
    }
}
