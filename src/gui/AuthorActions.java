package gui;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.Author;
import model.Conference;
import model.Paper;
import serialization.SerializationHelper;

public class AuthorActions extends Throwable {
	private static final long serialVersionUID = 1L;

	/**
	 * This adds the paper to the conference after asking the user for the
	 * information associated to it. Method name due to change since it's a
	 * duplicate of the conference submitPaper method.
	 * 
	 * @author Kevin Nguyen
	 * @param info
	 */
	public static void submitPaper(UserInfo info) {
		PrintStream output = info.out;
		Scanner input = info.in;
		Conference currentConference = info.getCurrentConference();
		Set<String> users = SerializationHelper.loadUsers();
		List<Author> theCoAuthors = new LinkedList<>();
		Author currentAuthor = currentConference.getAuthor(info.username);
		if (currentAuthor == null) {
		    currentConference.addAuthor(info.username);
		    currentAuthor = currentConference.getAuthor(info.username);
		    
		    if (currentAuthor == null) {
		        throw new IllegalStateException("Error, Conference didn't return the author that was added.");
		    }
		}
		
		theCoAuthors.add(currentAuthor);
		
		output.print("Enter the file path of the paper:");
		String filePath = input.nextLine();
		
		while (!isValidPath(filePath)) {
			// I need to make a option where the user can exit this and go back
			// to the conference screen.
			System.out.println("The file could not be found from the designated file path, please try again.");
	        output.print("Enter the file path of the paper:");
			filePath = input.nextLine();
		}
		
		output.print("Enter the title of the paper:");
		String paperTitle = input.nextLine();
		
		Integer choice = -1;
		do {
			// This will be done possibly by checkmarking in the future.
			// For the terminal would it be better to simply have a for loop of
			// the other users and have the user input the number/index?
		    output.println();
			output.println("0: Add a Co-Author to the paper (by username).");
			output.println("1: If there are no Co-authors or none left to add.");//TODO could be better worded
			
			// This will possibly already be shown in the gui in the future. I
			// think we need a way to show the real names aswell.
			output.println("2: To Display Current Authors registered in the System.");
			output.print("Enter choice: ");
			
			// I have to create a new scanner because printStream caused a bug.
			choice = ConsoleGUI.getNumberInput(info);

			if (choice.equals(0)) {
				output.print("Enter the username of the Co-author: ");
				String coAuthorUserName = input.nextLine().trim().toUpperCase();
				
				if (users.contains(coAuthorUserName) && coAuthorUserName != info.getUserName()) {
					currentConference.addAuthor(coAuthorUserName);
					
					// Makes the author the user.
					Author userNameInitAsAuthor = currentConference.getAuthor(coAuthorUserName);
					theCoAuthors.add(userNameInitAsAuthor);
					output.println(coAuthorUserName + " Added as a Co-Author.");
				} else {
					// For confidentiality purposes the author cannot register
					// for co authors. We can change this however if you guys
					// want.
					output.println(
							"Username not found. If the username you're looking for doesn't exist inform the Co-Author to register and add him/her back in later.");
				}
			} else if (choice.equals(2)) {
				// This prints out all the other users in the terminal
			    output.println();
				info.getOtherUsers().stream().forEach(output::println);
			}
		} while (!choice.equals(1));
		
		Paper conferenceSubmission = new Paper(Paths.get(filePath), theCoAuthors, paperTitle, currentAuthor);
		
		// Need to display the co authors and authors to the paper.
		output.print("You are about to submit the paper: \"" + conferenceSubmission.getTitle() + "\" Proceed? (Yes/No): ");
		String ans = input.nextLine().trim().toUpperCase();

		if (ans.equals("YES") || ans.equals("Y")) {
			boolean submitted = currentConference.submitPaper(conferenceSubmission);
			
			if (submitted) {
			    output.println("Submitted!");			    
			} else {
			    output.println("Error, one of the co-authors is at the max number of papers!");//TODO not sure if this is the only error possible.
			}
			
			//ConsoleGUI.dashBoard(info);
		} else {
			// Restarts from the beginning. In the future the user will have a
			// opportunity to go back to the dashboard at anytime.
			//ConsoleGUI.dashBoard(info);
		}
	}
	/**
	 * This code removes the paper from the conference
	 * 
	 *  * Preconditions: 
	 * 		The user info and all related fields must be valid
	 * 		All author information must be valid
	 * 		The user calling this method must be the one who submitted the paper
	 *  	It must not be past the conference's submission deadline
	 * Postconditions: 
	 * 		The specified paper will be removed from the conference
	 * 
	 * @author Ian Jury
	 * @param info information of the user trying to remove a paper.
	 */
	public static void removePaper(UserInfo info) {
		PrintStream output = info.out;
		Scanner input = info.in;
		Conference currentConference = info.getCurrentConference();
		Author currentAuthor = currentConference.getAuthor(info.username);
		List<Paper> papersThatContainCurrentAuthor = currentConference.getPapers(currentAuthor);

		// this is the list of papers that they actually submitted
		List<Paper> papersSubmittedByAuthor = new ArrayList<>();
		for (Paper paperWrittenByAuthor : papersThatContainCurrentAuthor) {
			// if the paper written by this author was also submitted by this
			// author
			if (paperWrittenByAuthor.getTheSubmitter().equals(currentAuthor)) {
				papersSubmittedByAuthor.add(paperWrittenByAuthor);
			}
		}
		int command = 1;
		// displays list of all papers the author has submitted
		output.println("Submitted Papers:");
		for (int idx = 0; idx < papersSubmittedByAuthor.size(); idx++) {
			output.println(command + ". " + papersSubmittedByAuthor.get(idx).getTitle());
			command++; // increments command #
		}
		output.print("Enter the associated number of the paper you want to remove (or 0 to cancel): ");
		// gets user input and validates
		Integer choice = checkIfValidIntegerInput(input.nextLine());
		if (choice > papersSubmittedByAuthor.size() + 1 || choice == null) {
			info.out.println("Could not find choice");
		} if (choice != 0) { // if the user didn't want to cancel, edit the paper
			choice--;// account for offset
			Paper paperToRemove = papersSubmittedByAuthor.get(choice);
			currentConference.removePaper(paperToRemove);
			output.println("The paper \"" + paperToRemove.getTitle() + "\" has successfully been removed.");
		} else { //if they enter a negative number or something
			output.println("Invalid input.");
		}
	}

	/**
	 * A helper method that checks if the file path could be found.
	 * 
	 * @author Kevin Nguyen
	 * @param filePath
	 *            the path of the file that the user inputs
	 * @return if the file could be found
	 */
	public static boolean isValidPath(String filePath) {
	    File theFile = null;
	    
		try {
			theFile = Paths.get(filePath).toFile();
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		
		return theFile.exists() && !theFile.isDirectory();
	}
	/**
	 * Allows logged in user to edit a paper if they are the user who submitted it.
	 * 
	 * Preconditions: 
	 * 		The user info and all related fields must be valid
	 * 		All author information must be valid
	 * 		The user calling this method must be the one who submitted the paper
	 *  	It must not be past the conference's submission deadline
	 * Postconditions: 
	 * 		The author can change the fields they want to
	 * 		The newly edited paper will be added to the conference
	 * 		The old information about the paper will be removed 
	 * @author Ian Jury
	 * @param info information of the person attempting to edit their papers
	 */
	public static void editPaper(UserInfo info) {
        //The location to send the output and input
        PrintStream output = info.out;
        Scanner input = info.in;
        Conference currentConference = info.getCurrentConference();
        Author currentAuthor = currentConference.getAuthor(info.username);       
        
       //gets list of papers -note that these are just ones where the user is an author
        List<Paper> papersThatContainCurrentAuthor = currentConference.getPapers
        												(currentAuthor);
        												
        //this is the list of papers that they actually submitted
        List<Paper> papersSubmittedByAuthor = new ArrayList<>();
        for (Paper paperWrittenByAuthor : papersThatContainCurrentAuthor) {
        	//if the paper written by this author was also submitted by this author
        	if (paperWrittenByAuthor.getTheSubmitter().equals(currentAuthor)) {
        		papersSubmittedByAuthor.add(paperWrittenByAuthor);
        	}
        }      

        output.println("\nSubmitted Papers:");
        int command = 1;
        //displays list of all papers the author has submitted
        for (int idx = 0; idx < papersSubmittedByAuthor.size(); idx++) {
        	output.println(command + ": " + papersSubmittedByAuthor.get(idx).getTitle()); 
        	command++;		//increments command #
        }
        output.print("Enter the associated number of the paper you want to edit (or 0 to cancel): ");
        

        //gets user input and validates
        Integer choice = checkIfValidIntegerInput(input.nextLine());
        if (choice > papersSubmittedByAuthor.size() + 1 || choice == null) {
			info.out.println("Could not find choice");
		} 
		
        if (choice != 0) { //if the user didn't want to cancel, edit the paper
        	choice--;//account for offset
        	Paper paperToEdit = papersSubmittedByAuthor.get(choice);
        	Integer userDecision = 0;
        	do {
        	    output.println();
	        	output.println("Currently Editing: " + paperToEdit.getTitle());
	        	output.println("0. Exit paper editor");
	        	output.println("1. Change file path");
	        	output.println("2. Change list of authors");
	        	output.println("3. Change title of paper");
	        	output.print("Enter the associated number of the information you want to edit: ");
	        	
	        	//prevents user from entering non integers
	        	if ((userDecision = checkIfValidIntegerInput(input.nextLine())).equals(-1)) {
	        		System.out.println("Invalid input.");
	        	}

	        	else if (userDecision.equals(1)) {	//user changes file path
	        		paperToEdit = changeFilePathOfPaper(paperToEdit, info);
	        		  		
	        	} else if (userDecision.equals(2)) { //user changes list of authors
	        		paperToEdit = changeAuthorsOfPaper(paperToEdit, info);
	        		
	        	} else if (userDecision.equals(3)) { //user changes 
	        		paperToEdit = changeTitleOfPaper(paperToEdit, info);
	        	}
	        	//checks if input is a number not specified
	        	else  { 
	        		output.println("Invalid input. Try again");
	        	}
        	} while(!userDecision.equals(0)); //while user doesn't want to exit
        } 
	}
	/**
	 * Takes the old paper and returns a new paper object with a new file path
	 * specified by the user.
	 * 
	 * Preconditions:
	 *  	It must not be past the conference's submission deadline 
	 * 		All authors must be valid for the specific conference
	 * 		The user info and all related fields must be valid
	 * Postconditions: 
	 * 		A new paper object will be returned with a new file path
	 * 		The new paper object will be submitted to the conference
	 * 		The old paper object will be removed from the conference 
	 * @author Ian Jury
	 * @param theOriginalPaper the original paper object
	 * @return the new paper object
	 */
	private static Paper changeFilePathOfPaper(Paper theOriginalPaper, UserInfo info) {
		info.out.println("Enter new file path: ");
		String userInputPath = info.in.nextLine();
		
		if (isValidPath(userInputPath)) {
			Path newFilePath = Paths.get(info.in.nextLine());
			Paper editedPaper = new Paper(newFilePath, theOriginalPaper.getAuthors(), 
					theOriginalPaper.getTitle(), theOriginalPaper.getTheSubmitter());

			info.getCurrentConference().removePaper(theOriginalPaper); //remove the old paper
			info.getCurrentConference().submitPaper(editedPaper); //add the edited paper
			System.out.println("File path has been changed to: " + newFilePath.toString());
			return editedPaper;
		} else {
			info.out.println("Invalid file path. Path wasn't changed.");
			return theOriginalPaper;
		}
				
	}
	/**
	 * Takes the old paper and returns a new paper object with a new list of authors
	 * specified by the user.
	 * 
	 * Preconditions: 
	 * 		It must not be past the conference's submission deadline
	 * 		All authors must be valid for the specific conference
	 * 		The user info and all related fields must be valid
	 * Postconditions: 
	 * 		The new paper object will be submitted to the conference
	 * 		The old paper object will be removed from the conference
	 * @author Ian Jury
	 * @param theOriginalPaper
	 * @return the new paper object
	 */
	private static Paper changeAuthorsOfPaper(Paper theOriginalPaper, UserInfo info) {
		
		List<Author> newListOfAuthors = createNewListOfAuthorsOfPaper(info);
		
		Paper editedPaper = new Paper(theOriginalPaper.getDocumentPath(), newListOfAuthors, 
				theOriginalPaper.getTitle(), theOriginalPaper.getTheSubmitter());
		
		if (info.getCurrentConference().submitPaper(editedPaper) == false) { //add the edited paper
			//if the paper couldn't be submitted because an author was invalid/ not part of conference
			info.out.println("The editing of authors was not possible because one of the usernames " + 
			"you entered is not valid for the current conference.\nNo changes were made. Please try again.\n");
			return theOriginalPaper; //if no changes could be made, return the original paper
		} else {
			info.getCurrentConference().removePaper(theOriginalPaper); //remove the old paper
			System.out.println("Author list has been changed!");
			return editedPaper; //else return the new paper
		}
		
	}
	/**
	 * Takes the old paper and returns a new paper object with a new title
	 * specified by the user.
	 * Preconditions: 
	 * 		The Paper must be a valid paper object for the conference
	 *  	The user info and all related fields must be valid
	 * Postconditions:
	 * 		A new paper with a new title will be returned(but the other fields will stay the same)
	 * 		The new paper object will be submitted to the conference
	 * 		The old paper object will be removed from the conference
	 * 
	 * @author Ian Jury
	 * @param theOriginalPaper
	 */
	private static Paper changeTitleOfPaper(Paper theOriginalPaper, UserInfo info) {
		info.out.print("Enter new title of paper: ");
		String newTitleOfPaper = info.in.nextLine();
		Paper editedPaper = new Paper(theOriginalPaper.getDocumentPath(), theOriginalPaper.getAuthors(), 
				newTitleOfPaper, theOriginalPaper.getTheSubmitter());
		info.getCurrentConference().removePaper(theOriginalPaper); //remove the old paper
		info.getCurrentConference().submitPaper(editedPaper); //add the edited paper
		System.out.println("Paper title has been changed to: " + newTitleOfPaper);
		return editedPaper;
		
	}
	
	/**
	 * Kevin, I stole this from your code in submitPaper to use when editing authors
	 * You could probably use this later when refactoring too if you want
	 * 
	 * Takes the user info and returns a new list of authors from the user's input
	 * 
	 * @author Kevin Nguyen
	 * @param info the information of the user
	 * @return the new list of authors
	 */
	private static List<Author> createNewListOfAuthorsOfPaper(UserInfo info) {
		PrintStream output = info.out;
		Scanner input = info.in;
		Integer choice = -1;
		Set<String> users = SerializationHelper.loadUsers();
		Conference currentConference = info.getCurrentConference();
		List<Author> theCoAuthors = new LinkedList<>();
		do {
			// This will be done possibly by checkmarking in the future.
			// For the terminal would it be better to simply have a for loop of
			// the other users and have the user input the number/index?
		    output.println();
			output.println("0: Add a Co-Author to the paper (by username).");
			output.println("1: If there are no Co-authors or none left to add.");//TODO could be better worded
			
			// This will possibly already be shown in the gui in the future. I
			// think we need a way to show the real names aswell.
			output.println("2: To Display Current Authors registered in the System.");
			output.print("Enter choice: ");
			
			// I have to create a new scanner because printStream caused a bug.
			choice = ConsoleGUI.getNumberInput(info);

			if (choice.equals(0)) {
				output.print("Enter the username of the Co-author: ");
				String coAuthorUserName = input.nextLine().trim().toUpperCase();
				
				if (users.contains(coAuthorUserName) && coAuthorUserName != info.getUserName()) {
					currentConference.addAuthor(coAuthorUserName);
					
					// Makes the author the user.
					Author userNameInitAsAuthor = currentConference.getAuthor(coAuthorUserName);
					theCoAuthors.add(userNameInitAsAuthor);
					output.println(coAuthorUserName + " Added as a Co-Author.");
				} else {
					// For confidentiality purposes the author cannot register
					// for co authors. We can change this however if you guys
					// want.
					output.println(
							"Username not found. If the username you're looking for doesn't exist inform the Co-Author to register and add him/her back in later.");
				}
			} else if (choice.equals(2)) {
				// This prints out all the other users in the terminal
			    output.println();
				info.getOtherUsers().stream().forEach(output::println);
			}
		} while (!choice.equals(1));
		return theCoAuthors;
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
			return choice;
		} catch (NumberFormatException e) { 
			choice = null;
		}
		return -1;
	}
}
