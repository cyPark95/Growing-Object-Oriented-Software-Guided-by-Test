package study.goos.auctionsniper;

import javax.swing.*;

import static study.goos.Main.ui;
import static study.goos.MainWindow.*;

public class SniperStateDisplayer implements SniperListener {

    @Override
    public void sniperLost() {
        showStatus(STATUS_LOST);
    }

    @Override
    public void sniperWon() {
        showStatus(STATUS_WON);
    }

    @Override
    public void sniperStateChanged(SniperSnapshot snapshot) {
        SwingUtilities.invokeLater(() -> ui.sniperStatusChanged(snapshot));
    }

    private void showStatus(String statusBidding) {
        SwingUtilities.invokeLater(() -> ui.showStatusText(statusBidding));
    }
}
