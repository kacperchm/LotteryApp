package pl.lotto.numbergenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record DrawnNumbersDto(
       Long drawId,
       LocalDateTime drawDate,
       List<Integer> drawNumbers
) {

}
