package pl.lotto.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.numbergenerator.dto.DrawnNumbersDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberGeneratorFacadeTest {

    NumberGeneratorRepository repository = new InMemoryNumberGeneratorRepository();

    @Test
    public void should_return_empty_winning_numbers_when_is_not_draw_date_time() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator(), repository);
        // when
        DrawnNumbersDto drawnNumbersDto =  numberGeneratorFacade.generateWonNumbers(dateTime);
        // then
        assertThat(drawnNumbersDto).isNull();
    }

    @Test
    public void should_return_winning_numbers_when_is_draw_date_time() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2023,1,7,20,0,0,0);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator(), repository);
        // when
        DrawnNumbersDto drawnNumbersDto =  numberGeneratorFacade.generateWonNumbers(dateTime);
        // then
        assertThat(dateTime.getDayOfWeek()).isEqualTo(DayOfWeek.SATURDAY);
        assertThat(drawnNumbersDto.drawNumbers().size()).isEqualTo(6);
    }
}
