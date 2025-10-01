package study.goos;

import study.goos.auctionsniper.SniperListener;

import javax.swing.*;

import static study.goos.Main.*;

public class SniperStateDisplayer implements SniperListener {

    @Override
    public void sniperBidding() {
        showStatus(STATUS_BIDDING);
    }

    @Override
    public void sniperWinning() {
        showStatus(STATUS_WINNING);
    }

    @Override
    public void sniperLost() {
        showStatus(STATUS_LOST);
    }

    @Override
    public void sniperWon() {
        showStatus(STATUS_WINNING);
    }

    private void showStatus(String statusBidding) {
        SwingUtilities.invokeLater(() -> ui.showStatus(statusBidding));
    }
}
