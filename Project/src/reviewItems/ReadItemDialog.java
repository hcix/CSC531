package reviewItems;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * 
 */
public class ReadItemDialog extends JDialog {
private static final long serialVersionUID = 1L;
	private JFrame parent;
	private ItemToReview item;
	private JTextField titleTextField;
	private JTextArea detailsTextPane;
	private JPanel mainPanel;
	String detailsText, titleText;
//-----------------------------------------------------------------------------
	ReadItemDialog(JFrame parent, ItemToReview item){
		super(parent, item.getTitle(), true);
		this.parent=parent;
		
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
		
		titleText = "<html><h2>" + item.getTitle() + "</h2></html>";
		titleTextField = new JTextField();
		Font titleFont = new Font("Serif", Font.PLAIN, 20);
		titleTextField.setFont(titleFont);
		titleTextField.setEditable(false);
		titleTextField.setText(titleText);
		titleTextField.setBackground(mainPanel.getBackground());
		
		detailsText = item.getDetails();
		detailsTextPane = new JTextArea();
		detailsTextPane.setText(detailsText);
		detailsTextPane.setEditable(false);
		detailsTextPane.setLineWrap(true);
		detailsTextPane.setWrapStyleWord(true);
		detailsTextPane.setBackground(mainPanel.getBackground());
		
		JPanel buttonPanel = createButtonsPanel();
		mainPanel.add(buttonPanel, "dock north");
		
		mainPanel.add(titleTextField, "alignx center, wrap");
		mainPanel.add(detailsTextPane, "alignx center");
		
	    Container contentPane = getContentPane();
	    contentPane.add(mainPanel);	
	}
//-----------------------------------------------------------------------------
	public JPanel createButtonsPanel(){
		
		JButton markAsReadButton = new JButton("Mark Item as Read");
		markAsReadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveAndClose();
			 }
		});
		
		JButton editButton = new JButton("Edit Item");
		markAsReadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				detailsTextPane.setEditable(true);
				mainPanel.validate();
			 }
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(markAsReadButton, "align left");
		buttonPanel.add(editButton);
		
		return buttonPanel;
	}
//-----------------------------------------------------------------------------
	private void saveAndClose(){

		/*
		if(!titleField.getText().isEmpty()){
			title=titleField.getText().toString().trim();
			System.out.println("AddItemDialog: AddItemDialog(): title = "+title);
			item.setTitle(title);
		}
		if(!textArea.getText().isEmpty()){
			details=textArea.getText().toString().trim();
			System.out.println("AddItemDialog: AddItemDialog(): details = "+details);
			item.setDetails(details);
		}
		
		
		try{ item.addToXML(); } 
		catch (Exception ex){ ex.printStackTrace(); }
	
	*/
		this.dispose();
	}
//-----------------------------------------------------------------------------
	private void closeAndCancel(){
		detailsTextPane.setText("");
		titleTextField.setText("");
		this.dispose();
	}
//-----------------------------------------------------------------------------
}