package tests;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Paper;
import model.Reviewer;

/**
 * JUnit tests for Reviewer class
 * @author Ian Jury
 * @version 1.0
 */
public class ReviewerTest {
	/** limit for for paper assignment */
	private static final int ASSIGNED_PAPER_LIMIT = 8;
	
	/** Used to 'offset' paper limit so lower bound business rule can be checked.*/
	private static final int ASSIGNED_PAPER_LIMIT_OFFSET = 5;
	
	/** Reviewer object to be used as test subject */
	private Reviewer reviewerTestObject;
	
	/** File path for test paper object*/
	private Path filePathOfPaper;
	
	/** List of authors for paper object */
	private List<Author> listOfAuthorsOfPaper;
	
	/** Paper object to test proper assignment */
	private Paper paperObjectToCheckAssignment;
	
	/** Name of author for paper object*/
	private String nameOfThisReviewer = "JSmith";
	
	/** Title of paper for paper object*/
	private String titleOfPaper = "Test Title";
	
	/**
	 * Sets up each test.
	 */
	@Before
	public void setup() {
		reviewerTestObject = new Reviewer(nameOfThisReviewer);
		filePathOfPaper = null;
		listOfAuthorsOfPaper = new ArrayList<>();
		
		Author submitter = new Author("simplesubmitter");
		listOfAuthorsOfPaper.add(submitter);
		paperObjectToCheckAssignment =
				new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper, submitter);
		
	}
	
	/**
	 * Tests if the object user is the name of the author.
	 */
	@Test
	public void testGetUser() {
		assertEquals(reviewerTestObject.getUser(), nameOfThisReviewer);
	}
	
	/**
	 * Doesn't assign any paper to test if review number is valid
	 */
	@Test
	public void testUnassigned() {
		//check if empty
		assertEquals(reviewerTestObject.getNumberOfReviews(), 0);
		
	}	
	
	/**
	 * Assigns one paper and checks if assignment worked properly.
	 */
	@Test
	public void testPaperAssignedIfNotAuthorOrCoAuthor() {		
		//assign 1 paper and test
		reviewerTestObject.assign(paperObjectToCheckAssignment);
		assertEquals(reviewerTestObject.getNumberOfReviews(), 1);
		
	}
	/**
	 * Attempts to assign a paper to a reviewer that is the author.
	 * Should not work
	 */
	public void testPaperNotAssignedIfReviewerIsAuthor() {
		List<Author> reviewerIsAuthorInList = new ArrayList<>();
		reviewerIsAuthorInList.add(new Author(nameOfThisReviewer));
		Paper reviewerIsAuthorOfPaper = new Paper(filePathOfPaper, reviewerIsAuthorInList, titleOfPaper, null);
		//assign reviewer to paper they are author of
		reviewerTestObject.assign(reviewerIsAuthorOfPaper); 
		assertEquals(reviewerTestObject.getNumberOfReviews(), 0);
		
	}
	/**
	 * Attempts to assign a paper to a reviewer that is the coAuthor.
	 * Should not work
	 */
	public void testPaperNotAssignedIfReviewerIsCoAuthor() {
		List<Author> reviewerIsAuthorInList = new ArrayList<>();
		reviewerIsAuthorInList.add(new Author("This is the name of the main author"));
		reviewerIsAuthorInList.add(new Author(nameOfThisReviewer));
		Paper reviewerIsAuthorOfPaper = new Paper(filePathOfPaper, reviewerIsAuthorInList, titleOfPaper, null);
		//assign reviewer to paper they are author of
		reviewerTestObject.assign(reviewerIsAuthorOfPaper); 
		assertEquals(reviewerTestObject.getNumberOfReviews(), 0);
		
	}
	
	/**
	 * Tests if assignment isn't at limit if number of 
	 * papers assigned is well below the limit.
	 */
	public void testIsWellBelowPaperLimit() {
		for (int limit = 0; 
				limit < ASSIGNED_PAPER_LIMIT - ASSIGNED_PAPER_LIMIT_OFFSET; limit++) {
			//note the new Paper object. This was added to make sure that unique instances were used
			//instead of the same object n times. (same for next 2 methods)
			reviewerTestObject.assign(new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper, listOfAuthorsOfPaper.get(0)));
		}
		assertFalse(reviewerTestObject.isAtPaperLimit());
	}

	/**
	 * Tests if a paper limit for reviewer is valid when n-1 under limit(n).
	 */
	@Test
	public void testIsOneLessThanPaperLimit() {
		//assign 7 papers, make sure limit isn't reached
		for (int limit = 0; limit < ASSIGNED_PAPER_LIMIT - 1; limit++) {
			reviewerTestObject.assign(new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper, listOfAuthorsOfPaper.get(0)));
		}
		assertFalse(reviewerTestObject.isAtPaperLimit());
	}
	
	/**
	 * Tests if a paper limit for reviewer is valid when at limit.
	 */
	public void testIsOverPaperLimit() {
		//assign 8 papers, make sure limit is reached
		for (int limit = 0; limit < ASSIGNED_PAPER_LIMIT; limit++) {
			reviewerTestObject.assign(new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper, listOfAuthorsOfPaper.get(0)));
		}
		assertTrue(reviewerTestObject.isAtPaperLimit());
	}
}
