package pl.lotto.resultChecker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ResultCheckerRepository {
    Result save(Result result);
    List<Result> saveAll(List<Result> results);

    List<Result> findAllByDrawDate(LocalDateTime drawDate);

    Optional<Result> findByTicketID(String id);

    void delete(String ticketId);

    void updateAll(List<Result> resultList);
}
