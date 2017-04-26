package model;

import java.io.Serializable;
import java.util.List;

public class ConferenceManager implements Serializable {
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -6646261571061047225L;

	/**
	 * Adds a conference to a list.
	 * @param c conference object
	 * @return if the conference was successfully added
	 */
	boolean addConference(Conference c) {
		return false;
	}
	
	/**
	 * Removes a conference from a list.
	 * @param c conference object
	 * @return if the conference was successfully removed
	 */
	boolean removeConference(Conference c) {
		return false;
	}
	
	/**
	 * Returns the list of conferences.
	 * @return list of conferences
	 */
	List<Conference> getConferences() {
		return null;
	}
	
}
