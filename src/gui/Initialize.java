package gui;

import java.util.Set;
import java.util.TreeSet;

import model.Conference;
import model.ConferenceManager;
import serialization.SerializationHelper;

public class Initialize {

    public static void main(String[] args) {
        ConferenceManager s = new ConferenceManager();
        Set<String> users = new TreeSet<String>();
        
        Conference simpleConference = new Conference("ABC Conference");
        simpleConference.addAuthor("ZACHAC");
        
        s.addConference(simpleConference);
        users.add("ZACHAC");
        
        
        SerializationHelper.saveConferenceManager(s);
        SerializationHelper.saveUsers(users);
    }

}