package reviewItems;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 * The <code>ItemRenderer</code> class specifies how a <code>ItemToReview</code>
 * should appear in a <code>JList</code>.
 */
//public class ItemRenderer extends DefaultListCellRenderer {
public class ItemRenderer implements ListCellRenderer {
	private Color selectedColor;
//-----------------------------------------------------------------------------
	public Component getListCellRendererComponent(JList list,Object value,int index,
            boolean isSelected, boolean hasFocus) {
		

		JPanel itemPanel = new JPanel(new MigLayout());
		selectedColor=(itemPanel.getBackground().darker());
		
		if(value instanceof ItemToReview){
			//this.setLayout(new MigLayout());
			ItemToReview item = (ItemToReview)value;
			itemPanel.setSize(new Dimension(280, 110));
			itemPanel.setPreferredSize(new Dimension(280, 110));
			JCheckBox cb = new JCheckBox();
			String num = "" + index;
			cb.setActionCommand(num);
			cb.setEnabled(hasFocus);
			cb.addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					e.getSource();
				}	
			});
			
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
			
			if(isSelected){ 
				itemPanel.setBackground(selectedColor); 
				details.setBackground(selectedColor);
			}
		}
			
		

		//return(itemPanel);
		return itemPanel;
	}
//-----------------------------------------------------------------------------
		public void itemStateChanged(ItemEvent e) {

	    Object source = e.getItemSelectable();

	    /*
	    if (source == chinButton) {
	        //...make a note of it...
	    } else if (source == glassesButton) {
	        //...make a note of it...
	    } else if (source == hairButton) {
	        //...make a note of it...
	    } else if (source == teethButton) {
	        //...make a note of it...
	    }
	    */

	    if (e.getStateChange() == ItemEvent.DESELECTED){
	    	//do something
	    }
	        

	    	
	    //update
	}
//-----------------------------------------------------------------------------

}