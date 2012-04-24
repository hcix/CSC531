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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;
import program.CurrentUser;
import program.ResourceManager;
import utilities.ChangeHelper;
import utilities.ui.SwingHelper;

/**
 * JDOC
 */
public class ManageItemsDialog  extends JDialog implements MouseListener, ActionListener{
private static final long serialVersionUID = 1L;
	final JTable table=new JTable();
	final JPanel itemsPanel = new JPanel();
	ResourceManager rm;
	JTextField titleField;
	JTextArea textArea;
	static final String ADD_ITEM = "add";
	static final String DELETE_ITEM = "delete";
	static final String EDIT_ITEM = "edit";
//-----------------------------------------------------------------------------
	public ManageItemsDialog(ResourceManager rm){
		super(rm.getGuiParent(), "Manage Review Items", true);
		this.rm=rm;
		this.setPreferredSize(new Dimension(700,500));
		this.setSize(new Dimension(700,500));

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});

		//items = rm.getItems();
		final JPanel mainPanel = new JPanel(new MigLayout());
		
		JButton addNewItemButton = 
				SwingHelper.createImageButton("Add Item", "icons/plusSign_48.png");
		addNewItemButton.setActionCommand(ADD_ITEM);
		addNewItemButton.addActionListener(this);
		
		JButton deleteItemButton = 
				SwingHelper.createImageButton("Delete Item", "icons/delete_48.png");
		deleteItemButton.setActionCommand(DELETE_ITEM);
		deleteItemButton.addActionListener(this);
		
		JButton editItemButton = 
				SwingHelper.createImageButton("Edit Item", "icons/edit_48.png");
		editItemButton.setActionCommand(EDIT_ITEM);
		editItemButton.addActionListener(this);
		
		mainPanel.add(addNewItemButton);

		//Create toolbar to hold actions buttons
		JToolBar toolbar = new JToolBar("Toolbar", SwingConstants.HORIZONTAL);
		toolbar.add(addNewItemButton);
		toolbar.add(deleteItemButton);
		toolbar.add(editItemButton);
		toolbar.setFloatable(false);
		
		//Create the table of items
		itemsPanel.add(createItemsPanel());
		
	    Container contentPane = getContentPane();
	    contentPane.setLayout(new MigLayout("ins 20"));
	    contentPane.add(toolbar, BorderLayout.NORTH);
	    contentPane.add(itemsPanel, BorderLayout.CENTER);
	}
//-----------------------------------------------------------------------------
	/**
	 * Close without saving.
	 */
	private void closeAndCancel(){
		this.dispose();
	}	
//-----------------------------------------------------------------------------
	private JPanel createItemsPanel(){
		JPanel itemsPanel = new JPanel(new MigLayout("ins 20"));		
		
		//Create initially empty table
	    table.setShowGrid(true);
	    table.setGridColor(Color.black);
	    table.setPreferredScrollableViewportSize(new Dimension(620, 400));
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
	    
	    //Put the table in a scroll pane
	    JScrollPane tableScrollPane = new JScrollPane();
	    tableScrollPane.setViewportView(table);
	    itemsPanel.setLayout(new BorderLayout());
	    itemsPanel.add(tableScrollPane,BorderLayout.CENTER);
	    tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    /*
	     * Set the table model to be the one custom created for this table
	     * and passing in the list of names for the shift
	     */
	    ItemsTableModel tableModel = new ItemsTableModel();
	    tableModel.addTableModelListener(tableModel);
	    table.setModel(tableModel);
	    table.addMouseListener(this);

	    //Resize the columns
	    TableColumn col;
	    int[] sizes = {100, 240, 260};
	    for(int i=0; i<sizes.length; i++){
		    col = table.getColumnModel().getColumn(i);
		    col.setPreferredWidth(sizes[i]);
		    col.setWidth(sizes[i]);
	    }
	    
		return itemsPanel;
	}
//-----------------------------------------------------------------------------
	public void refreshItemsTable(){
		table.repaint();
	}
//-----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		//Add item
		if(command.equals(ADD_ITEM)){
			AddItemDialog itemDialog = new AddItemDialog(rm);
			itemDialog.setVisible(true);
			itemDialog.setModal(true);
			//refresh while dialog is in view
			refreshItemsTable();
			itemsPanel.removeAll();
			itemsPanel.add(createItemsPanel());
			itemsPanel.revalidate();
<<<<<<< HEAD

=======
			ChangeHelper.makeChange(ChangeHelper.ADD_ITEM_TO_REVIEW);
>>>>>>> home tab comprete
		//Delete item
		} else if(command.equals(DELETE_ITEM)){
			int rowIndex = table.getSelectedRow();
			ItemToReview item = (rm.getItems()).get(rowIndex);
			//only allow the item's creator to delete an item
			if(CurrentUser.getCurrentUser().getCaneID().equals(item.getCreator())){
				rm.removeItem(rowIndex);
				refreshItemsTable();
			} else {
				JOptionPane.showMessageDialog(rm.getGuiParent(), "Only an item's creator may" +
						" delete an item once it's been created.", "Operation not Permited", 
						JOptionPane.INFORMATION_MESSAGE);
			}
<<<<<<< HEAD
		//Edit item

			ChangeHelper.makeChange(ChangeHelper.ADD_ITEM_TO_REVIEW);
		} else if(command.equals(DELETE_ITEM)){
			int rowIndex = table.getSelectedRow();
			rm.removeItem(rowIndex);
			refreshItemsTable();
			ChangeHelper.makeChange(ChangeHelper.DELETE_ITEM_TO_REVIEW);

=======
			ChangeHelper.makeChange(ChangeHelper.DELETE_ITEM_TO_REVIEW);
		//Edit item
>>>>>>> home tab comprete
		} else if (command.equals(EDIT_ITEM)){
			int rowIndex = table.getSelectedRow();
			ItemToReview item = (rm.getItems()).get(rowIndex);
			//only allow the item's creator to edit an item
			if(CurrentUser.getCurrentUser().getCaneID().equals(item.getCreator())){
				//show the ReadItemDialog to display the item
				ReadItemDialog readItem = new ReadItemDialog(
	            		rm, rm.getItems().get(rowIndex));
	            readItem.makeEditable();
	            readItem.setVisible(true);
	            //wait on the ReadItemDialog to be closed
	            readItem.setModal(true);
	            rm.loadItemsList();
<<<<<<< HEAD

=======
>>>>>>> home tab comprete
	            //refresh while dialog is in view
				refreshItemsTable();
				itemsPanel.removeAll();
				itemsPanel.add(createItemsPanel());
				itemsPanel.revalidate();
			} else {
					JOptionPane.showMessageDialog(rm.getGuiParent(), 
							"Only an item's creator may edit an item's " +
							"contents.", "Operation not Permited", 
							JOptionPane.INFORMATION_MESSAGE);
<<<<<<< HEAD

	            refreshItemsTable();
	            table.doLayout();
	            ChangeHelper.makeChange(ChangeHelper.EDIT_ITEM_TO_REVIEW);

=======
	            refreshItemsTable();
	            table.doLayout();
	            ChangeHelper.makeChange(ChangeHelper.EDIT_ITEM_TO_REVIEW);
>>>>>>> home tab comprete
			}
		}
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Called when an item is 'clicked' on. This method only performs a 
	 * function if the mouse 'click' happens to be a double click.
	 */
	public void mouseClicked(MouseEvent e){
		//checks if it was a double click
        if (e.getComponent().isEnabled() && e.getButton() == 
        		MouseEvent.BUTTON1 && e.getClickCount() == 2){
        	
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            
            //show the ReadItemDialog to display the item
            ReadItemDialog readItem = new ReadItemDialog(
            		rm, rm.getItems().get(row));
            readItem.setVisible(true);
            
            //wait on the ReadItemDialog to be closed
            readItem.setModal(true);
        }
    }
//-----------------------------------------------------------------------------
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
//=============================================================================
	/* Inner Class */
	private class ItemsTableModel extends AbstractTableModel 
								implements TableModelListener {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Reviewed","Title","Details"};
//-----------------------------------------------------------------------------

		public ItemsTableModel() {
        	
        }
//-----------------------------------------------------------------------------
		public int getColumnCount() {
        	return columnNames.length;
        }
//-----------------------------------------------------------------------------
      
		public int getRowCount() {
        	return rm.getItems().size();
        }
//-----------------------------------------------------------------------------
		@Override
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
		public void setValueAt(Object value, int row, int col) {  }
//-----------------------------------------------------------------------------        
	public void tableChanged(TableModelEvent e) { }
 //-----------------------------------------------------------------------------
	}
//=============================================================================
}
