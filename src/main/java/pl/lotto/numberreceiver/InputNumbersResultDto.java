package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public record InputNumbersResultDto(
        String lotteryId,
        LocalDateTime drawDate,
        List message
){

}
