package pl.lotto.resultAnnouncer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.lotto.resultChecker.ResultCheckerFacade;
import pl.lotto.resultChecker.dto.ResultDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ResultAnnouncerFacadeTest {
    @Test
    public void should_return_winning_message() {
        //given
        ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
        Mockito.when(resultCheckerFacade.checkWinner("TID001"))
                .thenReturn(new ResultDto("TID001", List.of(24, 5, 32, 72, 53, 91),
                        LocalDateTime.of(2023, 2, 3, 12, 0),
                        LocalDateTime.of(2023, 2, 4, 20, 0),
                        List.of(24, 5, 32, 72, 53, 91), 6, "Your lottery ticket TID001 from 2023-01-03T12:00 has 6 correct numbers."));
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade(resultCheckerFacade);
        //when
        String message = resultAnnouncerFacade.checkTicket("TID001");
        //then
        assertThat(message).isEqualTo("Your lottery ticket TID001 from 2023-01-03T12:00 has 6 correct numbers. Your lottery prize is 1 200 000,00 zł");
    }

    @Test
    public void should_return_error_message() {
        //given
        ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
        Mockito.when(resultCheckerFacade.checkWinner("TID001"))
                .thenReturn(new ResultDto(null, Collections.emptyList(), null, null, Collections.emptyList(),
                        0, "Lottery ticket does not exist."));
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade(resultCheckerFacade);
        //when
        String message = resultAnnouncerFacade.checkTicket("TID001");
        //then
        assertThat(message).isEqualTo("Lottery ticket does not exist.");
    }

    @Test
    public void should_return_early_message() {
        //given
        ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
        Mockito.when(resultCheckerFacade.checkWinner("TID001"))
                .thenReturn(new ResultDto("TID001", List.of(24, 5, 32, 72, 53, 91),
                        LocalDateTime.of(2023, 2, 3, 12, 0),
                        LocalDateTime.of(2023, 2, 4, 20, 0),
                        Collections.emptyList(), 0, "The numbers have not been drawn yet"));
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade(resultCheckerFacade);
        //when
        String message = resultAnnouncerFacade.checkTicket("TID001");
        //then
        assertThat(message).isEqualTo("The numbers have not been drawn yet");
    }
}