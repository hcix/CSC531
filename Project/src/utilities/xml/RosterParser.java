package utilities.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import progAdmin.Employee;

public class RosterParser {
	static final String CNUMBER = "cnumber";
	static final String EMPLOYEE = "employee";
	static final String LASTNAME = "lastname";
	static final String EMAIL = "email";
	static final String PERMISSIONS = "permissions";
	static final String FIRSTNAME = "firstname";
//-----------------------------------------------------------------------------	 
	@SuppressWarnings({ "unchecked", "null" })
	public List<Employee> readRoster(String configFile) {
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
//-----------------------------------------------------------------------------	 
}