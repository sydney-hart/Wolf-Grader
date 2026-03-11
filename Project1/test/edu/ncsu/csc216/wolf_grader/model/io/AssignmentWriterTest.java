package edu.ncsu.csc216.wolf_grader.model.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_grader.model.manager.Assignment;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests the assignment writer
 * 
 * @author Sydney Hart
 */
class AssignmentWriterTest {
	

	/**
	 * Tests writeAssignmentsToFile()
	 */
    @Test
    void testWriteAssignmentsToFileValid() {
        List<Assignment> assignments = new ArrayList<>();
        
        Assignment A = new Assignment("Assignment A", "Assignment Category 1", 20);
        Submission twoA = new Submission(2, "Submitted", "Carol Smith", "csmith", true, "Not Done", false, null, null);
        Submission fourA = new Submission(4, "Feedback", "Sara Mendez", "smendez1", false, "Not Done", false, "skumar", null);
        Submission fourteenA = new Submission(14, "Upload", "Oliver Grant", "ogrant", false, "Not Done", false, null, null);
        A.addSubmission(twoA);
        A.addSubmission(fourA);
        A.addSubmission(fourteenA);
        
        Assignment B = new Assignment("Assignment B", "Assignment Category 2", 100);
        Submission fourB = new Submission(4, "Grade", "Daniel Wright", "dwright", true, "Passed", false, "shassan", "F");
        Submission sevenB = new Submission(7, "Check", "Kate Miller", "kmiller", true, "Not Done", false, "lpatel", "B");
        B.addSubmission(fourB);
        B.addSubmission(sevenB);
        
        Assignment C = new Assignment("Assignment D", "Assignment Category 4", 15);
        Submission fiveC = new Submission(5, "Return", "Jonah Stein", "jstein3", true, "Failed", false, "pnair", "F-AIV");
        Submission sevenC = new Submission(7, "Regrade", "Kate Miller", "kmiller", true, "Passed", false, "pnair", "B");
        C.addSubmission(fiveC);
        C.addSubmission(sevenC);
        
        assignments.add(A);
        assignments.add(B);
        assignments.add(C);
        
        AssignmentWriter.writeAssignmentsToFile("test-files/actual_records.txt", assignments);
        checkFiles("test-files/expected_assignments.txt", "test-files/actual_records.txt");
    }

    /**
     * Tests that correct exceptions are thrown when invalid file is given
     */
    @Test
    void testWriteAssignmentsToFileInvalid() {
        List<Assignment> assignments = new ArrayList<>();
        Exception e = assertThrows(IllegalArgumentException.class, () -> 
            AssignmentWriter.writeAssignmentsToFile("/", assignments));
        assertEquals("Unable to save file.", e.getMessage());
    }
    
    /**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
