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
		filePathOfPaper = Paths.get("temp/file/path");
		listOfAuthorsOfPaper = new ArrayList<>();
		
		paperTestObject = new Paper(filePathOfPaper, listOfAuthorsOfPaper, titleOfPaper);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
