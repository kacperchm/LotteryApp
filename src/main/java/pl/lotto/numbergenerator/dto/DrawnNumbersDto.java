package pl.lotto.numbergenerator.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record DrawnNumbersDto(
       String drawId,
       LocalDateTime drawDate,
       List<Integer> drawNumbers
) {

}
