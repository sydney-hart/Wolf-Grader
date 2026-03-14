package edu.ncsu.csc216.wolf_grader.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.io.AssignmentReader;
import edu.ncsu.csc216.wolf_grader.model.io.AssignmentWriter;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Central controller of the WolfGrader system
 * Follows Singleton design pattern
 * 
 * @author Sydney Hart
 */
public class WolfGrader {

	/** Single interface of WolfGrader **/
	private static WolfGrader singleton;
	/** **/
	private ArrayList<Assignment> assignments;
	/** **/
	private Assignment activeAssignment;
	
	/**
	 * Constructs a WolfGrader object
	 */
	public WolfGrader() {
		assignments = new ArrayList<>();
		activeAssignment = null;
		Submission.setCounter(0);	}
	
	/**
	 * Returns a single instance of the WolfGrader
	 * 
	 * @return the single WolfGrader instance
	 */
	public static synchronized WolfGrader getInstance() {
		if (singleton == null) {
			singleton = new WolfGrader();
		}
		return singleton;
	}
	
	/**
	 * Loads assignments from the specified file
	 * 
	 * @param fileName name of the file to load
	 */
	public void loadAssignmentsFromFile(String fileName) {
		assignments = AssignmentReader.readAssignmentFile(fileName);
		activeAssignment = assignments.get(0);
	}
	
	/**
	 * Saves all assignments to the specified file
	 * 
	 * @param fileName name of the file to save to
	 */
	public void saveAssignmentsToFile(String fileName) {
		if (activeAssignment == null) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		AssignmentWriter.writeAssignmentsToFile(fileName, assignments);
	}
	
	/**
	 * Creates and adds a new Assignment to the system
	 * 
	 * @param assignmentName name of the assignment
	 * @param assignmentCategory the category of the assignment
	 * @param maxPoints maximum number of points for the assignment
	 */
	public void addNewAssignment(String assignmentName, String assignmentCategory, int maxPoints) {
		for (Assignment a: assignments) {
			if (a.getAssignmentName().equals(assignmentName))   {
				throw new IllegalArgumentException("Assignment not available");
			}
		}
		Assignment assignment = new Assignment(assignmentName, assignmentCategory, maxPoints);
		assignments.add(assignment);
		loadAssignment(assignmentName);
	}
	
	/**
	 * Sets the active Assignment based on the provided name
	 * 
	 * @param assignmentName name of the assignment to activate
	 */
	public void loadAssignment(String assignmentName) {
		boolean found = false;
	    for (Assignment a: assignments) {
	        if (a.getAssignmentName().equals(assignmentName)) {
	            activeAssignment = a;
	            activeAssignment.setSubmissionId();
	            found = true;
	        }
	    }
	    if (!found) {
	        throw new IllegalArgumentException("Assignment not available.");
	    }
	}
	
	/**
	 * Returns the currently active Assignment
	 * 
	 * @return the active Assignment
	 */
	public Assignment getActiveAssignment() {
		return activeAssignment;
	}
	
	/**
	 * Returns the name of the currently active Assignment
	 * 
	 * @return active assignment name
	 */
	public String getActiveAssignmentName() {
		if (activeAssignment != null) {
			return activeAssignment.getAssignmentName();
		}
		return null;
	}
	
	/**
	 * Returns a list of all assignment names in the system
	 * 
	 * @return an array of assignment names
	 */
	public String[] getAssignmentList() {
		int length = assignments.size();
		String[] assignmentNames = new String[length];
		for (int i = 0; i < length; i++) {
			String name = assignments.get(i).getAssignmentName();
			assignmentNames[i] = name;
		}
		return assignmentNames;
	}
	
	/**
	 * Adds a new Submission to the currently active Assignment
	 * 
	 * @param name student's name
	 * @param unityId student's unity ID
	 */
	public void addSubmissionToAssignment(String name, String unityId) {
		if (activeAssignment != null) {
			activeAssignment.addSubmission(name, unityId);
		}
	}
	
	/**
	 * Executes a Command on a Submission within the active Assignment
	 * 
	 * @param id the submission ID
	 * @param c the Command to execute
	 */
	public void executeCommand(int id, Command c) {
		if (activeAssignment != null) {
			Submission submission = getSubmissionById(id);
			submission.update(c);
		}
	}
	
	/**
	 * Deletes a Submission from the active Assignment
	 * 
	 * @param id the submission ID
	 */
	public void deleteSubmissionById(int id) {
		if (activeAssignment != null) {
			activeAssignment.deleteSubmissionById(id);
		}
	}
	
	/**
	 * Returns submission data filtered by state in a 2D array format
	 * 
	 * @param stateName name of the state to filter submissions by
	 * @return 2D array of submission data
	 */
	public String[][] getSubmissionsAsArray(String stateName) {
		if (activeAssignment == null) {
			return null;
		}
		List<Submission> submissions = activeAssignment.getSubmissions();
		List<Submission> filtered = new ArrayList<Submission>();
		for (Submission s: submissions) {
			if (stateName.equals("All") || s.getState().equals(stateName)) {
				filtered.add(s);
			}
		}
		String[][] displaySubmissions = new String[filtered.size()][4];
		for (int i = 0; i < filtered.size(); i++) {
			Submission submission = filtered.get(i);
			displaySubmissions[i][0] = Integer.toString(submission.getId());
			displaySubmissions[i][1] = submission.getState();
			displaySubmissions[i][2] = submission.getName() + "(" + submission.getUnityId() + ")";
			displaySubmissions[i][3] = submission.getCheckResult();
		}
		return displaySubmissions;
	}
	
	/**
	 * Retrieves a Submission by its ID from the active Assignment
	 * 
	 * @param id submission ID
	 * @return matching Submission
	 */
	public Submission getSubmissionById(int id) {
		if (activeAssignment != null) {
			List<Submission> submissions = activeAssignment.getSubmissions();
			for (Submission s: submissions) {
				if (s.getId() == id) {
					return s;
				}
			}
		}
		return null;
	}
	
	/**
	 * Resets the WolfGrader manager to its initial state
	 */
	protected void resetManager() {
		singleton = null;
	}
}
