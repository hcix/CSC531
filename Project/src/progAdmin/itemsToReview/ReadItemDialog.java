package progAdmin.itemsToReview;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import net.miginfocom.swing.MigLayout;
import program.CurrentUser;
import program.ResourceManager;
import utilities.ui.SwingHelper;

/**
 * JDOC 
 */
public class ReadItemDialog extends JDialog {
private static final long serialVersionUID = 1L;
	private ItemToReview item;
	private JTextArea titleTextField;
	private JTextArea detailsTextPane;
	private JPanel mainPanel;
	String detailsText, titleText;
	JToolBar toolbar;
	JButton saveButton, editButton;
	JFrame parent;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public ReadItemDialog(ResourceManager rm, ItemToReview item){
		super(rm.getGuiParent(), item.getTitle(), true);
		this.item=item;
		this.parent=parent;
		this.rm = rm;
		
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
	    
		mainPanel = new JPanel(new MigLayout("ins 20", "[]5%[]", ""));
		
		JLabel titleLabel = new JLabel("Title:");
		JLabel descLabel = new JLabel("Description:");
		
		titleText = item.getTitle();
		titleTextField = new JTextArea(2, 20);
		Font titleFont = titleTextField.getFont();
		titleFont = titleFont.deriveFont(20f);
		
		titleTextField.setFont(titleFont);
		titleTextField.setEditable(false);
		titleTextField.setText(titleText);
		titleTextField.setBackground(mainPanel.getBackground());
		titleTextField.setEditable(false);
		
		detailsText = item.getDetails();
		detailsTextPane = new JTextArea(7, 20);
		detailsTextPane.setText(detailsText);
		detailsTextPane.setEditable(false);
		detailsTextPane.setLineWrap(true);
		detailsTextPane.setWrapStyleWord(true);
		detailsTextPane.setBackground(mainPanel.getBackground());
		
		toolbar = createToolbar();
		mainPanel.add(toolbar, "dock north");
		mainPanel.add(titleLabel,"alignx left");
		mainPanel.add(titleTextField, "align left, wrap");
		//mainPanel.add(blank,"alignx left, wrap");
		mainPanel.add(descLabel, "alignx left");
		mainPanel.add(detailsTextPane, "align left, wrap");
		
	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);	
	}
//-----------------------------------------------------------------------------
	public JToolBar createToolbar(){
		
		toolbar = new JToolBar();
		toolbar.setLayout(new MigLayout());
		
		JButton markAsReadButton = 
				SwingHelper.createImageButton("Mark Item as Read", 
						"icons/redCheck_32.png");
		markAsReadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				item.setReviewed(true);
				item.setDateReviewed(System.currentTimeMillis());
				saveAndClose();
			 }
		});
		
		JButton markAsUnreadButton = 
				SwingHelper.createImageButton("Mark Item as Unread", "icons/markUnread32.png");
		markAsUnreadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("before set false");
				item.setReviewed(false);
				item.setDateReviewed(0);
				System.out.println("after set false");
				saveAndClose();
				System.out.println("after save and close");
			 }
		});
		
		editButton = SwingHelper.createImageButton("Edit Item", 
				"icons/edit_32.png");
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				editButton.setVisible(false);
				editButton.setEnabled(false);
				makeEditable();
				saveButton.setVisible(true);
				saveButton.setEnabled(true);
				toolbar.validate();
				//buttonPanel.repaint();
			 }
		});
		
		saveButton = SwingHelper.createImageButton("Save Item", 
				"icons/save_32.png");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveAndClose();
			 }
		});
		
	
		if(!item.isReviewed()){
			toolbar.add(markAsReadButton, "sg");
		}else{
			toolbar.add(markAsUnreadButton, "sg");
		}
		
		toolbar.add(editButton, "sg");
		//buttonPanel.add(saveItemButton);
		
		return toolbar;
	}
//-----------------------------------------------------------------------------
	public void makeEditable(){
		if(!CurrentUser.getCurrentUser().getCaneID().equals(item.getCreator())){
			JOptionPane.showMessageDialog(parent, "Only an item's creator may" +
					" edit an item's contents.", "Operation not Permited", 
					JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			return;
		
		}
		
		detailsTextPane.setEditable(true);
		titleTextField.setEditable(true);
		titleTextField.setBackground(Color.white);
		titleTextField.setBorder(BorderFactory.createLoweredBevelBorder());
		detailsTextPane.setBackground(Color.white);
		detailsTextPane.setBorder(BorderFactory.createLoweredBevelBorder());

		//System.out.println("detailsTextPane editable: " + detailsTextPane.isEditable()
		//		+"titleTextPane editable: " + titleTextField.isEditable());
		toolbar.remove(editButton);
		toolbar.add(saveButton, "sg");
		(mainPanel.getParent()).validate();
		mainPanel.validate();
	}
//-----------------------------------------------------------------------------
	private void saveAndClose(){

		if(!titleTextField.getText().isEmpty()){
			titleText=titleTextField.getText().toString().trim();

//DEBUG System.out.println("AddItemDialog: AddItemDialog(): title = "+titleText);
			item.setTitle(titleText);
		}
		if(!detailsTextPane.getText().isEmpty()){
			detailsText=detailsTextPane.getText().toString().trim();
//DEBUG	System.out.println("AddItemDialog: AddItemDialog(): details = "+detailsText);
			item.setDetails(detailsText);
		}
		
		try { 
			item.setTitle(titleText);
			item.setDetails(detailsText);
			//item.addToDB();
			//item.addToDB();
			
		}catch (Exception ex){ ex.printStackTrace(); }
		
		this.dispose();
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel(){
		detailsTextPane.setText(" ");
		titleTextField.setText(" ");
		this.dispose();
	}
//-----------------------------------------------------------------------------
	public ItemToReview getLastItemDisplayed(){
		return item;
	}
//-----------------------------------------------------------------------------
}