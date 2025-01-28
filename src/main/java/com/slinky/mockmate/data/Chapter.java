package com.slinky.mockmate.data;

import java.util.List;

/**
 * Represents a chapter within a system that manages chapter-specific questions
 * and metadata.
 * 
 * <p>
 * This interface defines methods for retrieving chapter details, managing
 * questions, and navigating through questions sequentially.
 * </p>
 *
 * @author Kheagen Haskins
 */
public interface Chapter {

    /**
     * Retrieves the title of the chapter.
     *
     * @return the chapter title.
     */
    String getTitle();

    /**
     * Retrieves the chapter number.
     *
     * @return the chapter number.
     */
    int getChapterNumber();

    /**
     * Counts the total number of questions in the chapter.
     *
     * @return the number of questions in the chapter.
     */
    int countQuestions();

    /**
     * Adds a question to the chapter.
     *
     * @param question the question to add.
     */
    void addQuestion(Question question);

    /**
     * Removes a question from the chapter based on its ordinal value.
     *
     * @param ordinal the ordinal value of the question to remove.
     * @return {@code true} if the question was removed successfully;
     * {@code false} otherwise.
     */
    boolean removeQuestion(int ordinal);

    /**
     * Retrieves a specific question based on its ordinal value.
     *
     * @param ordinal the ordinal value of the question to retrieve.
     * @return the question with the specified ordinal value.
     * @throws IllegalArgumentException if the ordinal value does not correspond
     * to any question.
     */
    Question getQuestion(int ordinal);

    /**
     * Retrieves the next question in the chapter's sequence.
     * <p>
     * If the end of the sequence is reached, the navigation wraps back to the
     * first question.
     * </p>
     *
     * @return the next question in the sequence.
     */
    Question nextQuestion();

    /**
     * Retrieves the previous question in the chapter's sequence.
     * <p>
     * If at the beginning of the sequence, the navigation wraps to the last
     * question.
     * </p>
     *
     * @return the previous question in the sequence.
     */
    Question previousQuestion();

    /**
     * Retrieves all questions associated with the chapter.
     *
     * @return a list of all questions in the chapter.
     */
    List<Question> getAllQuestions();

}