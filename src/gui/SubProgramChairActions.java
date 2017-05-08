package gui;

import java.io.PrintStream;
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
        	
        	Integer userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
            while (userReviewerChoice == null || userReviewerChoice > allReviewers.size() || userReviewerChoice < 0) {
            	out.println();
            	out.println("Could not find Reviewer at index " + userReviewerChoice + "!");
            	printListOfReviewers(out, allReviewers);
            	out.println("Please enter another value (or 0 to cancel): ");
            	userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
            }
            
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
        for (Reviewer r : allReviewers) {
            if (!ConsoleGUI.isReviewerFor(rc.getSubProgramChairRole(), r)) {   
                allReviewers.remove(r);
            }
        }
        
        printListOfReviewers(out, allReviewers);
    	
    	out.print("Enter the associated number of the Reviewer you want to remove (or 0 to cancel): ");
    	
    	Integer userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
        while (userReviewerChoice == null || userReviewerChoice > allReviewers.size() || userReviewerChoice < 0 ) {
        	out.println();
        	out.println("Could not find Reviewer at index " + userReviewerChoice + "!");
        	printListOfReviewers(out, allReviewers);
        	out.print("Please enter another value (or 0 to cancel): ");
        	userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
        }
        
        if (userReviewerChoice != 0) {
        	Reviewer reviewer = allReviewers.get(userReviewerChoice-1);
        	List<Paper> papersToBeReviewed = reviewer.getPapersToBeReviewed();
        	
        	for (Paper p : papersToBeReviewed) {
        	    if (!assignedPapers.contains(p)) {
        	        papersToBeReviewed.remove(p);
        	    }
        	}
        	
        	out.println();
        	out.println("Reviewer: " + reviewer.getUser());
        	printListOfPapers(out, papersToBeReviewed);
        	out.print("Enter the associated number of the Paper you want to remove from this Reviewer (or 0 to cancel): ");
        	
        	Integer userPaperChoice = checkIfValidIntegerInput(in.nextLine());
        	while (userPaperChoice == null || userPaperChoice > papersToBeReviewed.size() || userPaperChoice < 0 ) {
        		out.println();
            	out.println("Could not find Paper at index " + userPaperChoice + "!");
            	printListOfPapers(out, papersToBeReviewed);
            	out.println("Please enter another value (or 0 to cancel): ");
            	userPaperChoice = checkIfValidIntegerInput(in.nextLine());
            }
        	
        	if(userPaperChoice != 0) {
        		reviewer.removePaper(papersToBeReviewed.get((userPaperChoice-1)));
        		out.println();
        		out.println("Successcully removed Reviewer \"" + reviewer.getUser() + "\" from paper \"" 
        				+ papersToBeReviewed.get((userPaperChoice-1)).getTitle() + "\"");
        	}
        }
		
	}
	
	private static int getPaperIndexInput(PrintStream out, Scanner in, List<Paper> papers) {
		Integer userPaperChoice = checkIfValidIntegerInput(in.nextLine());	
    	while (userPaperChoice == null ||userPaperChoice > papers.size() || userPaperChoice < 0) {
    		out.println();
        	out.println("Could not find Paper at index " + userPaperChoice + "!");
        	printListOfPapers(out, papers);
        	out.println("Please enter another value (or 0 to cancel): ");
        	userPaperChoice = checkIfValidIntegerInput(in.nextLine());
        }
    	return userPaperChoice;
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
	 * Private helper method to print a list of papers.
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
