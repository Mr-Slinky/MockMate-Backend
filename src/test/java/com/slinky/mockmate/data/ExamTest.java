package com.slinky.mockmate.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Kheagen
 */
public class ExamTest {
    
    private static final ExamCode VALID_EXAM_CODE = ExamCode.EXAM_CODE_1Z0_829;
    
    @Test
    void testExamLoad() {
        assertDoesNotThrow(() -> {
            Exam.loadExam(VALID_EXAM_CODE);
        });
    }
    
    @Test
    void testExamEndToEnd() {
        var exam = Exam.loadExam(VALID_EXAM_CODE);
        assertAll("End to End Exam test",
                () -> assertTrue(exam.getChapterCount() > 0),
                () -> assertEquals(VALID_EXAM_CODE.getCode(), exam.getExamCode()),
                () -> assertEquals(VALID_EXAM_CODE.getJavaVersion(), exam.getJavaVersion())
        );
    }
    
}
