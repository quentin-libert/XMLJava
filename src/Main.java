import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {

	private JFrame frame;
	private MyFileChooser xml;
	private JComboBox<String> combo; 
	private HashMap<String, Table> Tables;
	private Formulaire form;
	private JPanel container = new JPanel();
	private DataBase runningDb;
	private String runningFile;
	private Logger logger;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createFrame();
		createMenu();
		this.logger = new Logger();
	}
	
	public void createFrame() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("xml.png"));
		frame.setTitle("XMLBdD administrator v1.0");
		frame.setResizable(false);
		frame.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - 1280/2, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - 800/2, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(frame.WIDTH, frame.HEIGHT));
		
		frame.setContentPane(container);
	}
	
	public void createMenu() {

		JMenuBar menubar = new JMenuBar();
        ImageIcon iconNew = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/new.png"));
        ImageIcon iconOpen = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/open.png"));
        ImageIcon iconSave = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/save.png"));
        ImageIcon iconSaveAs = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/saveas.png"));
        ImageIcon iconExit = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/exit.png"));
        ImageIcon iconXPath = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/X.png"));

        JMenu file = new JMenu("Fichier");
        JMenu help = new JMenu("?");

        JMenuItem fileNew = new JMenuItem("Nouveau", iconNew);
        fileNew.setToolTipText("Créer un nouveau fichier");
        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        fileNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newFile();
            }
        });

        JMenuItem fileOpen = new JMenuItem("Ouvrir", iconOpen);
        fileOpen.setToolTipText("Ouvrir un fichier existant");
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        fileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                openFile();
            }
        });
        
        JMenuItem XPathQueries = new JMenuItem("Requêtes XPath", iconXPath);
        fileOpen.setToolTipText("Exécuter des requêtes XPath sur fichier existant");
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        XPathQueries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	XPathQueries();
            }
        });


        JMenuItem fileSave = new JMenuItem("Enregistrer", iconSave);
        fileSave.setToolTipText("Enregistrer le fichier courrant");
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        
        fileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                saveFile();
            }
        });
        
        JMenuItem fileSaveAs = new JMenuItem("Enregistrer Sous...", iconSaveAs);
        fileSaveAs.setToolTipText("Enregistrer le fichier courant sous un nouveau fichier");
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        
        fileSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                saveAsFile();
            }
        });

        JMenuItem fileExit = new JMenuItem("Quitter", iconExit);
        fileExit.setToolTipText("Quitter l'application");
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        fileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
					closeApp();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

        file.add(fileNew);
        file.add(fileOpen);
        file.add(XPathQueries);
        file.add(fileSave);
        file.add(fileSaveAs);
        file.addSeparator();
        file.add(fileExit);

        menubar.add(file);
        
        JMenuItem infos = new JMenuItem("A Propos", iconNew);
        infos.setToolTipText("Informations sur l'application");
        infos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        
        infos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                infosApp();
            }
        });
        
        help.add(infos);
        
        menubar.add(help);

        frame.setJMenuBar(menubar);
    }
	
	public void newFile() {
		JOptionPane.showMessageDialog(null,"Afin de créer votre nouvelle base de données, merci de sélectionner un fichier xml de structure de type Module 1");
		xml = new MyFileChooser();
		xml.chooseXML();
		logger.Log("using struct " + xml.fichier.getAbsolutePath() + " in order to create a new database");
		
		// CREATION DES FORMULAIRES
		frame.getContentPane().removeAll();
		form = new Formulaire(new Parser(xml.fichier, false, logger), logger);
		this.runningDb = form.getParser().db;
		List<Table> tables = form.getParser().db.getTables();
		Tables = new HashMap<String, Table>();
		for(Table tb : tables){
			Tables.put(tb.getName(), tb);
		}

		JPanel comboContainer = new JPanel();
		comboContainer.setPreferredSize(new Dimension(frame.WIDTH, 30));
		JLabel label = new JLabel("Chosir la table à modifier :");
		label.setPreferredSize(new Dimension(180, 30));
		combo = new JComboBox<String>();
		combo.setPreferredSize(new Dimension(160, 30));
		combo.addActionListener(new ItemAction());
		for(Table table : tables){
			combo.addItem(table.getName());
		}
		
		comboContainer.add(label);
		comboContainer.add(combo);

		frame.getContentPane().add(comboContainer, BorderLayout.NORTH);
		frame.setVisible(true);

	}
	
	class ItemAction implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	    	Table table = Tables.get(combo.getSelectedItem());
	    	form.BuildUIForSingleTable(container, table);
	    	container.revalidate();
	    }               
	  }
	
	public void openFile() {
		xml = new MyFileChooser();
		xml.chooseExistingXML();
		this.runningFile = xml.fichier.getAbsolutePath();
		logger.Log("opening file " + runningFile + " for updating");
		

		frame.getContentPane().removeAll();
		form = new Formulaire(new Parser(xml.fichier, true, logger), logger);
		this.runningDb = form.getParser().db;
		List<Table> tables = form.getParser().db.getTables();
		Tables = new HashMap<String, Table>();
		for(Table tb : tables){
			Tables.put(tb.getName(), tb);
		}

		JPanel comboContainer = new JPanel();
		comboContainer.setPreferredSize(new Dimension(frame.WIDTH, 30));
		JLabel label = new JLabel("Chosir la table à modifier :");
		label.setPreferredSize(new Dimension(180, 30));
		combo = new JComboBox<String>();
		combo.setPreferredSize(new Dimension(160, 30));
		combo.addActionListener(new ItemAction());
		for(Table table : tables){
			combo.addItem(table.getName());
		}
		
		comboContainer.add(label);
		comboContainer.add(combo);

		frame.getContentPane().add(comboContainer, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	public void XPathQueries(){
		XPathParser xparser = new XPathParser();
		xparser.setVisible(true);
	}
	
	public void saveFile() {
		if(runningFile != null && runningFile != ""){
			File f = new File(runningFile);
			if (f.exists()){
				f.delete();
				XMLBuilder builder = new XMLBuilder(runningDb);
				this.runningFile = builder.BuildXMLFile(runningFile, true);
				logger.Log("saving file " + runningFile);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Il n'y a aucun fichier en cours d'édition !");
		}
	}
	
	public void saveAsFile() {
		xml = new MyFileChooser();
		this.runningFile = xml.chooseDirectoryToSave(runningDb);
		logger.Log("creating file " + runningFile + " for database " + runningDb);
	}
	
	public void closeApp() throws IOException {
		logger.SaveLogs();
		System.exit(0);
	}
	
	public void infosApp() {
		String message = "Projet XML - XMLBdD Administrator v1.0 \n";
		message += "Création Q. Libert - R. Leichnig - B. Graux - W. Qian \n";
		JOptionPane.showMessageDialog(null, message);
	}

}
