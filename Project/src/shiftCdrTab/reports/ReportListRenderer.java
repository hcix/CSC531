package shiftCdrTab.reports;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import utilities.ui.SwingHelper;
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 *<code>ReportListRenderer</code> class specifies how a <code>ShiftCdrReport</code>
 * should appear in a <code>JList</code>.
 */
public class ReportListRenderer implements ListCellRenderer, MouseListener, ListDataListener {
	private Color selectedColor;
	private JFrame parent;
	private JList<ShiftCdrReport> reportsList;
	ListModel listModel;
//-----------------------------------------------------------------------------
	public ReportListRenderer(JFrame parent, JList<ShiftCdrReport> reportsList){	
		this.parent=parent;
		this.reportsList=reportsList;
		
		listModel = reportsList.getModel();
	}
//-----------------------------------------------------------------------------
	public Component getListCellRendererComponent(JList list,Object value,int index,
            boolean isSelected, boolean hasFocus) {

		JPanel panel = new JPanel(new MigLayout());
		selectedColor=(panel.getBackground().darker());
		
		if(value instanceof ShiftCdrReport){
			ShiftCdrReport report = (ShiftCdrReport)value;
			panel.setSize(new Dimension(280, 110));
			panel.setPreferredSize(new Dimension(190, 90));
			
			String date = report.getShiftDate();
			
			panel.add(new JLabel(date), "wrap");
			
			//highlight the item if its currently selected
			if(isSelected){ 
				panel.setBackground(selectedColor); 
			}
			
			//if this item has been reviewed, check its checkbox
			if(report.isCurrentlyVisable()){
				panel.setBackground(Color.orange);
				list.repaint();
			}
//DEBUG
System.out.println("ReportListRenderer: report.getShiftDate()" +report.getShiftDate());
			
		}
		
		//SwingHelper.addLineBorder(panel);
		
		return panel;
	}
//-----------------------------------------------------------------------------
	public void itemStateChanged(ItemEvent e) { }
//-----------------------------------------------------------------------------*/
	@Override
	public void mouseClicked(MouseEvent e) {
//DEBUG
System.out.println("ReportListRenderer: mouseClicked(): CALLED!!");
		
		if(e.getClickCount() == 2){ //double click
			     int index = reportsList.locationToIndex(e.getPoint());
			     
			     Object item = listModel.getElementAt(index);
			     reportsList.ensureIndexIsVisible(index);
			     
			//
			     
			     reportsList.repaint();
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
		//((ItemToReview)e.getIndex0()).
	}
//-----------------------------------------------------------------------------
}