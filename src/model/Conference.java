package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * This class is responsible for handling all the general
 * information in a conference. Which includes:
 * 		- Papers submitted
 * 		- roles and privileges
 * 		- the submission deadline
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
	
	/**
	 * Creates a new empty conference.
	 * @author Zachary Chandler
	 */
	public Conference() {
	    authorsToPapers = new TreeMap<>();
	    papers = new LinkedList<>();
        authors = new TreeMap<>();
        subProgramChairs = new TreeMap<>();
        reviewers = new TreeMap<>();
	    
	    submissionDeadline = null;
	}
	
	/**
	 * Adds a paper to the conference
	 * @author Zachary Chandler
	 * 
	 * @param p the paper
	 * @return if it successfully submits the paper
	 */
	public boolean submitPaper(Paper p) {
	    if (!isBeforeSubmissionDeadline(p.getSubmissionDate())) {
	        return false;
	    }
	    
	    List<Author> authorsOfPaper = p.getAuthors();
	    
	    for (Author a : authorsOfPaper) {
	        if (isAuthorAtPaperLimit(a)) {
	            return false;
	        }
	    }
	    
	    for (Author a : authorsOfPaper) {
	        authorsToPapers.get(a).add(p);
	    }
	    
	    papers.add(p);
	    
		return true;
	}
	
	/**
	 * Removes a paper from the conference.
	 * @author Zachary Chandler
	 * 
	 * @param p the paper
	 * @return if it successfully removes the paper
	 */
	public boolean removePaper(Paper p) {
	    if (isBeforeSubmissionDeadline(new Date())) {
	        return false;
	    }
	    
	    if (!papers.contains(p)) {
	        return false;
	    }
	    
	    List<Author> authorsOfPaper = p.getAuthors();

        for (Author a : authorsOfPaper) {
            List<Paper> currentPapers = authorsToPapers.get(a);
            
            
            currentPapers.remove(p);
        }
        
        papers.remove(p);
	    
		return true;
	}
	
	/**
	 * Gets the papers an author submitted.
	 * @author Zachary Chandler
	 * 
	 * @return a list of papers
	 */
	public List<Paper> getPapers(Author a) {
		return new LinkedList<Paper>(authorsToPapers.get(a));
	}
	
	/**
	 * Returns the deadline for paper submission in the conference.
	 * @author Zachary Chandler
	 * 
	 * @return the date of the deadline or null if no deadline is specified
	 */
	public Date getDeadline() {
		return submissionDeadline == null ? null : (Date) submissionDeadline.clone();
	}
	
	/**
	 * Creates a deadline for paper submission in a conference.
	 * @author Zachary Chandler
	 * 
	 * @param d the date of the deadline
	 */
	public void setDeadline(Date d) {
		submissionDeadline = (Date) d.clone();
	}
	
	/**
	 * Returns the roles for a given user.
	 * @author Zachary Chandler
	 * 
	 * @param user self explanatory
	 * @return the list of roles for the user
	 */
	public List<Role> getRoles(String user) {
	    List<Role>  roles = new LinkedList<>();

        Role r = authors.get(user);
        if (r != null) {
            roles.add(r);
        }
        
        r = subProgramChairs.get(user);
        if (r != null) {
            roles.add(r);
        }
	    
        r = reviewers.get(user);
        if (r != null) {
            roles.add(r);
        }
	    
		return null;
	}
	
	/**
	 * Adds a reviewer to the conference.
	 * @author Zachary Chandler
	 * 
	 * @param user the user being made a reviewer
	 */
	public void addReviewer(String user) {
		reviewers.put(user, new Reviewer(user));
	}
	
	/**
	 * Returns a list of reviewers for a given conference.
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
	 * @author Zachary Chandler
	 * 
     * @param user the user being made a subprogram chair
     */
    public void addSubprogramChair(String user) {
        if (!subProgramChairs.containsKey(user)) {
            subProgramChairs.put(user, new SubProgramChair(user));            
        }
    }
    
    /**
     * Adds an Author to the conference.
	 * @author Zachary Chandler
	 * 
     * @param user the user being made a subprogram chair
     */
    public void addAuthor(String user) {
        if (!authors.containsKey(user)) {
        	Author a = new Author(user);
            authors.put(user, a);
            authorsToPapers.put(a, new LinkedList<Paper>());
        }
    }
    
    /**
     * Gets an Author in the conference, or null if 
     * the given user isn't an author.
     * @author Zachary Chandler
     * 
     * @param user the user name.
     * @return an Author object or null.
     */
    public Author getAuthor(String user) {
        return authors.get(user);
    }
    
    /**
     * Removes a subprogram chair from the conference.
	 * @author Zachary Chandler
	 * 
     * @param user the user being removed as a subprogram chair
     */
    public void removeSubprogramChair(String user) {
        subProgramChairs.remove(user);
    }
	
	/**
	 * Checks if the current date is past the deadline date.
	 * @author Zachary Chandler
	 * 
	 * @return if papers are submittable
	 */
	public boolean isBeforeSubmissionDeadline(Date time) {
		return time.before(submissionDeadline);
	}
	
	/**
	 * Checks if the author has reached the paper submission limit.
	 * @author Zachary Chandler
	 * 
	 * @param a the author
	 * @return if the author is able to submit more papers
	 */
	public boolean isAuthorAtPaperLimit(Author a) {
		return authorsToPapers.get(a).size() >= AUTHOR_MAX_PAPERS;
	}
}
