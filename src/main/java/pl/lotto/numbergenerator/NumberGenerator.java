package pl.lotto.numbergenerator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NumberGenerator {

    List<Integer> generateNumber(LocalDateTime date) {
            if(isDrawDateTime(date)) {
               return drawingMachine();
            }
            return  List.of();
    }

    boolean isDrawDateTime(LocalDateTime date) {
        if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && date.getHour() == 20 && date.getMinute() == 0) {
            return true;
        }

        return false;
    }

    List<Integer> drawingMachine() {
        List<Integer> drawnNumbers = new ArrayList<>();
        for(int i = 0; i<6; i++) {
            int drawnNumber = (int)(Math.random() * (99-1) +1);
            drawnNumbers.add(drawnNumber);
        }
        return drawnNumbers;
    }

}
