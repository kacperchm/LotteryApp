package pl.lotto.numberReceiver;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NumberReceiverFacadeConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    NumberReceiverFacade numberReceiverFacade(Clock clock, NumberReceiverRepository repository) {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator, clock, repository);
    }

    NumberReceiverFacade createForTests(Clock clock, NumberReceiverRepository repository) {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator, clock, repository);
    }
}
