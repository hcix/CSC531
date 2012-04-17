package userinterface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import progAdmin.AdminTab;
import progAdmin.PersonnelManager;
import program.CurrentUser;
import program.ResourceManager;
import shiftCdrTab.ShiftCdrTab;
import blueBookTab.BlueBookTab;
import boloTab.BOLOtab;


public class MainInterfaceWindow extends JPanel{
private static final long serialVersionUID = 1L;
	JPanel homeTab, boloTab, rollCallTab, blueBookTab;
	ShiftCdrTab shiftCdrTab;
	AdminTab adminTab;
	JButton logoutButton;
//-----------------------------------------------------------------------------	
	public MainInterfaceWindow(JFrame parent, ResourceManager rm){
		super(new GridLayout(1, 1));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Dimension dim = new Dimension(970,1000);
		
		/*
		 * Set up the tabbedPane panel and add the appropriate tabs
		 * depending on the current user's permissions.
		 */
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//Set up the 5 tabs everyone sees
		homeTab = new HomeTab(false);
		tabbedPane.addTab("Home", homeTab);
		homeTab.setPreferredSize(dim);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        boloTab = new BOLOtab(parent);
        boloTab.setPreferredSize(dim);
        tabbedPane.addTab("BOLOs", boloTab);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        blueBookTab = new BlueBookTab(parent);
        tabbedPane.addTab("Blue Book", blueBookTab);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
      //if user is at least a shift cdr, set up shift cdr tab
//COMMENT NEXT 2 LINES OUT TO GET RID OF THE LOGIN GUI FOR DEBUGGING PURPOSES
        	if(CurrentUser.getCurrentUser().getLevel()>=
        		PersonnelManager.PERMIS_LEVEL_COMMAND){  	
        	shiftCdrTab = new ShiftCdrTab(rm, this);
	        tabbedPane.addTab("Shift Commander", shiftCdrTab);
	        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4); //change to 4 from 5?
	        
	      //if user is at a supervisor, set up supervisor tab
//COMMENT NEXT 2 LINES OUT TO GET RID OF THE LOGIN GUI FOR DEBUGGING PURPOSES
	        if(CurrentUser.getCurrentUser().getLevel()>=
	        		PersonnelManager.PERMIS_LEVEL_SUPERVISR){
		        adminTab = new AdminTab(rm, this);
		        tabbedPane.addTab("Administration", adminTab);
		        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5); // change from 6 to 5?
//COMMENT NEXT 2 LINES OUT TO GET RID OF THE LOGIN GUI FOR DEBUGGING PURPOSES
	        }
        }
	        
        //The following line enables the use of scrolling tabs
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);	
        
        //Add the tabbed pane to this main panel.
        add(tabbedPane);
        
	}
//-----------------------------------------------------------------------------
	public void refreshItemsList(){
		shiftCdrTab.refreshItemsList();
	}
//-----------------------------------------------------------------------------
	public void refreshItemsTable(){
		adminTab.refreshItemsTable();
	}
//-----------------------------------------------------------------------------
}
