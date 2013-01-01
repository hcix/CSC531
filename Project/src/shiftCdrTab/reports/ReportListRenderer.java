package shiftCdrTab.reports;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DateFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 *<code>ReportListRenderer</code> class specifies how a <code>ShiftCdrReport</code>
 * should appear in a <code>JList</code>.
 */
public class ReportListRenderer implements ListCellRenderer, ListDataListener {
	private Color selectedColor;
	private Color currentlyDisplayedColor = Color.orange;
	private JFrame parent;
	private JList<ReportFile> reportFileList;
	ListModel<ReportFile> listModel;
//-----------------------------------------------------------------------------
	public ReportListRenderer(JFrame parent, JList<ReportFile> reportFileList){	
		this.parent=parent;
		this.reportFileList=reportFileList;
		this.listModel = reportFileList.getModel();
	}
//-----------------------------------------------------------------------------
	@Override
	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list,Object value,int index,
            boolean isSelected, boolean hasFocus) {

		JPanel panel = new JPanel(new MigLayout());
		selectedColor=(panel.getBackground().darker());
		
		if(value instanceof ReportFile){
			ReportFile reportFile = (ReportFile)value;
			
			panel.setSize(new Dimension(150, 50));
			panel.setPreferredSize(new Dimension(150, 50));
			
			DateFormat formater = DateFormat.getDateTimeInstance(
					DateFormat.SHORT, DateFormat.SHORT);
			
			String date = formater.format((reportFile.getDateCreated()));
			
			panel.add(new JLabel(date), "wrap");
			
			//highlight the item if its currently selected
			if(isSelected){ 
				panel.setBackground(selectedColor); 
				panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				//list.repaint();
			}
			
			//if this item has been reviewed, check its checkbox
			if(reportFile.isCurrentlyDisplayed()){
				//panel.setBackground(currentlyDisplayedColor);
				panel.setBorder(BorderFactory.createLineBorder(currentlyDisplayedColor));
				list.repaint();
			} else {
				panel.setBorder(null);
			}
//DEBUG System.out.println("ReportListRenderer: report.getShiftDate()" +report.getShiftDate());
			
		}
		
		//SwingHelper.addLineBorder(panel);
		
		return panel;
	}
//-----------------------------------------------------------------------------
/*	public void itemStateChanged(ItemEvent e) { }	*/
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