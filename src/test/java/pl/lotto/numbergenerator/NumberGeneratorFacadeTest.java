package pl.lotto.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.InputNumbersResultDto;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.NumberReceiverFacadeConfiguration;

import java.time.Clock;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberGeneratorFacadeTest {

    @Test
    public void should_return_empty_winning_numbers_when_is_not_draw_date_time() {
        // given
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator());
        // when
        DrawnNumbersDto drawnNumbersDto =  numberGeneratorFacade.generateWonNumbers();
        // then
        assertThat(drawnNumbersDto.drawNumbers()).isEmpty();
    }

    @Test
    public void should_return_winning_numbers_when_is_draw_date_time() {
        // given
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(new NumberGenerator());
        // when
        DrawnNumbersDto drawnNumbersDto =  numberGeneratorFacade.generateWonNumbers();
        // then
        assertThat(drawnNumbersDto.drawNumbers().size()).isEqualTo(6);
    }
}
