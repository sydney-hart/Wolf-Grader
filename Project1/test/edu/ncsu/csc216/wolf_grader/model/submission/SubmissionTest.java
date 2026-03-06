package edu.ncsu.csc216.wolf_grader.model.submission;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Submission Class
 * 
 * @author Sydney Hart
 */
class SubmissionTest {

	private static final int SUBMISSION_ID = 1;
	private static final String NAME = "Sydney Hart";
	private static final String UNITY_ID = "sehart";
	private static final String CHECK_RESULT = "NONE";
	private static final boolean FEEDBACK_PROCESSED = false;
	private static final boolean PUBLISHED = false;
	private static final String GRADER = "sesmith";
	private static final String GRADE = "A";
	private static final String STATE = "Upload";
	
    @BeforeEach
    void setUp() {
        Submission.setCounter(1); // reset static counter before each test
    }
	
	@Test
	public void testSubmissionNameAndID() {
		Submission s = assertDoesNotThrow(
				() -> new Submission(NAME, UNITY_ID), "Should not throw exception");
		assertAll("Submission",
				() -> assertEquals(SUBMISSION_ID, s.getId(), "incorrect id"),
				() -> assertEquals(STATE, s.getState(), "incorrect state"),
				() -> assertEquals(NAME, s.getName(), "incorrect name"),
				() -> assertEquals(UNITY_ID, s.getUnityId(), "incorrect unity id"),
				() -> assertEquals(FEEDBACK_PROCESSED, s.isFeedbackProcessed(), "incorrect feedback value"),
				() -> assertEquals(CHECK_RESULT, s.getCheckResult(), "incorrect check result"),
				() -> assertEquals(PUBLISHED, s.isPublished(), "incorrect published value"),
				() -> assertEquals("", s.getGrader(), "incorrect grader"),
				() -> assertEquals("", s.getGrade(), "incorrect grade"));
	}
	
	@Test
	public void testSubmission() {
		Submission s = assertDoesNotThrow(
				() -> new Submission(SUBMISSION_ID, STATE, NAME, UNITY_ID, FEEDBACK_PROCESSED, CHECK_RESULT, PUBLISHED, GRADER, GRADE), "Should not throw exception");
		assertAll("Submission",
				() -> assertEquals(SUBMISSION_ID, s.getId(), "incorrect id"),
				() -> assertEquals(STATE, s.getState(), "incorrect state"),
				() -> assertEquals(NAME, s.getName(), "incorrect name"),
				() -> assertEquals(UNITY_ID, s.getUnityId(), "incorrect unity id"),
				() -> assertEquals(FEEDBACK_PROCESSED, s.isFeedbackProcessed(), "incorrect feedback value"),
				() -> assertEquals(CHECK_RESULT, s.getCheckResult(), "incorrect check result"),
				() -> assertEquals(PUBLISHED, s.isPublished(), "incorrect published value"),
				() -> assertEquals(GRADER, s.getGrader(), "incorrect grader"),
				() -> assertEquals(GRADE,s.getGrade(), "incorrect grade"));
	}
	


}
