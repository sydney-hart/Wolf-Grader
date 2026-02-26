package edu.ncsu.csc216.wolf_grader.model.manager;

import java.util.List;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * 
 */
public class Assignment {

	/** **/
	private String assignmentName;
	/** **/
	private String assignmentCategory;
	/** **/
	private int maxPoints;
	
	/**
	 * @param assignmentName
	 * @param assignmentCategory
	 * @param maxPoints
	 */
	public Assignment(String assignmentName, String assignmentCategory, int maxPoints) {
		this.assignmentName = assignmentName;
		this.assignmentCategory = assignmentCategory;
		this.maxPoints = maxPoints;
	}
	
	/**
	 * 
	 */
	public void setSubmissionId() {
		
	}
	
	/**
	 * @param assignmentName the assignmentName to set
	 */
	private void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}


	/**
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}
	
	/**
	 * @param assignmentCategory the assignmentCategory to set
	 */
	private void setAssignmentCategory(String category) {
		this.assignmentCategory = category;
	}

	/**
	 * @return the assignmentCategory
	 */
	public String getAssignmentCategory() {
		return assignmentCategory;
	}
	
	/**
	 * @param maxPoints the maxPoints to set
	 */
	private void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	/**
	 * @return the maxPoints
	 */
	public int getMaxPoints() {
		return maxPoints;
	}
	
	/**
	 * 
	 * @param name
	 * @param unityId
	 * @return
	 */
	public int addSubmission(String name, String unityId) {
		return 0;
	}
	
	/**
	 * 
	 * @param submission
	 * @return
	 */
	public int addSubmission(Submission submission) {
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Submission> getSubmissions() {
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Submission getSubmissionById(int id) {
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @param c
	 */
	public void executeCommand(int id, Command c) {
		
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteSubmissionById(int id) {
	
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Assignment [assignmentName=" + assignmentName + ", assignmentCategory=" + assignmentCategory
				+ ", maxPoints=" + maxPoints + "]";
	}
	
}
