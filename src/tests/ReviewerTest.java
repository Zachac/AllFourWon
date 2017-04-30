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

	@Test
	public void testGetUser() {
		assertEquals(reviewerTestObject.getUser(), nameOfAnAuthor);
	}

	@Test
	public void testAssign() {
		//check if empty
		assertEquals(reviewerTestObject.getNumberOfReviews(), 0);
		//assign 1 paper and test
		reviewerTestObject.assign(paperObjectToCheckAssignment);
		
		//this test causes a null pointer exception - dmitriy
		//should i be testing if the author is same as reviewer? maybe thats why - dmitriy
		assertEquals(reviewerTestObject.getNumberOfReviews(), 1);
		
	}

	//i assume this test fails for the same reason as the one above - dmitriy
	@Test
	public void testIsAtPaperLimit() {

		//assign 7 papers, make sure limit isn't reached
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		assertFalse(reviewerTestObject.isAtPaperLimit());
		
		//assign one more paper - should now be at limit (8 papers)
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		assertTrue(reviewerTestObject.isAtPaperLimit());
		
	}

}
