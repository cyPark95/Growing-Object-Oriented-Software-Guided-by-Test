package study.goos.auctionsniper;

import javax.swing.table.AbstractTableModel;

import static study.goos.MainWindow.*;
import static study.goos.auctionsniper.SniperSnapshot.SniperState.*;

public class SnipersTableModel extends AbstractTableModel {

    private static String[] STATUS_TEXT = {STATUS_JOINING, STATUS_BIDDING, STATUS_WINNING, STATUS_LOST, STATUS_WON};
    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, JOINING);

    private String state = STATUS_JOINING;
    private SniperSnapshot snapshot = STARTING_UP;

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER -> snapshot.itemId;
            case LAST_PRICE -> snapshot.lastPrice;
            case LAST_BID -> snapshot.lastBid;
            case SNIPER_STATE -> state;
        };
    }

    public void setStatusText(String newStatusText) {
        state = newStatusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStatusChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        this.state = STATUS_TEXT[newSnapshot.state.ordinal()];

        fireTableRowsUpdated(0, 0);
    }
}
