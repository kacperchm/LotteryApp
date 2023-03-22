package pl.lotto.resultChecker;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.NumberReceiverFacade;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;
import pl.lotto.resultChecker.dto.ResultDto;

@Component
@Log4j2
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
    private AtomicInteger counter = new AtomicInteger(0);


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
        LocalDateTime now = finder.nextDrawDate(LocalDateTime.now(clock));
        List<LotteryTicketDto> listToChange = numberReceiverFacade.retrieveNumbersFromUser(now);
        List<Result> resultsWithoutDrawnNumbers = mapper.mapToResults(listToChange);
        List<Result> resultsFromRepository = repository.findAllByDrawDate(now);
        List<Result> resultsToSave =
                comparator.compareListOfResult(resultsFromRepository, resultsWithoutDrawnNumbers);
        if (!(resultsToSave.isEmpty())) {
            log.info("runned time: " + counter.getAndIncrement());
            repository.saveAll(resultsWithoutDrawnNumbers);
            log.info("how many results saved " + resultsWithoutDrawnNumbers.size());
        }
    }

    public boolean wasNumberChecked() {
        LocalDateTime now = finder.nextDrawDate(LocalDateTime.now(clock));
        return !repository.findAllByDrawDate(now).isEmpty();
    }

    public void checkNumbers() {
        LocalDateTime now = finder.nextDrawDate(LocalDateTime.now(clock));
        List<Result> resultsToUpdate = repository.findAllByDrawDateAndMessage(now, "The numbers have not been drawn yet");
        log.info("results to update: " + resultsToUpdate.toString());
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(now);
        List<Result> results = resultsUpdater.update(drawnNumbersDto, resultsToUpdate);

        for (Result result1 : results) {
            log.info("started updating results for ticketId " + result1.ticketID());
            Query query = new Query().addCriteria(Criteria.where("ticketID").is(result1.ticketID()));
            Update update = new Update();
            update.set("playerNumbers", result1.playerNumbers());
            update.set("creationTicketDate", result1.creationTicketDate());
            update.set("drawDate", result1.drawDate());
            log.info("drawDate for ticket was " + result1.drawDate());
            update.set("winningNumbers", result1.winningNumbers());
            log.info("winning numbers for ticket was " + result1.winningNumbers());
            update.set("correctNumbers", result1.correctNumbers());
            update.set("message", result1.message());
            mongoTemplate.upsert(query, update, Result.class);
        }
    }

    public ResultDto checkWinner(String lotteryTicketID) {
        log.info("checking winner");
        Result result = repository.findByTicketID(lotteryTicketID)
                .orElseThrow(() -> new RuntimeException("lotteryTicket: " + lotteryTicketID + " not found"));
        LocalDateTime now = LocalDateTime.now(clock);
        if (now.isBefore(result.drawDate())) {
            log.info("Its to early message");
            log.info("It is: " + now);
            log.info("And draw date is: " + result.drawDate());
            return ResultMapper.mapToResultDto(new Result(null, Collections.emptyList(), null, null, Collections.emptyList(),
                    0, "The numbers have not been drawn yet"));
        }
        log.info("correctly returned result dto for id:" + result.ticketID());
        return ResultMapper.mapToResultDto(result);
    }

}
