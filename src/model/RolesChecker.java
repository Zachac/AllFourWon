package model;

import java.util.List;

/**
 * A class to keep track of roles in a list. Only one instance of a role is supported at this time.
 * 
 * @author Zachary Chandler
 */
public class RolesChecker {
    
    /** The initial list of roles. */
    private List<Role> roles;
    
    /** The Author role. */
    private Author authorRole;
    
    /** The Reviewer role. */
    private Reviewer reviewerRole;
    
    /** The SubProgramChair role. */
    private SubProgramChair subProgramChairRole;
    
    /** If the list has an Author role. */
    public final boolean isAuthor;
    
    /** If the list has a Reviewer role. */
    public final boolean isReviewer;
    
    /** If the list has a SubProgramChair. */
    public final boolean isSubProgramChair;
    
    /**
     * Creates a roles checker with the values in the given list.
     * 
     * @param roles the roles to use.
     */
    public RolesChecker(List<Role> roles) {
        this.roles = roles;
        
        boolean isAuthor = false;
        boolean isReviewer = false;
        boolean isSubProgramChair = false;
        
        for (Role r : this.roles) {
            if (r instanceof Author) {
                isAuthor = true;
                this.authorRole = (Author) r;
            } else if (r instanceof Reviewer) {
                isReviewer = true;
                this.reviewerRole = (Reviewer) r;
            } else if (r instanceof SubProgramChair){
                isSubProgramChair = true;
                this.subProgramChairRole = (SubProgramChair) r;
            } else {
                throw new ClassCastException("Unkown role!");
            }
        }
        
        this.isAuthor = isAuthor;
        this.isReviewer = isReviewer;
        this.isSubProgramChair = isSubProgramChair;
    }


    /**
     * @return the authorRole
     */
    public Author getAuthorRole() {
        return authorRole;
    }


    /**
     * @return the reviewerRole
     */
    public Reviewer getReviewerRole() {
        return reviewerRole;
    }

    /**
     * @return the subProgramChairRole
     */
    public SubProgramChair getSubProgramChairRole() {
        return subProgramChairRole;
    }
}
