package study.goos.auctionsniper;

import com.objogate.exception.Defect;

import java.util.Objects;

import static study.goos.auctionsniper.SniperSnapshot.SniperState.*;

public class SniperSnapshot {

    public final String itemId;
    public final int lastPrice;
    public final int lastBid;
    public final SniperState state;

    public SniperSnapshot(String itemId, int lastPrice, int lastBid, SniperState state) {
        this.itemId = itemId;
        this.lastPrice = lastPrice;
        this.lastBid = lastBid;
        this.state = state;
    }

    public static SniperSnapshot joining(String itemId) {
        return new SniperSnapshot(itemId, 0, 0, JOINING);
    }

    public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
        return new SniperSnapshot(itemId, newLastPrice, newLastBid, BIDDING);
    }

    public SniperSnapshot winning(int newLastPrice) {
        return new SniperSnapshot(itemId, newLastPrice, lastBid, WINNING);
    }

    public SniperSnapshot closed() {
        return new SniperSnapshot(itemId, lastPrice, lastBid, state.whenAuctionClosed());
    }

    public boolean isForSameItemAs(SniperSnapshot snapshot) {
        return this.itemId.equals(snapshot.itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SniperSnapshot that = (SniperSnapshot) o;
        return lastPrice == that.lastPrice && lastBid == that.lastBid && Objects.equals(itemId, that.itemId) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, lastPrice, lastBid, state);
    }

    @Override
    public String toString() {
        return "SniperSnapshot{" +
                "itemId='" + itemId + '\'' +
                ", lastPrice=" + lastPrice +
                ", lastBid=" + lastBid +
                ", state=" + state +
                '}';
    }

    public enum SniperState {

        JOINING {
            @Override
            public SniperState whenAuctionClosed() {
                return LOST;
            }
        },
        BIDDING {
            @Override
            public SniperState whenAuctionClosed() {
                return LOST;
            }
        },
        WINNING {
            @Override
            public SniperState whenAuctionClosed() {
                return WON;
            }
        },
        LOST,
        WON,
        ;

        public SniperState whenAuctionClosed() {
            throw new Defect("Auction is already closed");
        }
    }
}
