package pl.lotto.numberReceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import pl.lotto.AdjustableClock;
import pl.lotto.numberReceiver.dto.InputNumbersResultDto;
import pl.lotto.numberReceiver.dto.LotteryTicketDto;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberReceiverFacadeTest {

    NumberReceiverRepository lotteryTicketDb = new InMemoryNumberReceiverRepository();

    @Test
    public void should_return_lottery_id_and_draw_date_when_user_gave_six_numbers_in_range_of_1_99() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryTicketDb);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.drawDate()).isNotNull();
        assertThat(result.lotteryId()).isNotEqualTo(null);
        assertThat(result.message()).contains("All went good");
    }

    @Test
    public void should_return_result_without_lottery_id_and_draw_date_when_user_gave_six_numbers_out_range_of_1_99() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryTicketDb);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 0);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.drawDate()).isNull();
        assertThat(result.lotteryId()).isNull();
        assertThat(result.message()).contains("Number from outside the range");
    }

    @Test
    public void should_return_result_without_lottery_id_and_draw_date_when_user_gave_less_than_six_numbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryTicketDb);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.drawDate()).isNull();
        assertThat(result.lotteryId()).isNull();
//        assertThat(result.message().get(0)).isEqualTo("You gave less than six numbers");
    }

    @Test
    public void should_return_result_without_lottery_id_and_draw_date_when_user_gave_more_than_six_numbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryTicketDb);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.drawDate()).isNull();
        assertThat(result.lotteryId()).isNull();
//        assertThat(result.message().get(0)).isEqualTo("You gave more than six numbers");
    }

    @Test
    public void should_return_result_without_lottery_id_and_draw_date_when_user_gave_at_least_one_duplicate() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryTicketDb);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 5);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.drawDate()).isNull();
        assertThat(result.lotteryId()).isNull();
//        assertThat(result.message().get(0)).isEqualTo("Your numbers have duplicate");
    }


    @Test
    public void should_return_result_without_lottery_id_and_draw_date_when_user_make_two_mistakes() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryTicketDb);
        List<Integer> numbersFromUser = List.of(1, 2, 1000, 4, 5, 5);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.drawDate()).isNull();
        assertThat(result.lotteryId()).isNull();
        assertThat(result.message())
                .contains("Number from outside the range,Your numbers have duplicate");
    }


    @Test
    public void should_return_ticket_when_user_played() {
        // given
        LocalDateTime now = LocalDateTime.of(2022, 12, 28, 17, 9);
        AdjustableClock clock = new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
        NumberReceiverRepository repository = new InMemoryNumberReceiverRepository();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, repository);
        List<Integer> numbersFromUser1 = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> numbersFromUser2 = List.of(1, 2, 5, 11, 22, 6);
        // first user
        InputNumbersResultDto inputNumbersResultDto1 = numberReceiverFacade.inputNumbers(numbersFromUser1);
        LocalDateTime drawDate = inputNumbersResultDto1.drawDate();

        // second user week later
        clock.plusDays(8);
        InputNumbersResultDto inputNumbersResultDto2 = numberReceiverFacade.inputNumbers(numbersFromUser2);
        // when
        List<LotteryTicketDto> result = numberReceiverFacade.retrieveNumbersFromUser(drawDate);
        List<LotteryTicketDto> result2 = numberReceiverFacade.retrieveNumbersFromUser(inputNumbersResultDto2.drawDate());
        // then
        LocalDateTime expectedDrawDate1 = LocalDateTime.of(2022, 12, 31, 20, 0);
        LocalDateTime expectedDrawDate2 = LocalDateTime.of(2023, 1, 7, 20, 0);
        assertThat(result).isNotEmpty();
        assertThat(result).contains(
                new LotteryTicketDto(inputNumbersResultDto1.lotteryId(),
                        numbersFromUser1,
                        inputNumbersResultDto1.creationTime(),
                        expectedDrawDate1)
        );
        assertThat(result2).contains(
                new LotteryTicketDto(inputNumbersResultDto2.lotteryId(),
                        numbersFromUser2,
                        inputNumbersResultDto2.creationTime(),
                        expectedDrawDate2)
        );
        assertThat(result).doesNotContain(
                new LotteryTicketDto(inputNumbersResultDto2.lotteryId(),
                        numbersFromUser2,
                        inputNumbersResultDto2.creationTime(),
                        expectedDrawDate2)
        );
    }
}
