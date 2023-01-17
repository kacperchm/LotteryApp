package pl.lotto.numbergenerator;

import pl.lotto.numbergenerator.dto.DrawnNumbersDto;
import pl.lotto.numberreceiver.LotteryTicket;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;

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
