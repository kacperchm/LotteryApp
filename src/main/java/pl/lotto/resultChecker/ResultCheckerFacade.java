package pl.lotto.resultChecker;

import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.DrawDateGenerator;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultCheckerFacade {
    private NumberGeneratorFacade numberGeneratorFacade;
    private NumberReceiverFacade numberReceiverFacade;
    private ResultCheckerRepository repository;


    public ResultCheckerFacade(NumberGeneratorFacade numberGeneratorFacade, NumberReceiverFacade numberReceiverFacade, ResultCheckerRepository repository) {
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
    }

    void LotteryTicketSaver() {
        LocalDateTime now = DrawDateGenerator.findFirstSaturday(LocalDateTime.now());
        List<LotteryTicketDto> retrieveNumbers = numberReceiverFacade.retrieveNumbersFromUser(now);
        for (LotteryTicketDto ticket: retrieveNumbers) {
            Result result = new Result(ticket.ticketID(),ticket.lotteryNumbers(),ticket.creationTicketDate(),
                    ticket.drawDate(), Collections.emptyList(),0,"",false);
            repository.save(result);
        }
    }

    void checkNumbers() {
        LocalDateTime now = DrawDateGenerator.findLastSaturday(LocalDateTime.now());
        List<Result> results = repository.findAllByDrawDate(now);
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(now);
        for (Result r: results) {

        }
    }

}
