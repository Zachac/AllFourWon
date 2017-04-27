/*
 * TCSS 360
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Subprogram chair class containing a list of papers assigned to instance of object.
 * @author Ian Jury
 * @version 1.0
 *
 */
public class SubProgramChair extends AbstractRole implements Cloneable {
	
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 3678435490225778582L;
	
	/**
	 * List of assigned papers to subprogram chair.
	 */
	private List<Paper> assignedPapers = new ArrayList<>();

	public SubProgramChair(String user) {
        super(user);
    }

    /**
	 * Assigns a paper to the subprogram chair to manage.
	 */
	public void addPaper(Paper thePaper) {
		assignedPapers.add(thePaper);
	}
	
	/**
	 * Gets all of the papers that the subprogram chair has been assigned.
	 * @return list of papers.
	 */
	public List<Paper> getPapers() {
		//NOTE this is currently a mutable object!
		//should we work on cloning? -Ian
		return assignedPapers;
	}

}
