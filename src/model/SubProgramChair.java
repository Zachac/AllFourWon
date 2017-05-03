/*
 * TCSS 360
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Subprogram chair class that is responsible for
 * containing a list of papers assigned to an instance of the object.
 * @author Ian Jury
 * @version 1.0
 *
 */
public final class SubProgramChair extends AbstractRole implements Cloneable {
    
    /** Generated UID.*/
    private static final long serialVersionUID = 3678435490225778582L;
    
    /** List of assigned papers to subprogram chair.*/
    private List<Paper> assignedPapers;
    
    /**
     * Constructor for subprogram chair object.
     * 
     * Preconditions: 
     * 		-Valid user name should be passed
     * 		-Unique user name (name should not already be in system)
     * Postconditions: 
     * 		-A SPC object will be made with its own list of papers 
     * 		and user name.
     * 
     * @param user the unique identifier of the user (AKA user name)
     */
    public SubProgramChair(String user) {
        super(user);
        assignedPapers = new ArrayList<>();
    }
    
    /**
     * Assigns a paper to the subprogram chair to manage.
     * 
     * Preconditions: 
     * 		-Paper object must be a valid paper object (not null).
     * 		-Elements of paper object should be valid(not null) such as author.
     * Postconditions: 
     * 		-Passed paper will be added to the SPC's assigned paper structure.
     * 
     * @param thePaper the paper to be added to the assigned paper list
     */
    public void addPaper(Paper thePaper) {
        if (!assignedPapers.contains(thePaper)) {
            assignedPapers.add(thePaper);            
        }
    }
    
    /**
     * Gets all of the papers that the subprogram chair has been assigned.
     * 
     * Preconditions:
     * 		 -Must be called on a valid SPC object.
     * Postcondition: 
     * 		-Will return a valid list of papers.
     * 
     * @return list of papers.
     * @throws CloneNotSupportedException 
     */
    public List<Paper> getPapers() {
        return new ArrayList<>(assignedPapers);
    }
}
