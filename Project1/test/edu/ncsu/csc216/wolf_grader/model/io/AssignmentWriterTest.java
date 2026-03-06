package edu.ncsu.csc216.wolf_grader.model.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.wolf_grader.model.manager.Assignment;
import edu.ncsu.csc216.wolf_grader.model.submission.Submission;

class AssignmentWriterTest {

    @Test
    void testWriteAssignmentsToFileValid() {
        List<Assignment> assignments = new ArrayList<>();
        Assignment a = new Assignment("Project 1", "Project", 100);
        a.addSubmission(new Submission("student1", "unityid")); 
        assignments.add(a);
        assertDoesNotThrow(() -> 
            AssignmentWriter.writeAssignmentsToFile("test-files/actual_out.txt", assignments)
        );
        File file = new File("test-files/actual_out.txt");
        assertTrue(file.exists());
    }

    @Test
    void testWriteAssignmentsToFileInvalid() {
        List<Assignment> assignments = new ArrayList<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AssignmentWriter.writeAssignmentsToFile("/", assignments);
        });

        assertEquals("Unable to save file.", exception.getMessage());
    }
}
