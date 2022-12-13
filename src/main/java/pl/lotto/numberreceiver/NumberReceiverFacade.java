package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NumberReceiverFacade {

    InputNumbersResultDto inputNumbers(List<Integer> numbersFromUser) {


        InputNumbersResultDto firstCheck = rangeChecker(numbersFromUser);
        if (firstCheck != null) return firstCheck;

        InputNumbersResultDto secondCheck = moreNumChecker(numbersFromUser);
        if (secondCheck != null) return secondCheck;

        InputNumbersResultDto thirdCheck = lessNumChecker(numbersFromUser);
        if (thirdCheck != null) return thirdCheck;

        InputNumbersResultDto fourthCheck = duplicateChecker(numbersFromUser);
        if (fourthCheck != null) return fourthCheck;

        return new InputNumbersResultDto(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "All went good"
        );
    }

    private static InputNumbersResultDto rangeChecker(List<Integer> numbersFromUser) {
        for(int i = 0; i< numbersFromUser.size(); i++) {
            if(numbersFromUser.get(i) <1 || numbersFromUser.get(i) >99) {
                return new InputNumbersResultDto(null, null, "Number from outside the range");
            }
        }
        return null;
    }

    private static InputNumbersResultDto moreNumChecker(List<Integer> numbersFromUser) {
        if (numbersFromUser.size() > 6) {
            return new InputNumbersResultDto(
                    null,
                    null,
                    "You gave more than six numbers"
            );
        }
        return null;
    }

    private static InputNumbersResultDto lessNumChecker(List<Integer> numbersFromUser) {
        if (numbersFromUser.size() < 6) {
            return new InputNumbersResultDto(
                    null,
                    null,
                    "You gave less than six numbers"
            );
        }
        return null;
    }

    private static InputNumbersResultDto duplicateChecker(List<Integer> numbersFromUser) {
        for(int i = 0; i<numbersFromUser.size(); i++) {
            for (int j = i + 1; j<numbersFromUser.size(); j++) {
                if((numbersFromUser.get(i)).equals(numbersFromUser.get(j))) {
                    return new InputNumbersResultDto(
                            null,
                            null,
                            "Your numbers have duplicate"
                    );
                }
            }
        }
        return null;
    }




}
