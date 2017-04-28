package model;

import java.io.Serializable;

/**
 * This class is responsible with implementing the
 * roles interface.
 * 
 * @author zachac
 */
public abstract class AbstractRole implements Role, Serializable, Comparable<AbstractRole> {
    
	/** Eclipse generated SVUID. */
    private static final long serialVersionUID = -4307407622136444656L;
    
    /** The user name of the user. */
    private String myName;
    
    /**
     * Creates a new role with the given user name.
     * @author Zachary Chandler
     * 
     * @param s the user name.
     */
    public AbstractRole(String s) {
        myName = s;
    }
    
    /**
     * Gets the user name of the one in that role.
     * @author Zachary Chandler
     * 
     * @return the user name.
     */
    public String getUser() {
        return myName;
    }

	@Override
	public int compareTo(AbstractRole o) {
		return myName.compareTo(o.myName);
	}
}
