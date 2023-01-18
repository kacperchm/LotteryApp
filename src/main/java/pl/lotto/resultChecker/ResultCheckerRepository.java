package pl.lotto.resultChecker;

import pl.lotto.numberreceiver.LotteryTicket;

import java.time.LocalDateTime;
import java.util.List;

public interface ResultCheckerRepository {
    Result save(Result result);

    List<Result> findAllByDrawDate(LocalDateTime drawDate);

    Result findByTicketID(String id);

    void delete(String ticketId);
}
