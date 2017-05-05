package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   AuthorTest.class,
   ConferenceTests.class,
   PaperTest.class,
   ReviewerTest.class,
   SubProgramTest.class
}) 	

public class AllTests {

}
