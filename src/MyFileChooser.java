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
	
	public void chooseDirectoryToSave(DataBase db){
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			XMLBuilder builder = new XMLBuilder(db);
			builder.BuildXMLFile(chooser.getSelectedFile().getPath());
	    } else {
	      System.out.println("bad selection");
	    }
	}
	
}
