package progAdmin.itemsToReview;

import javax.swing.DefaultListModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import program.ResourceManager;
//-----------------------------------------------------------------------------
/**
 * JDOC
 */
public class SharedListDataModel extends DefaultListModel implements TableModel{
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Reviewed","Title","Details"};
	ResourceManager rm;
//-----------------------------------------------------------------------------
	SharedListDataModel(ResourceManager rm){
		super();
		this.rm=rm;
	}
//-----------------------------------------------------------------------------
	public void rowChanged(int row) {
		fireContentsChanged(this, row, row); 
	}
//=============================================================================
	private TableModel tableModel = new AbstractTableModel() {
	//-----------------------------------------------------------------------------
	    public int getColumnCount() {
	    	return columnNames.length;
	    }
    //-----------------------------------------------------------------------------
	    public int getRowCount() {
	    	//return rm.getItems().size();
	    	return size();
	    }
    //-----------------------------------------------------------------------------
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
    //-----------------------------------------------------------------------------
	    public Object getValueAt(int row, int col) {

	    	if(col==0){
	    		return (rm.getItems().get(row).isReviewed());
	    	} else if(col==1){
	    		return (rm.getItems().get(row).getTitle());
	    	} else {
	    		return (rm.getItems().get(row).getDetails());
	    	}
	    	
	    }
    //-----------------------------------------------------------------------------
	    /*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell.  If we didn't implement this method,
	     * then the 'reviewed' column would contain text ("true"/"false"),
	     * rather than a check box.
	     */
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
    //-----------------------------------------------------------------------------
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
//-----------------------------------------------------------------------------
	//Implement the TableModel interface.
	public int getRowCount() {
		return tableModel.getRowCount();
	}
	//-----------------------------------------------------------------------------
	public int getColumnCount() {
		return tableModel.getColumnCount();
	}
	//-----------------------------------------------------------------------------
	public String getColumnName(int columnIndex) {
		return tableModel.getColumnName(columnIndex);
	}
	//-----------------------------------------------------------------------------
	public Class getColumnClass(int columnIndex) {
		return tableModel.getColumnClass(columnIndex);
	}
	//-----------------------------------------------------------------------------
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return tableModel.isCellEditable(rowIndex, columnIndex);
	}//-----------------------------------------------------------------------------
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tableModel.getValueAt(rowIndex, columnIndex);
	}//-----------------------------------------------------------------------------
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		tableModel.setValueAt(aValue, rowIndex, columnIndex);
	}//-----------------------------------------------------------------------------
	public void addTableModelListener(TableModelListener l) {
		tableModel.addTableModelListener(l);
	}
	//-----------------------------------------------------------------------------
	public void removeTableModelListener(TableModelListener l) {
		tableModel.removeTableModelListener(l);
	}
//=============================================================================
}