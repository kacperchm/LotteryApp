package pl.lotto.infrastructure.scheduler.resultChecker;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.resultChecker.ResultCheckerFacade;

@Component
@AllArgsConstructor
@Log4j2
public class ResultCheckerScheduler {

    private final ResultCheckerFacade resultCheckerFacade;

    @Scheduled(cron = "${lotto.result-checker.transformToResultOccurrence}")
    public void f1() {
        resultCheckerFacade.transformToResult();
    }

    @Scheduled(cron = "${lotto.result-checker.checkNumbersOccurrence}")
    public void f2() {
        resultCheckerFacade.checkNumbers();
        log.info("checked numbers");
    }
}
