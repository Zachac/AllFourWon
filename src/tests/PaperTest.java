package tests;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Author;
import model.Paper;

/**
 * JUnit tests for Paper class
 * @author Dmitriy Bliznyuk
 * @version 1.0
 */
public class PaperTest {

	/** Paper object to be used as test subject */
	private Paper paperTestObject;
	
	/** File path for test paper object*/
	private Path filePathOfPaper;
	
	/** List of authors for paper object */
	private List<Author> listOfAuthorsOfPaper;
	
	/** Title of paper for paper object*/
	private String titleOfPaper = "Example";
	
	@Before
	public void setUp() {
		filePathOfPaper = null;
		listOfAuthorsOfPaper = new ArrayList<>();
		listOfAuthorsOfPaper.add(new Author("James"));
		listOfAuthorsOfPaper.add(new Author("John"));
		
		paperTestObject = new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper, listOfAuthorsOfPaper.get(0));
	}
	
	/**
	 * Test that paper object returns correct author values.
	 */
	@Test
	public void getAuthorsTest() {
		List<Author> returnedAuthors = paperTestObject.getAuthors();
		
		assertTrue("Two authors should be returned", returnedAuthors.size() == 2);
		
		for (Author a : returnedAuthors) {
		    assertTrue(a.getUser().equals("James") || a.getUser().equals("John"));
		}
	}

	/**
	 * Test to see if paper returns correct title.
	 */
	@Test
	public void getTitleTest() {
		assertEquals(paperTestObject.getTitle(), "Example");
	}
	
	/**
	 * Test to see if paper returns correct filepath.
	 */
	@Test
	public void getDocumentPathTest() {
		assertEquals(paperTestObject.getDocumentPath(), filePathOfPaper);
	}
	
	/**
	 * Test to see if paper returns correct submission date.
	 */
	@Test
	public void getSubmissionDateTest() {
		Date testDate = new Date();
		//created date should be after this new testDate that was created
		testDate.setTime(testDate.getTime() + 1);//computers are way too fast though, not even a ms has passed -zach
		
		assertTrue(paperTestObject.getSubmissionDate().before(testDate));
	}
}