package shiftCdrTab;

import java.awt.Dimension;
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
import program.ResourceManager;
//-----------------------------------------------------------------------------
/**
 * JDOC
 */
public class ItemsSidePanel extends JScrollPane {
private static final long serialVersionUID = 1L;
	ResourceManager rm;
	ArrayList<ItemToReview> itemsList;
	JList<ItemToReview> itemsJList;
	ItemsListModel itemsModel;
//-----------------------------------------------------------------------------
	ItemsSidePanel(ResourceManager rm){
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.rm=rm;
		//Create the items panel to be displayed in the scroller
		JPanel panel = new JPanel(new MigLayout("flowy"));
		panel.setPreferredSize(new Dimension(300, 625));
		//add a title
		String title = "<html><h3>Items to Review</h3></html>";
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		panel.add(titleLabel, "alignx center");
		//add the review items
		addItemsToReview(panel);
		
		this.setViewportView(panel);
	}
//-----------------------------------------------------------------------------
	private void addItemsToReview(JPanel panel){

		itemsList = rm.getItems();
		//itemsModel = new ItemsListModel(itemsList);
		itemsModel = new ItemsListModel(rm);
		
		itemsJList = new JList<ItemToReview>(itemsModel);

		ItemRenderer itemRenderer = new ItemRenderer(rm.getGuiParent(), itemsJList);
		itemsJList.setCellRenderer(itemRenderer);
		itemsJList.addMouseListener(itemRenderer);
		itemsModel.addListDataListener(itemRenderer);

		panel.add(itemsJList);
	}
//-----------------------------------------------------------------------------
	public void updateItemsList(){
		itemsList = rm.getItems();
		itemsJList.repaint();
	}
//-----------------------------------------------------------------------------
}
