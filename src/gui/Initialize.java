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
        dima.assign(p);
        

        p = new Paper(null, authors, "Detecting Fake Titles", zach);
        afterDeadlineConference.submitPaper(p);
        brian.assign(p);
        dima.assign(p);
        
        p = new Paper(null, authors, "Analyzing Fake Titles", zach);
        afterDeadlineConference.submitPaper(p);
        kevin.addPaper(p);
        dima.assign(p);
        
        p = new Paper(null, authors, "More Stuff on Fake Titles", zach);
        kevin.addPaper(p);
        dima.assign(p);
        
        afterDeadlineConference.addAuthor("IANJ");
        Author ian = afterDeadlineConference.getAuthor("IANJ");
       // authors.add(ian);
        List<Author> authors2 = new LinkedList<>();
        authors2.add(ian);
        Paper p2 = new Paper(null, authors, "Parallel Title Conventions", ian);
        Paper p3 = new Paper(null, authors, "Another Review", ian);
        Paper p4 = new Paper(null, authors, "A Review", ian);
        Paper p5 = new Paper(null, authors, "Some Analyzation", ian);
        Paper p6 = new Paper(null, authors, "Some more Analyzation", ian);
        
        afterDeadlineConference.addAuthor("BRIANG5");
        authors.add(afterDeadlineConference.getAuthor("BRIANG5"));
        Paper p7 = new Paper(null, authors, "A Papers Title", zach);
        
        afterDeadlineConference.submitPaper(p2);
        afterDeadlineConference.submitPaper(p3);
        afterDeadlineConference.submitPaper(p4);
        afterDeadlineConference.submitPaper(p5);
        afterDeadlineConference.submitPaper(p6);
        afterDeadlineConference.submitPaper(p7);
        kevin.addPaper(p2);
        kevin.addPaper(p3);
        kevin.addPaper(p4);
        kevin.addPaper(p5);
        kevin.addPaper(p6);
        kevin.addPaper(p7);
        dima.assign(p2);
        dima.assign(p3);
        dima.assign(p4);
        dima.assign(p5);
        //dima.assign(p6);
        
        
        afterDeadlineConference.setDeadline(new Date(1487419200000L));
        
        return afterDeadlineConference;
    }

}