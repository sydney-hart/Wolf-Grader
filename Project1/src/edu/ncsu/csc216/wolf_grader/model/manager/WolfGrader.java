package edu.ncsu.csc216.wolf_grader.model.manager;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * 
 */
public class WolfGrader {

	/** **/
	private static WolfGrader singleton;

	/**
	 * 
	 */
	public WolfGrader() {
	}
	
	/**
	 * 
	 * @return
	 */
	public static synchronized WolfGrader getInstance() {
		return null;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public void loadAssignmentsFromFile(String fileName) {
		
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public void saveAssignmentsToFile(String fileName) {
		
	}
	
	/**
	 * 
	 * @param assignmentName
	 * @param assignmentCategory
	 * @param maxPoints
	 */
	public void addNewAssignment(String assignmentName, String assignmentCategory, int maxPoints) {
		
	}
	
	/**
	 * 
	 * @param assignmentName
	 */
	public void loadAssignment(String assignmentName) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Assignment getActiveAssignment() {
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getActiveAssignmentName() {
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getAssignmentList() {
		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @param unityId
	 */
	public void addSubmissionToAssignment(String name, String unityId) {
		
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
	 * @param stateName
	 * @return
	 */
	public String[][] getSubmissionsAsArray(String stateName) {
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
	 */
	protected void resetManager() {
		
	}
}
