package gui;

import java.util.Set;

import model.ConferenceManager;
import serialization.SerializationHelper;

public class Main {

	public static void main(String[] args) {
		ConferenceManager s = SerializationHelper.loadConferenceManager();
		Set<String> users = SerializationHelper.loadUsers();
		
		
		if (s == null || users == null) {
			System.out.println("FAILED to load system, Now exiting.");
			return;
		}
		
		System.out.println("Welcome to COMPANY_NAME conferences!");
		
		UserInfo info = null;
		while(info == null) {
			info = ConsoleGUI.login(users);
		}
		
		while (ConsoleGUI.mainMenu(info, s));

		SerializationHelper.saveConferenceManager(s);
		SerializationHelper.saveUsers(users);
	}
}
