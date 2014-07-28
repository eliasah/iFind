package engine.search;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ResultTable extends JPanel {

	private JTable tblresult;
	private MyTableModel model;
	public Object[][] data;
	
	public void setData(Object[][] d){
		this.data = d;
	}
	
	public ResultTable() {
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		model = new MyTableModel();
		tblresult = new JTable(model);
		scrollPane.setViewportView(tblresult);
	}
	
	
// tableau des resultats a modifier
	
	class MyTableModel extends AbstractTableModel {
		private String[] columnNames = {"Nom du fichier",
				"Chemin d Acces",
				"Taille"};
		
		Object[][] data = {
				{"toto.txt","C:/","1ko"},
				{"tata.txt","C:/","2ko"},
				{"titi.txt","C:/","4ko"}
				
		};
		
	
		
		private boolean DEBUG;



 
		public MyTableModel() {
			super();
		}
		
		//public void setData(Object[][] d){
		//	this.data = d ;
		//}
		
		
		public MyTableModel(Object[][] data) {
			super();
			this.data = data;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}


		public boolean isCellEditable(int row, int col) {
			if (col < 2) return false;
			else return true;
		}

		/*
		 * Don't need to implement this method unless your table's
		 * data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value
						+ " (an instance of "
						+ value.getClass() + ")");
			}

			data[row][col] = value;
			fireTableCellUpdated(row, col);

			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i=0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j=0; j < numCols; j++) {
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
}