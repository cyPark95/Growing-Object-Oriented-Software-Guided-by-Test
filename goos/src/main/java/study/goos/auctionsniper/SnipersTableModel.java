package study.goos.auctionsniper;

import javax.swing.table.AbstractTableModel;

import static study.goos.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {

    private final static SniperState STARTING_UP = new SniperState("", 0, 0);

    private String statusText = STATUS_JOINING;
    private SniperState sniperState = STARTING_UP;

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
            case ITEM_IDENTIFIER -> sniperState.itemId;
            case LAST_PRICE -> sniperState.lastPrice;
            case LAST_BID -> sniperState.lastBid;
            case SNIPER_STATUS -> statusText;
        };
    }

    public void setStatusText(String newStatusText) {
        statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStatusChanged(SniperState newSniperState, String newStatusText) {
        sniperState = newSniperState;
        statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
    }
}
