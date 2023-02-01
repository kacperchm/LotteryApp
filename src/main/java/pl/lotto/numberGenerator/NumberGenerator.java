package pl.lotto.numberGenerator;

import java.util.ArrayList;
import java.util.List;

class NumberGenerator {

    List<Integer> generateNumber() {
        return drawingMachine();
    }


    List<Integer> drawingMachine() {
        List<Integer> drawnNumbers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int drawnNumber = (int) (Math.random() * (99 - 1) + 1);
            drawnNumbers.add(drawnNumber);
        }
        return drawnNumbers;
    }

}
