package progAdmin.itemsToReview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import program.ResourceManager;
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 * The <code>ItemRenderer</code> class specifies how a <code>ItemToReview</code>
 * should appear in a <code>JList</code>.
 */
public class ItemRenderer implements ListCellRenderer, ListDataListener {
	private Color selectedColor;
	private JList<ItemToReview> itemsList;
	ListModel listModel;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public ItemRenderer(ResourceManager rm, JList<ItemToReview> itemsList){	
		this.rm=rm;
		this.itemsList=itemsList;
		
		listModel = itemsList.getModel();
	}
//-----------------------------------------------------------------------------
	public Component getListCellRendererComponent(JList list,Object value,int index,
            boolean isSelected, boolean hasFocus) {

		JPanel itemPanel = new JPanel(new MigLayout());
		selectedColor=(itemPanel.getBackground().darker());
		
		if(value instanceof ItemToReview){
			ItemToReview item = (ItemToReview)value;
			itemPanel.setSize(new Dimension(280, 110));
			itemPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.gray) );
			itemPanel.setPreferredSize(new Dimension(280, 75));
			JCheckBox cb = new JCheckBox();
			String num = "" + index;
			cb.setActionCommand(num);
			itemPanel.add(cb, "split");
			itemPanel.add(new JLabel((item.getTitle())), "wrap");
			JTextArea details = new JTextArea(220, 60);
			details.setBackground(itemPanel.getBackground());
			details.setLineWrap(true);
			details.setWrapStyleWord(true);
			details.setEditable(false);
			details.setMaximumSize(new Dimension(240, 100));
			//details.setText(item.getDetails());
//FIXME
			//displays only the first 150 chars in list; trails into a ...
			if(item.getDetails().length()>50){ 
				String textToShow = item.getDetails().substring(0, 40); 
				textToShow = textToShow.concat("...");
				details.setText(textToShow);
			} else {
				details.setText(item.getDetails());
			}
//----end of fixme
			
			itemPanel.add(details, "align left");
			
			//highlight the item if its currently selected
			if(isSelected){ 
				itemPanel.setBackground(selectedColor); 
				details.setBackground(selectedColor);
			}
			
			//if this item has been reviewed, check its checkbox
			if(item.reviewed){
				cb.setSelected(true);
				list.repaint();
			}
			
		}
		//list.repaint();
		

		list.repaint();
		return itemPanel;
	}
//-----------------------------------------------------------------------------
	public void itemStateChanged(ItemEvent e) {
//DEBUG
System.out.println("ItemRenderer: itemStateChanged: called");
		itemsList.repaint();
	}
//-----------------------------------------------------------------------------
	public void mouseClicked(MouseEvent e) {

//DEBUG System.out.println("ItemRenderer: mouseClicked(): CALLED!!");
		
		if(e.getClickCount() == 2){ //double click
			     int index = itemsList.locationToIndex(e.getPoint());
			     
			     Object item = listModel.getElementAt(index);
			     itemsList.ensureIndexIsVisible(index);
			     ReadItemDialog itemDialog = new ReadItemDialog(rm, ((ItemToReview)item));
			     itemDialog.setVisible(true);
			     itemDialog.setModal(true);
			     //int selected = itemsList.getSelectedIndex();
			     itemsList.repaint();
			    // (itemsList.getModel()).a
			     try{
			
			    	// itemsList.
			    //	 XmlParser.saveItemsToReviewList(itemsList, FileHelper.getItemsToReviewFile());
			     
			     } catch(Exception ex){
			    	 ex.printStackTrace();
			     }
		}
		
	}
//-----------------------------------------------------------------------------
	public void mousePressed(MouseEvent e) {
		//System.out.println("ItemRenderer: mousePressed(): CALLED!!");
	}
//-----------------------------------------------------------------------------
	public void mouseReleased(MouseEvent e) {
		//System.out.println("ItemRenderer: mouseReleased(): CALLED!!");
	}
//-----------------------------------------------------------------------------
	public void mouseEntered(MouseEvent e) {
		//System.out.println("ItemRenderer: mouseEntered(): CALLED!!");	
	}
//-----------------------------------------------------------------------------
	public void mouseExited(MouseEvent e) {
		//System.out.println("ItemRenderer: mouseExited(): CALLED!!");	
	}
	public void intervalAdded(ListDataEvent e) {
//DEBUG
System.out.println("ItemRenderer: intervalAdded: called");		
	}
//-----------------------------------------------------------------------------
	public void intervalRemoved(ListDataEvent e) {
//DEBUG
System.out.println("ItemRenderer: intervalRemoved: called");	
	}
//-----------------------------------------------------------------------------
	public void contentsChanged(ListDataEvent e) {
//DEBUG
System.out.println("ItemRenderer: contentsChanged: called");	
	}
//-----------------------------------------------------------------------------
}