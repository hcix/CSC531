package utilities;

import homeTab.Change;

import java.util.Date;

import program.CurrentUser;

/**
 * This class is used to create new Changes.  This class makes a new Change based off the current user, the current date, and the type of change made, as specified by the appropriate string value.
 * @author Brendan
 *
 */
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
	public static final String ADD_VIDEO = "add video";
	public static final String ADD_ITEM_TO_REVIEW = "add item to review";
	public static final String EDIT_ITEM_TO_REVIEW = "edit item to review";
	public static final String DELETE_ITEM_TO_REVIEW = "delete item to review";
	
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
