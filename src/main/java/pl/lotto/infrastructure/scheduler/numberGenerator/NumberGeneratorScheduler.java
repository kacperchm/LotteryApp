package pl.lotto.infrastructure.scheduler.numberGenerator;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.resultChecker.ResultCheckerFacade;

@Component
@AllArgsConstructor
public class NumberGeneratorScheduler {

    private final NumberGeneratorFacade numberGeneratorFacade;

    @Scheduled(cron = "0 20 * * Sat ?")
    public void f() {
        numberGeneratorFacade.generateWonNumbers();
    }
}
