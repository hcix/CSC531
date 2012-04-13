/**
 * 
 */
package utilities.pdf;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jpedal.PdfDecoder;
import utilities.ui.SwingHelper;


public class ZoomControls extends JPanel {
private static final long serialVersionUID = 1L;
	private int zoomIdx;
	private PdfDecoder pdfDecoder;
	String[] zoomVals = { "50%", "75%", "100%", "150%", "200%", "250%", "300%", "350%", "400%" };
	final double[] scalingVals = { 0.5, 0.75, 1, 1.50, 2, 2.5, 3, 3.5, 4 };
//-----------------------------------------------------------------------------
	public ZoomControls(PdfDecoder pdfDecoder){
		  
		this.pdfDecoder = pdfDecoder;
		
		JButton zoomIn = initZoomInButton();
	    JButton zoomOut = initZoomOutButton();
	    
	    this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
	    this.add(zoomIn);
	    this.add(zoomOut);
	  
	    //Set the initial zoom level to be 100%
	    zoomIdx = 2;
	}
//-----------------------------------------------------------------------------
	private JButton initZoomInButton() {
		JButton zoomIn = SwingHelper.createImageButton("icons/zoomIn_32.png");
	    zoomIn.setToolTipText("Zoom In"); 
	    zoomIn.setBorderPainted(false);
	    zoomIn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		zoomIdx++;
				if(zoomIdx>8){ zoomIdx=8; }
				pdfDecoder.setPageParameters((float)(scalingVals[zoomIdx]),1);
				pdfDecoder.invalidate();
				pdfDecoder.updateUI();
				pdfDecoder.validate();
	    	}
	    });
	    
	    return zoomIn;
	  }
//-----------------------------------------------------------------------------
	private JButton initZoomOutButton() {
		JButton zoomOut = SwingHelper.createImageButton("icons/zoomOut_32.png");
		zoomOut.setToolTipText("Zoom Out"); 
		zoomOut.setBorderPainted(false);
		zoomOut.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		zoomIdx--;
	    		if(zoomIdx<0){ zoomIdx=0; }
	    		pdfDecoder.setPageParameters((float)(scalingVals[zoomIdx]),1);
	    		pdfDecoder.invalidate();
	    		pdfDecoder.updateUI();
	    		pdfDecoder.validate();		
	    	}
	    });
	    
	    return zoomOut;
	}
//-----------------------------------------------------------------------------
}
