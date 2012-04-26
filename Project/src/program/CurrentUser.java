
package program;

import progAdmin.Employee;
/**
 * the <code>CurrentUser<code/> class to hold information about the current 
 * user of the system.
 */
public class CurrentUser extends Employee{
	private static Employee user;
//-----------------------------------------------------------------------------
	/**
	 * Setter method for the user variable.
	 * Indicates which employee is currently using the program.
	 *  
	 * @param employee
	 */
	public static void setCurrentUser(Employee employee){
		user = employee;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for the user variable.
	 * 
	 * @return
	 */
	public static Employee getCurrentUser(){
		return user;
	}
//-----------------------------------------------------------------------------	
}
