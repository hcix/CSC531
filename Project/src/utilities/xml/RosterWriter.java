package utilities.xml;

import java.io.FileOutputStream;
import java.util.List;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import progAdmin.Employee;
//-----------------------------------------------------------------------------	 
public class RosterWriter {
	private String rosterFile;
	List<Employee> roster;
//-----------------------------------------------------------------------------	 	
	public void setFile(String rosterFile) {
		this.rosterFile = rosterFile;
	}
//-----------------------------------------------------------------------------	 
	public void saveConfig() throws Exception {
		//Create a XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		//Create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.
				createXMLEventWriter(new FileOutputStream(rosterFile));
		//Create a EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		
		//Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);

		// Create config open tag
		StartElement configStartElement = eventFactory.createStartElement("",
				"", "roster");
		eventWriter.add(configStartElement);
		eventWriter.add(end);

		// Write the different nodes
		for (Employee employee : roster) {
			try{
				addEmployeeNode(eventWriter, employee);
			} catch(Exception e){ e.printStackTrace(); }	
		}
		
		eventWriter.add(eventFactory.createEndElement("", "", "roster"));
		
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}
//-----------------------------------------------------------------------------	 
	private void createNode(XMLEventWriter eventWriter, String name,
			String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		// Create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		
		// Create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		
		// Create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}
//-----------------------------------------------------------------------------	 
	private void createInnerNode(XMLEventWriter eventWriter, String name,
			String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent twoTabs = eventFactory.createDTD("\t\t");
		// Create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(twoTabs);
		eventWriter.add(sElement);
		
		// Create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		
		// Create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}
//-----------------------------------------------------------------------------	
	private void addEmployeeNode(XMLEventWriter eventWriter, Employee employee)
	throws Exception {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		
		// Create config open tag
		StartElement configStartElement = eventFactory.createStartElement("","", "employee");
		eventWriter.add(tab);
		eventWriter.add(configStartElement);
		eventWriter.add(end);
				
		createInnerNode(eventWriter, "cnumber", employee.getCnumber());
		createInnerNode(eventWriter, "firstname", employee.getFirstname());
		createInnerNode(eventWriter, "lastname", employee.getLastname());
		createInnerNode(eventWriter, "caneid", employee.getCaneID());
		createInnerNode(eventWriter, "email", employee.getEmail());
		createInnerNode(eventWriter, "permissions", employee.getPermissions());
			
		eventWriter.add(tab);
		eventWriter.add(eventFactory.createEndElement("", "", "employee"));
		eventWriter.add(end);
		
	}
//-----------------------------------------------------------------------------	
	public void setList(List<Employee> roster){
		this.roster = roster;
	}
//-----------------------------------------------------------------------------	 
}
