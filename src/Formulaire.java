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

import javax.sql.RowSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolTip;


public class Formulaire {
	private Parser parser;
	private DataBase db;
	private Table currentTable;
	private DynamicTableModel model;
	JTable dataTable;
	Logger logger;
	
	public Formulaire(Parser parser, Logger logger) {
		this.parser = parser;
		this.db = parser.db;
		this.logger = logger;
	}
	
	public void BuildUI(JFrame frame){
		JPanel grid = new JPanel();
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		int tableCount = 0;
		
		final Table currentTb; 
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
                		values += field.getText() + " ";
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
		currentTable = tb;
		final JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(box);
		
		JPanel colContainer = new JPanel();
		colContainer.setLayout(new BoxLayout(colContainer, BoxLayout.PAGE_AXIS));
		
		final List<JTextField> fields = new ArrayList<JTextField>();
		final List<String> colNames = new ArrayList<String>();
		for(Column col : tb.getColumns()){
			JPanel singleCol = new JPanel();
			JLabel label = new JLabel(col.getName());
			label.setPreferredSize(new Dimension(120, 30));
			JTextField txtField = new JTextField();
			fields.add(txtField);
			colNames.add(col.getName());
			txtField.setPreferredSize(new Dimension(180, 30));
			singleCol.add(label);
			singleCol.add(txtField);
			colContainer.add(singleCol);
		}
		
		List<String> headers = new ArrayList<String>();
		for(Column col : currentTable.getColumns())
			headers.add(col.getName());
		
		this.model = new DynamicTableModel(currentTable.getRows(), headers);
		dataTable = new JTable(model);

		JButton buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Row r = new Row();
            	String msg = "insert values ";
            	for (JTextField field : fields){
            		r.addRecord(new Record(colNames.get(fields.indexOf(field)), field.getText()));
            		msg += field.getText() + " ";
            		field.setText("");
            	}
            	msg += "in table " + currentTable.getName() + " from databse " + db.getDbName();
            	logger.Log(msg);
            	currentTable.addRow(r);
            	model.addRow(r);
            }
        });
		
		JButton buttonRem = new JButton("Remove");
		buttonRem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] selection = dataTable.getSelectedRows();
            	for(int i = selection.length - 1; i >= 0; i--){
            		model.removeRow(selection[i]);
                	logger.Log("remove row " + currentTable.getRows().get(selection[i]).seeRecords() + "in table " + currentTable.getName() + " from databse " + db.getDbName());
            		currentTable.RemoveRow(selection[i]);
            	}
            }
        });
		
		JPanel btnContainer = new JPanel();
		btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.LINE_AXIS));
		btnContainer.add(buttonAdd);
		btnContainer.add(buttonRem);
		
		
		dataTable.setPreferredSize(new Dimension(1200, 300));
		colContainer.setPreferredSize(new Dimension(1200, 400));
		colContainer.add(btnContainer);
		colContainer.add(Box.createVerticalStrut(400));
		panel.add(new JScrollPane(dataTable));
		panel.add(colContainer);
		container.add(panel, BorderLayout.CENTER);
	}
	
	public Parser getParser() {
		return parser;
	}
	
}
