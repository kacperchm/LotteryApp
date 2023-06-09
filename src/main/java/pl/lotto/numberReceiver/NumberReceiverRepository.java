package pl.lotto.numberReceiver;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberReceiverRepository extends MongoRepository<LotteryTicket, String> {
//    LotteryTicket save(LotteryTicket lotteryTicketDto);

    List<LotteryTicket> findAllByDrawDate(LocalDateTime drawDate);
}
