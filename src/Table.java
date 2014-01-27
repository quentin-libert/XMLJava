import java.util.ArrayList;
import java.util.List;


public class Table {
	
	private String name;
	private List<Column> list_column;
	
	public Table(String name) {
		this.name = name;
		list_column = new ArrayList<Column>();
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
}
