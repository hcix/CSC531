package reviewItems;

import utilities.xml.XmlParser;
//-----------------------------------------------------------------------------
/**
 * 
 */
public class ItemToReview {
	String title;
	String details;
//-----------------------------------------------------------------------------
	public ItemToReview(){
		
	}
//-----------------------------------------------------------------------------
	public ItemToReview(String title, String details){
		this.title=title;
		this.details=details;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param title - the title value to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return details
	 */
	public String getDetails() {
		return details;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param details - the details value to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
//-----------------------------------------------------------------------------
	/**
	 * <b> addToXML </b>
	 * <pre>public void addToXML() throws Exception</pre> 
	 * <blockquote> 
	 * Adds this ItemToReview object to the itemsToReview.xml
	 * </blockquote>
	 * @throws Exception
	 */
	public void addToXML() throws Exception{
		XmlParser.addItemToReview(this);
	}
//-----------------------------------------------------------------------------
	@Override 
	public String toString() {
	    StringBuilder result = new StringBuilder();//slightly faster

	    result.append(this.getClass().getName() + " Object {\n");
	    result.append(" Title: " + this.title + "\n");
	    result.append(" Details: " + this.details + "\n");
	    result.append("}");

	    return (result.toString());
	  }

//-----------------------------------------------------------------------------
}