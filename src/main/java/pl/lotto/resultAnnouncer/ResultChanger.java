package pl.lotto.resultAnnouncer;

import pl.lotto.resultChecker.dto.ResultDto;

public class ResultChanger {


    public static ResultRA resultChanger (ResultDto resultToChange) {
        ResultRA resultRA = new ResultRA(resultToChange.ticketID(),resultToChange.playerNumbers(),
                resultToChange.creationTicketDate(),resultToChange.drawDate(),resultToChange.winningNumbers(),
                resultToChange.correctNumbers(), resultToChange.message());

        return resultRA;
    }
}
