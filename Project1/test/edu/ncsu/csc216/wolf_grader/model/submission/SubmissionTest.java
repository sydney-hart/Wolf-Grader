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
	private static final String STATE1 = "Upload";
	/** Initial Submission state with grader and grade **/
	private static final String STATE2 = "Check";
	/** Student's name **/
	private static final String NAME = "Sydney Hart";
	/** Student's unity id **/
	private static final String UNITY_ID = "sehart";
	/** Initial Plagiarism check value**/
	private static final String CHECK_RESULT = "Not Done";
	/** Initial feedback processed value **/
	private static final boolean FEEDBACK_PROCESSED = true;
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
		Submission.setCounter(0);
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
        		() -> new Submission(SUBMISSION_ID, STATE2, NAME, UNITY_ID, FEEDBACK_PROCESSED,
        				CHECK_RESULT, PUBLISHED, GRADER, GRADE));
        
        assertAll("Submission",
        		() -> assertEquals(SUBMISSION_ID, s.getId(), "incorrect submission ID"),
        		() -> assertEquals(STATE2, s.getState(), "incorrect state"),
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
        Submission s = new Submission(SUBMISSION_ID, STATE1, NAME, UNITY_ID, FEEDBACK_PROCESSED,
				CHECK_RESULT, PUBLISHED, null, null);

        String expected = "* 5,Upload,Sydney Hart,sehart,true,Not Done,false,,";

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
    
    @Test
    public void testInvalidNullState() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, null, "Sydney Hart", "sehart", false, "Not Done", false, "", ""));
    }

    @Test
    public void testInvalidBadState() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "BadState", "Sydney Hart", "sehart", false, "Not Done", false, "", ""));
    }

    @Test
    public void testInvalidFeedbackProcessedInFeedback() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Feedback", "Sydney Hart", "sehart", true, "Not Done", false, "grader1", ""));
    }

    @Test
    public void testInvalidFeedbackProcessedFalseInSubmitted() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Submitted", "Sydney Hart", "sehart", false, "Not Done", false, "", ""));
    }

    @Test
    public void testInvalidGraderInSubmitted() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Submitted", "Sydney Hart", "sehart", true, "Not Done", false, "grader1", ""));
    }

    @Test
    public void testInvalidNoGraderInFeedback() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Feedback", "Sydney Hart", "sehart", false, "Not Done", false, "", ""));
    }

    @Test
    public void testInvalidPassedCheckInUpload() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Upload", "Sydney Hart", "sehart", false, "Passed", false, "", ""));
    }

    @Test
    public void testInvalidPassedCheckInFeedback() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Feedback", "Sydney Hart", "sehart", false, "Passed", false, "grader1", ""));
    }

    @Test
    public void testInvalidPassedCheckInSubmitted() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Submitted", "Sydney Hart", "sehart", true, "Passed", false, "", ""));
    }

    @Test
    public void testInvalidPassedCheckInCheck() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Check", "Sydney Hart", "sehart", true, "Passed", false, "grader1", "A"));
    }

    @Test
    public void testInvalidRegradeWithFailed() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Regrade", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "A"));
    }

    @Test
    public void testInvalidRegradeWithFAIV() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Regrade", "Sydney Hart", "sehart", true, "Passed", false, "grader1", "F-AIV"));
    }

    @Test
    public void testInvalidReturnNoDone() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Return", "Sydney Hart", "sehart", true, "Not Done", false, "grader1", "A"));
    }

    @Test
    public void testInvalidReturnPassedWithFAIV() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Return", "Sydney Hart", "sehart", true, "Passed", false, "grader1", "F-AIV"));
    }

    @Test
    public void testInvalidReturnPassedWithIN() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Return", "Sydney Hart", "sehart", true, "Passed", false, "grader1", "IN"));
    }

    @Test
    public void testInvalidReturnFailedWithLetterGrade() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "A"));
    }

    @Test
    public void testInvalidPublishedNotReturn() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Upload", "Sydney Hart", "sehart", false, "Not Done", true, "", ""));
    }

    @Test
    public void testInvalidPublishedWithIN() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", true, "grader1", "IN"));
    }

    @Test
    public void testInvalidGradeInUpload() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Upload", "Sydney Hart", "sehart", false, "Not Done", false, "", "A"));
    }

    @Test
    public void testInvalidNoGradeInCheck() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Check", "Sydney Hart", "sehart", true, "Not Done", false, "grader1", ""));
    }

    @Test
    public void testInvalidFAIVInCheck() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Check", "Sydney Hart", "sehart", true, "Not Done", false, "grader1", "F-AIV"));
    }

    @Test
    public void testInvalidGradeStateNotDoneWithGrade() {
        assertThrows(IllegalArgumentException.class, () ->
            new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Not Done", false, "grader1", "A"));
    }
    
    /**
     * Tests that Submission can correctly change states
     */
    @Test
    public void testStateChangesReviseA() {
    	Submission s = new Submission(SUBMISSION_ID, STATE1, NAME, UNITY_ID, false,
				CHECK_RESULT, PUBLISHED, null, null);
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
    
    @Test
    public void testUploadAssignTransitionToFeedback() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        assertEquals("Feedback", s.getState());
        assertEquals("grader1", s.getGrader());
    }

    @Test
    public void testUploadAssignFailsWhenFeedbackProcessed() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.ASSIGN, "grader1")));
    }

    @Test
    public void testUploadMoveToSubmitted() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        s.update(new Command(CommandValue.MOVE, null));
        assertEquals("Submitted", s.getState());
    }

    @Test
    public void testUploadReupload() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        s.update(new Command(CommandValue.REUPLOAD, null));
        assertEquals("Upload", s.getState());
    }

    @Test
    public void testUploadReuploadFailsWithoutFeedback() {
        Submission s = new Submission("Sydney Hart", "sehart");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.REUPLOAD, null)));
    }

    @Test
    public void testUploadMoveFailsWithoutFeedback() {
        Submission s = new Submission("Sydney Hart", "sehart");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.MOVE, null)));
    }

    @Test
    public void testUploadInvalidCommand() {
        Submission s = new Submission("Sydney Hart", "sehart");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.SUBMIT, "A")));
    }

    // FEEDBACK STATE TESTS

    @Test
    public void testFeedbackProvideTransition() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        assertEquals("Upload", s.getState());
        assertTrue(s.isFeedbackProcessed());
        assertEquals("", s.getGrader());
    }

    @Test
    public void testFeedbackInvalidCommand() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.SUBMIT, "A")));
    }

    // SUBMITTED STATE TESTS

    @Test
    public void testSubmittedAssignToGrade() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        s.update(new Command(CommandValue.MOVE, null));
        s.update(new Command(CommandValue.ASSIGN, "grader2"));
        assertEquals("Grade", s.getState());
        assertEquals("grader2", s.getGrader());
    }

    @Test
    public void testSubmittedInvalidCommand() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        s.update(new Command(CommandValue.MOVE, null));
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.SUBMIT, "A")));
    }

    // ========================
    // GRADE STATE TESTS
    // ========================

    private Submission getSubmissionInGradeState() {
        Submission s = new Submission("Sydney Hart", "sehart");
        s.update(new Command(CommandValue.ASSIGN, "grader1"));
        s.update(new Command(CommandValue.PROVIDE, null));
        s.update(new Command(CommandValue.MOVE, null));
        s.update(new Command(CommandValue.ASSIGN, "grader2"));
        return s;
    }

    @Test
    public void testGradeAnalyzeToCheck() {
        Submission s = getSubmissionInGradeState();
        s.update(new Command(CommandValue.ANALYZE, "A"));
        assertEquals("Check", s.getState());
        assertEquals("A", s.getGrade());
    }

    @Test
    public void testGradeAnalyzeFailsWhenNotNone() {
        Submission s = new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.ANALYZE, "A")));
    }

    @Test
    public void testGradeSubmitPassedLetterGrade() {
        Submission s = new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Passed", false, "grader1", "");
        s.update(new Command(CommandValue.SUBMIT, "B"));
        assertEquals("Return", s.getState());
        assertEquals("B", s.getGrade());
    }

    @Test
    public void testGradeSubmitFailedFAIV() {
        Submission s = new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "");
        s.update(new Command(CommandValue.SUBMIT, "F-AIV"));
        assertEquals("Return", s.getState());
        assertEquals("F-AIV", s.getGrade());
    }

    @Test
    public void testGradeSubmitFailedIN() {
        Submission s = new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "");
        s.update(new Command(CommandValue.SUBMIT, "IN"));
        assertEquals("Return", s.getState());
        assertEquals("IN", s.getGrade());
    }

    @Test
    public void testGradeSubmitFailedLetterGradeResetsToPass() {
        Submission s = new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "");
        s.update(new Command(CommandValue.SUBMIT, "A"));
        assertEquals("Return", s.getState());
        assertEquals("Passed", s.getCheckResult());
    }

    @Test
    public void testGradeSubmitNoneThrows() {
        Submission s = getSubmissionInGradeState();
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.SUBMIT, "A")));
    }

    @Test
    public void testGradeSubmitPassedFAIVThrows() {
        Submission s = new Submission(1, "Grade", "Sydney Hart", "sehart", true, "Passed", false, "grader1", "");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.SUBMIT, "F-AIV")));
    }

    @Test
    public void testGradeInvalidCommand() {
        Submission s = getSubmissionInGradeState();
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.PUBLISH, null)));
    }

    // CHECK STATE TESTS

    @Test
    public void testCheckPassToReturn() {
        Submission s = getSubmissionInGradeState();
        s.update(new Command(CommandValue.ANALYZE, "A"));
        s.update(new Command(CommandValue.PASS, null));
        assertEquals("Return", s.getState());
        assertEquals("Passed", s.getCheckResult());
    }

    @Test
    public void testCheckFailToGrade() {
        Submission s = getSubmissionInGradeState();
        s.update(new Command(CommandValue.ANALYZE, "A"));
        s.update(new Command(CommandValue.FAIL, null));
        assertEquals("Grade", s.getState());
        assertEquals("Failed", s.getCheckResult());
    }

    @Test
    public void testCheckInvalidCommand() {
        Submission s = getSubmissionInGradeState();
        s.update(new Command(CommandValue.ANALYZE, "A"));
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.SUBMIT, "A")));
    }

    // REGRADE STATE TESTS

    private Submission getSubmissionInReturnState() {
        Submission s = getSubmissionInGradeState();
        s.update(new Command(CommandValue.ANALYZE, "A"));
        s.update(new Command(CommandValue.PASS, null));
        return s;
    }

    @Test
    public void testRegradeProcessToGrade() {
        Submission s = getSubmissionInReturnState();
        s.update(new Command(CommandValue.REQUEST, null));
        s.update(new Command(CommandValue.PROCESS, null));
        assertEquals("Grade", s.getState());
    }

    @Test
    public void testRegradeInvalidCommand() {
        Submission s = getSubmissionInReturnState();
        s.update(new Command(CommandValue.REQUEST, null));
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.PUBLISH, null)));
    }

    // RETURN STATE TESTS

    @Test
    public void testReturnPublish() {
        Submission s = getSubmissionInReturnState();
        s.update(new Command(CommandValue.PUBLISH, null));
        assertTrue(s.isPublished());
        assertEquals("Return", s.getState());
    }

    @Test
    public void testReturnPublishAlreadyPublishedThrows() {
        Submission s = getSubmissionInReturnState();
        s.update(new Command(CommandValue.PUBLISH, null));
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.PUBLISH, null)));
    }

    @Test
    public void testReturnRequestRegrade() {
        Submission s = getSubmissionInReturnState();
        s.update(new Command(CommandValue.REQUEST, null));
        assertEquals("Regrade", s.getState());
    }

    @Test
    public void testReturnRequestRegradeFailsWithIN() {
        Submission s = new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "IN");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.REQUEST, null)));
    }

    @Test
    public void testReturnRequestRegradeFailsWithFAIV() {
        Submission s = new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "F-AIV");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.REQUEST, null)));
    }

    @Test
    public void testReturnResolveIN() {
        Submission s = new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "IN");
        s.update(new Command(CommandValue.RESOLVE, "B"));
        assertEquals("Return", s.getState());
        assertEquals("B", s.getGrade());
        assertEquals("Passed", s.getCheckResult());
    }

    @Test
    public void testReturnResolveINToFAIV() {
        Submission s = new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "IN");
        s.update(new Command(CommandValue.RESOLVE, "F-AIV"));
        assertEquals("Return", s.getState());
        assertEquals("F-AIV", s.getGrade());
        assertEquals("Failed", s.getCheckResult());
    }

    @Test
    public void testReturnResolveFailsWhenNotIN() {
        Submission s = getSubmissionInReturnState();
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.RESOLVE, "B")));
    }

    @Test
    public void testReturnPublishFailsWithIN() {
        Submission s = new Submission(1, "Return", "Sydney Hart", "sehart", true, "Failed", false, "grader1", "IN");
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.PUBLISH, null)));
    }

    @Test
    public void testReturnInvalidCommand() {
        Submission s = getSubmissionInReturnState();
        assertThrows(UnsupportedOperationException.class, () ->
            s.update(new Command(CommandValue.ASSIGN, "grader1")));
    }

}