package gui;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

import model.Conference;

/**
 * A class to keep track of the current users information. 
 * 
 * @author Zachary Chandler
 */
public class UserInfo {
	/** The current conference. */
	private Conference currentConference;
	
	/** Other users in the system. */
	private final Set<String> otherUsers;
	
	/** The output stream that will be displayed to the user. */
	public final PrintStream out;
	
	/** The input scanner that reads input from the user. */
	public final Scanner in;
	
	/** The user name of the user. */
	public final String username;
	
	/**
	 * Instantiate a UserInfo.
	 * 
	 * @param output the output stream to the user.
	 * @param input the input stream to the user.
	 * @param username the user name of the user.
	 * @param otherUsers the user names of everyone else in the system.
	 */
	public UserInfo(PrintStream output, Scanner input, String username,
			Set<String> otherUsers) {
		
		this.out = output;
		this.in = input;
		this.username = username;
		this.otherUsers = otherUsers;
		
		this.setCurrentConference(null);
	}
	
	/**
	 * @return the current conference this user is in.
	 */
	public Conference getCurrentConference() {
		return currentConference;
	}

	/**
	 * @param currentConference the current conference this user is in.
	 */
	public void setCurrentConference(Conference currentConference) {
		this.currentConference = currentConference;
	}
	
	/**
	 * Checks if otherUser is in the system with this user. 
	 * @param otherUser the user name of the other user.
	 */
	public void canSee(String otherUser) {
		otherUsers.contains(otherUser);
	}
}
