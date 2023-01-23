package pl.lotto.resultChecker;

import pl.lotto.numberreceiver.LotteryTicket;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.util.Collections;
import java.util.List;

public class LotteryTicketChanger {


    public static List<LotteryTicketRC> lotteryTicketListChanger (List<LotteryTicketDto> listToChange) {
        List<LotteryTicketRC> lotteryTicketRCList = Collections.emptyList();
        for (LotteryTicketDto ltDto: listToChange) {
           lotteryTicketRCList.add(new LotteryTicketRC(ltDto.ticketID(), ltDto.lotteryNumbers() ,ltDto.creationTicketDate(),ltDto.drawDate()));
        }
        return lotteryTicketRCList;
    }
}
