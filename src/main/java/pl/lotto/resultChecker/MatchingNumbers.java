package pl.lotto.resultChecker;

import java.util.List;

class MatchingNumbers {
    int checkMatchingNum(List<Integer> playerNumbers, List<Integer> winningNumbers) {
        int correctNumbers = 0;
        for (int i = 0; i < playerNumbers.size(); i++) {
            for (int j = 0; j < winningNumbers.size(); j++) {
                if (playerNumbers.get(i).equals(winningNumbers.get(j))) {
                    correctNumbers++;
                }
            }
        }
        return correctNumbers;
    }
}
