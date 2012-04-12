package blueBookTab;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import boloTab.Bolo;
import net.miginfocom.swing.MigLayout;
import userinterface.ItemsViewerPanel;
import utilities.DatabaseHelper;
import utilities.ImageHandler;
import utilities.SwingHelper;
/**
 * Configures UI for the Blue Book Tab
 */
//-----------------------------------------------------------------------------	
public class BlueBookTab extends JPanel implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	
	public BlueBookTab(final JFrame parent){
		this.setLayout(new BorderLayout());
		
		//Create Blue Book tabbed display area
		//JTabbedPane tabbedPane = new JTabbedPane();
		//Add recent Blue Book Entries tab

		//tabbedPane.addTab("Blue Book", recentBolosTab);
		//tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
				
		JPanel entriesPanel = createEntriesPanel();
		//Create a button to create a new Blue Book entry 
		JButton newEntryButton = 
				SwingHelper.createImageButton("Create", "icons/plusSign_48.png");
		newEntryButton.addActionListener(new ActionListener() {
			//Create new Blue Book entry form dialog
			BlueBookForm formDialog = new BlueBookForm(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create search button
		JButton searchButton = SwingHelper.createImageButton("Search", "icons/search.png");
		searchButton.addActionListener(new ActionListener() {
			//Search dialog
			JDialog searchDialog = createSearchDialog(parent);
			public void actionPerformed(ActionEvent e){
				searchDialog.setVisible(true);
			}
		});
		 
		//add the components to this panel
		
		//this.add(tabbedPane, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(newEntryButton);
		buttonsPanel.add(searchButton);
		this.add(buttonsPanel, BorderLayout.PAGE_END);
	}
//-----------------------------------------------------------------------------
	JDialog createSearchDialog(JFrame parent){
		//Create the dialog and set the size
		JDialog searchDialog = new JDialog(parent, "Search Blue Book Database", true);
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
		JLabel nameLabel = new JLabel("Name: ");
		
		JTextField caseNumField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField locationField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField nameField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		
		String[] statusStrings = { "Need to Identify", "Identified", "Arrested" };
		JComboBox<String> statusList = new JComboBox<String>(statusStrings);
		statusList.setSelectedIndex(0);

		searchPanel.add(caseNumLabel, "alignx left");
		searchPanel.add(caseNumField, "alignx left, wrap");
		searchPanel.add(nameLabel, "alignx left");
		searchPanel.add(nameField, "alignx left, wrap");
		searchPanel.add(locationLabel,"alignx left");
		searchPanel.add(locationField, "alignx left, wrap");
	
		SwingHelper.addDateRangePanel(searchPanel);
		
		Container contentPane = searchDialog.getContentPane();
		contentPane.add(searchPanel);
		return searchDialog;
	}
//-----------------------------------------------------------------------------	
	public JPanel createEntriesPanel(){
		JPanel panel = new JPanel(new MigLayout("gapx 30, wrap 4"));
		Date prepDate;
		
		//TODO: make scrollable!
		/*
		try {
			boloList = DatabaseHelper.getBOLOsFromDB();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int listSize = boloList.size();
		JPanel[] items = new JPanel[listSize];
		Format formatter = new SimpleDateFormat("E, MMM dd, yyyy");
		
		int i=0;
		for(Bolo bolo: boloList){
			String listId = "" + boloList.indexOf(bolo);
//DEBUG			System.out.printf("listId = %s\n", listId);
			prepDate = DatabaseHelper.convertEpochToDate(bolo.getprepDate());
			
			JLabel photoLabel = new JLabel(ImageHandler.getScaledImageIcon(bolo.getPhoto(), 100, 100));

			String date = formatter.format(prepDate);
			String caseNum = "";
			if(bolo.getCaseNum()!=null){ caseNum=bolo.getCaseNum(); }
			String status = "";
			if(bolo.getStatus()!=null){ status=bolo.getStatus(); }
			
			boloPanel = new JPanel(new MigLayout("flowy", "[][]", "[][center]"));
			boloPanel.add(photoLabel);
			
			String armedText = "";
			if(bolo.getWeapon()!=null){ 
				armedText = ("<html><center><font color=#FF0000>ARMED</font></center></html>");
			}
			
			boloPanel.add(new JLabel(armedText, JLabel.CENTER), "alignx center,wrap");
			
			boloPanel.add(new JLabel(date), "split 3, aligny top");
			boloPanel.add(new JLabel("Case#: "+caseNum));
			boloPanel.add(new JLabel(status));
			boloPanel.setSize(new Dimension(130, 150));
			boloPanel.setPreferredSize(new Dimension(130, 150));
			
			boloPanel.setName(listId);
			items[i]=boloPanel;
			i++;
		}
		
		ItemsViewerPanel entriesPanel = new ItemsViewerPanel(items, this);
		
		recentBOLOsPanel.add(entriesPanel);
		*/
		return panel;
	}
//-----------------------------------------------------------------------------	
	@Override
	public void actionPerformed(ActionEvent ev) {
		
		
	}
//-----------------------------------------------------------------------------	
}