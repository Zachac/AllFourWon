package model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

public class Paper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7258827148995367406L;

	/**
	 * Constructor for paper object.
	 * @param file
	 * @param authors
	 * @param title
	 */
	public Paper(Path theFilePath, List<String> theAuthors, String theTitle) {
		
	}
	
	/**
	 * Gets the authors for the paper.
	 * @return list of the author names.
	 */
	public List<String> getAuthors() {
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
}
