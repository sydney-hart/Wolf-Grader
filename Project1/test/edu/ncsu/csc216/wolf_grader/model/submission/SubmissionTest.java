package edu.ncsu.csc216.wolf_grader.model.submission;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;

public class SubmissionTest {

    @BeforeEach
    public void resetCounter() {
        Submission.setCounter(1);
    }

    /**
     * Test constructor with name and unityId
     */
    @Test
    public void testSubmissionConstructorSimple() {
        Submission s = new Submission("John Doe", "jdoe");

        assertEquals(1, s.getId());
        assertEquals("John Doe", s.getName());
        assertEquals("jdoe", s.getUnityId());
        assertEquals("UPLOAD", s.getState().toUpperCase());
        assertEquals("NONE", s.getCheckResult());
        assertFalse(s.isFeedbackProcessed());
        assertFalse(s.isPublished());
    }

    /**
     * Test full constructor
     */
    @Test
    public void testSubmissionConstructorFull() {
        Submission s = new Submission(5, "UPLOAD", "Jane Doe", "jdoe",
                false, "PASS", false, "grader1", "A");

        assertEquals(5, s.getId());
        assertEquals("Jane Doe", s.getName());
        assertEquals("jdoe", s.getUnityId());
        assertEquals("PASS", s.getCheckResult());
        assertEquals("grader1", s.getGrader());
        assertEquals("A", s.getGrade());
    }

    /**
     * Test invalid name
     */
    @Test
    public void testSetNameInvalid() {

        Submission s = new Submission("John Doe", "jdoe");

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            s.setName(null);
        });

        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test invalid unityId
     */
    @Test
    public void testSetUnityIdInvalid() {

        Submission s = new Submission("John Doe", "jdoe");

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            s.setUnityId("");
        });

        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test invalid state
     */
    @Test
    public void testInvalidState() {

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Submission(1, "INVALID", "John", "jdoe", false, "NONE", false, null, null);
        });

        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test check result setter
     */
    @Test
    public void testSetCheckResult() {

        Submission s = new Submission("John Doe", "jdoe");

        s.setCheckResult("PASS");

        assertEquals("PASS", s.getCheckResult());
    }

    /**
     * Test invalid check result
     */
    @Test
    public void testInvalidCheckResult() {

        Submission s = new Submission("John Doe", "jdoe");

        assertThrows(IllegalArgumentException.class, () -> {
            s.setCheckResult("BAD");
        });
    }

    /**
     * Test grader getter when null
     */
    @Test
    public void testGetGraderNull() {

        Submission s = new Submission("John Doe", "jdoe");

        assertEquals("", s.getGrader());
    }

    /**
     * Test grade getter when null
     */
    @Test
    public void testGetGradeNull() {

        Submission s = new Submission("John Doe", "jdoe");

        assertEquals("", s.getGrade());
    }

    /**
     * Test toString output
     */
    @Test
    public void testToString() {

        Submission s = new Submission(3, "UPLOAD", "John Doe", "jdoe",
                false, "NONE", false, null, null);

        String expected = "* 3,John Doe,jdoe,false,NONE,false,null,null";

        assertEquals(expected, s.toString());
    }

    /**
     * Test update with null command
     */
    @Test
    public void testUpdateNullCommand() {

        Submission s = new Submission("John Doe", "jdoe");

        assertThrows(UnsupportedOperationException.class, () -> {
            s.update(null);
        });
    }

    /**
     * Test basic state transition (Upload -> Feedback)
     */
    @Test
    public void testAssignCommand() {

        Submission s = new Submission("John Doe", "jdoe");

        Command c = new Command(CommandValue.ASSIGN, "grader1");

        s.update(c);

        assertEquals("Feedback", s.getState());
        assertEquals("grader1", s.getGrader());
    }
    
    @Test
    void testSubmittedAndGradeState() {
        // Start in SUBMITTED state using the complex constructor
        Submission s = new Submission(1, "SUBMITTED", "Name", "id", false, "NONE", false, null, null);
        assertEquals("Submitted", s.getState());

        // 1. Test SubmittedState -> GradeState
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        assertEquals("Grade", s.getState());
        assertEquals("grader1", s.getGrader());

        // 2. Test GradeState -> CheckState (ANALYZE command)
        // Note: checkResult must be NONE for this to work
        s.update(new Command(CommandValue.ANALYZE, "B"));
        assertEquals("Check", s.getState());
        assertEquals("B", s.getGrade());
    }

    @Test
    void testCheckAndReturnState() {
        // Start in CHECK state
        Submission s = new Submission(1, "CHECK", "Name", "id", false, "NONE", false, "grader1", "B");
        
        // 1. Test CheckState -> ReturnState (PASS command)
        s.update(new Command(CommandValue.PASS, null));
        assertEquals("Return", s.getState());
        assertEquals("PASS", s.getCheckResult());

        // 2. Test ReturnState -> RegradeState (REQUEST command)
        s.update(new Command(CommandValue.REQUEST, "A"));
        assertEquals("Regrade", s.getState());
        assertEquals("A", s.getGrade());
    }

    @Test
    void testRegradeState() {
        // Start in REGRADE state
        Submission s = new Submission(1, "REGRADE", "Name", "id", false, "PASS", false, "grader1", "A");
        
        // 1. Test RegradeState -> GradeState (PROCESS command)
        s.update(new Command(CommandValue.PROCESS, null));
        assertEquals("Grade", s.getState());
    }

    @Test
    void testGradeStateComplexTransitions() {
        // Test the 'SUBMIT' branch in GradeState which requires checkResult != NONE
        Submission s = new Submission(1, "GRADE", "Name", "id", false, "FAIL", false, "grader1", "C");
        
        // GradeState -> ReturnState (via SUBMIT)
        s.update(new Command(CommandValue.SUBMIT, "F"));
        assertEquals("Return", s.getState());
        assertEquals("F", s.getGrade());
    }

    @Test
    void testStateErrorBranches() {
        Submission s = new Submission(1, "SUBMITTED", "Name", "id", false, "NONE", false, null, null);
        
        // Trigger null command exception (present in all updateState methods)
        assertThrows(UnsupportedOperationException.class, () -> s.update(null));

        // Trigger Invalid command for current state (SubmittedState only likes ASSIGN)
        Command invalid = new Command(CommandValue.PROCESS, null);
        assertThrows(UnsupportedOperationException.class, () -> s.update(invalid));
    }
}