package pl.lotto.numberReceiver;

import pl.lotto.util.Finder;

import java.time.Clock;

public class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createForTests(Clock clock, NumberReceiverRepository repository) {
        NumberValidator numberValidator = new NumberValidator();
        Finder generator = new Finder();
        return new NumberReceiverFacade(numberValidator, clock, repository, generator);
    }
}
