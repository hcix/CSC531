package shiftCdrTab.reports;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import utilities.FileHelper;

//-----------------------------------------------------------------------------

/**
 * Small class used to represent a <code>ShiftCdfReport</code> file in the list 
 * of reports in the <code>ReportsPanel</code> within the <code>ShiftCdrTab</code>.
 */
public class ReportFile {
	File file;
	String filename;
	Date dateCreated = new Date();
	boolean currentlyDisplayed=false;
//-----------------------------------------------------------------------------
	public ReportFile(String filename){
		this.filename = filename;
//DEBUG System.out.println("filename = " + filename);
		
		this.file = new File(filename);
		this.dateCreated = getDateFromFile(file);			
	}
//-----------------------------------------------------------------------------
	/**
	 * @return filename in system of this <code>ReportFile</code>
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
	 * @return dateCreated - 
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dateCreated - the dateCreated value to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return currentlyDisplayed - 
	 */
	public boolean isCurrentlyDisplayed() {
		return currentlyDisplayed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param currentlyDisplayed - the currentlyDisplayed value to set
	 */
	public void setCurrentlyDisplayed(boolean currentlyDisplayed) {
		this.currentlyDisplayed = currentlyDisplayed;
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 * ...
	 * 
	 * All Shift Cdr Summary Report files are saved in the directory
	 * <Program Directory>\Documents\ShiftReports .
	 * This is what a typical report's filename looks like:
	 * 			ShiftCDRSumReport04_14_2012_23_20.pdf
	 * This method creates a date object from the info in the filename
	 */
	private Date getDateFromFile(File file){	
		int month=0, day=0, year=0, hour=0, minute=0;
		
		//get rid of the .pdf extension
		String noExtension = FileHelper.getNameWithoutExtension(file.getName());

//DEBUG System.out.println("ReportFile: getDateFromFilename: noExtension = " + noExtension);
		
		//gets rid of the ShiftCDRSumReport part
		String dateString = noExtension.substring(17);
		
//DEBUG System.out.println("ReportFile: getDateFromFilename: dateString = " + dateString);
		
		//grab the month, day, year, hour, and minute info from dateString
		String[] results = dateString.split("_");
		if(results.length>=5){
			month=Integer.valueOf(results[0]);
			day=Integer.valueOf(results[1]);
			year=Integer.valueOf(results[2]);
			hour=Integer.valueOf(results[3]);
			minute=Integer.valueOf(results[4]);
		} else { return null; }//error in filename; ignore this file

	     //use the month, day, year, hour, and minute info to set date & time
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute);
		
		Date created = cal.getTime();

	    //return the resulting date object
		return created;
		//return (new Date());
	}
//-----------------------------------------------------------------------------
}