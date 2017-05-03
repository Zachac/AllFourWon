package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * This class is responsible for handling all the general
 * information in a conference. Which includes:
 *      - Papers submitted
 *      - roles and privileges
 *      - the submission deadline
 *      
 * @author Zachary Chandler
 */
public class Conference implements Serializable {

    /** Eclipse generated SVUID. */
    private static final long serialVersionUID = 3239987612704413287L;
    
    /** Max number of papers an author can publish. */
    private static final int AUTHOR_MAX_PAPERS = 5;

    /** The Papers submitted to the conference. */
    private List<Paper> papers;
    
    /** A map of authors to a list of their papers. */
    private TreeMap<Author, List<Paper>> authorsToPapers;
    
    /** A map of user names to their Author role. */
    private TreeMap<String, Author> authors;
    
    /** A map of user names to their SubProgramChair role. */
    private TreeMap<String, SubProgramChair> subProgramChairs;
    
    /** A map of user names to their Reviewer role. */
    private TreeMap<String, Reviewer> reviewers;
    
    /** The submission deadline for the conference. */
    private Date submissionDeadline;
    
    /** The name of the conference. */
    public final String name;
    
    /**
     * Creates a new empty conference. With no tracked papers, or roles.
     * @author Zachary Chandler
     */
    public Conference(String name) {
        authorsToPapers = new TreeMap<>();
        papers = new LinkedList<>();
        authors = new TreeMap<>();
        subProgramChairs = new TreeMap<>();
        reviewers = new TreeMap<>();
        
        this.name = name;
        
        submissionDeadline = null;
    }
    
    /**
     * Adds a paper to the conference
     * 
     * Preconditions:
     * 		thePaper is non null
     * 		thePaper contains only authors in this conference
     * 		thePaper was created before the deadline
     * 		every Author of thePaper is not at their limit of papers 
     * 		
     * Postconditions:
     * 		if thePaper is null, a NullPointerException is thrown.
     * 		if thePaper was created after the deadline, the method will return false
     * 			without submitting the paper.
     * 		if thePaper has one or more authors that are at their limit of papers,
     * 			the method will return false without submitting the paper.
     * 		if thePaper was already submitted, the method will return true without
     * 			resubmitting the paper
     * 		if thePaper contains an Author not from this conference, the method
     * 			will return false without submitting the paper
     * 		otherwise the paper will be submitted and the method will return true
     * 
     * @author Zachary Chandler
     * 
     * @param thePaper the paper.
     * @throws NullPointerExcpetion if thePaper is null.
     * @return if it successfully submits the paper.
     */
    public boolean submitPaper(Paper thePaper) throws NullPointerException {
    	
    	if (thePaper == null) {
    		throw new NullPointerException("Cannot submit null objects to a conference!");
    	}
    	
        if (papers.contains(thePaper)) {
            return true;
        }
        
        if (!isBeforeSubmissionDeadline(thePaper.getSubmissionDate())) {
            return false;
        }
        
        List<Author> authorsOfPaper = thePaper.getAuthors();
        
        for (Author a : authorsOfPaper) {
        	if (!authors.containsValue(a)) {
        		return false;
        	} else if (isAuthorAtPaperLimit(a)) {
                return false;
            }
        }
        
        for (Author a : authorsOfPaper) {
            authorsToPapers.get(a).add(thePaper);
        }
        
        papers.add(thePaper);
        
        return true;
    }
    
    /**
     * Removes a paper from the conference.
     * 
     * Preconditions:
     * 		thePaper is not null
     * 		thePaper is in this conference
     * 		the current date is before the deadline
     * 
     * Postconditions:
     * 		if thePaper is null, a null pointer exception will be thrown.
     * 		if thePaper is not in this conference, the method will return false
     * 			without removing any papers.
     * 		if the current date is after the deadline,  the method will return false
     * 			without removing any papers.
     * 		otherwise the method returns true and removes the given paper
     * 
     * @author Zachary Chandler
     * 
     * @param thePaper the paper
     * @throws NullPointerException if thePaper is null
     * @return if it successfully removes the paper
     */
    public boolean removePaper(Paper thePaper) throws NullPointerException {
    	if (thePaper == null) {
    		throw new NullPointerException("Cannot remove null papers from a Conference!");
    	}
    	
        if (!isBeforeSubmissionDeadline(new Date())) {
            return false;
        }
        
        if (!papers.contains(thePaper)) {
            return false;
        }
        
        List<Author> authorsOfPaper = thePaper.getAuthors();

        for (Author a : authorsOfPaper) {
            List<Paper> currentPapers = authorsToPapers.get(a);
            
            currentPapers.remove(thePaper);
        }
        
        papers.remove(thePaper);
        
        return true;
    }
    
    /**
     * Gets the papers an author submitted.
     * 
     * Preconditions:
     * 		theAuthor is not null.
     * 		theAuthor is in the conference.
     * 		
     * Postconditions:
     * 		if theAuthor is null, a NullPointerException is thrown.
     * 		if theAuthor is not in the conference, a null is returned.
     * 		if theAuthor does not have any papers, an empty List is returned.
     * 		otherwise the method returns a List of all papers by the author.
     * 
     * @author Zachary Chandler
     * @throws NullPointerException if theAuthor is null.
     * @param theAuthor the author to get papers for.
     * @return a list of papers by theAuthor.
     */
    public List<Paper> getPapers(Author theAuthor) throws NullPointerException {
    	if (theAuthor == null) {
    		throw new NullPointerException("Cannot getPapers with a null Author in a Conference!");
    	}
    	
    	if (authors.containsValue(theAuthor)) {
    		return new LinkedList<Paper>(authorsToPapers.get(theAuthor));
    	} else {
    		return null;    		
    	}
    	
    }
    
    /**
     * Returns the deadline for paper submission in the conference.
     * 
     * Postconditions:
     * 		if a deadline has not been set, then this method will return null.
     * 		if a deadline has been set, then this method will return another Date
     * 			at the same time of the deadline.
     * 
     * @author Zachary Chandler
     * 
     * @return the date of the deadline or null if no deadline is specified
     */
    public Date getDeadline() {
        return submissionDeadline == null ? null : (Date) submissionDeadline.clone();
    }
    
    /**
     * Creates a deadline for paper submission in a conference.
     * 
     * Preconditions:
     * 		theDeadline is not null
     * 
     * Postconditions:
     * 		if theDeadline is null, a NullPointerException is thrown.
     * 		the deadline of the conference will have the same time as theDeadline.
     * 
     * @author Zachary Chandler
     * @throws NullPointerException if theDeadline is null.
     * @param theDeadline the date of the deadline.
     */
    public void setDeadline(Date theDeadline) throws NullPointerException {
    	if (theDeadline == null) {
    		throw new NullPointerException("Cannot set the deadline of a Conference to null!");
    	}
    	
        submissionDeadline = (Date) theDeadline.clone();
    }
    
    /**
     * Returns the roles for a given user.
     * 
     * Preconditions:
     * 		theUser is not null
     * 		
     * Postconditions
     * 		if theUser is null, a NullPointerException is thrown.
     * 		if theUser does not currently any roles, an empty List is returned.
     * 		otherwise, a List of all roles for the user is returned.
     * 
     * @author Zachary Chandler
     * @throws NullPointerException if theUser is null
     * @param theUser self explanatory
     * @return the list of roles for the user
     */
    public List<Role> getRoles(String theUser) throws NullPointerException {
    	
    	if (theUser == null) {
    		throw new NullPointerException("Cannot get roles of a null User from a Conference!");
    	}
    	
        List<Role>  roles = new LinkedList<>();

        Role r = authors.get(theUser);
        if (r != null) {
            roles.add(r);
        }
        
        r = subProgramChairs.get(theUser);
        if (r != null) {
            roles.add(r);
        }
        
        r = reviewers.get(theUser);
        if (r != null) {
            roles.add(r);
        }
        
        return roles;
    }
    
    /**
     * Adds a reviewer to the conference.
     * 
     * Preconditions:
     * 		theUser is not null
     * 		theUser is not an empty string
     * 		theUser is not already a Reviewer
     * 
     * Postconditions
     * 		if theUser is null, a NullPointerException is thrown.
     * 		if theUser is an empty string, a NullPointerException is thrown.
     * 		it theUser is already a Reviewer, theUsers is not added as a Reviewer. 
     * 		otherwise, theUser is added as a Reviewer.		
     * 
     * @author Zachary Chandler
     * @throws NullPointerException if theUser is null or an empty string.
     * @param theUser the user being made a reviewer
     */
    public void addReviewer(String theUser) throws NullPointerException {
    	if (theUser == null) {
    		throw new NullPointerException("Cannot add null user names as Reviewers to Conferences!");
    	}
    	
    	if (theUser.equals("")) {
    		throw new NullPointerException("Cannot add empty user names as Reviewers to Conferences!");
    	}
    	
    	if (!reviewers.containsKey(theUser)) {
            reviewers.put(theUser, new Reviewer(theUser));            
        }
    }
    
    /**
     * Returns a list of reviewers for a given conference.
     * 
     * Postconditions:
     * 		if there are no reviewers, an empty List is returned
     * 		otherwise a List of all Reviewers is returned.
     * 
     * @author Zachary Chandler
     * 
     * @return list of reviewers
     */
    public List<Reviewer> getReviewers() {
        List<Reviewer> reviewerUsers = new LinkedList<>();
        
        for (String s : reviewers.keySet()) {
            reviewerUsers.add(reviewers.get(s));
        }
        
        return reviewerUsers;
    }

    /**
     * Adds a subprogram chair to the conference.
     * 
     * Preconditions:
     * 		theUser is not null
     * 		theUser is not an empty string
     * 		theUser is not already a SubProgramChair
     * 
     * Postconditions
     * 		if theUser is null, a NullPointerException is thrown.
     * 		if theUser is an empty string, a NullPointerException is thrown.
     * 		if theUser is already a SubProgramChair, then theUser is not added
     * 			as a SubProgramChair.
     * 		otherwise, theUser is added as a SubProgramChair.	
     * 
     * @author Zachary Chandler
     * 
     * @throws NullPointerException if theUser is null or an empty string.
     * @param theUser the user being made a subprogram chair
     */
    public void addSubprogramChair(String theUser) throws NullPointerException {
    	if (theUser == null) {
    		throw new NullPointerException("Cannot add null user names as SubProgramChair to Conferences!");
    	}
    	
    	if (theUser.equals("")) {
    		throw new NullPointerException("Cannot add empty user names as SubProgramChair to Conferences!");
    	}
    	
        if (!subProgramChairs.containsKey(theUser)) {
            subProgramChairs.put(theUser, new SubProgramChair(theUser));            
        }
    }
    
    /**
     * Adds an Author to the conference.
     * 
     * Preconditions:
     * 		theUser is not null
     * 		theUser is not an empty string
     * 		theUser is not already an Author
     * 
     * Postconditions
     * 		if theUser is null, a NullPointerException is thrown.
     * 		if theUser is an empty string, a NullPointerException is thrown.
     * 		if theUser is already an Author, then theUser is not added
     * 			as an Author.
     * 		otherwise, theUser is added as an Author.	
     * 
     * @author Zachary Chandler
     * 
     * @throws NullPointerException if theUser is null or an empty string.
     * @param theUser the user being made a subprogram chair
     */
    public void addAuthor(String theUser) throws NullPointerException {
    	if (theUser == null) {
    		throw new NullPointerException("Cannot add null user names as an Author to Conferences!");
    	}
    	
    	if (theUser.equals("")) {
    		throw new NullPointerException("Cannot add empty user names as an Author to Conferences!");
    	}
    	
        if (!authors.containsKey(theUser)) {
            Author a = new Author(theUser);
            authors.put(theUser, a);
            authorsToPapers.put(a, new LinkedList<Paper>());
        }
    }
    
    /**
     * Gets an Author in the conference, or null if 
     * the given user isn't an author.
     * 
     * Preconditions:
     * 		theUser is not null.
     * 		theUser is an Author in the Conference.
     * 
     * Postconditions
     * 		if theUser is null, a NullPointerException is thrown.
     * 		if theUser is not an Author in the Conference, a null is returned.
     * 		otherwise, the Author object associated with theUser will be returned.
     * 
     * @author Zachary Chandler
     * @throws NullPointerException if theUser is null.
     * @param theUser the user name.
     * @return an Author object or null.
     */
    public Author getAuthor(String theUser) throws NullPointerException {
    	if (theUser == null) {
    		throw new NullPointerException("Cannot get Authors from conference with null user names!");
    	}
    	
        return authors.get(theUser);
    }
    
    /**
     * Removes a subprogram chair from the conference.
     * 
     * Preconditions:
     * 		theUser is not null
     * 		theUser is a SubProgramChair in this Conference
     * 
     * Postconditions:
     * 		if theUser is null, a NullPointerException is thrown
     * 		if theUser is not a SubProgramChair in this Conference, nothing happens
     * 		otherwise the SubProgramChair associated with the given user name is removed
     * 
     * @author Zachary Chandler
     * @throws NullPointerException if theUser is null.
     * @param theUser the user being removed as a subprogram chair.
     */
    public void removeSubprogramChair(String theUser) throws NullPointerException {
    	if (theUser == null) {
    		throw new NullPointerException("Cannot remove null user names from being a SubProgramChair of a Conference!");
    	}
    	
        subProgramChairs.remove(theUser);
    }
    
    /**
     * Checks if the current date is past the deadline date.
     * 
     * Preconditions:
     * 		theTime is not null.
     * 
     * Postconditions:
     * 		if theTime is null, a NullPointerException is thrown.
     * 		if theTime is before the deadline, the method returns
     * 			true without effecting the Conference.
     * 		if theTime is after or on the deadline, the method returns
     * 			false without effecting the Conference.
     * 
     * @author Zachary Chandler
     * 
     * @param theTime the time that should be before the deadline.
     * @throws NullPointerException if theTime is null.
     * @return if theTime is before the deadline.
     */
    public boolean isBeforeSubmissionDeadline(Date theTime) throws NullPointerException {
    	if (theTime == null) {
    		throw new NullPointerException("Cannot compare null Dates in Conference.isBeforeSubmissionDeadline!");
    	}
    	
        return theTime.before(submissionDeadline);
    }
    
    /**
     * Checks if the author has reached the paper submission limit.
     * 
     * Preconditions:
     * 		theAuthor is not null.
     * 		theAuthor is in the conference.
     * 
     * Postconditions:
     * 		if theAuthor is null, a NullPointerException is thrown.
     * 		if theAuthor is not in the conference the method returns false.
     * 		otherwise the method returns if theAuthor is at the paper limit
     * 			of the conference.
     * 
     * @author Zachary Chandler
     * 
     * @param theAuthor the author.
     * @throws NullPointerException if theAuthor is null.
     * @return if the author is able to submit more papers.
     */
    public boolean isAuthorAtPaperLimit(Author theAuthor) throws NullPointerException {
    	if (theAuthor == null) {
    		throw new NullPointerException("Cannot check paper limit of a null Author in Conference!");
    	}
    	
    	if (authors.containsValue(theAuthor)) {
    		return authorsToPapers.get(theAuthor).size() >= AUTHOR_MAX_PAPERS;    		
    	} else {
    		return false;
    	}
    }
}
