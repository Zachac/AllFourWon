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
	
	
	//CM.getConferences
	//conference.getRoles
	//choose conference
	//submit paper
		//create paper
			//new Paper(file, authors, title)
			//paper.setLastEdit(date)
		//conference.submitPaper(Paper);
	//remove paper
		//conference.getPapers(Author);
		//conference.removePaper(Paper);

	
	//CM.getConferences
	//conference.getRoles
	//choose conference
	//assign reviewer
		//conference.getPapers(SubProgramChair)
		//choose paper
		//conference.getReviewers();
		//choose reviewer
		//dreviewer.assign(Paper);
		//repeat?
	
	
	
	
	
	
}
