package utilities;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import shiftCdrTab.RollCall;
import blueBookTab.BlueBookEntry;
import boloTab.Bolo;
//-----------------------------------------------------------------------------
/**
 * A helper class designed to make accessing the crime table within the
 * database in program easier. 
 * This classes methods should be used exclusively to interact with the 
 * The <code>DatabaseHelper</code> class is designed to make accessing 
 * the crime table within the database in program easier. 
 * These methods should be used exclusively to interact with the 
 * database to ensure thread safety. Only one connection can write to the
 * database at a time, thus once a connection is opened the entire database 
 * locks until it is closed. 
 *
 */
public class DatabaseHelper {	
//-----------------------------------------------------------------------------
	/**
	 * Retrieves all the <code>Bolo</code>s from the database and places them into an 
	 * <code>Arraylist</code> of <code>Bolo</code> objects, which is returned to the 
	 * caller.
	 * @return boloList - an <code>Arraylist</code> of <code>Bolo</code> objects
	 * @throws Exception
	 */
	public static ArrayList<Bolo> getBOLOsFromDB() throws Exception{
		ArrayList<Bolo> boloList = new ArrayList<Bolo>();
		String age, race, sex, height, weight, build, eyes, hair;
		String reference, caseNum, status, weapon;
		String preparedBy, approvedBy;
		String otherDescrip, narrative, photoPath, videoPath;
		long incidentDate=0, prepDate=0;
		 
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
		
		//test to make database file access syst indep, changed added Project
		//Path dbFilePath = Paths.get("Project", "Database", "umpd.db");
		Path dbFilePath = Paths.get("Database", "umpd.db");

		String dbFileName = dbFilePath.toString();
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
	    
	    Statement stat = conn.createStatement();
	    ResultSet allBOLOs = stat.executeQuery("SELECT * FROM bolo;");

	    Bolo bolo;
	    while (allBOLOs.next()) {
	    	bolo = new Bolo();
	        
	    	bolo.setBoloID(allBOLOs.getInt("bolo_id"));
	    	
	        age = allBOLOs.getString("age");
	        if(age!=null){ bolo.setAge(age); }
			race = allBOLOs.getString("race");
			if(race!=null){ bolo.setRace(race); }
			sex = allBOLOs.getString("sex");
			if(sex!=null){ bolo.setSex(sex); }
			height = allBOLOs.getString("height");
			if(height!=null){ bolo.setHeight(height); }
			weight = allBOLOs.getString("weight");
			if(weight!=null){ bolo.setWeight(weight); }
			build=allBOLOs.getString("build");
			if(build!=null){ bolo.setBuild(build); }
			eyes=allBOLOs.getString("eyes");
			if(eyes!=null){ bolo.setEyes(eyes); }
			hair=allBOLOs.getString("hair");
			if(hair!=null){ bolo.setHair(hair); }
			reference=allBOLOs.getString("reference");
			if(reference!=null){ bolo.setReference(reference); }
			caseNum=allBOLOs.getString("caseNum");
			if(caseNum!=null){ bolo.setCaseNum(caseNum); }
			status=allBOLOs.getString("status");
			if(status!=null){ bolo.setStatus(status); }
			weapon=allBOLOs.getString("weapon");
			if(weapon!=null){ bolo.setWeapon(weapon); }
			preparedBy= allBOLOs.getString("prepedBy");
			if(preparedBy!=null){ bolo.setPreparedBy(preparedBy); }
			approvedBy= allBOLOs.getString("approvedBy");
			if(approvedBy!=null){ bolo.setApprovedBy(approvedBy); }
			otherDescrip= allBOLOs.getString("description");
			if(otherDescrip!=null){ bolo.setOtherDescrip(otherDescrip); }
			narrative=allBOLOs.getString("narrative");
			if(narrative!=null){ bolo.setNarrative(narrative); }
			incidentDate = allBOLOs.getLong("incidentDate");
			if(incidentDate!=0){ bolo.setincidentDate(incidentDate); }
			prepDate = allBOLOs.getLong("prepdate");
			if(prepDate!=0){ bolo.setprepDate(prepDate); }
			
			photoPath=allBOLOs.getString("photoPath");
			if(photoPath!=null){ 
				Path pp = Paths.get(photoPath);
				bolo.setPhotoFilePath(pp);
//DEBUG:
			} else { 
				System.out.printf("\n photo path is null\n");
			}
			
			videoPath=allBOLOs.getString("videoPath");
			if(videoPath!=null){ 
				Path vp = Paths.get(videoPath);
				bolo.setVideoFilePath(vp);
//DEBUG:			
			} else { 
				//System.out.printf("\n video path is null\n");
			}

			boloList.add(bolo);
	    }
	    	
	    //Close the connections
	    allBOLOs.close();
	    conn.close();
	    
	    return boloList;
	}
//-----------------------------------------------------------------------------
	/**
	 * Retrieves all the <code>BlueBookEntry</code>s from the database and places them into an 
	 * <code>Arraylist</code> of <code>BlueBookEntry</code> objects, which is returned to the 
	 * caller.
	 * @return bluebook - an <code>Arraylist</code> of <code>BlueBookEntry</code> objects
	 * @throws Exception
	 */
	public static ArrayList<BlueBookEntry> getBluebookFromDB() throws Exception{
		ArrayList<BlueBookEntry> bluebook = new ArrayList<BlueBookEntry>();
		String name, caseNum, time, date;
		String narrative, description, location, address, affili, dob;
		String preparedBy, approvedBy, photoPath, videoPath;
		long incidentDate=0, prepDate=0;
		
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");

		Path dbFilePath = Paths.get("Database", "umpd.db");

		String dbFileName = dbFilePath.toString();
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
	    
	    Statement stat = conn.createStatement();
	    ResultSet allEntries = stat.executeQuery("SELECT * FROM bluebook;");

	    BlueBookEntry entry;
	    while (allEntries.next()) {
	    	entry = new BlueBookEntry();
	    	entry.setBluebkID(allEntries.getInt("bbID"));
	    	
	    	name = allEntries.getString("offenderName");
	        if(name!=null){ entry.setName(name); }
	        narrative = allEntries.getString("narrartive");
	        if(narrative!=null){ entry.setNarrative(narrative); }
	       // preparedBy = allEntries.getString("preparedBy");
	       // if(preparedBy!=null){ entry.setPreparedBy(preparedBy); }
	        address = allEntries.getString("address");
	        if(address!=null){ entry.setAddress(address); }
	        affili = allEntries.getString("affili");
	        if(affili!=null){ entry.setAffili(affili); }
	        caseNum = allEntries.getString("caseNum");
	        if(caseNum!=null){ entry.setCaseNum(caseNum); }
	        description = allEntries.getString("description");
	        if(description!=null){ entry.setCrimeDescrip(description); }
	        dob = allEntries.getString("dob");
	        if(dob!=null){ entry.setDob(dob); } 
	        location = allEntries.getString("location");
	        if(location!=null){ entry.setLocation(location); }
	        
	        photoPath = allEntries.getString("photoFileName");
	        if(photoPath!=null){ 
				Path pp = Paths.get(photoPath);
				entry.setPhotoFilePath(pp);
	        } else { 
	        	System.out.printf("\nphoto path is null\n");
	        }
	        bluebook.add(entry);
	    }
	    	
	    //Close the connections
	    allEntries.close();
	    conn.close();
	    
	    return bluebook;
	} 
//-----------------------------------------------------------------------------
	/**
	 * Takes <code>RollCall</code> data imported from the the UMPD Scheduler and populates the database
	 * 
	 * @param name - officer name
	 * @param present - either here or not here
	 * @param comment - add text about that day
	 * @param timeArrived - time the officer arrived 
	 * @param shiftDate - the date of the officers shift
	 * @throws Exception
	 */
	public void addRollCall(String name, String present, String comment, 
			String timeArrived, String shiftDate) throws Exception {
		
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
		Path dbFilePath = Paths.get("Database", "umpd.db");
		String dbFileName = dbFilePath.toString();
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
		
		//check for already existing shift
		//Statement stat = conn.createStatement();
		//ResultSet allEntries = stat.executeQuery("SELECT 1 FROM RollCall WHERE ShiftDate = shiftDate;");
		
		//if (allEntries.next()) {    
			//JOptionPane.showMessageDialog(null, "Shift already submitted for this date.");
			//return;
		//}
		
		//insert into person table
		PreparedStatement personStatement = conn.prepareStatement(
				"INSERT into RollCall(roll_call_ID, Name, Present, Comment, TimeArrived, ShiftDate) " +
				"VALUES(?,?,?,?,?,?);"
		    );
		
		//long shiftDateEpoch = convertDateToEpoch(shiftDate);
		    
		personStatement.setString(1, null);
		personStatement.setString(2,name);
		personStatement.setString(3,present);
		personStatement.setString(4,comment);
		personStatement.setString(5,timeArrived);
		personStatement.setString(6,shiftDate);
		personStatement.addBatch();

	    //Create new row in the table for the data
		conn.setAutoCommit(false);
		personStatement.executeBatch();
		conn.setAutoCommit(true);
		
		//Close the connection
		conn.close();
	}

//-----------------------------------------------------------------------------
    /**
     * Takes <code>RollCall</code> data stored in the database and populate the 
     * <code>ShiftCdrTab</code> 
     * 
     * @param shiftDate - the officer's assigned date to come into work
     * @return rollCallList - an <code>Arraylist</code> of <code>RollCall</code> objects
     * @throws Exception
     */
	public ArrayList<RollCall> getRollCallFromDatabase(String shiftDate) throws Exception {
    	ArrayList<RollCall> rollCallList = new ArrayList<RollCall>();
    	// Implement later TODO
    	
    	//Create the connection to the database
    	Class.forName("org.sqlite.JDBC");
    			
    	//test to make database file access syst indep, changed added Project
    	//Path dbFilePath = Paths.get("Project", "Database", "umpd.db");
    	Path dbFilePath = Paths.get("Database", "umpd.db");

    	String dbFileName = dbFilePath.toString();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
    	
        Statement stat = conn.createStatement();
	    ResultSet allEntries = stat.executeQuery("SELECT * FROM RollCall WHERE ShiftDate = shiftDate;");
    	
	    //RollCall rollCall;
	    
	    /*
	    // hacktastic method
	    int i = 0;
	    
	    int count = 0;
	    while (allEntries.next()) {
	    	count++;
	    }
	    
	    ResultSet allEntries2 = stat.executeQuery("SELECT * FROM RollCall WHERE ShiftDate = shiftDate;");
	    RollCall[] rollCallArray = new RollCall[count];
	 
	    int m = 0;
	    while (allEntries2.next()) {
	        rollCall = new RollCall();
	    
	        rollCall.setName(allEntries.getString("Name")); 
	        rollCall.setPresent(allEntries.getString("Present"));
	        rollCall.setComment(allEntries.getString("Comment"));
	        rollCall.setTimeArrived(allEntries.getString("TimeArrived"));
	        
	        rollCallArray[m++] = rollCall;
	    }
	    Collections.addAll(rollCallList, rollCallArray);
	    */
	    
	    //right method, that doesn't fucking work! 
	    //Update, works, but not the way I originally wanted it to / it should
	    RollCall rollCall;
	    while (allEntries.next()) {
	    	
	    	//hack-fu
	    rollCallList.add(new RollCall(allEntries.getString("Name"),
	    			allEntries.getString("Present"), allEntries.getString("TimeArrived"),
	    			allEntries.getString("Comment")));
	    	
	    }
	    allEntries.close();
	    conn.close();
	   
	    for (RollCall testrollCall : rollCallList) {   	
        	System.out.println(testrollCall.getName());
        }
    	
    	return rollCallList;
    }
//-----------------------------------------------------------------------------
	/*
	 * Wrote this code when thinking I needed a many to many relational database
	 * probably unnecessary now, keeping it just in case
	 * -BL
	 */
	
	/*
	public static void addShift(int shiftNumber, String date) throws Exception {
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	    
	    //insert into shift table
	    PreparedStatement shiftStatement = conn.prepareStatement(
	    		"INSERT into Person(ShiftNumber, Date) " +
	    		"VALUES(?,?);"
	        );
	    shiftStatement.setInt(1,shiftNumber);
	    shiftStatement.setString(2,date);
	    shiftStatement.addBatch();
	    
	  //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    shiftStatement.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------
	public static void addShiftPerson(int shiftNumber, int Cnumber) throws Exception {
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	    
	    //insert into shift table
	    PreparedStatement shiftPersonStatement = conn.prepareStatement(
	    		"INSERT into Person(ShiftNumber, Cnumber) " +
	    		"VALUES(?,?);"
	        );
	    shiftPersonStatement.setInt(1,shiftNumber);
	    shiftPersonStatement.setInt(2,Cnumber);
	    shiftPersonStatement.addBatch();
	    
	  //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    shiftPersonStatement.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
	*/
//-----------------------------------------------------------------------------
	/**
	 * Converts a <code>Date</code> object into a <code>long</code> representing
	 * the number of seconds elapsed since the epoch. 
	 * @param date - <code>Date</code> object to convert
	 * @return <code>long</code> value representing time in seconds since epoch
	 */
	public static long convertDateToEpoch(Date date){
		long epoch = (date.getTime() / 1000);
		return epoch;
	}
//-----------------------------------------------------------------------------
	/**
	 * Converts a <code>long</code> value representing time in seconds since 
	 * epoch into a <code>Date</code> object representing the same time. 
	 * @param epoch - time in seconds since epoch
	 * @return <code>Date</code> object representing the given time
	 */
	public static Date convertEpochToDate(long epoch){
		Date date = new Date(epoch*1000);
		return date;
	}
//-----------------------------------------------------------------------------
}
