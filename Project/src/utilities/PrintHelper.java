package utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JFrame;
import javax.swing.JPanel;
//-----------------------------------------------------------------------------
/**
 * The <code>PrintHelper</code> class is designed to help printing
 *
 */
public class PrintHelper implements Printable, ActionListener {
	JFrame frameToPrint;
	JPanel panelToPrint;
//-----------------------------------------------------------------------------
    /**
     * Get the print job and pass it to <code>print</code>
     * 
     * @param f - get the current frame
     */
	public PrintHelper(JFrame f) {
        frameToPrint = f;
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
    }
//-----------------------------------------------------------------------------
    //WHY ARE THERE 2 OF THESE?
	public PrintHelper(JPanel f) {
    	panelToPrint = f;
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
    }
//-----------------------------------------------------------------------------
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
 
        if (page > 0) { 
            return NO_SUCH_PAGE;
        }
 
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
 
        /* Now print the window and its visible contents */
        panelToPrint.printAll(g);
 
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
//-----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }
//-----------------------------------------------------------------------------
	
}
