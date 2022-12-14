package pl.lotto.numberreceiver;

import java.util.List;

class NumberValidator {

    public static final int LOWEST_POSSIBLE_NUMBER_FROM_USER = 1;
    public static final int HIGHEST_POSSIBLE_NUMBER_FROM_USER = 99;

    public ValidationResult validate(List<Integer> numbersFromUser) {
        if (areAllNumbersInRange(numbersFromUser)) {
            return new ValidationResult(false, "Number from outside the range");
        }
        if (isMoreThanSixNumbers(numbersFromUser)) {
            return new ValidationResult(false, "Number from outside the range");
        }
        if (isLessThanSixNumbers(numbersFromUser)) {
            return new ValidationResult(false, "Number from outside the range");
        }
        if (hasDuplicate(numbersFromUser)) {
            return new ValidationResult(false, "Number from outside the range");
        }
        return new ValidationResult(true, "All went good");
    }

    private boolean areAllNumbersInRange(List<Integer> numbersFromUser) {
        for (int i = 0; i < numbersFromUser.size(); i++) {
            if (numbersFromUser.get(i) < LOWEST_POSSIBLE_NUMBER_FROM_USER || numbersFromUser.get(i) > HIGHEST_POSSIBLE_NUMBER_FROM_USER) {
                return true;
            }
        }
        return false;
    }

    private boolean isMoreThanSixNumbers(List<Integer> numbersFromUser) {
        return numbersFromUser.size() > 6;
    }

    private boolean isLessThanSixNumbers(List<Integer> numbersFromUser) {
        return numbersFromUser.size() < 6;
    }

    private boolean hasDuplicate(List<Integer> numbersFromUser) {
        for (int i = 0; i < numbersFromUser.size(); i++) {
            for (int j = i + 1; j < numbersFromUser.size(); j++) {
                if ((numbersFromUser.get(i)).equals(numbersFromUser.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
