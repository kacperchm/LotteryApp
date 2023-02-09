package pl.lotto.infrastructure.controller.numberreceiver;

import java.util.List;

public record InputNumbersRequestDto(
        List<Integer> inputNumbers
) {
}
