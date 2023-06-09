package pl.lotto.numberReceiver;

import java.util.ArrayList;
import java.util.List;

import static pl.lotto.numberReceiver.ValidationError.*;

class NumberValidator {

    public static final int LOWEST_POSSIBLE_NUMBER_FROM_USER = 1;
    public static final int HIGHEST_POSSIBLE_NUMBER_FROM_USER = 99;


    public ValidationResult validate(List<Integer> numbersFromUser) {
        List<String> message = new ArrayList<>();
        if (isAnyNumberOfOutRange(numbersFromUser)) {
            message.add(OUT_OF_RANGE.getMessage());
        }
        if (isMoreThanSixNumbers(numbersFromUser)) {
            message.add(MORE_NUMBERS.getMessage());
        }
        if (isLessThanSixNumbers(numbersFromUser)) {
            message.add(LESS_NUMBERS.getMessage());
        }
        if (hasDuplicate(numbersFromUser)) {
            message.add(NUMBER_DUPLICATED.getMessage());
        }
        boolean valid = message.isEmpty();
        if (valid) {
            message.add("All went good");
        }
        String collect = String.join(",", message);
        return new ValidationResult(valid, collect);
    }

    private boolean isAnyNumberOfOutRange(List<Integer> numbersFromUser) {
        return numbersFromUser.stream().anyMatch(this::isOutOfRange);
    }

    private boolean isOutOfRange(Integer number) {
        return number < LOWEST_POSSIBLE_NUMBER_FROM_USER || number > HIGHEST_POSSIBLE_NUMBER_FROM_USER;
    }

    private boolean isMoreThanSixNumbers(List<Integer> numbersFromUser) {
        return numbersFromUser.size() > 6;
    }

    private boolean isLessThanSixNumbers(List<Integer> numbersFromUser) {
        return numbersFromUser.size() < 6;
    }

    private boolean hasDuplicate(List<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .distinct()
                .toList()
                .size() != 6;
    }
}
