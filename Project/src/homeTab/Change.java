package homeTab;

import java.util.Date;
//-----------------------------------------------------------------------------
/**
 * The Change class represents a change made by the current user.  Inside a Change instance, there is the name of
 * the current user (the one who made the change), the current date, and the type of change that they made.
 *
 */
public class Change 
{
//-----------------------------------------------------------------------------
	private String user;
	private long time;
	private String type;
	private String info;
//-----------------------------------------------------------------------------
	/**
	 * Change the user, time, and type
	 * 
	 * @param user
	 * @param time
	 * @param type
	 */
	public Change(String user, long time, String type)
	{
		this.user = user;
		this.time = time;
		this.type = type;
	}
//-----------------------------------------------------------------------------
	/**
	 * gets the user
	 * @return user
	 */
	public String getUser()
	{
		return user;
	}
//-----------------------------------------------------------------------------
	/**
	 * gets the date
	 * @return time
	 */
	public long getDate()
	{
		return time;
	}
//-----------------------------------------------------------------------------
	/**
	 * gets the type
	 * 
	 * @return type
	 */
	public String getType()
	{
		return type;
	}
//-----------------------------------------------------------------------------
	public String getInfo()
	{
		long edt = new Date(time).getTime() - 14400000;
		Date d = new Date(edt);
		info = this.user + " made change [" + (this.type).toUpperCase() + "] at " 
				+ d;
		
		return info;	
	}
//-----------------------------------------------------------------------------
}
