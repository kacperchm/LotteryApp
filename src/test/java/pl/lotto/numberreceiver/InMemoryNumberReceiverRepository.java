package pl.lotto.numberreceiver;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNumberReceiverRepository implements NumberReceiverRepository {

    List<LotteryTicketDto> lotteryTicketDb = new ArrayList<>();

    @Override
    public void add(LotteryTicketDto lotteryTicketDto) {
        lotteryTicketDb.add(lotteryTicketDto);
    }
}
