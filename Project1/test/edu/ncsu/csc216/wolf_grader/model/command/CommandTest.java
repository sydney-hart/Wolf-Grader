package edu.ncsu.csc216.wolf_grader.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;

/**
 * Tests the Command Class
 * 
 * @author Sydney Hart
 */
class CommandTest {

	/**
	 * Tests that constructor is working for different scenarios
	 */
	@Test
	public void testCommandConstructor() {
		Command command1 = new Command(CommandValue.RESOLVE, "A");
		Command command2 = new Command(CommandValue.MOVE, null);
		Command command3 = new Command(CommandValue.SUBMIT, "IN");
		Command command4 = new Command(CommandValue.ANALYZE, "B");
		assertEquals(CommandValue.RESOLVE, command1.getCommand());
		assertEquals(CommandValue.MOVE, command2.getCommand());
		assertEquals(CommandValue.SUBMIT, command3.getCommand());
		assertEquals(CommandValue.ANALYZE, command4.getCommand());
	}
	
	/**
	 * Tests invalid null parameters
	 */
	@Test
	public void testNullInformation() {
		Exception d = assertThrows(IllegalArgumentException.class, () -> new Command(null, null));
		assertEquals("Invalid information.", d.getMessage());
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.RESOLVE, null));
		assertEquals("Invalid information.", e.getMessage());
		Exception f = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.SUBMIT, null));
		assertEquals("Invalid information.", f.getMessage());
		Exception g = assertThrows(IllegalArgumentException.class, () -> new Command(CommandValue.ANALYZE, null));
		assertEquals("Invalid information.", g.getMessage());
	}

}
