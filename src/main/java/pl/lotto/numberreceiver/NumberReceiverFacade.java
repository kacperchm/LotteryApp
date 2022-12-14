package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NumberReceiverFacade {

    private final NumberValidator numberValidator;

    public NumberReceiverFacade(NumberValidator numberValidator) {
        this.numberValidator = numberValidator;
    }

    public InputNumbersResultDto inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validate = numberValidator.validate(numbersFromUser);
        if (validate.isNotValid()) {
            return new InputNumbersResultDto(null, null, validate.validationMessage());
        }
        // zapis do bazy danych
        return new InputNumbersResultDto(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                validate.validationMessage()
        );
    }

//    public List<LotteryTicketDto> retreiveNumbersFromUser(LocalDateTime date) {
//        // pobierz zbazy danych wszystkie tickety z podaną datą (date)
//    }


}
