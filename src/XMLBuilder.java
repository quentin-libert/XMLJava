import java.io.File;

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
	
	public void BuildXMLFile(String fileLocation){
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();  
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();  
			Element rootElement = document.createElement(db.getDbName());  
			document.appendChild(rootElement);
			
			for(Table tb : db.getTables()){
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
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();  
			Transformer transformer = transformerFactory.newTransformer();  
			DOMSource domSource = new DOMSource(document);  
			StreamResult streamResult = new StreamResult(new File("/Users/romanleichnig/Desktop/"+db.getDbName()+".xml"));  
				  
			transformer.transform(domSource, streamResult);  
				  
			
		} catch (Exception e) {
		}
	}
}
