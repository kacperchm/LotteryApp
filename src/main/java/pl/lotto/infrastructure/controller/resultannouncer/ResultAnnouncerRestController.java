package pl.lotto.infrastructure.controller.resultannouncer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.resultAnnouncer.ResultAnnouncerFacade;
import pl.lotto.resultAnnouncer.ResultAnnouncerResponseDto;

@RestController
@AllArgsConstructor
public class ResultAnnouncerRestController {

    private final ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/results/{id}")
    public ResponseEntity<ResultAnnouncerResponseDto> checkTicket(@PathVariable("id") String id) {
        ResultAnnouncerResponseDto responseDto = resultAnnouncerFacade.checkTicket(id);
        if(responseDto.message().equals("Lottery ticket does not exist.")) {
            return ResponseEntity.badRequest().body(responseDto);
        }
        return ResponseEntity.ok(responseDto);
    }
}
