package edu.ncsu.csc216.wolf_grader.model.io;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	public static ArrayList<Assignment> readAssignmentFile(String fileName) {
		ArrayList<Assignment> assignments = new ArrayList<>();
	    String fileContents = "";
	    try (Scanner fileReader = new Scanner(new FileInputStream(fileName))) {
	        while (fileReader.hasNextLine()) {
	            fileContents += fileReader.nextLine() + "\n";
	        }
	    } catch (FileNotFoundException e) {
	        throw new IllegalArgumentException("Unable to load file");
	    }
	    if (fileContents.isEmpty() || !fileContents.trim().startsWith("#")) {
	        return assignments;
	    }
	    String[] assignmentTokens = fileContents.split("\\r?\\n?#");
	    for (String s : assignmentTokens) {
	        if (!s.trim().isEmpty()) {
	            try {
	                Assignment assignment = processAssignment(s);
	                assignments.add(assignment);
	            } catch (Exception e) {
	            }
	        }
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
	    if (!in.hasNextLine()) {
	        in.close();
	        throw new IllegalArgumentException();
	    }
	    String header = in.nextLine();
	    Assignment assignment = processAssignmentLine(header);
	    while (in.hasNextLine()) {
	        String subLine = in.nextLine().trim();
	        if (!subLine.isEmpty()) {
	            try {
	                assignment.addSubmission(processSubmission(subLine));
	            } catch (Exception e) {
	            }
	        }
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
	    Scanner lineScanner = new Scanner(assignmentLine);
	    lineScanner.useDelimiter(",");
	    try {
	        String name = lineScanner.next().trim();
	        String category = lineScanner.next().trim();
	        int maxPoints = lineScanner.nextInt();
	        lineScanner.close();
	        return new Assignment(name, category, maxPoints);
	    } catch (Exception e) {
	        lineScanner.close();
	        throw new IllegalArgumentException("Invalid assignment header");
	    }
	}
	
	/**
	 * Processes a single line of text representing a submission entry
	 * 
	 * @param submissionLine line containing submission-specific information
	 * @return Submission object created from data given
	 */
	private static Submission processSubmission(String submissionLine) {
	    if (!submissionLine.startsWith("*")) {
	        throw new IllegalArgumentException();
	    }
	    String data = submissionLine.substring(1).trim();
	    Scanner lineScanner = new Scanner(data);
	    lineScanner.useDelimiter(",");
	    
	    try {
	        int id = lineScanner.nextInt();
	        String state = lineScanner.next().trim();
	        String name = lineScanner.next().trim();
	        String unityId = lineScanner.next().trim();
	        boolean feedback = lineScanner.nextBoolean();
	        String result = lineScanner.next().trim();
	        boolean published = lineScanner.nextBoolean();
	        String grader = lineScanner.next().trim();
	        String grade = lineScanner.next().trim();
	        
	        lineScanner.close();
	        return new Submission(id, state, name, unityId, feedback, result, published, grader, grade);
	    } catch (Exception e) {
	        lineScanner.close();
	        throw new IllegalArgumentException("Invalid submission line");
	    }
	}
}
