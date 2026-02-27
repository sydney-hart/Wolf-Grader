package edu.ncsu.csc216.wolf_grader.model.manager;

import java.util.List;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Represents a single Assignment in the WolfGrader system
 * 
 * TODO author
 */
public class Assignment {

	/** Name of the assignment **/
	private String assignmentName;
	/** Category of the assignment **/
	private String assignmentCategory;
	/** The maximum number of points possible for this assignment **/
	private int maxPoints;
	
	/**
	 * Constructs an Assignment with the specified name, category, 
	 * and maximum points
	 * 
	 * @param assignmentName name of the assignment
	 * @param assignmentCategory category of the assignment
	 * @param maxPoints maximum number of points of possible
	 */
	public Assignment(String assignmentName, String assignmentCategory, int maxPoints) {
		this.assignmentName = assignmentName;
		this.assignmentCategory = assignmentCategory;
		this.maxPoints = maxPoints;
	}
	
	/**
	 * Sets or updates the submission ID counter
	 */
	public void setSubmissionId() {
		
	}
	
	/**
	 * Sets the assignment name
	 * 
	 * @param assignmentName the assignmentName to set
	 */
	private void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}


	/**
	 * Returns the assignment name
	 * 
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}
	
	/**
	 * Sets the assignment category
	 * 
	 * @param assignmentCategory the assignmentCategory to set
	 */
	private void setAssignmentCategory(String category) {
		this.assignmentCategory = category;
	}

	/**
	 * Returns the assignment category
	 * 
	 * @return the assignmentCategory
	 */
	public String getAssignmentCategory() {
		return assignmentCategory;
	}
	
	/**
	 * Sets the maximum points for the assignment
	 * 
	 * @param maxPoints the maxPoints to set
	 */
	private void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	/**
	 * Returns the maximum number of points for the assignment
	 * 
	 * @return the maxPoints
	 */
	public int getMaxPoints() {
		return maxPoints;
	}
	
	/**
	 * Creates and adds a new Submission to this assignment
	 * 
	 * @param name the student name
	 * @param unityId the student's unity ID
	 * @return the ID assigned to the new submission
	 */
	public int addSubmission(String name, String unityId) {
		return 0;
	}
	
	/**
	 * Adds an existing Submission object to this assignment
	 * 
	 * @param submission the Submission to add
	 * @return the ID of the added submission
	 */
	public int addSubmission(Submission submission) {
		return 0;
	}
	
	/**
	 * Returns a list of all submission for this assignment
	 * 
	 * @return a List of Submission objects
	 */
	public List<Submission> getSubmissions() {
		return null;
	}
	
	/**
	 * Retrieves a Submission by its unique ID
	 * 
	 * @param id the submission ID
	 * @return the matching Submission
	 */
	public Submission getSubmissionById(int id) {
		return null;
	}
	
	/**
	 * Executes a Command on the Submission with the specified ID.
	 * May trigger state transition
	 * 
	 * @param id the submission ID
	 * @param c the Command to execute
	 */
	public void executeCommand(int id, Command c) {
		
	}
	
	/**
	 * Deletes the Submission with the specified ID
	 * 
	 * @param id the submission ID
	 */
	public void deleteSubmissionById(int id) {
	
	}

	/**
	 * Returns a string representation of the Assignment
	 * 
	 * @return a formatted string containing assignment information
	 */
	@Override
	public String toString() {
		return "Assignment [assignmentName=" + assignmentName + ", assignmentCategory=" + assignmentCategory
				+ ", maxPoints=" + maxPoints + "]";
	}
	
}
