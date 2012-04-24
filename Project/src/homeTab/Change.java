package homeTab;

import java.util.Date;
//-----------------------------------------------------------------------------
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
