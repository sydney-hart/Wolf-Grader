package edu.ncsu.csc216.wolf_grader.model.command;

/**
 * Encapsulates the information about a user command that would lead to a 
 * transition in the submission FSM. Contains one inner enumeration, CommandValue
 * 
 * TODO author tag
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

	/**
	 * Constructs a Command with a specified type and associated information
	 * 
	 * @param command CommandValue representing the action to perform
	 * @param commandInformation additional information needed for the command
	 */
	public Command(CommandValue command, String commandInformation) {
		
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
