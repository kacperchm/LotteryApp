package pl.lotto.resultChecker;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MongoTemplateImpl implements MongoTemplateable {

    private final MongoTemplate mongoTemplate;

    @Override
    public void upsert(Query query, Update update, Class<Result> resultClass) {
        mongoTemplate.upsert(query, update, resultClass);
    }
}
