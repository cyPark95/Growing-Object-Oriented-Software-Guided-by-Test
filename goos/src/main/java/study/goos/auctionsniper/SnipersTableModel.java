package study.goos.auctionsniper;

import com.objogate.exception.Defect;
import study.goos.auctionsniper.SniperSnapshot.SniperState;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SnipersTableModel extends AbstractTableModel {

    private static final String[] STATUS_TEXT = {"Joining", "Bidding", "Lost", "Winning", "Winning"};

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    private List<SniperSnapshot> snapshots =  new ArrayList<>();

    @Override
    public int getRowCount() {
        return snapshots.size();
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
    }

    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        int row = rowMatching(newSnapshot);
        snapshots.set(row, newSnapshot);
        fireTableRowsUpdated(row, row);
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }

    public void addSniper(SniperSnapshot snapshot) {
        int lastRow = snapshots.size();
        snapshots.add(snapshot);
        fireTableRowsInserted(lastRow, lastRow);
    }

    private int rowMatching(SniperSnapshot newSnapshot) {
        for (int i = 0; i < snapshots.size(); i++) {
            if (newSnapshot.isForSameItemAs(snapshots.get(i))) {
                return i;
            }
        }

        throw new Defect("Cannot find match for " + newSnapshot);
    }
}
