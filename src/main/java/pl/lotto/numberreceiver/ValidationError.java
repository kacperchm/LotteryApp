package pl.lotto.numberreceiver;

enum ValidationError {

    NUMBER_DUPLICATED("Your numbers have duplicate"),
    OUT_OF_RANGE("Number from outside the range");

    private final String message;

    ValidationError(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}
