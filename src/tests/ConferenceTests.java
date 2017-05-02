package tests;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import model.Author;
import model.Conference;
import model.Paper;
import model.Reviewer;
import model.Role;
import model.SubProgramChair;

public class ConferenceTests {
	
	/**
	 * A test for the conference constructor.
	 * @author Zachary Chandler
	 */
	@Test
	public void testConferenceConstrutor() {
		Conference emptyConference = new Conference();

		assertTrue(emptyConference.getDeadline() == null);
		assertTrue(emptyConference.getReviewers().size() == 0);
	}

    /**
     * A test for submitting a paper that was already submitted.
     * @author Zachary Chandler
     */
    @Test
    public void testSubmitPaper_SamePaperMoreThanOnce_ReturnTrueWithoutAffect() { 
        Conference simpleConference = new Conference();
        simpleConference.setDeadline(new Date(new Date().getTime() + 5000));
        simpleConference.addAuthor("zachac");
        
        Author zach = simpleConference.getAuthor("zachac");
        

        List<Author> authors = new LinkedList<Author>();
        authors.add(zach);

        Paper justZach1 = new Paper(null, authors, "Just Zach's Paper");
        assertTrue(simpleConference.submitPaper(justZach1));
        assertTrue(simpleConference.getPapers(zach).size() == 1);
        
        assertTrue(simpleConference.submitPaper(justZach1));
        assertTrue(simpleConference.getPapers(zach).size() == 1);
    }
    
    /**
     * A test for submitting a paper with only one Author.
     * @author Zachary Chandler
     */
    @Test
    public void testSubmitPaper_OneAuthorSubmitSixPapers_FailsAtSixthSubmition() {
		Conference simpleConference = new Conference();
		simpleConference.setDeadline(new Date(new Date().getTime() + 5000));
		simpleConference.addAuthor("zachac");

		Author zach = simpleConference.getAuthor("zachac");
		
		List<Author> authors = new LinkedList<Author>();
		authors.add(zach);
		
        assertTrue(simpleConference.submitPaper(new Paper(null, authors, "Just Zach's Paper")));
        assertTrue(simpleConference.submitPaper(new Paper(null, authors, "Just Zach's Paper")));
        assertTrue(simpleConference.submitPaper(new Paper(null, authors, "Just Zach's Paper")));
        assertTrue(simpleConference.submitPaper(new Paper(null, authors, "Just Zach's Paper")));
        assertTrue(simpleConference.submitPaper(new Paper(null, authors, "Just Zach's Paper")));  //5
        assertFalse(simpleConference.submitPaper(new Paper(null, authors, "Just Zach's Paper"))); //6
	}
	
	/**
	 * A test for submitting a paper with more than one Author.
     * @author Zachary Chandler
	 */
	@Test
	public void testSubmitPaper_MoreThanOneAuthorSubmitSixPapers_FailsAtSixthSubmition() {
		Conference simpleConference = new Conference();
		simpleConference.setDeadline(new Date(new Date().getTime() + 5000));
		simpleConference.addAuthor("zachac");
		simpleConference.addAuthor("dimabliz");
		simpleConference.addAuthor("ianjury");
		simpleConference.addAuthor("briang5");
		simpleConference.addAuthor("kvn96");

		Author zach = simpleConference.getAuthor("zachac");
		Author dimtriy = simpleConference.getAuthor("dimabliz");
		Author ian = simpleConference.getAuthor("ianjury");
		Author brian = simpleConference.getAuthor("briang5");
		Author kevin = simpleConference.getAuthor("kvn96");
		
		List<Author> authors = new LinkedList<Author>();
		authors.add(zach);
		authors.add(dimtriy);
		authors.add(ian);
		authors.add(brian);
		authors.add(kevin);

		Paper fiveAuthors1 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors1));
		assertTrue(simpleConference.getPapers(zach).size() 		== 1);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 1);
		assertTrue(simpleConference.getPapers(ian).size() 		== 1);
		assertTrue(simpleConference.getPapers(brian).size()		== 1);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 1);

		Paper fiveAuthors2 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors2));
		assertTrue(simpleConference.getPapers(zach).size() 		== 2);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 2);
		assertTrue(simpleConference.getPapers(ian).size() 		== 2);
		assertTrue(simpleConference.getPapers(brian).size()		== 2);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 2);

		Paper fiveAuthors3 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors3));
		assertTrue(simpleConference.getPapers(zach).size() 		== 3);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 3);
		assertTrue(simpleConference.getPapers(ian).size() 		== 3);
		assertTrue(simpleConference.getPapers(brian).size()		== 3);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 3);

		Paper fiveAuthors4 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors4));
		assertTrue(simpleConference.getPapers(zach).size() 		== 4);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 4);
		assertTrue(simpleConference.getPapers(ian).size() 		== 4);
		assertTrue(simpleConference.getPapers(brian).size()		== 4);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 4);

		Paper fiveAuthors5 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors5));
		assertTrue(simpleConference.getPapers(zach).size() 		== 5);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 5);
		assertTrue(simpleConference.getPapers(ian).size() 		== 5);
		assertTrue(simpleConference.getPapers(brian).size()		== 5);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 5);
		
		Paper fiveAuthors6 = new Paper(null, authors, "Just a Paper");
		assertFalse(simpleConference.submitPaper(fiveAuthors6));
		assertTrue(simpleConference.getPapers(zach).size() 		== 5);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 5);
		assertTrue(simpleConference.getPapers(ian).size() 		== 5);
		assertTrue(simpleConference.getPapers(brian).size()		== 5);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 5);
	}
	

    
    /**
     * Tests if paper removal method works properly.
     * @author Ian Jury
     */
    @Test
    public void testPaperRemoval() {
        //set up conference and paper
        Conference conferenceToTestPaperRemoval = new Conference();
        conferenceToTestPaperRemoval.setDeadline(new Date(new Date().getTime() + 50000));
        
        conferenceToTestPaperRemoval.addAuthor("AuthorName");
        
        List<Author> authors = new LinkedList<Author>();
        authors.add(conferenceToTestPaperRemoval.getAuthor("AuthorName"));
        Path filePathOfPaper = Paths.get("temp/file/path");
        Paper paperToTestRemoval = new Paper(filePathOfPaper, authors, "Paper name");
        
        //submit and remove
        assertTrue(conferenceToTestPaperRemoval.submitPaper(paperToTestRemoval));
        assertTrue(conferenceToTestPaperRemoval.removePaper(paperToTestRemoval));
        
        //should be 0 if removed successfully 
        assertEquals(conferenceToTestPaperRemoval.getPapers(authors.get(0)).size(), 0);
    }
    
    /**
     * Tests if getter for papers in conference works properly.
     * @author Ian Jury
     */
    @Test
    public void testGetPapers() {
        Conference conferenceToTestGetPapers = new Conference();
        conferenceToTestGetPapers.setDeadline(new Date(new Date().getTime() + 5000));
        
        conferenceToTestGetPapers.addAuthor("AuthorName");
        Author testAuthor = conferenceToTestGetPapers.getAuthor("AuthorName");
        List<Author> authors = new LinkedList<Author>();
        authors.add(testAuthor);
        
        Path filePathOfPaper = Paths.get("temp/file/path");
        Paper paperToTestGetPapers = new Paper(filePathOfPaper, authors, "Paper name");
        
        conferenceToTestGetPapers.submitPaper(paperToTestGetPapers);
        
        List<Paper> papers = conferenceToTestGetPapers.getPapers(testAuthor);
        
        assertTrue(papers.size() == 1);
        assertEquals(papers.get(0), paperToTestGetPapers);
    }
	
	/**
     * Tests if boolean variable for the submission limit is working.
     * @author Kevin Nguyen
     */
    @Test
    public void testAuthorPaperLimit() {

        Conference conferencePaperLimit = new Conference();
        conferencePaperLimit.setDeadline(new Date(new Date().getTime() + 50000));
        conferencePaperLimit.addAuthor("sam"); 
        Author sam = conferencePaperLimit.getAuthor("sam"); //add author takes in a string and creates the author object from there.

        List<Author> authors = new LinkedList<Author>();
        authors.add(sam);
        assertFalse(conferencePaperLimit.isAuthorAtPaperLimit(sam));
        
        Path filePathOfPaper = Paths.get("temp/file/path");
        Paper mockPaperTest = new Paper(filePathOfPaper, authors, "AwesomePaper");
        assertTrue(conferencePaperLimit.submitPaper(mockPaperTest)); // papers are added to conferences
        assertFalse(conferencePaperLimit.isAuthorAtPaperLimit(sam));
        
        mockPaperTest = new Paper(filePathOfPaper, authors, "AwesomePaper"); // I don't think there is a need for unique parameters
        assertTrue(conferencePaperLimit.submitPaper(mockPaperTest));
        assertFalse(conferencePaperLimit.isAuthorAtPaperLimit(sam));

        mockPaperTest = new Paper(filePathOfPaper, authors, "AwesomePaper");
        assertTrue(conferencePaperLimit.submitPaper(mockPaperTest));
        assertFalse(conferencePaperLimit.isAuthorAtPaperLimit(sam));

        mockPaperTest = new Paper(filePathOfPaper, authors, "AwesomePaper");
        assertTrue(conferencePaperLimit.submitPaper(mockPaperTest));
        assertFalse(conferencePaperLimit.isAuthorAtPaperLimit(sam));

        mockPaperTest = new Paper(filePathOfPaper, authors, "AwesomePaper");
        assertTrue(conferencePaperLimit.submitPaper(mockPaperTest));
        assertTrue(conferencePaperLimit.isAuthorAtPaperLimit(sam));
        
        mockPaperTest = new Paper(filePathOfPaper, authors, "AwesomePaper");
        assertFalse(conferencePaperLimit.submitPaper(mockPaperTest));
        assertTrue(conferencePaperLimit.isAuthorAtPaperLimit(sam));
    }
	
	/**
     * Tests if roles are correctly identified in the conference.
     * Not sure if testing right since its a tree map I presume.
     * @author Kevin Nguyen
     */
    @Test
    public void testGetRoles() {
        Conference conRole = new Conference();
        
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
		Conference testConference = new Conference();
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
	 * Testing the addAuthor method and getAuthor method in Conference class.
	 * @author Dmitriy Bliznyuk
	 */
	@Test
	public void testAddGetAuthor() {
		Conference testConference = new Conference();
		testConference.addAuthor("johndoe");
		testConference.addAuthor("janedoe");
		testConference.addAuthor("johnnyMcJohnnyface");
		
		Author authorOne = testConference.getAuthor("johndoe");
		assertEquals(authorOne.getUser(), "johndoe");
		
		Author authorTwo = testConference.getAuthor("janedoe");
		assertEquals(authorTwo.getUser(), "janedoe");
		
		Author authorThree = testConference.getAuthor("johnnyMcJohnnyface");
		assertEquals(authorThree.getUser(), "johnnyMcJohnnyface");
	}

    /**
     * Testing if the isAuthorAtPaperLimit returns the correct boolean value when
     * author submits the allowed amount of papers.
     * @author Dmitriy Bliznyuk
     */
    @Test
    public void testIsAuthorAtPaperLimit() {
        Conference testConference = new Conference();
        testConference.setDeadline(new Date(new Date().getTime() + 50000));
        
        Path filePathOfPaper = Paths.get("temp/file/path");
        List<Author> listOfAuthorsOfPaper = new ArrayList<>();
        testConference.addAuthor("James");
        Author testAuthor = testConference.getAuthor("James");
        listOfAuthorsOfPaper.add(testAuthor);
        
        String firstPaper = "ExampleOne";
        String secondPaper = "ExampleTwo";
        String thirdPaper = "ExampleThree";
        String fourthPaper = "ExampleFour";
        String fifthPaper = "ExampleFive";
        
        Paper paperTestOne = new Paper(filePathOfPaper, listOfAuthorsOfPaper, firstPaper);
        Paper paperTestTwo = new Paper(filePathOfPaper, listOfAuthorsOfPaper, secondPaper);
        Paper paperTestThree = new Paper(filePathOfPaper, listOfAuthorsOfPaper, thirdPaper);
        Paper paperTestFour = new Paper(filePathOfPaper, listOfAuthorsOfPaper, fourthPaper);
        Paper paperTestFive = new Paper(filePathOfPaper, listOfAuthorsOfPaper, fifthPaper);
        
        //author hasn't submitted a single paper (should return false)
        assertFalse(testConference.isAuthorAtPaperLimit(testAuthor));
        
        testConference.submitPaper(paperTestOne);
        testConference.submitPaper(paperTestTwo);
        testConference.submitPaper(paperTestThree);
        testConference.submitPaper(paperTestFour);
        
        //author has submitted 4 papers (should return false)
        assertFalse(testConference.isAuthorAtPaperLimit(testAuthor));
        
        testConference.submitPaper(paperTestFive);
        
        //author has submitted 5 papers (should return true)
        assertTrue(testConference.isAuthorAtPaperLimit(testAuthor));
    }
    
    /**
     * Testing deadline business rule.
     * @author Zachary Chandler
     */
    @Test
    public void testIsBeforeSubmissionDeadlineBeforeSubmissionDeadline() {
        Conference deadlineConference = new Conference();
        Date time = new Date();
        
        deadlineConference.setDeadline(time);
        time.setTime(time.getTime() - (24 * 60 * 60 * 1000));
        assertTrue(deadlineConference.isBeforeSubmissionDeadline(time));
    }
    
    /**
     * Testing deadline business rule.
     * @author Zachary Chandler
     */
    @Test
    public void testIsBeforeSubmissionDeadlineJustBeforeSubmissionDeadline() {
        Conference deadlineConference = new Conference();
        Date time = new Date();
        
        deadlineConference.setDeadline(time);
        time.setTime(time.getTime() - 1);
        assertTrue(deadlineConference.isBeforeSubmissionDeadline(time));
    }
    
    /**
     * Testing deadline business rule.
     * @author Zachary Chandler
     */
    @Test
    public void testIsBeforeSubmissionDeadlineJustAfterSubmissionDeadline() {
        Conference deadlineConference = new Conference();
        Date time = new Date();
        
        deadlineConference.setDeadline(time);
        time.setTime(time.getTime());
        assertFalse(deadlineConference.isBeforeSubmissionDeadline(time));
    }
    
    /**
     * Testing deadline business rule.
     * @author Zachary Chandler
     */
    @Test
    public void testIsBeforeSubmissionDeadlineAfterSubmissionDeadline() {
        Conference deadlineConference = new Conference();
        Date time = new Date();
        
        deadlineConference.setDeadline(time);
        time.setTime(time.getTime() + (24 * 60 * 60 * 1000));
        assertFalse(deadlineConference.isBeforeSubmissionDeadline(time));
    }
}
