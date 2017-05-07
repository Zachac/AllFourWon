package gui;

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
		theCoAuthors.add(currentAuthor);
		output.println("Enter the file path of the paper:");
		String filePath = input.nextLine();
		while (!isValidPath(filePath)) {
			// I need to make a option where the user can exit this and go back
			// to the conference screen.
			System.out.println("The file could not be found from the designated file path, please try again.");
			filePath = input.nextLine();
		}
		output.println("Enter the title of the paper:");
		String paperTitle = input.nextLine();
		Integer choice = -1;
		do {
			// This will be done possibly by checkmarking in the future.
			// For the terminal would it be better to simply have a for loop of
			// the other users and have the user input the number/index?
			output.println("0: Add a Co-Author to the paper (by username).");
			output.println("1: If they're no Co-authors or none left to add.");
			// This will possibly already be shown in the gui in the future. I
			// think we need a way to show the real names aswell.
			output.println("2: To Display Current Users registered on the System.");
			output.print("Enter choice: ");
			// I have to create a new scanner because printStream caused a bug.
			Scanner scan = new Scanner(System.in);
			choice = scan.nextInt();

			if (choice.equals(0)) {
				output.println("Enter the username of the Co-author:");
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
				info.getOtherUsers().stream().forEach(output::println);
			}

		} while (!choice.equals(1));
		Paper conferenceSubmission = new Paper(Paths.get(filePath), theCoAuthors, paperTitle, currentAuthor);
		// Need to display the co authors and authors to the paper.
		output.println("You are about to submit the paper: " + conferenceSubmission.getTitle() + " Proceed? (Yes/No)");
		Scanner lastStep = new Scanner(System.in);
		String ans = lastStep.nextLine().trim().toUpperCase();

		if (ans.equals("YES") || ans.equals("Y")) {
			currentConference.submitPaper(conferenceSubmission);
			output.println("Submitted!");
			ConsoleGUI.dashBoard(info);
		} else {
			// Restarts from the beginning. In the future the user will have a
			// opportunity to go back to the dashboard at anytime.
			ConsoleGUI.dashBoard(info);
		}
		lastStep.close();
	}
	/**
	 * This code removes the paper from the conference
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
		output.println("Enter the associated number of the paper you want to remove (or 0 to cancel): ");
		for (int idx = 0; idx < papersSubmittedByAuthor.size(); idx++) {
			output.println(command + ". " + papersSubmittedByAuthor.get(idx).getTitle());
			command++; // increments command #
		}
		// gets user input and validates
		Integer choice = checkIfValidIntegerInput(input.nextLine());
		if (choice > papersSubmittedByAuthor.size() + 1 || choice == null) {
			info.out.println("Could not find choice");
		} if (choice != 0) { // if the user didn't want to cancel, edit the paper
			choice--;// account for offset
			Paper paperToRemove = papersSubmittedByAuthor.get(choice);
			currentConference.removePaper(paperToRemove);
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
		try {
			Paths.get(filePath);
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		return true;
	}
	/**
	 * Allows logged in user to edit paper if they are the user who submitted it.
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
        int command = 1;
        //displays list of all papers the author has submitted
        output.println("Enter the associated number of the paper you want to edit (or 0 to cancel): ");
        for (int idx = 0; idx < papersSubmittedByAuthor.size(); idx++) {
        	output.println(command + ". " + papersSubmittedByAuthor.get(idx).getTitle()); 
        	command++;		//increments command #
        }
        

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
	        	output.println("Enter the associated number of the information you want to edit: ");
	        	output.println("CURRENTLY EDITING: " + paperToEdit.getTitle());
	        	output.println("0. Exit paper editor");
	        	output.println("1. Change file path");
	        	output.println("2. Change list of authors");
	        	output.println("3. Change title of paper");
	        	userDecision = checkIfValidIntegerInput(input.nextLine());
	        	
	        	//this code is really lame, going to fix it later
	        	//doesn't check for invalid inputs yet
	        	if (userDecision.equals(1)) {	//user changes file path
	        		output.println("Enter new file path: ");
	        		Path newFilePath = Paths.get(input.nextLine());
	        		//replace old path
	        		Paper editedPaper = new Paper(newFilePath, paperToEdit.getAuthors(), 
	        				paperToEdit.getTitle(), paperToEdit.getTheSubmitter());
	        		//TODO check if the deadline has passed so a paper wont just be removed and then 
	        		//fail to be submitted?
	        		info.getCurrentConference().removePaper(paperToEdit); //remove the old paper
	        		info.getCurrentConference().submitPaper(editedPaper); //add the edited paper
	        		System.out.println("File path has been changed to: " + newFilePath.toString());
	        		
	        	} else if (userDecision.equals(2)) { //user changes list of authors
	        		output.println("Enter new author names,  ");
	        		String newAuthorNames = input.nextLine();
	        		//splits to whitespace
	        		List<String> stringListOfAuthors = Arrays.asList(newAuthorNames.split("\\s*,\\s*"));
	        		List<Author> newListOfAuthors = new ArrayList<>();
	        		//converts string of author names into new list of 'authors'
	        		for(String authorName : stringListOfAuthors) {
	        			newListOfAuthors.add(new Author(authorName));
	        		}
	        		Paper editedPaper = new Paper(paperToEdit.getDocumentPath(), newListOfAuthors, 
	        				paperToEdit.getTitle(), paperToEdit.getTheSubmitter());
	        		info.getCurrentConference().removePaper(paperToEdit); //remove the old paper
	        		info.getCurrentConference().submitPaper(editedPaper); //add the edited paper
	        		System.out.println("Author list has been changed!");
	        		
	        		
	        	} else if (userDecision.equals(3)) { //user changes 
	        		output.println("Enter new title of paper: ");
	        		String newTitleOfPaper = input.nextLine();
	        		Paper editedPaper = new Paper(paperToEdit.getDocumentPath(), paperToEdit.getAuthors(), 
	        				newTitleOfPaper, paperToEdit.getTheSubmitter());
	        		info.getCurrentConference().removePaper(paperToEdit); //remove the old paper
	        		info.getCurrentConference().submitPaper(editedPaper); //add the edited paper
	        		System.out.println("Paper title has been changed to: " + newTitleOfPaper);
	        		System.out.println("NOTE! Exit paper editor (press 0) for changes to display here");
	        		
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
