package edu.ncsu.csc216.wolf_grader.model.io;

import java.util.ArrayList;

import edu.ncsu.csc216.wolf_grader.model.manager.Assignment;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Reads Assignment records from text files and creates Assignment objects
 * 
 * @author Sydney Hart
 */
public class AssignmentReader {

	/**
	 * Reads assignment records from a file and generates a list of valid Assignments. Any invalid
	 * Assignments are ignored. If the file to read cannot be found or the permissions are incorrect
	 * a FileNotFoundException is thrown
	 * 
	 * @param fileName file to read Assignment records from
	 * @return a list of valid Assignments
	 */
	public static ArrayList<Assignment> readAssignmentFile(String fileName) {
		return null;
	}
	
	/**
	 * Processes a block of text representing a single assignment 
	 * and its associated submissions
	 * 
	 * @param assignmentText the full text block for one assignment
	 * @return the constructed Assignment object
	 */
	private static Assignment processAssignment(String assignmentText) {
		return null;
	}
	
	/**
	 * Processes a single line of text representing as assignment header
	 * 
	 * @param assignmentLine a line containing assignment information
	 * @return Assignment object initialized with the data given
	 */
	private static Assignment processAssignmentLine(String assignmentLine) {
		return null;
	}
	
	/**
	 * Processes a single line of text representing a submission entry
	 * 
	 * @param submissionLine line containing submission-specific information
	 * @return Submission object created from data given
	 */
	private static Submission processSubmission(String submissionLine) {
		return null;
	}
}
