package pl.lotto.resultChecker;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.NumberReceiverFacade;
import pl.lotto.resultChecker.dto.ResultDto;

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
    private MongoTemplateable mongoTemplate;


    public ResultCheckerFacade(NumberGeneratorFacade numberGeneratorFacade, NumberReceiverFacade numberReceiverFacade,
                               ResultCheckerRepository repository, Clock clock, MongoTemplateable mongoTemplate) {
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.finder = new Finder();
        this.comparator = new Comparator();
        this.mapper = new LotteryTicketMapper();
        this.matchingNumbers = new MatchingNumbers();
        this.resultsUpdater = new ResultsUpdater();
        this.clock = clock;
    }

    public void transformToResult() {
        LocalDateTime now = finder.findFirstSaturday(LocalDateTime.now(clock));
        List<Result> resultsWithoutDrawnNumbers = mapper.mapToResults(numberReceiverFacade.retrieveNumbersFromUser(now));
        List<Result> resultsFromRepository = repository.findAllByDrawDate(now);
        List<Result> resultsToSave =
                comparator.compareListOfResult(resultsFromRepository, resultsWithoutDrawnNumbers);
        if (!(resultsToSave.isEmpty())) {
            repository.saveAll(resultsWithoutDrawnNumbers);
        }
    }

    public boolean wasNumberChecked() {
        //
        LocalDateTime now = finder.findFirstSaturday(LocalDateTime.now(clock));
        return !repository.findAllByDrawDate(now).isEmpty();
    }

    public void checkNumbers() {
        LocalDateTime now = finder.findLastSaturday(LocalDateTime.now(clock));
        List<Result> resultsToUpdate = repository.findAllByDrawDate(now);
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(now);
        List<Result> results = resultsUpdater.update(drawnNumbersDto, resultsToUpdate);

        for (Result result1 : results) {
            Query query = new Query().addCriteria(Criteria.where("ticketId").is(result1.ticketID()));
            Update update = new Update();
            update.set("playerNumbers", result1.playerNumbers());
            update.set("creationTicketDate", result1.creationTicketDate());
            update.set("drawDate", result1.drawDate());
            update.set("winningNumbers", result1.winningNumbers());
            update.set("correctNumbers", result1.correctNumbers());
            update.set("message", result1.message());
            mongoTemplate.upsert(query, update, Result.class);
        }
    }

    public ResultDto checkWinner(String lotteryTicketID) {
        Result result = repository.findByTicketID(lotteryTicketID)
                .orElse(new Result(null, Collections.emptyList(), null, null, Collections.emptyList(),
                        0, "Lottery ticket does not exist."));
        ResultDto resultDto = ResultMapper.mapToResultDto(result);
        return resultDto;
    }

}
