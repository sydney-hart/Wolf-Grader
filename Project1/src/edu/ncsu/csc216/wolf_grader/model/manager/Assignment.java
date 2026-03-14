package edu.ncsu.csc216.wolf_grader.model.manager;

import java.util.ArrayList;

import java.util.List;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Represents a single Assignment in the WolfGrader system
 * 
 * @author Sydney Hart
 */
public class Assignment {

	/** Name of the assignment **/
	private String assignmentName;
	/** Category of the assignment **/
	private String assignmentCategory;
	/** The maximum number of points possible for this assignment **/
	private int maxPoints;
	/** List of submissions for the assignment **/
	private ArrayList<Submission> submissions;
	
	/**
	 * Constructs an Assignment with the specified name, category, 
	 * and maximum points
	 * 
	 * @param assignmentName name of the assignment
	 * @param assignmentCategory category of the assignment
	 * @param maxPoints maximum number of points of possible
	 * @throws IllegalArgumentException if one of the parameters is null, empty, or invalid
	 */
	public Assignment(String assignmentName, String assignmentCategory, int maxPoints) {
		setAssignmentName(assignmentName);
		setAssignmentCategory(assignmentCategory);
		setMaxPoints(maxPoints);
		submissions = new ArrayList<Submission>();
	}
	
	/**
	 * Sets or updates the submission ID counter
	 */
	public void setSubmissionId() {
		int maxId = 0;
		for (Submission s : submissions) {
			if (s.getId() > maxId) {
				maxId = s.getId();
			}
		}
		Submission.setCounter(maxId + 1);
	}
	
	/**
	 * Sets the assignment name
	 * 
	 * @param assignmentName the assignmentName to set
	 * @throws IllegalArgumentException if assignment name is null or empty
	 */
	private void setAssignmentName(String assignmentName) {
		if (assignmentName == null || assignmentName.isEmpty()) {
			throw new IllegalArgumentException("Assignment cannot be created.");
		}
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
	 * @param category the assignmentCategory to set
	 * @throws IllegalArgumentException if category name is null or empty
	 */
	private void setAssignmentCategory(String category) {
		if (category == null || category.isEmpty()) {
			throw new IllegalArgumentException("Assignment cannot be created.");
		}
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
	 * @throws IllegalArgumentException if maxPoints is under 1 or over 100
	 */
	private void setMaxPoints(int maxPoints) {
		if (maxPoints < 1 || maxPoints > 100) {
			throw new IllegalArgumentException("Assignment cannot be created.");
		}
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
	 * @throws IllegalArgumentException if there is already a submission with the same
	 * 		   id number
	 */
	public int addSubmission(String name, String unityId) {
		Submission submission = new Submission(name, unityId);
		int id = addSubmission(submission);
		return id;
	}
	
	/**
	 * Adds an existing Submission object to this assignment
	 * 
	 * @param submission the Submission to add
	 * @return the ID of the added submission
	 * @throws IllegalArgumentException if there is already a submission with the same
	 * 		   id number
	 */
	public int addSubmission(Submission submission) {
		for (int i = 0; i < submissions.size(); i++) {
			if (submissions.get(i).getId() == submission.getId()) {
				throw new IllegalArgumentException("Submission cannot be created.");
			} else if (submissions.get(i).getId() > submission.getId()) {
				submissions.add(i, submission);
				setSubmissionId();
				return submission.getId();
			}
		}
		submissions.add(submission);
		setSubmissionId();
		return submission.getId();
	}
	
	/**
	 * Returns a list of all submission for this assignment
	 * 
	 * @return a List of Submission objects
	 */
	public List<Submission> getSubmissions() {
		return submissions;
	}
	
	/**
	 * Retrieves a Submission by its unique ID
	 * 
	 * @param id the submission ID
	 * @return the matching Submission, or null if there is no matching submission
	 */
	public Submission getSubmissionById(int id) {
		for (Submission s: submissions) {
			if (s.getId() == id) {
				return s;
			}
		}
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
		for (Submission s: submissions) {
			if (s.getId() == id) {
				s.update(c);
			}
		}
	}
	
	/**
	 * Deletes the Submission with the specified ID
	 * 
	 * @param id the submission ID
	 */
	public void deleteSubmissionById(int id) {
		for (int i = 0; i < submissions.size(); i++) {
			if (submissions.get(i).getId() == id) {
				submissions.remove(i);
			}
		}
	}

	/**
	 * Returns a string representation of the Assignment
	 * 
	 * @return a formatted string containing assignment information
	 */
	@Override
	public String toString() {
		return "# " + assignmentName + "," + assignmentCategory
				+ "," + maxPoints;
	}
	
}
