import java.util.ArrayList;
import java.util.List;


public class Table {
	
	private String name;
	private List<Column> list_column;
	private List<Row> Rows;
	
	public Table(String name) {
		this.name = name;
		list_column = new ArrayList<Column>();
		this.Rows = new ArrayList<Row>();
	}
	
	public void addColumn(Column c) {
		list_column.add(c);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Column> getColumns() {
		return list_column;
	}
	
	public void addRow(Row r){
		Rows.add(r);
	}
	
	public void RemoveRow(int index){
		this.Rows.remove(index);
	}
	
	public List<Row> getRows(){
		return this.Rows;
	}
	
	public String SeeRow(){
		String res = "";
		for(Row r : Rows){
			res += r.seeRecords() + '\n';
		}
		return res;
	}
}
