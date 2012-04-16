package progAdmin.itemsToReview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.ui.SwingHelper;

/**
 * JDOC
 */
public class ManageItemsDialog  extends JDialog implements MouseListener{
private static final long serialVersionUID = 1L;
	//ItemToReview item;
	JTextField titleField;
	JTextArea textArea;
	//ArrayList<ItemToReview> items;
	final JTable table=new JTable();
	final JPanel itemsPanel = new JPanel();
	final ResourceManager rm;
//-----------------------------------------------------------------------------
	public ManageItemsDialog(final ResourceManager rm){
		super(rm.getGuiParent(), "Manage Review Items", true);
		this.rm=rm;
		this.setPreferredSize(new Dimension(700,700));
		this.setSize(new Dimension(700,700));

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});

		//items = rm.getItems();
		final JPanel mainPanel = new JPanel(new MigLayout());
		
		JButton addNewItemButton = 
				SwingHelper.createImageButton("Add Item", "icons/plusSign_48.png");
		addNewItemButton.addActionListener(new ActionListener() {
			AddItemDialog itemDialog = new AddItemDialog(rm);
			public void actionPerformed(ActionEvent e) {
				itemDialog.setVisible(true);
				itemDialog.setModal(true);
				//refresh items list from ResourceManager
				//items=rm.getItems();
				table.repaint();
				
			}
		});
		
		JButton deleteItemButton = SwingHelper
				.createImageButton("Delete Item", "icons/delete_48.png");
		deleteItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				//delete the selected item
				//items.remove(rowIndex);
				//rm.setItems(items);
				rm.removeItem(rowIndex);
				table.repaint();
			}
		});
		
		JButton editItemButton = SwingHelper
				.createImageButton("Edit Item", "icons/edit_48.png");
		editItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if(rowIndex>=0){
					//show the ReadItemDialog to display the item
		           // ReadItemDialog readItem = new ReadItemDialog(
		            	//	rm.getGuiParent(), items.get(rowIndex));
					ReadItemDialog readItem = new ReadItemDialog(
		            		rm.getGuiParent(), rm.getItems().get(rowIndex));
		            readItem.makeEditable();
		            readItem.setVisible(true);
		            //wait on the ReadItemDialog to be closed
		            readItem.setModal(true);
		            //items=rm.getItems();
		            table.repaint();
				}
			}
		});
		
		mainPanel.add(addNewItemButton);

		//Create toolbar to hold actions buttons
		JToolBar toolbar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
		toolbar.add(addNewItemButton);
		toolbar.add(deleteItemButton);
		toolbar.add(editItemButton);
		
		//Create the table of items
		itemsPanel.add(createItemsPanel());
		
		//Create a split pane with the two scroll panes in it
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				mainPanel, itemsPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		
	    Container contentPane = getContentPane();
	    contentPane.add(toolbar, BorderLayout.NORTH);
	    contentPane.add(splitPane, BorderLayout.CENTER);
	}
//-----------------------------------------------------------------------------
	private void saveAndClose(){
		this.dispose();
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel(){

		this.dispose();
	}	
//-----------------------------------------------------------------------------
	private JPanel createItemsPanel(){
		JPanel itemsPanel = new JPanel();		
		
		//Create initially empty table
	    table.setShowGrid(true);
	    table.setGridColor(Color.black);
	    table.setPreferredScrollableViewportSize(new Dimension(700, 400));
	    table.setFillsViewportHeight(true);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	    //Put the table in a scroll pane
	    JScrollPane tableScrollPane = new JScrollPane();
	    tableScrollPane.setViewportView(table);
	    itemsPanel.setLayout(new BorderLayout());
	    itemsPanel.add(tableScrollPane,BorderLayout.CENTER);
	    tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    /*
	     * Set the table model to be the one custom created for this table
	     * and passing in the list of names for the shift
	     */
	  //  ItemsTableModel tableModel = new ItemsTableModel(items);
	    ItemsTableModel tableModel = new ItemsTableModel();
	    tableModel.addTableModelListener(tableModel);
	    table.setModel(tableModel);
	    table.addMouseListener(this);

	    //Resize the columns
	    TableColumn col;
	    int[] sizes = {100, 330, 240};
	    
	    for(int i=0; i<sizes.length; i++){
		    col = table.getColumnModel().getColumn(i);
		    col.setPreferredWidth(sizes[i]);
		    col.setWidth(sizes[i]);
	    }
	    
		return itemsPanel;
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent e){
		//checks if it was a double click
        if (e.getComponent().isEnabled() && e.getButton() == 
        		MouseEvent.BUTTON1 && e.getClickCount() == 2){
        	
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            
            //show the ReadItemDialog to display the item
        //    ReadItemDialog readItem = new ReadItemDialog(
        //    		rm.getGuiParent(), items.get(row));
            ReadItemDialog readItem = new ReadItemDialog(
            		rm.getGuiParent(), rm.getItems().get(row));
            readItem.setVisible(true);
            
            //wait on the ReadItemDialog to be closed
            readItem.setModal(true);
        }
    }
//-----------------------------------------------------------------------------
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseEntered(MouseEvent e) {		
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
//=============================================================================
	private class ItemsTableModel extends AbstractTableModel implements TableModelListener {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Reviewed","Title","Details"};
		
       // private ArrayList<ItemToReview> items = new ArrayList<ItemToReview>();
//-----------------------------------------------------------------------------
   //     public ItemsTableModel(ArrayList<ItemToReview> items) {
		public ItemsTableModel() {
			
        	//this.items=items;
        	
        }
//-----------------------------------------------------------------------------
    /*    public void setList(ArrayList<ItemToReview> newItems) {
        	this.items = newItems;
        }*/
//-----------------------------------------------------------------------------
        public int getColumnCount() {
        	return columnNames.length;
        }
//-----------------------------------------------------------------------------
        public int getRowCount() {
            //return data.length;
        	return rm.getItems().size();
        }
//-----------------------------------------------------------------------------
        public String getColumnName(int col) {
            return columnNames[col];
	    }
//-----------------------------------------------------------------------------
	    public Object getValueAt(int row, int col) {
	    	//return data[row][col];
	    	/*
	    	if(col==0){
	    		return (items.get(row).isReviewed());
	    	} else if(col==1){
	    		return (items.get(row).getTitle());
	    	} else {
	    		return (items.get(row).getDetails());
	    	}
	    	*/
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
	    /*
	     * Implement this method if your table's data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {
	
	    	// data[row][col] = value;
	    	/*
	    	if(col==0){
	    		items.get(row).setReviewed((boolean)value);   		
	    	} else if(col==1){
	    		items.get(row).setTitle((String)value);
	    	} else {
	    		items.get(row).setDetails((String)value);
	    	}
	    	*/
	    	
	    }
//-----------------------------------------------------------------------------  
	/*	public void addRow(ItemToReview item) {  
			Boolean reviewed;
		    	
			if(item.isReviewed()){
				reviewed = new Boolean(true);
			}else{
				reviewed = new Boolean(false);
			}
		    	
			//item.setCreator(System.getProperty("UMPD.user"));
			
			items.add(item);
		}   */
//-----------------------------------------------------------------------------  
   /*     public void deleteRow(int row) {  
        	items.remove(row);
        }        */  
//-----------------------------------------------------------------------------        
       public void tableChanged(TableModelEvent e) {
    	   //if(rm.getItems().size()!=items.size()){
    	//	   items = rm.getItems();
    	 //  }
    	   
       }
 //-----------------------------------------------------------------------------
	}
//=============================================================================
}