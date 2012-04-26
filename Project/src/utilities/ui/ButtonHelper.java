package utilities.ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

//-----------------------------------------------------------------------------
/**
 * <code>ButtonHelper</code> is a useful class for creating buttons. It makes 
 * changing the image for a button simple, the path only needs to be changed once 
 * here. <code>ButtonHelper</code> also ensures that button styles are consistant
 * throughout the application.
 */
public class ButtonHelper {
	/*Button Sizes*/
	public static final int LARGE = 48;
	public static final int MEDIUM = 32;
	public static final int SMALL = 16;
	//public static final int XTRA_SMALL = 8;
	
	/*Filenames of buttons relative to userinterface package*/
	private static final String SAVE = "icons/save_";
	private static final String DELETE = "icons/delete_";
	private static final String CANCEL = "icons/cancel_";
	private static final String EDIT = "icons/edit_";
	private static final String EMAIL = "icons/email";
	private static final String PRINT = "icons/print_";
	private static final String CONFIRM = "icons/check_";
	private static final String PREVIEW = "icons/preview_";
		
	//all icons are .png's
	private static final String PNG = ".png";
	
	/*Default Text displayed on buttons*/
	private static final String SAVE_TXT = "Save ";
	private static final String CANCEL_TXT = "Cancel ";
	private static final String EDIT_TXT = "Edit ";
	private static final String DELETE_TXT = "Delete ";
	private static final String PREVIEW_TXT = "Preview ";
//-----------------------------------------------------------------------------	
	public static JButton createSaveButton(int size, String txt){
		size=checkSize(size);
		
		ImageIcon buttonIcon = ImageHandler.getProgramImgIcon((SAVE+size+PNG));
		JButton imageButton= new JButton((SAVE_TXT+txt), buttonIcon);
		imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return imageButton;
	}
//-----------------------------------------------------------------------------	
	public static JButton createCancelButton(int size){
		size=checkSize(size);
		
		ImageIcon buttonIcon = ImageHandler.getProgramImgIcon((CANCEL+size+PNG));
		JButton imageButton= new JButton(CANCEL_TXT, buttonIcon);
		imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return imageButton;
	}	
//-----------------------------------------------------------------------------	
	public static JButton createConfirmButton(int size, String txt){
		size=checkSize(size);
		
		ImageIcon buttonIcon = ImageHandler.getProgramImgIcon((CONFIRM+size+PNG));
		JButton imageButton= new JButton(txt, buttonIcon);
		imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return imageButton;
	}
//-----------------------------------------------------------------------------
	public static JButton createEditButton(int size, String txt){
		size=checkSize(size);
		
		ImageIcon buttonIcon = ImageHandler.getProgramImgIcon((EDIT+size+PNG));
		JButton imageButton= new JButton((EDIT_TXT+txt), buttonIcon);
		imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return imageButton;
	}	
//-----------------------------------------------------------------------------	
	public static JButton createDeleteButton(int size, String txt){
		size=checkSize(size);
		
		ImageIcon buttonIcon = ImageHandler.getProgramImgIcon((DELETE+size+PNG));
		JButton imageButton= new JButton(DELETE_TXT, buttonIcon);
		imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return imageButton;
	}
//-----------------------------------------------------------------------------	
	public static JButton createPreviewButton(int size, String txt){
		size=checkSize(size);
		
		ImageIcon buttonIcon = ImageHandler.getProgramImgIcon((PREVIEW+size+PNG));
		JButton imageButton= new JButton((PREVIEW_TXT+txt), buttonIcon);
		imageButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		imageButton.setHorizontalTextPosition(SwingConstants.CENTER);
		
		return imageButton;
	}
//-----------------------------------------------------------------------------
	/**
	 * Used internally to ensure only existing size images are used.
	 */
	private static int checkSize(int size){
		if(size==LARGE | size==MEDIUM | size==SMALL){
			return size;
		} else {
			if(size>LARGE){ 
				return  LARGE;
			} else if(size<SMALL){
				return SMALL;
			}
		}
		return MEDIUM;
	}
//-----------------------------------------------------------------------------
}



