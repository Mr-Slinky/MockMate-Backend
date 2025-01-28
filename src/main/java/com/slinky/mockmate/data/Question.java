package com.slinky.mockmate.data;

/**
 * Represents a question data model with methods for validation, evaluation, and
 * utility operations related to questions and their answers.
 *
 * This interface defines the contract for question-related data handling,
 * ensuring consistency across different implementations. Accessor methods
 * follow Java 17 record naming conventions, providing clear and concise access
 * to the question's properties.
 *
 * @author  Kheagen Haskins
 * @version 0.2
 * @since   2025-01-06
 */
public interface Question {

    /**
     * Returns the ordinal of the question.
     *
     * @return the ordinal number of the question.
     */
    int ordinal();

    /**
     * Returns the text of the question.
     *
     * @return the question text.
     */
    String questionText();

    /**
     * Returns the associated code snippet, if any.
     *
     * @return the code snippet as a string or {@code null} if none is present.
     */
    String codeSnippet();

    /**
     * Returns the available choices for the question.
     *
     * @return an array of strings representing the answer choices.
     */
    String[] choices();

    /**
     * Returns the correct answers for the question.
     *
     * @return an array of characters representing the correct answers.
     */
    char[] answers();

    /**
     * Determines whether the provided answers are correct.
     *
     * @param answers an array of answer characters to evaluate; must not be
     * {@code null}.
     * @return {@code true} if all provided answers are correct and match the
     * test answers; {@code false} otherwise.
     * @throws NullPointerException if {@code answers} is {@code null}.
     */
    boolean isCorrect(char[] answers);

    /**
     * Checks whether a code snippet is associated with the question.
     *
     * @return {@code true} if a non-blank code snippet is present;
     * {@code false} otherwise.
     */
    boolean hasCodeSnippet();

    /**
     * Counts the number of correct answers in the provided array.
     *
     * @param answers an array of answer characters to evaluate; must not be
     * {@code null}.
     * @return the number of correct answers found.
     * @throws NullPointerException if {@code answers} is {@code null}.
     */
    int countCorrect(char[] answers);

    /**
     * Retrieves the ordinal character corresponding to the specified answer.
     *
     * @param answer the answer to find the ordinal for; must not be
     * {@code null}.
     * @return the ordinal character ('A', 'B', 'C', etc.) of the answer, or
     * {@code 0} if not found.
     * @throws NullPointerException if {@code answer} is {@code null}.
     */
    char getOrdinalOf(String answer);

}