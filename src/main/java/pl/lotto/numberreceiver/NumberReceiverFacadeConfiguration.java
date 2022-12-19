package pl.lotto.numberreceiver;

public class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createForTests() {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(numberValidator);
    }
}
