package pl.lotto.numberReceiver;

import pl.lotto.numberReceiver.LotteryTicket;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;

public class LotteryTicketMapper {

    public static LotteryTicket mapToLotteryTicket(LotteryTicketDto dto) {
        return LotteryTicket.builder()
                .lotteryNumbers(dto.lotteryNumbers())
                .drawDate(dto.drawDate())
                .ticketID(dto.ticketID())
                .creationTicketDate(dto.creationTicketDate())
                .build();
    }

    public static LotteryTicketDto mapToLotteryTicketDto(LotteryTicket lotteryTicket) {
        return LotteryTicketDto.builder()
                .lotteryNumbers(lotteryTicket.lotteryNumbers())
                .drawDate(lotteryTicket.drawDate())
                .ticketID(lotteryTicket.ticketID())
                .creationTicketDate(lotteryTicket.creationTicketDate())
                .build();
    }
}
