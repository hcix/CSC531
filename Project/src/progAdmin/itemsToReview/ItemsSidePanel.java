package progAdmin.itemsToReview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import userinterface.MainInterfaceWindow;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * JDOC
 */
public class ItemsSidePanel extends JScrollPane implements MouseListener, ActionListener {
private static final long serialVersionUID = 1L;
	//private static final String ADD_ITEM = "add";
	ResourceManager rm;
	JList<ItemToReview> itemsJList;
	ItemsListModel itemsModel;
	MainInterfaceWindow mainInterface;
//-----------------------------------------------------------------------------
	public ItemsSidePanel(ResourceManager rm, MainInterfaceWindow mainInterface){
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.rm=rm;
		this.mainInterface=mainInterface;
		
		//Create the items panel to be displayed in the scroller
		JPanel panel = createItemsPanel();
		//panel.setLayout(new BorderLayout());
		this.setViewportView(panel);
		this.setColumnHeaderView(createToolbar());
	}
//-----------------------------------------------------------------------------
	/**
	 * Create the panel to display the items list within.
	 */
	private JPanel createItemsPanel(){
		JPanel panel = new JPanel(new MigLayout("flowy"));
		//add a title 
		//String title = "<html><h3>Items to Review</h3></html>";
		//JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		//panel.add(titleLabel, "alignx center");
		
		//add the toolbar
		//addToolbar(panel);
		
		

		//add a title
		String title = "<html><h3>Items to Review</h3></html>";
		JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
		panel.add(titleLabel, "alignx center");
		//add the review items
		addItemsToPanel(panel);
		
		return panel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Create the toolbar to display at the top of the panel.
	 */
	private JToolBar createToolbar(){
		//create toolbar
		JToolBar toolbar = new JToolBar("Items To Review");
		toolbar.setLayout(new MigLayout("fillx", "[]push[]", null));
		//make a tool bar immovable
		toolbar.setFloatable(false); 
		//visually indicate tool bar buttons when the user passes over them w/ cursor
		toolbar.setRollover(true); 
				
		JButton addItemButton = SwingHelper.createImageButton("icons/plusSign_16.png");
		addItemButton.addActionListener(this);
		
		//add a title 
		String title = "<html><h2>Items to Review</h2></html>";
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		toolbar.add(titleLabel, "growx");//, "alignx center");
		
		toolbar.add(addItemButton);
		
		return toolbar;
	}
//-----------------------------------------------------------------------------
	/**
	 * Create a new JList to display the review items and add it to the given
	 * JPanel.
	 */
	private void addItemsToPanel(JPanel panel){
		itemsModel = new ItemsListModel(rm);
		
		itemsJList = new JList<ItemToReview>(itemsModel);
		
		ItemRenderer itemRenderer = new ItemRenderer(rm.getGuiParent(), itemsJList);
		
		itemsJList.setCellRenderer(itemRenderer);
		itemsJList.addMouseListener(this);
		itemsModel.addListDataListener(itemRenderer);

		panel.add(itemsJList);
	}
//-----------------------------------------------------------------------------
	public void updateItemsList(){
		JPanel panel = createItemsPanel();
		this.setViewportView(panel);
	}
//-----------------------------------------------------------------------------
	public void mouseClicked(MouseEvent e) {

//DEBUG System.out.println("ItemRenderer: mouseClicked(): CALLED!!");
		
		if(e.getClickCount() == 2){ //double click
			     int index = itemsJList.locationToIndex(e.getPoint());
			     
			     Object item = itemsModel.getElementAt(index);
			     itemsJList.ensureIndexIsVisible(index);
			     ReadItemDialog itemDialog = new ReadItemDialog(rm.getGuiParent(), ((ItemToReview)item));
			     itemDialog.setVisible(true);
			     itemDialog.setModal(true);
			     //repaint the JList in case data changed
			    itemsJList.repaint();
			    //tell the items table it needs to update too
			    mainInterface.refreshItemsTable();
		}
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		AddItemDialog itemDialog = new AddItemDialog(rm);
		itemDialog.setVisible(true);
		itemDialog.setModal(true);
		itemDialog.setModal(true);
		//repaint the JList to display the new item
	    //itemsJList.repaint();
	    updateItemsList();
	    //tell the items table it needs to update too
	    mainInterface.refreshItemsTable();

	}
//-----------------------------------------------------------------------------
//	@Override
	public void mousePressed(MouseEvent e) { }
//-----------------------------------------------------------------------------
	public void mouseReleased(MouseEvent e) { }
//-----------------------------------------------------------------------------
	public void mouseEntered(MouseEvent e) { }
//-----------------------------------------------------------------------------
	public void mouseExited(MouseEvent e) { }
//=============================================================================
}