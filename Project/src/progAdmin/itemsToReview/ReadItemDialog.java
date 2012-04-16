package progAdmin.itemsToReview;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import utilities.ui.SwingHelper;
import net.miginfocom.swing.MigLayout;

/**
 * JDOC 
 */
public class ReadItemDialog extends JDialog {
private static final long serialVersionUID = 1L;
	private JFrame parent;
	private ItemToReview item;
	private JTextField titleTextField;
	private JTextArea detailsTextPane;
	private JPanel mainPanel;
	String detailsText, titleText;
	JPanel buttonPanel;
	JButton saveButton, editButton;
//-----------------------------------------------------------------------------
	public ReadItemDialog(JFrame parent, ItemToReview item){
		super(parent, item.getTitle(), true);
		this.parent=parent;
		this.item=item;
		
		this.setPreferredSize(new Dimension(700,500));
		this.setSize(new Dimension(700,500));

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});
	    
		mainPanel = new JPanel(new MigLayout());
		
		titleText = item.getTitle();
		titleTextField = new JTextField();
		Font titleFont = new Font("Serif", Font.PLAIN, 20);
		titleTextField.setFont(titleFont);
		titleTextField.setEditable(false);
		titleTextField.setText(titleText);
		titleTextField.setBackground(mainPanel.getBackground());
		titleTextField.setEditable(false);
		
		detailsText = item.getDetails();
		detailsTextPane = new JTextArea();
		detailsTextPane.setText(detailsText);
		detailsTextPane.setEditable(false);
		detailsTextPane.setLineWrap(true);
		detailsTextPane.setWrapStyleWord(true);
		detailsTextPane.setBackground(mainPanel.getBackground());
				
		buttonPanel = createButtonsPanel();
		mainPanel.add(buttonPanel, "dock north");
		mainPanel.add(titleTextField, "alignx center, wrap");
		mainPanel.add(detailsTextPane, "alignx center");
		
	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);	
	}
//-----------------------------------------------------------------------------
	public JPanel createButtonsPanel(){
		
		JButton markAsReadButton = 
				SwingHelper.createImageButton("Mark Item as Read", 
						"icons/redCheck_32.png");
		markAsReadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				item.setReviewed(true);
				saveAndClose();
			 }
		});
		
		JButton markAsUnreadButton = new JButton("Mark Item as Unread");
				//SwingHelper.createImageButton("Mark Item as Unread", "icons/redCheck_32.png");
		markAsReadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				item.setReviewed(false);
				saveAndClose();
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
				buttonPanel.validate();
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
		
		
		JPanel buttonPanel = new JPanel();
		if(!item.isReviewed()){
			buttonPanel.add(markAsReadButton);
		}else{
			buttonPanel.add(markAsUnreadButton);
		}
		
		buttonPanel.add(editButton);
		//buttonPanel.add(saveItemButton);
		
		return buttonPanel;
	}
//-----------------------------------------------------------------------------
	public void makeEditable(){
		detailsTextPane.setEditable(true);
		titleTextField.setEditable(true);
		titleTextField.setBackground(Color.white);
		detailsTextPane.setBackground(Color.white);
		//System.out.println("detailsTextPane editable: " + detailsTextPane.isEditable()
		//		+"titleTextPane editable: " + titleTextField.isEditable());
		buttonPanel.remove(editButton);
		buttonPanel.add(saveButton);
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
		
		try{ 
			item.setTitle(titleText);
			item.setDetails(detailsText);
		}catch (Exception ex){ ex.printStackTrace(); }
		
		this.dispose();
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel(){
		detailsTextPane.setText("");
		titleTextField.setText("");
		this.dispose();
	}
//-----------------------------------------------------------------------------
	public ItemToReview getLastItemDisplayed(){
		return item;
	}
//-----------------------------------------------------------------------------
}