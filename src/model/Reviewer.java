package model;

public class Reviewer implements Role {

	@Override
	public String getUser() {
		return null;
	}
	
	public boolean addPaper(Paper p) {
		return false;
	}
	
	public boolean isAtPaperLimit() {
		return false;
	}

}
