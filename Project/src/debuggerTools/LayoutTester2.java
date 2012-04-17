package debuggerTools;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import utilities.ui.ImageHandler;

//-----------------------------------------------------------------------------

/**
 *
 */
public class LayoutTester2 extends JPanel {
	JPanel mainPanel;
	int itemWidth=20, itemHeight=50;

	public LayoutTester2() {
	/*	JPanel jp1=new JPanel();
		JPanel items = {
			
		}
		mainPanel = new JPanel(new MigLayout(wrapString));	
		this.add(mainPanel);
		addItemsToPanel(items);*/
		Path path = Paths.get("/Users/heatherciechowski/CSC531",
				"Project/src/userinterface", "images/unknownPerson.jpeg");
		
		ImageIcon photo = ImageHandler.getResizableImageIcon(path, 100, 100);
		Format formatter = new SimpleDateFormat("E, MMM dd, yyyy");
		Date now = new Date();
		String date = formatter.format(now);
		String caseNum = "128786";

		String status = "Apprehended";
		
		JPanel boloPanel = new JPanel(new MigLayout("flowy", "[][]", "[][center]"));
		//JLabel textArea = new JLabel(date + "\n" + "Case #: " + caseNum + "\n\n" + status);
		//boloPanel.add(new JLabel(photo));
		boloPanel.add(new JLabel(photo));
		boloPanel.add(new JLabel("ARMED", SwingConstants.CENTER), "alignx center,wrap");
		boloPanel.add(new JLabel(date), "split 3, aligny top");
		boloPanel.add(new JLabel("Case#: "+caseNum));
		boloPanel.add(new JLabel(status));
	

		this.add(boloPanel);
		
	}
	//-----------------------------------------------------------------------------
	


//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------


//-----------------------------------------------------------------------------
	public void addItemsToPanel(JPanel items[]){
		for(int i=0; i<items.length; i++){
			(items[i]).setBorder(BorderFactory.createRaisedSoftBevelBorder());
			mainPanel.add(items[i]);
		}
	}
//-----------------------------------------------------------------------------
	public void addItemToPanel(JButton item){
		String constraints="";
		
		if(itemWidth!=0){
			constraints = "width " + itemWidth;
			//if there's also an itemHeight to be set, add a separator to the string
			if(itemHeight!=0){ constraints = constraints.concat(", "); }
		}
		if(itemHeight!=0){
			constraints=constraints.concat("height" + itemHeight);
		}
		mainPanel.add(item);
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the value indicating what percentage of the total width of the panel
	 * each item should occupy. 
	 * For example, if setItemWidthPercentageVal was called with the parameter
	 * value itemWidth equal to 10, the each item within the panel would occupy 10%
	 * of the total panel's width.
	 * @param itemWidth - the percentage of the panel's total width that each item
	 * should occupy
	 */
	public void setItemWidthPercentageVal(int itemWidth){
		this.itemWidth=itemWidth;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the value indicating what percentage of the total height of the panel
	 * each item should occupy. 
	 * For example, if setItemHeightPercentageVal was called with the parameter
	 * value itemHeight equal to 10, the each item within the panel would occupy 10%
	 * of the total panel's height.
	 * @param itemHeight - the percentage of the panel's total height that each item
	 * should occupy
	 */
	public void setItemHeightPercentageVal(int itemHeight){
		this.itemHeight=itemHeight;
	}
//-----------------------------------------------------------------------------
}