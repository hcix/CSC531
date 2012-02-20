package utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import userinterface.MapPanel;

public class ImageHandler {
//-----------------------------------------------------------------------------
	/**
	 * Create an ImageIcon from the specified image path. Returns an ImageIcon,
	 * or null if the path was invalid. 
	 */
	public static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = MapPanel.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
//-----------------------------------------------------------------------------
    /**
     * Resize an image to the specified size, returning a scaled image.
     */
    public Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
        		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
//-----------------------------------------------------------------------------
}
