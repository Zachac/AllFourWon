package model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class Paper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7258827148995367406L;
	private Date submissionDate;

	
	
	/**
	 * Constructor for paper object.
	 * @param file
	 * @param authors
	 * @param title
	 */
	public Paper(Path theFilePath, List<Author> theAuthors, String theTitle) {
		submissionDate = new Date(); //creates a new date at the current time -zach
	}
	
	/**
	 * Gets the authors for the paper.
	 * @return list of the author names.
	 */
	public List<Author> getAuthors() {
		return null;
	}
	
	/**
	 * Gets the title of the paper.
	 * @return title of paper
	 */
	public String getTitle() {
		return null;
	}
	
	/**
	 * Gets the path of the paper.
	 * @return the path of the paper
	 */
	public Path getDocumentPath() {
		/*
		 * suggestion, return file path of the file.
		 * -Zach
		 */
		return null;
	}

	/**
	 * @return the submissionDate
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}
}
