package pl.lotto.numberReceiver;

import java.time.LocalDateTime;

public record InputNumbersResultDto(
        String lotteryId,
        LocalDateTime creationTime,
        LocalDateTime drawDate,
        String message
) {

}
