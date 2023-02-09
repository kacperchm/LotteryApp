package pl.lotto.infrastructure.controller.numberreceiver;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberReceiver.NumberReceiverFacade;
import pl.lotto.numberReceiver.ValidationError;
import pl.lotto.numberReceiver.dto.InputNumbersResultDto;

@RestController
@AllArgsConstructor
public class NumberReceiverRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<InputNumbersResultDto> inputNumbers(@RequestBody InputNumbersRequestDto request){
        InputNumbersResultDto inputNumbersResultDto = numberReceiverFacade.inputNumbers(request.inputNumbers());
        if(!inputNumbersResultDto.message().equals("All went good")){
            return ResponseEntity.badRequest().body(inputNumbersResultDto);
        }
        return ResponseEntity.ok(inputNumbersResultDto);
    }
}
