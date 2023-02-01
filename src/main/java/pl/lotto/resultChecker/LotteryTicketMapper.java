package pl.lotto.resultChecker;

import pl.lotto.numberReceiver.dto.LotteryTicketDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class LotteryTicketMapper {


    List<Result> mapToResults(List<LotteryTicketDto> listToChange) {
//

        return listToChange.stream()
                .map(result -> new Result(result.ticketID(), result.lotteryNumbers(), result.creationTicketDate(),
                        result.drawDate(), Collections.emptyList(), 0, "The numbers have not been drawn yet"))
                .collect(Collectors.toList());
    }
}
