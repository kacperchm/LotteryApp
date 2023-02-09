package pl.lotto.numberReceiver;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("lotterytickets")
 record LotteryTicket(
         @Id String ticketID,
        List<Integer> lotteryNumbers,
        LocalDateTime creationTicketDate,
        LocalDateTime drawDate
) {
}

