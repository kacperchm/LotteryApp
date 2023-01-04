package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public interface NumberReceiverRepository {
    LotteryTicket save(LotteryTicket lotteryTicketDto);

    List<LotteryTicket> findAllByDrawDate(LocalDateTime drawDate);
}
