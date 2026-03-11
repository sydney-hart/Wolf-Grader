package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.command.Command;
import edu.ncsu.csc216.wolf_grader.model.command.Command.CommandValue;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests Assignment class
 * 
 * @author Sydney Hart
 */
public class AssignmentTest {

	/** Name of the assignment **/
	private static final String NAME = "Practice Problems";
	/** Category of the assignment **/
	private static final String CATEGORY = "Homework";
	/** Max amount of points given on assignment **/
	private static final int MAX_POINTS = 50;
	
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
     * Tests valid constructor
     */
    @Test
    public void testAssignmentConstructorValid() {
        Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);

        assertEquals(NAME, a.getAssignmentName(), "invalid name");
        assertEquals(CATEGORY, a.getAssignmentCategory(), "invalid category");
        assertEquals(MAX_POINTS, a.getMaxPoints(), "invalid number of points");
        assertEquals(0, a.getSubmissions().size(), "ensures submissions array was created");
    }

    /**
     * Tests constructor with invalid name
     */
    @Test
    public void testConstructorInvalidName() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> 
            new Assignment(null, CATEGORY, MAX_POINTS));
        assertEquals("Assignment cannot be created.", e.getMessage());
        
        Exception f = assertThrows(IllegalArgumentException.class, () -> 
            new Assignment("", CATEGORY, MAX_POINTS));
        assertEquals("Assignment cannot be created.", f.getMessage());
    }

    /**
     * Tests constructor with invalid category
     */
    @Test
    public void testConstructorInvalidCategory() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> 
        	new Assignment(NAME, null, MAX_POINTS));
        assertEquals("Assignment cannot be created.", e.getMessage());
    
        Exception f = assertThrows(IllegalArgumentException.class, () -> 
        	new Assignment(NAME, "", MAX_POINTS));
        assertEquals("Assignment cannot be created.", f.getMessage());
    }

    /**
     * Tests constructor with invalid maxPoints
     */
    @Test
    public void testConstructorInvalidMaxPoints() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> 
	    	new Assignment(NAME, CATEGORY, 0));
	    assertEquals("Assignment cannot be created.", e.getMessage());
	
	    Exception f = assertThrows(IllegalArgumentException.class, () -> 
	    	new Assignment(NAME, CATEGORY, 101));
	    assertEquals("Assignment cannot be created.", f.getMessage());
	    }

    /**
     * Tests addSubmission(String, String)
     */
    @Test
    public void testAddSubmissionByInfo() {
        Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
        int id = a.addSubmission("Sydney Hart", "sehart");
        assertEquals(1, id);
        assertEquals(1, a.getSubmissions().size());
    }

    /**
     * Tests addSubmission(Submission)
     */
    @Test
    public void testAddSubmissionObject() {
    	Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
        Submission s = new Submission(5, "Upload", "Sydney Hart", "sehart", false, "Not Done", false, "sesmith", "A");
        int id = a.addSubmission(s);
        assertEquals(id, s.getId());
        assertEquals(1, a.getSubmissions().size());
    }

    /**
     * Tests duplicate submission id rejection
     */
    @Test
    public void testDuplicateSubmissionId() {
    	Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
        Submission s1 = new Submission("Sydney Hart", "sehart");
        a.addSubmission(s1);
        Submission duplicate = new Submission(s1.getId(), "Upload", "Katie Hart", "kbhart", false, "Not Done", false, "sehart", "B");
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            a.addSubmission(duplicate));
        assertEquals("Submission cannot be created.", e.getMessage());
    }

    /**
     * Tests getSubmissionById
     */
    @Test
    public void testGetSubmissionById() {
    	Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
    	int id = a.addSubmission("Sydney Hart", "sehart");
    	Submission expected = new Submission(1, "Upload", "Sydney Hart", "sehart", false, "Not Done", false, "", "");
        assertEquals(expected.getId(), id);
        assertEquals(expected.getName(), a.getSubmissionById(id).getName());
        assertEquals(expected.getState(), a.getSubmissionById(id).getState());
        assertNull(a.getSubmissionById(999));
    }

    /**
     * Tests deleteSubmissionById
     */
    @Test
    public void testDeleteSubmissionById() {
    	Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
    	int id = a.addSubmission("Sydney Hart", "sehart");
    	assertEquals(1, a.getSubmissions().size());
        a.deleteSubmissionById(id);
        assertEquals(0, a.getSubmissions().size());
    }

    /**
     * Tests toString
     */
    @Test
    public void testToString() {
    	Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
    	String expected = "# Practice Problems,Homework,50";
        assertEquals(expected, a.toString());
    }
    
    /**
     * Tests executeCommand
     */
    @Test
    public void testExecuteCommand() {
    	Assignment a = new Assignment(NAME, CATEGORY, MAX_POINTS);
    	int id = a.addSubmission("Sydney Hart", "sehart");
    	Command assign = new Command(CommandValue.ASSIGN, "sesmith");
    	a.executeCommand(id, assign);
    	assertEquals("Feedback", a.getSubmissionById(id).getState(), "incorrect state: FEEDBACK");
    }
}
