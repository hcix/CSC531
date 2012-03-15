/**
 * Tab available only to Shift Commanders.
 */
package userinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;


public class ReportsPanel extends JPanel  implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
		public ReportsPanel(final JFrame parent){
			this.setLayout(new MigLayout("fill"));
			
			//Create a button to create a new BOLO 
			JButton newBOLOButton = SwingHelper.createImageButton("Create new Report", "icons/plusSign_48.png");
			newBOLOButton.addActionListener(new ActionListener() {
				//BOLO form dialog
				
				ShiftCdrReptForm formDialog = new ShiftCdrReptForm(parent);
				public void actionPerformed(ActionEvent e){
					formDialog.setVisible(true);
				}
			});

			//Create a button to import an existing BOLO
			JButton importBOLOButton = SwingHelper.createImageButton("Import Existing Report", "icons/Import.png");
			importBOLOButton.addActionListener(new ActionListener() {
				//file chooser dialog
				public void actionPerformed(ActionEvent e){
					//file chooser dialog .setVisable(true);
					//Create a file chooser
					final JFileChooser fc = new JFileChooser();

					//In response to a button click:
			//		int returnVal = 
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

	       // this.add(tabbedPane, BorderLayout.CENTER);
	        JPanel buttonsPanel = new JPanel();
	        buttonsPanel.add(newBOLOButton);
	        buttonsPanel.add(importBOLOButton);
	        buttonsPanel.add(searchButton);
	        this.add(buttonsPanel, "dock south");
		}
//-----------------------------------------------------------------------------
	JDialog createSearchDialog(JFrame parent){
		//Create the dialog and set the size
		JDialog searchDialog = new JDialog(parent, "Search Past Reports", true);
		searchDialog.setPreferredSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
		searchDialog.setSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
			
		//Put the dialog in the middle of the screen
		searchDialog.setLocationRelativeTo(null);
	
		//Create the various search fields and add them to the dialog
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new MigLayout("align left"));
		SwingHelper.addLineBorder(searchPanel);
		
		JLabel completedByLabel = new JLabel("Completed by: ");
		JLabel relievedByLabel = new JLabel("Relieved by: ");
		
		JTextField completedByField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField relievedByField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);

		
		searchPanel.add(completedByLabel, "align left");
		searchPanel.add(completedByField, "align left, wrap");
		searchPanel.add(relievedByLabel,"alignx left");
		searchPanel.add(relievedByField, "alignx left, wrap");

		SwingHelper.addDateRangePanel(searchPanel);

		Container contentPane = searchDialog.getContentPane();
		contentPane.add(searchPanel);
		return searchDialog;
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

