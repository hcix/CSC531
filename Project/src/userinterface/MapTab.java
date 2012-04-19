package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import utilities.ui.ImageHandler;
import utilities.ui.ScrollablePicture;
import utilities.ui.SwingHelper;

public class MapTab extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static final int GAP=20;
	static final private String ZOOM_IN = "zoomin";
	static final private String ZOOM_OUT = "zoomout";
    static final private String FULL_SCREEN = "fullscreen";
//-----------------------------------------------------------------------------	
	/**
	 * Create the map panel.
	 */
	public MapTab() {
	    JPanel mapPanel = new JPanel(true);
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
	    
	    //Create & add time frame panel
	    JPanel timeFramePanel = makeTimeFramePanel();
	    topPanel.add(timeFramePanel);
	    
	    //Create & add the map
	    mapPanel.add(makeMapArea());
    
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    this.add(topPanel);
	    this.add(mapPanel);
	}
//-----------------------------------------------------------------------------	
	/**
	 * Create the panel holding the two date and time selectors used for specifying 
	 * the desired time frame to examine.
	 */
	JPanel makeTimeFramePanel(){
		
		JPanel timeFramePanel = new JPanel();
		JPanel fromPanel = new JPanel();
		fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.Y_AXIS));
		makeBorder(fromPanel);
		JPanel toPanel = new JPanel();
		toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.Y_AXIS));
		makeBorder(toPanel);
		
		Calendar calendar = Calendar.getInstance();
		JSpinner dateSpinner;
		JSpinner timeSpinner;
		
		//Set up dates
		Date initDate = calendar.getTime();
		Date latestDate = calendar.getTime();		
        calendar.add(Calendar.YEAR, -10);        
        Date earliestDate = calendar.getTime();

        //Set up times
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        Date initTime = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 59);
        Date finalTime = calendar.getTime();
        
       //Date Spinners
        SpinnerModel fromModel = new SpinnerDateModel(initDate,earliestDate,latestDate,
                                     Calendar.DAY_OF_MONTH);//ignored for user input
        dateSpinner = SwingHelper.addLabeledSpinner(fromPanel, "From: ", fromModel);       
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
        
        SpinnerModel toModel = new SpinnerDateModel(initDate,earliestDate,latestDate,
                Calendar.DAY_OF_MONTH);//ignored for user input
        dateSpinner = SwingHelper.addLabeledSpinner(toPanel, "To: ", toModel);       
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
        
        //Time Spinners
        SpinnerModel fromTimeModel = new SpinnerDateModel(initTime,initTime,finalTime,
                Calendar.HOUR);//ignored for user input
		timeSpinner = SwingHelper.addLabeledSpinner(fromPanel, " ", fromTimeModel);       
		timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm a"));
		
		SpinnerModel toTimeModel = new SpinnerDateModel(initTime,initTime,finalTime,
				Calendar.HOUR);//ignored for user input
		timeSpinner = SwingHelper.addLabeledSpinner(toPanel, " ", toTimeModel);       
		timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm a"));

		timeFramePanel.add(fromPanel);
		timeFramePanel.add(toPanel);
		return timeFramePanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Create a space border to be placed around an object to give the UI 
	 * uniform spacing between components.
	 */
    public void makeBorder(JComponent c){
    	
	    Border spaceBorder = BorderFactory.createEmptyBorder(0,GAP,0,GAP);
		c.setBorder(spaceBorder);
    }
//-----------------------------------------------------------------------------
    /**
     * Create the panel which holds the scrollable map and toolbar for interacting
     * with the map.
     * @return
     */
    public JPanel makeMapArea(){
    	JPanel mapPanel = new JPanel(new BorderLayout());
    	
    	//mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
    	
    	//Create the toolbar
    	JToolBar toolbar = new JToolBar("Map Actions");
    	addButtonsToToolbar(toolbar);
    	toolbar.setFloatable(true);
    	//toolbar.setRollover(false);
    	
    	//Get the map image
    	ImageIcon mapImageIcon = ImageHandler.createImageIcon("images/campusMapsmall.png");
    /*	Image mapImage = mapImageIcon.getImage();
    	Image resizedMap = ImageHandler.getScaledImage(mapImage, 1200, 600);
    	ImageIcon resizedMapIcon = new ImageIcon(resizedMap);*/
    	
    	
	    //Set up the scroll pane.
        ScrollablePicture scrollableMap = new ScrollablePicture(mapImageIcon, 20);
        JScrollPane mapScrollPane = new JScrollPane(scrollableMap);
        mapScrollPane.setPreferredSize(new Dimension(895, 445));
        mapScrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.black));
 
        //Layout the map panel
        mapPanel.setPreferredSize(new Dimension(950, 490));
        mapPanel.add(toolbar, BorderLayout.PAGE_START);
        mapPanel.add(mapScrollPane, BorderLayout.CENTER);

	    return mapPanel;
    }
//-----------------------------------------------------------------------------
	public void addButtonsToToolbar(JToolBar toolbar) {
		
		//Add zoom controls
		ImageIcon zoomInIcon = ImageHandler.createImageIcon("icons/zoomIn_32.png");
		JButton zoomInbutton = new JButton(zoomInIcon);
		zoomInbutton.setToolTipText("Zoom In");
		zoomInbutton.setActionCommand(ZOOM_IN);
		zoomInbutton.addActionListener(this);
		toolbar.add(zoomInbutton);//add zoom in
		
		ImageIcon zoomOutIcon = ImageHandler.createImageIcon("icons/zoomOut_32.png");
		JButton zoomOutbutton = new JButton(zoomOutIcon);
		zoomOutbutton.setToolTipText("Zoom Out");
		zoomOutbutton.setActionCommand(ZOOM_OUT);
		zoomOutbutton.addActionListener(this);
		toolbar.add(zoomOutbutton);//add zoom out
		
		//Add a separator 
        toolbar.addSeparator();

	    //Add a fullscreen option button
		ImageIcon fullScreenIcon = ImageHandler.createImageIcon("icons/fullscreen_32.png");
		JButton fullScreenbutton = new JButton(fullScreenIcon);
		fullScreenbutton.setToolTipText("Full Screen mode");
		fullScreenbutton.setActionCommand(FULL_SCREEN);
		fullScreenbutton.addActionListener(this);
		toolbar.add(fullScreenbutton);
		
		//Add other toolbar buttons/options/fields
		//...
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Called when an action is performed within the map panel.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		//Handle the various toolbar buttons
		if(ZOOM_IN.equals(cmd)){
			//TODO: implement zoom in functionality
		} else if(ZOOM_OUT.equals(cmd)){
			//TODO: implement zoom out functionality
		} else if(FULL_SCREEN.equals(cmd)){
			//TODO: implement full screen functionality
		}
		
	}
//-----------------------------------------------------------------------------
}
