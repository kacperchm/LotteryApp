package pl.lotto.numberreceiver;

import java.time.LocalDateTime;

public record InputNumbersResultDto(
        String lotteryId,
        LocalDateTime creationTime,
        LocalDateTime drawDate,
        String message
) {

}
