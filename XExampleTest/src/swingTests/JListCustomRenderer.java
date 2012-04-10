package swingTests;
import java.awt.*;
import javax.swing.*;

/** Simple JList example illustrating the use of a custom
 *  cell renderer (JavaLocationRenderer).
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JListCustomRenderer extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public static void main(String[] args) {
    new JListCustomRenderer();
  }

  public JListCustomRenderer() {
    super("JList with a Custom Cell Renderer");
    WindowUtilities.setNativeLookAndFeel();
    addWindowListener(new ExitListener());
    Container content = getContentPane();

    JavaLocationCollection collection =
      new JavaLocationCollection();
    JavaLocationListModel listModel =
      new JavaLocationListModel(collection);
    JList sampleJList = new JList(listModel);
    sampleJList.setCellRenderer(new JavaLocationRenderer());
    Font displayFont = new Font("Serif", Font.BOLD, 18);
    sampleJList.setFont(displayFont);
    content.add(sampleJList);

    pack();
    setVisible(true);
  }
}
