package pl.lotto.numberreceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class NumberValidator {

    public static final int LOWEST_POSSIBLE_NUMBER_FROM_USER = 1;
    public static final int HIGHEST_POSSIBLE_NUMBER_FROM_USER = 99;



    public ValidationResult validate(List<Integer> numbersFromUser) {
        ArrayList<String> message = new ArrayList<>();
        boolean valid = true;
        if (areAllNumbersInRange(numbersFromUser)) {
            valid = false;
            message.add("Number from outside the range");
        }
        if (isMoreThanSixNumbers(numbersFromUser)) {
            valid = false;
            message.add("You gave more than six numbers");
        }
        if (isLessThanSixNumbers(numbersFromUser)) {
            valid = false;
            message.add("You gave less than six numbers");
        }
        if (hasDuplicate(numbersFromUser)) {
            valid = false;
            message.add("Your numbers have duplicate");
        }
        if (message.isEmpty()) {
            message.add("All went good");
        }

        return new ValidationResult(valid, message);
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
        List<Integer> listAfterDistinct = numbersFromUser.stream().distinct().collect(Collectors.toList());
            if(listAfterDistinct.size() < 6 && numbersFromUser.size() == 6) {
                return true;
            }
        return false;
    }
}
