package pl.lotto.numberGenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberGeneratorFacadeTest {

    NumberGeneratorRepository repository = new InMemoryNumberGeneratorRepository();

    @Test
    public void should_return_winning_numbers_when_is_draw_date_time() {
        // given
        LocalDateTime now = LocalDateTime.of(2023,1,11,20,0);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator(), repository, now);
        // when
        DrawnNumbersDto drawnNumbersDto =  numberGeneratorFacade.generateWonNumbers();
        // then
        assertThat(drawnNumbersDto.drawDate()).isEqualTo(LocalDateTime.of(2023,1,14,20,0));
        assertThat(drawnNumbersDto.drawNumbers().size()).isEqualTo(6);
    }

    @Test
    public void should_return_empty_when_numbers_was_not_generate_yet() {
        // given
        LocalDateTime now = LocalDateTime.of(2023,1,11,20,0);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator(), repository, now);
        // when
        DrawnNumbers drawnNumbers = DrawnNumbersMapper.mapToDrawnNumbers(numberGeneratorFacade
                .retrieveWonNumbers(LocalDateTime.of(2023,1,14,20,0)));
        // then
        assertThat(drawnNumbers.drawNumbers()).isEmpty();
    }

    @Test
    public void should_return_winning_numbers_when_numbers_were_generate_for_given_draw_date() {
        // given
        LocalDateTime now = LocalDateTime.of(2023,1,11,20,0);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator(), repository, now);
        DrawnNumbersDto drawnNumbersDto1 = numberGeneratorFacade.generateWonNumbers();
        // when
        DrawnNumbersDto drawnNumbersDto2 = numberGeneratorFacade.retrieveWonNumbers(drawnNumbersDto1.drawDate());
        // then
        assertThat(drawnNumbersDto2.drawDate()).isEqualTo(drawnNumbersDto1.drawDate());
        assertThat(drawnNumbersDto2.drawNumbers()).isNotEmpty();
    }
}
