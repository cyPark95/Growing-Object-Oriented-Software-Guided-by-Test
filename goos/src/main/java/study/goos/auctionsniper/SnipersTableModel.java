package study.goos.auctionsniper;

import study.goos.auctionsniper.SniperSnapshot.SniperState;

import javax.swing.table.AbstractTableModel;

import static study.goos.auctionsniper.SniperSnapshot.SniperState.JOINING;

public class SnipersTableModel extends AbstractTableModel {

    private static final String[] STATUS_TEXT = {"Joining", "Bidding", "Lost", "Winning", "Winning"};
    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, JOINING);

    private SniperSnapshot snapshot = STARTING_UP;

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

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
        return Column.at(columnIndex).valueIn(snapshot);
    }

    public void sniperStatusChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
    }
}
