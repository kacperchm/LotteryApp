package pl.lotto.numberReceiver;

record ValidationResult(
        boolean isValid,
        String validationMessage
) {

    public boolean isNotValid() {
        return !isValid;
    }

}
