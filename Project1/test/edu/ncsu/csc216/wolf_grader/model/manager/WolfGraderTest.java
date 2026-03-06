package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests the WolfGrader class
 */
public class WolfGraderTest {

    /**
     * Tests Singleton behavior
     */
    @Test
    public void testGetInstance() {
        WolfGrader wg1 = WolfGrader.getInstance();
        WolfGrader wg2 = WolfGrader.getInstance();

        assertSame(wg1, wg2);

        wg1.resetManager();
    }

    /**
     * Tests addNewAssignment and getAssignmentList
     */
    @Test
    public void testAddNewAssignment() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        int before = wg.getAssignmentList().length;

        wg.addNewAssignment("TestAssignment", "Homework", 50);

        int after = wg.getAssignmentList().length;

        assertEquals(before + 1, after);
        assertEquals("TestAssignment", wg.getActiveAssignmentName());

        wg.resetManager();
    }

    /**
     * Tests loadAssignment
     */
    @Test
    public void testLoadAssignment() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        String first = wg.getAssignmentList()[0];

        wg.loadAssignment(first);

        assertEquals(first, wg.getActiveAssignmentName());

        wg.resetManager();
    }

    /**
     * Tests addSubmissionToAssignment
     */
    @Test
    public void testAddSubmission() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        Assignment active = wg.getActiveAssignment();

        int before = active.getSubmissions().size();

        wg.addSubmissionToAssignment("John Doe", "jdoe");

        int after = active.getSubmissions().size();

        assertEquals(before + 1, after);

        wg.resetManager();
    }

    /**
     * Tests deleteSubmissionById
     */
    @Test
    public void testDeleteSubmission() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        Assignment active = wg.getActiveAssignment();

        int id = active.getSubmissions().get(0).getId();

        wg.deleteSubmissionById(id);

        assertNull(wg.getSubmissionById(id));

        wg.resetManager();
    }

    /**
     * Tests getSubmissionsAsArray
     */
    @Test
    public void testGetSubmissionsAsArray() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        String[][] submissions = wg.getSubmissionsAsArray("All");

        assertNotNull(submissions);
        assertTrue(submissions.length > 0);
        assertEquals(4, submissions[0].length);

        wg.resetManager();
    }

    /**
     * Tests executeCommand
     */
    @Test
    public void testExecuteCommand() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        Submission s = wg.getActiveAssignment().getSubmissions().get(0);
        int id = s.getId();

        Command c = new Command(CommandValue.SUBMIT, "Passed");

        wg.executeCommand(id, c);

        assertNotNull(wg.getSubmissionById(id));

        wg.resetManager();
    }

    /**
     * Tests getSubmissionById
     */
    @Test
    public void testGetSubmissionById() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        Submission s = wg.getActiveAssignment().getSubmissions().get(0);

        assertEquals(s, wg.getSubmissionById(s.getId()));

        wg.resetManager();
    }

    /**
     * Tests getActiveAssignmentName
     */
    @Test
    public void testGetActiveAssignmentName() {
        WolfGrader wg = WolfGrader.getInstance();
        wg.resetManager();
        wg = WolfGrader.getInstance();

        wg.loadAssignmentsFromFile("test-files/assignment1.txt");

        assertNotNull(wg.getActiveAssignmentName());

        wg.resetManager();
    }
}