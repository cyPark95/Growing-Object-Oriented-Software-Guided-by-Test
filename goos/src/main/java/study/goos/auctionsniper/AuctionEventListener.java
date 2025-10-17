package study.goos.auctionsniper;

public interface AuctionEventListener {

    void auctionClosed();

    void currentPrice(int price, int increment, PriceSource priceSource);

    enum PriceSource {
        FROM_SNIPER,
        FROM_OTHER_BIDDER,
        ;
    }
}
