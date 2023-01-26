package pl.lotto.resultAnnouncer;

enum LotteryPrize {

    SIX("1 200 000,00 zł"),
    FIVE("100 000,00 zł"),
    FOUR("1 000,00 zł"),
    THREE("500,00 zł"),
    OTHER("0,00 zł");

    private final String prize;

    LotteryPrize(String prize) {
        this.prize = prize;
    }

    String getPrize() {
        return prize;
    }
}
