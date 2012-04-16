package program;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import progAdmin.itemsToReview.ItemToReview;
import utilities.EmailHandler;
import utilities.FileHelper;
import utilities.PdfHandler;
import utilities.RosterParser;
import utilities.xml.XmlParser;
//-----------------------------------------------------------------------------
/**
 * The <code>ResourceManager</code> class manages the programs resources.
 * Upon creation, it performs a series of checks to determine what system 
 * resources are currently available to the program. The program prefers
 * to be as integrated as possible with the Desktop and will use the host
 * system's default browser, email client, pdf reader, etc to open and
 * manipulate files if the desktop is supported. If the Desktop API is not
 * available, alternative modules are loaded to compensate.
 * <code>ResourceManager</code> also checks to make sure that the proper files 
 * and directories necessary to the program's operation are found and creates 
 * any necessary directories that were not found. 
 */
public class ResourceManager {
	private Desktop desktop;
    private Desktop.Action action = Desktop.Action.OPEN;
    //JDOC
    JFrame parent;
    PdfHandler pdfHandler;
    EmailHandler emailHandler;
    Properties progProps;
    ArrayList<ItemToReview> items;
//-----------------------------------------------------------------------------
    protected ResourceManager(JFrame parent){
    	this.parent = parent;
    	
    	//Set up the classes controlling the program's actions
    	if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            // now enable buttons for actions that are supported.
            verifyAllActions();
        } else {
        	//load backup modules
        	
        }
    	
    	//Load the properties from the progProperties.xml file
		progProps = new Properties();
    	try{ loadProperties();
    	}catch (Exception e){ e.printStackTrace(); }
    	
    	loadItemsList();
    		
    }
//-----------------------------------------------------------------------------
	/**
     * Check if each of the actions are enabled on this host. For any
     * actions not enabled load the backup modules to support them.
     * The actions: open browser, open email client, open, edit, 
     * and print files using their associated default application.
     * 
     */
    private void verifyAllActions() {
        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
        	//load backup browser
        }
        if (!desktop.isSupported(Desktop.Action.MAIL)) {
            //load email handler
        }
        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            //load backup pdf reader
        }
        if (!desktop.isSupported(Desktop.Action.EDIT)) {
        	//load backup pdf reader
        }
        if (!desktop.isSupported(Desktop.Action.PRINT)) {
            //load backup print mechanisms
        }
    }
//-----------------------------------------------------------------------------
    /**
	* Launch the default application associated with a specific
	* filename using the preset Desktop.Action.
	*
	*/
	public void launchDefaultApplication(ActionEvent evt, String fileName) {
		File file = new File(fileName);
		
		try{
			switch(action){
			case OPEN:
				desktop.open(file);
			}
		} catch (IOException e){
			e.printStackTrace();
			
		}
		
    }
//-----------------------------------------------------------------------------
    /**
	* Launch the default application associated with a specific
	* filename using the preset Desktop.Action.
	*
	*/
	public void launchDefaultApplication(String fileName) {
		File file = new File(fileName);
		
		try{
			switch(action){
			case OPEN:
				desktop.open(file);
			}
		} catch (IOException e){
			e.printStackTrace();
			
		}
		
    }
//-----------------------------------------------------------------------------
	/**
     * Launch the default email client using the "mailto"
     * protocol and the text supplied by the user.
     * @param destEmailAddr - the destination's email address
     */
    public void launchMail(ActionEvent evt, String destEmailAddr) {
        URI uriMailTo = null;
        try {
            if (destEmailAddr.length() > 0) {
                uriMailTo = new URI("mailto", destEmailAddr, null);
                desktop.mail(uriMailTo);
            } else {
                desktop.mail();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(URISyntaxException use) {
            use.printStackTrace();
        }
    }
//-----------------------------------------------------------------------------  
	/**
	 * Show the user an error dialog.
	 * The error dialog's body will contain the specified message and it's title
	 * will be the specified errorType. 
	 * @param errorType - the type of error that occurred (i.e. I/O error)
	 * @param errorMessage - the message accompanying the associated error
	 * communicating to the user what went wrong and how (if at all) they 
	 * can fix it
	 */
	public void showErrorDialog(String errorType, String errorMessage){
		
		JOptionPane.showMessageDialog(parent, errorMessage, errorType, 
				JOptionPane.ERROR_MESSAGE);
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the parent frame and returns it to the calling method. 
	 * Used mainly to create dialogs.
	 * @return the program's top level parent JFrame
	 */
	public JFrame getGuiParent(){
		return parent;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return items - 
	 */
	public ArrayList<ItemToReview> getItems() {
//DEBUG
System.out.println("=====================================================================");
System.out.println("\nReturning:");
int i=0;
for(ItemToReview item : items){
System.out.printf("item %d: %s\n", i, item.toString());
i++;
}System.out.println("=====================================================================");

		return items;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param items - the items value to set
	 */
/*	public void setItems(ArrayList<ItemToReview> items) {
		this.items = items;
		saveItemsList();
	}*/
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	public void removeItem(int index) {
//DEBUG
System.out.println("DELETE: Resourcemanager: removeItem: curListSize = " + 
items.size() + "; index to delete= " +index);
		
		items.remove(index);	
		saveItemsList();
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	public void addItem(ItemToReview newItem) {
//DEBUG
System.out.println("ADD: Resourcemanager: addItem: curListSize = " + 
items.size() + "; newItem.getTitle = " + newItem.getTitle());
		items.add(newItem);
		saveItemsList();
	}
//-----------------------------------------------------------------------------	 
	/**
	 * JDOC
	 */
	private void loadItemsList(){
		//populate the items list from the itemsToReview.xml file
		try{
			this.items = XmlParser.loadItemsToReviewList();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	private void saveItemsList(){
//DEBUG
System.out.println("--------------------------------------------------------------------");
System.out.println("\nThe list about to be saved:");
int i=0;
for(ItemToReview item : items){
System.out.printf("item %d: %s\n", i, item.toString());
i++;
}System.out.println("--------------------------------------------------------------------");
			
		//save the items list to the xml file
		try{
			XmlParser.saveItemsToReviewList(items);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		items = XmlParser.loadItemsToReviewList();
	}
//-----------------------------------------------------------------------------	 
		/**
		 * Load the additional Properties from the progProperties.xml file.
		 */
		public void loadProperties() throws Exception{
			//set up new properties object 
	        FileInputStream propFile = new FileInputStream(FileHelper.getPropertiesFile());
	        progProps.loadFromXML(propFile);
	        Properties p = new Properties(System.getProperties());
	        p.putAll(progProps);
	        //p.load(propFile);

	        //set the system properties
	        System.setProperties(p);
	        // display new properties
	        
	//DEBUG Displays new properties list       
	//System.getProperties().list(System.out);

		}	
//-----------------------------------------------------------------------------
	/**
	 * Set the UMPD.latestReport property to the specified value.
	 * 
	 * @param reportFileName - the value to set for the UMPD.latestReport 
	 * property
	 */
	public void setLatestReportName(String reportFileName){
		progProps.setProperty("UMPD.latestReport", reportFileName);
		System.setProperty("UMPD.latestReport", reportFileName);
		//save the system properties again now in case of an error later
		saveProperties();
	}
//-----------------------------------------------------------------------------	
	/**
	 * Set the UMPD.latestVideo property to the specified value.
	 * 
	 * @param videoPath - the value to set for the UMPD.videoPath property
	 */
	public void setLastestVideoName(String videoPath){
		progProps.setProperty("UMPD.latestVideo", videoPath);
		System.setProperty("UMPD.latestVideo", videoPath);
		//save the system properties again now in case of an error later
		saveProperties();
	}	
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	public void saveProperties(){
		try{
			FileOutputStream out = new FileOutputStream(FileHelper.getPropertiesFile());
			progProps.storeToXML(out, "<!-- No comment--!>");
			out.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	public ArrayList<String> getRollCall() {
		int shiftTime;
		ArrayList<String> Employees = new ArrayList<String>();
			
		shiftTime = getShiftTime();
		if (shiftTime == -1) {
			System.out.println("Couldn't get shift time");
			System.exit(0);
		}
			
		RosterParser parser = new RosterParser();
		Employees = parser.getEmployeesOnShift(shiftTime);
		return Employees;
	}
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	public ArrayList<String> getRollCall(int shiftTime) throws Exception {
		ArrayList<String> Employees = new ArrayList<String>();
				
		if (shiftTime < 0 || shiftTime > 24 ) {
			System.out.println("bad shift time");
			throw new Exception();
		}
		
		RosterParser parser = new RosterParser();
		Employees = parser.getEmployeesOnShift(shiftTime);
		return Employees;
	}
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	public static int getShiftTime() {
		int currentHour, currentMin, shiftTime;
		Calendar cal;
		
		/*
		 * determine the time of the nearest shift, if greater than 
		 * half an hour past a shift, go to the next one
		 */
		shiftTime = -1;
		cal = Calendar.getInstance();
		currentHour = cal.get(Calendar.HOUR_OF_DAY);
		currentMin = cal.get(Calendar.MINUTE);
			
		if (currentHour < 6) 
			shiftTime = 6;
		else if (currentHour == 6) {
			if (currentMin <= 30)
				shiftTime = 6;
			else
				shiftTime
				= 10;
		}
		else if (currentHour < 10)
			shiftTime = 10;
		else if (currentHour == 10) {
			if (currentMin <= 30)
				shiftTime = 10;
			else 
				shiftTime = 18;
		}
		else if (currentHour < 18)
			shiftTime = 18;
		else if (currentHour == 18) {
			if (currentMin <= 30)
				shiftTime = 18;
			else 
				shiftTime = 22;
		}
		else if (currentHour < 22)
			shiftTime = 22;
		else if (currentHour == 22) 
				shiftTime = 22;
		else 
			shiftTime = 22;
		return shiftTime;
	}
//-----------------------------------------------------------------------------
	/**
	 * DEBUG prints the system environment. 
	 */
	public void printEnv(){
		//get the syst env
		Map<String, String> env = System.getenv();
		//go thru the properties and print each key/value pair
	    for (String envName : env.keySet()) {
	        System.out.format("%s=%s%n",
	                          envName,
	                          env.get(envName));
	    }
	
	}
//-----------------------------------------------------------------------------
	/**
	 * Given an epoch time, getTimeStringFromEpoch() returns the time of day
	 * associated with that epoch time in String format. The date of the epoch
	 * is irrelevant to this method, thus two epoch values corresponding to, 
	 * say 3:00pm on completely separate days would generate the same return
	 * from this method.
	 * @param epoch - the epoch time which to return the time of day of
	 */
	public String getTimeStringFromEpoch(long epoch){
		Date date = new Date(epoch);
		
		return (DateFormat.getTimeInstance(DateFormat.SHORT).format(date));

	}
//-----------------------------------------------------------------------------
	/**
	 * Given an epoch time, getTimeStringFromEpoch() returns the date
	 * associated with that epoch time in String format. The time of the epoch
	 * is irrelevant to this method, thus two epoch values corresponding to the
	 * same day, but different times, will generate the same return from this 
	 * method. The string returned is of the format MM/dd/yy.
	 * @param epoch - the epoch time which to return the date of
	 */
	public String getDateStringFromEpoch(long epoch){
		Format formatter;
		Date date = new Date(epoch);
		
		formatter = new SimpleDateFormat("MM/dd/yy");
		
		return(formatter.format(date));
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
   public static String shiftTimeAsString(int shiftTime) {
	   String time;
	       if (shiftTime == 6)
	    	   time = "06";
	       else 
	    	   time = ((Integer)shiftTime).toString();
	   return time;
   }	
//-----------------------------------------------------------------------------	
}



