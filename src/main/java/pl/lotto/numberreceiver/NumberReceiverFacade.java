package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;

public class NumberReceiverFacade {

    private final NumberReceiverRepository repository;
    private final NumberValidator numberValidator;
    private final DrawDateGenerator drawDateGenerator;
    private final Clock clock;

    public NumberReceiverFacade(NumberValidator numberValidator, Clock clock, NumberReceiverRepository repository, DrawDateGenerator drawDateGenerator) {
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
        LotteryTicket savedLotteryTicket = repository.save(new LotteryTicket(generatedId,
                numbersFromUser,
                dateOfCreationTicket,
                drawDate));
        return new InputNumbersResultDto(
                generatedId,
                dateOfCreationTicket,
                drawDate,
                validate.validationMessage()
        );
    }

    public List<LotteryTicketDto> retrieveNumbersFromUser(LocalDateTime date) {
        return repository.findAllByDrawDate(date)
                .stream()
                .map(LotteryTicketMapper::mapToLotteryTicketDto)
                .collect(Collectors.toList());
    }


}
