package utilities.xml;

import java.util.List;
import progAdmin.Employee;
import progAdmin.PersonnelManager;

public class TestWrite {
//-----------------------------------------------------------------------------	 
	public static void main(String[] args) {
		//RosterParser read = new RosterParser();
		List<Employee> readConfig = PersonnelManager.getRoster("src/utilities/xml/roster.xml");
		
		for (Employee employee : readConfig) {
			System.out.println(employee);
		}
		
		RosterWriter configFile = new RosterWriter();
		configFile.setFile("src/utilities/xml/test_out.xml");
		
		try {
			configFile.setList(readConfig);
			configFile.saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//-----------------------------------------------------------------------------	 
}