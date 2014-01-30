import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Logger {
	private String logsLocation;
	private List<String> logsContent;
	private String today;
	private String hour;
	final static Charset ENCODING = StandardCharsets.UTF_8;
	private Calendar cal = Calendar.getInstance();
	SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");
	SimpleDateFormat notDottedHour = new SimpleDateFormat("HHmmss");
	SimpleDateFormat dottedHour = new SimpleDateFormat("HH:mm:ss");
	
	public Logger(){
		logsLocation = System.getProperty("user.dir") + "/Logs/";
		logsContent = new ArrayList<String>();
		cal.add(Calendar.DATE, 1);
		today = ddMMyyyy.format(cal.getTime());
		File dir = new File(logsLocation);
		if (!dir.exists()){
			dir.mkdir();
		}
	}
	
	public void Log(String msg){
		hour = dottedHour.format(cal.getTime());
		logsContent.add(today + "-" + hour + "	" + msg);
	}
	
	public void SaveLogs() throws IOException{
		if (logsContent.size() > 0){
			File log = new File(logsLocation + today + "-" + notDottedHour.format(cal.getTime()) + ".txt");
			if (!log.exists())
				log.createNewFile();
			
			Files.write(log.toPath(), logsContent, ENCODING);
		}
	}
}
