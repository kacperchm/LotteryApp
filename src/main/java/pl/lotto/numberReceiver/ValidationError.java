package pl.lotto.numberReceiver;

public enum ValidationError {

    NUMBER_DUPLICATED("Your numbers have duplicate"),
    OUT_OF_RANGE("Number from outside the range"),
    MORE_NUMBERS("You gave more than six numbers"),
    LESS_NUMBERS("You gave less than six numbers");

    private final String message;

    ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
