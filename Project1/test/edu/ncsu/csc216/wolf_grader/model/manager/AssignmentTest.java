package edu.ncsu.csc216.wolf_grader.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

/**
 * Tests Assignment class
 */
public class AssignmentTest {

    /**
     * Tests valid constructor
     */
    @Test
    public void testAssignmentConstructorValid() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        assertEquals("Lab1", a.getAssignmentName());
        assertEquals("Programming", a.getAssignmentCategory());
        assertEquals(100, a.getMaxPoints());
        assertEquals(0, a.getSubmissions().size());
    }

    /**
     * Tests constructor with invalid name
     */
    @Test
    public void testConstructorInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Assignment(null, "Programming", 100);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Assignment("", "Programming", 100);
        });
    }

    /**
     * Tests constructor with invalid category
     */
    @Test
    public void testConstructorInvalidCategory() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Assignment("Lab1", null, 100);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Assignment("Lab1", "", 100);
        });
    }

    /**
     * Tests constructor with invalid maxPoints
     */
    @Test
    public void testConstructorInvalidMaxPoints() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Assignment("Lab1", "Programming", 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Assignment("Lab1", "Programming", 101);
        });
    }

    /**
     * Tests addSubmission(String, String)
     */
    @Test
    public void testAddSubmissionByInfo() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        int id = a.addSubmission("John Doe", "jdoe");

        assertNotNull(a.getSubmissionById(id));
        assertEquals(1, a.getSubmissions().size());
    }

    /**
     * Tests addSubmission(Submission)
     */
    @Test
    public void testAddSubmissionObject() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        Submission s = new Submission("Jane Doe", "jdoe2");

        int id = a.addSubmission(s);

        assertEquals(id, s.getId());
        assertEquals(1, a.getSubmissions().size());
    }

    /**
     * Tests duplicate submission id rejection
     */
    @Test
    public void testDuplicateSubmissionId() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        Submission s1 = new Submission("John", "jd1");
        Submission s2 = new Submission("Jane", "jd2");

        a.addSubmission(s1);

        // force duplicate id scenario
        Submission duplicate = new Submission(s1.getId(), "Submitted", "Fake", "fake", false, "NONE", false, "", "0");

        assertThrows(IllegalArgumentException.class, () -> {
            a.addSubmission(duplicate);
        });
    }

    /**
     * Tests getSubmissionById
     */
    @Test
    public void testGetSubmissionById() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        int id = a.addSubmission("John Doe", "jdoe");

        assertNotNull(a.getSubmissionById(id));
        assertNull(a.getSubmissionById(999));
    }

    /**
     * Tests deleteSubmissionById
     */
    @Test
    public void testDeleteSubmissionById() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        int id = a.addSubmission("John Doe", "jdoe");

        assertEquals(1, a.getSubmissions().size());

        a.deleteSubmissionById(id);

        assertEquals(0, a.getSubmissions().size());
    }

    /**
     * Tests toString
     */
    @Test
    public void testToString() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        assertEquals("# Lab1,Programming,100", a.toString());
    }

    /**
     * Tests setSubmissionId updates counter correctly
     */
    @Test
    public void testSetSubmissionId() {
        Assignment a = new Assignment("Lab1", "Programming", 100);

        Submission s1 = new Submission("John", "jd1");
        Submission s2 = new Submission("Jane", "jd2");

        a.addSubmission(s1);
        a.addSubmission(s2);

        a.setSubmissionId();

        Submission newSubmission = new Submission("Bob", "bb1");

        assertTrue(newSubmission.getId() > s2.getId());
    }
}
