package edu.ncsu.csc216.wolf_grader.model.submission;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;

/**
 * Represents a student Submission for an Assignment
 * 
 * @author Sydney Hart
 */
public class Submission {

	/** Unique ID for this submission **/
	private int submissionId;
	/** Student's name **/
	private String name;
	/** Student's unity ID **/
	private String unityId;
	/** Result of the plagiarism check **/
	private PlagiarismCheckResult checkResult;
	/** True if feedback has been processed **/
	private boolean feedbackProcessed;
	/** True is grade has been published **/
	private boolean published;
	/** Name of assignment grader **/
	private String grader;
	/** Final letter grade assigned **/
	private String grade;
	/** Static counter used to generate unique submission IDs**/
	private static int counter;
	/** State Name Constant Upload**/
	public static final String UPLOAD_NAME = "Upload";
	/** State Name Constant Feedback**/
	public static final String FEEDBACK_NAME = "Feedback";
	/** State Name Constant Submitted**/
	public static final String SUBMITTED_NAME = "Submitted";
	/** State Name Constant Grade**/
	public static final String GRADE_NAME = "Grade";
	/** State Name Constant Re-grade**/
	public static final String REGRADE_NAME = "Regrade";
	/** State Name Constant Check**/
	public static final String CHECK_NAME = "Check";
	/** State Name Constant Return**/
	public static final String RETURN_NAME = "Return";
	/** Plagiarism Check Result Constant Passed **/
	public static final String PC_PASS = "Passed";
	/** Plagiarism Check Result Constant Failed **/
	public static final String PC_FAIL = "Failed";
	/** Plagiarism Check Result Constant Not Done**/
	public static final String PC_NONE = "Not Done";
	/** Grade Constant A **/
	public static final String GRADE_A_RETURN = "A";
	/** Grade Constant B **/
	public static final String GRADE_B_RETURN = "B";
	/** Grade Constant C **/
	public static final String GRADE_C_RETURN = "C";
	/** Grade Constant F **/
	public static final String GRADE_F_RETURN = "F";
	/** Grade Constant IN**/
	public static final String GRADE_IN_RETURN = "IN";
	/** Grade Constant F-AIV**/
	public static final String GRADE_F_AIV_RETURN = "F-AIV";
	
	/** Enumeration representing plagiarism check results */
	public enum PlagiarismCheckResult { PASS, FAIL, NONE }

	/** State Instances current **/
	private SubmissionState currentState;
	/** State Instances Upload **/
	private SubmissionState uploadState = new UploadState();
	/** State Instances Feedback **/
	private SubmissionState feedbackState = new FeedbackState();
	/** State Instances Submitted **/
	private SubmissionState submittedState = new SubmittedState();
	/** State Instances Grade **/
	private SubmissionState gradeState = new GradeState();
	/** State Instances Check **/
	private SubmissionState checkState = new CheckState();
	/** State Instances Re-grade **/
	private SubmissionState regradeState = new RegradeState();
	/** State Instances Return**/
	private SubmissionState returnState = new ReturnState();
	
	
	/**
	 * Constructs a new Submission with the given student name and unity ID
	 * 
	 * @param name student name
	 * @param unityId student unity ID
	 */
	public Submission(String name, String unityId) {
		this(counter, "UPLOAD", name, unityId, false, PC_NONE, false, null, null);
	}

	/**
	 * Constructs a Submission with all information
	 * 
	 * @param submissionId submission ID
	 * @param state state name
	 * @param name student name
	 * @param unityId student unity ID
	 * @param checkResult plagiarism check result
	 * @param feedbackProcessed feedback processed flag
	 * @param published published flag
	 * @param grader grader name
	 * @param grade assigned grade
	 * @throws IllegalArgumentException if any of the parameters are invalid values
	 */
	public Submission(int submissionId, String state, String name, String unityId,
			boolean feedbackProcessed, String checkResult, boolean published, String grader, String grade) {
		if (submissionId <= 0) {
			if (counter == 0) {
				submissionId = 1;
			} else {
				throw new IllegalArgumentException("Submission cannot be created.");
			}
		}
		setState(state);
		setFeedbackProcessed(feedbackProcessed);
		setName(name);
		setUnityId(unityId);
		setCheckResult(checkResult);
		setPublished(published);
		setGrader(grader);
		setGrade(grade);
		setId(submissionId);
	}

	/**
	 * Sets the Submission ID
	 * 
	 * @param submissionId the submissionId to set
	 */
	public void setId(int id) {
		if (id == 0) {
			this.submissionId = 1;
		} else {
			this.submissionId = id;
			if (id >= counter) {
				setCounter(id);
			}
		}
		incrementCounter();
	}
	
	/**
	 * Sets the Submission state value
	 * 
	 * @param stateValue the stateValue to set
	 * @throws IllegalArgumentException if stateValue String does not match one of 
	 * the valid options
	 */
	public void setState(String stateValue) {
		if (stateValue == null ) {
			throw new IllegalArgumentException("Submission cannot be created.");
		}
		stateValue = stateValue.toUpperCase();
		switch (stateValue) {
			case "UPLOAD": 
				this.currentState = uploadState;
				break;
			case "FEEDBACK":
				this.currentState = feedbackState;
				break;
			case "SUBMITTED":
				this.currentState = submittedState;
				break;
			case "GRADE":
				this.currentState = gradeState;
				break;
			case "REGRADE":
				this.currentState = regradeState;
				break;
			case "CHECK":
				this.currentState = checkState;
				break;
			case "RETURN":
				this.currentState = returnState;
				break;
			default:
				throw new IllegalArgumentException("Submission cannot be created.");
		}
	}
	
	/**
	 * Sets the Submission name
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null or empty
	 */
	public void setName(String name) {
		if (name ==  null || name.isEmpty()) {
			throw new IllegalArgumentException("Submission cannot be created.");
		}
		this.name = name;
	}
	
	/**
	 * Sets the Submission unity ID
	 * 
	 * @param unityId the unityId to set
	 * @throws IllegalArgumentException if unityId is null or empty
	 */
	public void setUnityId(String unityId) {
		if (unityId ==  null || unityId.isEmpty()) {
			throw new IllegalArgumentException("Submission cannot be created.");
		}
		this.unityId = unityId;
	}
	
	/**
	 * Sets the Submission feedback flag
	 * 
	 * @param feedbackProcessed the feedbackProcessed to set
	 */
	public void setFeedbackProcessed(boolean feedbackProcessed) {
		this.feedbackProcessed = feedbackProcessed;
	}
	
	/**
	 * Sets the Submission plagiarism check result
	 * 
	 * @param checkResult the checkResult to set
	 * @throws IllegalArgumentException if checkResult String does not match one of 
	 * the valid options
	 */
	public void setCheckResult(String checkResult) {
		switch (checkResult) {
			case PC_PASS: 
				this.checkResult = PlagiarismCheckResult.PASS;
				break;
			case PC_FAIL:
				this.checkResult = PlagiarismCheckResult.FAIL;
				break;
			case PC_NONE:
				this.checkResult = PlagiarismCheckResult.NONE;
				break;
			default:
				throw new IllegalArgumentException("Submission cannot be created.");
		}
	}

	/**
	 * Sets the Submission's grader
	 * 
	 * @param grader the grader to set
	 */
	public void setGrader(String grader) {
		if (grader == null) {
			this.grader = "";
		} else {
			this.grader = grader;
		}
	}

	/**
	 * Sets the Submission's grade
	 * 
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		if (grade == null) {
			if (grader != null && !grader.equals("")) {
				throw new IllegalArgumentException("Submission cannot be created.");
			} else {
				this.grade = "";
			}
		} else {
			this.grade = grade;
		}
	}

	/**
	 * Sets the Submission published flag
	 * 
	 * @param published the published to set
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	/**
	 * Returns the Submission ID
	 * 
	 * @return the submissionId
	 */
	public int getId() {
		return submissionId;
	}
	
	/**
	 * Returns the Submission's state
	 * 
	 * @return
	 */
	public String getState() {
		return currentState.getStateName();
	}
	
	/**
	 * Returns the Submission's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the Submission's unity ID
	 * 
	 * @return the unityId
	 */
	public String getUnityId() {
		return unityId;
	}
	
	/**
	 * Returns the Submission's plagiarism check status
	 * 
	 * @return the checkResult
	 */
	public String getCheckResult() {
		switch (checkResult) {
			case PlagiarismCheckResult.PASS: 
				return PC_PASS;
			case PlagiarismCheckResult.FAIL:
				return PC_FAIL;
			case PlagiarismCheckResult.NONE:
				return PC_NONE;
			default:
				return "";
	}
	}
	
	/**
	 * Returns the Submission feedback status
	 * 
	 * @return the feedbackProcessed
	 */
	public boolean isFeedbackProcessed() {
		return feedbackProcessed;
	}
	
	/**
	 * Returns the Submission's grader
	 * 
	 * @return the grader
	 */
	public String getGrader() {
		if (grader == null) {
			return "";
		}
		return grader;
	}
	
	/**
	 * Returns the Submission's grade
	 * 
	 * @return the grade
	 */
	public String getGrade() {
		if (grade == null) {
			return "";
		}
		return grade;
	}

	/**
	 * Returns the Submission published status
	 * 
	 * @return the published
	 */
	public boolean isPublished() {
		return published;
	}

	/**
	 * Increments the static submission ID counter
	 */
	public static void incrementCounter() {
		counter += 1;
	}

	/**
	 * Sets the static submission counter
	 * 
	 * @param newCount new counter value
	 */
	public static void setCounter(int newCount) {
		Submission.counter = newCount;
	}

	/**
	 * Returns a string representation of the Submission
	 */
	@Override
	public String toString() {
		return "* " + submissionId + "," + currentState.getStateName() + "," + name + "," + unityId + ","
				+ feedbackProcessed + "," + getCheckResult() + "," + published + ","
				+ grader + "," + grade;
	}
	
	/**
	 * Updates the Submission based on the provided Command
	 * Delegates transition logic to the current state
	 * 
	 * @param c Command triggering a state change
	 * @throws UnsupportedOperationException if the Command is not
	 * 		   valid for the current state
	 */
	public void update(Command c) {
		currentState.updateState(c);
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
	 * Represents the Upload State
	 */
	private class UploadState implements SubmissionState {

		/**
		 * Updates the Submission based on a Command.
		 * 
		 * @param command the Command to process
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 * @throws IllegalArgumentException if invalid info is entered
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			
			switch(command.getCommand()) {
				case CommandValue.ASSIGN:
					String graderId = command.getCommandInformation();
					if (graderId == null || graderId.isEmpty() || feedbackProcessed) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					grader = graderId;
					currentState = feedbackState;
					break;
				case CommandValue.REUPLOAD:
					if (!feedbackProcessed) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					currentState = uploadState;
					break;
				case CommandValue.MOVE:
					if (!feedbackProcessed) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					currentState = submittedState;
					break;
				default:
					throw new UnsupportedOperationException("Invalid command.");
			}
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return UPLOAD_NAME;
		}
		
	}
	
	/**
	 * Represents the Feedback State
	 */
	private class FeedbackState implements SubmissionState {

		/**
		 * Updates the Submission based on a Command.
		 * 
		 * @param command the Command to process
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			switch(command.getCommand()) {
				case CommandValue.PROVIDE:
					setFeedbackProcessed(true);
					setGrader("");
					currentState = uploadState;
					break;
				default:
					throw new UnsupportedOperationException("Invalid command.");
			}
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return FEEDBACK_NAME;
		}
		
	}
	
	/**
	 * Represents the Submitted State
	 */
	private class SubmittedState implements SubmissionState {
		
		/**
		 * Updates the Submission based on a Command.
		 * 
		 * @param command the Command to process
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			
			switch(command.getCommand()) {
				case CommandValue.ASSIGN:
					String graderId = command.getCommandInformation();
					if (graderId == null || graderId.isEmpty()) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					grader = graderId;
					currentState = gradeState;
					break;
				default:
					throw new UnsupportedOperationException("Invalid command");
			}
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return SUBMITTED_NAME;
		}
		
	}
	
	/**
	 * Represents the Grade State
	 */
	private class GradeState implements SubmissionState {

		/**
		 * Updates the Submission based on a Command.
		 * 
		 * @param command the Command to process
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			String assignmentGrade = "";
			switch(command.getCommand()) {
				case CommandValue.ANALYZE:
					assignmentGrade = command.getCommandInformation();
					if (!checkResult.equals(PlagiarismCheckResult.NONE)) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					if (assignmentGrade.equals(GRADE_F_AIV_RETURN) || assignmentGrade.equals(GRADE_IN_RETURN)) {
						throw new UnsupportedOperationException("Invalid informtaion.");
					}
					grade = assignmentGrade;
					currentState = checkState;
					break;
				case CommandValue.SUBMIT:
					assignmentGrade = command.getCommandInformation();
					if (assignmentGrade.equals(GRADE_IN_RETURN)) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					if (assignmentGrade.equals(GRADE_F_AIV_RETURN)) {
						 setCheckResult(PC_FAIL);
					} else {
						setCheckResult(PC_PASS);
					}
					if (checkResult.equals(PlagiarismCheckResult.NONE)) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					switch (checkResult) {
						case FAIL:
							grade = assignmentGrade;
							currentState = returnState;
							break;
						case PASS:
							if (assignmentGrade.equals(GRADE_F_AIV_RETURN) || assignmentGrade.equals(GRADE_IN_RETURN)) {
								throw new UnsupportedOperationException("Invalid informtaion.");
							}
							grade = assignmentGrade;
							currentState = returnState;
							break;
						default:
							throw new UnsupportedOperationException("Invalid information.");
					}
					break;
				default:
					throw new UnsupportedOperationException("Invalid command");
			}
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return GRADE_NAME;
		}
		
	}
	
	/**
	 * Represents the Check State
	 */
	private class CheckState implements SubmissionState {

		/**
		 * Updates the Submission based on a Comm)and.
		 * 
		 * @param command the Command to process
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			switch (command.getCommand()) {
				case PASS:
					setCheckResult(PC_PASS);
					currentState = returnState;
					break;
				case FAIL:
					setCheckResult(PC_FAIL);
					currentState = gradeState;
					break;
				default:
					throw new UnsupportedOperationException("Invalid command");
			}
			
			// TODO Auto-generated method stub
			
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return CHECK_NAME;
		}
		
	}
	
	/**
	 * Represents the Regrade State
	 */
	private class RegradeState implements SubmissionState {

		/**
		 * Updates the Submission based on a Command.
		 * 
		 * @param command the Command to 
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			
			switch(command.getCommand()) {
				case PROCESS:
					currentState = gradeState;
					break;
				default:
					throw new UnsupportedOperationException("Invalid command");
			}
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return REGRADE_NAME;
		}
		
	}
	
	/**
	 * Represents the Return State
	 */
	private class ReturnState implements SubmissionState {

		/**
		 * Updates the Submission based on a Command.
		 * 
		 * @param command the Command to process
		 * @throws UnsupportedOperationException if the Command is not valid
		 * 		   for the current state
		 */
		@Override
		public void updateState(Command command) {
			if (command == null) {
				throw new UnsupportedOperationException("Invalid command.");
			}
			String assignmentGrade = "";
			switch (command.getCommand()) {
				case RESOLVE:
					if (!grade.equals("IN")) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					assignmentGrade = command.getCommandInformation();
					if (assignmentGrade.equals(GRADE_IN_RETURN)) {
						throw new UnsupportedOperationException("Invalid informtaion.");
					}
					if (assignmentGrade.equals(GRADE_F_AIV_RETURN)) {
						setCheckResult(PC_FAIL);
					} else {
						setCheckResult(PC_PASS);
					}
					grade = assignmentGrade;
					currentState = returnState;
					break;
				case REQUEST:
					if (grade.equals(GRADE_IN_RETURN) || grade.equals(GRADE_F_AIV_RETURN)) {
						throw new UnsupportedOperationException("Invalid informtaion.");
					}
					currentState = regradeState;
					break;
				case PUBLISH:
					if (published || grade.equals(GRADE_IN_RETURN)) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					setPublished(true);
					currentState = returnState;
					break;
				default:
					throw new UnsupportedOperationException("Invalid command");
			}
		}

		/**
		 * Returns the name of the current state.
		 * 
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return RETURN_NAME;
		}
		
	}
	
}
