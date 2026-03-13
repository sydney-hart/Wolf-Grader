package edu.ncsu.csc216.wolf_grader.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.manager.Assignment;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests the AssignmentReader Class
 * 
 * @author Sydney Hart
 */
class AssignmentReaderTest {

	/** Valid assignment records **/
	private final String validTestFile = "test-files/assignment1.txt";
	/** Assignment info **/
	private final String assignmentInfo = "# Problem Set 1,Problem Set,50";
	/** Submission 1 **/
	private final String submission1 = "* 2,Submitted,Carol Smith,csmith,true,Not Done,false,,";
	/** Submission 2 **/
	private final String submission2 = "* 3,Return,Kathleen Parker,kparker,true,Passed,true,sallen,A";
	/** Submission 3 **/
	private final String submission3 = "* 4,Feedback,Sara Mendez,smendez1,false,Not Done,false,skumar,";
	/** Submission 4 **/
	private final String submission4 = "* 7,Check,Kate Miller,kmiller,true,Not Done,false,lpatel,B";
	/** Submission 5 **/
	private final String submission5 = "* 8,Grade,Daniel Wright,dwright,true,Failed,false,lpatel,C";
	/** Submission 6 **/
	private final String submission6 = "* 11,Regrade,Nora Kim,nkim,true,Passed,false,jrivera,C";
	/** Submission 7 **/
	private final String submission7 = "* 14,Upload,Oliver Grant,ogrant,false,Not Done,false,,";
	
	/** Array to hold expected submissions **/
	private final String[] validSubmissions = {submission1, submission2, submission3, submission4,
				submission5, submission6, submission7};
	
	/**
	 * Tests readAssignmentFile()
	 * 
	 * Subsequently tests processAssignment, processAssignmentLine, processSubmission
	 */
	@Test
	public void testAssignmentReader() {
		ArrayList<Assignment> assignments = AssignmentReader.readAssignmentFile(validTestFile);
		assertEquals(1, assignments.size());
		assertEquals(assignmentInfo, assignments.get(0).toString());
		
		List<Submission> submissions = assignments.get(0).getSubmissions();
		for (int i = 0; i < validSubmissions.length; i++) {
			assertEquals(validSubmissions[i], submissions.get(i).toString());
		}
	}	
		
	
	/**
	 * Tests correct exception is thrown when invalid file is read
	 */
	@Test
	public void testInvalidFile() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> AssignmentReader.readAssignmentFile("does_not_exist.txt"));
		assertEquals("Unable to load file .", e.getMessage());
	}
	
	/**
	 * Tests correct exception is thrown when file is empty
	 */
	@Test
	public void testEmptyFile() {
		ArrayList<Assignment> assignments = new ArrayList<>();
		assertEquals(assignments, AssignmentReader.readAssignmentFile("test-files/empty.txt"));
	}
	
	/**
	 * Tests exception for invalid assignments and submissions
	 */
	@Test
	public void testExceptions() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> 
				AssignmentReader.readAssignmentFile("test-files/assignment3.txt"));
		assertEquals("Unable to load file .", e.getMessage());
		Exception f = assertThrows(IllegalArgumentException.class, () -> 
			AssignmentReader.readAssignmentFile("test-files/assignment8.txt"));
		assertEquals("Unable to load file .", f.getMessage());
	}

}
