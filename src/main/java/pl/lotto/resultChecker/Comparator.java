package pl.lotto.resultChecker;

import java.util.List;
import java.util.stream.Collectors;

class Comparator {
    List<Result> compareListOfResult(List<Result> resultsFromDb, List<Result> resultsToSave) {
        int sizeLTRC = resultsFromDb.size();
        int sizeLTNR = resultsToSave.size();

        List<Result> temporaryList = resultsToSave;

        temporaryList.removeAll(resultsFromDb);
        return temporaryList.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
