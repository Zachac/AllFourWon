package model;

import java.util.List;

public class Paper {
	
	public List<String> getAuthors() {
		return null;
	}
	
	public boolean addAuthor(Author a) {
		return false;
	}
	
	public boolean removeAuthor(Author a) {
		return false;
	}
	
	public List<Review> getReviews() {
		return null;
	}
	
	public boolean addReview(Review r) {
		return false;
	}
	
	public boolean removeReview(Review r) {
		return false;
	}
	
	public boolean isReviewed() {
		return false;
	}
	
	public boolean isAccepted() {
		return false;
	}
	
	public String getTitle() {
		return null;
	}
	
	public void setTitle(String s) {
		
	}
	
	public void setDocument() {
		/*
		 *  suggestion, copy document into local directory and
		 *  then just save the file path of the copied file.
		 *  -Zach
		 */
	}
	
	public String getDocument() {
		/*
		 * suggestion, return file path of the file.
		 * -Zach
		 */
		return null;
	}
	
}
