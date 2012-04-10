package swingTests;
import javax.swing.*;
import javax.swing.event.*;

/** A simple illustration of writing your own ListModel.
 *  Note that if you wanted the user to be able to add and
 *  remove data elements at runtime, you should start with
 *  AbstractListModel and handle the event reporting part.
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JavaLocationListModel implements ListModel {
  private JavaLocationCollection collection;
  
  public JavaLocationListModel(JavaLocationCollection collection) {
    this.collection = collection;
  }

  public Object getElementAt(int index) {
    return(collection.getLocations()[index]);
  }

  public int getSize() {
    return(collection.getLocations().length);
  }

  public void addListDataListener(ListDataListener l) {}

  public void removeListDataListener(ListDataListener l) {}
}
