package utilities;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * The <code>InterfaceSizer<code/> class used for the purpose of making component sizes standard, and based off the size of the screen, rather than a numerical value.
 * This is useful in case users run at different resolutions.
 *
 */
public class InterfaceSizer 
{
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	private static Dimension screenSize = toolkit.getScreenSize();
	private static Dimension tabSize = new Dimension((int)(screenSize.width*0.90), (int)(screenSize.height*0.90));
	private static Dimension recentActivitySize = new Dimension((int)(tabSize.width*0.95), (int)(tabSize.height*0.16));
	
	public static Dimension getScreenSize()
	{
		return screenSize;
	}
	public static Dimension getTabSize()
	{
		return tabSize;
	}
	public static Dimension getRecentActivitySize()
	{
		return recentActivitySize;
	}
}
