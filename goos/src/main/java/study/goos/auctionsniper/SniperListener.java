package study.goos.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {

    void sniperLost();

    void sniperWon();

    void sniperStateChanged(SniperSnapshot snapshot);
}
