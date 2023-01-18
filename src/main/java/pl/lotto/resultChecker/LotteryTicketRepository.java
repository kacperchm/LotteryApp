package pl.lotto.resultChecker;

import pl.lotto.numberreceiver.LotteryTicket;

import java.time.LocalDateTime;
import java.util.List;

public interface LotteryTicketRepository {
    LotteryTicket save(LotteryTicket lotteryTicket);

    List<LotteryTicket> findAllByDrawDate(LocalDateTime drawDate);
}
