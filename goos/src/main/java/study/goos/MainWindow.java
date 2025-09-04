package study.goos;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static study.goos.Main.SNIPER_STATUS_NAME;

public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "";

    private final JLabel sniperStatus = createLabel(Main.STATUS_JOINING);

    public MainWindow() {
        super("Auction Sniper");
        setName(MAIN_WINDOW_NAME);
        add(sniperStatus);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }

    private static JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }
}
