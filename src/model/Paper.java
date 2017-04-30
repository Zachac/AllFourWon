package model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
/**
 * This is the class that creates the paper to be passed on to the other classes.
 * @author Kevin Nguyen
 * 
 */
public class Paper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7258827148995367406L;
	private Path theFilePath;
	//We don't need a setter for this right? Since it's handled in the GUI.
	private String thePaperTitle;
	private List<Author> theAuthorNames;
	//The date class gets the specified time of the submission down to milliseconds.
	private Date theSubmissionDate;
	
	/**
	 * Constructor for paper object.
	 * @param file
	 * @param authors
	 * @param title
	 */
	public Paper(Path filePath, List<Author> theAuthors, String theTitle) {
		theFilePath = filePath;
		thePaperTitle = theTitle;
		theAuthorNames = theAuthors;
		theSubmissionDate = new Date();
	}
	
	/**
	 * Gets the authors for the paper.
	 * @return list of the author names.
	 */
	public List<Author> getAuthors() {
		return theAuthorNames;
	}
	
	/**
	 * Gets the title of the paper.
	 * @return title of paper
	 */
	public String getTitle() {
		//If we want to just get the file name we could do 
		//theFilePath.getFileName(); 
		//but i'm assuming the author sets the name in the gui?
		return thePaperTitle;
	}
	/**
	 * Gets the path of the paper.
	 * @return the path of the paper
	 */
	public Path getDocumentPath() {
		//Is this sufficient? This returns the entire path name from the file.
		return theFilePath.toAbsolutePath();
	}
	/**
	 * This returns the submission date of the file. 
	 * This might be set to change since we might have to change to UTC-12 or something.
	 * @return the submission date of the file
	 */
	public Date getSubmissionDate() {
		return theSubmissionDate;
	}
}

