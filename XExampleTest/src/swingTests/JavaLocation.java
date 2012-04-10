package swingTests;

/** Simple data structure with three properties: country,
 *  comment, and flagFile. All are strings, and they are
 *  intended to represent a country that has a city or
 *  province named "Java", a comment about a more
 *  specific location within the country, and a path
 *  specifying an image file containing the country's flag.
 *  Used in examples illustrating custom models and cell
 *  renderers for JLists.
 *
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JavaLocation {
  private String country, comment, flagFile;

  public JavaLocation(String country, String comment,
		      String flagFile) {
    setCountry(country);
    setComment(comment);
    setFlagFile(flagFile);
  }

  /** String representation used in printouts and in JLists */
  
  public String toString() {
    return("Java, " + getCountry() + " (" + getComment() + ").");
  }
  
  /** Return country containing city or province named "Java". */
  
  public String getCountry() {
    return(country);
  }

  /** Specify country containing city or province named "Java". */
  
  public void setCountry(String country) {
    this.country = country;
  }

  /** Return comment about city or province named "Java".
   *  Usually of the form "near such and such a city".
   */
  
  public String getComment() {
    return(comment);
  }

  /** Specify comment about city or province named "Java". */

  public void setComment(String comment) {
    this.comment = comment;
  }
  
  /** Return path to image file of country flag. */
  
  public String getFlagFile() {
    return(flagFile);
  }

  /** Specify path to image file of country flag. */
  
  public void setFlagFile(String flagFile) {
    this.flagFile = flagFile;
  }
}
