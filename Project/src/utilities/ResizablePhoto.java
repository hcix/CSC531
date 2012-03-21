package utilities;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
//-----------------------------------------------------------------------------
/**
 *
 */
public class ResizablePhoto {
	JLabel photoFrame = new JLabel();
	ImageIcon originalImgIcon;
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @param imgIcon
	 */
	ResizablePhoto(ImageIcon imgIcon){
		originalImgIcon = imgIcon;
		photoFrame.setIcon(originalImgIcon);
		
		SwingHelper.addLineBorder(photoFrame);
		
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(photoFrame);
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
}