package progAdmin.itemsToReview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 * The <code>ItemRenderer</code> class specifies how a <code>ItemToReview</code>
 * should appear in a <code>JList</code>.
 */
public class ItemRenderer implements ListCellRenderer, MouseListener, ListDataListener {
	private Color selectedColor;
	private JFrame parent;
	private JList<ItemToReview> itemsList;
//-----------------------------------------------------------------------------
	public ItemRenderer(JFrame parent, JList<ItemToReview> itemsList){	
		this.parent=parent;
		this.itemsList=itemsList;
	}
//-----------------------------------------------------------------------------
	public Component getListCellRendererComponent(JList list,Object value,int index,
            boolean isSelected, boolean hasFocus) {

		JPanel itemPanel = new JPanel(new MigLayout());
		selectedColor=(itemPanel.getBackground().darker());
		
		if(value instanceof ItemToReview){
			ItemToReview item = (ItemToReview)value;
			itemPanel.setSize(new Dimension(280, 110));
			itemPanel.setPreferredSize(new Dimension(280, 110));
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
			details.setText(item.getDetails());
//FIXME:
	/*		if(item.getDetails().length()>200){ 
				String textToShow = item.getDetails().substring(0, 150); 
				textToShow = textToShow.concat("...");
				details.setText(textToShow);
			} else {
				details.setText(item.getDetails());
			}*/
//end of fixme
			
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
		
		return itemPanel;
	}
//-----------------------------------------------------------------------------
	/*	public void itemStateChanged(ItemEvent e) {

	    Object source = e.getItemSelectable();

	    if (source == chinButton) {
	        //...make a note of it...
	    } else if (source == glassesButton) {
	        //...make a note of it...
	    } else if (source == hairButton) {
	        //...make a note of it...
	    } else if (source == teethButton) {
	        //...make a note of it...
	    }

	    if (e.getStateChange() == ItemEvent.DESELECTED)
	        //...make a note of it...
	    ...
	    updatePicture();
	}*/
//-----------------------------------------------------------------------------*/
	@Override
	public void mouseClicked(MouseEvent e) {

System.out.println("ItemRenderer: mouseClicked(): CALLED!!");
		
		if(e.getClickCount() == 2){ //double click
			     int index = itemsList.locationToIndex(e.getPoint());
			     ListModel listModel = itemsList.getModel();
			     Object item = listModel.getElementAt(index);
			     itemsList.ensureIndexIsVisible(index);
			     //System.out.println("Double clicked on " + item.toString());
			     ReadItemDialog itemDialog = new ReadItemDialog(parent, ((ItemToReview)item));
			     itemDialog.setVisible(true);
			     itemDialog.setModal(true);
			     
			     try{
			    	 
			    	// itemsList.
			    //	 XmlParser.saveItemsToReviewList(itemsList, FileHelper.getItemsToReviewFile());
			     
			     } catch(Exception ex){
			    	 ex.printStackTrace();
			     }
		}
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("ItemRenderer: mousePressed(): CALLED!!");
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("ItemRenderer: mouseReleased(): CALLED!!");
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("ItemRenderer: mouseEntered(): CALLED!!");	
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("ItemRenderer: mouseExited(): CALLED!!");	
	}
//=============================================================================
	@Override
	public void intervalAdded(ListDataEvent e) {
		
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void intervalRemoved(ListDataEvent e) {
		
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void contentsChanged(ListDataEvent e) {
		//((ItemToReview)e.getSource()).
	}
//-----------------------------------------------------------------------------
}