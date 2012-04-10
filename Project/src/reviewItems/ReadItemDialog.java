package reviewItems;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------

/**
 * 
 */
public class ReadItemDialog extends JDialog {
private static final long serialVersionUID = 1L;
	JFrame parent;
	ItemToReview item;
	JTextField titleField;
	JTextArea textArea;
	JPanel mainPanel;
//-----------------------------------------------------------------------------
	ReadItemDialog(JFrame parent, ItemToReview item){
		super(parent, "New Item", true);
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
		
		String titleText = "<html><h2>" + item.getTitle() + "</h2></html>";
		JLabel titleLabel = new JLabel(titleText);
		
		//String detailsText = "<html>" + item.getDetails() + "</html>";
		String detailsText = item.getDetails();
		//JLabel detailsLabel = new JLabel(detailsText);

		textArea = new JTextArea(100, 20);
		textArea.setText(detailsText);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(mainPanel.getBackground());
		
		JPanel buttonPanel = createButtonsPanel();
		mainPanel.add(buttonPanel, "dock north");
		
		mainPanel.add(titleLabel, "align left");
//		mainPanel.add(titleField, "align left, wrap");
		mainPanel.add(textArea, "align left");
		mainPanel.add(textArea, "align left, wrap");
		
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
				textArea.setEditable(true);
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
		String title="", details="";

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
		//titleField.setText("");
		textArea.setText("");
		
		this.dispose();
	}
//-----------------------------------------------------------------------------
}