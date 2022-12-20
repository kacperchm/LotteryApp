package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NumberReceiverFacade {

    static ArrayList<LotteryTicketDto> lotteryTicketDb = new ArrayList<>();

    private final NumberValidator numberValidator;

    public NumberReceiverFacade(NumberValidator numberValidator) {
        this.numberValidator = numberValidator;
    }

    public InputNumbersResultDto inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validate = numberValidator.validate(numbersFromUser);

        if (validate.isNotValid()) {
            return new InputNumbersResultDto(null, null, validate.validationMessage());
        }
        String generatedId = UUID.randomUUID().toString();
        LocalDateTime dateOfCreationTicket = LocalDateTime.now();
        lotteryTicketDb.add(
                new LotteryTicketDto(generatedId,
                        numbersFromUser,
                        dateOfCreationTicket,
                        LotteryTicketDto.findFirstSaturday(dateOfCreationTicket)));
        // TODO: zapis do bazy danych - done
        return new InputNumbersResultDto(
                generatedId,
                dateOfCreationTicket,
                validate.validationMessage()
        );
    }
    // TODO: pobierz zbazy danych wszystkie tickety z podaną datą (date) - done
    public List<LotteryTicketDto> retrieveNumbersFromUser(LocalDateTime date) {
          List<LotteryTicketDto> filteredTickets = lotteryTicketDb.stream().
                filter(ticket -> ticket.drawDate().equals(date))
                .collect(Collectors.toList());

          return filteredTickets;
    }


}
