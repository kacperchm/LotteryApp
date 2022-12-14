package pl.lotto.numberreceiver;

record ValidationResult(
        boolean isValid,
        String validationMessage
) {

    public boolean isNotValid() {
        return !isValid;
    }

}
