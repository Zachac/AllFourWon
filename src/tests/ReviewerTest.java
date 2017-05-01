package tests;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
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
	/** Reviewer object to be used as test subject */
	private Reviewer reviewerTestObject;
	
	/** File path for test paper object*/
	private Path filePathOfPaper;
	
	/** List of authors for paper object */
	private List<Author> listOfAuthorsOfPaper;
	
	/** Paper object to test reviewer limit */
	private Paper paperObjectToFillReviewerLimit;
	
	/** Paper object to test proper assignment */
	private Paper paperObjectToCheckAssignment;
	
	/** Name of author for paper object*/
	private String nameOfAnAuthor = "JSmith";
	
	/** Title of paper for paper object*/
	private String titleOfPaper = "Test Title";
	
	/** limit for for paper assignment */
	private int paperLimit = 8;
	
	@Before
	public void setup() {
		reviewerTestObject = new Reviewer(nameOfAnAuthor);
		filePathOfPaper = Paths.get("temp/file/path");
		listOfAuthorsOfPaper = new ArrayList<>();
		paperObjectToFillReviewerLimit = 
				new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper);
		paperObjectToCheckAssignment =
				new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper);
		
	}
	/**
	 * Tests if the object user is the name of the author.
	 */
	@Test
	public void testGetUser() {
		assertEquals(reviewerTestObject.getUser(), nameOfAnAuthor);
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
	public void testAssigned() {		
		//assign 1 paper and test
		reviewerTestObject.assign(paperObjectToCheckAssignment);
		assertEquals(reviewerTestObject.getNumberOfReviews(), 1);
		
	}

	/**
	 * Tests if a paper limit for reviewer is valid when under limit.
	 */
	@Test
	public void testIsAtPaperLimit() {
		//assign 7 papers, make sure limit isn't reached
		for (int limit = 0; limit < paperLimit - 1; limit++) {
			reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		}
		assertFalse(reviewerTestObject.isAtPaperLimit());
	}
	/**
	 * Tests if a paper limit for reviewer is valid when over limit.
	 */
	public void testIsOverPaperLimit() {
		//assign 7 papers, make sure limit isn't reached
		for (int limit = 0; limit < paperLimit; limit++) {
			reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		}
		assertTrue(reviewerTestObject.isAtPaperLimit());
	}
}
