package gui;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

import model.Conference;
import model.ConferenceManager;
import model.Paper;
import model.RolesChecker;

/**
 * The main console user interface.
 * 		logs in a user.
 * 		chooses a conference.
 * 		displays the dash board to a user.
 * 
 * @author Zachary Chandler
 */
public class ConsoleGUI {

	/**
	 * Login a user and return their user info.
	 * 
	 * Preconditions:
	 * 		users is not null
	 * 
	 * Postconditions:
	 * 		if the user failed to provide adequet login information,
	 * 			then the result will be null.
	 * 		otherwise the result will be the user info for a user at the
	 * 			command line
	 *   
	 * 
	 * @param users a set of all user names in the system.
	 * @return the user info of the logged in user.
	 */
	public static UserInfo login(Set<String> users) {
		PrintStream output = System.out;
		Scanner input = new Scanner(System.in);
		
		output.print("Enter your user name: ");
		String userName = input.nextLine().trim().toUpperCase();
		
		UserInfo result;
		
		if (users.contains(userName)) {
			result = new UserInfo(output, input, userName, users);
		} else {
			output.print("Username not found. Would you like to register? (Yes/No): ");
			
			String ans = input.nextLine().trim().toUpperCase();
			
			if (ans.equals("YES") || ans.equals("Y")) {
				users.add(userName);
				output.println("Registered!");
				result = new UserInfo(output, input, userName, users);
			} else {
				result = null;
			}
			
		}
		
		return result;
	}

	/**
	 * Show and run the main menu for a particular user. This means choosing
	 * and selecting a conference.
	 *  
	 * @param info the users info to interact with them.
	 * @return if the user wishes to exit.
	 */
	public static boolean mainMenu(UserInfo info, ConferenceManager cm) {
		boolean shouldContinue = true;

		info.out.println();
		info.out.println("\tCONFERENCES");
		info.out.println();
		
		List<Conference> conferencesList = cm.getConferences();
		Conference[] conferences = new Conference[conferencesList.size()];
		Date now = new Date();
		
		{
			// TODO move to separate function?
			int i = 0;
			for (Conference c : conferencesList) {
				conferences[i++] = c;
			}
		}
		
		if (conferences.length == 0) {
			info.out.println("There are no conferences right now.");
		}
		
		for (int i = 0; i < conferences.length; i++) {
			info.out.printf(i + 1 + ": " + conferences[i].name);
			if (conferences[i].isBeforeSubmissionDeadline(now)) {
				info.out.printf("%d: %-15s (%-15s)\n", 
						i+1, conferences[i].name, conferences[i].getDeadline());				
			} else {
				info.out.printf("%d: %-15s (CLOSED)\n",
						i+1, conferences[i].name);
			}
		}
		
		info.out.print("Choose Conference (or 0 to exit): ");
		
		String inputLine = info.in.nextLine();
		
		Integer choice;
		
		try {
			choice = Integer.parseInt(inputLine);			
		} catch (NumberFormatException e) { 
			choice = null;
		}
		
		if (choice == null) {
			info.out.println("Invalid input.");			
		} else if (choice > conferences.length + 1) {
			info.out.println("Could not find choice");
		} else if (choice == 0) {
			shouldContinue = false;
		} else {
			info.setCurrentConference(conferences[choice - 1]);
			while (dashBoard(info));
			info.setCurrentConference(null);
		}
				
		return shouldContinue;
	}
	
	/**
	 * Displays the dash board to the user and gives them possible actions based
	 * on their roles for currentConference in the info.
	 * 
	 * @param info the information about the user.
	 * @return if the user should continue looking at the dash board.
	 */
	public static boolean dashBoard(UserInfo info) {
	    boolean result = true;
	    
		info.out.println();
		info.out.println("\tDASH BOARD");
		
		displayDashboardInfo(info);
		info.out.println();
		
		RolesChecker rc = new RolesChecker(info.getCurrentConference().getRoles(info.username));
		Date now = new Date();
		
		Action[] commands = new Action[5];		
		int possibleCommands = 0;

		System.out.println(possibleCommands + ": choose another conference.");
		commands[possibleCommands] = (i) -> {};
		possibleCommands++;

		if (info.getCurrentConference().isBeforeSubmissionDeadline(now)) {
	        
			if (rc.isAuthor) {
			    List<Paper> papers = info.getCurrentConference().getPapers(rc.getAuthorRole());
	            
			    if (!info.getCurrentConference().isAuthorAtPaperLimit(rc.getAuthorRole())) {
			        System.out.println(possibleCommands + ": Submit Paper");
			        commands[possibleCommands] = AuthorActions::submitPaper;
			        possibleCommands++;
			    }
			    
			    if (!papers.isEmpty()) {
	                System.out.println(possibleCommands + ": Remove Paper");
	                commands[possibleCommands] = AuthorActions::removePaper;
	                possibleCommands++;
	                
	                System.out.println(possibleCommands + ": Edit Paper");
	                commands[possibleCommands] = AuthorActions::editPaper;
	                possibleCommands++;
			    }
			} else {
		        System.out.println(possibleCommands + ": Submit Paper");
		        commands[possibleCommands] = AuthorActions::submitPaper;
		        possibleCommands++;
			}
		}
		
		if (rc.isSubProgramChair) {
		    List<Paper> papers = rc.getSubProgramChairRole().getPapers();
		    
		    if (!papers.isEmpty()) {
		        System.out.println(possibleCommands + ": Assign Reviewer");
                commands[possibleCommands] = SubProgramChairActions::assignReviewer;
                possibleCommands++;
                
                System.out.println(possibleCommands + ": Remove Reviewer");
                commands[possibleCommands] = SubProgramChairActions::removeReviewer;
                possibleCommands++;
		    }
		    
		}
		    
	    System.out.print("Enter choice: ");
	    String inputLine = info.in.nextLine();
        
        Integer choice;
        
        try {
            choice = Integer.parseInt(inputLine);           
        } catch (NumberFormatException e) { 
            choice = null;
        }
        
        if (choice == null) {
            info.out.println("Invalid input."); 
        } else if (choice == 0) {
            result = false;
        } else if (choice < possibleCommands){
            commands[choice].run(info);
        } else {
            info.out.println("Could not find choice");
        }
		
        return result;
	}
	
	public static void displayDashboardInfo(UserInfo info) {
        RolesChecker rc = new RolesChecker(info.getCurrentConference().getRoles(info.username));
        
        if (rc.isAuthor) {
            List<Paper> papers = info.getCurrentConference().getPapers(rc.getAuthorRole());
            
            if (papers.isEmpty()) {
                info.out.println("As an Author, You have not submitted any papers!");
            } else {
                List<Paper> submitted = new LinkedList<>();
                List<Paper> coauthored = new LinkedList<>();
                
                for (Paper p : papers) {
                	if (p.getTheSubmitter() == rc.getAuthorRole()) {
                		submitted.add(p);
                	} else {
                		coauthored.add(p);
                	}
                }
                
                if (!submitted.isEmpty()) {
                	info.out.println("As an Author, You have submitted:");

                	for (Paper p : submitted) {
                		info.out.printf("    %-30.30s (%s)\n", p.getTitle(), p.getSubmissionDate().toString());
                	}
                }
                
                if (!coauthored.isEmpty()) {
                	info.out.println("As an Author, You are the co-author of:");

                	for (Paper p : coauthored) {
                		info.out.printf("    %-30.30s (%s)\n", p.getTitle(), p.getSubmissionDate().toString());
                	}
                }                
            }
        }
        

        if (rc.isReviewer) {
            List<Paper> unReviewedPapers = rc.getReviewerRole().getPapersToBeReviewed();
            
            if (unReviewedPapers.isEmpty()) {
                System.out.println("As a Reviewer, You have no papers that need to be reviewed!");
            } else {
                System.out.println("As a Reviewer, You have papers that need to be reviewed:");
                
                for (Paper p : unReviewedPapers) {
            		info.out.printf("    %-30.30s (%s)\n", p.getTitle(), p.getSubmissionDate().toString());
                }
            }
        }
        
        if (rc.isSubProgramChair) {
            List<Paper> assignedPapers = rc.getSubProgramChairRole().getPapers();
            
            if (assignedPapers.isEmpty()) {
                System.out.println("As a SubProgramChair, You have no papers assigned to you!");
            } else {
                System.out.println("As a SubProgramChair, You have been assigned the papers:");
                
                for (Paper p : assignedPapers) {
            		info.out.printf("    %-30.30s (%s)\n", p.getTitle(), p.getSubmissionDate().toString());
                }
            }
        }
	}
	
}

interface Action {
    void run(UserInfo info);
}
