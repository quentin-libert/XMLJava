import java.io.File;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class MyFileChooser extends JFileChooser {
	
	public File fichier;

	public MyFileChooser() {
	}
	
	public void chooseXML() {
		FiltreSimple xml = new FiltreSimple("Fichier xml", ".xml");
		JFileChooser newF = new JFileChooser(".");
		newF.changeToParentDirectory();
		newF.addChoosableFileFilter(xml);
		newF.setFileFilter(xml);
		newF.setAcceptAllFileFilterUsed(false);
		int retour = newF.showOpenDialog(this);
		System.out.println(retour + " | " + JFileChooser.APPROVE_OPTION);
		if(retour == JFileChooser.APPROVE_OPTION) {
			fichier = newF.getSelectedFile();
		}
	}
	
}
