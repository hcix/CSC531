package homeTab;

import java.util.Date;
//-----------------------------------------------------------------------------
/**
 * The Change class represents a change made by the current user.  Inside a Change instance, there is the name of
 * the current user (the one who made the change), the current date, and the type of change that they made.
 * @author Brendan
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
	public Change(String user, long time, String type)
	{
		this.user = user;
		this.time = time;
		this.type = type;
	}
//-----------------------------------------------------------------------------
	public String getUser()
	{
		return user;
	}
//-----------------------------------------------------------------------------
	public long getDate()
	{
		return time;
	}
//-----------------------------------------------------------------------------
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
