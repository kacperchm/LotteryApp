package pl.lotto.resultChecker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.NumberReceiverFacade;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResultCheckerFacadeTest {

    ResultCheckerRepository repository = new InMemoryResultCheckerRepository();

    @Test
    public void should_save_whole_list_to_repository() {
        //given
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24,5,32,72,53,91),LocalDateTime.now(),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID002", List.of(5,22,16,56,13,84),LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID003", List.of(13,28,23,75,46,81),LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID004", List.of(9,12,54,23,65,45),LocalDateTime.now(),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID005", List.of(15,25,66,74,22,87),LocalDateTime.now().minusDays(2),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID006", List.of(25,2,16,56,42,81),LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID007", List.of(1,2,3,4,5,6),LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023,2,4,20,0)),
                new LotteryTicketDto("TID008", List.of(2,22,11,37,85,63),LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023,2,4,20,0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023,2,4,20,0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023,2,4,20,0),
                        List.of(2,16,56,42,12,92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade,numberReceiverFacade,repository);
        //when
        resultCheckerFacade.lotteryTicketSaver();
        //then
        assertThat(repository.findAllByDrawDate(LocalDateTime.of(2023,2,4,20,0)).size()).isEqualTo(8);
    }

}
