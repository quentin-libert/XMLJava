import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Parser {
	
	public DataBase db;
	
	public Parser(File fichier) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(fichier);
			parseXML(doc);
		} catch (ParserConfigurationException e) {
			System.out.println("problème lors de la definition du DocumentBuilder");
		} catch (SAXException e) {
			System.out.println("problème lors de l'ouverture du fichier");
		} catch (IOException e) {
			System.out.println("fichier non trouvé");
		}
	}

	private void parseXML(Document doc) {
		Element meta = (Element) doc.getElementsByTagName("metaInformations").item(0);
		
		String dbname = meta.getChildNodes().item(1).getFirstChild().getNodeValue();
		String creator = meta.getChildNodes().item(3).getFirstChild().getNodeValue();
		String date = meta.getChildNodes().item(5).getFirstChild().getNodeValue();
		
		db = new DataBase(dbname, creator, date);
		
		Element tables = (Element) doc.getElementsByTagName("tables").item(0);
		
		for(int i = 0; i < tables.getElementsByTagName("table").getLength(); i++) {
			Element columns = (Element) tables.getElementsByTagName("table").item(i);
			String Tname = columns.getChildNodes().item(1).getFirstChild().getNodeValue();
			Table t = new Table(Tname);
			for(int j = 0; j < columns.getElementsByTagName("column").getLength(); j++) {
				String Cname = columns.getElementsByTagName("column").item(j).getChildNodes().item(1).getFirstChild().getNodeValue();
				String type = columns.getElementsByTagName("column").item(j).getChildNodes().item(3).getFirstChild().getNodeValue();
				t.addColumn(new Column(Cname, type));
			}
			db.addTable(t);
		}
	}
	
}
