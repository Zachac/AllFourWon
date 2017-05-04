package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Reviewer class containing list of papers for the Reviewer to review.
 * 
 * @author Dmitriy Bliznyuk
 * @version 1.0
 *
 */
public class Reviewer extends AbstractRole {

    /**
     * The maximum number of papers that a Reviewer is allowed to review.
     */
    public static int REVIEW_LIMIT = 8;
    
    /**
     * The list of papers that have been reviewed by the Reviewer.
     */
    //private List<Paper> reviewedPapers;
    
    /**
     * The list of papers yet to be reviewed by the Reviewer.
     */
    private List<Paper> papersToBeReviewed;
    
    /**
     * Number of total number of papers assigned to the Reviewer.
     */
    private int numberOfReviews;

    /** Generated UID.*/
    private static final long serialVersionUID = -3892385185436691553L;

    /**
     * Constructor for the Reviewer class that sets of the Reviewer.
     * @param user the unique user identifier
     */
    public Reviewer(String user) {
       super(user);
       //reviewedPapers = new ArrayList<>();
       papersToBeReviewed = new ArrayList<>();
       numberOfReviews = 0;
    }

    /**
     * Assigns paper to reviewer.
     * @param p the Paper assigned to reviewer.
     * @return true if assignable, false otherwise.
     */
    public boolean assign(Paper p) {
        boolean authorIsDifferent = true;
        List<Author> authorList = p.getAuthors();
        //testing if the author name is
        for(int i = 0; i < authorList.size(); i++) {
            if(authorList.get(i).getUser().equals(this.getUser())) {
                authorIsDifferent = false;
            }
        }
        if (authorIsDifferent && !isAtPaperLimit()) {
            numberOfReviews++;
            papersToBeReviewed.add(p);
        }
        
        return authorIsDifferent && !isAtPaperLimit();
    }

    /**
     * Checks if reviewer is at review limit.
     * @return true if at paper limit, false otherwise.
     */
    public boolean isAtPaperLimit() {
        return numberOfReviews >= REVIEW_LIMIT;
    }

    /**
     * Finds out how many papers the Reviewer was assigned to.
     * @return int number of reviews the Reviewer was assigned to
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    /**
     * Gets the papers assigned to the reviewer that haven't been reviewed.
     * @author Zachary Chandler
     * @return a list of papers assigned to the reviewer that haven't been reviewed.
     */
    public List<Paper> getPapersToBeReviewed() {
        return new LinkedList<>(papersToBeReviewed);
    }

}
