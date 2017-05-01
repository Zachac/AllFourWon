package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Author;

public class AuthorTest {

	/**
	 * Test author constructor.
	 * @author Brian
	 */
	@Test
	public void testAuthorConstructor() {
		String authorName = "Brian";
		Author brian = new Author(authorName);
		
		assertTrue(brian.getUser().equals(authorName));
	}

}
