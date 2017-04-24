package model;

import java.util.Date;
import java.util.List;

public class Conference {

	public void setProgramChair(ProgramChair pc) {
		
	}
	
	public boolean submitPaper(Paper p) {
		return false;
	}
	
	public boolean removePaper(Paper p) {
		return false;
	}
	
	public Date getDeadline() {
		return null;
	}
	
	public void setDeadline(Date d) {
		
	}
	
	public List<Role> getRoles(String user) {
		return null;
	}
	
	public List<String> getReviewers() {
		return null;
	}
	
	public boolean isPastSubmissionDeadline() {
		return false;
	}
	
	public boolean doesPaperExceedAuthorSubmissionAmount(Paper p) {
		return false;
	}
}
