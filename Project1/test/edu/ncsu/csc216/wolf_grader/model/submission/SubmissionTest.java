package edu.ncsu.csc216.wolf_grader.model.submission;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;

/**
 * Tests the Submission class, including all inner classes
 * and interface
 * 
 * @author Sydney Hart
 */
public class SubmissionTest {

	/** Submission ID **/
	private static final int SUBMISSION_ID = 5;
	/** Initial Submission state **/
	private static final String STATE = "Upload";
	/** Student's name **/
	private static final String NAME = "Sydney Hart";
	/** Student's unity id **/
	private static final String UNITY_ID = "sehart";
	/** Initial Plagiarism check value**/
	private static final String CHECK_RESULT = "Not Done";
	/** Initial feedback processed value **/
	private static final boolean FEEDBACK_PROCESSED = false;
	/** Initial published value **/
	private static final boolean PUBLISHED = false;
	/** Submission grader **/
	private static final String GRADER = "sesmith";
	/** Submission grade **/
	private static final String GRADE = "A";
	
	
	/**
	 * Resets the counter back to zero when tests are re-run
	 * 
	 * @throws Exception throws an exception if there are any errors with the counter
	 */
	@BeforeEach
	public void setUp() throws Exception {
		//Reset the counter at the beginning of every test.
		Submission.setCounter(1);
	}

    /**
     * Test constructor with name and unityId
     */
    @Test
    public void testSubmissionConstructorSimple() {
        Submission s = assertDoesNotThrow(
        		() -> new Submission(NAME, UNITY_ID));
        
        assertAll("Submission",
        		() -> assertEquals(1, s.getId(), "incorrect submission ID"),
        		() -> assertEquals("Upload", s.getState(), "incorrect state"),
        		() -> assertEquals(NAME, s.getName(), "incorrect name"),
        		() -> assertEquals(UNITY_ID, s.getUnityId(), "incorrect unity ID"),
        		() -> assertFalse(s.isFeedbackProcessed(), "incorrect feedback processed"),
        		() -> assertEquals("Not Done", s.getCheckResult(), "incorrect plagiarism check result"),
        		() -> assertEquals("", s.getGrader(), "incorrect grader"),
        		() -> assertEquals("", s.getGrade(), "incorrect grade"));
    }

    /**
     * Test full constructor
     */
    @Test
    public void testSubmissionConstructorFull() {
    	Submission s = assertDoesNotThrow(
        		() -> new Submission(SUBMISSION_ID, STATE, NAME, UNITY_ID, FEEDBACK_PROCESSED,
        				CHECK_RESULT, PUBLISHED, GRADER, GRADE));
        
        assertAll("Submission",
        		() -> assertEquals(SUBMISSION_ID, s.getId(), "incorrect submission ID"),
        		() -> assertEquals(STATE, s.getState(), "incorrect state"),
        		() -> assertEquals(NAME, s.getName(), "incorrect name"),
        		() -> assertEquals(UNITY_ID, s.getUnityId(), "incorrect unity ID"),
        		() -> assertEquals(FEEDBACK_PROCESSED, s.isFeedbackProcessed(), "incorrect feedback processed"),
        		() -> assertEquals(CHECK_RESULT, s.getCheckResult(), "incorrect plagiarism check result"),
        		() -> assertEquals(GRADER, s.getGrader(), "incorrect grader"),
        		() -> assertEquals(GRADE, s.getGrade(), "incorrect grade"));
    }

    /**
     * Test invalid name
     */
    @Test
    public void testSetNameInvalid() {
        Submission s = new Submission(NAME, UNITY_ID);
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            s.setName(null));
        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test invalid unityId
     */
    @Test
    public void testSetUnityIdInvalid() {
        Submission s = new Submission(NAME, UNITY_ID);
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            s.setUnityId(""));
        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test invalid state
     */
    @Test
    public void testInvalidState() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> 
            new Submission(SUBMISSION_ID, "INVALID", NAME, UNITY_ID, FEEDBACK_PROCESSED, 
            		CHECK_RESULT, PUBLISHED, GRADER, GRADE));
        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test check result setter
     */
    @Test
    public void testSetCheckResult() {
        Submission s = new Submission(NAME, UNITY_ID);
        s.setCheckResult("Passed");
        assertEquals("Passed", s.getCheckResult());
        s.setCheckResult("Failed");
        assertEquals("Failed", s.getCheckResult());
        s.setCheckResult("Not Done");
        assertEquals("Not Done", s.getCheckResult());
    }

    /**
     * Test invalid check result
     */
    @Test
    public void testInvalidCheckResult() {
        Submission s = new Submission(NAME, UNITY_ID);
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            s.setCheckResult("BAD"));
        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Test toString output
     */
    @Test
    public void testToString() {
        Submission s = new Submission(SUBMISSION_ID, STATE, NAME, UNITY_ID, FEEDBACK_PROCESSED,
				CHECK_RESULT, PUBLISHED, GRADER, GRADE);

        String expected = "* 5,Upload,Sydney Hart,sehart,false,Not Done,false,sesmith,A";

        assertEquals(expected, s.toString());
    }
    
    /**
     * Test update with null command
     */
    @Test
    public void testUpdateNullCommand() {
        Submission s = new Submission(NAME, UNITY_ID);
        Exception e = assertThrows(UnsupportedOperationException.class, () -> 
            s.update(null));
        assertEquals("Invalid command.", e.getMessage());
    }
    
    /**
     * Tests that Submission can correctly change states
     */
    @Test
    public void testStateChangesReviseA() {
    	Submission s = new Submission(SUBMISSION_ID, STATE, NAME, UNITY_ID, FEEDBACK_PROCESSED,
				CHECK_RESULT, PUBLISHED, GRADER, GRADE);
    	assertEquals("Upload", s.getState(), "incorrect state: UPLOAD");
    	
    	Command assign = new Command(CommandValue.ASSIGN, "sesmith");
    	s.update(assign);
    	assertEquals("Feedback", s.getState(), "incorrect state: FEEDBACK");
    	
    	Command provide = new Command(CommandValue.PROVIDE, null);
    	s.update(provide);
    	assertEquals("Upload", s.getState(), "incorrect state: Upload2");
    	assertTrue(s.isFeedbackProcessed());
    	
    	Command reupload = new Command(CommandValue.REUPLOAD, null);
    	s.update(reupload);
    	assertEquals("Upload", s.getState(), "incorrect state: Reupload");
    	
    	Command move = new Command(CommandValue.MOVE, null);
    	s.update(move);
    	assertEquals("Submitted", s.getState(), "incorrect state: Submitted");
    	
    	s.update(assign);
    	assertEquals("Grade", s.getState(), "incorrect State: Grade");
    	
    	Command analyze = new Command(CommandValue.ANALYZE, "A");
    	s.update(analyze);
    	assertEquals("Check", s.getState(), "incorrect state: Check");
    	
    	Command fail = new Command(CommandValue.FAIL, null);
    	s.update(fail);
    	assertEquals("Grade", s.getState(), "incorrect state: Grade2");
    	assertEquals("Failed", s.getCheckResult(), "incorrect check result");
    	
    	Command submit = new Command(CommandValue.SUBMIT, "IN");
    	s.update(submit);
    	assertEquals("Return", s.getState(), "incorrect state: Return");
    	
    	Command resolve = new Command(CommandValue.RESOLVE, "B");
    	s.update(resolve);
    	assertEquals("Return", s.getState(), "incorrect state: Return2");
    	assertEquals("Passed", s.getCheckResult(), "incorrect check result");
    	
    	Command request = new Command(CommandValue.REQUEST, null);
    	s.update(request);
    	assertEquals("Regrade", s.getState(), "incorrect state: Regrade");
    	
    	Command process = new Command(CommandValue.PROCESS, null);
    	s.update(process);
    	assertEquals("Grade", s.getState(), "incorrect state: Grade3");
    	
    	Command submitUpdate = new Command(CommandValue.SUBMIT, "A");
    	s.update(submitUpdate);
    	assertEquals("Return", s.getState(), "incorrect state: Return3");
    	assertEquals("A", s.getGrade(), "incorrect grade");
    	
    	Command publish = new Command(CommandValue.PUBLISH, null);
    	s.update(publish);
    	assertEquals("Return", s.getState(), "incorrect state: Return4");
    	assertTrue(s.isPublished());
    }

}