package utilities.pdf;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import net.miginfocom.swing.MigLayout;
import org.jpedal.Display;
import org.jpedal.PdfDecoder;
import org.jpedal.examples.simpleviewer.Commands;
import org.jpedal.examples.simpleviewer.Values;
import org.jpedal.examples.simpleviewer.gui.MultiViewTransferHandler;
import org.jpedal.examples.simpleviewer.gui.SingleViewTransferHandler;
import org.jpedal.examples.simpleviewer.gui.SwingGUI;
import org.jpedal.examples.simpleviewer.gui.generic.GUIMouseHandler;
import org.jpedal.examples.simpleviewer.gui.generic.GUISearchWindow;
import org.jpedal.examples.simpleviewer.gui.generic.GUIThumbnailPanel;
import org.jpedal.examples.simpleviewer.gui.swing.SwingMouseListener;
import org.jpedal.examples.simpleviewer.gui.swing.SwingSearchWindow;
import org.jpedal.examples.simpleviewer.gui.swing.SwingThumbnailPanel;
import org.jpedal.examples.simpleviewer.utils.Printer;
import org.jpedal.examples.simpleviewer.utils.PropertiesFile;
import org.jpedal.exception.PdfException;
import org.jpedal.external.Options;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.Messages;
import program.ResourceManager;

public class PDFView {
//-----------------------------------------------------------------------------
	public static boolean showMessages=false;
	protected Values commonValues=new Values();
	protected Printer currentPrinter=new Printer();
	protected PdfDecoder decode_pdf = new PdfDecoder(true);
	protected GUIThumbnailPanel thumbnails=new SwingThumbnailPanel(commonValues,decode_pdf);
	private PropertiesFile properties=new PropertiesFile();
	public SwingGUI currentGUI=new SwingGUI(decode_pdf,commonValues,thumbnails,properties);
	private GUISearchWindow searchFrame=new SwingSearchWindow(currentGUI);
	protected Commands currentCommands=new Commands(commonValues,currentGUI,decode_pdf,
      thumbnails,properties,searchFrame,currentPrinter);
	protected GUIMouseHandler mouseHandler=new 
		  SwingMouseListener(decode_pdf,currentGUI,commonValues,currentCommands);
	protected String[] scalingValues;
	public static boolean exitOnClose=true;
    private JButton[] topButtons = null;
    private JPanel topButtonPanel = null;
    org.jpedal.objects.acroforms.rendering.AcroRenderer formRenderer = null;

    ArrayList<FieldAndVal> allFormFields;
//-----------------------------------------------------------------------------
    /**
     * Create a new <code>PDFView</code>.
     */
    public PDFView(String doc, Container comp, ResourceManager rm) {
		//enable error messages which are OFF by default
		PdfDecoder.showErrorMessages=true;

		properties.loadProperties();
  
		this.setRootContainer(comp);
		this.setupViewer();
		this.openPdfFile(doc);
		  
		formRenderer = decode_pdf.getFormRenderer();
		
		if(formRenderer==null) { 
			rm.showErrorDialog("File I/O", "An error occured when attempting to display" +
					"the document.");
			System.out.println("fail"); 
		}
     
    }
//-----------------------------------------------------------------------------
    /**
     * Create a new <code>PDFView</code> with the preferences set in the specified
     * properties xml file.
     * @param prefsFile - the absolute path of the file containing the preferences 
     * to use for this <code>PDFView</code>.
     */
    public PDFView(String doc, Container comp, ResourceManager rm, String prefsFile) {
		//enable error messages which are OFF by default
		PdfDecoder.showErrorMessages=true;
		  
		String prefFile = "/Users/heatherciechowski/CSC531/Project/src/utilities/pdf/properties/pdf_reportsTab.xml";

		  if(prefFile != null){
		    properties.loadProperties(prefFile);
		  }else{
		    properties.loadProperties();
		  }
		  
		this.setRootContainer(comp);
		this.setupViewer();
		this.openPdfFile(doc);
		  
		formRenderer = decode_pdf.getFormRenderer();
		
		if(formRenderer==null) { 
			rm.showErrorDialog("File I/O", "An error occured when attempting to display" +
					"the document.");
			System.out.println("fail"); 
		}
     
    }
//-----------------------------------------------------------------------------
    /**
     * Create a new <code>PDFView</code>.
     * This constructor is to be used by JScrollPanes with top button panels.
     */
    public PDFView(String doc, Container comp, JButton[] topButtons) {
      //enable error messages which are OFF by default
      PdfDecoder.showErrorMessages=true;

      this.topButtons = topButtons;
      this.setRootContainer(comp);
      this.setupViewer();
      this.openPdfFile(doc);
      
      formRenderer = decode_pdf.getFormRenderer();

      if(formRenderer==null) { 
    	 System.out.println("fail");
      }  

    }
//-----------------------------------------------------------------------------
    /**
     * Create a new <code>PDFView</code>.
     * This constructor is to be used by JScrollPanes with top button panels.
     */
    public PDFView(String doc, Container comp, JPanel topButtonPanel) {
        //enable error messages which are OFF by default
        PdfDecoder.showErrorMessages=true;
        
        this.topButtonPanel= topButtonPanel;
        this.setRootContainer(comp);
        this.setupViewer();
        this.openPdfFile(doc);
        
        formRenderer
        	= decode_pdf.getFormRenderer();

        if(formRenderer==null) { System.out.println("fail"); }
   
      }
//-----------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public void createFormFieldsList(){

		allFormFields = new ArrayList<FieldAndVal>();
		
    	List<String> compNames = null;
    	
		try {
			compNames = formRenderer.getComponentNameList();
		} catch (PdfException e) {
			e.printStackTrace();
		}
    	
		if(compNames==null){System.out.println("fail"); return; }
			
		String text;
		
    	for(String name : compNames){
    		Object[] components = formRenderer.getComponentsByName(name);
			System.out.println(name);
			if(name.equals("ucr_crime_grp")){
				if ( ((JRadioButton)components[0]).isSelected()) { 
					System.out.println("selected one is : components[0].toString()"); 
				}
					if(((JRadioButton)components[0]).isSelected()){ text = "true"; }
					else { text = "false"; }
					//(((JRadioButton)components[0]).
					allFormFields.add(new FieldAndVal(name, text));
			} else if(name.equals("remarks_box")){
				text = ((JTextArea)components[0]).getText();
				System.out.println("text in remarks = " + text);
				allFormFields.add(new FieldAndVal(name, text));
			} else {
			//System.out.println("((JTextField)components[0]).getText() = " +
			//		((JTextField)components[0]).getText());
			text = ((JTextField)components[0]).getText();
			if(text==null){ System.out.println("text is null"); text = ""; }
				FieldAndVal curr = new FieldAndVal(name, text);
				allFormFields.add(curr);
			}

		}
    	
//DEBUG:
    //	System.out.println("components.toString() = " + components.toString());
    //	System.out.println("components[0].toString() = " + components[0].toString());
    
    	
      }
//-----------------------------------------------------------------------------
	public List<FieldAndVal> getAllFormFields(){
		return allFormFields;
	}
//-----------------------------------------------------------------------------
	public SwingGUI getSwingGUI(){
		return currentGUI;
	}
//-----------------------------------------------------------------------------
    /**
     *
     * @param file
     * Allow user to open PDF file (or webpage) to display
     */
	public void openPdfFile(String file) {
		//ensure reset for any viewport
		commonValues.maxViewY=0;

		//open default file to first page
	    if(file!=null){
	    	File testExists=new File(file);
	    	boolean isURL=false;
	    	if(file.startsWith("http:")|| file.startsWith("jar:") || 
	    			file.startsWith("file:")){
	    		LogWriter.writeLog("Opening http connection");
            	isURL=true;
            }

	    	//display error dialog if not a URL and file doesn't exist
	    	if((!isURL) && (!testExists.exists())){
	    		currentGUI.showMessageDialog(file+ '\n' +
	    				Messages.getMessage("PdfViewerdoesNotExist.message"));
    		//display error dialog if the file is a directory	
	    	}else if((!isURL) &&(testExists.isDirectory())){
	    		currentGUI.showMessageDialog(file+ '\n' +
	    				Messages.getMessage("PdfViewerFileIsDirectory.message"));
	    	}else{
                commonValues.setFileSize(testExists.length() >> 10);
                commonValues.setSelectedFile(file);

                //see if user set a page val
                String page=System.getProperty("org.jpedal.page");
                if(page!=null && !isURL){
                	try{
                		int pageNum=Integer.parseInt(page);
                		if(pageNum<1){
                			pageNum=-1;
                			System.err.println(page+ " must be 1 or larger. Opening on page 1");
                			LogWriter.writeLog(page+ " must be 1 or larger. Opening on page 1");
                		}
                	}catch(Exception e){
                		System.err.println(page+ "is not a valid number for a " +
                				"page number. Opening on page 1");
                		LogWriter.writeLog(page+ "is not a valid number for a page number. " +
                				"Opening on page 1");
                	}
                }else{
                	try {
                		currentCommands.openFile(file);
                	} catch (PdfException e) { 
                		currentGUI.showMessageDialog("File: " + file + '\n' + "File cannot be opened."); 
                	}
                }
	    	}
	    }

}
//-----------------------------------------------------------------------------
	/**
	 * Set the container this <code>PDFView</code> should be placed in.
	 */
	public void setRootContainer(Container rootContainer){
		if(rootContainer==null){ throw new RuntimeException("Null containers not allowed."); }
    
		Container c = rootContainer;
    
    if((rootContainer instanceof JTabbedPane)){
      JPanel temp = new JPanel(new BorderLayout());
      rootContainer.add(temp);
      c = temp;
    }else if(rootContainer instanceof JScrollPane){
      JPanel temp = new JPanel(new BorderLayout());
      ((JScrollPane)rootContainer).getViewport().add(temp);
      c = temp;
      if(topButtons!=null){
    	  addTopButtonPanel(((JScrollPane)rootContainer));
      } else if (topButtonPanel!=null){
    	  addTopButtonPanels(((JScrollPane)rootContainer));
      } else {
    	  ((JScrollPane)rootContainer).setColumnHeaderView(new ZoomControls(decode_pdf));
      }
      
    }else if(rootContainer instanceof JSplitPane){
      throw new RuntimeException("To add the simpleViewer to a split pane " +
      		"please pass through either JSplitPane.getLeftComponent() or" +
      		" JSplitPane.getRightComponent()");
    }
    
    if(!(rootContainer instanceof JFrame)){
      c.setLayout(new BorderLayout());
    }
    
    c.setPreferredSize(new Dimension(600, 750));
    
    currentGUI.setFrame(c);
  }
//-----------------------------------------------------------------------------
	private void addTopButtonPanel(JScrollPane scroller){
		JPanel buttonPanel = new JPanel(new MigLayout("rtl"));
		
		JPanel p = new JPanel();
		
		for(JButton button : topButtons){
			p.add(button);
			//button.setBorder(null);
		}
	
		buttonPanel.add(p, "pushx");
		buttonPanel.add(new ZoomControls(decode_pdf));
		scroller.setColumnHeaderView(buttonPanel);
	}
//-----------------------------------------------------------------------------
	private void addTopButtonPanels(JScrollPane scroller){
		JPanel buttonPanel = new JPanel(new MigLayout("rtl"));
	
		buttonPanel.add(topButtonPanel, "pushx");
		buttonPanel.add(new ZoomControls(decode_pdf));
		scroller.setColumnHeaderView(buttonPanel);
	}
//-----------------------------------------------------------------------------
	/**
	 * Initialize and run client PDF viewer. 
	 * If a root container wasn't specified, it will appear as in it's own
	 * frame separate from the application
	 */
	public void setupViewer() {
		//also allow messages to be suppressed with JVM option
		String flag=System.getProperty("org.jpedal.suppressViewerPopups");
		boolean suppressViewerPopups = false;
		if(flag!=null && flag.toLowerCase().equals("true")){
			suppressViewerPopups = true;
		}
	  
		/**
		 *  set search window position here to ensure
		 *  that gui has correct value
		
		String searchType = properties.getValue("searchWindowType");
		if(searchType!=null && searchType.length() != 0){
			System.out.println("System property: searchWindowType = "+searchType);
		    int type = Integer.parseInt(searchType);
		    searchFrame.setStyle(type);
		}else
		    searchFrame.setStyle(SwingSearchWindow.SEARCH_MENU_BAR);
		
		//Set search frame here
		currentGUI.setSearchFrame(searchFrame);
 */
		
        /*switch on thumbnails if flag is set
		String setThumbnail=System.getProperty("org.jpedal.thumbnail");
        if(setThumbnail!=null){
        	System.out.println("System property: org.jpedal.thumbnail = "+setThumbnail);
            if(setThumbnail.equals("true"))
                thumbnails.setThumbnailsEnabled(true);
            else if(setThumbnail.equals("true"))
                thumbnails.setThumbnailsEnabled(false);
        }else{ //default
            thumbnails.setThumbnailsEnabled(true);
        }
*/
        //non-GUI initialization
        String customBundle=System.getProperty("org.jpedal.bundleLocation");

        if(customBundle!=null){
        	System.out.println("System property: org.jpedal.bundleLocation = "+customBundle);
        	
            BufferedReader input_stream = null;
            ClassLoader loader = Messages.class.getClassLoader();
            String fileName=customBundle.replaceAll("\\.","/")+"_"+java.util.Locale.getDefault().getLanguage()+".properties";

            //also tests if locale file exists and tell user if it does not
            try{
                input_stream =new BufferedReader(new InputStreamReader(
                		loader.getResourceAsStream(fileName)));
                input_stream.close();
            }catch(Exception ee){
            	java.util.Locale.setDefault(new java.util.Locale("en", "EN"));
                currentGUI.showMessageDialog("No locale file "+fileName+
                		" has been defined for this Locale - using English as Default"+
                        "\n Format is path, using '.' as break ie org.jpedal.international.messages");
            }
            ResourceBundle rb = ResourceBundle.getBundle(customBundle);
            //Messages.setBundle(ResourceBundle.getBundle(customBundle));
            init(rb);
            
        }else
            init(null);


        //gui setup, create gui, load properties
        currentGUI.init(scalingValues,currentCommands,currentPrinter);
        mouseHandler.setupMouse();

        if(searchFrame.getStyle()==SwingSearchWindow.SEARCH_TABBED_PANE){
            currentGUI.searchInTab(searchFrame);
        }
        
        //setup window for warning if renderer has problem
        decode_pdf.getDynamicRenderer().setMessageFrame(currentGUI.getFrame());

        if(currentGUI.isSingle()){
        	//System.out.println("is single");
            TransferHandler singleViewTransferHandler = 
            		new SingleViewTransferHandler(commonValues, thumbnails, currentGUI, currentCommands);
            decode_pdf.setTransferHandler(singleViewTransferHandler);
        } else {
        	//System.out.println("is NOT single");
            TransferHandler multiViewTransferHandler = 
            		new MultiViewTransferHandler(commonValues, thumbnails, currentGUI, currentCommands);
            currentGUI.getMultiViewerFrames().setTransferHandler(multiViewTransferHandler);
        }
        
    }
//-----------------------------------------------------------------------------
	/**
	* setup the viewer
	*/
	@SuppressWarnings("static-access")
	protected void init(ResourceBundle bundle) {
	
		/**
	     * load correct set of messages
	     */
	    if(bundle==null){
	
	    	//load locale file
	    	try{
	    		Messages.setBundle(ResourceBundle.getBundle("org.jpedal.international.messages"));
	    	}catch(Exception e){
	    		LogWriter.writeLog("Exception "+e+" loading resource bundle.\n" +
	            "Also check you have a file in org.jpedal.international.messages to support Locale="
	    				+java.util.Locale.getDefault());
	    	}
	    }else{
	    	try{ Messages.setBundle(bundle); 
	    	}catch(Exception ee){
	                LogWriter.writeLog("Exception with bundle "+bundle);
	                ee.printStackTrace();
	        }
	    }
	    
	    //setup scaling values which are displayed for user to choose
	    this.scalingValues= new String[]{Messages.getMessage("PdfViewerScaleWindow.text"),
	    		Messages.getMessage("PdfViewerScaleHeight.text"),
	        Messages.getMessage("PdfViewerScaleWidth.text"),
	        "25%","50%","75%","100%","125%","150%","200%","250%","500%","750%","1000%"};
	
	
	    //setup display
		if(commonValues.isContentExtractor()){
			if (SwingUtilities.isEventDispatchThread()) {
				decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_LEFT_ALIGNED);
			} else {
				final Runnable doPaintComponent = new Runnable() {
					public void run() {
						decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_LEFT_ALIGNED);
					}
				};
				SwingUtilities.invokeLater(doPaintComponent);
			}
		} else {
			if (SwingUtilities.isEventDispatchThread()) {
				decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_CENTERED);
			} else {
				final Runnable doPaintComponent = new Runnable() {
					public void run() {
						decode_pdf.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_CENTERED);
					}
				};
				SwingUtilities.invokeLater(doPaintComponent);
			}
			
			//decode_pdf.setDisplayView(Display.SINGLE_PAGE,Display.DISPLAY_CENTERED);
			//pass through GUI for use in multipages and Javascript
			decode_pdf.addExternalHandler(currentGUI, Options.MultiPageUpdate);
	
			//make sure widths in data CRITICAL if we want to split lines correctly!!
			decode_pdf.init(true);
		}
	}
//-----------------------------------------------------------------------------
  /**
     * Allows external helper classes to be added to JPedal to alter default functionality.
     * <br><br>If Options.FormsActionHandler is the type then the <b>newHandler</b> should be
     * of the form <b>org.jpedal.objects.acroforms.ActionHandler</b>
     * <br><br>If Options.JPedalActionHandler is the type then the <b>newHandler</b> should be
     * of the form <b>Map</b> which contains Command Integers, mapped onto their respective
     * <b>org.jpedal.examples.simpleviewer.gui.swing.JPedalActionHandler</b> implementations.  For example,
     * to create a custom help action, you would add to your map, Integer(Commands.HELP) ->  JPedalActionHandler.
     * For a tutorial on creating custom actions in the SimpleViewer, see 
     * <b>http://www.jpedal.org/support.php</b>
     *
     * @param newHandler
     * @param type
     */
    public void addExternalHandler(Object newHandler, int type) {
      decode_pdf.addExternalHandler(newHandler, type);
    }
//-----------------------------------------------------------------------------
  public void dispose() {
   
    commonValues=null;
    currentPrinter=null;
    if(thumbnails!=null){ thumbnails.dispose(); }
    
    thumbnails=null;
    properties.dispose();
    properties=null;
    
    if(currentGUI!=null) { currentGUI.dispose(); }
    
    currentGUI=null;
    searchFrame=null;
    currentCommands=null;
    mouseHandler=null;
    scalingValues=null;

    if(decode_pdf!=null){ decode_pdf.dispose(); }
    
    decode_pdf =null;
    Messages.dispose(); 
  }
//-----------------------------------------------------------------------------
}