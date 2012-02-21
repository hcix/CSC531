package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utilities.ImageHandler;

public class BOLOform extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	BOLOform(JFrame parent){
		super(parent, "New BOLO", true);
		this.setPreferredSize(new Dimension(700,900));
		this.setSize(new Dimension(700,900));
		
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		
		//put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		// Make sure that if the user cancels, the window does the right thing
		this.addWindowListener(new WindowAdapter( ) {
			public void windowClosing(WindowEvent e) {
				closeAndCancel( );
			}
		});
	    
	    
	    //Set up form interface
	    Container contentPane = getContentPane();
	 //   SpringLayout layout = new SpringLayout();
   //     contentPane.setLayout(layout);
        
        
	    
		//Add the BOLO "letter head" image
		ImageIcon boloHeaderIcon = ImageHandler.createImageIcon("images/boloHeader.png");
		JLabel boloHeader = new JLabel(boloHeaderIcon);
		//add(boloHeader);
		JPanel boloHeaderPanel = new JPanel();
		boloHeaderPanel.add(boloHeader);
		
	
		
		
	    JPanel basicInfo = new JPanel();
	   // basicInfo.setLayout(new BoxLayout(basicInfo, BoxLayout.Y_AXIS));

	    JLabel dateField = new JLabel("Date: ");
	    basicInfo.add(dateField);

	    dialogPanel.add(basicInfo);

	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(new ActionListener( ) {
	      public void actionPerformed(ActionEvent ae) {
	        closeAndCancel( );
	      }
	    });
	    
	    // Add in the OK and Cancel buttons for our dialog box.
	    JButton saveButton = new JButton("Save");
	    saveButton.addActionListener(new ActionListener( ) {
	      public void actionPerformed(ActionEvent e) {
	        closeAndSave( );
	      }
	    });
	    
	

	    JPanel controlPanel = new JPanel();
	    
	    controlPanel.add(saveButton);
	    controlPanel.add(cancelButton);

		dialogPanel.add(boloHeaderPanel);
	    dialogPanel.add(basicInfo);
	    dialogPanel.add(controlPanel);
	    
	    contentPane.add(dialogPanel);
	 //   contentPane.add(boloHeaderPanel);
	 //   contentPane.add(basicInfo);
	 //   contentPane.add(controlPanel);
	    
	//    this.pack();
	}
//-----------------------------------------------------------------------------
	 // Something in the font changed, so figure out what and make a
	  // new font for the preview label.
	  public void actionPerformed(ActionEvent e) {
	    // Check the name of the font.
/*	    if (!StyleConstants.getFontFamily(attributes)
	                       .equals(fontName.getSelectedItem( ))) {
	      StyleConstants.setFontFamily(attributes, (String)fontName.getSelectedItem( ));
	    }
	    // Check the font size (no error checking yet).
	    if (StyleConstants.getFontSize(attributes) != 
	                                   Integer.parseInt(fontSize.getText( ))) {
	      StyleConstants.setFontSize(attributes, Integer.parseInt(fontSize.getText( )));
	    }
	    // Check to see if the font should be bold.
	    if (StyleConstants.isBold(attributes) != fontBold.isSelected( )) {
	      StyleConstants.setBold(attributes, fontBold.isSelected( ));
	    }
	    // Check to see if the font should be italic.
	    if (StyleConstants.isItalic(attributes) != fontItalic.isSelected( )) {
	      StyleConstants.setItalic(attributes, fontItalic.isSelected( ));
	    }
	    // And update our preview label
	    updatePreviewFont( );*/
	  }
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------

  
	//  public Font getNewFont( ) { return newFont; }
	//  public Color getNewColor( ) { return newColor; }
	//  public AttributeSet getAttributes( ) { return attributes; }
//-----------------------------------------------------------------------------
	 public void closeAndSave( ) {
	    // Save font and color information.
	    //newFont = previewLabel.getFont( );
	   // newColor = previewLabel.getForeground( );

	    // Close the window.
	    setVisible(false);
	  }
//-----------------------------------------------------------------------------
	  public void closeAndCancel( ) {
	    // Erase any font information and then close the window.
	//    newFont = null;
	 //   newColor = null;
	    setVisible(false);
	  }
//-----------------------------------------------------------------------------
		JPanel makeInfoChart(){
			JPanel tablePanel = new JPanel();
			
			String[] tableRowHeaderNames = {"Date; Time; Location of Incident:",
			"Reference & Case #:",
			"BOLO prepared by:",
			"BOLO approved by:",
			"Date & Time of BOLO:",
			"Status",
			"Subject description & information"};
			
			//Create initially empty table
			JTable table = new JTable();
			table.setShowGrid(true);
			table.setGridColor(Color.black);
			table.setPreferredScrollableViewportSize(new Dimension(895, 100));
			table.setFillsViewportHeight(true);
			
			//Put the table in a scroll pane
			JScrollPane tableScrollPane = new JScrollPane();
			tableScrollPane.setViewportView(table);
			
			tablePanel.setLayout(new BorderLayout());
			tablePanel.add(tableScrollPane,BorderLayout.CENTER);
			add(tablePanel, BorderLayout.CENTER);
			String[] colHeaders = {"", ""};
			
			DefaultTableModel tableModel = new DefaultTableModel(colHeaders,7);
			table.setModel(tableModel);
			
			for(int i=0; i<tableRowHeaderNames.length; i++){
				
			}
			return tablePanel;
		}
//-----------------------------------------------------------------------------	

}
