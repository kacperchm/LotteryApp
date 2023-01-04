package pl.lotto.numbergenerator;

import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.util.ArrayList;

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

}
