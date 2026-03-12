package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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
    	/** This assignment was already added so this should thrown an exception **/
    	Exception e = assertThrows(IllegalArgumentException.class, () ->
    		manager.addNewAssignment("Practice Problems", "Homework", 50));
    	assertEquals("Assignment not available", e.getMessage());
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
    
    
//
//    @Test
//    void testCommandAndDeletion() {
//        manager.addNewAssignment("Project 2", "CSC216", 100);
//        manager.addSubmissionToAssignment("Bob", "bobby");
//        
//        // Covers executeCommand
//        Command c = new Command(Command.CommandValue.ASSIGN, "grader_smith");
//        manager.executeCommand(0, c); 
//        
//        // Covers deleteSubmissionById
//        manager.deleteSubmissionById(0);
//        assertEquals(0, manager.getSubmissionsAsArray("All").length);
//    }
//
//    @Test
//    void testErrorBranches() {
//        // Covers saveAssignmentsToFile exception
//        assertThrows(IllegalArgumentException.class, () -> manager.saveAssignmentsToFile("invalid.txt"));
//    }
//
//    @Test
//    void testFilteringSubmissionsByState() {
//        WolfGrader manager = WolfGrader.getInstance();
//        manager.resetManager();
//        manager = WolfGrader.getInstance();
//        
//        manager.addNewAssignment("FilteredTask", "Lab", 50);
//        manager.addSubmissionToAssignment("Student A", "sa");
//        manager.addSubmissionToAssignment("Student B", "sb");
//        
//        // Move Student A to 'Feedback' state via command
//        // This ensures the filter loop has different states to look at
//        int firstId = Integer.parseInt(manager.getSubmissionsAsArray("All")[0][0]);
//        manager.executeCommand(firstId, new Command(Command.CommandValue.ASSIGN, "grader1"));
//
//        // Coverage for the 'if' branches in getSubmissionsAsArray
//        String[][] all = manager.getSubmissionsAsArray("All");
//        String[][] feedbackOnly = manager.getSubmissionsAsArray("Feedback");
//        String[][] uploadOnly = manager.getSubmissionsAsArray("Upload");
//
//        assertTrue(all.length == 2);
//        assertEquals(1, feedbackOnly.length);
//        assertEquals(1, uploadOnly.length);
//    }
//
//    @Test
//    void testGetSubmissionByIdNotFound() {
//        WolfGrader manager = WolfGrader.getInstance();
//        manager.resetManager();
//        manager = WolfGrader.getInstance();
//        
//        manager.addNewAssignment("FindMe", "Exam", 100);
//        manager.addSubmissionToAssignment("User", "u1");
//
//        // Hit the 'return null' after the loop finishes without finding the ID
//        assertNull(manager.getSubmissionById(-999));
//        
//        // Hit the branch where activeAssignment is null
//        manager.resetManager();
//        manager = WolfGrader.getInstance();
//        assertNull(manager.getSubmissionById(1));
//    }
//    
//    @Test
//    void testGetActiveAssignmentNameNullBranch() {
//        WolfGrader manager = WolfGrader.getInstance();
//        manager.resetManager();
//        manager = WolfGrader.getInstance();
//        
//        // Hits the 'return null' when activeAssignment is null
//        assertNull(manager.getActiveAssignmentName());
//    }
}