package gui;

import java.io.PrintStream;
import java.util.Scanner;

import model.Author;
import model.Conference;

public class SubProgramChairActions {
	
	public static void assignReviewer(UserInfo info) {
		PrintStream out = info.out;
        Scanner in = info.in;
        Conference currentConference = info.getCurrentConference();
        
        //currentConference.
        out.println("Enter the associated number of the paper you want to assign a Reviewer to (or 0 to cancel): ");
        
	}

	
	public static void removeReviewer(UserInfo info) {
		
	}
}
