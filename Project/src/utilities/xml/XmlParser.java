package utilities.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import progAdmin.Employee;
import progAdmin.itemsToReview.ItemToReview;
import utilities.FileHelper;

public class XmlParser {
//-----------------------------------------------------------------------------	
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
	
	static final String ITEM = "item";
	static final String TITLE = "title";
	static final String DETAILS = "details";

	static String propertiesFile;//so they can be final vars
//-----------------------------------------------------------------------------	
	//static ArrayList<Employee> roster;
	//static ProgramProperties programProperties;
//-----------------------------------------------------------------------------	 
	/**
	 * Creates a <code>List</code> of <code>Employee</code> objects and populates
	 * the list with <code>Employee</code> objects created from the information 
	 * stored in the roster.xml file.
	 * 
	 * @return a <code>List</code> of <code>Employee</code> objects containing
	 * all employees currently represented in the roster.xml file
	 */
	public static ArrayList<Employee> getRoster() {
		String rosterFileName = FileHelper.getRosterFilePathName();
		ArrayList<Employee> roster = new ArrayList<Employee>();
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(rosterFileName);
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
							if(event.isCharacters())
								employee.setCnumber(event.asCharacters().getData());
							continue;
							
						}
					}
					
					if (event.asStartElement().getName().getLocalPart()
							.equals(LASTNAME)) {
						event = eventReader.nextEvent();
						if(event.isCharacters())
							employee.setLastname(event.asCharacters().getData());
						continue;
						
					}
					
					if (event.asStartElement().getName().getLocalPart()
							.equals(CANEID)) {
						event = eventReader.nextEvent();
						if(event.isCharacters())
							employee.setCaneID(event.asCharacters().getData());
						continue;
						
					}
					
					if (event.asStartElement().getName().getLocalPart()
							.equals(EMAIL)) {
						event = eventReader.nextEvent();
						if(event.isCharacters()){
							employee.setEmail(event.asCharacters().getData());
						}
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
						if(event.isCharacters())
							employee.setFirstname(event.asCharacters().getData());
						continue;
						
					}
				}
				// If we reach the end of an item element we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (EMPLOYEE)) {
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
	public static void saveRoster(ArrayList<Employee> roster) throws Exception {
		String rosterFileName = FileHelper.getRosterFilePathName();
		//Create a XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		//Create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.
				createXMLEventWriter(new FileOutputStream(rosterFileName));
		//Create a EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		
		//Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(end);

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
	/**
	 * Adds the given <code>ItemToReview</code> object to the itemsToReview.xml
	 * file.
	 * 
	 * @param newItem - the <code>ItemToReview</code> to add
	 */
	public static void addItemToReview(ItemToReview newItem){
		//load the existing list
		ArrayList<ItemToReview> itemsList = loadItemsToReviewList();
		
		//add the specified item to the list
		itemsList.add(newItem);

		//re-save the list
		try { 
			saveItemsToReviewList(itemsList);
		} catch (Exception e) { e.printStackTrace(); }
		
	}
//-----------------------------------------------------------------------------	
	/**
	 * Saves the given <code>ArrayList</code> of <code>ItemToReview</code> objects
	 * into the itemsToReview.xml file. Note that this method rewrites the
	 * itemsToReview.xml file with the item contents of the given list.
	 * 
	 * @param itemsList - the ArrayList of <code>ItemToReview</code> objects
	 * to save to the itemsToReview.xml file
	 */
	public static void saveItemsToReviewList(ArrayList<ItemToReview> itemsList)
	throws Exception {
		String itemsToReviewFile = FileHelper.getItemsToReviewFile();
		//Create a XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		//Create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.
				createXMLEventWriter(new FileOutputStream(itemsToReviewFile));
		//Create a EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		
		//Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(end);
		
		// Create config open tag
		StartElement configStartElement = eventFactory.createStartElement("",
				"", "UMPD.itemsList");
		eventWriter.add(configStartElement);
		eventWriter.add(end);
		
		// Write the different nodes
		for (ItemToReview item : itemsList) {
			try{
				addItemNode(eventWriter, item);
			} catch(Exception e){ e.printStackTrace(); }	
		}
		
		eventWriter.add(eventFactory.createEndElement("", "", "UMPD.itemsList"));
		
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
		
	}	
//-----------------------------------------------------------------------------
	/**
	 * Creates an <code>ArrayList</code> of <code>ItemToReview</code> objects
	 * and populates the list with <code>ItemToReview</code> objects created
	 * from the information stored in the itemsToReview.xml file.
	 * 
	 * @return an <code>ArrayList</code> of <code>ItemToReview</code> objects
	 * containing all of the current items to be reviewed
	 */
	public static ArrayList<ItemToReview> loadItemsToReviewList(){
		ArrayList<ItemToReview> itemsList = new ArrayList<ItemToReview>();
		String itemsToReviewFile = FileHelper.getItemsToReviewFile();
		
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(itemsToReviewFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		
			ItemToReview item=null;
			
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have a item element we create a new item
					if (startElement.getName().getLocalPart() == (ITEM)) {
						item = new ItemToReview();
					}
	
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(TITLE)) {
							event = eventReader.nextEvent();
							if(event.isCharacters())
								item.setTitle(event.asCharacters().getData());
							continue;
							
						}
					}
					
					if (event.asStartElement().getName().getLocalPart()
							.equals(DETAILS)) {
						event = eventReader.nextEvent();
						if(event.isCharacters())
							item.setDetails(event.asCharacters().getData());
						continue;
						
					}
					
				}
				// If we reach the end of an item element we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (ITEM)) {
						itemsList.add(item);
					}
				}
	
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	
		return itemsList;
	}
//-----------------------------------------------------------------------------	
	private static void createNode(XMLEventWriter eventWriter, String name,
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
	private static void createInnerNode(XMLEventWriter eventWriter, String name,
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
	private static void addEmployeeNode(XMLEventWriter eventWriter, Employee employee)
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
	private static void addItemNode(XMLEventWriter eventWriter, ItemToReview item)
	throws Exception {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		
		// Create config open tag
		StartElement configStartElement = eventFactory.createStartElement("","", ITEM);
		eventWriter.add(tab);
		eventWriter.add(configStartElement);
		eventWriter.add(end);
				
		createInnerNode(eventWriter, TITLE, item.getTitle());
		createInnerNode(eventWriter, DETAILS, item.getDetails());
			
		eventWriter.add(tab);
		eventWriter.add(eventFactory.createEndElement("", "", ITEM));
		eventWriter.add(end);
		
	}
//-----------------------------------------------------------------------------	
}