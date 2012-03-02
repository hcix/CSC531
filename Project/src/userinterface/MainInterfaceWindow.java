package userinterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilities.SwingHelper;


public class MainInterfaceWindow extends JPanel{
	private static final long serialVersionUID = 1L;
	JPanel sidePanel, homeTab, boloTab, rollCallTab, 
		mapTab, messagesTab, blueBookTab, schedTab, adminTab;
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
        
        rollCallTab = new RollCallTab(parent);
        tabbedPane.addTab("Roll Call", rollCallTab);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        mapTab = new MapTab();
        tabbedPane.addTab("Map", mapTab);
        mapTab.setPreferredSize(dim);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        
        messagesTab = SwingHelper.makeTextPanel("Messages");
        tabbedPane.addTab("Messages", messagesTab);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
        

        blueBookTab = new BlueBookTab(parent);
        tabbedPane.addTab("Blue Book", blueBookTab);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
        
        schedTab = SwingHelper.makeTextPanel("Schedule");
        tabbedPane.addTab("Schedule", schedTab);
        tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);
        
        adminTab = new AdminTab();
        tabbedPane.addTab("Administration", adminTab);
        tabbedPane.setMnemonicAt(7, KeyEvent.VK_8);
        
        //The following line enables the use of scrolling tabs
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);	
        
        //Register a change listener
        tabbedPane.addChangeListener(new ChangeListener() {
        //This method is called whenever the selected tab changes
        	public void stateChanged(ChangeEvent evt) {
        		JTabbedPane pane = (JTabbedPane)evt.getSource();
        		//Get current tab
        		Component currentPane = pane.getSelectedComponent();
        		placeSidePanel(currentPane);
        	}
        });
        
        //Add the tabbed pane to this main panel.
        add(tabbedPane);
        
        
		//Add the right changing side panel to this main panel
        sidePanel = new RightSidePanel();
		add(sidePanel, BorderLayout.LINE_END);
	}
//-----------------------------------------------------------------------------	
	/**
	 * Make a panel with the specified text as the only element.
	 * 
	 */
    private void placeSidePanel(Component current){

    	if(current == mapTab){
    		((RightSidePanel)sidePanel).makeMapsPanel();
    	} else if (current == mapTab){
    		((RightSidePanel)sidePanel).makeAnnouncementsPanel();
    	} else if (current == boloTab){
    		((RightSidePanel)sidePanel).makeBOLOPanel();
    	} else if (current == rollCallTab){
    		
    	} else if (current == messagesTab){
    		
    	} else if (current == blueBookTab){
    		
    	} else if (current == schedTab){
    		
    	}
    }
//-----------------------------------------------------------------------------
}
