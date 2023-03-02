package pl.lotto.numberGenerator;

public class DrawNumberNotFoundException extends RuntimeException {

    DrawNumberNotFoundException(String message) {
        super(message);
    }
}
