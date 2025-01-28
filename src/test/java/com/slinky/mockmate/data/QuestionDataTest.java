package com.slinky.mockmate.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Kheagen Haskins
 */
public class QuestionDataTest {

    private static final int      ORDINAL = 20;
    private static final String   Q_TEXT  = "Which statements about the following class are correct? (Choose all that apply.)";
    private static final String   CODE    = "1: public class PoliceBox {\n2:     String color;\n3:     long age;\n4: \n5:     public void PoliceBox() {\n6:         color = \"blue\";\n7:         age = 1200;\n8:     }\n9: \n10:    public static void main(String[] time) {\n11:        var p = new PoliceBox();\n12:        var q = new PoliceBox();\n13:        p.color = \"green\";\n14:        p.age = 1400;\n15:        p = q;\n16: \n17:        System.out.println(\"q1=\" + q.color);\n18:        System.out.println(\"q2=\" + q.age);\n19:        System.out.println(\"p1=\" + p.color);\n20:        System.out.println(\"p2=\" + p.age);\n21:    }\n22: }";
    private static final char[]   ANSWERS = {'A', 'B', 'C'};
    private static final String[] CHOICES = {
        "A. It prints q1=blue.",
        "B. It prints q2=1200.",
        "C. It prints p1=null.",
        "D. It prints p2=1400.",
        "E. Line 4 does not compile.",
        "F. Line 12 does not compile.",
        "G. Line 13 does not compile.",
        "H. None of the above."
    };

    private Question testQuestion;

    @BeforeEach
    public void setup() {
        testQuestion = new QuestionData(ORDINAL, Q_TEXT, CODE, CHOICES, ANSWERS);
    }

    @Test
    public void testAccessorMethods() {
        assertAll("Accessor Methods",
                () -> assertEquals(ORDINAL, testQuestion.ordinal()),
                () -> assertEquals(Q_TEXT,  testQuestion.questionText()),
                () -> assertEquals(CODE,    testQuestion.codeSnippet()),
                () -> assertEquals(ANSWERS, testQuestion.answers()),
                () -> assertEquals(CHOICES, testQuestion.choices())
        );
    }

    // =========================[ isCorrect() Unit Tests ]========================= \\
    @Test
    public void testIsCorrectWithCorrectAnswers() {
        // Arrange
        char[] correctAnswers = {'A', 'B', 'C'};

        // Act & Assert
        assertTrue(testQuestion.isCorrect(correctAnswers), "Expected true for correct answers.");
    }

    @Test
    public void testIsCorrectWithPartiallyCorrectAnswers() {
        // Arrange
        char[] partiallyCorrectAnswers = {'A', 'B'};

        // Act & Assert
        assertFalse(testQuestion.isCorrect(partiallyCorrectAnswers), "Expected false for partially correct answers.");
    }

    @Test
    public void testIsCorrectWithIncorrectAnswers() {
        // Arrange
        char[] incorrectAnswers = {'D', 'E', 'F'};

        // Act & Assert
        assertFalse(testQuestion.isCorrect(incorrectAnswers), "Expected false for incorrect answers.");
    }

    @Test
    public void testIsCorrectWithNullAnswers() {
        // Arrange, Act & Assert
        assertThrows(NullPointerException.class, 
                () -> testQuestion.isCorrect(null), "Expected NullPointerException for null answers."
        );
    }

    @Test
    public void testIsCorrectWithMismatchedAnswerCount() {
        // Arrange
        char[] mismatchedAnswers = {'A', 'B', 'C', 'D'};

        // Act & Assert
        assertFalse(testQuestion.isCorrect(mismatchedAnswers), "Expected false for mismatched number of answers.");
    }

    // ======================[ hasCodeSnippet() Unit Tests ]====================== \\
    @Test
    public void testHasCodeSnippetWithValidCode() {
        // Arrange
        QuestionData questionWithCode = new QuestionData(
                1,
                "Sample question",
                "public class Test {}",
                new String[]{"A. Option 1", "B. Option 2"},
                new char[]{'A'}
        );

        // Act & Assert
        assertTrue(questionWithCode.hasCodeSnippet(), "Expected true when code snippet is non-blank.");
    }

    @Test
    public void testHasCodeSnippetWithEmptyCode() {
        // Arrange
        QuestionData questionWithEmptyCode = new QuestionData(
                1,
                "Sample question",
                "",
                new String[]{"A. Option 1", "B. Option 2"},
                new char[]{'A'}
        );

        // Act & Assert
        assertFalse(questionWithEmptyCode.hasCodeSnippet(), "Expected false when code snippet is empty.");
    }

    @Test
    public void testHasCodeSnippetWithNullCode() {
        // Arrange
        QuestionData questionWithNullCode = new QuestionData(
                1,
                "Sample question",
                null,
                new String[]{"A. Option 1", "B. Option 2"},
                new char[]{'A'}
        );

        // Act & Assert
        assertFalse(questionWithNullCode.hasCodeSnippet(), "Expected false when code snippet is null.");
    }

    // =======================[ countCorrect() Unit Tests ]======================= \\
    @Test
    public void testCountCorrectWithAllCorrectAnswers() {
        // Arrange
        char[] answersToCheck = {'A', 'B', 'C'};

        // Act
        int count = testQuestion.countCorrect(answersToCheck);

        // Assert
        assertEquals(3, count, "Expected count to be 3 for all correct answers.");
    }

    @Test
    public void testCountCorrectWithSomeCorrectAnswers() {
        // Arrange
        char[] answersToCheck = {'A', 'D', 'C'};

        // Act
        int count = testQuestion.countCorrect(answersToCheck);

        // Assert
        assertEquals(2, count, "Expected count to be 2 for some correct answers.");
    }

    @Test
    public void testCountCorrectWithNoCorrectAnswers() {
        // Arrange
        char[] answersToCheck = {'D', 'E', 'F'};

        // Act
        int count = testQuestion.countCorrect(answersToCheck);

        // Assert
        assertEquals(0, count, "Expected count to be 0 for no correct answers.");
    }

    @Test
    public void testCountCorrectWithEmptyAnswersArray() {
        // Arrange
        char[] answersToCheck = {};

        // Act
        int count = testQuestion.countCorrect(answersToCheck);

        // Assert
        assertEquals(0, count, "Expected count to be 0 for an empty answers array.");
    }

    @Test
    public void testCountCorrectWithNullAnswers() {
        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> testQuestion.countCorrect(null),
                "Expected NullPointerException for null answers array.");
    }
    
    
    // =======================[ getOrdinalOf() Unit Tests ]======================= \\
    @Test
    public void testGetOrdinalOfValidAnswer() {
        // Arrange
        String validAnswer = "A. It prints q1=blue.";

        // Act
        char ordinal = testQuestion.getOrdinalOf(validAnswer);

        // Assert
        assertEquals('A', ordinal, "Expected 'A' for the first choice.");
    }

    @Test
    public void testGetOrdinalOfValidAnswerCaseInsensitive() {
        // Arrange
        String validAnswer = "a. it prints q1=blue."; // Different casing

        // Act
        char ordinal = testQuestion.getOrdinalOf(validAnswer);

        // Assert
        assertEquals('A', ordinal, "Expected 'A' for a valid answer, regardless of case.");
    }

    @Test
    public void testGetOrdinalOfInvalidAnswer() {
        // Arrange
        String invalidAnswer = "Z. Invalid choice";

        // Act
        char ordinal = testQuestion.getOrdinalOf(invalidAnswer);

        // Assert
        assertEquals(0, ordinal, "Expected 0 for an invalid answer not in the choices.");
    }

    @Test
    public void testGetOrdinalOfEmptyAnswer() {
        // Arrange
        String emptyAnswer = "";

        // Act
        char ordinal = testQuestion.getOrdinalOf(emptyAnswer);

        // Assert
        assertEquals(0, ordinal, "Expected 0 for an empty answer.");
    }

    @Test
    public void testGetOrdinalOfNullAnswer() {
        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> testQuestion.getOrdinalOf(null),
                "Expected NullPointerException for a null answer.");
    }

    @Test
    public void testGetOrdinalOfFirstMatch() {
        // Arrange
        String validAnswer = "A. It prints q1=blue.";
        // A duplicate choice is intentionally not possible in this implementation since choices are unique.
        // In this test, we confirm the first occurrence of a correct match (implicitly).

        // Act
        char ordinal = testQuestion.getOrdinalOf(validAnswer);

        // Assert
        assertEquals('A', ordinal, "Expected 'A' for the first matching choice.");
    }

}