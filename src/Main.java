import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import java.util.HashMap;
import java.util.List;

public class Main {

	private JFrame frame;
	private MyFileChooser xml;

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
	}
	
	public void createFrame() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("xml.png"));
		frame.setTitle("XMLBdD administrator v1.0");
		frame.setResizable(false);
		frame.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - 1280/2, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - 800/2, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMenu() {

		JMenuBar menubar = new JMenuBar();
        ImageIcon iconNew = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/new.png"));
        ImageIcon iconOpen = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/open.png"));
        ImageIcon iconSave = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/save.png"));
        ImageIcon iconSaveAs = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/saveas.png"));
        ImageIcon iconExit = new ImageIcon(Toolkit.getDefaultToolkit().getImage("icons/exit.png"));

        JMenu file = new JMenu("Fichier");
        JMenu help = new JMenu("?");

        JMenuItem fileNew = new JMenuItem("Nouveau", iconNew);
        fileNew.setToolTipText("Cr�er un nouveau fichier");
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
                System.exit(0);
            }
        });

        file.add(fileNew);
        file.add(fileOpen);
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
		JOptionPane.showMessageDialog(null,"Afin de cr�er votre nouvelle base de donn�es, merci de s�lectionner un fichier xml de structure de type Module 1");
		xml = new MyFileChooser();
		xml.chooseXML();
		
		// CREATION DES FORMULAIRES
		Formulaire form = new Formulaire(new Parser(xml.fichier));
		form.BuildUI(frame);

	}
	
	public void openFile() {
		JFileChooser open = new JFileChooser(".");
        open.showOpenDialog(null);
	}
	
	public void saveFile() {
		JOptionPane.showMessageDialog(null, "Tu as lanc� la fonction \"Save\"");
	}
	
	public void saveAsFile() {
		JFileChooser save = new JFileChooser();
		save.showSaveDialog(null);
	}
	
	public void closeApp() {
		System.exit(0);
	}
	
	public void infosApp() {
		JOptionPane.showMessageDialog(null, "Tu as lanc� la fonction \"infosApp\"");
	}

}
