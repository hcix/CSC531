package utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import program.ProgramCore;

import userinterface.MainInterface;
import userinterface.MapPanel;

public class ImageHandler {
//-----------------------------------------------------------------------------
	/** <b> createImageIcon </b>
	 * <pre>public static ImageIcon <b>createImageIcon</b>(String path)</pre> 
	 * <blockquote> 
	 * Creates an <code>ImageIcon</code> from the specified image path. 
	 * Image path should be given relative to the <code>userinterface</code> 
	 * package.
	 * </blockquote>
	 * @param path - the image's path relative to the <code>userinterface</code> package
	 * @return an <code>ImageIcon</code> or <code>null</code> if the path was invalid 
	 */
	public static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = MainInterface.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
//-----------------------------------------------------------------------------
    /**
     * 
     */
	/** <b> getScaledImage </b>
	 * <pre>public Image <b>getScaledImage</b>(Image srcImg, int w, int h)</pre> 
	 * <blockquote> 
	 * Scales/Resizes a given <code>Image</code> to the specified size.
	 * </blockquote>
	 * @param srcImg - the <code>Image</code> to be resized
	 * @param w - the desired width
	 * @param h - the desired height 
	 * @return a scaled version of the given <code>Image</code>
	 */
    public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
        		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
//-----------------------------------------------------------------------------
/**
 * 
 */
/** <b> getScaledImageIcon </b>
 * <pre>public ImageIcon <b>getScaledImageIcon</b>(ImageIcon srcImg, int w, int h)</pre> 
 * <blockquote> 
 * Scales/Resizes a given <code>ImageIcon</code> to the specified size returning
 * an <code>ImageIcon</code>. If an error occurs with locating the original image
 * from the <code>ImageIcon</code>, then the original <code>ImageIcon</code> is
 * returned.
 * </blockquote>
 * @param srcImg - the <code>ImageIcon</code> to be resized
 * @param w - the desired width
 * @param h - the desired height 
 * @return a scaled version of the given <code>ImageIcon</code>
 */
public static ImageIcon getScaledImageIcon(ImageIcon srcImgIcon, int w, int h){
	Image srcImg;
	try{
		srcImg = srcImgIcon.getImage();
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("Unable to find original image");
		return srcImgIcon;
	}
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
    		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    ImageIcon resizedImgIcon = new ImageIcon(resizedImg);
    return resizedImgIcon;
}
//-----------------------------------------------------------------------------
}
