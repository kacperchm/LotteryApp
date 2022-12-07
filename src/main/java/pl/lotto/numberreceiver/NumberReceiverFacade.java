package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NumberReceiverFacade {

    InputNumbersResultDto inputNumbers(List<Integer> numbersFromUser) {
        if (numbersFromUser.size() < 6) {
            return new InputNumbersResultDto(
                    null,
                    null,
                    "u gave less than six numbers"
            );
        }
        return new InputNumbersResultDto(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "all went good"
        );
    }
}
