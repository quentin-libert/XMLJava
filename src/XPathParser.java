package XMLModule2_2013;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

public class XPathParser extends JFrame{

	private JPanel XpathPanel;
	//Xpath
	private static String path = "";
	
	private static File file;

	private JTextField Xpath;
	
	private static JTextArea area, resultArea;
	
	private JButton button, clear, open;
	
	public XPathParser()
	{
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
			
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
		});
		
		
		area = new JTextArea("", 20, 50);
		area.setEditable(false);
		area.setBackground(Color.WHITE);
		
		
		resultArea = new JTextArea("", 15, 50);
		resultArea.setEditable(false);
		resultArea.setBackground(Color.WHITE);
		
		button = new JButton();
		button.setText("Enter");
		button.setEnabled(false);
		button.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println(path);
						read();
						
					}
			} );
		
		open = new JButton();
		open.setText("OPEN");
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				openFile();
				FileInputStream fileInputStream;
				String tmp = "";
				try {
					fileInputStream = new FileInputStream(file);
					InputStream in = fileInputStream;
					tmp = inputStream2String(in);
		            area.setText(tmp); 
				} catch (FileNotFoundException e) {
				
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				clear.setEnabled(true);
				button.setEnabled(true);
				
			}
		});
		
		clear = new JButton();
		clear.setText("Clear");
		clear.setEnabled(false);
		clear.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						path = "";
						resultArea.setText("");
						Xpath.setText("");
					}
			} );
		
		
		
		XpathPanel.add(Xpath);
		XpathPanel.add(button);
		XpathPanel.add(clear);
		XpathPanel.add(open);
		XpathPanel.add(resultArea);
		XpathPanel.add(area);
		XpathPanel.add(new JScrollPane(area));
		XpathPanel.add(new JScrollPane(resultArea));
		
		this.setTitle("XMLBdD administrator v1.0");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	  
	 
    public void read() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            
            InputStream in = new FileInputStream(file);
            
            Document doc = builder.parse(in);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile(path);
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            String sub = "@";
            int tmp = path.indexOf(sub);
            if (tmp >= 0) 
            {
            	for (int i = 0; i < nodes.getLength(); i++) 
            	{
                    System.out.println(nodes.item(i).getNodeName() + " = " + nodes.item(i).getNodeValue());
                    resultArea.append(nodes.item(i).getNodeName() + " = " + nodes.item(i).getNodeValue()+"\n");
                    
                }
				
			} 
            else 
            {
            	for (int i = 0; i < nodes.getLength(); i++) 
            	{
                    System.out.println(nodes.item(i).getNodeName() + " = " + nodes.item(i).getTextContent());
                    resultArea.append(nodes.item(i).getNodeName() + " = " + nodes.item(i).getTextContent()+"\n");
            	}
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
    
    public void openFile() {
		JFileChooser open = new JFileChooser();
		open.setFileSelectionMode(JFileChooser.FILES_ONLY);
		open.removeChoosableFileFilter(open.getFileFilter());
		FileFilter filter = new FileFilter();
		open.setFileFilter(filter);
	    open.showOpenDialog(null);
	    file = open.getSelectedFile();
  	}
    
    public static String inputStream2String(InputStream is) throws IOException{ 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        int i=-1; 
        while((i=is.read())!=-1){ 
        baos.write(i); 
        } 
       return baos.toString(); 
    }
    
    class FileFilter extends javax.swing.filechooser.FileFilter {
    	   public boolean accept( File f ) {
    	      if( f.isDirectory() || f.getName().endsWith( ".xml" ) ) {
    	         return true;
    	      } else {
    	         return false;
    	      }
    	   }
	   public String getDescription() {
		      return "xml";
		   }
		}
	    
	}


