package com.slinky.mockmate.data;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

/**
 *
 * @author Kheagen Haskins
 */
public class ChapterDataTest {

    // =========================[ Constructor Unit Tests ]========================= \\
    @ParameterizedTest
    @CsvSource({
        "1, Introduction to Java",
        "2, Advanced Java Concepts",
        "3, Data Structures and Algorithms"
    })
    void testConstructorInitialisation(int chapterNumber, String chapterTitle) {
        // Act
        Chapter chapter = new ChapterData(chapterNumber, chapterTitle);

        // Assert
        assertAll("Constructor Initialisation",
                () -> assertEquals(chapterNumber, chapter.getChapterNumber(), "Chapter number should be set correctly."),
                () -> assertEquals(chapterTitle, chapter.getTitle(), "Chapter title should be set correctly."),
                () -> assertTrue(chapter.getAllQuestions().isEmpty(), "Questions list should be initialised as empty."),
                () -> assertEquals(0, chapter.countQuestions(), "Question count should be 0 initially.")
        );
    }
    
    @ParameterizedTest
    @CsvSource({
        "-1, Valid Title", // Invalid chapter number
        "0, Valid Title",  // Invalid chapter number
        "1, ''",           // Empty title
                           // null source below
    })
    void testConstructorInvalidData(int chapterNumber, String chapterTitle) {
        assertThrows(IllegalArgumentException.class, 
            () -> new ChapterData(chapterNumber, chapterTitle), 
            "Expected IllegalArgumentException for invalid inputs: [%s | %s]".formatted(chapterNumber, chapterTitle)
        );
    }
    
    @ParameterizedTest
    @NullSource
    void testConstructorNullData(String title) {
        assertThrows(IllegalArgumentException.class, 
            () -> new ChapterData(1, title), 
            "Expected IllegalArgumentException for invalid title: [%s]".formatted(title)
        );
    }
    
    // ======================[ countQuestions() Unit Tests ]======================= \\
    @Test
    public void testCountQuestionsNoQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Empty Chapter");

        // Act & Assert
        assertEquals(0, chapter.countQuestions(), "Expected count to be 0 for no questions.");
    }

    @ParameterizedTest
    @CsvSource({
        "1, What is Java?",
        "2, Explain polymorphism.",
        "3, Describe inheritance."
    })
    void testCountQuestionsSingleQuestion(int ordinal, String questionText) {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Single Question Chapter");
        Question question   = new QuestionData(ordinal, questionText, null, new String[]{"A. Option 1"}, new char[]{'A'});

        // Act
        chapter.addQuestion(question);

        // Assert
        assertEquals(1, chapter.countQuestions(), "Expected count to be 1 after adding one question.");
    }


    @ParameterizedTest
    @MethodSource("provideMultipleQuestions")
    void testCountQuestionsMultipleQuestions(List<Question> questions) {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Multiple Questions Chapter");

        // Act
        questions.forEach(chapter::addQuestion);

        // Assert
        assertEquals(questions.size(), chapter.countQuestions(), "Expected count to match the number of added questions.");
    }

    static Stream<Arguments> provideMultipleQuestions() {
        return Stream.of(
                Arguments.of(List.of(
                        new QuestionData(1, "Question 1?", null, new String[]{"A. Option 1"}, new char[]{'A'}),
                        new QuestionData(2, "Question 2?", null, new String[]{"A. Option 1"}, new char[]{'A'})
                )),
                Arguments.of(List.of(
                        new QuestionData(3, "What is Java?",          null, new String[]{"A. Option 1"}, new char[]{'A'}),
                        new QuestionData(4, "Explain abstraction.",   null, new String[]{"A. Option 1"}, new char[]{'A'}),
                        new QuestionData(5, "What is encapsulation?", null, new String[]{"A. Option 1"}, new char[]{'A'})
                ))
        );
    }


    @Test
    public void testCountQuestionsDuplicateQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Duplicate Questions Chapter");
        Question question   = new QuestionData(1, "Sample Question?", null, new String[]{"A. Option 1"}, new char[]{'A'});

        // Act
        chapter.addQuestion(question);
        chapter.addQuestion(question); // Adding the same question again

        // Assert
        assertEquals(2, chapter.countQuestions(), "Expected count to include duplicate questions.");
    }

    @Test
    public void testCountQuestionsAfterRemoval() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Remove Questions Chapter");
        Question question   = new QuestionData(1, "Sample Question?", null, new String[]{"A. Option 1"}, new char[]{'A'});

        chapter.addQuestion(question);

        // Act
        chapter.removeQuestion(1);

        // Assert
        assertEquals(0, chapter.countQuestions(), "Expected count to be 0 after removing the question.");
    }

    @Test
    public void testCountQuestionsRemoveNonExistentQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Non-existent Removal Chapter");
        Question question   = new QuestionData(1, "Sample Question?", null, new String[]{"A. Option 1"}, new char[]{'A'});

        chapter.addQuestion(question);

        // Act
        chapter.removeQuestion(999); // Try to remove a non-existent question

        // Assert
        assertEquals(1, chapter.countQuestions(), "Expected count to remain unchanged after attempting to remove a non-existent question.");
    }

    @ParameterizedTest
    @MethodSource("provideAddRemoveSequence")
    void testCountQuestionsAddAndRemoveSequence(List<Question> addQuestions, List<Integer> removeOrdinals, int expectedCount) {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Add and Remove Sequence Chapter");

        // Act
        addQuestions.forEach(chapter::addQuestion);
        removeOrdinals.forEach(chapter::removeQuestion);

        // Assert
        assertEquals(expectedCount, chapter.countQuestions(), "Expected count after adding and removing questions.");
    }

    static Stream<Arguments> provideAddRemoveSequence() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new QuestionData(1, "Question 1?", null, new String[]{"A. Option 1"}, new char[]{'A'}),
                                new QuestionData(2, "Question 2?", null, new String[]{"A. Option 1"}, new char[]{'A'})
                        ),
                        List.of(1),
                        1 // Expected count
                ),
                Arguments.of(
                        List.of(
                                new QuestionData(3, "What is Java?",        null, new String[]{"A. Option 1"}, new char[]{'A'}),
                                new QuestionData(4, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'})
                        ),
                        List.of(3, 4),
                        0 // Expected count
                )
        );
    }

    // ========================[ addQuestion() Unit Tests ]======================== \\
    @Test
    public void testAddSingleQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Add Single Question Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});

        // Act
        chapter.addQuestion(question);

        // Assert
        assertAll("Adding a single question",
                () -> assertEquals(1, chapter.countQuestions(), "Expected count to be 1 after adding a single question."),
                () -> assertTrue(chapter.getAllQuestions().contains(question), "Expected question to be present in the chapter.")
        );
    }

    @Test
    public void testAddMultipleQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Add Multiple Questions Chapter");
        Question question1  = new QuestionData(1, "What is Java?",        null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});

        // Act
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Assert
        assertAll("Adding multiple questions",
                () -> assertEquals(2, chapter.countQuestions(), "Expected count to be 2 after adding two questions."),
                () -> assertTrue(chapter.getAllQuestions().contains(question1), "Expected question1 to be present in the chapter."),
                () -> assertTrue(chapter.getAllQuestions().contains(question2), "Expected question2 to be present in the chapter.")
        );
    }

    @Test
    public void testAddDuplicateQuestionsAllowed() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Add Duplicate Questions Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});

        // Act
        chapter.addQuestion(question);
        chapter.addQuestion(question); // Add the same question again

        // Assert
        assertAll("Adding duplicate questions",
                () -> assertEquals(2, chapter.countQuestions(), "Expected count to be 2 after adding the same question twice."),
                () -> assertEquals(2, chapter.getAllQuestions().stream().filter(q -> q.equals(question)).count(),
                        "Expected both duplicate questions to be present.")
        );
    }

    @ParameterizedTest
    @NullSource
    public void testAddNullQuestion(Question nullQuestion) {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Add Null Question Chapter");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> chapter.addQuestion(nullQuestion),
                "Expected IllegalArgumentException for null question."
        );
    }

    
    // ======================[ removeQuestion() Unit Tests ]======================= \\
    @Test
    public void testRemoveSingleExistingQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Remove Single Question Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        boolean result = chapter.removeQuestion(1);

        // Assert
        assertAll("Removing a single existing question",
                () -> assertTrue(result, "Expected removeQuestion to return true for an existing question."),
                () -> assertEquals(0, chapter.countQuestions(), "Expected question count to be 0 after removal."),
                () -> assertFalse(chapter.getAllQuestions().contains(question), "Expected question to be removed from the list.")
        );
    }

    @Test
    public void testRemoveMultipleExistingQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Remove Multiple Questions Chapter");
        Question question1  = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Act
        boolean result1 = chapter.removeQuestion(1);
        boolean result2 = chapter.removeQuestion(2);

        // Assert
        assertAll("Removing multiple existing questions",
                () -> assertTrue(result1, "Expected removeQuestion to return true for question1."),
                () -> assertTrue(result2, "Expected removeQuestion to return true for question2."),
                () -> assertEquals(0, chapter.countQuestions(), "Expected question count to be 0 after removing both questions."),
                () -> assertFalse(chapter.getAllQuestions().contains(question1), "Expected question1 to be removed."),
                () -> assertFalse(chapter.getAllQuestions().contains(question2), "Expected question2 to be removed.")
        );
    }

    @Test
    public void testRemoveNonExistentQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Remove Non-existent Question Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        boolean result = chapter.removeQuestion(999);

        // Assert
        assertAll("Removing a non-existent question",
                () -> assertFalse(result, "Expected removeQuestion to return false for a non-existent question."),
                () -> assertEquals(1, chapter.countQuestions(), "Expected question count to remain unchanged."),
                () -> assertTrue(chapter.getAllQuestions().contains(question), "Expected the existing question to remain in the list.")
        );
    }

    @Test
    public void testRemoveFromEmptyChapter() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Remove from Empty Chapter");

        // Act
        boolean result = chapter.removeQuestion(1);

        // Assert
        assertAll("Removing from an empty chapter",
                () -> assertFalse(result, "Expected removeQuestion to return false for an empty chapter."),
                () -> assertEquals(0, chapter.countQuestions(), "Expected question count to remain 0.")
        );
    }

    @Test
    public void testRemoveDuplicateQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Remove Duplicate Questions Chapter");
        Question question   = new QuestionData(1,  "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);
        chapter.addQuestion(question); // Add duplicate

        // Act
        boolean result = chapter.removeQuestion(1);

        // Assert
        assertAll("Removing a duplicate question",
                () -> assertTrue(result, "Expected removeQuestion to return true for an existing question."),
                () -> assertEquals(1, chapter.countQuestions(), "Expected one instance of the duplicate question to remain."),
                () -> assertTrue(chapter.getAllQuestions().contains(question), "Expected one instance of the duplicate question to remain.")
        );
    }
    // ========================[ getQuestion() Unit Tests ]======================== \\
    @Test
    public void testGetExistingQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Retrieve Existing Question Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        Question retrievedQuestion = chapter.getQuestion(1);

        // Assert
        assertAll("Retrieve an existing question",
                () -> assertNotNull(retrievedQuestion, "Expected a non-null question to be retrieved."),
                () -> assertEquals(question, retrievedQuestion, "Expected the retrieved question to match the added question.")
        );
    }

    @Test
    public void testGetMultipleExistingQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Retrieve Multiple Questions Chapter");
        Question question1  = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Act
        Question retrievedQuestion1 = chapter.getQuestion(1);
        Question retrievedQuestion2 = chapter.getQuestion(2);

        // Assert
        assertAll("Retrieve multiple existing questions",
                () -> assertEquals(question1, retrievedQuestion1, "Expected the first question to be retrieved correctly."),
                () -> assertEquals(question2, retrievedQuestion2, "Expected the second question to be retrieved correctly.")
        );
    }

    @Test
    public void testGetNonExistentQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Retrieve Non-existent Question Chapter");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> chapter.getQuestion(999),
                "Expected IllegalArgumentException for a non-existent question."
        );
    }

    @Test
    public void testGetQuestionFromEmptyChapter() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Retrieve from Empty Chapter");

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> chapter.getQuestion(1),
                "Expected IllegalArgumentException when retrieving from an empty chapter."
        );
    }

    @Test
    public void testGetQuestionWithInvalidOrdinal() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Retrieve with Invalid Ordinal Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> chapter.getQuestion(-1),
                "Expected IllegalArgumentException for a negative ordinal."
        );
    }

    // =======================[ nextQuestion() Unit Tests ]======================== \\
    @Test
    public void testNextQuestionInSequence() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Next Question Sequence Chapter");
        Question question1  = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Act
        Question next1 = chapter.nextQuestion();
        Question next2 = chapter.nextQuestion();

        // Assert
        assertAll("Retrieve questions in sequence",
                () -> assertEquals(question1, next1, "Expected the first question to be retrieved."),
                () -> assertEquals(question2, next2, "Expected the second question to be retrieved.")
        );
    }

    @Test
    public void testNextQuestionWrapToFirst() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Next Question Wrap Chapter");
        Question question1  = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Act
        chapter.nextQuestion(); // Skip to second question
        chapter.nextQuestion(); // Wrap to first question
        Question wrappedQuestion = chapter.nextQuestion();

        // Assert
        assertEquals(question1, wrappedQuestion, "Expected to wrap back to the first question.");
    }

    @Test
    public void testNextQuestionWithSingleQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Single Question Chapter");
        Question question = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        Question next1 = chapter.nextQuestion();
        Question next2 = chapter.nextQuestion();

        // Assert
        assertAll("Single question always returns itself",
                () -> assertEquals(question, next1, "Expected the single question to be retrieved."),
                () -> assertEquals(question, next2, "Expected the single question to always be retrieved.")
        );
    }

    @Test
    public void testNextQuestionWithEmptyChapter() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Empty Chapter");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                chapter::nextQuestion,
                "Expected IllegalStateException for calling nextQuestion on an empty chapter."
        );
    }

    @Test
    public void testNextQuestionSequencePersistence() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Sequence Persistence Chapter");
        Question question1 = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2 = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question3 = new QuestionData(3, "What is encapsulation?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);
        chapter.addQuestion(question3);

        // Act
        Question next1 = chapter.nextQuestion();
        Question next2 = chapter.nextQuestion();
        Question next3 = chapter.nextQuestion();

        // Assert
        assertAll("Maintain sequence across multiple calls",
                () -> assertEquals(question1, next1, "Expected the first question in sequence."),
                () -> assertEquals(question2, next2, "Expected the second question in sequence."),
                () -> assertEquals(question3, next3, "Expected the third question in sequence.")
        );
    }

    
    // =====================[ previousQuestion() Unit Tests ]====================== \\
    @Test
    public void testPreviousQuestionInSequence() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Previous Question Sequence Chapter");
        Question question1  = new QuestionData(1, "What is Java?",        null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);
        chapter.nextQuestion(); // Move to second question

        // Act
        Question previousQuestion = chapter.previousQuestion();

        // Assert
        assertEquals(question1, previousQuestion, "Expected the previous question to be retrieved.");
    }

    @Test
    public void testPreviousQuestionWrapToLast() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Previous Question Wrap Chapter");
        Question question1  = new QuestionData(1, "What is Java?",        null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Act
        Question wrappedQuestion = chapter.previousQuestion();

        // Assert
        assertEquals(question2, wrappedQuestion, "Expected to wrap back to the last question.");
    }

    @Test
    public void testPreviousQuestionWithSingleQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Single Question Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        Question prev1 = chapter.previousQuestion();
        Question prev2 = chapter.previousQuestion();

        // Assert
        assertAll("Single question always returns itself",
                () -> assertEquals(question, prev1, "Expected the single question to be retrieved."),
                () -> assertEquals(question, prev2, "Expected the single question to always be retrieved.")
        );
    }

    @Test
    public void testPreviousQuestionWithEmptyChapter() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Empty Chapter");

        // Act & Assert
        assertThrows(IllegalStateException.class,
                chapter::previousQuestion,
                "Expected IllegalStateException for calling previousQuestion on an empty chapter."
        );
    }

    @Test
    public void testPreviousQuestionSequencePersistence() {
        // Arrange
        ChapterData chapter = new ChapterData (1, "Sequence Persistence Chapter");
        Question question1  = new QuestionData(1, "What is Java?",          null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2  = new QuestionData(2, "Explain inheritance.",   null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question3  = new QuestionData(3, "What is encapsulation?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);
        chapter.addQuestion(question3);
        chapter.nextQuestion(); // Move to question 2
        chapter.nextQuestion(); // Move to question 3

        // Act
        Question prev1 = chapter.previousQuestion();
        Question prev2 = chapter.previousQuestion();

        // Assert
        assertAll("Maintain sequence across multiple calls",
                () -> assertEquals(question2, prev1, "Expected the second question in sequence."),
                () -> assertEquals(question1, prev2, "Expected the first question in sequence.")
        );
    }
    
    // ======================[ getAllQuestions() Unit Tests ]====================== \\
    @Test
    public void testGetAllQuestionsWithNoQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Empty Chapter");

        // Act
        List<Question> questions = chapter.getAllQuestions();

        // Assert
        assertAll("No questions in the chapter",
                () -> assertNotNull(questions, "Expected a non-null list."),
                () -> assertTrue(questions.isEmpty(), "Expected an empty list when no questions are present.")
        );
    }

    @Test
    public void testGetAllQuestionsWithSingleQuestion() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Single Question Chapter");
        Question question = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        List<Question> questions = chapter.getAllQuestions();

        // Assert
        assertAll("Single question in the chapter",
                () -> assertNotNull(questions, "Expected a non-null list."),
                () -> assertEquals(1, questions.size(), "Expected the list size to be 1."),
                () -> assertTrue(questions.contains(question), "Expected the list to contain the single question.")
        );
    }

    @Test
    public void testGetAllQuestionsWithMultipleQuestions() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Multiple Questions Chapter");
        Question question1 = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        Question question2 = new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question1);
        chapter.addQuestion(question2);

        // Act
        List<Question> questions = chapter.getAllQuestions();

        // Assert
        assertAll("Multiple questions in the chapter",
                () -> assertNotNull(questions, "Expected a non-null list."),
                () -> assertEquals(2, questions.size(), "Expected the list size to match the number of questions."),
                () -> assertEquals(question1, questions.get(0), "Expected the first question to match."),
                () -> assertEquals(question2, questions.get(1), "Expected the second question to match.")
        );
    }

    @Test
    public void testGetAllQuestionsImmutability() {
        // Arrange
        ChapterData chapter = new ChapterData(1, "Immutability Test Chapter");
        Question question   = new QuestionData(1, "What is Java?", null, new String[]{"A. Option 1"}, new char[]{'A'});
        chapter.addQuestion(question);

        // Act
        List<Question> questions = chapter.getAllQuestions();

        // Attempt to modify the returned list
        assertThrows(UnsupportedOperationException.class,
                () -> questions.add(new QuestionData(2, "Explain inheritance.", null, new String[]{"A. Option 1"}, new char[]{'A'})),
                "Expected UnsupportedOperationException when attempting to modify the list."
        );
    }
    
}