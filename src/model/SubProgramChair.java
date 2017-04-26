package model;

import java.io.Serializable;
import java.util.List;

public class SubProgramChair implements Role, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3678435490225778582L;

	//field for paper structure
	@Override
	public String getUser() {
		return null;
	}
	
	/**
	 * Assigns a paper to the subprogram chair to manage.
	 */
	public void addPaper(Paper thePaper) {
		
	}
	
	/**
	 * Gets all of the papers that the subprogram chair has been assigned.
	 * @return list of papers.
	 */
	public List<Paper> getPapers() {
		return null;
	}
	

}
