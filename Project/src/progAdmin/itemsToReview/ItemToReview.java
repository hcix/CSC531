package progAdmin.itemsToReview;

import java.util.Date;
//-----------------------------------------------------------------------------
/**
 * 
 */
public class ItemToReview {
	/** the title of this ItemToReview object **/
	String title;
	/** the details associated with this ItemToReview object **/
	String details;
	/** whether or not this ItemToReview has been **/
	boolean reviewed;
	/** the name of the person who created this ItemToReview object **/
	String creator;
	/** the date this ItemToReview object was created **/
	Date dateCreated;
	/** the date this ItemToReview object was reviewed **/
	Date dateReviewed;
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
	 * @return whether or not this item has been marked as read
	 */
	public boolean isReviewed() {
		return reviewed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param reviewed - the reviewed value to set
	 */
	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return creator - 
	 */
	public String getCreator() {
		return creator;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param creator - the creator value to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return dateCreated - 
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dateCreated - the dateCreated value to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return dateReviewed - 
	 */
	public Date getDateReviewed() {
		return dateReviewed;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param dateReviewed - the dateReviewed value to set
	 */
	public void setDateReviewed(Date dateReviewed) {
		this.dateReviewed = dateReviewed;
	}
//-----------------------------------------------------------------------------
	@Override 
	public String toString() {
	    StringBuilder result = new StringBuilder();//slightly faster

	    result.append("{\n");
	    result.append(" Title: " + this.title + "\n");
	    result.append(" Details: " + this.details + "\n");
	    result.append(" }");

	    return (result.toString());
	  }
//-----------------------------------------------------------------------------
}