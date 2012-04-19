package blueBookTab;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import utilities.FileHelper;
import utilities.pdf.PDFHelper;
import utilities.ui.ImageHandler;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

//-----------------------------------------------------------------------------
/**
 * The <code>BlueBookEntry</code> class represents a particular incident instance 
 * including name, date of birth, affiliation, address, crime description, narrative
 * date, time, location, case number, weapon, and attached photos and videos
 */
public class BlueBookEntry {
	/** Name of individual associated with Entry */
	private String name;
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
	/** Time BlueBook Entry was reported */
	private String time;
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
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the name of a BOLO individual
	 * @param name - the name value to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return date 
	 */
	public String getDate() {
		return date;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return time 
	 */
	public String getTime() {
		return time;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
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
	public void setPhotoFilePath(Path p_photoFilePath) {
		this.photoFilePath = p_photoFilePath;
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
	 * 
	 * @return photo - an ImageIcon of the respective photo
	 */
	public ImageIcon getPhoto(){
		ImageIcon photo = ImageHandler.getThumbnailImageIcon(photoFilePath);
		if(photo==null){ System.out.printf("null photo\n"); }
		return photo;
	}
//-----------------------------------------------------------------------------
	/**
	 * <b> addToDB </b>
	 * <pre>public void addToDB() throws Exception</pre> 
	 * <blockquote> 
	 * Adds this BlueBookEntry to the 'bluebook' table in the database.
	 * </blockquote>
	 * @throws Exception
	 */
	public void addToDB() throws Exception{
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

	    //Add the data to the prepared statement
	    if(isArmed){ prep.setInt(1, 1); } else { prep.setInt(1, 0); }
	    prep.setString(2, this.narrative);
	    prep.setString(3, this.crimeDescrip);
	    prep.setString(4, this.location);
	    prep.setString(5, this.address);
	    prep.setString(6, this.affili);
	    prep.setString(7, this.dob);
	    prep.setString(8, this.name);
	    prep.setString(9, this.caseNum);
	    prep.setString(10, this.time);
	    prep.setString(11, this.date);

	    if(this.bbID!=null){ prep.setInt(14, this.bbID); }

	    if(photoFilePath!=null){
		    Path absPhotoFilePath = photoFilePath.toAbsolutePath();
		    photoPathName = absPhotoFilePath.toString();
		    System.out.println("photoPathName = " + photoPathName);
	    }
	    prep.setString(12, photoPathName);	    

	    if(videoFilePath!=null){
	    	Path absVideoFilePath = videoFilePath.toAbsolutePath();
	    	videoPathName = absVideoFilePath.toString();
	    } 
	    prep.setString(13, videoPathName);
	    prep.setBytes(15, getBytes(photoFilePaths));

	    prep.addBatch();

	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);

	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------
    private byte[] getBytes(ArrayList<String> p_photoFilePaths) throws IOException, SQLException {
    	    byte[] bytes = null;
    	
    		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    		ObjectOutput out = new ObjectOutputStream(bos);   
    		out.writeObject(p_photoFilePaths);
    		
    		out.close();
    		out.flush();
    		bos.close();
    		bytes = bos.toByteArray();
    		
		return bytes;
	}

//-----------------------------------------------------------------------------
    public static Object getObjectFromBytes(byte[] bytes) throws SQLException, IOException, ClassNotFoundException {
    	Object o = null;
    	//blob.getBytes(1, );
    	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	//ByteArrayInputStream bis = (ByteArrayInputStream) blob.getBinaryStream();
    	ObjectInput in = new ObjectInputStream(bis);
    	o = in.readObject(); 
    	
    	bis.close();
    	in.close();
    	
    	return o;
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

	}
//-----------------------------------------------------------------------------
	/**
	 * Save this BlueBookEntry to a pdf file based on a template.
	 * 
	 */
	public String saveToFileSystem(String filename){
		File saveAsFile = new File(filename);
		
		PdfStamper stamper;
				
		if(saveAsFile.exists()){
			stamper = PDFHelper.getPdfStampler(filename);
		} else{
			stamper = PDFHelper.getPdfStampler(formPathName, filename);
		}
		
		fill(stamper);
		
		//flattens the form so fields cannot be edited
		stamper.setFormFlattening(true);
		
		try{ stamper.close(); return filename; } 
		catch(Exception e){ e.printStackTrace(); return null; }
	}
//-----------------------------------------------------------------------------
	public String createNewUniqueFilename(){
		//create the filename the saved form will have
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
		dateFormat.applyPattern("MM_dd_yyyy_HH_mm");
		Date date = new Date(System.currentTimeMillis());
		String dateTime = dateFormat.format(date);
		String saveAs = FileHelper.getBBEntryPdfPathName("BlueBookForm" + dateTime + ".pdf");
		this.date=dateTime;
		
		//if a file w/ this name already exists, append extra extension to end
		File saveAsFile = new File(saveAs);
		int i=0;
		while((i<100) && (saveAsFile.exists())){
			i++;
			String newFileName = FileHelper.getNameWithoutExtension(saveAs.toString()) 
					+"_v" + i + "." + FileHelper.getFileExtension(saveAsFile);
			saveAsFile = new File(saveAs);
		}
		
		return saveAsFile.toString();
	}
//-----------------------------------------------------------------------------
	/**
	 * Save this ShiftCdrReport's information in a pdf on the file system
	 * 
	 */
	public void loadInfoIntoForm(String saveAs){
		//used to put text into the form
		PdfStamper stamper = PDFHelper.getPdfStampler(formPathName, saveAs);
		
		fill(stamper);
		
		//flattens the form so fields cannot be edited
		stamper.setFormFlattening(false);
		
		try{ stamper.close(); } 
		catch(Exception e){ e.printStackTrace(); }
	}
//-----------------------------------------------------------------------------
    /**
     * Fill out the fields using this Entry's info.
     * @param form - the form object
     */
    public void fill(PdfStamper stamper) {
    	AcroFields form = stamper.getAcroFields();
    	
    	try{
    		if(this.caseNum!=null)
    			form.setField("caseNum", this.getCaseNum()); 
	    	if(this.date!=null)
	    		form.setField("date", this.getDate());
	        if(this.name!=null)
	        	form.setField("name", this.getName());
	       //TODO: finish extracting the rest of these fields!
	        //The png of the form with the fields names in in dropbox!!!
	        //Do it!
	        //You! Now!
	     // ...
	        
	        //TODO: add photo(s)
    	} catch(Exception e){
    		e.printStackTrace();
    	}
        
    }
//-----------------------------------------------------------------------------
  /*  public void addPhotoToForm(PdfStamper stamper) {
    	PdfWriter writer = stamper.getWriter();
    	writer.
    	*/
    	/*
    	 * 
   java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(RESOURCE);
  img = com.itextpdf.text.Image.getInstance(awtImage, null);
  document.add(img);
    	 */
    //}
    
    public String createPdf(String filename)
            throws IOException, DocumentException {
        	// step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            // step 3
            document.open();
            // step 4
            document.add(createParagraph(
                "My favorite movie featuring River Phoenix was ", "0092005"));
            document.add(createParagraph(
                "River Phoenix was nominated for an academy award for his role in ", "0096018"));
            document.add(createParagraph(
                "River Phoenix played the young Indiana Jones in ", "0097576"));
            document.add(createParagraph(
                "His best role was probably in ", "0102494"));
            // step 5
            document.close();
            
            return filename;
        }
//-----------------------------------------------------------------------------
    /**
     * Creates a paragraph with some text about a movie with River Phoenix,
     * and a poster of the corresponding movie.
     * @param text the text about the movie
     * @param imdb the IMDB code referring to the poster
     * @throws DocumentException
     * @throws IOException
     */
    public Paragraph createParagraph(String text, String imdb)
        throws DocumentException, IOException {
        Paragraph p = new Paragraph(text);
        Image img = Image.getInstance((this.getPhotoFilePath().toString()));
        img.scaleToFit(1000, 72);
        img.setRotationDegrees(-30);
        p.add(new Chunk(img, 0, -15, true));
        return p;
    }
//-----------------------------------------------------------------------------
}
