package utilities.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import userinterface.MainInterfaceWindow;

public class ImageHandler {
//-----------------------------------------------------------------------------
	/** 
	 * Creates an <code>ImageIcon</code> from the specified image path. 
	 * Image path should be given relative to the <code>userinterface</code> 
	 * package.
	 * @param path - the image's path relative to the <code>userinterface</code> package
	 * @return an <code>ImageIcon</code> or <code>null</code> if the path was invalid 
	 * @deprecated use <code>getImageIcon()</code> or getProgramImgIcon instead
	 */
	@Deprecated
	public static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = MainInterfaceWindow.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
//-----------------------------------------------------------------------------
	/** 
	 * Creates an <code>ImageIcon</code> form the specified image path 
	 * resource within the program. Image path should be given relative to the
	 * <code>userinterface</code> package.
	 * @param path - the image's path relative to the <code>userinterface</code> package
	 * @return an <code>ImageIcon</code> or <code>null</code> if the path was invalid 
	 */
	public static ImageIcon getProgramImgIcon(String path) {
	    java.net.URL imgURL = MainInterfaceWindow.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
//-----------------------------------------------------------------------------
	/** 
	 * JDOC
	 * 
	 * @param absFileName - the absolute filename of the image file
	 */
	public static ImageIcon getImageIcon(String absFileName){
		ImageIcon imgIcon = new ImageIcon(absFileName);
		return imgIcon;
	}
//-----------------------------------------------------------------------------
	/** 
	 * Scales/Resizes a given <code>Image</code> to the specified size.
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
	 * Scales/Resizes a given <code>ImageIcon</code> to the specified size returning
	 * an <code>ImageIcon</code>. If an error occurs with locating the original image
	 * from the <code>ImageIcon</code>, then the original <code>ImageIcon</code> is
	 * returned.
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
			return null;
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
	/**
	 * JDOC
	 */
	public static ImageIcon getThumbnailImageIcon(File file, int w, int h){
		ImageIcon thumbnail = null;
		ImageIcon tmpIcon = new ImageIcon(file.getPath());
	    
		if (tmpIcon != null) {
	        if (tmpIcon.getIconWidth() > 90) {
	            thumbnail = new ImageIcon(tmpIcon.getImage().
	                                      getScaledInstance(90, -1,
	                                                  Image.SCALE_DEFAULT));
	        } else { 
	        	//image is already smaller than a thumbnail icon
	            thumbnail = tmpIcon;
	        }
		}
		
		return thumbnail;
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	public static ImageIcon getThumbnailImageIcon(Path path){
		ImageIcon thumbnail = null;
		ImageIcon tmpIcon = null;
		 
		if(path==null){
//DEBUG System.out.printf("NULL; path \n");
			return null;
		}
		
//DEBUG System.out.printf("path = %s\n", path.toString());

		path = path.toAbsolutePath();
		URI imgURI = path.toUri();
		    
		try {
	    	tmpIcon = new ImageIcon(imgURI.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    
		if (tmpIcon != null) {
	        if (tmpIcon.getIconWidth() > 90) {
	            thumbnail = new ImageIcon(tmpIcon.getImage().
	                                      getScaledInstance(90, -1,
	                                                  Image.SCALE_DEFAULT));
	        } else { 
	        	//image is already smaller than a thumbnail icon
	            thumbnail = tmpIcon;
	        }
		} else{
			System.out.println("error: image path null");
		}
		
		return thumbnail;
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	public static ImageIcon getScaledImageIcon(Path image, int w, int h){
		ImageIcon thumbnail = null;
		ImageIcon tmpIcon = null;
	    image = image.toAbsolutePath();
	    URI imgURI = image.toUri();
	    
	    
	    try {
	    	tmpIcon = new ImageIcon(imgURI.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    
		if (tmpIcon != null) {
	        if (tmpIcon.getIconWidth() > w) {
	            thumbnail = new ImageIcon(tmpIcon.getImage().
	                                      getScaledInstance(w, -1,
	                                                  Image.SCALE_DEFAULT));
	        } else { 
	        	//image is already smaller than a thumbnail icon
	            thumbnail = tmpIcon;
	        }
		} else{
			System.out.println("error: image path null");
		}
		
		return thumbnail;
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	public static BufferedImage getBufferedImage(String path, int w, int h){
		// Create an image, and wait for it to load
		ImageIcon imgIcn = getImageIcon(path);
		ImageIcon scaledImgIcn = getScaledImageIcon(imgIcn, w, h);
		
		BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = buffImg.createGraphics();
		g.drawImage(scaledImgIcn.getImage(), 0, 0, null);

		return(buffImg);
	}
//-----------------------------------------------------------------------------
}
