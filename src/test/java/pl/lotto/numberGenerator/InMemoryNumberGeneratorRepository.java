package pl.lotto.numberGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class InMemoryNumberGeneratorRepository implements NumberGeneratorRepository {

    List<DrawnNumbers> drawnNumbersDB = new ArrayList<>();

    @Override
    public DrawnNumbers save(DrawnNumbers drawnNumbers) {
        drawnNumbersDB.add(drawnNumbers);
        return drawnNumbers;
    }

    @Override
    public Optional<DrawnNumbers> findDrawnNumbersByDrawDate(LocalDateTime drawDate) {
        return Optional.of(drawnNumbersDB.stream()
                .filter(drawnNumbers -> drawnNumbers.drawDate().equals(drawDate))
                .findFirst().orElse(new DrawnNumbers(null, null, Collections.emptyList())));
    }

    @Override
    public boolean existsByDrawDate(LocalDateTime drawDate) {
        return drawnNumbersDB.stream()
                .anyMatch(drawnNumbers -> drawnNumbers.drawDate().equals(drawDate));
    }

    @Override
    public <S extends DrawnNumbers> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<DrawnNumbers> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<DrawnNumbers> findAll() {
        return null;
    }

    @Override
    public Iterable<DrawnNumbers> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(DrawnNumbers entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends DrawnNumbers> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<DrawnNumbers> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<DrawnNumbers> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DrawnNumbers> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends DrawnNumbers> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends DrawnNumbers> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DrawnNumbers> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends DrawnNumbers> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends DrawnNumbers> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DrawnNumbers> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DrawnNumbers> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DrawnNumbers, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
