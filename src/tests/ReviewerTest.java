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
 *
 */
public class ReviewerTest {
	
	private Reviewer reviewerTestObject;
	
	@Before
	public void setup() {
		reviewerTestObject = new Reviewer("JSmith");
	}

	@Test
	public void testGetUser() {
		assertEquals(reviewerTestObject.getUser(), "JSmith");
	}

	@Test
	public void testAssign() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIsAtPaperLimit() {
		Path filePathOfPaper = Paths.get("temp/file/path");
		List<Author> listOfAuthorsOfPaper = new ArrayList<>();
		
		Paper paperObjectToFillReviewerLimit = 
				new Paper(filePathOfPaper, listOfAuthorsOfPaper, "theTitle");
		//assign 4 papers, make sure limit isn't reached
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		assertFalse(reviewerTestObject.isAtPaperLimit());
		
		//assign one more paper - should now be at limit (5 papers)
		reviewerTestObject.assign(paperObjectToFillReviewerLimit);
		assertTrue(reviewerTestObject.isAtPaperLimit());
		
	}

}
