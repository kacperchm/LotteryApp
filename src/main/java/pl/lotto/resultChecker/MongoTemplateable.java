package pl.lotto.resultChecker;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface MongoTemplateable {
    void upsert(Query query, Update update, Class<Result> resultClass);
}
