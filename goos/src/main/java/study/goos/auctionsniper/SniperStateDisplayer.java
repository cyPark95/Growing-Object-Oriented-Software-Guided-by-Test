package study.goos.auctionsniper;

import javax.swing.*;

import static study.goos.Main.*;
import static study.goos.MainWindow.*;

public class SniperStateDisplayer implements SniperListener {

    @Override
    public void sniperBidding(SniperState state) {
        SwingUtilities.invokeLater(() -> ui.sniperStatusChanged(state, STATUS_BIDDING));
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
        showStatus(STATUS_WON);
    }

    private void showStatus(String statusBidding) {
        SwingUtilities.invokeLater(() -> ui.showStatusText(statusBidding));
    }
}
