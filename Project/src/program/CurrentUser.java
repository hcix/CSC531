/**
 * Class to hold information about the current user of the system.
 */
package program;

import progAdmin.Employee;

public class CurrentUser extends Employee{
	private static Employee user;
//-----------------------------------------------------------------------------
	/**
	 * Setter method for the user variable
	 * <p>
	 * Indicates which employee is currently using the program
	 *  
	 * @param employee
	 */
	public static void setCurrentUser(Employee employee){
		user = employee;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for the user variable
	 * 
	 * @return
	 */
	public static Employee getCurrentUser(){
		return user;
	}
//-----------------------------------------------------------------------------	
}
