package pl.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.lotto.numberGenerator.DrawNumberNotFoundException;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
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

@SpringBootTest(classes = {LottoApplication.class, IntegrationConfiguration.class})
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("integration")
class WinnerUserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ResultCheckerFacade resultCheckerFacade;

    @Autowired
    AdjustableClock adjustableClock;

    @Autowired
    NumberGeneratorFacade numberGeneratorFacade;

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void winnerUserScenario() throws Exception {
        //step 1: user gave 6 numbers at 09.02.2023 08:00 on endpoint and system returned lottery ticket with draw date 11.02.2023 20:00

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


        //step 2: user checked result before draw date and system return early message GET /results/asdasd-2qweqw-asda-123123
        // given
        String ticketHash = result.lotteryId();
        // when
        ResultActions performGetMethod = mockMvc.perform(get("/results/" + ticketHash));
        // then
        MvcResult mvcResultGetMethod = performGetMethod.andExpect(status().isOk()).andReturn();
        String jsonGetMethod = mvcResultGetMethod.getResponse().getContentAsString();
        ResultAnnouncerResponseDto finalResult = objectMapper.readValue(jsonGetMethod, ResultAnnouncerResponseDto.class);
        assertThat(finalResult.message()).isEqualTo("The numbers have not been drawn yet");

        //step 3: 2 days and 11 hours, 59 minutes passed (11.02.2023 19:59)
        adjustableClock.plusDays(2);
        adjustableClock.plusHours(11);
        adjustableClock.plusMinutes(59);

        //step 4: system generated winning numbers for date 11.02.2023 20:00
        await().atMost(20, SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() ->
                        {
                            try {
                                return numberGeneratorFacade.retrieveWonNumbers(ticketDrawDate).drawId() != null;
                            } catch (DrawNumberNotFoundException e) {
                                return false;
                            }
                        }
                );


//        //step 5: 2 minutes passed (11.02.2023 20:01)
//        adjustableClock.plusMinutes(2);


        // step 6: system generated results for user
        await().atMost(20, SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> !resultCheckerFacade.checkWinner(ticketHash).winningNumbers().isEmpty());


        //step 7: user checked result and system return winner message
        // given
        // when
        ResultActions performGetMethod2 = mockMvc.perform(get("/results/" + ticketHash));
        // then
        MvcResult mvcResultGetMethod2 = performGetMethod2.andExpect(status().isOk()).andReturn();
        String jsonGetMethod2 = mvcResultGetMethod2.getResponse().getContentAsString();
        ResultAnnouncerResponseDto finalResult2 = objectMapper.readValue(jsonGetMethod2, ResultAnnouncerResponseDto.class);
        assertThat(finalResult2.message()).isEqualTo("Your lottery ticket " + ticketHash + " from "
                + result.creationTime() + " has " + "0" + " correct numbers.");

    }

}
