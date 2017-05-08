package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConferenceManager implements Serializable {
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -6646261571061047225L;
	
	private List<Conference> conferences;
	
	/**
	 * Constructor that creates the ConferenceManager object
	 */
	public ConferenceManager () {
	    conferences = new LinkedList<Conference>();
	}
	
	/**
	 * Adds a conference to a list.
	 * 
	 * @param c conference object.
	 * @throws NullPointerException if the given conference is null.
	 * 
	 * if the conference is already being managed,
	 * the result will be true, and the function will not effect anything.
	 * 
	 * Postconditions:
	 * 		-adds a Conference to the ConferenceManager
	 * 
	 * @return if the conference was successfully added.
	 */
	public boolean addConference(Conference c) throws NullPointerException {
	    if (c == null) {
	        throw new NullPointerException("Cannot add null conferences!");
	    }
	    
	    if (conferences.contains(c)) {
	        return true;
	    }
	    
		return conferences.add(c);
	}
	
	/**
	 * Removes a conference from a list.
	 * 
	 * if the given conference is not being managed,
	 * the method will return false without effecting anything
	 * 
	 * Postconditions:
	 * 		-removes a Conference from the ConferenceManager
	 * 
	 * @param c conference object
     * @throws NullPointerException if the given conference is null.
	 * @return if the conference was successfully removed
	 */
	public boolean removeConference(Conference c) throws NullPointerException {
	    if (c == null) {
            throw new NullPointerException("Cannot remove null conference!");
        }
	    
		return conferences.remove(c);
	}
	
	/**
	 * Returns the list of conferences.
	 * @return list of conferences
	 */
	public List<Conference> getConferences() {
		return new ArrayList<Conference>(conferences);
	}
	
}
