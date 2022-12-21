package pl.lotto.numberreceiver;

import java.util.List;

record ValidationResult(
        boolean isValid,
        String validationMessage
) {

    public boolean isNotValid() {
        return !isValid;
    }

}
