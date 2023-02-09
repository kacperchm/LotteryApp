package pl.lotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LottoApplcation {
        public static void main(String[] args) {
            SpringApplication.run(LottoApplcation.class, args);
        }
}
