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
public class SharedDataModel extends DefaultListModel implements TableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Reviewed","Title","Details"};
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public SharedDataModel(ResourceManager rm){
		this.rm=rm;
	}
//-----------------------------------------------------------------------------
	/* Implement ListModel */
	@Override
	public Object getElementAt(int index) {
		return(rm.getItems().get(index));
	}
//-----------------------------------------------------------------------------
	@Override
	public int getSize() {
		return(rm.getItems().size());
	}
//-----------------------------------------------------------------------------
	/* Implement the TableModel interface */
	@Override
	public int getRowCount() {
		return tableModel.getColumnCount();
	}
//-----------------------------------------------------------------------------
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
//-----------------------------------------------------------------------------
	public void rowChanged(int row){
		fireContentsChanged(this,row, row);
	}
//-----------------------------------------------------------------------------
	@Override
	public String getColumnName(int columnIndex) {
		return tableModel.getColumnName(columnIndex);
	}
//-----------------------------------------------------------------------------
	@Override
	public Class getColumnClass(int columnIndex) {
		return tableModel.getColumnClass(columnIndex);
	}
//-----------------------------------------------------------------------------
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
//-----------------------------------------------------------------------------
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tableModel.getValueAt(rowIndex, columnIndex);
	}
//-----------------------------------------------------------------------------
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		tableModel.setValueAt(aValue, rowIndex, columnIndex);
	}
//-----------------------------------------------------------------------------
	@Override
	public void addTableModelListener(TableModelListener l) {
		tableModel.addTableModelListener(l);
	}
//-----------------------------------------------------------------------------
	@Override
	public void removeTableModelListener(TableModelListener l) {
		tableModel.removeTableModelListener(l);
	}
//=============================================================================
		/* Create the TableModel object */
		private TableModel tableModel = new AbstractTableModel(){
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}
		//-----------------------------------------------------------------------------
			@Override
			public int getRowCount() {
				return rm.getItems().size();
			}
		//-----------------------------------------------------------------------------
			@Override
			public String getColumnName(int col) {
			    return columnNames[col];
			}
		//-----------------------------------------------------------------------------
		    @Override
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
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int c) {
			    return getValueAt(0, c).getClass();
			}
	//-----------------------------------------------------------------------------
			/*
			 * Implement this method if your table's data can change.
			 */
			@Override
			public void setValueAt(Object value, int row, int col) {
				fireTableCellUpdated(row, col);
				rowChanged(row);
			} 
	//-----------------------------------------------------------------------------        
		};
//=============================================================================
		
}