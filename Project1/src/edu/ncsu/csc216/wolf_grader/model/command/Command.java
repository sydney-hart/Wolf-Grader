package edu.ncsu.csc216.wolf_grader.model.command;

/**
 * 
 */
public class Command {

	/** **/
	private CommandValue command;
	/** **/
	private String commandInformation;
	/** Comments go here */
	public enum CommandValue { ASSIGN, MOVE, PROVIDE, REUPLOAD, SUBMIT, ANALYZE, RESOLVE, PROCESS, REQUEST, PUBLISH, PASS, FAIL }

	/**
	 * 
	 * @param command
	 * @param commandInformation
	 */
	public Command(CommandValue command, String commandInformation) {
		
	}
	
	/**
	 * @return the command
	 */
	public CommandValue getCommand() {
		return command;
	}

	/**
	 * @return the commandInformation
	 */
	public String getCommandInformation() {
		return commandInformation;
	}

	
}
