/**
 * 
 */
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
import model.SubProgramChair;

/**
 * JUnit test for the subprogram chair.
 * @author Kevin Nguyen
 *
 */
public class SubProgramTest {
	/** The subprogram chair test user **/
	private SubProgramChair testSub;
	/** The name of the SubP. Chair */
	private String subName = "Boo";
	/** List of Papers that are assigned to the subprogram chair */
	//private List<Paper> paperList;
	/** List of authors for paper object */
	private List<Author> listOfAuthorsForPaper;
	/** A paper to add for testing. */
	private Paper testPaper;
	/** temporary file path to make the paper object */
	private Path paperFilePath;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//paperList = new ArrayList<>();
		paperFilePath = Paths.get("temp/file/path");
		listOfAuthorsForPaper = new ArrayList<>();
		listOfAuthorsForPaper.add(new Author("Ian"));
		listOfAuthorsForPaper.add(new Author("Dimitry"));
		//Creating the paper.
		testPaper = new Paper(paperFilePath, listOfAuthorsForPaper, "AwesomePaper");
	}
	
	@Test
	public void constructSubProgramChairTest() {
		testSub = new SubProgramChair(null);
		//test the null subprogram chair
		assertNull(testSub.getUser());
		testSub = new SubProgramChair(subName);
		//Seeing if the user is correctly identified as a subprogram chair
		assertEquals(testSub.getUser(), "Boo");
		/**
		 * testSub = new SubProgramChair(subName);
		 * Currently need a way to check for unique users.
		 */
	}
	/**
	 * Testing if the method gets the paper correctly
	 * Adding duplicate papers to see the results and different ones.
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void getPaperTest() throws CloneNotSupportedException {
		//this thorws a null pointer for some reason.
        testSub = new SubProgramChair("username");
		testSub.addPaper(testPaper);
		
		List<Paper> papers = testSub.getPapers();
		assertTrue(papers.size() == 1);
		assertEquals(testSub.getPapers().get(0).getTitle(), testPaper.getTitle());
		
		//forgot how to assert equals all the strings
		testSub.addPaper(testPaper);
		testSub.addPaper(testPaper);
		
		papers = testSub.getPapers();
		assertTrue(papers.size() == 1);
		
		Paper testPaper2 = new Paper(testPaper.getDocumentPath(), testPaper.getAuthors(), "another name");
		testSub.addPaper(testPaper2);
		papers = testSub.getPapers();
        assertTrue(papers.size() == 2);
		        
        for (Paper p : papers) {
            assertTrue(p == testPaper || p == testPaper2);
        }

	}
	/**
	 * Checks if the papers are added correctly.
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void AddPaperToSubTest() throws CloneNotSupportedException {
		//This is throwing a null paper for some reason.
	    testSub = new SubProgramChair("username");
		testSub.addPaper(testPaper);
		
		//Using the get method to verify the adding worked.
		List<Paper> papers = testSub.getPapers();
		assertTrue(papers.size() == 1);
		assertEquals(papers.get(0), testPaper);

        Paper testPaper2 = new Paper(testPaper.getDocumentPath(), testPaper.getAuthors(), "another name");
        
		//Add multiple papers
		testSub.addPaper(testPaper2);

        papers = testSub.getPapers();
        assertTrue(papers.size() == 2);
        
        
        for (Paper p : papers) {
            assertTrue(p == testPaper || p == testPaper2);
        }
	}
	
}
