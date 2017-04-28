package model;

import java.util.ArrayList;
import java.util.List;

public class Reviewer extends AbstractRole {
	
	private List<Paper> reviewedPapers = new ArrayList<>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3892385185436691553L;
	
	public Reviewer(String user) {
       super(user);
    }
	
	/**
	 * Assigns paper to reviewer.
	 * @param p the Paper assigned to reviewer.
	 * @return true if assignable, false otherwise.
	 */
	public boolean assign(Paper p) {
		//increment reviews
		return false;
	}
	
	/**
	 * Checks if reviewer is at review limit.
	 * @return true if at paper limit, false otherwise.
	 */
	public boolean isAtPaperLimit() {
		return false;
	}

}
