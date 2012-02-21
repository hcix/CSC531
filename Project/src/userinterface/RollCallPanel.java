package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import utilities.SwingHelper;

public class RollCallPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public RollCallPanel(final JFrame parent) {
		
		// create a table
		JTable rollCallTable = new JTable(new RollCallTable());
		
		// create import button
		JButton importRoll = new JButton("Import");
		// create button to add an officer
		JButton addOfficer = new JButton("Add");
		// create button to edit an officer
		JButton editOfficer = new JButton("Edit");
		// create button to delete an officer
		JButton deleteOfficer = new JButton("Delete");
		
		// add buttons to buttonPanel
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(importRoll);
		buttonPanel.add(addOfficer);
		buttonPanel.add(editOfficer);
		buttonPanel.add(deleteOfficer);
		
		// place panes
		this.add(new JScrollPane(rollCallTable), BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	class RollCallTable extends AbstractTableModel {
		private boolean DEBUG = false;
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Name",
                                        "Present",
                                        "Time Arrived",
                                        "Comment",
                                        };
        private Object[][] data = {
	    {"John Doe", new Boolean(false),"n/a","(day off)"},
	    {"Jane Roe", new Boolean(true),"12:00","(n/a)"},
	    {"Ray Moe", new Boolean(true),"12:30","mysteriously late"}
        };

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

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
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