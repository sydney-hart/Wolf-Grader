package edu.ncsu.csc216.wolf_grader.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.manager.Assignment;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests the AssignmentReader Class
 * 
 * @author Sydney Hart
 */
class AssignmentReaderTest {

	private final String validTestFile = "test-files/assignment1.txt";
	
	@Test
	public void testAssignmentReader() {
		ArrayList<Assignment> assignments = AssignmentReader.readAssignmentFile(validTestFile);
		Assignment a = assignments.get(0);
		assertAll("Assignment information",
				() -> assertEquals("Problem Set 1", a.getAssignmentName()),
				() -> assertEquals("Problem Set", a.getAssignmentCategory()),
				() -> assertEquals(50, a.getMaxPoints()),
				() -> assertEquals(7, a.getSubmissions().size())
		);

		Submission s1 = a.getSubmissions().get(0);
		assertAll("First submission",
				() -> assertEquals(2, s1.getId()),
				() -> assertEquals("Carol Smith", s1.getName()),
				() -> assertEquals("csmith", s1.getUnityId())
		);
	}
	
	@Test
	public void testInvalidFile() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> AssignmentReader.readAssignmentFile("does_not_exist.txt"));
		assertEquals("Unable to load file", e.getMessage());
	}

}
