
public class Record {
	private String ColumnName;
	private String Value;
	
	public Record(String columnName, String value){
		this.ColumnName = columnName;
		this.Value = value;
	}
	
	public String getColumnName() {
		return this.ColumnName;
	}
	
	public String getValue() {
		return this.Value;
	}
}
