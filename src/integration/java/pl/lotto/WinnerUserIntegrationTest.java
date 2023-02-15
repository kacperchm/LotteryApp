package pl.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.numberReceiver.dto.InputNumbersResultDto;
import pl.lotto.resultAnnouncer.ResultAnnouncerResponseDto;
import pl.lotto.resultChecker.ResultCheckerFacade;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = IntegrationConfiguration.class)
@AutoConfigureMockMvc
class WinnerUserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ResultCheckerFacade resultCheckerFacade;

    @Test
    void winnerUserScenario() throws Exception {
        //step 1: user gave 6 numbers at 02.02.2023 12:00 on endpoint and system returned lottery ticket with draw date 04.02.2023 20:00

        // given
        // when
        ResultActions perform = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                        "inputNumbers" : [1,2,3,4,5,6]
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        InputNumbersResultDto result = objectMapper.readValue(json, InputNumbersResultDto.class);
        LocalDateTime ticketDrawDate = LocalDateTime.of(2023, 2, 11, 20, 0, 0);
        assertAll(
                () -> assertThat(result.message()).isEqualTo("All went good"),
                () -> assertThat(result.drawDate()).isEqualTo(ticketDrawDate));


        // step 1.5 result checker generated result
        await().atMost(30, SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> resultCheckerFacade.wasNumberChecked());

        //step 2: user checked result before draw date and system return early message

        // given
        String ticketHash = result.lotteryId();

        // when
        ResultActions performGetMethod = mockMvc.perform(get("/results/" + ticketHash));

        // GET /results/asdasd-2qweqw-asda-123123

        // then
        MvcResult mvcResultGetMethod = performGetMethod.andExpect(status().isNoContent()).andReturn();

        String jsonGetMethod = mvcResultGetMethod.getResponse().getContentAsString();
        ResultAnnouncerResponseDto finalResult = objectMapper.readValue(jsonGetMethod, ResultAnnouncerResponseDto.class);

        assertThat(finalResult.message()).isEqualTo("The numbers have not been drawn yet");

        //step 3: 2 days and 7 hours passed (04.02.2023 19:00)
        //step 4: system generated winning numbers for date 04.02.2023 20:00
        //step 5: 1 hour and minute passed (04.02.2023 20:01)
        //step 6: system checked result 04.02.2023 20:01
        //step 7: user checked result and system return winner message

        //TODO:Read about @RestController, MockMvc, @RequestBody, HttpStatus
    }

}
