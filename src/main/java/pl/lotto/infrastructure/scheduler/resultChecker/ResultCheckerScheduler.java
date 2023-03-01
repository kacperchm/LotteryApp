package pl.lotto.infrastructure.scheduler.resultChecker;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.resultChecker.ResultCheckerFacade;

@Component
@AllArgsConstructor
public class ResultCheckerScheduler {

    private final ResultCheckerFacade resultCheckerFacade;

    @Scheduled(cron = "* */1 * * * *")
    public void f1() {
        resultCheckerFacade.transformToResult();
    }

    @Scheduled(cron = "1 20 * * Sat ?")
    public void f2() {
        resultCheckerFacade.checkNumbers();
    }
}
