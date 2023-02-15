package pl.lotto.resultChecker;

import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.NumberReceiverFacade;
import pl.lotto.resultChecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ResultCheckerFacade {
    private NumberGeneratorFacade numberGeneratorFacade;
    private NumberReceiverFacade numberReceiverFacade;
    private ResultCheckerRepository repository;
    private Finder finder;
    private Comparator comparator;
    private LotteryTicketMapper mapper;
    private MatchingNumbers matchingNumbers;
    private ResultsUpdater resultsUpdater;
    private Clock clock;


    public ResultCheckerFacade(NumberGeneratorFacade numberGeneratorFacade, NumberReceiverFacade numberReceiverFacade,
                               ResultCheckerRepository repository, Clock clock) {
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
        this.finder = new Finder();
        this.comparator = new Comparator();
        this.mapper = new LotteryTicketMapper();
        this.matchingNumbers = new MatchingNumbers();
        this.resultsUpdater = new ResultsUpdater();
        this.clock = clock;
    }

    void transformToResult() {
        LocalDateTime now = finder.findFirstSaturday(LocalDateTime.now(clock));
        List<Result> resultsWithoutDrawnNumbers = mapper.mapToResults(numberReceiverFacade.retrieveNumbersFromUser(now));
        List<Result> resultsFromRepository = repository.findAllByDrawDate(now);
        List<Result> resultsToSave =
                comparator.compareListOfResult(resultsFromRepository, resultsWithoutDrawnNumbers);
        if (!(resultsToSave.isEmpty())) {
            repository.saveAll(resultsWithoutDrawnNumbers);
        }
    }

    void checkNumbers() {
        LocalDateTime now = finder.findLastSaturday(LocalDateTime.now(clock));
        List<Result> resultsToUpdate = repository.findAllByDrawDate(now);
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(now);
        List<Result> results = resultsUpdater.update(drawnNumbersDto, resultsToUpdate);
        repository.updateAll(results);
    }

    public ResultDto checkWinner(String lotteryTicketID) {
        Result result = repository.findByTicketID(lotteryTicketID)
                .orElse(new Result(null, Collections.emptyList(), null, null, Collections.emptyList(),
                        0, "Lottery ticket does not exist."));
        ResultDto resultDto = ResultMapper.mapToResultDto(result);
        return resultDto;
    }

}
