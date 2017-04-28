package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Reviewer;

/**
 * JUnit tests for Reviewer class
 * @author Ian Jury
 *
 */
public class ReviewerTest {
	
	private static int PAPER_LIMIT = 5;
	
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
		fail("Not yet implemented");
	}

	@Test
	public void testIsAtPaperLimit() {
		fail("Not yet implemented");
	}

}
