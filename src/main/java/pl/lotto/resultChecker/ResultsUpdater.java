package pl.lotto.resultChecker;

import pl.lotto.numberGenerator.dto.DrawnNumbersDto;

import java.util.ArrayList;
import java.util.List;

public class ResultsUpdater {

    private MatchingNumbers matchingNumbers;


    public ResultsUpdater() {
        this.matchingNumbers = new MatchingNumbers();
    }

    List<Result> update(DrawnNumbersDto drawnNumbersDto, List<Result> resultsToUpdate) {
        List<Result> results = new ArrayList<>();
        for (Result result : resultsToUpdate) {
            int winningNum = matchingNumbers.checkMatchingNum(result.playerNumbers(), drawnNumbersDto.drawNumbers());
            results.add(
                    new Result(result.ticketID(),
                            result.playerNumbers(),
                            result.creationTicketDate(),
                            result.drawDate(),
                            drawnNumbersDto.drawNumbers(),
                            winningNum,
                            "Your lottery ticket " + result.ticketID() + " from "
                                    + result.creationTicketDate() + " has " + winningNum + " correct numbers."));
        }
        return  results;
    }
}
