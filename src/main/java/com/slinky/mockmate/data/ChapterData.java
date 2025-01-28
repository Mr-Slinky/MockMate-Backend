package com.slinky.mockmate.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents metadata for a specific chapter and acts as a manager for
 * chapter-specific questions.
 * 
 * <p>
 * This class implements the {@link Chapter} interface, providing methods for
 * managing questions, retrieving metadata, and navigating through questions
 * sequentially.
 * </p>
 *
 * @author  Kheagen Haskins
 * @version 0.1
 * @since   2025-01-08
 */
final class ChapterData implements Chapter {

    // ================================[ Fields ]================================ \\
    
    /**
     * The title of the chapter.
     */
    private final String title;

    /**
     * The number of the chapter.
     */
    private final int chapterNumber;

    /**
     * The list of questions associated with this chapter.
     */
    private final List<Question> questions;

    /**
     * The index of the next question to be retrieved.
     */
    private int nextIndex;

    // =============================[ Constructors ]============================= \\
    
    /**
     * Constructs a new {@code ChapterData} instance.
     *
     * @param number the chapter number.
     * @param title  the title of the chapter.
     */
    ChapterData(int number, String title) {
        if (number <= 0) {
            throw new IllegalArgumentException("Invalid chapter number : " + number);
        }
        
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Chapter title cannot be null or empty");
        }
        
        this.title    = title;
        chapterNumber = number;
        questions     = new ArrayList<>();
        nextIndex     = 0;
    }

    // ===========================[ Accessor Methods ]=========================== \\
    
    /**
     * Gets the title of the chapter.
     *
     * @return the chapter title.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Gets the chapter number.
     *
     * @return the chapter number.
     */
    @Override
    public int getChapterNumber() {
        return chapterNumber;
    }

    // =============================[ API Methods ]============================== \\
    
    /**
     * Counts the number of questions in the chapter.
     *
     * @return the number of questions.
     */
    @Override
    public int countQuestions() {
        return questions.size();
    }

    /**
     * Adds a new question to the chapter.
     *
     * @param question the question to add.
     */
    @Override
    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Cannot add a null question to a chapter");
        }
        
        questions.add(question);
    }

    /**
     * Removes a question from the chapter based on its ordinal value.
     *
     * @param ordinal the ordinal value of the question to remove.
     * @return {@code true} if the question was removed successfully, otherwise {@code false}.
     */
    @Override
    public boolean removeQuestion(int ordinal) {
        for (Question question : questions) {
            if (question.ordinal() == ordinal) {
                return questions.remove(question);
            }
        }
        
        return false;
    }

    /**
     * Retrieves a question by its ordinal value.
     *
     * @param ordinal the ordinal value of the question to retrieve.
     * @return the question with the specified ordinal value.
     * @throws IllegalArgumentException if no question matches the given ordinal value.
     */
    @Override
    public Question getQuestion(int ordinal) {
        for (Question question : questions) {
            if (question.ordinal() == ordinal) {
                return question;
            }
        }
        
        throw new IllegalArgumentException("Invalid question number: " + ordinal);
    }

    /**
     * Retrieves the next question in the sequence.
     * <p>
     * If there are no more questions in the list, the sequence wraps back to the first question.
     * </p>
     *
     * @return the next question in the sequence.
     */
    @Override
    public Question nextQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("Cannot iterate through questions whens they are empty");
        }
        
        var q     = questions.get(nextIndex);
        nextIndex = (nextIndex + 1) % questions.size();
        return q;
    }

    /**
     * Retrieves the previous question in the sequence.
     * <p>
     * If at the beginning of the list, the sequence wraps to the last question.
     * </p>
     *
     * @return the previous question in the sequence.
     */
    @Override
    public Question previousQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("Cannot iterate through questions whens they are empty");
        }
        
        nextIndex = ((nextIndex + questions.size()) - 1) % questions.size();
        var q     = questions.get(nextIndex);
        return q;
    }

    /**
     * Retrieves an unmodifiable list of all questions associated with this
     * chapter.
     *
     * @return a list of all questions in the chapter.
     */
    @Override
    public List<Question> getAllQuestions() {
       return Collections.unmodifiableList(questions);
    }

    @Override
    public String toString() {
        StringBuilder qStrBuilder = new StringBuilder();
        for (Question question : questions) {
            qStrBuilder.append("%n%s".formatted(question));
        }
        
        return "Chapter %d: %s\n%s".formatted(chapterNumber, title, qStrBuilder.toString());
    }
    
}