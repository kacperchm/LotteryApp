package pl.lotto.numbergenerator;

import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NumberGeneratorFacade {

    static ArrayList<LotteryTicketDto> drawsArchive  = new ArrayList<>();
    private NumberGenerator numberGenerator;

    public NumberGeneratorFacade(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    //TODO: fix this method. Use scheduler (Spring)
    void getDraw() {
        while (true) {
            numberGenerator.generateNumber();
        }
    }

    public DrawnNumbersDto generateWonNumbers() {
        List<Integer> drawNumbers = numberGenerator.generateNumber();
        return new DrawnNumbersDto(1L, LocalDateTime.now(),drawNumbers);
    }
}
