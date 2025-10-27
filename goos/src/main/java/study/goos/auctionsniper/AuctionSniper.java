package study.goos.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private final Auction auction;
    private final SniperListener sniperListener;

    private SniperSnapshot snapshot;

    public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
        this.auction = auction;
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(itemId);
    }

    @Override
    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        snapshot = switch (priceSource) {
            case FROM_SNIPER -> snapshot.winning(price);
            case FROM_OTHER_BIDDER -> {
                final int bid = price + increment;
                auction.bid(bid);
                yield snapshot.bidding(price, bid);
            }
        };
        notifyChange();
    }

    private void notifyChange() {
        sniperListener.sniperStateChanged(snapshot);
    }
}
