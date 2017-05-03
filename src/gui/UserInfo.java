package gui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import model.Conference;

/**
 * A class to keep track of the current users information. 
 * 
 * @author Zachary Chandler
 */
public class UserInfo {
	/** The current conference. */
	private Conference currentConference;
	
	/** The output stream that will be displayed to the user. */
	public final PrintStream output;
	
	/** The input scanner that reads input from the user. */
	public final Scanner input;
	
	/** The user name of the user. */
	public final String username;	
	
	/**
	 * Instantiate a UserInfo.
	 * 
	 * @param output the output stream to the user.
	 * @param inStream the input stream to the user.
	 * @param username the user name of the user.
	 */
	public UserInfo(PrintStream output, InputStream inStream, String username) {
		this.output = output;
		this.input = new Scanner(inStream);
		this.username = username;
		
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
}
