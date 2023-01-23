package pl.lotto.resultChecker;

import java.time.LocalDateTime;
import java.util.List;

public interface LotteryTicketRepository {
    LotteryTicketRC save(LotteryTicketRC lotteryTicket);

    List<LotteryTicketRC> findAllByDrawDate(LocalDateTime drawDate);

    void saveAll(List<LotteryTicketRC> lotteryTicketList);
}
