package tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import model.Author;
import model.Conference;
import model.Paper;

public class ConferenceTests {
	
	/**
	 * A test for the conference constructor.
	 */
	@Test
	public void testConferenceConstrutor() {
		Conference emptyConference = new Conference();

		assertTrue(emptyConference.getDeadline() == null);
		assertTrue(emptyConference.getReviewers().size() == 0);
	}

	/**
	 * A test for submitting a paper with only one Author.
	 */
	@Test
	public void testPaperSubmissionOneAuthor() {
		Conference simpleConference = new Conference();
		simpleConference.setDeadline(new Date(new Date().getTime() + 5000));
		simpleConference.addAuthor("zachac");

		Author zach = simpleConference.getAuthor("zachac");
		
		List<Author> authors = new LinkedList<Author>();
		authors.add(zach);

		Paper justZach1 = new Paper(null, authors, "Just Zach's Paper");
		assertTrue(simpleConference.submitPaper(justZach1));
		assertTrue(simpleConference.getPapers(zach).size() == 1);

		Paper justZach2 = new Paper(null, authors, "Just Zach's Paper");
		assertTrue(simpleConference.submitPaper(justZach2));
		assertTrue(simpleConference.getPapers(zach).size() == 2);

		Paper justZach3 = new Paper(null, authors, "Just Zach's Paper");
		assertTrue(simpleConference.submitPaper(justZach3));
		assertTrue(simpleConference.getPapers(zach).size() == 3);

		Paper justZach4 = new Paper(null, authors, "Just Zach's Paper");
		assertTrue(simpleConference.submitPaper(justZach4));
		assertTrue(simpleConference.getPapers(zach).size() == 4);

		Paper justZach5 = new Paper(null, authors, "Just Zach's Paper");
		assertTrue(simpleConference.submitPaper(justZach5));
		assertTrue(simpleConference.getPapers(zach).size() == 5);
		
		Paper justZach6 = new Paper(null, authors, "Just Zach's Paper");
		assertFalse(simpleConference.submitPaper(justZach6));
		assertTrue(simpleConference.getPapers(zach).size() == 5);
	}
	
	/**
	 * A test for submitting a paper with more than one Author.
	 */
	@Test
	public void testPaperSubmissionMoreThanOneAuthor() {
		Conference simpleConference = new Conference();
		simpleConference.setDeadline(new Date(new Date().getTime() + 5000));
		simpleConference.addAuthor("zachac");
		simpleConference.addAuthor("dimabliz");
		simpleConference.addAuthor("ianjury");
		simpleConference.addAuthor("briang5");
		simpleConference.addAuthor("kvn96");

		Author zach = simpleConference.getAuthor("zachac");
		Author dimtriy = simpleConference.getAuthor("dimabliz");
		Author ian = simpleConference.getAuthor("ianjury");
		Author brian = simpleConference.getAuthor("briang5");
		Author kevin = simpleConference.getAuthor("kvn96");
		
		List<Author> authors = new LinkedList<Author>();
		authors.add(zach);
		authors.add(dimtriy);
		authors.add(ian);
		authors.add(brian);
		authors.add(kevin);

		Paper fiveAuthors1 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors1));
		assertTrue(simpleConference.getPapers(zach).size() 		== 1);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 1);
		assertTrue(simpleConference.getPapers(ian).size() 		== 1);
		assertTrue(simpleConference.getPapers(brian).size()		== 1);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 1);

		Paper fiveAuthors2 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors2));
		assertTrue(simpleConference.getPapers(zach).size() 		== 2);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 2);
		assertTrue(simpleConference.getPapers(ian).size() 		== 2);
		assertTrue(simpleConference.getPapers(brian).size()		== 2);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 2);

		Paper fiveAuthors3 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors3));
		assertTrue(simpleConference.getPapers(zach).size() 		== 3);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 3);
		assertTrue(simpleConference.getPapers(ian).size() 		== 3);
		assertTrue(simpleConference.getPapers(brian).size()		== 3);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 3);

		Paper fiveAuthors4 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors4));
		assertTrue(simpleConference.getPapers(zach).size() 		== 4);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 4);
		assertTrue(simpleConference.getPapers(ian).size() 		== 4);
		assertTrue(simpleConference.getPapers(brian).size()		== 4);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 4);

		Paper fiveAuthors5 = new Paper(null, authors, "Just a Paper");
		assertTrue(simpleConference.submitPaper(fiveAuthors5));
		assertTrue(simpleConference.getPapers(zach).size() 		== 5);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 5);
		assertTrue(simpleConference.getPapers(ian).size() 		== 5);
		assertTrue(simpleConference.getPapers(brian).size()		== 5);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 5);
		
		Paper fiveAuthors6 = new Paper(null, authors, "Just a Paper");
		assertFalse(simpleConference.submitPaper(fiveAuthors6));
		assertTrue(simpleConference.getPapers(zach).size() 		== 5);
		assertTrue(simpleConference.getPapers(dimtriy).size() 	== 5);
		assertTrue(simpleConference.getPapers(ian).size() 		== 5);
		assertTrue(simpleConference.getPapers(brian).size()		== 5);
		assertTrue(simpleConference.getPapers(kevin).size() 	== 5);
	}

}
