package edu.ncsu.csc216.wolf_grader.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
	public static ArrayList<Assignment> readAssignmentFile(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		String fileContents = "";
		while (fileReader.hasNextLine()) {
			String nextLine = fileReader.nextLine() + "\n";
			fileContents += nextLine;
		}
		fileReader.close();
		if (fileContents.charAt(0) != '#') {
			return assignments;
		}
		String[] assignmentTokens = fileContents.split("\\r?\\n?[#]");
		for (String s: assignmentTokens) {
			Assignment assignment = processAssignment(s);
			assignments.add(assignment);
		}
		return assignments;
	}
	
	/**
	 * Processes a block of text representing a single assignment 
	 * and its associated submissions
	 * 
	 * @param assignmentText the full text block for one assignment
	 * @return the constructed Assignment object
	 */
	private static Assignment processAssignment(String assignmentText) {
		Scanner in = new Scanner(assignmentText);
		String assignmentHeader = in.nextLine();
		Assignment assignment = processAssignmentLine(assignmentHeader);
		
		while (in.hasNextLine()) {
			String submissionLine = in.nextLine();
			Submission s = processSubmission(submissionLine);
			assignment.addSubmission(s);
		}
		in.close();
		return assignment;
	}
	
	/**
	 * Processes a single line of text representing as assignment header
	 * 
	 * @param assignmentLine a line containing assignment information
	 * @return Assignment object initialized with the data given
	 */
	private static Assignment processAssignmentLine(String assignmentLine) {
		Scanner in = new Scanner(assignmentLine);
		in.useDelimiter(",");
		try {
			String assignmentName = in.next().substring(2);
			String assignmentCategory = in.next();
			int maxPoints = in.nextInt();
			Assignment assignment = new Assignment(assignmentName, assignmentCategory, maxPoints);
			in.close();
			return assignment;
		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Incorrect input.");
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Element not found.");
		}
	}
	
	/**
	 * Processes a single line of text representing a submission entry
	 * 
	 * @param submissionLine line containing submission-specific information
	 * @return Submission object created from data given
	 */
	private static Submission processSubmission(String submissionLine) {
		Scanner in = new Scanner(submissionLine);
		in.useDelimiter(",");
		try {
			String idString = in.next().substring(2);
			int submissionId = Integer.parseInt(idString);
			String state = in.next();
			String name = in.next();
			String unityId = in.next();
			boolean feedbackProcessed = in.nextBoolean();
			String checkResult = in.next();
			boolean published = in.nextBoolean();
			String graderId = in.next();
			String grade = in.next();
			Submission submission = new Submission(submissionId, state, name, unityId, feedbackProcessed, checkResult, published, graderId, grade);
			in.close();
			return submission;
		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Incorrect input.");
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Element not found.");
		}
	}
}
