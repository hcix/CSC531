package utilities.ui;


import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.FileHelper;
//-----------------------------------------------------------------------------
/**
 *
 */
public class ResizablePhotoDialog extends JDialog {
private static final long serialVersionUID = 1L;
	JLabel photoFrame = new JLabel();
	ImageIcon originalImgIcon;
	ImageIcon resizedImgIcon;
	Path newPhotoFilePath;
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @param imgIcon
	 */
	public ResizablePhotoDialog(ImageIcon imgIcon, Window parent, final String photofilename){
		super(parent, "Resize Photo", Dialog.ModalityType.DOCUMENT_MODAL);
		originalImgIcon = imgIcon;
		resizedImgIcon = imgIcon;
		
		//default width and height values for the dialog
		int w=700, h=700;
		
		//get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//if the img is bigger than the default dialog size, make the dialog bigger 
		if(originalImgIcon.getIconWidth()>500){
			w=originalImgIcon.getIconWidth();
			if(w>screenSize.width){
				//if the img is bigger than the screen, make the dialog just a little 
				//smaller than the screen and the img just a little smaller the dialog
				w=screenSize.width-20;
				originalImgIcon=ImageHandler.getScaledImageIcon(
						originalImgIcon, (w-20), originalImgIcon.getIconHeight());
				resizedImgIcon=originalImgIcon;
			}
		}
		if(originalImgIcon.getIconHeight()>500){
			h=originalImgIcon.getIconHeight();
			if(h>screenSize.height){
				//if the img is bigger than the screen, make the dialog just a little 
				//smaller than the screen and the img just a little smaller the dialog
				h=screenSize.height-20;
				originalImgIcon=ImageHandler.getScaledImageIcon(
						originalImgIcon, originalImgIcon.getIconWidth(), (h-20));
				resizedImgIcon=originalImgIcon;
			}
		}
		
		this.setPreferredSize(new Dimension(w, h));
		this.setSize(new Dimension(w, h));
		
		photoFrame.setIcon(originalImgIcon);
		
		SwingHelper.addLineBorder(photoFrame);
		
		PhotoResizer pr = new PhotoResizer(this);
		pr.registerComponent(photoFrame);
		
		
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(w, h));
		p.setSize(new Dimension(w, h));
		this.setLocationRelativeTo(parent);
		
		p.add(photoFrame);
		JButton saveImgButton = new JButton("Set Image");
		p.add(saveImgButton);
		saveImgButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		saveAndClose(photofilename);
				//bolo.setPhotoFilePath(photoPath);	
	    	}
		}); 
		
		Container cp = this.getContentPane();
		cp.add(p);
		this.setVisible(true);
	}
//-----------------------------------------------------------------------------
	/**
	 * @return photoFrame - 
	 */
	public JLabel getPhotoFrame() {
		return photoFrame;
	}
//-----------------------------------------------------------------------------
	
	/**
	 * @param photoFrame - the photoFrame value to set for this ResizablePhoto.java
	 */
	public void setPhotoFrame(JLabel photoFrame) {
		this.photoFrame = photoFrame;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return originalImgIcon - 
	 */
	public ImageIcon getOriginalImgIcon() {
		return originalImgIcon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param originalImgIcon - the originalImgIcon value to set for this ResizablePhoto.java
	 */
	public void setOriginalImgIcon(ImageIcon originalImgIcon) {
		this.originalImgIcon = originalImgIcon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the resizedImgIcon 
	 */
	public ImageIcon getResizedImgIcon() {
		return resizedImgIcon;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param resizedImgIcon - the resizedImgIcon value to set for this ResizablePhoto.java
	 */
	public void setResizedImgIcon(ImageIcon resizedImgIcon) {
		this.resizedImgIcon = resizedImgIcon;
		photoFrame.setIcon(resizedImgIcon);
	}
//-----------------------------------------------------------------------------
	/**
	 * @return the newPhotoFilePath 
	 */
	public Path getNewPhotoFilePath() {
		return newPhotoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	 * @param newPhotoFilePath - the newPhotoFilePath value to set
	 */
	public void setNewPhotoFilePath(Path newPhotoFilePath) {
		this.newPhotoFilePath = newPhotoFilePath;
	}
//-----------------------------------------------------------------------------
	/**
	* Save the resized photo and close the dialog.
	*/
	public void saveAndClose(String photofilename){
		Path photoPath = FileHelper.savePhoto(getResizedImgIcon(), photofilename);
//DEBUG
//System.out.printf("\nResizablePhotoDialog: ResizablePhotoDialog(): photoPath.toString() = %s\n",photoPath.toString());
		setNewPhotoFilePath(photoPath);
		
		this.setVisible(false);
		 //close the window
		this.dispose();	
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	 public void closeAndCancel( ) {
//dispose of all resources used
		 
		this.setNewPhotoFilePath(null);
		 
		//close the dialog
		this.dispose();	
	 }
//-----------------------------------------------------------------------------
}