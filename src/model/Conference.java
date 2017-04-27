package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Conference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239987612704413287L;
	
	private static final int AUTHOR_MAX_PAPERS = 5;

	private List<Paper> papers;
    private TreeMap<Author, List<Paper>> authorsToPapers;
    private TreeMap<String, Author> authors;
    private TreeMap<String, SubProgramChair> subProgramChairs;
    private TreeMap<String, Reviewer> reviewers;
	
	private Date deadline;
	
	public Conference() {
	    authorsToPapers = new TreeMap<>();
	    papers = new LinkedList<>();
        authors = new TreeMap<>();
        subProgramChairs = new TreeMap<>();
        reviewers = new TreeMap<>();
	    
	    deadline = null;
	}
	
	
	
	/**
	 * Adds a paper to the conference
	 * @param p the paper
	 * @return if it successfully submits the paper
	 */
	public boolean submitPaper(Paper p) {
	    if (isPastSubmissionDeadline()) {
	        return false;
	    }
	    
	    List<Author> authorsOfPaper = new LinkedList<>();
	    
	    for (String s : p.getAuthors()) {
	        Author a = authors.get(s);
	        
	        if (a == null) {
	            a = new Author(s);
	            authors.put(s, a);
	        }

            authorsOfPaper.add(a);
	    }
	    
	    for (Author a : authorsOfPaper) {
	        if (isAuthorPaperLimit(a)) {
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
	 * @param p the paper
	 * @return if it successfully removes the paper
	 */
	public boolean removePaper(Paper p) {
	    if (isPastSubmissionDeadline()) {
	        return false;
	    }
	    
	    if (!papers.contains(p)) {
	        return false;
	    }
	    
	    
	    List<Author> authorsOfPaper = new LinkedList<>();
        
        for (String s : p.getAuthors()) {
            Author a = authors.get(s);
            
            if (a == null) {
                throw new NullPointerException("Unfound author, this shouldn't be possible.");
            }

            authorsOfPaper.add(a);
        }
	    

        for (Author a : authorsOfPaper) {
            List<Paper> currentPapers = authorsToPapers.get(a);
            
            
            currentPapers.remove(p);
            
            if (currentPapers.isEmpty()) {
                authors.remove(a.getUser());
                authorsToPapers.remove(a);
            }
            
        }
        
        papers.remove(p);
	    
		return true;
	}
	
	/**
	 * Gets the papers an author submitted.
	 * @return a list of papers
	 */
	public List<Paper> getPapers(Author a) {
		return authorsToPapers.get(a);
	}
	
	/**
	 * Returns the deadline for paper submission in the conference.
	 * @return the date of the deadline or null if no deadline is specified
	 */
	public Date getDeadline() {
		return deadline == null ? null : (Date) deadline.clone();
	}
	
	/**
	 * Creates a deadline for paper submission in a conference.
	 * @param d the date of the deadline
	 */
	public void setDeadline(Date d) {
		deadline = (Date) d.clone();
	}
	
	/**
	 * Returns the roles for a given user.
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
	 * @param user the user being made a reviewer
	 */
	public void addReviewer(String user) {
		reviewers.put(user, new Reviewer(user));
	}
	
	/**
	 * Returns a list of reviewers for a given conference.
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
     * @param user the user being made a subprogram chair
     */
    public void addSubprogramChair(String user) {
        if (!subProgramChairs.containsKey(user)) {
            subProgramChairs.put(user, new SubProgramChair(user));            
        }
    }
    
    /**
     * Removes a subprogram chair from the conference.
     * @param user the user being removed as a subprogram chair
     */
    public void removeSubprogramChair(String user) {
        subProgramChairs.remove(user);
    }
	
	/**
	 * Checks if the current date is past the deadline date.
	 * @return if papers are submittable
	 */
	public boolean isPastSubmissionDeadline() {
		return new Date().before(deadline);
	}
	
	/**
	 * Checks if the author has reached the paper submission limit.
	 * @param a the author
	 * @return if the author is able to submit more papers
	 */
	public boolean isAuthorPaperLimit(Author a) {
		return authorsToPapers.get(a).size() <= AUTHOR_MAX_PAPERS;
	}
}
