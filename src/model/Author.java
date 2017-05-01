package model;


/**
 * This class creates an author object that is used to submit papers.
 * @author Brian
 *
 */
public class Author extends AbstractRole {


    /** Generated UID.*/
	private static final long serialVersionUID = 4787057477135476176L;
	
	/**
	 * Constructs an Author object given a user name.
	 * @param name user name of the author.
	 */
	public Author(String name) {
	    super(name);
    }
}
