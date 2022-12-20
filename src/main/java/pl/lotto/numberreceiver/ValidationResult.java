package pl.lotto.numberreceiver;

import java.util.ArrayList;

record ValidationResult(
        boolean isValid,
        ArrayList validationMessage
) {

    public boolean isNotValid() {
        return !isValid;
    }

}
