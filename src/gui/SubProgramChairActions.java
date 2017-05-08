package gui;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Conference;
import model.Paper;
import model.Reviewer;
import model.RolesChecker;

/**
 * Class to guide a Subprogram chair through their actions such as assigning a Reviewer
 * to a paper using a UI.
 * 
 * @author Dmitriy Bliznyuk
 *
 */
public class SubProgramChairActions {
	
	/**
	 * UI method to assign a Reviewer to a Paper
	 * Preconditions:
	 * 		-UserInfo is not null
	 * 		-UserInfo's public fields are not null
	 * 		-UserInfo's getCurrentConference method does not return null
	 * Postconditions:
	 * 		-Assigns a Reviewer to user inputed Paper and Reviewer through the UI
	 * 
	 * @param info UserInfo object of the Subprogram chair
	 */
	public static void assignReviewer(UserInfo info) {
		PrintStream out = info.out;
        Scanner in = info.in;
        Conference currentConference = info.getCurrentConference();   
        RolesChecker rc = new RolesChecker(info.getCurrentConference().getRoles(info.username));
        List<Paper> papers = rc.getSubProgramChairRole().getPapers();
        
        printListOfPapers(out, papers);
        out.print("Enter the associated number of the paper to which you want to assign a Reviewer (or 0 to cancel): ");

        Integer userPaperChoice = getPaperIndexInput(out, in, papers);
        if (userPaperChoice != 0) {
        	out.println();
        	out.println("Paper Title: " + papers.get(userPaperChoice-1).getTitle());
        	
        	List<Reviewer> allReviewers = currentConference.getReviewers();
        	
        	printListOfReviewers(out, allReviewers);
        	out.print("Enter the associated number of the Reviewer you want to assign to this paper (or 0 to cancel): ");
            
            Integer userReviewerChoice = getReviewerIndexInput(out, in, allReviewers);      
            if (userReviewerChoice != 0) {
            	allReviewers.get(userReviewerChoice - 1).assign(papers.get(userPaperChoice - 1));
            	out.println();
            	out.println("Successfully asssigned \"" + allReviewers.get(userReviewerChoice - 1).getUser() 
            			+ "\" to review " + "\"" + papers.get(userPaperChoice - 1).getTitle() + "\"");
            }
        }
	}
	
	/**
	 * UI method to remove a Reviewer from a Paper
	 * Preconditions:
	 * 		-UserInfo is not null
	 * 		-UserInfo's public fields are not null
	 * 		-UserInfo's getCurrentConference method does not return null
	 * Postconditions:
	 * 		-Removes user inputed Reviewer from user inputed Paper through the UI
	 * 
	 * @param info UserInfo object of the Subprogram chair
	 */
	public static void removeReviewer(UserInfo info) {
	    RolesChecker rc = new RolesChecker(info.getCurrentConference().getRoles(info.getUserName()));
	    List<Paper> assignedPapers = rc.getSubProgramChairRole().getPapers();   
		PrintStream out = info.out;
        Scanner in = info.in;
        Conference currentConference = info.getCurrentConference();
        out.println();
		
        List<Reviewer> allReviewers = currentConference.getReviewers();
        Iterator<Reviewer> iterReviewer = allReviewers.iterator();
        while (iterReviewer.hasNext()) {
            Reviewer r = iterReviewer.next();
            if (!ConsoleGUI.isReviewerFor(rc.getSubProgramChairRole(), r)) {   
                iterReviewer.remove();
            }
        }
        
        printListOfReviewers(out, allReviewers);  	
    	out.print("Enter the associated number of the Reviewer you want to remove (or 0 to cancel): ");
    	
    	Integer userReviewerChoice = getReviewerIndexInput(out, in, allReviewers);
        if (userReviewerChoice != 0) {
        	Reviewer reviewer = allReviewers.get(userReviewerChoice-1);
        	List<Paper> papersToBeReviewed = reviewer.getPapersToBeReviewed();
        	
        	Iterator<Paper> iterPaper = papersToBeReviewed.iterator();
        	while (iterPaper.hasNext()) {
        	    Paper p = iterPaper.next();
        	    if (!assignedPapers.contains(p)) {
        	        iterPaper.remove();
        	    }
        	}
        	
        	out.println();
        	out.println("Reviewer: " + reviewer.getUser());
        	printListOfPapers(out, papersToBeReviewed);
        	out.print("Enter the associated number of the Paper you want to remove from this Reviewer (or 0 to cancel): ");
        	
        	Integer userPaperChoice = getPaperIndexInput(out, in, papersToBeReviewed);   	
        	if(userPaperChoice != 0) {
        		reviewer.removePaper(papersToBeReviewed.get((userPaperChoice-1)));
        		out.println();
        		out.println("Successfully removed Reviewer \"" + reviewer.getUser() + "\" from paper \"" 
        				+ papersToBeReviewed.get((userPaperChoice-1)).getTitle() + "\"");
        	}
        }
		
	}
	
	/**
	 * Private helper method to get user's input on which paper they choose.
	 * 
	 * @param out PrintStream where to print prompts and paper list
	 * @param in Scanner from which to get user's input
	 * @param papers List of papers from which the user must choose
	 * @return int value of the paper user chose
	 */
	private static int getPaperIndexInput(PrintStream out, Scanner in, List<Paper> papers) {
		Integer userPaperChoice = checkIfValidIntegerInput(in.nextLine());	
    	while (userPaperChoice == null || userPaperChoice > papers.size() || userPaperChoice < 0) {
    		out.println();
        	out.println("Could not find Paper at index " + userPaperChoice + "!");
        	printListOfPapers(out, papers);
        	out.println("Please enter another value (or 0 to cancel): ");
        	userPaperChoice = checkIfValidIntegerInput(in.nextLine());
        }
    	return userPaperChoice;
	}
	
	/**
	 * Private helper method to get user's input on which Reviewer they choose.
	 * 
	 * @param out PrintStream where to print prompts and Reviewer list
	 * @param in Scanner from which to get user's input
	 * @param reviewers List of Reviewers from which the user must choose
	 * @return int value of the Reviewer user chose
	 */
	private static int getReviewerIndexInput(PrintStream out, Scanner in, List<Reviewer> reviewers) {
		Integer userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
        while (userReviewerChoice == null || userReviewerChoice > reviewers.size() || userReviewerChoice < 0) {
        	out.println();
        	out.println("Could not find Reviewer at index " + userReviewerChoice + "!");
        	printListOfReviewers(out, reviewers);
        	out.println("Please enter another value (or 0 to cancel): ");
        	userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
        }
    	return userReviewerChoice;
	}
	
	/**
	 * Private helper method to print a list of papers.
	 * @param out PrintStream where to print the list
	 * @param papers List of Papers to print
	 */
	private static void printListOfPapers(PrintStream out, List<Paper> papers) {
		out.println();
        for (int i = 0; i < papers.size(); i++) {
        	out.println("\t" + (i+1) + ". " + papers.get(i).getTitle());
        }
	}
	
	/**
	 * Private helper method to print a list of Reviewers.
	 * @param out PrintStream where to print the list
	 * @param papers List of Papers to print
	 */
	private static void printListOfReviewers(PrintStream out, List<Reviewer> allReviewers) {
		for (int i = 0; i < allReviewers.size(); i++) {
        	out.println("\t" + (i+1) + ". " + allReviewers.get(i).getUser());
        }
	}
	
	/**
	 * Helper method to determine that the user entered a valid integer.
	 * @author Ian Jury
	 * @param userInput The string read from the input source
	 * @return null if input is invalid, otherwise it returns an Integer
	 */// changed to return Integer (not primitive) by zach
	private static Integer checkIfValidIntegerInput(String userInput) {
		Integer choice;
		try {
			choice = Integer.parseInt(userInput);			
		} catch (NumberFormatException e) { 
			choice = null;
		}
		return choice;
	}
}
