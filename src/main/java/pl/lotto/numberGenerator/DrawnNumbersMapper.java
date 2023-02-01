package pl.lotto.numberGenerator;

import pl.lotto.numberGenerator.DrawnNumbers;
import pl.lotto.numberGenerator.dto.DrawnNumbersDto;

public class DrawnNumbersMapper {

    public static DrawnNumbers mapToDrawnNumbers(DrawnNumbersDto dto) {
        return DrawnNumbers.builder()
                .drawNumbers(dto.drawNumbers())
                .drawDate(dto.drawDate())
                .drawId(dto.drawId())
                .build();
    }

    public static DrawnNumbersDto mapToDrawnNumbersDto(DrawnNumbers drawnNumbers) {
        return DrawnNumbersDto.builder()
                .drawNumbers(drawnNumbers.drawNumbers())
                .drawDate(drawnNumbers.drawDate())
                .drawId(drawnNumbers.drawId())
                .build();
    }
}
