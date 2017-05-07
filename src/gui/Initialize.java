package gui;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Author;
import model.Conference;
import model.ConferenceManager;
import model.Paper;
import model.Reviewer;
import model.RolesChecker;
import model.SubProgramChair;
import serialization.SerializationHelper;

public class Initialize {

    public static void main(String[] args) {
        ConferenceManager s = new ConferenceManager();
        Set<String> users = new TreeSet<String>();
        users.add("ZACHAC");
        users.add("IANJ");
        users.add("KVN96");
        users.add("DIMABLIZ");
        users.add("BRIANG5");
        
        s.addConference(getBeforeDeadlineConference());
        s.addConference(getAfterDeadlineConference());        
        
        SerializationHelper.saveConferenceManager(s);
        SerializationHelper.saveUsers(users);
    }
    
    public static Conference getBeforeDeadlineConference() {
        Date aMonthFromNow = new Date(1497787200000L);

        Conference beforeDeadlineConference = new Conference("ABC Conference");
        beforeDeadlineConference.setDeadline(aMonthFromNow);

        beforeDeadlineConference.addAuthor("IANJ");
        Author ian2 = beforeDeadlineConference.getAuthor("IANJ");
        List<Author> listForBeforeDeadlineAuthors = new LinkedList<>();
        listForBeforeDeadlineAuthors.add(ian2);
        beforeDeadlineConference.submitPaper(new Paper(null,  listForBeforeDeadlineAuthors, 
                "Fake Titles in Early Education", ian2));
        beforeDeadlineConference.submitPaper(new Paper(null,  listForBeforeDeadlineAuthors, 
                "More Things to Think About", ian2));
        beforeDeadlineConference.submitPaper(new Paper(null,  listForBeforeDeadlineAuthors, 
                "Education and Age: Correlation?", ian2));
        beforeDeadlineConference.submitPaper(new Paper(null,  listForBeforeDeadlineAuthors, 
                "Another Review on Education and Things", ian2));
        
        return beforeDeadlineConference;
    }
    
    public static Conference getAfterDeadlineConference() {
        Conference afterDeadlineConference = new Conference("XYZ Conference");
        afterDeadlineConference.setDeadline(new Date(1497787200000L));
        afterDeadlineConference.addAuthor("ZACHAC");
        afterDeadlineConference.addSubprogramChair("KVN96");
        afterDeadlineConference.addReviewer("DIMABLIZ");
        afterDeadlineConference.addReviewer("BRIANG5");
        
        SubProgramChair kevin = new RolesChecker(afterDeadlineConference.getRoles("KVN96")).getSubProgramChairRole();
        Reviewer dima = new RolesChecker(afterDeadlineConference.getRoles("DIMABLIZ")).getReviewerRole();
        Reviewer brian = new RolesChecker(afterDeadlineConference.getRoles("BRIANG5")).getReviewerRole();
        Author zach = afterDeadlineConference.getAuthor("ZACHAC");
        
        List<Author> authors = new LinkedList<>();
        authors.add(zach);
        
        Paper p = new Paper(null, authors, "Constructing Fake Titles", zach);
        afterDeadlineConference.submitPaper(p);
        kevin.addPaper(p);
        

        p = new Paper(null, authors, "Detecting Fake Titles", zach);
        afterDeadlineConference.submitPaper(p);
        brian.assign(p);
        
        p = new Paper(null, authors, "Analyzing Fake Titles", zach);
        afterDeadlineConference.submitPaper(p);
        kevin.addPaper(p);
        dima.assign(p);
        
        afterDeadlineConference.addAuthor("IANJ");
        Author ian = afterDeadlineConference.getAuthor("IANJ");
        authors.add(ian);
        
        afterDeadlineConference.submitPaper(new Paper(null, authors, "Parallel Title Conventions", ian));
        
        afterDeadlineConference.setDeadline(new Date(1487419200000L));
        
        return afterDeadlineConference;
    }

}