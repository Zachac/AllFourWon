package model;


import static java.nio.file.StandardCopyOption.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    
    private String stringFilePath;
    
    transient private Path theFilePath;
    //We don't need a setter for this right? Since it's handled in the GUI.
    private String thePaperTitle;
    
    private List<Author> theAuthorNames;
    //The date class gets the specified time of the submission down to milliseconds.
    private Date theSubmissionDate;
    
    /** The one who submitted the paper. */
	private Author theSubmitter;
    
    /**
     * Constructor for paper object.
     * 
     * The filePath must point to a valid file (exists and isn't a directory). 
     * 
     * @param file
     * @param authors
     * @param title
     */
    public Paper(Path filePath, List<Author> theAuthors, String theTitle, Author submitter) {
        thePaperTitle = theTitle;
        theAuthorNames = theAuthors;
        theSubmitter = submitter;
        theSubmissionDate = new Date();
        
        if (filePath != null) {
            File originalFile = filePath.toFile();
            
            if (!originalFile.exists() || originalFile.isDirectory()) {
                throw new IllegalArgumentException("Invalid file path!");
            }
            
            StringBuilder newFile = new StringBuilder();
            newFile.append("data/papers/");
            newFile.append(theSubmissionDate.getTime());
            newFile.append("-");
            newFile.append(originalFile.getName());
            
            
            try {
                theFilePath = Files.copy(filePath, Paths.get(newFile.toString()), REPLACE_EXISTING);
                stringFilePath = newFile.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            theFilePath = null;
            stringFilePath = null;            
        }
        
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
        if (theFilePath == null && stringFilePath != null) {
            theFilePath = Paths.get(stringFilePath);
        }
        
        return theFilePath;
    }
    /**
     * This returns the submission date of the file. 
     * This might be set to change since we might have to change to UTC-12 or something.
     * @return the submission date of the file
     */
    public Date getSubmissionDate() {
        return (Date) theSubmissionDate.clone();
    }

	/**
	 * @author Zachary Chandler
	 * @return the theSubmitter
	 */
	public Author getTheSubmitter() {
		return theSubmitter;
	}
}

