package pl.lotto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.lotto.numberReceiver.AdjustableClock;

@TestConfiguration
public class IntegrationConfiguration {

    @Bean
    AdjustableClock clock(){
        LocalDateTime now = LocalDateTime.of(2023, 2, 9, 8, 0);
        return new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
    }
}
