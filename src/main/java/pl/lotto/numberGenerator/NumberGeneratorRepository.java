package pl.lotto.numberGenerator;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface NumberGeneratorRepository extends MongoRepository<DrawnNumbers,String> {

//        DrawnNumbers save(DrawnNumbers drawnNumbers);

        Optional<DrawnNumbers> findDrawnNumbersByDrawDate(LocalDateTime drawDate);

    boolean existsByDrawDate(LocalDateTime drawDate);
}
