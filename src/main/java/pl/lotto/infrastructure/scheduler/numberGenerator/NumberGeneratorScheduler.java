package pl.lotto.infrastructure.scheduler.numberGenerator;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.resultChecker.ResultCheckerFacade;

@Component
@AllArgsConstructor
@Log4j2
public class NumberGeneratorScheduler {

    private final NumberGeneratorFacade numberGeneratorFacade;

    @Scheduled(cron = "${lotto.number-generator.lotteryRunOccurrence}")
    public void f() {
        log.info("generateWonNumbers SCHEDULER STARTERD");
        numberGeneratorFacade.generateWonNumbers();
        log.info("generateWonNumbers SCHEDULER FINISHED");
    }
}
