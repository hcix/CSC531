package userinterface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import utilities.SwingHelper;


public class MainInterfaceWindow extends JPanel{
private static final long serialVersionUID = 1L;
	JPanel sidePanel, homeTab, boloTab, rollCallTab, 
		mapTab, shiftCdrTab, blueBookTab, schedTab, adminTab;
//-----------------------------------------------------------------------------	
	public MainInterfaceWindow(JFrame parent){
		super(new GridLayout(1, 1));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Dimension dim = new Dimension(970,1000);
		
		/*
		 * Set up the tabbedPane panel and add the appropriate tabs.
		 */
		JTabbedPane tabbedPane = new JTabbedPane();
	        
		homeTab = new HomeTab(false);
		tabbedPane.addTab("Home", homeTab);
		homeTab.setPreferredSize(dim);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        boloTab = new BOLOtab(parent);
        boloTab.setPreferredSize(dim);
        tabbedPane.addTab("BOLOs", boloTab);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        mapTab = new MapTab();
        tabbedPane.addTab("Map", mapTab);
        mapTab.setPreferredSize(dim);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_4);
        
        shiftCdrTab = new ShiftCdrTab(parent);
        tabbedPane.addTab("Shift Commander", shiftCdrTab);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_5);
        
        blueBookTab = new BlueBookTab(parent);
        tabbedPane.addTab("Blue Book", blueBookTab);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_6);
        
        schedTab = SwingHelper.makeTextPanel("Schedule");
        tabbedPane.addTab("Schedule", schedTab);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_7);
        
        adminTab = new AdminTab();
        tabbedPane.addTab("Administration", adminTab);
        tabbedPane.setMnemonicAt(6, KeyEvent.VK_8);
        
        //The following line enables the use of scrolling tabs
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);	
        
        //Add the tabbed pane to this main panel.
        add(tabbedPane);
        
	}
//-----------------------------------------------------------------------------
}
