package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.lang.reflect.Field;
import edu.ncsu.csc216.wolf_grader.model.command.Command;

class WolfGraderTest {
    private WolfGrader manager;

    @BeforeEach
    void setUp() throws Exception {
        manager = WolfGrader.getInstance();
        manager.resetManager();
        manager = WolfGrader.getInstance();

        // Initialize the internal list via reflection since constructor doesn't
        Field assignmentsField = WolfGrader.class.getDeclaredField("assignments");
        assignmentsField.setAccessible(true);
        assignmentsField.set(manager, new ArrayList<Assignment>());
    }

    @Test
    void testAssignmentAndSubmissionLogic() {
        // Covers addNewAssignment, loadAssignment (now working), and getActiveAssignmentName
        manager.addNewAssignment("Project 1", "CSC216", 100);
        assertEquals("Project 1", manager.getActiveAssignmentName());
        assertNotNull(manager.getActiveAssignment());

        // Covers addSubmissionToAssignment and getSubmissionById
        manager.addSubmissionToAssignment("Alice student", "astudent");
        // We assume the first submission ID is 0 or 1 based on your counter logic
        assertNotNull(manager.getSubmissionById(0)); 
        
        // Covers getSubmissionsAsArray (All branch)
        String[][] allSubmissions = manager.getSubmissionsAsArray("All");
        assertEquals(1, allSubmissions.length);
        assertEquals("astudent", allSubmissions[0][2]);

        // Covers getSubmissionsAsArray (State filter branch)
        String[][] uploadSubmissions = manager.getSubmissionsAsArray("Upload");
        assertEquals(1, uploadSubmissions.length);
    }

    @Test
    void testCommandAndDeletion() {
        manager.addNewAssignment("Project 2", "CSC216", 100);
        manager.addSubmissionToAssignment("Bob", "bobby");
        
        // Covers executeCommand
        Command c = new Command(Command.CommandValue.ASSIGN, "grader_smith");
        manager.executeCommand(0, c); 
        
        // Covers deleteSubmissionById
        manager.deleteSubmissionById(0);
        assertEquals(0, manager.getSubmissionsAsArray("All").length);
    }

    @Test
    void testErrorBranches() {
        // Covers saveAssignmentsToFile exception
        assertThrows(IllegalArgumentException.class, () -> manager.saveAssignmentsToFile("invalid.txt"));
        
        // Covers addNewAssignment duplicate exception
        manager.addNewAssignment("Duplicate", "CSC216", 100);
        assertThrows(IllegalArgumentException.class, () -> manager.addNewAssignment("Duplicate", "CSC216", 100));
    }

    @Test
    void testFilteringSubmissionsByState() {
        WolfGrader manager = WolfGrader.getInstance();
        manager.resetManager();
        manager = WolfGrader.getInstance();
        
        manager.addNewAssignment("FilteredTask", "Lab", 50);
        manager.addSubmissionToAssignment("Student A", "sa");
        manager.addSubmissionToAssignment("Student B", "sb");
        
        // Move Student A to 'Feedback' state via command
        // This ensures the filter loop has different states to look at
        int firstId = Integer.parseInt(manager.getSubmissionsAsArray("All")[0][0]);
        manager.executeCommand(firstId, new Command(Command.CommandValue.ASSIGN, "grader1"));

        // Coverage for the 'if' branches in getSubmissionsAsArray
        String[][] all = manager.getSubmissionsAsArray("All");
        String[][] feedbackOnly = manager.getSubmissionsAsArray("Feedback");
        String[][] uploadOnly = manager.getSubmissionsAsArray("Upload");

        assertTrue(all.length == 2);
        assertEquals(1, feedbackOnly.length);
        assertEquals(1, uploadOnly.length);
    }

    @Test
    void testGetSubmissionByIdNotFound() {
        WolfGrader manager = WolfGrader.getInstance();
        manager.resetManager();
        manager = WolfGrader.getInstance();
        
        manager.addNewAssignment("FindMe", "Exam", 100);
        manager.addSubmissionToAssignment("User", "u1");

        // Hit the 'return null' after the loop finishes without finding the ID
        assertNull(manager.getSubmissionById(-999));
        
        // Hit the branch where activeAssignment is null
        manager.resetManager();
        manager = WolfGrader.getInstance();
        assertNull(manager.getSubmissionById(1));
    }
    
    @Test
    void testGetActiveAssignmentNameNullBranch() {
        WolfGrader manager = WolfGrader.getInstance();
        manager.resetManager();
        manager = WolfGrader.getInstance();
        
        // Hits the 'return null' when activeAssignment is null
        assertNull(manager.getActiveAssignmentName());
    }
}