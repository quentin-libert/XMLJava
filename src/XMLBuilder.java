import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLBuilder {
	private DataBase db;
	
	public XMLBuilder(DataBase db){
		this.db = db;
	}
	
	public String BuildXMLFile(String fileLocation, Boolean editing){
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();  
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();  
			Element rootElement = document.createElement(db.getDbName());  
			document.appendChild(rootElement);
			
			for(Table tb : db.getTables()){
				if (tb.getRows().size() > 0){
					Element table = document.createElement(tb.getName());  
					rootElement.appendChild(table);
					for (Row r : tb.getRows()){
						Element row = document.createElement(tb.getName().substring(0, tb.getName().length()-1));  
						table.appendChild(row);
						for (Record rec : r.getRecords()){
							Element elm = document.createElement(rec.getColumnName());  
							elm.appendChild(document.createTextNode(rec.getValue()));  
							row.appendChild(elm);  
						}
					}
				}
			}
			
			String filePath;
			if (!editing)
				filePath = fileLocation + "/" + db.getDbName()+".xml";
			else
				filePath = fileLocation;
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();  
			Transformer transformer = transformerFactory.newTransformer();  
			DOMSource domSource = new DOMSource(document);  
			StreamResult streamResult = new StreamResult(new File(filePath));  
				  
			transformer.transform(domSource, streamResult);  
			JOptionPane.showMessageDialog(null, "Le fichier a été enregistré.");
			return filePath;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erreur dans l'enregistrement du fichier.");
			return null;
		}
	}
}
