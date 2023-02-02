package pl.lotto.resultAnnouncer;

import pl.lotto.resultChecker.ResultCheckerFacade;

import static pl.lotto.resultAnnouncer.LotteryPrize.*;

public class ResultAnnouncerFacade {

    private ResultCheckerFacade resultCheckerFacade;

    public ResultAnnouncerFacade(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }

    public String checkTicket (String lotteryTicketId) {
        Result result = ResultChanger.resultMapper(resultCheckerFacade.checkWinner(lotteryTicketId));

        if(result.ticketID() == null) {
            return result.message();
        }

        String prize = switch (result.correctNumbers()) {
            case 6 -> SIX.getPrize();
            case 5 -> FIVE.getPrize();
            case 4 -> FOUR.getPrize();
            case 3 -> THREE.getPrize();
            default -> OTHER.getPrize();
        };

        return result.message() + " Your lottery prize is " + prize;
    }
}
