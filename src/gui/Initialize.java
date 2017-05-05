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
import serialization.SerializationHelper;

public class Initialize {

    public static void main(String[] args) {
        ConferenceManager s = new ConferenceManager();
        Set<String> users = new TreeSet<String>();
        Date now = new Date();
        Date aMonthFromNow = new Date(now.getTime() + (31L *  24 * 60 * 60 * 1000));
        Date aMonthBeforeNow = new Date(now.getTime() - (31L *  24 * 60 * 60 * 1000));

        Conference beforeDeadlineConference = new Conference("ABC Conference");
        beforeDeadlineConference.setDeadline(aMonthFromNow);
        
        Conference afterDeadlineConference = new Conference("XYZ Conference");
        afterDeadlineConference.setDeadline(aMonthFromNow);
        afterDeadlineConference.addAuthor("ZACHAC");
        
        Author zach = afterDeadlineConference.getAuthor("ZACHAC");
        List<Author> authors = new LinkedList<>();
        authors.add(zach);
        
        afterDeadlineConference.submitPaper(new Paper(null, authors, "Constructing Fake Titles", zach));
        afterDeadlineConference.submitPaper(new Paper(null, authors, "Detecting Fake Titles", zach));
        afterDeadlineConference.submitPaper(new Paper(null, authors, "Analyzing Fake Titles", zach));
        
        afterDeadlineConference.addAuthor("IANJ");
        Author ian = afterDeadlineConference.getAuthor("IANJ");
        authors.add(ian);
        
        afterDeadlineConference.submitPaper(new Paper(null, authors, "Parallel Title Conventions", ian));
        
        afterDeadlineConference.setDeadline(aMonthBeforeNow);
        
        s.addConference(beforeDeadlineConference);
        s.addConference(afterDeadlineConference);
        
        users.add("ZACHAC");
        users.add("IANJ");
        
        SerializationHelper.saveConferenceManager(s);
        SerializationHelper.saveUsers(users);
    }

}