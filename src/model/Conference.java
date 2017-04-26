package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Conference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239987612704413287L;

	/**
	 * Adds a paper to the conference
	 * @param p the paper
	 * @return if it successfully submits the paper
	 */
	public boolean submitPaper(Paper p) {
		return false;
	}
	
	/**
	 * Removes a paper from the conference.
	 * @param p the paper
	 * @return if it successfully removes the paper
	 */
	public boolean removePaper(Paper p) {
		return false;
	}
	
	/**
	 * Gets the papers an author submitted.
	 * @return a list of papers
	 */
	public List<Paper> getPapers(Author a) {
		return null;
	}
	
	/**
	 * Returns the deadline for paper submission in the conference.
	 * @return the date of the deadline
	 */
	public Date getDeadline() {
		return null;
	}
	
	/**
	 * Creates a deadline for paper submission in a conference.
	 * @param d the date of the deadline
	 */
	public void setDeadline(Date d) {
		
	}
	
	/**
	 * Returns the roles for a given user.
	 * @param user self explanatory
	 * @return the list of roles for the user
	 */
	public List<Role> getRoles(String user) {
		return null;
	}
	
	/**
	 * Adds a reviewer to the conference.
	 * @param user the user being made a reviewer
	 */
	public void addReviewer(String user) {
		
	}
	
	/**
	 * Returns a list of reviewers for a given conference.
	 * @return list of reviewers
	 */
	public List<String> getReviewers() {
		return null;
	}
	
	/**
	 * Adds a subprogram chair to the conference.
	 * @param user the user being made a subprogram chair
	 */
	public void addSubprogramChair(String user) {
		
	}
	
	/**
	 * Checks if the current date is past the deadline date.
	 * @return if papers are submittable
	 */
	public boolean isPastSubmissionDeadline() {
		return false;
	}
	
	/**
	 * Checks if the author has reached the paper submission limit.
	 * @param a the author
	 * @return if the author is able to submit more papers
	 */
	public boolean isAuthorPaperLimit(Author a) {
		return false;
	}
}
