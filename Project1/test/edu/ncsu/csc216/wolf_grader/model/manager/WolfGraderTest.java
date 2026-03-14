package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests WolfGrader object, with emphasis on the singleton pattern
 * 
 * @author Sydney Hart
 */
class WolfGraderTest {

	/** Creates the instance of WolfGrader that will be used for testing **/
	private WolfGrader manager;
	/** Valid assignment records **/
	private final String validTestFile = "test-files/expected_assignments.txt";
	
	/**
	 * Resets the single instance of the WolfGrader
	 * 
	 * @throws Exception
	 */
    @BeforeEach
    void setUp() throws Exception {
        manager = WolfGrader.getInstance();
        manager.resetManager();
        manager = WolfGrader.getInstance();
        
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_expected_assignments.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "expected_assignments.txt");
        try {
        	Files.deleteIfExists(destinationPath);
        	Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
        	fail("Unable to reset files");
        }
    }
    
    /**
     * Test loadAssignmentsFromFile()
     */
    @Test
    public void testLoadAssignmentsFromFile() {
    	manager.loadAssignmentsFromFile(validTestFile);
    	assertEquals(3, manager.getAssignmentList().length, "Tests correct amount of assignments were read");
    	Assignment assignment1 = manager.getActiveAssignment();
    	assertEquals(3, assignment1.getSubmissions().size(), "Test first assignment has all submissions");
    }

    /**
     * Test addNewAssignment()
     */
    @Test
    public void testAddNewAssignment() {
    	Assignment expected = new Assignment("Practice Problems", "Homework", 50);
    	manager.addNewAssignment("Practice Problems", "Homework", 50);
    	assertEquals(expected.getAssignmentName(), manager.getActiveAssignment().getAssignmentName());
    	assertEquals(expected.getAssignmentCategory(), manager.getActiveAssignment().getAssignmentCategory());
    	assertEquals(expected.getMaxPoints(), manager.getActiveAssignment().getMaxPoints());
    	Exception e = assertThrows(IllegalArgumentException.class, () ->
    		manager.addNewAssignment("Practice Problems", "Homework", 50));
    	assertEquals("Assignment cannot be created.", e.getMessage());
    }
    
    /**
     * Test loadAssignments()
     */
    @Test
    public void testLoadAssignments() {
    	manager.loadAssignmentsFromFile(validTestFile);
    	assertEquals(3, manager.getAssignmentList().length, "Tests correct amount of assignments were read");
    	manager.loadAssignment("Assignment B");
    	assertEquals("Assignment B", manager.getActiveAssignment().getAssignmentName());
    	
    	Exception e = assertThrows(IllegalArgumentException.class, () ->
    			manager.loadAssignment("Practice Problems"));
    	assertEquals("Assignment not available.", e.getMessage());
    }
    
    /**
     * Test getAssignmentList()
     */
    @Test
    public void testGetAssignmentList() {
    	String assignment1 = "Assignment A";
    	String assignment2 = "Assignment B";
    	String assignment3 = "Assignment D";
    	String[] expectedNames = {assignment1, assignment2, assignment3};
    	manager.loadAssignmentsFromFile(validTestFile);
    	String[] actualNames = manager.getAssignmentList();
    	for (int i = 0; i < expectedNames.length; i++) {
    		assertEquals(expectedNames[i], actualNames[i]);
    	}
    }
    
    /**
     * Test addSubmissionToAssignment()
     */
    @Test
    public void testAddSubmissionToAssignment() {
    	manager.addNewAssignment("Practice Problems", "Homework", 50);
    	assertEquals("Practice Problems", manager.getActiveAssignment().getAssignmentName());
    	manager.addSubmissionToAssignment("Sydney Hart", "sehart");
    	assertEquals(1, manager.getActiveAssignment().getSubmissions().size());
    }
    
    /**
     * Test executeCommand()
     */
    @Test
    public void testExecuteCommand() {
    	manager.loadAssignmentsFromFile(validTestFile);
    	Command assign = new Command(CommandValue.ASSIGN, "sehart");
    	manager.executeCommand(14, assign);
    	Submission s = manager.getActiveAssignment().getSubmissionById(14);
    	assertEquals("Feedback", s.getState());
    }
    
    /**
     * Test deleteSubmissionById()
     */
    @Test
    public void testDeleteSubmissionById() {
    	manager.loadAssignmentsFromFile(validTestFile);
    	manager.deleteSubmissionById(14);
    	assertEquals(2, manager.getActiveAssignment().getSubmissions().size());
    	assertEquals("Carol Smith", manager.getActiveAssignment().getSubmissionById(2).getName());
    	assertEquals("Sara Mendez", manager.getActiveAssignment().getSubmissionById(4).getName());
    	assertNull(manager.getActiveAssignment().getSubmissionById(14));
    }
    
    /**
     * Test getSubmissionAsArray()
     * Also test filtering ability
     */
    @Test
    public void testFilteringSubmissionsByState() {
    	
	  manager.addNewAssignment("Lab Report", "Lab", 50);
	  manager.addSubmissionToAssignment("Sydney Hart", "sehart");
	  manager.addSubmissionToAssignment("Katie Hart", "kbhart");
	  
	  manager.executeCommand(1, new Command(CommandValue.ASSIGN, "sesmith"));

	  String[][] all = manager.getSubmissionsAsArray("All");
	  String[][] feedbackOnly = manager.getSubmissionsAsArray("Feedback");
	  String[][] uploadOnly = manager.getSubmissionsAsArray("Upload");
	
	  assertTrue(all.length == 2);
	  assertEquals(1, feedbackOnly.length);
	  assertEquals(1, uploadOnly.length);
    }
    
    /**
     * Test saveAssignments()
     */
    @Test
    public void testSaveAssignments() {
    	manager.addNewAssignment("Lab Report", "Lab", 50);
    	manager.addSubmissionToAssignment("Sydney Hart", "sehart");
    	manager.saveAssignmentsToFile("test-files/actual_save_assignments.txt");
    	checkFiles("test-files/expected_save_assignments.txt", "test-files/actual_save_assignments.txt");
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