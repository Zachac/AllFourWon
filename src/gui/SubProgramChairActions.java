package gui;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import model.Conference;
import model.Paper;
import model.Reviewer;
import model.RolesChecker;

public class SubProgramChairActions {
	
	public static void assignReviewer(UserInfo info) {
		PrintStream out = info.out;
        Scanner in = info.in;
        Conference currentConference = info.getCurrentConference();
        
        RolesChecker rc = new RolesChecker(info.getCurrentConference().getRoles(info.username));
        List<Paper> papers = rc.getSubProgramChairRole().getPapers();
        
        out.println("Enter the associated number of the paper to which you want to assign a Reviewer (or 0 to cancel): ");
        for (int i = 0; i < papers.size(); i++) {
        	out.println("\t" + (i+1) + ". " + papers.get(i).getTitle());
        }
        int userPaperChoice = in.nextInt();
        if (userPaperChoice > papers.size() + 1) {
        	out.println("Could not find paper at index " + userPaperChoice + "!");
        }
        
        else if (userPaperChoice != 0) {
        	out.println("Enter the associated number of the Reviewer you want to assign to this paper (or 0 to cancel): ");
        	
        	List<Reviewer> allReviewers = currentConference.getReviewers();
        	for (int i = 0; i < allReviewers.size(); i++) {
            	out.println("\t" + (i+1) + ". " + allReviewers.get(i).getUser());
            }
        	
        	int userReviewerChoice = in.nextInt();
            if (userReviewerChoice > allReviewers.size() + 1) {
            	out.println("Could not find Reviewer at index " + userReviewerChoice + "!");
            }
            
            else if (userReviewerChoice != 0) {
            	allReviewers.get(userReviewerChoice - 1).assign(papers.get(userPaperChoice - 1));
            }
        }
        
	}

	
	public static void removeReviewer(UserInfo info) {
		
	}
}
