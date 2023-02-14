package pl.lotto.infrastructure.controller.numbergenerator;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberGenerator.NumberGeneratorFacade;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;
import pl.lotto.resultAnnouncer.ResultAnnouncerResponseDto;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class NumberGeneratorRestController {

    private final NumberGeneratorFacade numberGeneratorFacade;

    @GetMapping("/retrieveWonNumbers/{date}")
    public ResponseEntity<DrawnNumbersDto> checkTicket(@PathVariable("date") LocalDateTime dateTime) {
        DrawnNumbersDto drawnNumbersDto = numberGeneratorFacade.retrieveWonNumbers(dateTime);
        if(drawnNumbersDto.drawNumbers().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drawnNumbersDto);
    }

}
