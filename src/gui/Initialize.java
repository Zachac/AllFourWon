package gui;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import model.Conference;
import model.ConferenceManager;
import serialization.SerializationHelper;

/**
 * A class to setup a system that we can experiment with.
 * 
 * @author Zachary Chandler
 */
public class Initialize {
	
	public static void main(String[] args) {
		ConferenceManager cm = new ConferenceManager();
		Conference testConference = new Conference("Conference Name Here");
		Date deadline = new Date();
		
		//set the deadline 3 months from now.
		deadline.setTime(deadline.getTime() + 3 * 31 * 24 * 60 * 60 * 1000);
		testConference.setDeadline(deadline);
		cm.addConference(testConference);

		Set<String> users = new TreeSet<>();

		SerializationHelper.saveConferenceManager(cm);
		SerializationHelper.saveUsers(users);
	}
	
}
