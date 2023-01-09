package pl.lotto.numbergenerator;

import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.LotteryTicket;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NumberGeneratorFacade {

    static ArrayList<LotteryTicketDto> drawsArchive  = new ArrayList<>();
    private NumberGenerator numberGenerator;
    private NumberGeneratorRepository repository;

    public NumberGeneratorFacade(NumberGenerator numberGenerator, NumberGeneratorRepository repository) {
        this.numberGenerator = numberGenerator;
        this.repository = repository;
    }

    //TODO: fix this method. Use scheduler (Spring)

    public DrawnNumbersDto generateWonNumbers(LocalDateTime dateTime) {
        List<Integer> drawNumbers = numberGenerator.generateNumber(dateTime);

        if(drawNumbers.isEmpty()) {
            return null;
        }
        DrawnNumbers savedDrawnNumbers = repository.save(new DrawnNumbers(1L, dateTime,drawNumbers));
        return new DrawnNumbersDto(1L, dateTime,drawNumbers);
    }

    public DrawnNumbers retrieveWonNumbers(LocalDateTime dateTime) {
        return repository.findDrawnNumbersByDrawDate(dateTime);
    }
}
