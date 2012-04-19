package utilities;

import java.text.DateFormat;
import java.util.Calendar;
//-----------------------------------------------------------------------------
/**
 * JDOC
 */
public class UDate {
	Calendar calendar;
	DateFormat defaultDateFormatter; 
	DateFormat defaultTimeFormatter;
//-----------------------------------------------------------------------------
							/*Constructors*/
							
	/**
	 * Creates a new UDate object initially set to the current date & time.
	 */
	UDate(){
		calendar = Calendar.getInstance();
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 */
	UDate(int year, int month, int day){
		calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, day);
/*
		 cal.setTime(date);
		 System.out.println("Today is " +cal );
		  }
		  catch (ParseException e)
		  {System.out.println("Exception :"+e);  }  */
		 
	}
//-----------------------------------------------------------------------------
	UDate(int year, int month, int day, int hour, int minute){
		calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, day, hour, minute);
	}
//-----------------------------------------------------------------------------
	UDate(int year, int month, int day, int hour, int minute, int sec){
		calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, day, hour, minute, sec);
	}
//-----------------------------------------------------------------------------	
							/*Compare functions*/
	/**
	 * @return true if this <code>UDate</code> object is before the given 
	 * <code>UDate</code>
	
	public boolean isBefore(UDate otherDate){
		calendar.before(otherDate);
	}
	 */
//-----------------------------------------------------------------------------		
	
//-----------------------------------------------------------------------------	
}

