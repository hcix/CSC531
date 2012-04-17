package progAdmin;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel implements TableModelListener 
{
	private static final long serialVersionUID = 1L;
	private String [] columnNames = {"cnumber", "firstname", "lastname", "caneid", "email", "permissions"};
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
