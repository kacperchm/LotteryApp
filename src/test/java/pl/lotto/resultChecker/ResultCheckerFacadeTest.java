package pl.lotto.resultChecker;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.numberReceiver.NumberReceiverFacade;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;
import pl.lotto.resultChecker.dto.ResultDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResultCheckerFacadeTest {

    InMemoryResultCheckerRepository repository = new InMemoryResultCheckerRepository();
    private MongoTemplateable mongoTemplate = new InMemoryMongoTemplate(repository);

    @Test
    public void should_save_whole_list_of_results_without_winning_numbers_to_repository() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 20, 0);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID002", List.of(5, 22, 16, 56, 13, 84), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID003", List.of(13, 28, 23, 75, 46, 81), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID004", List.of(9, 12, 54, 23, 65, 45), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID005", List.of(15, 25, 66, 74, 22, 87), LocalDateTime.now().minusDays(2),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID006", List.of(25, 2, 16, 56, 42, 81), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID007", List.of(1, 2, 3, 4, 5, 6), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID008", List.of(2, 22, 11, 37, 85, 63), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023, 2, 4, 20, 0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023, 2, 4, 20, 0),
                        List.of(2, 16, 56, 42, 12, 92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository,clock, mongoTemplate);
        //when
        resultCheckerFacade.transformToResult();
        //then
        assertThat(repository.findAllByDrawDate(LocalDateTime.of(2023, 2, 4, 20, 0)).size()).isEqualTo(8);
        assertThat(repository.findAllByDrawDate(LocalDateTime.of(2023, 2, 4, 20, 0)).get(0).message())
                .isEqualTo("The numbers have not been drawn yet");
    }

    @Test
    public void should_save_four_results_without_winning_numbers_to_repository() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 20, 0);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID002", List.of(5, 22, 16, 56, 13, 84), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID003", List.of(13, 28, 23, 75, 46, 81), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID004", List.of(9, 12, 54, 23, 65, 45), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID002", List.of(5, 22, 16, 56, 13, 84), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID003", List.of(13, 28, 23, 75, 46, 81), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID004", List.of(9, 12, 54, 23, 65, 45), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023, 2, 4, 20, 0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023, 2, 4, 20, 0),
                        List.of(2, 16, 56, 42, 12, 92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository,clock, mongoTemplate);
        //when
        resultCheckerFacade.transformToResult();
        //then
        assertThat(repository.findAllByDrawDate(LocalDateTime.of(2023, 2, 4, 20, 0))).hasSize(4);
        assertThat(repository.findAllByDrawDate(LocalDateTime.of(2023, 2, 4, 20, 0)).get(0).message())
                .isEqualTo("The numbers have not been drawn yet");
    }

    @Test
    public void should_update_three_result_to_include_winning_numbers() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 20, 0);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91), LocalDateTime.now(),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID002", List.of(5, 22, 16, 56, 13, 84), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID003", List.of(13, 28, 23, 75, 46, 81), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID004", List.of(9, 12, 54, 23, 65, 45), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID005", List.of(15, 25, 66, 74, 22, 87), LocalDateTime.now().minusDays(2),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID006", List.of(25, 2, 16, 56, 42, 81), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID007", List.of(1, 2, 3, 4, 5, 6), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID008", List.of(2, 22, 11, 37, 85, 63), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023, 1, 28, 20, 0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023, 1, 28, 20, 0),
                        List.of(2, 16, 56, 42, 12, 92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository,clock, mongoTemplate);
        resultCheckerFacade.transformToResult();
        //when
        resultCheckerFacade.checkNumbers();
        //then
        assertThat(repository.findAllByDrawDate(LocalDateTime.of(2023, 1, 28, 20, 0)).size()).isEqualTo(3);
        assertThat(repository.findByTicketID("TID002").orElse(null).message()).contains("Your lottery ticket");
        assertThat(repository.findByTicketID("TID006").orElse(null).message()).isEqualTo("The numbers have not been drawn yet");
    }

    @Test
    public void should_return_result_with_updated_winning_numbers() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 20, 0);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91),
                        LocalDateTime.of(2023, 1, 27, 13, 0),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID002", List.of(5, 22, 16, 56, 13, 84),
                        LocalDateTime.of(2023, 1, 26, 13, 0).minusDays(1),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID003", List.of(13, 28, 23, 75, 46, 81),
                        LocalDateTime.of(2023, 1, 26, 13, 0).plusDays(1),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID004", List.of(9, 12, 54, 23, 65, 45), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID005", List.of(15, 25, 66, 74, 22, 87), LocalDateTime.now().minusDays(2),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID006", List.of(25, 2, 16, 56, 42, 81), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID007", List.of(1, 2, 3, 4, 5, 6), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID008", List.of(2, 22, 11, 37, 85, 63), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023, 1, 28, 20, 0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023, 1, 28, 20, 0),
                        List.of(5, 22, 56, 42, 12, 92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository,clock, mongoTemplate);
        //when
        resultCheckerFacade.transformToResult();
        resultCheckerFacade.checkNumbers();
        ResultDto result = resultCheckerFacade.checkWinner("TID002");
        //then
        assertThat(result.playerNumbers()).isEqualTo(List.of(5, 22, 16, 56, 13, 84));
        assertThat(result.winningNumbers()).isEqualTo(List.of(5, 22, 56, 42, 12, 92));
        assertThat(result.correctNumbers()).isEqualTo(3);
        assertThat(result.message()).isEqualTo("Your lottery ticket TID002 from " + result.creationTicketDate() + " has 3 correct numbers.");
    }

    @Test
    public void should_return_result_without_updated_winning_numbers() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 20, 0);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91), LocalDateTime.of(2023, 1, 27, 13, 0),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID002", List.of(5, 22, 16, 56, 13, 84), LocalDateTime.of(2023, 1, 26, 13, 0).minusDays(1),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID003", List.of(13, 28, 23, 75, 46, 81), LocalDateTime.of(2023, 1, 26, 13, 0).plusDays(1),
                        LocalDateTime.of(2023, 1, 28, 20, 0)),
                new LotteryTicketDto("TID004", List.of(9, 12, 54, 23, 65, 45), LocalDateTime.now(),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID005", List.of(15, 25, 66, 74, 22, 87), LocalDateTime.now().minusDays(2),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID006", List.of(25, 2, 16, 56, 42, 81), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID007", List.of(1, 2, 3, 4, 5, 6), LocalDateTime.now().plusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0)),
                new LotteryTicketDto("TID008", List.of(2, 22, 11, 37, 85, 63), LocalDateTime.now().minusDays(1),
                        LocalDateTime.of(2023, 2, 4, 20, 0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023, 1, 28, 20, 0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023, 1, 28, 20, 0),
                        List.of(5, 22, 56, 42, 12, 92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository,clock, mongoTemplate);
        //when
        resultCheckerFacade.transformToResult();
        resultCheckerFacade.checkNumbers();
        ResultDto result = resultCheckerFacade.checkWinner("TID005");
        //then
        assertThat(result.playerNumbers()).isEqualTo(List.of(15, 25, 66, 74, 22, 87));
        assertThat(result.winningNumbers()).isEqualTo(Collections.emptyList());
        assertThat(result.correctNumbers()).isEqualTo(0);
        assertThat(result.message()).isEqualTo("The numbers have not been drawn yet");
    }

    @Test
    public void should_return_error_result() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 20, 0);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        Mockito.when(numberReceiverFacade.retrieveNumbersFromUser(any())).thenReturn(List.of(
                new LotteryTicketDto("TID001", List.of(24, 5, 32, 72, 53, 91), LocalDateTime.of(2023, 1, 27, 13, 0),
                        LocalDateTime.of(2023, 1, 28, 20, 0))));
        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
        Mockito.when(numberGeneratorFacade.retrieveWonNumbers(LocalDateTime.of(2023, 1, 28, 20, 0)))
                .thenReturn(new DrawnNumbersDto("DRW001", LocalDateTime.of(2023, 1, 28, 20, 0),
                        List.of(5, 22, 56, 42, 12, 92)));
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, repository,clock, mongoTemplate);
        //when
        resultCheckerFacade.transformToResult();
        resultCheckerFacade.checkNumbers();
        ResultDto result = resultCheckerFacade.checkWinner("TID005");
        //then
        assertThat(result.correctNumbers()).isEqualTo(0);
        assertThat(result.drawDate()).isNull();
        assertThat(result.playerNumbers()).isEqualTo(Collections.emptyList());
        assertThat(result.creationTicketDate()).isNull();
        assertThat(result.message()).isEqualTo("Lottery ticket does not exist.");
    }

}
