package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WolfGraderTest {
    private WolfGrader manager;

    @BeforeEach
    void setUp() {
        manager = WolfGrader.getInstance();
        manager.resetManager(); // Start fresh for every test
        manager = WolfGrader.getInstance();
    }

    @Test
    void testAssignmentLifecycle() {
        // 1. Initial State (Coverage for null returns)
        assertNull(manager.getActiveAssignment());
        assertNull(manager.getActiveAssignmentName());

        // 2. Add Assignment (Covers addNewAssignment and loop for equality)
        // Note: Initializing the internal list if it's null in your code might be needed 
        // depending on your AssignmentReader/Constructor implementation.
        manager.addNewAssignment("CSC216", "Project", 100);
        assertEquals("CSC216", manager.getActiveAssignmentName());
        
        // 3. Get List (Covers getAssignmentList loop)
        String[] names = manager.getAssignmentList();
        assertEquals(1, names.length);
        assertEquals("CSC216", names[0]);
    }

    @Test
    void testGetSubmissionsAsArray() {
        manager.addNewAssignment("CSC216", "Project", 100);
        
        // Add a submission to trigger the array logic
        manager.addSubmissionToAssignment("John Doe", "jdoe");
        
        // This covers the loops in getSubmissionsAsArray for "All" and filtered views
        String[][] allData = manager.getSubmissionsAsArray("All");
        assertTrue(allData.length >= 1);
        assertEquals("jdoe", allData[0][2]);
        
        // Hit the filtered path
        String[][] uploadData = manager.getSubmissionsAsArray("Upload");
        assertEquals(1, uploadData.length);
    }

    @Test
    void testSaveFailures() {
        // Test save without an active assignment
        assertThrows(IllegalArgumentException.class, () -> manager.saveAssignmentsToFile("test.txt"));
    }

    @Test
    void testGetSubmissionById() {
        manager.addNewAssignment("CSC216", "Project", 100);
        manager.addSubmissionToAssignment("Student", "sid");
        
        // Since we don't know the exact ID (static counter), we find it first
        String[][] data = manager.getSubmissionsAsArray("All");
        int id = Integer.parseInt(data[0][0]);
        
        assertNotNull(manager.getSubmissionById(id));
        assertNull(manager.getSubmissionById(-1)); // Coverage for loop finishing without find
    }
}