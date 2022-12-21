package pl.lotto.numberreceiver;

import java.time.Clock;

public class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createForTests(Clock clock, NumberReceiverRepository repository) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator generator = new DrawDateGenerator();
        return new NumberReceiverFacade(numberValidator, clock, repository, generator);
    }
}
