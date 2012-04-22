package utilities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.filechooser.FileFilter;
import org.jpedal.examples.simpleviewer.SimpleViewer;
//-----------------------------------------------------------------------------
/**
* The <code>FileHelper</code> class is designed to help with accessing files 
* within the file system.
*/
public class FileHelper {
	//Image File Extensions
	public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String GIF = "gif";
    public final static String TIFF = "tiff";
    public final static String TIF = "tif";
    public final static String PNG = "png";
    //Text File Extensions
	public final static String DOC = "doc";
    public final static String DOCX = "docx";
    public final static String PDF = "pdf";
    public final static String TXT = "txt";
    public final static String RTF = "rtf";
    
    //Associated Program Directories
    public final static String PHOTO_DIR = "Photos";
    public final static String VIDEO_DIR = "Videos";
    public final static String DOC_DIR = "Documents";
    public final static String FORMS_SUBDIR = "FormTemplates";
    public final static String ANNOUN_SUB_DIR = "Announcements";
    public final static String SFT_RPTS_SUBDIR = "ShiftReports";
    public final static String BB_SUBDIR = "BlueBookEntries";
    public final static String BOLOS_SUBDIR = "Bolos";
    
    //Codes to use for the various kinds of files/forms 
    public final static int BLUE_BK_ENTRY_FILE = 0;
    public final static int BOLO_FILE = 1;
    public final static int SHIFT_CDR_RPT_FILE = 2;
    
    public final static String PATH_SEP = File.pathSeparator;
//-----------------------------------------------------------------------------
  	/**
  	 * Constructor used to provide access to the various inner classes of 
  	 * <code>FileHelper</code>. Also serves to get the path of the directory
  	 * that this program and it's associated files are in.
  	 */
  	public FileHelper() { }
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the file where the document 
	 * is stored on the file system
	 * 
	 * @param doc - the local path of the document
	 * @return docName - the absolute path of the document
	 * @deprecated use the method corresponding to name of subdir to 
	 * access instead
	 */
	public static String getDocumentPathName(String doc){
		File progDir = new File(getProgramDirPathName());
		Path docPath=null;
		String docName=null;
			
		try{
		docPath = Paths.get(progDir.getCanonicalPath(), DOC_DIR, doc);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		docName = docPath.toString();
		
//DEBUG	System.out.println("getDocumentPathName = " + docName);	
		return docName;
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the absolute path name of the image file located in the 
	 * userinterface/images package.
	 */
	public static String getImageResourcePathName(String imageName){
		File progDir = new File(getProgramDirPathName());
		Path imgPath=null;
		String imgName=null;
			
		try{
			imgPath = Paths.get(progDir.getCanonicalPath(), "Project", "src",
					"userinterface", "images", imageName);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		imgName = imgPath.toString();
		
//DEBUG	System.out.println("getDocumentPathName = " + docName);	
		return imgName;
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the file where the pdf of the
	 * <code>BlueBookEntry</code> is stored
	 * 
	 * @param bbPdfName - the local path of the report
	 * @return the absolute path of the report
	 */
	public static String getBBEntryPdfPathName(String bbPdfName){
		File progDir = new File("..");
		Path docPath=null;
		String docName=null;
			
		try{
		docPath = Paths.get(progDir.getCanonicalPath(), 
				DOC_DIR, BB_SUBDIR, bbPdfName);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		docName = docPath.toString();
		
//DEBUG	
//System.out.println("getBBEntryPdfPathName = " + docName);	
		return docName;
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the file where the pdf of the
	 * <code>BlueBookEntry</code> is stored
	 * 
	 * @param bbPdfName - the local path of the report
	 * @return the absolute path of the report
	 */
	public static String getBoloPdfPathName(String bolofn){
		File progDir = new File(getProgramDirPathName());
		Path docPath=null;
		String docName=null;
			
		try{
			docPath = Paths.get(progDir.getCanonicalPath(), 
				DOC_DIR, BOLOS_SUBDIR, bolofn);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		docName = docPath.toString();
		
//DEBUG	System.out.println("getBBEntryPdfPathName = " + docName);	
		return docName;
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the directory where the pdf of the
	 * <code>BlueBookEntry</code> is stored on the filesystem
	 * 
	 * @return the canonical path name of the reports
	 * @see getCanonicalPath()
	 */
	public static String getStoredBBentriesDir(){
		File progDir = new File(getProgramDirPathName());
		Path dirPath=null;
		String dirName=null;
			
		try{
			dirPath = Paths.get(progDir.getCanonicalPath(), 
				DOC_DIR, BB_SUBDIR);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		dirName = dirPath.toString();
		
//DEBUG 
//System.out.println("FileHelper: getStoredBBentriesDir: dir = " + dirName);	

		return dirName;	
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the file where the report 
	 * is stored on the file system
	 * 
	 * @param reportName - the local path of the report
	 * @return the absolute path of the report
	 */
	public static String getReportPathName(String reportName){
		File progDir = new File(getProgramDirPathName());
		Path docPath=null;
		String docName=null;
			
		try{
		docPath = Paths.get(progDir.getCanonicalPath(), 
				DOC_DIR, SFT_RPTS_SUBDIR, reportName);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		docName = docPath.toString();
		
//DEBUG	System.out.println("getDocumentPathName = " + docName);	
		return docName;
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the directory where the report 
	 * is stored on the file system
	 * 
	 * @return dirName - the canonical path name of the reports
	 * @see getCanonicalPath()
	 */
	public static String getReportsDir(){
		File progDir = new File(getProgramDirPathName());
		Path dirPath=null;
		String dirName=null;
			
		try{
			dirPath = Paths.get(progDir.getCanonicalPath(), 
				DOC_DIR, SFT_RPTS_SUBDIR);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		dirName = dirPath.toString();
		
//DEBUG System.out.println("FileHelper: getReportsDir: dir = " + dirName);	

		return dirName;	
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the file where the roster 
	 * is stored on the file system
	 * 
	 * @return rosterFileName - the canonical path name of the roster
	 */
	public static String getRosterFilePathName(){
		File progDir = new File("..");
		Path rosterPath=null;
		String rosterFileName=null;
			
		try{
			rosterPath = Paths.get(progDir.getCanonicalPath(), 
					"Project", "src", "utilities", "xml", "roster.xml");
		} catch (IOException e){
			e.printStackTrace();
		}
		
		rosterFileName = rosterPath.toString();
		
//DEBUG System.out.println("rosterFileName = " + rosterFileName);	
		return rosterFileName;
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the absolute path to the file where the form template
	 * is stored on the file system
	 * 
	 * @param form - the name of the form
	 * @return docName - the absolute path of the form template
	 */
	public static String getFormTemplatePathName(String form){
		File progDir = new File(getProgramDirPathName());
		Path docPath=null;
		String docName=null;
			
		try{
		docPath = Paths.get(progDir.getCanonicalPath(), DOC_DIR, FORMS_SUBDIR, form);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		docName = docPath.toString();
		
//DEBUG	System.out.println("getFormTemplatePathName = " + docName);
		
		return docName;
	}
//-----------------------------------------------------------------------------
  	/**
  	 * Gets the name of the absolute path to the directory where the UMPD Management
  	 * Program is stored on the file system
  	 * .
  	 * @return programDir - the absolute path to the directory containing the UMPD Management app
  	 * and all of it's accompanying files
  	 */
	public static String getProgramDirPathName(){
		String programDir = null;
		//File progDir = new File("."); //this one to run in a jar
		File progDir = new File(".."); //this one to run in Eclipse
		try {
			programDir = progDir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
//DEBUG System.out.println("programDir = " + programDir);	
		
		return programDir;
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the properties file path, which is used by 
	 * <code>ResourceManager</code> to load the program's properties.
	 * 
	 * @return the absolute path to properties file
	 */
	public static String getPropertiesFile(){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path propFilePath = Paths.get(progDir, 
				"Project", "src","program", "progProperties.xml");

		return (propFilePath.toString());
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the <code>itemsToReview.xml</code> file.
	 * 
	 * @return the absolute path to itemsToReview.xml
	 */
	public static String getItemsToReviewFile(){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path itemsFilePath = Paths.get(progDir, "Project", "src",
				"progAdmin", "itemsToReview", "itemsToReview.xml");
		
		return (itemsFilePath.toString());
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets the name of the properties file path, which is used by 
	 * <code>ResourceManager</code> to load the program's properties.
	 * 
	 * @return the absolute path to properties file
	 */
	public static String getDatabaseFile(){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path propFilePath = Paths.get(progDir, "Project", "Database", "umpd.db" );
		
		return (propFilePath.toString());
	}
//-----------------------------------------------------------------------------
	/**
	 * Copies a Shift Commander Summary Report into the UMPD Management Program's
	 * program files. Makes a copy of the specified file and places the copy into
	 * the program's Documents/ShiftCdrReports sub-directory.
	 * 
	 *  @param original - the original report file
	 *  @return the path of the newly created file, which is an exact copy of the
	 *  original
	 */
	public static Path copyShiftCdrReport(File original){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path destination = Paths.get(progDir, DOC_DIR, SFT_RPTS_SUBDIR);
		
		return(copyFile(original, destination));
	}
//-----------------------------------------------------------------------------
	/**
	 * Copies the specified photo file into the Photos directory associated with
	 * the program.
	 * @param original - file to copy into 'Photos' directory
	 */
	public static Path copyPhoto(File original){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path destination = Paths.get(progDir, PHOTO_DIR);
		
		return (copyFile(original, destination));
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Saves the given image with the specified filename as a photo file into 
	 * the Photos directory associated with the program.
	 * @param image - image to save into 'Photos' directory
	 * @param filename - name to save image file as
	 */
	public static Path savePhoto(ImageIcon imgIcn, String filename){
		String progDir = getProgramDirPathName();
		
		System.out.printf("\nFileHelper: savePhoto(): filename = %s\n", filename);
		
		//Specifies a system independent path
		Path destinationFile = Paths.get(progDir, PHOTO_DIR, filename);
		//Path destinationFile = Paths.get(filename, null);
		
		int w = imgIcn.getIconWidth();
		int h = imgIcn.getIconHeight();
		BufferedImage imageToSave = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = imageToSave.createGraphics();
		graphics2D.drawImage(imgIcn.getImage(), 0, 0, null);
     
		
	    File outputfile = new File(destinationFile.toString());
	    String extension = getFileExtension(outputfile);
	    
//DEBUG System.out.printf("\nFileHelper: savePhoto(): destinationFile.toString() = %s\n",
//destinationFile.toString());
	    
	    
	    int i=0;
	    while(outputfile.exists()){
	    	i++;
	    	String name=getNameWithoutExtension(filename);
	    	name=name+i; 	
	    	destinationFile = Paths.get(progDir, PHOTO_DIR, name+"."+extension);
	    	outputfile = new File(destinationFile.toString());
		    
//DEBUG
//System.out.printf("\nFileHelper: savePhoto(): (while loop) " +
//		"destinationFile.toString() = %s\n", destinationFile.toString());
	    
	    	//safety condition to protect against infinite loop
	    	if(i>100){ 
	    		//TODO make the println below into a error/warning message for user
	    		System.out.println("ERROR 100 files with this name exist already!"); }
	    }
		    
	    
	    try { 
	    	
	    	ImageIO.write(imageToSave, extension, outputfile);
	    } catch (IOException e) {
	    	//TODO make the println below into a error/warning message for user
	    	System.out.println("FileHelper: savePhoto(): Problem saving photo.");
	    	return null;
	    }
	
		return destinationFile;
	}
//-----------------------------------------------------------------------------
	/**
	 * Copies the specified video file into the Videos directory associated with
	 * the program.
	 * @param original - file to copy into 'Videos' directory
	 */
	public static Path copyVideo(File original){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path destination = Paths.get(progDir, VIDEO_DIR);
		
		return (copyFile(original, destination));

	}
//-----------------------------------------------------------------------------
	/**
	 * Copies the specified video file into the Videos/Announcements directory
	 * associated with the program.
	 * @param original - file to copy into 'Videos/Announcements' directory
	 */
	public static Path copyVideoAnnoun(File original){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path destination = Paths.get(progDir, VIDEO_DIR, ANNOUN_SUB_DIR);
		
		return(copyFile(original, destination));
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Copies the specified source file to the target destination.
	 * @param source - file to copy
	 * @param target - absolute path of the location to copy the 
	 * source file to
	 */
	public static Path copyFile(File source, Path target){
		
		Path orginalPath = source.toPath();
		Path newPath = target.resolve(orginalPath.getFileName());
		String newFileName;
		
		for(int i=1; i<100; i++){
			try {
				target = Files.copy(orginalPath, newPath);
				return target;
			} catch (FileAlreadyExistsException e){
				Path p = orginalPath.getFileName();
//DEBUG System.out.println("\n\n copyfile pathp : " + p.toString() + "\n\n");
				newFileName = getNameWithoutExtension(p.toString()) + i + "."
						+ getFileExtension(source);
//DEBUG System.out.println("\n\n copyfile newFilename : " + newFileName + "\n\n");
				newPath = Paths.get(target.toString(), newFileName);

//DEBUG System.out.println("\n\n copyfile newpath : " + newPath.toString() + "\n\n");
			} catch (IOException x) {
				System.err.format("Unable to copy: %s: %s%n", orginalPath, x);  
		    }
		}
		
		//100 files with this file's same name (and diff numbers after) already exist, 
		//tell the user to pick a new name
		return newPath;
	}
//-----------------------------------------------------------------------------
	/**
	 * Create a unique filename to save an instance of the related form in
	 * the correct directory.
	 * 
	 * @param formTypeInt - the int corresponding to the type of form 
	 */
	public static String createNewUniqueFilename(int formTypeInt){
		String saveAs;
		//create the filename the saved form will have
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
		dateFormat.applyPattern("MM_dd_yyyy_HH_mm");
		
		Date date = new Date(System.currentTimeMillis());
		String dateTime = dateFormat.format(date);
		
		if(formTypeInt==BLUE_BK_ENTRY_FILE){
			saveAs = FileHelper.getBBEntryPdfPathName("BlueBookForm" + dateTime + ".pdf");
			File saveAsFile = new File(saveAs);
			int i=0;
			while((i<100) && (saveAsFile.exists())){
				i++;
				String newFileName = FileHelper.getNameWithoutExtension(saveAs.toString()) 
						+"_v" + i + "." + FileHelper.getFileExtension(saveAsFile);
				saveAsFile = new File(saveAs);
			}
			//return the saveAsFile name 
			return saveAsFile.toString();
		} else if(formTypeInt==BOLO_FILE){
			saveAs = FileHelper.getBoloPdfPathName("Bolo" + dateTime + ".pdf");
			File saveAsFile = new File(saveAs);
			int i=0;
			while((i<100) && (saveAsFile.exists())){
				i++;
				String newFileName = FileHelper.getNameWithoutExtension(saveAs.toString()) 
						+"_v" + i + "." + FileHelper.getFileExtension(saveAsFile);
				saveAsFile = new File(saveAs);
			}
			//return the saveAsFile name 
			return saveAsFile.toString();
		}
			//error occured, return null
			return null;
		
	}
//-----------------------------------------------------------------------------
  	/** 
  	 * Creates and returns an instance of <code>FileHelper</code>'s inner
  	 * <code>ImageFilter</code> class. 
  	 * @return an instance of <code>FileHelper</code>'s inner
  	 * <code>ImageFilter</code> class.
  	 */
  	public static ImageFilter getImageFilter() { 
	
  		FileHelper fh = new FileHelper();
  		ImageFilter imgFilter = fh.new ImageFilter();
  		
  		return imgFilter;
  	}
//-----------------------------------------------------------------------------
	/** 
	 * Creates and returns an instance of <code>FileHelper</code>'s inner
	 * <code>PDFFilter</code> class. 
	 * @return an instance of <code>FileHelper</code>'s inner
	 * <code>PDFFilter</code> class.
	 */
	public static PDFFilter getPDFFilter() { 
	
		FileHelper fh = new FileHelper();
		PDFFilter pdfFilter = fh.new PDFFilter();
		
		return pdfFilter;
	}
//-----------------------------------------------------------------------------
  	/**
	 * Given a <code>File</code>, returns the extension of the given file.
	 * @param file - the <code>File</code> to get the extension of
	 * @return the extension of the given <code>File</code>
	 */
	public static String getFileExtension(File file) {
		String ext = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');
		 
		if (i > 0 &&  i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		
		return ext;
	}
//-----------------------------------------------------------------------------
  	/**
	 * Given a filename, returns the name w/o the extension of the given file.
	 * @param filename - the filename to remove the extension of
	 * @return the filename without the .extenstion
	 */
	public static String getNameWithoutExtension(String filename) {
		int i = filename.lastIndexOf('.');

		filename=filename.substring(0, i);
		
//DEBUG System.out.println("FileHelper: getNameWithoutExtension: filename = " + filename);
		
		return filename;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @param pdfFile - the filename of a PDF
	 * @param c - @see JComponent
	 */
	public static void openPDFInComponent(String pdfFile, JComponent c){
		
		SimpleViewer viewer = new SimpleViewer();	
		viewer.setRootContainer(c);
		viewer.setupViewer();
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return the absolute file path of the script to check the password
	 */
	public static String getPasswordScript(){
		String progDir = getProgramDirPathName();
		
		//Specifies a system independent path
		Path passwdScript = Paths.get(progDir, "c.py");
		
		return passwdScript.toString();

	}
//-----------------------------------------------------------------------------
/**
 * INNER CLASSES
 * Various inner classes to extend FileFilter to provide JFileChoosers with
 * filtering options for the desired file type.
 */	
//=============================================================================
	/**
	 * Inner class extending <code>FileFilter</code> to allow a JFileChooser 
	 * to only accept files with an extension indicative of an image file.
	 */	
	public class ImageFilter extends FileFilter {
		
		public ImageFilter(){ 
			//do nothing
		}
	 
	    //Accept all gif, jpg, tiff, pdf, or png files
	    @Override
		public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	 
	        String extension = FileHelper.getFileExtension(f);
	        if (extension != null) {
	            if (extension.equals(TIFF) ||
	                extension.equals(TIF) ||
	                extension.equals(GIF) ||
	                extension.equals(JPEG) ||
	                extension.equals(JPG) ||
	                extension.equals(PDF) ||
	                extension.equals(PNG)) {
	                    return true;
	            } else {
	                return false;
	            }
	        } 
	 
	        return false;
	    }
	 
	    //The description of this filter
	    @Override
		public String getDescription() {
	        return "Images";
	    }
	}
//=============================================================================
	/**
	 * Inner class extending <code>FileFilter</code> to allow a JFileChooser 
	 * to only accept files with an extension indicative of a text file.
	 */	
	public class TextFilter extends FileFilter {
	 
	    //Accept all doc, docx, pdf, txt, or rtf files
	    @Override
		public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	 
	        String extension = FileHelper.getFileExtension(f);
	        if (extension != null) {
	            if (extension.equals(DOC) ||
	                extension.equals(DOCX) ||
	                extension.equals(PDF) ||
	                extension.equals(TXT) ||
	                extension.equals(RTF)) {
	                    return true;
	            } else {
	                return false;
	            }
	        } 
	 
	        return false;
	    }
	 
	    //The description of this filter
	    @Override
		public String getDescription() {
	        return "Images";
	    }
	}
//=============================================================================
	/**
	 * Inner class extending <code>FileFilter</code> to allow a JFileChooser 
	 * to only accept PDF files with an extension indicative of a text file.
	 */	
	public class PDFFilter extends FileFilter {
	 
	    //Accept only pdf files
	    @Override
		public boolean accept(File f) {
	        if (f.isDirectory()) {
	            return true;
	        }
	 
	        String extension = FileHelper.getFileExtension(f);
	        if (extension != null) {
	            if (extension.equals(PDF)) {
	                    return true;
	            } else {
	                return false;
	            }
	        } 
	 
	        return false;
	    }
	 
	    //The description of this filter
	    @Override
		public String getDescription() {
	        return "PDF";
	    }
	}
//=============================================================================
}
