/**
 * A helper class designed to make accessing the crime table within the
 * database in program easier. 
 * This classes methods should be used exclusively to interact with the 
 * database to ensure thread safety. Only one connection can write to the
 * database at a time, thus once a connection is opened the entire database 
 * locks until it is closed. 
 *
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseHelper {
	
//-----------------------------------------------------------------------------
	/**
	 * <b> addCrime </b>
	 * <pre>public void addCrime(int caseNum, String location, String time, String date)</pre> 
	 * <blockquote> 
	 * Adds a new crime to the crime table in the database. 
	 * This method adds the most basic data that every crime entry must have.
	 * </blockquote>
	 * @param caseNum - the case number associated with the crime
	 * @param location - the location of the crime
	 * @param time - the time of the crime occurance
	 * @param date - the date of the crime occurance 
	 * @throws Exception
	 */
	public void addCrime(int caseNum, String location, String time, String date) throws Exception{
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	
	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "INSERT into crimes(caseNum, location, time, date) VALUES (?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    prep.setInt(1, caseNum);
	    prep.setString(2, location);
	    prep.setString(3, time);
	    prep.setString(4, date);
	    prep.addBatch();
	
	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------	
	/**
	 * <b> addCrime </b>
	 * <pre>public void addCrime(int caseNum, String location, int offCode, String time, String date)</pre> 
	 * <blockquote> 
	 * Adds a new crime to the crime table in the database. 
	 * This method adds the most basic data that every crime entry must have.
	 * </blockquote>
	 * @param caseNum - the case number associated with the crime
	 * @param location - the location of the crime
	 * @param offCode - the offense code of the incident
	 * @param time - the time of the crime occurance
	 * @param date - the date of the crime occurance 
	 * @throws Exception
	 */
	public void addCrime(int caseNum, String location, int offCode, String time, String date) throws Exception{
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	
	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "INSERT into crimes(caseNum, location, offCode, time, date) VALUES (?, ?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    prep.setInt(1, caseNum);
	    prep.setString(2, location);
	    prep.setInt(3, offCode);
	    prep.setString(4, time);
	    prep.setString(5, date);
	    prep.addBatch();
	
	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------	
	/**
	 * <b> addCrime </b>
	 * <pre>public void addCrime(int caseNum, String location, int offCode, String incident, String time, String date)</pre> 
	 * <blockquote> 
	 * Adds a new crime to the crime table in the database. 
	 * This method adds the most basic data that every crime entry must have.
	 * </blockquote>
	 * @param caseNum - the case number associated with the crime
	 * @param location - the location of the crime
	 * @param offCode - the offense code of the crime
	 * @param incident - the incident associated with the crime
	 * @param time - the time of the crime occurance
	 * @param date - the date of the crime occurance 
	 * @throws Exception
	 */
	public void addCrime(int caseNum, String location, int offCode, String incident, String time, String date) throws Exception{
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	
	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "INSERT into crimes(caseNum, location, offCode, incident, time, date) VALUES (?, ?, ?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    prep.setInt(1, caseNum);
	    prep.setString(2, location);
	    prep.setInt(3, offCode);
	    prep.setString(4, incident);
	    prep.setString(5, time);
	    prep.setString(6, date);
	    prep.addBatch();
	
	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------	
	/**
	 * <b> addCrime </b>
	 * <pre>public void addCrime(int caseNum, String location, int offCode, String incident, String time, String date, String remarks)</pre> 
	 * <blockquote> 
	 * Adds a new crime to the crime table in the database. 
	 * This method adds the most basic data that every crime entry must have.
	 * </blockquote>
	 * @param caseNum - the case number associated with the crime
	 * @param location - the location of the crime
	 * @param offCode - the offense code of the crime
	 * @param incident - the incident associated with the crime
	 * @param time - the time of the crime occurance
	 * @param date - the date of the crime occurance 
	 * @param remarks - remarks associated with the crime
	 * @throws Exception
	 */
	public void addCrime(int caseNum, String location, int offCode, String incident, 
			String time, String date, String remarks) throws Exception{
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	
	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "INSERT into crimes(caseNum, location, offCode, incident, time, date, remarks) VALUES (?, ?, ?, ?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    prep.setInt(1, caseNum);
	    prep.setString(2, location);
	    prep.setInt(3, offCode);
	    prep.setString(4, incident);
	    prep.setString(5, time);
	    prep.setString(6, date);
	    prep.setString(7, remarks);
	    prep.addBatch();
	
	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------	
	/**
	 * <b> addCrime </b>
	 * <pre>public void addCrime(int caseNum, String location, int offCode, String incident, String time, String date, String remarks, String status)</pre> 
	 * <blockquote> 
	 * Adds a new crime to the crime table in the database. 
	 * This method adds the most basic data that every crime entry must have.
	 * </blockquote>
	 * @param caseNum - the case number associated with the crime
	 * @param location - the location of the crime
	 * @param offCode - the offense code of the crime
	 * @param incident - the incident associated with the crime
	 * @param time - the time of the crime occurance
	 * @param date - the date of the crime occurance 
	 * @param remarks - remarks associated with the crime
	 * @param status - the crime's status
	 * @throws Exception
	 */
	public void addCrime(int caseNum, String location, int offCode, String incident, 
			String time, String date, String remarks, String status) throws Exception{
		//Create the connection to the database
		Class.forName("org.sqlite.JDBC");
	    Connection conn = DriverManager.getConnection("jdbc:sqlite:umpd.db");
	
	    //Create a prepared statement to add the crime data
	    PreparedStatement prep = conn.prepareStatement(
	      "INSERT into crimes(caseNum, location, offCode, incident, time, date, remarks, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
	
	    //Add the data to the prepared statement
	    prep.setInt(1, caseNum);
	    prep.setString(2, location);
	    prep.setInt(3, offCode);
	    prep.setString(4, incident);
	    prep.setString(5, time);
	    prep.setString(6, date);
	    prep.setString(7, remarks);
	    prep.setString(8, status);
	    prep.addBatch();
	
	    //Create new row in the table for the data
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
	    
	    //Close the connection
	    conn.close();
	}
//-----------------------------------------------------------------------------		
}
