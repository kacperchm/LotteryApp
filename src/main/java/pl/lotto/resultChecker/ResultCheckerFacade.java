package pl.lotto.resultChecker;

import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.DrawDateGenerator;
import pl.lotto.numberreceiver.LotteryTicket;
import pl.lotto.numberreceiver.LotteryTicketMapper;
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
    private LotteryTicketRepository lotteryTicketRepository;


    public ResultCheckerFacade(NumberGeneratorFacade numberGeneratorFacade, NumberReceiverFacade numberReceiverFacade,
                               ResultCheckerRepository repository, LotteryTicketRepository lotteryTicketRepository) {
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
        this.lotteryTicketRepository = lotteryTicketRepository;
    }

    void LotteryTicketSaver() {
        LocalDateTime now = DrawDateGenerator.findFirstSaturday(LocalDateTime.now());
        List<LotteryTicketDto> retrieveNumbers = numberReceiverFacade.retrieveNumbersFromUser(now);
        for (LotteryTicketDto ticket: retrieveNumbers) {
            lotteryTicketRepository.save(LotteryTicketMapper.mapToLotteryTicket(ticket));
        }
    }

    void checkNumbers() {
        LocalDateTime now = DrawDateGenerator.findLastSaturday(LocalDateTime.now());
        List<LotteryTicket> lotteryTickets = lotteryTicketRepository.findAllByDrawDate(now);
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(now);
        for (LotteryTicket lotteryTicket: lotteryTickets) {
            repository.save(
                    new Result(lotteryTicket.ticketID(),
                            lotteryTicket.lotteryNumbers(),
                            lotteryTicket.creationTicketDate(),
                            lotteryTicket.drawDate(),
                            drawnNumbersDto.drawNumbers(),
                            MatchingNumbers.checkMatchingNum(lotteryTicket.lotteryNumbers(),drawnNumbersDto.drawNumbers()),
                            "Your ticket has " + MatchingNumbers.checkMatchingNum(lotteryTicket.lotteryNumbers(),
                                    drawnNumbersDto.drawNumbers()) + " correct Numbers",
                            true));
        }
    }

}
