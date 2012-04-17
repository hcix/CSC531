package shiftCdrTab;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import progAdmin.itemsToReview.ItemRenderer;
import progAdmin.itemsToReview.ItemToReview;
import progAdmin.itemsToReview.ItemsListModel;
import progAdmin.itemsToReview.ReadItemDialog;
import program.ResourceManager;
import userinterface.MainInterfaceWindow;
//-----------------------------------------------------------------------------
/**
 * JDOC
 */
public class ItemsSidePanel extends JScrollPane implements MouseListener {
private static final long serialVersionUID = 1L;
	ResourceManager rm;
	JList<ItemToReview> itemsJList;
	ItemsListModel itemsModel;
	MainInterfaceWindow mainInterface;
//-----------------------------------------------------------------------------
	ItemsSidePanel(ResourceManager rm, MainInterfaceWindow mainInterface){
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.rm=rm;
		this.mainInterface=mainInterface;
		//Create the items panel to be displayed in the scroller
		JPanel panel = createItemsPanel();
		
		this.setViewportView(panel);
	}
//-----------------------------------------------------------------------------
	private JPanel createItemsPanel(){
		JPanel panel = new JPanel(new MigLayout("flowy"));
		//add a title
		String title = "<html><h3>Items to Review</h3></html>";
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		panel.add(titleLabel, "alignx center");
		//add the review items
		addItemsToReview(panel);
		
		return panel;
	}
//-----------------------------------------------------------------------------
	private void addItemsToReview(JPanel panel){

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
		//itemsList = rm.getItems();
		//itemsJList.getModel();
	//	itemsJList.repaint();
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseClicked(MouseEvent e) {

//DEBUG System.out.println("ItemRenderer: mouseClicked(): CALLED!!");
		
		if(e.getClickCount() == 2){ //double click
			     int index = itemsJList.locationToIndex(e.getPoint());
			     
			     Object item = itemsModel.getElementAt(index);
			     itemsJList.ensureIndexIsVisible(index);
			     ReadItemDialog itemDialog = new ReadItemDialog(rm.getGuiParent(), ((ItemToReview)item));
			     itemDialog.setVisible(true);
			     itemDialog.setModal(true);
			     //int selected = itemsList.getSelectedIndex();
			    itemsJList.repaint();
			    //tell the items table it needs to update too
			    mainInterface.refreshItemsTable();
		}
		
	}
//-----------------------------------------------------------------------------
//	@Override
	public void mousePressed(MouseEvent e) { }
//-----------------------------------------------------------------------------
//	@Override
	public void mouseReleased(MouseEvent e) { }
//-----------------------------------------------------------------------------
//	@Override
	public void mouseEntered(MouseEvent e) { }
//-----------------------------------------------------------------------------
//	@Override
	public void mouseExited(MouseEvent e) { }
//=============================================================================
}
