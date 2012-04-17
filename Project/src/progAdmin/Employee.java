package progAdmin;
//-----------------------------------------------------------------------------
/**
 * The <code>Employee</code> class represents a UMPD employee used for 
 * login/permissions purposes
 */
public class Employee {
	private String cnumber; 
	private String firstname;
	private String lastname;
	private String email;
	private String permissions;
	private String caneID;
	private int level;
//-----------------------------------------------------------------------------
	/**
	 * 'Getter' method for the cnumber variable
	 * @return cnumber - employee's C number, as assigned by the University of Miami
	 */
	public String getCnumber() {
		return cnumber;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for cnumber variable
	 * @param cnumber - employee's C number, as assigned by the University of Miami
	 */
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for caneID variable
	 * @return caneID - employee's UM username
	 */
	public String getCaneID() {
		return caneID;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for caneID variable
	 * @param caneID - employee's UM username
	 */
	public void setCaneID(String caneID) {
		this.caneID = caneID;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for employee's first name
	 * @return - employee's first name
	 */
	public String getFirstname() {
		return firstname;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for employee's first name
	 * @param firstname - employee's first name
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for employee's last name
	 * @return - employee's last name
	 */
	public String getLastname() {
		return lastname;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for employee's last name
	 * @param lastname - employee's last name
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for employee's email
	 * 
	 * @return - employee's email
	 */
	public String getEmail() {
		return email;
	}
//-----------------------------------------------------------------------------
	/**
	 * Setter method for employee's email
	 * 
	 * @param email - employee's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for employee's permissions
	 * 
	 * @return - employee's permissions
	 */
	public String getPermissions() {
		return permissions;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the level of access a particular employee is allowed based on their
	 * permissions
	 * 
	 * @param permissions - employee's permissions
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
		
		if(permissions.equals(PersonnelManager.PERMIS_OFFICER)){
			level = PersonnelManager.PERMIS_LEVEL_OFFICER; 
		} else if(permissions.equals(PersonnelManager.PERMIS_COMMAND)){
			level = PersonnelManager.PERMIS_LEVEL_COMMAND;
		} else if(permissions.equals(PersonnelManager.PERMIS_SUPERVISR)){
			level = PersonnelManager.PERMIS_LEVEL_SUPERVISR; 
		} else {
			level = PersonnelManager.PERMIS_LEVEL_NONE; 
		}  

	}
//-----------------------------------------------------------------------------
	/**
	 * Getter method for employee's level
	 * 
	 * @return level - their access level
	 */
	public int getLevel(){
		return level;
	}
//-----------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Employee [cnumber=" + cnumber + ", firstname=" + firstname + ", " +
				"lastname=" + lastname + ", caneID=" + caneID +  ", email=" + email +  
				", permissions=" + permissions + "]";
	}
//-----------------------------------------------------------------------------
}

