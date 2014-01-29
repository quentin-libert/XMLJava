import java.util.List;

import javax.swing.table.AbstractTableModel;


public class DynamicTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final List<Row> rows;
	private final List<String> headers;
 
    public DynamicTableModel(List<Row> rows, List<String> headers) {
        super();
        this.rows = rows;
        this.headers = headers;
    }
    
    public int getRowCount() {
        return rows.size();
    }
 
    public int getColumnCount() {
        return headers.size();
    }
 
    public String getColumnName(int columnIndex) {
        return headers.get(columnIndex);
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            default:
                return rows.get(rowIndex).getRecords().get(columnIndex).getValue();
        }
    }
 
    public void addRow(Row r) {
        fireTableRowsInserted(rows.size() -1, rows.size() -1);
    }
 
    public void removeRow(int rowIndex) {
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
