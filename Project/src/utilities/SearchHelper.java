package utilities;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import shiftCdrTab.RollCall;
import blueBookTab.BlueBookEntry;
import boloTab.Bolo;
//-----------------------------------------------------------------------------
/**
 * The <code>SearchHelper</code> class is designed to aid in the ability to search 
 * the database to find a desired query.
 *
 */
public class SearchHelper {
//-----------------------------------------------------------------------------
	/**
	 * Queries the database for the desired <code>String</code> parameters
	 * 
	 * @param table - either <code>Bolo</code>, <code>BlueBookEntry</code>, or <code>RollCall</code>
	 * @param fields - 
	 * @param parameters - the search parameters
	 * @return depending on the <code>table</code> it calls the appropriate method. 
	 * (<code>fillBOLO()</code>, <code>fillBlueBook()</code>, <code>fillRollCall()</code>)
	 * @throws Exception
	 */
	public static ArrayList<?> search (String table, ArrayList<String> fields, ArrayList<String> parameters) throws Exception {
		String queryString;
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
				
		//test to make database file access system independent, changed added Project
		//Path dbFilePath = Paths.get("Project", "Database", "umpd.db");
		Path dbFilePath = Paths.get("Database", "umpd.db");

		String dbFileName = dbFilePath.toString();
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
			    
	    Statement stat = conn.createStatement();
	    
	    // construct query string
	    queryString = "SELECT * FROM " + table + " WHERE ";
	    for (int i = 0; i < parameters.size(); i++) {
	    	if (i != 0)
	    		queryString = queryString.concat(" AND ");
	    	queryString = queryString.concat(fields.get(i) + " = " + "'" + 
	    		parameters.get(i) + "'");
	    }
	    queryString = queryString.concat(";");
	    
	    System.out.println(queryString); //DEBUG
	    ResultSet allEntries = stat.executeQuery(queryString);
	    
	    
	    if (table.equals("bolo")) 
	    	return fillBOLO(allEntries);
	    if (table.equals("bluebook"))
	        return fillBlueBook(allEntries);
	    if (table.equals("RollCall"))
	        return (fillRollCall(allEntries));
	    return null;
	}
//-----------------------------------------------------------------------------
	/**
	 * Populates <code>Bolo</code>s with respective age, race, sex, height, weight, build, eyes, 
	 * hair, reference, case number, weapon, prepared by, ... data
	 * 
	 * @param allEntries - all entries from an SQL query
	 * @return boloList - an <code>ArrayList</code> of <code>Bolo</code>s found from SQL query
	 * @throws SQLException
	 */
	private static ArrayList<Bolo> fillBOLO(ResultSet allEntries) throws SQLException {
		ArrayList<Bolo> boloList = new ArrayList<Bolo>();
		String age, race, sex, height, weight, build, eyes, hair;
		String reference, caseNum, status, weapon;
		String preparedBy, approvedBy;
		String otherDescrip, narrative, photoPath, videoPath;
		long incidentDate=0, prepDate=0;
		
		 Bolo bolo;
		    while (allEntries.next()) {
		    	bolo = new Bolo();
		        
		    	bolo.setBoloID(allEntries.getInt("bolo_id"));
		    	
		        age = allEntries.getString("age");
		        if(age!=null){ bolo.setAge(age); }
				race = allEntries.getString("race");
				if(race!=null){ bolo.setRace(race); }
				sex = allEntries.getString("sex");
				if(sex!=null){ bolo.setSex(sex); }
				height = allEntries.getString("height");
				if(height!=null){ bolo.setHeight(height); }
				weight = allEntries.getString("weight");
				if(weight!=null){ bolo.setWeight(weight); }
				build=allEntries.getString("build");
				if(build!=null){ bolo.setBuild(build); }
				eyes=allEntries.getString("eyes");
				if(eyes!=null){ bolo.setEyes(eyes); }
				hair=allEntries.getString("hair");
				if(hair!=null){ bolo.setHair(hair); }
				reference=allEntries.getString("reference");
				if(reference!=null){ bolo.setReference(reference); }
				caseNum=allEntries.getString("caseNum");
				if(caseNum!=null){ bolo.setCaseNum(caseNum); }
				status=allEntries.getString("status");
				if(status!=null){ bolo.setStatus(status); }
				weapon=allEntries.getString("weapon");
				if(weapon!=null){ bolo.setWeapon(weapon); }
				preparedBy= allEntries.getString("prepedBy");
				if(preparedBy!=null){ bolo.setPreparedBy(preparedBy); }
				approvedBy= allEntries.getString("approvedBy");
				if(approvedBy!=null){ bolo.setApprovedBy(approvedBy); }
				otherDescrip= allEntries.getString("description");
				if(otherDescrip!=null){ bolo.setOtherDescrip(otherDescrip); }
				narrative=allEntries.getString("narrative");
				if(narrative!=null){ bolo.setNarrative(narrative); }
				incidentDate = allEntries.getLong("incidentDate");
				if(incidentDate!=0){ bolo.setincidentDate(incidentDate); }
				prepDate = allEntries.getLong("prepdate");
				if(prepDate!=0){ bolo.setprepDate(prepDate); }
				
				photoPath=allEntries.getString("photoPath");
				if(photoPath!=null){ 
					Path pp = Paths.get(photoPath);
					bolo.setPhotoFilePath(pp);
	//DEBUG:
				} else { 
					System.out.printf("\n photo path is null\n");
				}
				
				videoPath=allEntries.getString("videoPath");
				if(videoPath!=null){ 
					Path vp = Paths.get(videoPath);
					bolo.setVideoFilePath(vp);
	//DEBUG:			
				} else { 
					//System.out.printf("\n video path is null\n");
				}

				boloList.add(bolo);
		    }
		return boloList;
	}
//-----------------------------------------------------------------------------
	/**
	 * Populates <code>BlueBookEntry</code>s with respective name, date of birth, affiliation, 
	 * address, crime description, narrative, date, time, location, case number, weapon data
	 * 
	 * @param allEntries - all entries from an SQL query
	 * @return bluebook - an <code>ArrayList</code> of <code>BlueBookEntry</code>s found from SQL query
	 * @throws SQLException
	 */
	private static ArrayList<BlueBookEntry> fillBlueBook(ResultSet allEntries) throws SQLException {
		ArrayList<BlueBookEntry> bluebook = new ArrayList<BlueBookEntry>();
		String name, caseNum, time, date;
		String narrative, description, location, address, affili, dob;
		String preparedBy, approvedBy, photoPath, videoPath;
		long incidentDate=0, prepDate=0;
		
		 BlueBookEntry entry;
		    while (allEntries.next()) {
		    	entry = new BlueBookEntry();
		    	entry.setBluebkID(allEntries.getInt("bbID"));
		    	
		    	name = allEntries.getString("name");
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
		return bluebook;
	}
//-----------------------------------------------------------------------------
	/**
	 * Fills <code>RollCall</code> with respective name, present, time arrived, 
	 * comment data
	 * 
	 * @param allEntries - all entries from an SQL query
	 * @return rollCallList - a <code>RollCall</code>s found from SQL query
	 * @throws SQLException
	 */
	private static ArrayList<RollCall> fillRollCall(ResultSet allEntries) throws SQLException {
    	ArrayList<RollCall> rollCallList = new ArrayList<RollCall>();
    	RollCall rollCall;
	    while (allEntries.next()) {  	
	    	//hack-fu
	    	rollCallList.add(new RollCall(allEntries.getString("name"),
	    			allEntries.getString("present"), allEntries.getString("timearrived"),
	    			allEntries.getString("comment")));    	    
	    }
	   
	    for (RollCall testrollCall : rollCallList) {   	
        	System.out.println(testrollCall.getName());
        }
    	
    	return rollCallList;
	}
}
