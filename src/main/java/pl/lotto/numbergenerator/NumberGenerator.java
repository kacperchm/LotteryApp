package pl.lotto.numbergenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NumberGenerator {

    List<Integer> generateNumber() {
            if(isDrawDateTime()) {
               return drawingMachine();
            }
            return  List.of();
    }

    boolean isDrawDateTime() {
       LocalDateTime now = LocalDateTime.now();
        if(now.getDayOfWeek().equals("SATURDAY") && now.getHour() == 20
                && now.getMinute() == 0 && now.getSecond()== 0 && now.getNano()==0) {
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
