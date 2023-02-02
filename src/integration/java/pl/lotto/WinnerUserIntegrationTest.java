package pl.lotto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WinnerUserIntegrationTest {

	@Test
	void winnerUserScenario() {
		//step 1: user gave 6 numbers at 02.02.2023 12:00 on endpoint and system returned lottery ticket with draw date 04.02.2023 20:00
		//step 2: user checked result before draw date and system return early message
		//step 3: 2 days and 7 hours passed (04.02.2023 19:00)
		//step 4: system generated winning numbers for date 04.02.2023 20:00
		//step 5: 1 hour and minute passed (04.02.2023 20:01)
		//step 6: system checked result 04.02.2023 20:01
		//step 7: user checked result and system return winner message

		//TODO:Read about @RestController, MockMvc, @RequestBody, HttpStatus
	}

}
