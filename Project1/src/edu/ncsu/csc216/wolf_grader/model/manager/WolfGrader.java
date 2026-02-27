package edu.ncsu.csc216.wolf_grader.model.manager;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Central controller of the WolfGrader system
 * Follows Singleton design pattern
 * 
 * TODO author
 */
public class WolfGrader {

	/** Single interface of WolfGrader **/
	private static WolfGrader singleton;

	/**
	 * Constructs a WolfGrader object
	 */
	public WolfGrader() {
	}
	
	/**
	 * Returns a single instance of the WolfGrader
	 * 
	 * @return the single WolfGrader instance
	 */
	public static synchronized WolfGrader getInstance() {
		return null;
	}
	
	/**
	 * Loads assignments from the specified file
	 * 
	 * @param fileName name of the file to load
	 */
	public void loadAssignmentsFromFile(String fileName) {
		
	}
	
	/**
	 * Saves all assignments to the specified file
	 * 
	 * @param fileName name of the file to save to
	 */
	public void saveAssignmentsToFile(String fileName) {
		
	}
	
	/**
	 * Creates and adds a new Assignment to the system
	 * 
	 * @param assignmentName name of the assignment
	 * @param assignmentCategory the category of the assignment
	 * @param maxPoints maximum number of points for the assignment
	 */
	public void addNewAssignment(String assignmentName, String assignmentCategory, int maxPoints) {
		
	}
	
	/**
	 * Sets the active Assignment based on the provided name
	 * 
	 * @param assignmentName name of the assignment to activate
	 */
	public void loadAssignment(String assignmentName) {
		
	}
	
	/**
	 * Returns the currently active Assignment
	 * 
	 * @return the active Assignment
	 */
	public Assignment getActiveAssignment() {
		return null;
	}
	
	/**
	 * Returns the name of the currently active Assignment
	 * 
	 * @return active assignment name
	 */
	public String getActiveAssignmentName() {
		return "";
	}
	
	/**
	 * Returns a list of all assignment names in the system
	 * 
	 * @return an array of assignment names
	 */
	public String[] getAssignmentList() {
		return null;
	}
	
	/**
	 * Adds a new Submission to the currently active Assignment
	 * 
	 * @param name student's name
	 * @param unityId student's unity ID
	 */
	public void addSubmissionToAssignment(String name, String unityId) {
		
	}
	
	/**
	 * Executes a Command on a Submission within the active Assignment
	 * 
	 * @param id the submission ID
	 * @param c the Command to execute
	 */
	public void executeCommand(int id, Command c) {
		
	}
	
	/**
	 * Deletes a Submission from the active Assignment
	 * 
	 * @param id the submission ID
	 */
	public void deleteSubmissionById(int id) {
		
	}
	
	/**
	 * Returns submission data filtered by state in a 2D array format
	 * 
	 * @param stateName name of the state to filter submissions by
	 * @return 2D array of submission data
	 */
	public String[][] getSubmissionsAsArray(String stateName) {
		return null;
	}
	
	/**
	 * Retrieves a Submission by its ID from the active Assignment
	 * 
	 * @param id submission ID
	 * @return matching Submission
	 */
	public Submission getSubmissionById(int id) {
		return null;
	}
	
	/**
	 * Resets the WolfGrader manager to its initial state
	 */
	protected void resetManager() {
		
	}
}
