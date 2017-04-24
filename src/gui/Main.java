package gui;

import model.ConferenceManager;
import serialization.SerializationHelper;

public class Main {

	public static void main(String[] args) {
		ConferenceManager s = SerializationHelper.loadConferenceManager();
		
		if (s == null) {
			System.out.println("FAILED to load system, Now exiting.");
			return;
		}
		
		System.out.println("System loaded, now exiting.");
	}
	
	/*
	 * needed UI prompts
	 * 
	 * guide author to create paper,
	 *       which is then submitted
	 * 
	 * guide subprogram chair to display valid reviewers,
	 * 	 	 choose a reviewer,
	 *       and then attempt to add that reviewer to their paper
	 */
}
