package model;

import java.io.Serializable;

public class Reviewer implements Role, Serializable {
	//#of papers reviewed
	/**
	 * 
	 */
	private static final long serialVersionUID = -3892385185436691553L;

	@Override
	public String getUser() {
		return null;
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
