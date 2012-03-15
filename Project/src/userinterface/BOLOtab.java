package userinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;

public class BOLOtab extends JPanel  implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	public BOLOtab(final JFrame parent){
		this.setLayout(new BorderLayout());
				
		//Create BOLOs tabbed display area
		JTabbedPane tabbedPane = new JTabbedPane();
		//Add recent BOLOs tab
		JPanel recentBolosTab = new JPanel();
		tabbedPane.addTab("Recent BOLOs", recentBolosTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
	    //Add archived BOLOs tab 
		JPanel archievedBolosTab = new JPanel();
		tabbedPane.addTab("Archieved", archievedBolosTab);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
        
		//Create a button to create a new BOLO 
		JButton newBOLOButton = SwingHelper.createImageButton("Create BOLO", "icons/plusSign_48.png");
		newBOLOButton.addActionListener(new ActionListener() {
			//BOLO form dialog
			BOLOform formDialog = new BOLOform(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create a button to import an existing BOLO
		JButton importBOLOButton = SwingHelper.createImageButton("Import Existing BOLO", "icons/Import.png");
		importBOLOButton.addActionListener(new ActionListener() {
			//file chooser dialog
			public void actionPerformed(ActionEvent e){
				//file chooser dialog .setVisable(true);
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();

				//In response to a button click:
			//	int returnVal = 
						fc.showOpenDialog(parent);
			}
		});

		//Create search button
		JButton searchButton = SwingHelper.createImageButton("Search Records", "icons/search.png");
		searchButton.addActionListener(new ActionListener() {
			//Search dialog
			JDialog searchDialog = createSearchDialog(parent);
			public void actionPerformed(ActionEvent e){
				searchDialog.setVisible(true);
			}
		});

        this.add(tabbedPane, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(newBOLOButton);
        buttonsPanel.add(importBOLOButton);
        buttonsPanel.add(searchButton);
        this.add(buttonsPanel, BorderLayout.PAGE_END);
		
	}
//-----------------------------------------------------------------------------
	JDialog createSearchDialog(JFrame parent){
		//Create the dialog and set the size
		JDialog searchDialog = new JDialog(parent, "Search BOLO Database", true);
		searchDialog.setPreferredSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
		searchDialog.setSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
		
		//Put the dialog in the middle of the screen
		searchDialog.setLocationRelativeTo(null);
	
		//Create the various search fields and add them to the dialog
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new MigLayout("align left"));
		SwingHelper.addLineBorder(searchPanel);

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel statusLabel = new JLabel("Status: ");
		
		JTextField caseNumField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField locationField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		
		String[] statusStrings = { "Need to Identify", "Identified", "Arrested" };
		JComboBox statusList = new JComboBox(statusStrings);
		statusList.setSelectedIndex(0);

		searchPanel.add(caseNumLabel, "alignx left");
		searchPanel.add(caseNumField, "alignx left, wrap");
		searchPanel.add(locationLabel,"alignx left");
		searchPanel.add(locationField, "alignx left, wrap");

		SwingHelper.addDateRangePanel(searchPanel);

		searchPanel.add(statusLabel, "alignx left");
		searchPanel.add(statusList, "alignx left, wrap");

		
		Container contentPane = searchDialog.getContentPane();
		contentPane.add(searchPanel);
		return searchDialog;
	}
//-----------------------------------------------------------------------------
	public static JSpinner addLabeledSpinner(Container c,String label,SpinnerModel model, boolean wrap) {
		JLabel l = new JLabel(label);
		c.add(l, "align left");
		
		JSpinner spinner = new JSpinner(model);
		
		l.setLabelFor(spinner);
		if(wrap){
			c.add(spinner, "align left, wrap");
		} else{
			c.add(spinner, "align left, split");
		}
		
		return spinner;
	}
//-----------------------------------------------------------------------------		
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
//-----------------------------------------------------------------------------	
}
