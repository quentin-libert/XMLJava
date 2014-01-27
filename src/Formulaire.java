import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolTip;


public class Formulaire {
	private Parser parser;
	private DataBase db;
	
	public Formulaire(Parser parser) {
		this.parser = parser;
		this.db = parser.db;
	}
	
	public void BuildUI(JFrame frame){
		JPanel grid = new JPanel();
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		int tableCount = 0;

		for(Table tb : db.getTables()){
			tableCount++;
			JPanel container = new JPanel();
			container.setBackground(Color.white);
			container.setPreferredSize(new Dimension(400, 250));
			container.setLayout(new GridBagLayout());
			GridBagConstraints contConstraints = new GridBagConstraints();
			contConstraints.gridx = 0;
			contConstraints.gridy = 0;
			container.add(new JLabel(tb.getName()), contConstraints);
			
			JPanel colContainer = new JPanel();
			colContainer.setLayout(new GridBagLayout());
			GridBagConstraints colConstraints = new GridBagConstraints();
			colConstraints.gridx = 0;
			colConstraints.gridy = 0;
			
			int colCount = 0;
			final List<JTextField> fields = new ArrayList<JTextField>();
			for(Column col : tb.getColumns()){
				colCount++;
				JPanel singleCol = new JPanel();
				singleCol.setBackground(Color.white);
				JLabel label = new JLabel(col.getName());
				label.setPreferredSize(new Dimension(120, 30));
				JTextField txtField = new JTextField();
				fields.add(txtField);
				txtField.setPreferredSize(new Dimension(180, 30));
				singleCol.add(label);
				singleCol.add(txtField);
				colConstraints.gridy = colCount;
				colContainer.add(singleCol, colConstraints);
			}
			
			contConstraints.gridy = 1;
			container.add(colContainer, contConstraints);
			
			JButton buttonAdd = new JButton("Add");
			buttonAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String values = "";
                	for (JTextField field : fields){
                		values += field.getText() + "|";
                		field.setText("");
                	}
            		JOptionPane.showMessageDialog(null, values);
                }
            });
			
			contConstraints.gridy = 2;
			container.add(buttonAdd, contConstraints);
			
			gridConstraints.gridy = tableCount;
			grid.add(container, gridConstraints);
		}
		
		frame.setContentPane(grid);
		frame.setVisible(true);
	}
	
	public void BuildUIForSingleTable(JPanel container, Table tb){
		JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(box);
		panel.add(new JLabel(tb.getName()));
		
		JPanel colContainer = new JPanel();
		colContainer.setLayout(new BoxLayout(colContainer, BoxLayout.PAGE_AXIS));
		
		final List<JTextField> fields = new ArrayList<JTextField>();
		for(Column col : tb.getColumns()){
			JPanel singleCol = new JPanel();
			singleCol.setBackground(Color.white);
			JLabel label = new JLabel(col.getName());
			label.setPreferredSize(new Dimension(120, 30));
			JTextField txtField = new JTextField();
			fields.add(txtField);
			txtField.setPreferredSize(new Dimension(180, 30));
			singleCol.add(label);
			singleCol.add(txtField);
			colContainer.add(singleCol);
		}
		
		panel.add(colContainer);
		
		JButton buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String values = "";
            	for (JTextField field : fields){
            		values += field.getText() + "|";
            		field.setText("");
            	}
        		JOptionPane.showMessageDialog(null, values);
            }
        });
		
		panel.add(buttonAdd);
		panel.add(Box.createVerticalGlue());
		container.add(panel, BorderLayout.CENTER);
		container.revalidate();
	}
	
	public Parser getParser() {
		return parser;
	}
	
}
