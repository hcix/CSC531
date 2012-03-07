/**
 * Tab avaliable only to Shift Commanders.
 */
package userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;


public class ShiftCdrTab extends JPanel  implements ActionListener {
	private static final long serialVersionUID = 1L;
	//-----------------------------------------------------------------------------
		public ShiftCdrTab(final JFrame parent){
			this.setLayout(new MigLayout("nogrid"));
			
			//Create the search fields panel
			JPanel searchPanel = createSearchFieldsPanel();

			//Create search button
			JButton searchButton = SwingHelper.createImageButton("Search Past Reports", "icons/search.png");
					
			//Create a button to create a new BOLO 
			JButton newBOLOButton = SwingHelper.createImageButton("Create new Report", "icons/plusSign.png");
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
					int returnVal = fc.showOpenDialog(parent);
				}
			});

			//add the components to this panel
			this.add(newBOLOButton, "gapy para");
			this.add(importBOLOButton, "wrap, gapy para");
			this.add(searchPanel, "shrink, gapy para");
			this.add(searchButton, "wrap, aligny bottom");
			
		}
//-----------------------------------------------------------------------------
	JPanel createSearchFieldsPanel(){
		JPanel searchFieldsPanel = new JPanel();
		searchFieldsPanel.setLayout(new MigLayout("align left"));

		JLabel completedByLabel = new JLabel("Completed by: ");
		JLabel relievedByLabel = new JLabel("Relieved by: ");
		
		JTextField completedByField = new JTextField(20);
		JTextField relievedByField = new JTextField(20);

		SwingHelper.addLineBorder(searchFieldsPanel);
		
		searchFieldsPanel.add(completedByLabel, "align left");
		searchFieldsPanel.add(completedByField, "align left, wrap");
		searchFieldsPanel.add(relievedByLabel,"alignx left");
		searchFieldsPanel.add(relievedByField, "alignx left, wrap");

		SwingHelper.addDateRangePanel(searchFieldsPanel);

		return searchFieldsPanel;
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

