import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {
	public DataBase db;
	File fichier;
	Logger logger;
	
	public Parser(File fichier, boolean existingXML, Logger logger) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		this.logger = logger;
		this.fichier = fichier;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(fichier);
			validateXML();
	
			if (!existingXML)
				parseXML(doc);
			else
				parseExistingFile(doc);
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

		logger.Log("parsed "+fichier.getAbsolutePath()+" for create database");
	}
	
	private void parseExistingFile(Document doc){
		Element root = doc.getDocumentElement();
		NodeList tables = root.getChildNodes();
		db = new DataBase("XMLPROJECT", "applicationUser", "TODAY");
		for(int i = 0; i < tables.getLength(); i++){
			Node table = tables.item(i);
			if (table.getNodeName() != "#text") {
				Table tb = new Table(table.getNodeName());
				NodeList occurences = table.getChildNodes();
				if (occurences.getLength() > 0){
					Node first = occurences.item(1);
					NodeList columns = first.getChildNodes();
					for(int j = 0; j < columns.getLength(); j++){
						Node col = columns.item(j);
						if (col.getNodeName() != "#text") {
							tb.addColumn(new Column(col.getNodeName(), "varchar"));
						}
					}
					
					for(int k = 0; k < occurences.getLength(); k++){
						Node occurence = occurences.item(k);
						if (occurence.getNodeName() != "#text") {
							Row r = new Row();
							NodeList values = occurence.getChildNodes();
							for(int l = 0; l < columns.getLength(); l++){
								Node value = values.item(l);
								if (value.getNodeName() != "#text") {
									r.addRecord(new Record(value.getNodeName(), value.getTextContent()));
								}
							}
							tb.addRow(r);
						}
					}
				}
				db.addTable(tb);
		    }
		}

		logger.Log("parsed "+fichier.getAbsolutePath()+" for edit database");
	}

	private Boolean validateXML() throws SAXException, IOException{
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
	    SchemaFactory factory = SchemaFactory.newInstance(language);
	    Schema schema = factory.newSchema(new File(System.getProperty("user.dir") + "/Schema/"+"database.xsd"));
	    Source fileToValidate = new StreamSource(fichier); 
	    try{
	    	schema.newValidator().validate(fileToValidate);
	    	System.out.println("valid file");
	    	return true;
	    } catch (SAXException e) {
	    	String message = "Fichier xml invalide ! \n ";
			JOptionPane.showMessageDialog(null, message + e.getMessage());
			logger.Log("error while validating "+fichier.getAbsolutePath()+" : " + e.getMessage());
	    	return false;
	    }
	}
}
