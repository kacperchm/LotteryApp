package pl.lotto.resultChecker;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class InMemoryMongoTemplate implements MongoTemplateable {
    private final InMemoryResultCheckerRepository repository;

    public InMemoryMongoTemplate(InMemoryResultCheckerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void upsert(Query query, Update update, Class<Result> resultClass) {
//        String ticketId = update.getUpdateObject().getString("ticketId");
//        String playerNumbers = update.getUpdateObject().getString("playerNumbers");
//        repository.remove(ticketId);
//        repository.save(ticketId, new Result(
//                ticketId,
//                playerNumbers
//
//        ));
    }
}
