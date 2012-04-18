package boloTab;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utilities.DatabaseHelper;
import utilities.ui.ImageHandler;
import utilities.ui.DisplayPanel;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The class <code>BOLOtab</code> creates a tab on the UMPD Management System
 * to hold information of <code>Bolo</code>s (Be On the Look Out) and organize them
 * into Recent <code>Bolo</code>s and Archived <code>Bolo</code>s  
 */
public class BOLOtab extends JPanel implements ActionListener {
private static final long serialVersionUID = 1L;
	BOLOform newFormDialog;
	ArrayList<Bolo> boloList;
	JFrame parent;
	JPanel recentBolosTab;
//-----------------------------------------------------------------------------
	/**
	 * Create the <code>BOLOtab</code> to hold Recent <code>Bolo</code>s and 
	 * Archived <code>Bolo</code>s.  
	 * 
	 * @param parent 
	 */
	public BOLOtab(final JFrame parent){
		this.setLayout(new BorderLayout());

		this.parent = parent;
		
		
		//Create BOLOs tabbed display area
		final JTabbedPane tabbedPane = new JTabbedPane();

		//Add recent BOLOs tab
		recentBolosTab = createRecentBOLOsTab();
		tabbedPane.addTab("Recent BOLOs", recentBolosTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

	    //Add archived BOLOs tab 
		JPanel archievedBolosTab = new JPanel();
		tabbedPane.addTab("Archived", archievedBolosTab);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
        
		
		//Create BOLO button
		JButton newBOLOButton = SwingHelper.createImageButton("Create BOLO", 
				"icons/plusSign_48.png");
		newBOLOButton.addActionListener(new ActionListener() {
			//BOLO form dialog

			public void actionPerformed(ActionEvent e){
				//Display the new BOLO form dialog
				newFormDialog.setVisible(true);	
				//wait for the dialog to be dismissed before continuing
				newFormDialog.setModal(true);
				//refresh to display any changes
				refreshRecentBOLOsTab();

				//refresh to display any changes
				recentBolosTab.removeAll();
				recentBolosTab.add(createRecentBOLOsTab());
				tabbedPane.revalidate();
			}
		});

		//Import existing BOLO button
		JButton importBOLOButton = SwingHelper.createImageButton("Import Existing BOLO", 
				"icons/Import.png");
		importBOLOButton.addActionListener(new ActionListener() {
			//file chooser dialog
			public void actionPerformed(ActionEvent e){
				//file chooser dialog .setVisable(true);
				//create a file chooser
				final JFileChooser fc = new JFileChooser();

				//In response to a button click:
			//	int returnVal = 
						fc.showOpenDialog(parent);
			}
		});

		//Search button
		JButton searchButton = SwingHelper.createImageButton("Search Records", 
				"icons/search.png");
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
        
        //TODO: Change below to be happening on bg thread so usr doesn't have to wait
        newFormDialog = new BOLOform(parent, this);

	}
//-----------------------------------------------------------------------------
	/**
	 * Creates a search dialog for the <code>BOLOtab</code> when the 
	 * <code>searchButton</code> is clicked
	 * 
	 * @param parent  the JFrame parent 
	 * @return searchDialog
	 */
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


		String[] statusStrings = { "Need to Identify", "Identified", "Apprehended", "Cleared" };
		JComboBox<String> statusList = new JComboBox<String>(statusStrings);
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
	/**
	 * In the <code>BOLOtab</code> create and set a recent BOLO tab as a JPanel
	 * <p>This displays the bolos in 
	 * 
	 * @return recentBOLOsPanel
	 */
	private JPanel createRecentBOLOsTab(){
		JPanel recentBOLOsPanel = new JPanel(new MigLayout());
		JPanel boloPanel;
		Date prepDate;

		try {
			boloList = DatabaseHelper.getBOLOsFromDB();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int listSize = boloList.size();
		System.out.println("boloList.size() = " + listSize);
		JPanel[] items = new JPanel[listSize];
		Format formatter = new SimpleDateFormat("E, MMM dd, yyyy");

		int i=0;
		for(Bolo bolo: boloList){
			String listId = "" + boloList.indexOf(bolo);

			prepDate = DatabaseHelper.convertEpochToDate(bolo.getprepDate());

			JLabel photoLabel = new JLabel(
					ImageHandler.getScaledImageIcon(bolo.getPhoto(), 100, 100));

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

		DisplayPanel entriesPanel = new DisplayPanel(items, this, 4);

		recentBOLOsPanel.add(entriesPanel);

		return recentBOLOsPanel;
	}
//-----------------------------------------------------------------------------		
	public void refreshRecentBOLOsTab(){
		recentBolosTab.removeAll();
		recentBolosTab.add(createRecentBOLOsTab());
		this.revalidate();
	}

//-----------------------------------------------------------------------------	
	/**
	 * Invoked by the <code>DisplayPanel</code> when a BOLO is 'clicked'
	 * on.
	 */
	public void actionPerformed(ActionEvent ev) {
		String listId = ev.getActionCommand();
		int id = Integer.valueOf(listId);

		Bolo selectedBOLO = boloList.get(id);
		BOLOpreview preview = new BOLOpreview(parent, this, selectedBOLO);

		preview.setVisible(true);
//		preview.setModal(true);

	}
//-----------------------------------------------------------------------------	
}
