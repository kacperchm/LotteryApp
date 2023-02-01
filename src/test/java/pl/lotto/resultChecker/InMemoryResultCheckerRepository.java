package pl.lotto.resultChecker;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryResultCheckerRepository implements ResultCheckerRepository {

    public Map<String,Result> resultDb = new HashMap<>();

    @Override
    public Result save(Result result) {
        resultDb.put(result.ticketID(), result);
        return result;
    }

    @Override
    public List<Result> saveAll(List<Result> results) {
        Map<String,Result> temporaryResultMap = new HashMap<>();
        for (Result result: results) {
            temporaryResultMap.put(result.ticketID(), result);
        }
        resultDb.putAll(temporaryResultMap);
        return results;
    }

    @Override
    public List<Result> findAllByDrawDate(LocalDateTime drawDate) {
        List<Result> resultsDbList = new ArrayList<>();
        resultDb.forEach((s, result) -> resultsDbList.add(result));
        List<Result> resultListWithCorrectDrawDate = resultsDbList.stream()
                .filter(result -> result.drawDate().equals(drawDate))
                .toList();
        return resultListWithCorrectDrawDate;
    }


    @Override
    public Optional<Result> findByTicketID(String id) {
        return Optional.of(resultDb.get(id));
    }

    @Override
    public void delete(String ticketId) {
        resultDb.remove(ticketId);
    }

    @Override
    public void updateAll(List<Result> resultList) {
        Map<String,Result> temporaryResultMap = new HashMap<>();
        for (Result result : resultList) {
            resultDb.remove(result.ticketID());
            temporaryResultMap.put(result.ticketID(), result);
        }
        resultDb.putAll(temporaryResultMap);
    }
}
