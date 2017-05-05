package gui;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import model.Author;
import model.Paper;

public class AuthorActions {
	public static void submitPaper(UserInfo info) {
		System.out.println("--temporary flag for submitPaper");

		
	}
	
	public static void removePaper(UserInfo info) {
        System.out.println("--temporary flag for removePaper");
	}
	
	/**
	 * Allows logged in user to edit paper if they are the submitter.
	 * @author Ian Jury
	 * @param info
	 */
	public static void editPaper(UserInfo info) {
        System.out.println("--temporary flag for editPaper");
        
        //The location to send the output and input
        PrintStream output = info.out;
        Scanner input = info.in;
        
       //gets list of papers
        List<Paper> papersSubmittedByAuthor = info.getCurrentConference().getPapers(new Author (info.username));
        int command = 1;
        for (int idx = 0; idx < papersSubmittedByAuthor.size(); idx++) {
        	output.println(command + ". " + papersSubmittedByAuthor.get(idx).getTitle()); //prints the title and #
        	command++;		//increments command #
        }
        output.println("Enter the associated number of the paper you want to edit (or 0 to cancel): ");

        //gets user input and validates
        Integer choice = checkIfValidIntegerInput(input.nextLine());
        if (choice > papersSubmittedByAuthor.size() + 1 || choice == null) {
			info.out.println("Could not find choice");
		} 
		
        if (choice != 0) { //if the user didn't want to cancel, edit the paper
        	Paper paperToEdit = papersSubmittedByAuthor.get(choice);
        	Integer userDecision = 0;
        	do {
	        	output.println("Enter the associated number of the information you want to edit: ");
	        	output.println("CURRENTLY EDITING: " + paperToEdit.getTitle());
	        	output.println("0. Exit paper editor");
	        	output.println("1. Change file path");
	        	output.println("2. Change list of authors");
	        	output.println("3. Change title of paper");
	        	userDecision = checkIfValidIntegerInput(input.nextLine());
	        	
	        	//this code is really lame, going to fix it later
	        	if (userDecision.equals(1)) {
	        		
	        	} else if (userDecision.equals(2)) {
	        		
	        	} else if (userDecision.equals(3)) {
	        		
	        	}
	        	//checks if input is invalid. If so it re-prompts user
	        	else if (userDecision.equals(null)) { 
	        		output.println("Invalid input. Try again");
	        	}
        	} while(!userDecision.equals(0)); //while user doesn't want to exit
        }
        
        
        
	}
	/**
	 * Helper method to determine that the user entered a valid integer.
	 * @author Ian Jury
	 * @param userInput The string read from the input source
	 * @return null if input is invalid, otherwise it returns an integer (primitive)
	 */
	private static int checkIfValidIntegerInput(String userInput) {
		Integer choice;
		try {
			choice = Integer.parseInt(userInput);			
		} catch (NumberFormatException e) { 
			choice = null;
		}
		return choice;
	}
}
