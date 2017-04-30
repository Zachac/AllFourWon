package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Author;
import model.AbstractRole;
import model.Conference;

public class AuthorTest {

	/**
	 * Test author constructor.
	 */
	@Test
	public void testAuthorConstructor() {
		String authorName = "Brian";
		Author brian = new Author(authorName);
		
		assertTrue(brian.getName().equals(authorName));
		assertFalse(brian.login(authorName));
	}
	
	/**
	 * Test login information.
	 */
	@Test
	public void testLogin() {
		String loginInfo = "b41naosp";
		Author brian = new Author("Brian");
		
		assertFalse(brian.login(loginInfo));
		
		brian.setLogin(loginInfo);
		
		assertTrue(brian.login(loginInfo));
	}

}
