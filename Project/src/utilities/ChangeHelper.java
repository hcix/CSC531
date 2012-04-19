package utilities;

import homeTab.Change;

import java.util.Date;

import program.CurrentUser;

public class ChangeHelper 
{
	public static final String ADD_USER = "add user";
	public static final String EDIT_USER = "edit user";
	public static final String DELETE_USER = "delete user";
	public static final String CREATE_BOLO = "create bolo";
	public static final String EDIT_BOLO = "edit bolo";
	public static final String DELETE_BOLO = "delete bolo";
	public static final String CHANGE_ADMIN_SETTINGS = "change admin settings";
	public static final String ADD_BB_ENTRY = "add blue book entry";
	public static final String EDIT_BB_ENTRY = "edit blue book entry";
	public static final String DELETE_BB_ENTRY = "delete blue book entry";
	
	
	public static void makeChange(String type)
	{
		String usr = CurrentUser.getCurrentUser().getFirstname() + " " + CurrentUser.getCurrentUser().getLastname();
		Date d = new Date();
		Change c = new Change(usr, d.getTime(), type);
		try {
			DatabaseHelper.postChangeToDB(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}