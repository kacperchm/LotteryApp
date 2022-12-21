package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NumberReceiverFacade {

    private final NumberReceiverRepository repository;
    private final NumberValidator numberValidator;
    private final DrawDateGenerator drawDateGenerator;
    private final Clock clock;

    public NumberReceiverFacade(NumberValidator numberValidator, Clock clock, List<LotteryTicketDto> repository, DrawDateGenerator drawDateGenerator) {
        this.numberValidator = numberValidator;
        this.clock = clock;
        this.repository = repository;
        this.drawDateGenerator = drawDateGenerator;
    }

    public InputNumbersResultDto inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validate = numberValidator.validate(numbersFromUser);

        if (validate.isNotValid()) {
            return new InputNumbersResultDto(null, null, null, validate.validationMessage());
        }
        String generatedId = UUID.randomUUID().toString();
        LocalDateTime dateOfCreationTicket = LocalDateTime.now(clock);
        LocalDateTime drawDate = drawDateGenerator.findFirstSaturday(dateOfCreationTicket);
        repository.add(new LotteryTicketDto(generatedId,
                numbersFromUser,
                dateOfCreationTicket,
                drawDate));
        // TODO: zapis do bazy danych - done
        return new InputNumbersResultDto(
                generatedId,
                dateOfCreationTicket,
                drawDate,
                validate.validationMessage()
        );
    }

    // TODO: pobierz zbazy danych wszystkie tickety z podaną datą (date) - done
    public List<LotteryTicketDto> retrieveNumbersFromUser(LocalDateTime date) {
        return repository.stream().
                filter(ticket -> ticket.drawDate().equals(date))
                .collect(Collectors.toList());
    }


}
