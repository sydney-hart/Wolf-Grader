package edu.ncsu.csc216.wolf_grader.model.io;

import java.io.File;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import edu.ncsu.csc216.wolf_grader.model.manager.Assignment;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Writes a set of Assignment Records to a file
 * 
 * @author Sydney Hart
 */
public class AssignmentWriter {

	/**
	 * Writes list of assignments to the filename provided by the user
	 * 
	 * @param fileName file to write assignments to
	 * @param assignments list of assignments to write
	 */
	public static void writeAssignmentsToFile(String fileName, List<Assignment> assignments) throws IOException {
		try {
			PrintStream fileWriter = new PrintStream(new File(fileName));
			for (Assignment a: assignments) {
				fileWriter.println(a.toString());
				List<Submission> submissions = a.getSubmissions();
				for (Submission s: submissions) {
					fileWriter.println(s.toString());
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
