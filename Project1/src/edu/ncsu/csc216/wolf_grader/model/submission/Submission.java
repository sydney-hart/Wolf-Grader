package edu.ncsu.csc216.wolf_grader.model.submission;

import edu.ncsu.csc216.wolf_grader.model.command.Command;

/**
 * 
 */
public class Submission {

	/** **/
	private int submissionId;
	/** **/
	private String name;
	/** **/
	private String unityId;
	/** **/
	private PlagiarismCheckResult checkResult;
	/** **/
	private boolean feedbackProcessed;
	/** **/
	private boolean published;
	/** **/
	private String grader;
	/** **/
	private String grade;
	/** **/
	private static int counter;
	/** **/
	public static final String UPLOAD_NAME = "Upload";
	/** **/
	public static final String FEEDBACK_NAME = "Feedback";
	/** **/
	public static final String SUBMITTED_NAME = "Submitted";
	/** **/
	public static final String GRADE_NAME = "Grade";
	/** **/
	public static final String REGRADE_NAME = "Regrade";
	/** **/
	public static final String CHECK_NAME = "Check";
	/** **/
	public static final String RETURN_NAME = "Return";
	/** **/
	public static final String PC_PASS = "Passed";
	/** **/
	public static final String PC_FAIL = "Failed";
	/** **/
	public static final String PC_NONE = "Not Done";
	/** **/
	public static final String GRADE_A_RETURN = "A";
	/** **/
	public static final String GRADE_B_RETURN = "B";
	/** **/
	public static final String GRADE_C_RETURN = "C";
	/** **/
	public static final String GRADE_F_RETURN = "F";
	/** **/
	public static final String GRADE_IN_RETURN = "IN";
	/** **/
	public static final String GRADE_F_AIV_RETURN = "F-AIV";
	
	/** Comments go here */
	public enum PlagiarismCheckResult { PASS, FAIL, NONE }

	/** **/
	private SubmissionState currentState;
	/** **/
	private SubmissionState uploadState = new UploadState();
	/** **/
	private SubmissionState feedbackState = new FeedbackState();
	/** **/
	private SubmissionState submittedState = new SubmittedState();
	/** **/
	private SubmissionState gradeState = new GradeState();
	/** **/
	private SubmissionState checkState = new CheckState();
	/** **/
	private SubmissionState regradeState = new RegradeState();
	/** **/
	private SubmissionState returnState = new ReturnState();
	
	
	/**
	 * @param name
	 * @param unityId
	 */
	public Submission(String name, String unityId) {
		this.name = name;
		this.unityId = unityId;
	}

	/**
	 * @param submissionId
	 * @param name
	 * @param unityId
	 * @param checkResult
	 * @param feedbackProcessed
	 * @param published
	 * @param grader
	 * @param grade
	 */
	public Submission(int submissionId, String state, String name, String unityId,
			boolean feedbackProcessed, String checkResult, boolean published, String grader, String grade) {
		this.submissionId = submissionId;
		this.name = name;
		this.unityId = unityId;
		//this.checkResult = checkResult;
		this.feedbackProcessed = feedbackProcessed;
		this.published = published;
		this.grader = grader;
		this.grade = grade;
	}

	/**
	 * @param submissionId the submissionId to set
	 */
	public void setId(int id) {
		this.submissionId = id;
	}
	
	/**
	 * 
	 * @param stateValue
	 */
	public void setState(String stateValue) {
		
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param unityId the unityId to set
	 */
	public void setUnityId(String unityId) {
		this.unityId = unityId;
	}
	
	/**
	 * @param feedbackProcessed the feedbackProcessed to set
	 */
	public void setFeedbackProcessed(boolean feedbackProcessed) {
		this.feedbackProcessed = feedbackProcessed;
	}
	
	/**
	 * @param checkResult the checkResult to set
	 */
	public void setCheckResult(String checkResult) {

	}

	/**
	 * @param grader the grader to set
	 */
	public void setGrader(String grader) {
		this.grader = grader;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	/**
	 * @return the submissionId
	 */
	public int getId() {
		return submissionId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getState() {
		return "";
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the unityId
	 */
	public String getUnityId() {
		return unityId;
	}
	
	/**
	 * @return the checkResult
	 */
	public String getCheckResult() {
		return "";
	}
	
	/**
	 * @return the feedbackProcessed
	 */
	public boolean isFeedbackProcessed() {
		return feedbackProcessed;
	}
	
	/**
	 * @return the grader
	 */
	public String getGrader() {
		return grader;
	}
	
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @return the published
	 */
	public boolean isPublished() {
		return published;
	}

	/**
	 * 
	 */
	public static void incrementCounter() {
	
	}

	/**
	 * @param counter the counter to set
	 */
	public static void setCounter(int newCount) {
		Submission.counter = newCount;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Submission [submissionId=" + submissionId + ", name=" + name + ", unityId=" + unityId + ", checkResult="
				+ checkResult + ", feedbackProcessed=" + feedbackProcessed + ", published=" + published + ", grader="
				+ grader + ", grade=" + grade + "]";
	}
	
	/**
	 * 
	 * @param c
	 */
	public void update(Command c) {
		
	}
	
	/**
	 * Interface for states in the Submission State Pattern.  All 
	 * concrete Submission states must implement the SubmissionState interface.
	 * The SubmissionState interface should be a private interface of the 
	 * Submission class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 * @author Dr. Chandrika Satyavolu (jsatyav@ncsu.edu)
	 */
	private interface SubmissionState {
		
		/**
		 * Update the Submission from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Submission's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command command);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	/**
	 * 
	 */
	private class UploadState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 
	 */
	private class FeedbackState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 
	 */
	private class SubmittedState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 
	 */
	private class GradeState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * '
	 */
	private class CheckState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 
	 */
	private class RegradeState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 
	 */
	private class ReturnState implements SubmissionState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
