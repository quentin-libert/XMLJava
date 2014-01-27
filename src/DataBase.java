import java.util.ArrayList;
import java.util.List;


public class DataBase {
	private String dbname;
	private String creator;
	private String date;
	private List<Table> list_table;
	
	public DataBase(String dbname, String creator, String date) {
		this.dbname = dbname;
		this.creator = creator;
		this.date = date;
		list_table = new ArrayList<Table>();
	}
	
	public void addTable(Table t) {
		list_table.add(t);
	}
	
	public String getDbName() {
		return dbname;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public String getDate() {
		return date;
	}
	
	public List<Table> getTables() {
		return list_table;
	}
}
