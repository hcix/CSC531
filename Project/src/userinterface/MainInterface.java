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


public class MainInterface extends JPanel{
	private static final long serialVersionUID = 1L;
	JPanel sidePanel, homePanel, boloPanel, rollCallPanel, 
		mapPanel, messagesPanel, blueBookPanel, schedPanel, adminPanel;
//-----------------------------------------------------------------------------	
	public MainInterface(JFrame parent){
		super(new GridLayout(1, 1));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Dimension dim = new Dimension(970,1000);
		
		/*
		 * Set up the tabbedPane panel and add the appropriate tabs.
		 */
		JTabbedPane tabbedPane = new JTabbedPane();
	        
		homePanel = new HomeTab(false);
		tabbedPane.addTab("Home", homePanel);
		homePanel.setPreferredSize(dim);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        boloPanel = new BOLOPanel(parent);
        boloPanel.setPreferredSize(dim);
        tabbedPane.addTab("BOLOs", boloPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        rollCallPanel = new RollCallPanel(parent);
        tabbedPane.addTab("Roll Call", rollCallPanel);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        mapPanel = new MapPanel();
        tabbedPane.addTab("Map", mapPanel);
        mapPanel.setPreferredSize(dim);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        
        messagesPanel = SwingHelper.makeTextPanel("Messages");
        tabbedPane.addTab("Messages", messagesPanel);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
        

        //blueBookPanel = SwingHelper.makeTextPanel("Blue Book");
        blueBookPanel = new BlueBookPanel(parent);
        tabbedPane.addTab("Blue Book", blueBookPanel);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
        
        schedPanel = SwingHelper.makeTextPanel("Schedule");
        tabbedPane.addTab("Schedule", schedPanel);
        tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);
        
        adminPanel = new AdminPanel();
        tabbedPane.addTab("Administration", adminPanel);
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

    	if(current == mapPanel){
    		((RightSidePanel)sidePanel).makeMapsPanel();
    	} else if (current == homePanel){
    		((RightSidePanel)sidePanel).makeAnnouncementsPanel();
    	} else if (current == boloPanel){
    		((RightSidePanel)sidePanel).makeBOLOPanel();
    	} else if (current == rollCallPanel){
    		
    	} else if (current == mapPanel){
    		
    	} else if (current == messagesPanel){
    		
    	} else if (current == blueBookPanel){
    		
    	} else if (current == schedPanel){
    		
    	}
    }
//-----------------------------------------------------------------------------
}
