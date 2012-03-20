/**
 * Class to hold information about the current user of the system.
 */
package program;

import utilities.xml.Employee;

public class CurrentUser extends Employee{
	private static Employee user;
//-----------------------------------------------------------------------------
	public static void setCurrentUser(Employee employee){
		user = employee;
	}
//-----------------------------------------------------------------------------
	public static Employee getCurrentUser(){
		return user;
	}
//-----------------------------------------------------------------------------	
}
