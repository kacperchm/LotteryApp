package pl.lotto.resultChecker;

import java.util.Collections;
import java.util.List;

public class Comparator {
    public static List<LotteryTicketRC> compareListOfLotteryTicket(List<LotteryTicketRC> lotteryTicketsRC, List<LotteryTicketRC> lotteryTicketsFromNR) {
        int sizeLTRC = lotteryTicketsRC.size();
        int sizeLTNR = lotteryTicketsFromNR.size();

        List<LotteryTicketRC> returnedList = Collections.emptyList();
        if (!(sizeLTRC == sizeLTNR && lotteryTicketsRC.get(sizeLTRC - 1).ticketID().equals(lotteryTicketsFromNR.get(sizeLTNR - 1).ticketID()))) {
            for (LotteryTicketRC lt : lotteryTicketsFromNR) {
                if (!lotteryTicketsRC.contains(lt)) {
                    returnedList.add(lt);
                }
            }
        }
        return returnedList;
    }
}
