package progAdmin.itemsToReview;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import program.CurrentUser;
import program.ResourceManager;
import utilities.ChangeHelper;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 *
 */
public class AddItemDialog extends JDialog {
private static final long serialVersionUID = 1L;
private static String  ADD_ITEM_ERROR = "Item Error";
	private static String NO_TITLE_ERROR_MESSAGE = "Item must have title";
	ItemToReview item;
	JTextField titleField;
	JTextArea detailsTextArea;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public AddItemDialog(ResourceManager rm){
		super(rm.getGuiParent(), "New Item", true);
		
		this.rm=rm;
		this.setPreferredSize(new Dimension(500,500));
		this.setSize(new Dimension(500,500));

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});
	    
		item=new ItemToReview(CurrentUser.getCurrentUser().getCaneID());
		
		JPanel mainPanel = new JPanel(new MigLayout());
		
		JLabel titleLabel = new JLabel("Item Title: ");
		JLabel detailsLabel = new JLabel("Description: ");
		
		titleField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		
		detailsTextArea = new JTextArea(100, 20);
		detailsTextArea.setLineWrap(true);
		detailsTextArea.setWrapStyleWord(true);

		//Add item button
		JButton addItem = SwingHelper.createImageButton("Add Item", 
				"icons/plusSign_48.png");
		addItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveAndClose();
			 }
		});
		
		mainPanel.add(titleLabel, "align left");
		mainPanel.add(titleField, "align left, wrap");
		mainPanel.add(detailsLabel, "align left");
		mainPanel.add(detailsTextArea, "align left, wrap");
		mainPanel.add(addItem, "align left");
		
	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);

	}
//-----------------------------------------------------------------------------
	private void saveAndClose(){
		String title="", details="";
		
		if(!titleField.getText().isEmpty()){
			title=titleField.getText().toString().trim();
//DEBUG System.out.println("AddItemDialog: AddItemDialog(): title = "+title);
			item.setTitle(title);
		}
		
		if(!detailsTextArea.getText().isEmpty()){
			details=detailsTextArea.getText().toString().trim();
//DEBUG System.out.println("AddItemDialog: AddItemDialog(): details = "+details);
			item.setDetails(details);
		}
		
		item.setCreator(CurrentUser.getCurrentUser().getCaneID());
	
		//ensure item has a tile, then add to the program's item list
		if(!title.isEmpty()){
			try{ 
				rm.addItem(item);
				//rm.loadItemsList();
			} catch (Exception ex){ ex.printStackTrace(); }
			titleField.setText("");
			detailsTextArea.setText("");
			this.dispose();
		} else{
			//display error telling user that items must have titles!
			JOptionPane.showMessageDialog(rm.getGuiParent(), NO_TITLE_ERROR_MESSAGE, 
					ADD_ITEM_ERROR, JOptionPane.ERROR_MESSAGE);
		}
	
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel(){
		titleField.setText("");
		detailsTextArea.setText("");
		
		this.dispose();
	}
//-----------------------------------------------------------------------------
}