import java.util.ArrayList;
import java.util.List;

public class Row {
	private List<Record> Records;
	
	
	public Row(){
		this.Records = new ArrayList<Record>();
	}
	
	public void addRecord(Record r){
		this.Records.add(r);
	}
	
	public List<Record> getRecords(){
		return this.Records;
	}
	
	public String seeRecords(){
		String res = "";
		for (Record r : Records){
			res += r.getValue() + " ";
		}
		return res;
	}
}
