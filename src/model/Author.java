package model;


/**
 * This class creates an author object that is used to submit papers.
 * @author Brian
 *
 */
public class Author extends AbstractRole {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4787057477135476176L;
	private String loginInfo;
	private String myName;
	
	/**
	 * Constructs an Author object given a name.
	 * @param name name of author
	 */
	public Author(String name) {
	    super(name);
	    myName = name;
	    loginInfo = null;
    }
	
	/**
	 * Placeholder login information setup. 
	 * @param login String used to login
	 */
	public void setLogin(String login) {
		loginInfo = login;
	}
	
	/**
	 * Simple placeholder login verification method.
	 * @param login login information
	 * @return if login was done successfully
	 */
	public boolean login(String login) {
		if(login.equals(loginInfo)) 
			return true;
		return false;
	}
	
	/**
	 * Gets the name of the author.
	 * @return authors name
	 */
	public String getName() {
		return myName;
	}

}
