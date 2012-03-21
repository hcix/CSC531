package progAdmin;

public class Employee {
	private String cnumber; 
	private String firstname;
	private String lastname;
	private String email;
	private String permissions;
	private String caneID;
	private int level;
//-----------------------------------------------------------------------------
	public String getCnumber() {
		return cnumber;
	}
//-----------------------------------------------------------------------------
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
//-----------------------------------------------------------------------------
	public String getCaneID() {
		return caneID;
	}
//-----------------------------------------------------------------------------
	public void setCaneID(String caneID) {
		this.caneID = caneID;
	}
//-----------------------------------------------------------------------------
	public String getFirstname() {
		return firstname;
	}
//-----------------------------------------------------------------------------
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
//-----------------------------------------------------------------------------
	public String getLastname() {
		return lastname;
	}
//-----------------------------------------------------------------------------
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
//-----------------------------------------------------------------------------
	public String getEmail() {
		return email;
	}
//-----------------------------------------------------------------------------
	public void setEmail(String email) {
		this.email = email;
	}
//-----------------------------------------------------------------------------
	public String getPermissions() {
		return permissions;
	}
//-----------------------------------------------------------------------------
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

