package pl.lotto.resultAnnouncer;

import pl.lotto.resultChecker.dto.ResultDto;

 class ResultMapper {


    public static Result map(ResultDto resultToChange) {
        Result result = new Result(resultToChange.ticketID(),resultToChange.playerNumbers(),
                resultToChange.creationTicketDate(),resultToChange.drawDate(),resultToChange.winningNumbers(),
                resultToChange.correctNumbers(), resultToChange.message());

        return result;
    }
}
