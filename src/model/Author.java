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
	 * Constructs an Author object given a name.
	 * @param name name of author
	 */
	public Author(String name) {
	    super(name);
    }
}
