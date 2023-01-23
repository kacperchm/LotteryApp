package pl.lotto.resultChecker;

import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;
import pl.lotto.resultChecker.dto.ResultDto;
import pl.lotto.util.Finder;
import pl.lotto.util.mapper.ResultMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    void lotteryTicketSaver() {
        LocalDateTime now = Finder.findFirstSaturday(LocalDateTime.now());
        List<LotteryTicketDto> retrieveNumbers = numberReceiverFacade.retrieveNumbersFromUser(now);
        List<LotteryTicketRC> retrieveNumbersRC = LotteryTicketChanger.lotteryTicketListChanger(retrieveNumbers);
        List<LotteryTicketRC> lotteryTicketsFromRepository = lotteryTicketRepository.findAllByDrawDate(now);
        List<LotteryTicketRC> lotteryTicketsToSave =
                Comparator.compareListOfLotteryTicket(lotteryTicketsFromRepository, retrieveNumbersRC);
        if (!(lotteryTicketsToSave.isEmpty())) {
            for (LotteryTicketRC ticket : lotteryTicketsToSave) {
                lotteryTicketsToSave.add(ticket);
            }
            lotteryTicketRepository.saveAll(lotteryTicketsToSave);
        }
    }

    void checkNumbers() {
        LocalDateTime now = Finder.findLastSaturday(LocalDateTime.now());
        List<LotteryTicketRC> lotteryTickets = lotteryTicketRepository.findAllByDrawDate(now);
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(now);
        List<Result> results = new ArrayList<>();
        for (LotteryTicketRC lotteryTicket : lotteryTickets) {
            int winningNum = MatchingNumbers.checkMatchingNum(lotteryTicket.lotteryNumbers(), drawnNumbersDto.drawNumbers());
            results.add(
                    new Result(lotteryTicket.ticketID(),
                            lotteryTicket.lotteryNumbers(),
                            lotteryTicket.creationTicketDate(),
                            lotteryTicket.drawDate(),
                            drawnNumbersDto.drawNumbers(),
                            winningNum,
                            "Your ticket has " + winningNum + " correct numbers"));
        }
        repository.saveAll(results);
    }

    public ResultDto checkWinner(String lotteryTicketID) {
        ResultDto resultDto = ResultMapper.mapToResultDto(repository.findByTicketID(lotteryTicketID));
        return resultDto;
    }

}
