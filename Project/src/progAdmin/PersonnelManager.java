
package progAdmin;

import java.util.List;
import utilities.xml.XmlParser;
//-----------------------------------------------------------------------------
/**
 * Manages the personnel information associated with the system.
 */
public class PersonnelManager {
	public static final String PERMIS_NONE="none";
	public static final String PERMIS_OFFICER="officer";
	public static final String PERMIS_COMMAND="command";
	public static final String PERMIS_SUPERVISR="supervisor";
	public static final int PERMIS_LEVEL_NONE=0;
	public static final int PERMIS_LEVEL_OFFICER=1;
	public static final int PERMIS_LEVEL_COMMAND=2;
	public static final int PERMIS_LEVEL_SUPERVISR=3;

	static final String CNUMBER = "cnumber";
	static final String EMPLOYEE = "employee";
	static final String LASTNAME = "lastname";
	static final String CANEID = "caneid";
	static final String EMAIL = "email";
	static final String PERMISSIONS = "permissions";
	static final String FIRSTNAME = "firstname";
//-----------------------------------------------------------------------------
	/**
	 * Find's an employee based on their caneID
	 * 
	 * @param caneID - caneID of an employee
	 * @return - If the caneID exists, the employee object is returned.
	 *           If it does not exist, NULL is returned
	 */
	public static Employee getEmployeeByCaneID(String caneID){
		//List<Employee> roster = getRoster("src/utilities/xml/roster.xml");
		List<Employee> roster = XmlParser.getRoster();
		
		//Search through the list of employees for a matching cnum
		for(Employee employee : roster){
			if(employee.getCaneID().equals(caneID)){
				return employee;
			}
		}

		//employee with matching cnum not found - return null
		return null;

	}
//-----------------------------------------------------------------------------
	/**
	 * Gets list of employees in the current roster
	 * 
	 * @param - configFile
	 * @return roster - current employee roster
	 */
/*	public static List<Employee> getRoster(String configFile) {
		List<Employee> roster = new ArrayList<Employee>();
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// Read the XML document
			Employee employee = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have a item element we create a new item
					if (startElement.getName().getLocalPart() == (EMPLOYEE)) {
						employee = new Employee();
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(CNUMBER)) {
							event = eventReader.nextEvent();
							employee.setCnumber(event.asCharacters().getData());
							continue;
						}
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(LASTNAME)) {
						event = eventReader.nextEvent();
						employee.setLastname(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(CANEID)) {
						event = eventReader.nextEvent();
						employee.setCaneID(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(EMAIL)) {
						event = eventReader.nextEvent();
						employee.setEmail(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(PERMISSIONS)) {
						event = eventReader.nextEvent();
						employee.setPermissions(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart()
							.equals(FIRSTNAME)) {
						event = eventReader.nextEvent();
						employee.setFirstname(event.asCharacters().getData());
						continue;
					}
				}
				// If we reach the end of an item element we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (FIRSTNAME)) {
						roster.add(employee);
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return roster;
	}
*///-----------------------------------------------------------------------------	 
}
