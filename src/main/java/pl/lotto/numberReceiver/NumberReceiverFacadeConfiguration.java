package pl.lotto.numberReceiver;

import java.time.Clock;

class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createForTests(Clock clock, NumberReceiverRepository repository) {
        NumberValidator numberValidator = new NumberValidator();
        Finder generator = new Finder();
        return new NumberReceiverFacade(numberValidator, clock, repository);
    }
}
