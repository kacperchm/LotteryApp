package pl.lotto.infrastructure.scheduler.resultChecker;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.resultChecker.ResultCheckerFacade;

@Component
@AllArgsConstructor
public class ResultCheckerSchdeuler {

    private final ResultCheckerFacade resultCheckerFacade;

    @Scheduled(cron = "*/1 * * * * *")
    public void f() {
        System.out.println("scheduler result checker started");
//        resultCheckerFacade.transformToResult();
    }
}
