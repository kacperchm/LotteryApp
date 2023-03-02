package pl.lotto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationConfiguration {

    @Bean
    AdjustableClock clock() {
        LocalDateTime now = LocalDateTime.of(2023, 2, 9, 8, 0);
        return new AdjustableClock(now.toInstant(ZoneOffset.UTC), ZoneId.of("Europe/Warsaw"));
    }
}
