package pl.lotto.resultChecker;

import pl.lotto.numberReceiver.LotteryTicket;
import pl.lotto.numberReceiver.NumberReceiverRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryResultCheckerRepository implements ResultCheckerRepository {

    List<Result> resultDb = new ArrayList<>();

    @Override
    public Result save(Result result) {
        resultDb.add(result);
        return result;
    }

    @Override
    public void saveAll(List<Result> results) {
        resultDb.addAll(results);
    }

    @Override
    public List<Result> findAllByDrawDate(LocalDateTime drawDate) {
        List<Result> resultListWithCorrectDrawDate = resultDb.stream()
                .filter(result -> result.drawDate().equals(drawDate))
                .toList();

        return resultListWithCorrectDrawDate;
    }


    @Override
    public Optional<Result> findByTicketID(String id) {
        return Optional.of(resultDb.stream().findFirst().filter(result -> result.ticketID().equals(id)).get());
    }

    @Override
    public void delete(String ticketId) {
        resultDb.remove(ticketId);
    }
}
