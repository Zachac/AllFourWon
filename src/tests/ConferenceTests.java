package tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Conference;
import model.Paper;
import model.Reviewer;
import model.Role;
import model.SubProgramChair;

public class ConferenceTests {
	
	private static final int DEADLINE = 5000;
	private static final int MAX_PAPERS = 5;
	
	Conference emptyConference;
	Conference simpleConference;
	Conference deadlineConference;
	Date deadline;
	Date before;
	Date justBefore;
	Date justAfter;
	Date after;
	Author zach;
	Author brian;
	Author dmitriy;
	Author ian;
	Author kevin;
	List<Author> oneAuthor;
	List<Author> fiveAuthors;
	Paper oneAuthorPaper;
	Paper oneAuthorPaperSecondPaper;
	Paper oneAuthorPaperThirdPaper;
	Paper oneAuthorPaperFourthPaper;
	Paper oneAuthorPaperFifthPaper;
	Paper oneAuthorPaperSixthPaper;
	Paper fiveAuthorPaper;
	Paper fiveAuthorPaperSecondPaper;
	Paper fiveAuthorPaperThirdPaper;
	Paper fiveAuthorPaperFourthPaper;
	Paper fiveAuthorPaperFifthPaper;
	Paper fiveAuthorPaperSixthPaper;
	
	@Before
	public void setUp() {
		
		emptyConference = new Conference(null);
		
		deadline = new Date();
		
		deadlineConference = new Conference(null);
		deadlineConference.setDeadline(deadline);
		
		before = new Date();
		before.setTime(deadline.getTime() - 10000);
				

		justBefore = new Date();
		justBefore.setTime(deadline.getTime() - 1);
		

		justAfter = new Date(deadline.getTime());
		
		after = new Date(deadline.getTime() + 10000);
		
		simpleConference = new Conference(null);
		simpleConference.setDeadline(new Date(new Date().getTime() + DEADLINE));
		simpleConference.addAuthor("zachac");
		simpleConference.addAuthor("dimabliz");
		simpleConference.addAuthor("ianjury");
		simpleConference.addAuthor("briang5");
		simpleConference.addAuthor("kvn96");
		
		zach = simpleConference.getAuthor("zachac");
		brian = simpleConference.getAuthor("briang5");
		dmitriy = simpleConference.getAuthor("dimabliz");
		ian = simpleConference.getAuthor("ianjury");
		kevin = simpleConference.getAuthor("kvn96");
		
		oneAuthor = new LinkedList<Author>();
		oneAuthor.add(zach);
		
		fiveAuthors = new LinkedList<Author>();
		fiveAuthors.add(zach);
		fiveAuthors.add(brian);
		fiveAuthors.add(ian);
		fiveAuthors.add(kevin);
		fiveAuthors.add(dmitriy);
		
		oneAuthorPaper = new Paper(null, oneAuthor, "One Author Title", zach);
		oneAuthorPaperSecondPaper = new Paper(null, oneAuthor, "One Author Title", brian);
		oneAuthorPaperThirdPaper = new Paper(null, oneAuthor, "One Author Title", brian);
		oneAuthorPaperFourthPaper = new Paper(null, oneAuthor, "One Author Title", ian);
		oneAuthorPaperFifthPaper = new Paper(null, oneAuthor, "One Author Title", kevin);
		oneAuthorPaperSixthPaper = new Paper(null, oneAuthor, "One Author Title", dmitriy);
		
		fiveAuthorPaper = new Paper(null, fiveAuthors, "Five Authors Title", zach);
		fiveAuthorPaperSecondPaper = new Paper(null, fiveAuthors, "Five Authors Title", zach);
		fiveAuthorPaperThirdPaper = new Paper(null, fiveAuthors, "Five Authors Title", zach);
		fiveAuthorPaperFourthPaper = new Paper(null, fiveAuthors, "Five Authors Title", zach);
		fiveAuthorPaperFifthPaper = new Paper(null, fiveAuthors, "Five Authors Title", ian);
		fiveAuthorPaperSixthPaper = new Paper(null, fiveAuthors, "Five Authors Title", brian);
		
		
	}
	
	/**
	 * A test for the conference constructor.
	 * @author Zachary Chandler, Brian Geving
	 */
	@Test
	public void testConferenceConstrutor() {
		assertTrue("No deadline expected.", emptyConference.getDeadline() == null);
		assertTrue("No reviewers expected.", emptyConference.getReviewers().size() == 0);
	}

    /**
     * A test for submitting a paper that was already submitted.
     * @author Zachary Chandler, Brian Geving
     */
    @Test
    public void testSubmitPaper_SamePaperMoreThanOnce_ReturnTrueWithoutAffect() { 
       
        assertTrue("Paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaper));
        assertTrue("Author should have 1 total papers submitted.", simpleConference.getPapers(zach).size() == 1);
        
        assertTrue("Paper should succesfully submit.", simpleConference.submitPaper(oneAuthorPaper));
        assertTrue("Author should still have 1 total papers submitted.", simpleConference.getPapers(zach).size() == 1);
    }
    
    /**
     * A test for submitting a paper with only one Author.
     * Submitting more than the MAX_PAPERS should not be allowed.
     * @author Zachary Chandler, Brian Geving
     */
    @Test
    public void testSubmitPaper_OneAuthorSubmitSixPapers_FailsAtSixthSubmition() {
		
        assertTrue("Paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaper));
        assertTrue("Second paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaperSecondPaper));
        assertTrue("Third Paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaperThirdPaper));
        assertTrue("Fourth Paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaperFourthPaper));
        assertTrue("Fifth paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaperFifthPaper));  //MAX_PAPERS
        assertFalse("Sixth paper should not submit.", simpleConference.submitPaper(oneAuthorPaperSixthPaper)); //over MAX_PAPERS
        
        assertTrue("Author should have MAX_PAPERS amount.", simpleConference.getPapers(zach).size() == MAX_PAPERS);
	}
	
	/**
	 * A test for submitting a paper with more than one Author.
	 * All authors should have the size of their getPapers increase.
	 * More than the MAX_PAPERS should not be allowed.
     * @author Zachary Chandler, Brian Geving
	 */
	@Test
	public void testSubmitPaper_MoreThanOneAuthorSubmitSixPapers_FailsAtSixthSubmition() {
		assertTrue(simpleConference.submitPaper(fiveAuthorPaper));
		assertTrue(simpleConference.getPapers(zach).size() == 1);
		assertTrue(simpleConference.getPapers(brian).size() == 1);
		assertTrue(simpleConference.getPapers(ian).size() == 1);
		assertTrue(simpleConference.getPapers(kevin).size() == 1);
		assertTrue(simpleConference.getPapers(dmitriy).size() == 1);
		
		assertTrue(simpleConference.submitPaper(fiveAuthorPaperSecondPaper));
		assertTrue(simpleConference.submitPaper(fiveAuthorPaperThirdPaper));
		assertTrue(simpleConference.submitPaper(fiveAuthorPaperFourthPaper));
		assertTrue(simpleConference.submitPaper(fiveAuthorPaperFifthPaper)); //MAX_PAPERS
		assertFalse(simpleConference.submitPaper(fiveAuthorPaperSixthPaper)); //over MAX_PAPERS
		
		assertTrue(simpleConference.getPapers(zach).size() == MAX_PAPERS);
		assertTrue(simpleConference.getPapers(brian).size() == MAX_PAPERS);
		assertTrue(simpleConference.getPapers(ian).size() == MAX_PAPERS);
		assertTrue(simpleConference.getPapers(kevin).size() == MAX_PAPERS);
		assertTrue(simpleConference.getPapers(dmitriy).size() == MAX_PAPERS);
		
		
	}
	

    
    /**
     * Tests if paper removal method works properly.
     * Removing a paper should both remove it from the conference and decrement the amount of papers
     * sent for that author.
     * @author Ian Jury, Brian Geving
     */
    @Test
    public void testPaperRemoval() {
        
        //submit and remove
        assertTrue("Paper should successfully submit.", simpleConference.submitPaper(oneAuthorPaper));
        assertTrue("Paper should successfully remove.", simpleConference.removePaper(oneAuthorPaper));
        
        //should be 0 if removed successfully 
        assertEquals("Author should have no papers submitted.", simpleConference.getPapers(zach).size(), 0);
    }
    
	
	/**
     * Tests if roles are correctly identified in the conference.
     * Not sure if testing right since its a tree map I presume.
     * @author Kevin Nguyen
     */
    @Test
    public void testGetRoles() {
        Conference conRole = new Conference(null);
        
        conRole.addAuthor("Ian"); 
        conRole.addReviewer("Ian");
        
        conRole.addSubprogramChair("Zach");
        
        conRole.addAuthor("Dimitry");
        conRole.addSubprogramChair("Dimitry");
        conRole.addReviewer("Dimitry");

        List<Role> roles = conRole.getRoles("Ian");
        boolean foundAuthor = false;
        boolean foundReviewer = false;
        for (Role r : roles) {
            if (!foundAuthor && r instanceof Author) {
                foundAuthor = true;
            }
            if (!foundReviewer && r instanceof Reviewer) {
                foundReviewer = true;
            } 
        }
        assertTrue(foundAuthor);
        assertTrue(foundReviewer);
        

        roles = conRole.getRoles("Zach");
        boolean foundSubProgramChair = false;
        for (Role r : roles) {
            if (!foundSubProgramChair && r instanceof SubProgramChair) {
                foundSubProgramChair = true;
            }
        }
        assertTrue(foundSubProgramChair);
        

        roles = conRole.getRoles("Dimitry");
        foundSubProgramChair = false;
        foundAuthor = false;
        foundReviewer = false;
        for (Role r : roles) {
            if (!foundSubProgramChair && r instanceof SubProgramChair) {
                foundSubProgramChair = true;
            }
            if (!foundAuthor && r instanceof Author) {
                foundAuthor = true;
            }
            if (!foundReviewer && r instanceof Reviewer) {
                foundReviewer = true;
            }
        }
        assertTrue(foundSubProgramChair);
        assertTrue(foundAuthor);
        assertTrue(foundReviewer);
        
    }
	/**
	 * Testing the addReviewer method and the getReviewer method in Conference class.
	 * @author Dmitriy Bliznyuk
	 */
	@Test
	public void testAddGetReviewer() {
		Conference testConference = new Conference(null);
		testConference.addReviewer("johndoe");
		testConference.addReviewer("janedoe");
		testConference.addReviewer("johnnyMcJohnnyface");
		
		List<Reviewer> reviewerList = testConference.getReviewers();
		
		assertTrue(reviewerList.size() == 3);
		
		for (Reviewer r : reviewerList) {
		    assertTrue(r.getUser().equals("johndoe") || r.getUser().equals("janedoe") || r.getUser().equals("johnnyMcJohnnyface"));
		}
	}
	
    
    /**
     * Testing submitting a paper before the deadline.
     * @author Zachary Chandler, Brian Geving
     */
    @Test
    public void testIsBeforeSubmissionDeadlineBeforeSubmissionDeadline() {
        assertTrue("Should be before submission deadline.", deadlineConference.isBeforeSubmissionDeadline(before));
    }
    
    /**
     * Testing submitting a paper just before the deadline.
     * @author Zachary Chandler, Brian Geving
     */
    @Test
    public void testIsBeforeSubmissionDeadlineJustBeforeSubmissionDeadline() {
    	assertTrue("Should be just before submission deadline.", deadlineConference.isBeforeSubmissionDeadline(justBefore));
    }
    
    /**
     * Testing submitting a paper just after the deadline.
     * @author Zachary Chandler, Brian Geving
     */
    @Test
    public void testIsBeforeSubmissionDeadlineJustAfterSubmissionDeadline() {
    	assertFalse("Should be just after submission deadline.", deadlineConference.isBeforeSubmissionDeadline(justAfter));
    }
    
    /**
     * Testing submitting a paper after the deadline
     * @author Zachary Chandler, Brian Geving
     */
    @Test
    public void testIsBeforeSubmissionDeadlineAfterSubmissionDeadline() {
    	assertFalse("Should be after submission deadline.", deadlineConference.isBeforeSubmissionDeadline(after));
    }
}
