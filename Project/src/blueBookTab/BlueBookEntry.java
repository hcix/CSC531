package blueBookTab;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import utilities.ChangeHelper;
import utilities.FileHelper;
import utilities.pdf.PDFHelper;
import utilities.ui.ImageHandler;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfStamper;

//-----------------------------------------------------------------------------
/**
 * The <code>BlueBookEntry</code> class represents a particular incident instance 
 * including name, date of birth, affiliation, address, crime description, narrative
 * date, time, location, case number, weapon, and attached photos and videos
 */
public class BlueBookEntry {
	/** First name of individual associated with Entry */
	private String firstName;
	/** Last name of individual associated with Entry */
	private String lastName;
	/** Middle name of individual associated with Entry */
	private String middleName;
	/** Date of birth of individual associated with Entry*/
	private String dob;
	/** Affiliation of individual associated with Entry */
	private String affili;
	/** Address of a reported BlueBook Entry */
	private String address;
	/** Description of crime associated with a BlueBook Entry */
	private String crimeDescrip;
	/** Narrative of individual reporting a BlueBook Entry */
	private String narrative;
	/** Date BlueBook Entry was reported */
	private String date;
	private long incidentDate=0;
	/** Time BlueBook Entry was reported */
	private String time;
	private long incidentTime=0;
	/** Location BlueBook Entry was reported at*/
	private String location;
	/** BlueBook Entry's case number */
	private String caseNum;
	/** Status of BlueBook Entry */
	private String status;
	/** Weapon carried by an individual associated with the BlueBook Entry */
	private String weapon;
	/** Indicates if associated individual is armed with a weapon */
	private boolean isArmed=false;
	/** Officer that prepared the BlueBook Entry */
	private String preparedBy;
	/** Path in file system leading to photo pertaining to the BlueBook Entry*/
	private Path photoFilePath = null;
	/** Path in file system leading to a video pertaining to the BlueBook Entry*/
	private Path videoFilePath = null;
	/** ID of BlueBook Entry in the Blue book*/
	private Integer bbID=null;
	/** list of absolute filenames of photos associated with this BlueBook Entry */
	ArrayList<String> photoFilePaths;
	/**JDOC*/
	private String[] fieldArray;
	/** JDOC */
	String filename;
	/** JDOC */
	String formPathName = FileHelper.getFormTemplatePathName("BlueBookForm.pdf");
//-----------------------------------------------------------------------------
	/**
	 * Creates a new <code>BlueBookEntry</code> with all fields initially empty
	 */
	public BlueBookEntry(){
		fieldArray = new String[16];
		for(int i=0; i<fieldArray.length; i++){
			fieldArray[i]=null;
		}
		photoFilePaths = new ArrayList<String>();
		firstName="";
		middleName="";
		lastName="";
	}
//-----------------------------------------------------------------------------
	/**
	 * Break up the given string and set it into the appropriate boxes
	 */
	public void setFullName(String fullName) {
		StringTokenizer st = new StringTokenizer(fullName, ",");
	    if(st.hasMoreTokens()) {
	    	lastName = st.nextToken();
	    } 
	    if(st.hasMoreTokens()) {
	    	firstName = st.nextToken();
	    }
	    if(st.hasMoreTokens()) {
	    	middleName = st.nextToken();
	    }
	    
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the first name of a BOLO individual
	 * @param first name - the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the last name of a BOLO individual
	 * @param lastName - the name value to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the middle name of a BOLO individual
	 * @param middleName - the name value to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the date of birth for a BOLO individual
	 * @return dob - date of birth
	 */
	public String getDob() {
		return dob;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dob - date of birth
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return affili - affiliation
	 */
	public String getAffili() {
		return affili;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param affili - affiliation
	 */
	public void setAffili(String affili) {
		this.affili = affili;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return address - address of the individual 
	 */
	public String getAddress() {
		return address;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param address - address of the individual 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return crimeDescrip - description of the crime
	 */
	public String getCrimeDescrip() {
		return crimeDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @param crimeDescrip - description of the crime
	 */
	public void setCrimeDescrip(String crimeDescrip) {
		this.crimeDescrip = crimeDescrip;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return narrative - account of events
	 */
	public String getNarrative() {
		return narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param narrative - account of events
	 */
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return incidentDate 
	 */
	public long getIncidentDate() {
		return this.incidentDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 */
	public void setIncidentDate(long incidentDate) {
		this.incidentDate = incidentDate;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 */
	public long getIncidentTime() {
		return this.incidentTime;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 */
	public void setIncidentTime(long time) {
		this.incidentTime = time;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return location 
	 */
	public String getLocation() {
		return location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return caseNum - case number
	 */
	public String getCaseNum() {
		return caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param caseNum - case number
	 */
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return status 
	 */
	public String getStatus() {
		return status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return weapon 
	 */
	public String getWeapon() {
		return weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param weapon 
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return isArmed
	 */
	public boolean isArmed() {
		return isArmed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param isArmed
	 */
	public void setArmed(boolean isArmed) {
		this.isArmed = isArmed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return preparedBy - the officer who prepared the document 
	 */
	public String getPreparedBy() {
		return preparedBy;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param preparedBy - the officer who prepared the document 
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}		
//-----------------------------------------------------------------------------
	 /**
	 * @return bbID - unique ID used in the database
	 */
	public int getBbID() {
		return bbID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param bbID - unique ID used in the database
	 */
	public void setBbID(int bbID) {
		this.bbID = bbID;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param photoFilePath - the path of the photo associated with this each entry
	 */
	public void setPhotoFilePath(Path photoFilePath) {
		this.photoFilePath = photoFilePath;
		photoFilePaths.add(photoFilePath.toString());
	}
//-----------------------------------------------------------------------------
	/**
	 * @return path - where on the disk the associated photo lies
	 */
	public Path getPhotoFilePath() {
		return photoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param videoFilePath - where on the disk the associated video lies
	 */
	public void setVideoFilePath(Path videoFilePath) {
		this.videoFilePath = videoFilePath;
	}
public ArrayList<String> getPhotoFilePaths() {
		return photoFilePaths;
	}
	public void setPhotoFilePaths(ArrayList<String> photoFilePaths) {
		this.photoFilePaths = photoFilePaths;
	}
	//-----------------------------------------------------------------------------
	/**
	 * @return videoFilePath - where on the disk the associated video lies
	 */
	public Path getVideoFilePath() {
		return videoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return filename - 
	 */
	public String getFilename() {
		return filename;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param filename - the filename value to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return photo - an ImageIcon of the respective photo
	 */
	public ImageIcon getPhoto(){
		ImageIcon photo = ImageHandler.getThumbnailImageIcon(photoFilePath);
//DEBUG if(photo==null){ System.out.printf("null photo\n"); }
		return photo;
	}
//-----------------------------------------------------------------------------
    private byte[] getBytes(ArrayList<String> photoFilePaths) throws IOException, SQLException {
    	    byte[] bytes = null;
    	
    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		ObjectOutput out = new ObjectOutputStream(bos);   
    		out.writeObject(photoFilePaths);
    		bytes = bos.toByteArray();
    		//close
    		out.close();
    		out.flush();
    		bos.close();
    		
    		
		return bytes;
	}
 //-----------------------------------------------------------------------------
    private SerialBlob getBlob(ArrayList<String> photoFilePaths) throws IOException, SQLException {
	    SerialBlob blob = null;
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);   
		out.writeObject(photoFilePaths);
		blob = new SerialBlob(bos.toByteArray());
		//close
		out.close();
		out.flush();
		bos.close();
		
			
		return blob;
	}
//-----------------------------------------------------------------------------
    public static Object getObjectFromBytes(byte[] bytes) throws SQLException, IOException, ClassNotFoundException {
    	Object o = null;
    	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	ObjectInput in = new ObjectInputStream(bis);
    	o = in.readObject(); 
    	
    	bis.close();
    	in.close();
    	
    	return o;
    }
//-----------------------------------------------------------------------------
	/**
	 * Adds this <code>BlueBookEntry</code> to the 'bluebook' table in the database.
	 * @throws Exception
	 */
	public void addToDB() throws Exception{
	    String name;
		String photoPathName = null, videoPathName = null;
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:Database/umpd.db");

	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "REPLACE into bluebook(armed, narrative, description, location, address, affili," +
	      " dob, name, caseNum, time, date, photoFileName," +
	      " videoFileName, bbID, photofilenames)" + 
	      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

	    name = this.lastName + "," + this.firstName + "," + this.middleName;

	    
	    //Add the data to the prepared statement
	    if(isArmed){ prep.setInt(1, 1); } else { prep.setInt(1, 0); }
	    prep.setString(2, this.narrative);
	    prep.setString(3, this.crimeDescrip);
	    prep.setString(4, this.location);
	    prep.setString(5, this.address);
	    prep.setString(6, this.affili);
	    prep.setString(7, this.dob);
	    prep.setString(8, name);
	    prep.setString(9, this.caseNum);
	    prep.setString(10, this.time);
	    prep.setString(11, this.date);

	    if(this.bbID!=null){ prep.setInt(14, this.bbID); }

	    if(photoFilePath!=null){
		    Path absPhotoFilePath = photoFilePath.toAbsolutePath();
		    //photoPathName = absPhotoFilePath.toString();
		    photoPathName = FileHelper.getFilenameFromAbsPath(absPhotoFilePath);
		    System.out.println("photoPathName = " + photoPathName);
	    }
	    prep.setString(12, photoPathName);	    

	    if(videoFilePath!=null){
	    	Path absVideoFilePath = videoFilePath.toAbsolutePath();
	    	videoPathName = FileHelper.getFilenameFromAbsPath(absVideoFilePath);
	    } 
	    prep.setString(13, videoPathName);
	    //prep.setBlob(15, getBlob(photoFilePaths));
	    prep.setBytes(15, getBytes(photoFilePaths));
	    prep.addBatch();

	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);

	    //Close the connection
	    conn.close();
	    ChangeHelper.makeChange(ChangeHelper.ADD_BB_ENTRY);
	}
//-----------------------------------------------------------------------------
	/**
	 * Deletes this BlueBook Entry object from the 'BlueBook Entry' table in the database.
	 * @throws Exception
	 */
	public void deleteFromDB() throws Exception {
		//check that this BlueBook Entry is in the DB before attempting to delete it
		if(bbID==null){
			//do nothing, this BlueBook Entry was never written to the database 
			return;
		}

		//create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:Database/umpd.db");	
	    Statement stat = conn.createStatement();

	    //perform delete
	    stat.executeUpdate("DELETE FROM bluebook WHERE bbID = " + bbID);

	    //close the connection
	    conn.close();
	    ChangeHelper.makeChange(ChangeHelper.DELETE_BB_ENTRY);
	}
//-----------------------------------------------------------------------------
	/**
	 * Create a unique filename to save an instance of the related form version
	 * of this Entry entity.
	 */
	public String createNewUniqueFilename(){
		//create the filename the saved form will have
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
		dateFormat.applyPattern("MM_dd_yyyy_HH_mm");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		this.date=dateTime;
		
		//if a file w/ this name already exists, append extra extension to end
		String filename = FileHelper.createNewUniqueFilename(
				FileHelper.BLUE_BK_ENTRY_FILE);
	
		this.filename = filename;
		return filename;
	}
//-----------------------------------------------------------------------------
    /**
     * Fill out the fields using this Entry's info.
     * @param form - the form object
     */
    public void fill(PdfStamper stamper) {
    	String name;
    	AcroFields form = stamper.getAcroFields();
    	
    	//TODO play with size of form field on BlueBookForm to fix apperance
    	
    	Format formatter = new SimpleDateFormat("MMMMM dd, yyyy");
    	try{
    		if(this.caseNum!=null){
    			form.setField("caseNum", this.getCaseNum()); }
	    	if(this.date!=null){
	    		form.setField("date", formatter.format(new Date(this.getIncidentDate()))); }
	        if(!this.lastName.equals("")){
	        	form.setField("lastName", this.getLastName()); }
	        if(!this.firstName.equals("")){
	        	form.setField("firstName", this.getFirstName()); }
	        if(!this.middleName.equals("")){
	        	form.setField("middleName", this.getMiddleName()); }
	        if(this.dob!=null)
	        	form.setField("dob", this.getDob());
	        if(this.affili!=null)
	        	form.setField("affili", this.getAffili());
	        if(this.address!=null){
	        	form.setField("addr_street_1", this.getAddress());
	        	form.setField("addr_street_2", this.getAddress());
	        	form.setField("addr_city", this.getAddress());
	        	form.setField("addr_state", this.getAddress());
	        	form.setField("addr_zip", this.getAddress());
	        }
	        if(this.crimeDescrip!=null)
	        	form.setField("descrip", this.getCrimeDescrip());
	        if(this.location!=null)
	        	form.setField("location", this.getLocation());
	        if(this.narrative!=null)
	        	form.setField("narrative", this.getNarrative());
	        
    	} catch(Exception e){
    		e.printStackTrace();
    	}
        
    }
//-----------------------------------------------------------------------------
	/**
	 * Save this BlueBookEntry's information in a pdf on the file system.
	 */
    public String createPdf(String fn) throws IOException, DocumentException{
    	//used to put text into the form
		PdfStamper stamper = PDFHelper.getPdfStampler(formPathName, fn);
		
		fill(stamper);
              
    	PdfContentByte content = stamper.getOverContent(1);

    	//float startYInPts = Utilities.inchesToPoints(8);
    	float startYInPts = Utilities.inchesToPoints(3.5f) - 100;
    	float startXInPts = 30f;
    	
    	for(String photo:photoFilePaths){
    		Image img = Image.getInstance(photo);
    		img.setBorder(Image.BOX);
    		img.setAbsolutePosition(startXInPts, startYInPts);
    		content.addImage(img);
    		startXInPts = img.getRight() + 10;
    	}
    	
		//flattens the form so fields cannot be edited
		stamper.setFormFlattening(false);
		
		try{ stamper.close(); } 
		catch(Exception e){ e.printStackTrace(); }
		
    	return fn;
    }
//-----------------------------------------------------------------------------
}
