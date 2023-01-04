package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryNumberReceiverRepository implements NumberReceiverRepository {

    List<LotteryTicket> lotteryTicketDb = new ArrayList<>();

    @Override
    public LotteryTicket save(LotteryTicket lotteryTicket) {
        lotteryTicketDb.add(lotteryTicket);
        return lotteryTicket;
    }

    @Override
    public List<LotteryTicket> findAllByDrawDate(LocalDateTime drawDate) {
        return lotteryTicketDb.stream()
                .filter(lotteryTicket -> lotteryTicket.drawDate().equals(drawDate))
                .collect(Collectors.toList());
    }
}
