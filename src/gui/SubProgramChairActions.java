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
        
        out.println();
        out.println("Enter the associated number of the paper to which you want to assign a Reviewer (or 0 to cancel): ");
        for (int i = 0; i < papers.size(); i++) {
        	out.println("\t" + (i+1) + ". " + papers.get(i).getTitle());
        }
        //int userPaperChoice = in.nextInt();
        Integer userPaperChoice = checkIfValidIntegerInput(in.nextLine());
        if (userPaperChoice > papers.size()) {
        	out.println();
        	out.println("Could not find paper at index " + userPaperChoice + "!");
        }
        
        else if (userPaperChoice != 0) {
        	out.println();
        	out.println("Paper Title: " + papers.get(userPaperChoice-1).getTitle());
        	out.println("Enter the associated number of the Reviewer you want to assign to this paper (or 0 to cancel): ");
        	
        	List<Reviewer> allReviewers = currentConference.getReviewers();
        	for (int i = 0; i < allReviewers.size(); i++) {
            	out.println("\t" + (i+1) + ". " + allReviewers.get(i).getUser());
            }
        	
        	//int userReviewerChoice = in.nextInt();
        	Integer userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
            if (userReviewerChoice > allReviewers.size()) {
            	out.println();
            	out.println("Could not find Reviewer at index " + userReviewerChoice + "!");
            }
            
            else if (userReviewerChoice != 0) {
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
		
		PrintStream out = info.out;
        Scanner in = info.in;
        Conference currentConference = info.getCurrentConference();
        out.println();
        out.println("Enter the associated number of the Reviewer you want to remove (or 0 to cancel): ");
		
        List<Reviewer> allReviewers = currentConference.getReviewers();
    	for (int i = 0; i < allReviewers.size(); i++) {
        	out.println("\t" + (i+1) + ". " + allReviewers.get(i).getUser());
        }
    	
    	//int userReviewerChoice = in.nextInt();
    	Integer userReviewerChoice = checkIfValidIntegerInput(in.nextLine());
        if (userReviewerChoice > allReviewers.size()) {
        	out.println();
        	out.println("Could not find Reviewer at index " + userReviewerChoice + "!");
        }
        
        else if (userReviewerChoice != 0) {
        	Reviewer reviewer = allReviewers.get(userReviewerChoice-1);
        	List<Paper> papersToBeReviewed = reviewer.getPapersToBeReviewed();
        	out.println();
        	out.println("Reviewer: " + reviewer.getUser());
        	out.println("Enter the associated number of the Paper you want to remove from this Reviewer (or 0 to cancel): ");
        	for (int i = 0; i < papersToBeReviewed.size(); i++) {
        		out.println("\t" + (i+1) + ". " + papersToBeReviewed.get(i).getTitle());
        	}
        	
        	//int userPaperChoice = in.nextInt();
        	Integer userPaperChoice = checkIfValidIntegerInput(in.nextLine());
        	
        	if (userPaperChoice > papersToBeReviewed.size()) {
        		out.println();
            	out.println("Could not find Paper at index " + userReviewerChoice + "!");
            }
        	
        	else if(userPaperChoice != 0) {
        		reviewer.removePaper(papersToBeReviewed.get((userPaperChoice-1)));
        		out.println();
        		out.println("Successcully removed Reviewer \"" + reviewer.getUser() + "\" from paper \"" 
        				+ papersToBeReviewed.get((userPaperChoice-1)).getTitle() + "\"");
        	}
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
