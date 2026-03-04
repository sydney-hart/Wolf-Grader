package edu.ncsu.csc216.wolf_grader.model.command;

import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Encapsulates the information about a user command that would lead to a 
 * transition in the submission FSM. Contains one inner enumeration, CommandValue
 * 
 * @author Sydney Hart
 */
public class Command {

	/** The type of command being issued **/
	private CommandValue command;
	/** Additional information associated with the command **/
	private String commandInformation;
	/** Enumeration of all possible command types that can trigger
	 * state transitions in Submission 
	 **/
	public enum CommandValue { ASSIGN, MOVE, PROVIDE, REUPLOAD, SUBMIT, ANALYZE, RESOLVE, PROCESS, REQUEST, PUBLISH, PASS, FAIL }

	private String[] submitGrades = {Submission.GRADE_A_RETURN, Submission.GRADE_B_RETURN, Submission.GRADE_C_RETURN, Submission.GRADE_F_RETURN, Submission.GRADE_IN_RETURN, 
			Submission.GRADE_F_AIV_RETURN};
	private String[] grades = {Submission.GRADE_A_RETURN, Submission.GRADE_B_RETURN, Submission.GRADE_C_RETURN, Submission.GRADE_F_RETURN, Submission.GRADE_F_AIV_RETURN};
	/**
	 * Constructs a Command with a specified type and associated information
	 * 
	 * @param command CommandValue representing the action to perform
	 * @param commandInformation additional information needed for the command
	 * @throws IllegalArgumentException if command is not a valid parameter
	 */
	public Command(CommandValue command, String commandInformation) {
		if (command == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		switch (command) {
			case ASSIGN:
				if (commandInformation == null) {
					throw new IllegalArgumentException("Invalid information.");
				}
			case RESOLVE:
				if (commandInformation == null) {
					throw new IllegalArgumentException("Invalid information.");
				}
				boolean found = false;
				for (int i = 0; i < grades.length; i++) {
					String grade = grades[i];
					if (grade.equals(commandInformation)) {
						found = true;
						break;
					}
				}
				if (!found) {
					throw new IllegalArgumentException("Invalid information.");
				}
				break;
			case SUBMIT:
				if (commandInformation == null) {
					throw new IllegalArgumentException("Invalid information.");
				}
				found = false;
				for (int i = 0; i < submitGrades.length; i++) {
					String grade = submitGrades[i];
					if (grade.equals(commandInformation)) {
						found = true;
						break;
					}
				}
				if (!found) {
					throw new IllegalArgumentException("Invalid information.");
				}
				break;
			case ANALYZE:
				if (commandInformation == null) {
					throw new IllegalArgumentException("Invalid information.");
				}
				found = false;
				for (int i = 0; i < grades.length; i++) {
					String grade = grades[i];
					if (grade.equals(commandInformation)) {
						found = true;
						break;
					}
				}
				if (!found) {
					throw new IllegalArgumentException("Invalid information.");
				}
				break;
			default:
				break; 
		}
		this.command = command;
		this.commandInformation = commandInformation;
	}
	
	/**
	 * Returns the type of this command
	 * 
	 * @return the CommandVlaue representing the action
	 */
	public CommandValue getCommand() {
		return command;
	}

	/**
	 * Returns the additional information associated with command
	 * 
	 * @return the commandInformation string
	 */
	public String getCommandInformation() {
		return commandInformation;
	}

	
}
