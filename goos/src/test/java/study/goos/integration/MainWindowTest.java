package study.goos.integration;

import com.objogate.wl.swing.probe.ValueMatcherProbe;
import org.junit.Test;
import study.goos.MainWindow;
import study.goos.auctionsniper.SnipersTableModel;
import study.goos.e2e.AuctionSniperDriver;

import static org.hamcrest.Matchers.equalTo;

public class MainWindowTest {

    private final SnipersTableModel tableModel = new SnipersTableModel();
    private final MainWindow mainWindow = new MainWindow(tableModel);
    private final AuctionSniperDriver driver = new AuctionSniperDriver(100);

    @Test
    public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<String> buttonProbe = new ValueMatcherProbe<>(equalTo("an item-id"), "join request");

        mainWindow.addUserRequestListener(itemId -> buttonProbe.setReceivedValue(itemId));

        driver.startBiddingFor("an item-id");
        driver.check(buttonProbe);
    }
}
