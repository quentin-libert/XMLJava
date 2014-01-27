import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class XPathParser extends JFrame{

	private JPanel XpathPanel;
	//Xpath
	private static String path = "";
	//file path
	private String filePath;
	
	private File File;
	
	private JTextField Xpath;
	
	private static JTextArea area;
	
	private JButton button, clear;

	public XPathParser(File file)
	{
		this.File = file;
		init();
	}
	
	private void init()
	{
		Container contentPane = this.getContentPane();

		XpathPanel = new JPanel();
		XpathPanel.setLayout(new FlowLayout());
		contentPane.add(XpathPanel,BorderLayout.CENTER);
	
		Xpath = new JTextField();
		Xpath.setColumns(35);
		Xpath.setEditable(true);
		
		Xpath.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {	
				path += "" + arg0.getKeyChar();
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		area = new JTextArea("", 20, 50);
		area.setEditable(false);
		area.setBackground(Color.WHITE);
		
		button = new JButton();
		button.setText("Enter");
		button.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println(path);
						read();
						
					}
			} );
		
		clear = new JButton();
		clear.setText("Clear");
		clear.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						path = "";
						area.setText("");
						Xpath.setText("");
					}
			} );
		
		XpathPanel.add(Xpath);
		XpathPanel.add(button);
		XpathPanel.add(clear);
		XpathPanel.add(area);
		
		this.setTitle("XMLBdD administrator v1.0");
		this.setSize(600, 400);
		this.setVisible(true);
	}	  
	 
	 //analyse le XPATH et affiche sur le textarea
    public void read() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            //InputStream in = XPathParser.class.getClassLoader().getResourceAsStream(filePath);
            Document doc = builder.parse(File);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile(path);
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println("name = " + nodes.item(i).getNodeValue());
                area.setText("name = " + nodes.item(i).getNodeValue());
                    }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }	
}