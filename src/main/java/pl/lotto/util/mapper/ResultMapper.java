package pl.lotto.util.mapper;

import pl.lotto.resultChecker.Result;
import pl.lotto.resultChecker.dto.ResultDto;

public class ResultMapper {


    public static Result mapToResult(ResultDto dto) {
        return Result.builder()
                .ticketID(dto.ticketID())
                .playerNumbers(dto.playerNumbers())
                .creationTicketDate(dto.creationTicketDate())
                .drawDate(dto.drawDate())
                .winningNumbers(dto.winningNumbers())
                .correctNumbers(dto.correctNumbers())
                .message(dto.message())
                .build();
    }

    public static ResultDto mapToResultDto(Result result) {
        return ResultDto.builder()
                .ticketID(result.ticketID())
                .playerNumbers(result.playerNumbers())
                .creationTicketDate(result.creationTicketDate())
                .drawDate(result.drawDate())
                .winningNumbers(result.winningNumbers())
                .correctNumbers(result.correctNumbers())
                .message(result.message())
                .build();
    }
}
