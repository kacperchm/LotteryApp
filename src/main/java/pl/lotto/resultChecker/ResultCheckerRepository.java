package pl.lotto.resultChecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ResultCheckerRepository extends MongoRepository<Result, String> {
//    Result save(Result result);
//    List<Result> saveAll(List<Result> results);

    void saveAll(List<Result> results);

    List<Result> findAllByDrawDate(LocalDateTime drawDate);

    Optional<Result> findByTicketID(String id);

    void delete(String ticketId);

    void updateAll(List<Result> resultList);
}
