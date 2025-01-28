package com.slinky.mockmate.data;

import com.slinky.mockmate.util.StringUtil;

import java.util.Arrays;

/**
 * A record model for a mock question to be displayed by a front-end view
 * component.
 *
 * <p>
 * The {@code QuestionData} class represents a single question in a quiz-like
 * format, including the question text, optional code snippet, <b>multiple
 * choices</b>, and correct answer<b>s</b>. It provides methods to validate
 * input, check answer correctness, and format question details.
 * </p>
 *
 * @author  Kheagen Haskins
 * @version 0.1
 * @since   2025-01-06
 */
record QuestionData (int ordinal, String questionText, String codeSnippet, String[] choices, char[] answers) implements Question {

    // =============================[ Constructors ]============================= \\
    /**
     * Compact constructor to validate input
     */
    QuestionData {
        if (ordinal <= 0) {
            throw new IllegalArgumentException("Ordinal must be positive.");
        }
        
        if (questionText == null || questionText.isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty.");
        }
        
        if (answers == null || answers.length <= 0) {
            throw new IllegalArgumentException("User choices cannot be empty or null");
        }
        
        if (answers == null || answers.length <= 0) {
            throw new IllegalArgumentException("Question answers cannot be empty or null");
        }
    } // long constructor called

    // =============================[ API Methods ]============================== \\

    /**
     * Determines whether the provided answers are correct.
     *
     * @param answers an array of answer characters to evaluate; must not be
     *                {@code null}
     * @return {@code true} if all provided answers are correct and match the
     *         test answers; {@code false} otherwise
     * @throws NullPointerException if {@code answers} is {@code null}
     */
    @Override
    public boolean isCorrect(char[] answers) {
        if (answers == null) {
            throw new NullPointerException("Answers array cannot be null.");
        }

        if (answers.length != this.answers.length) {
            return false;
        }
        
        return countCorrect(answers) == this.answers.length;
    }

    /**
     * Checks whether a code snippet is associated with the question.
     *
     * @return {@code true} if a non-blank code snippet is present;
     * {@code false} otherwise
     */
    @Override
    public boolean hasCodeSnippet() {
        return codeSnippet != null && !codeSnippet.isBlank();
    }

    /**
     * Counts the number of correct answers in the provided array.
     *
     * @param answers an array of answer characters to evaluate; must not be
     *                {@code null}
     * @return the number of correct answers found
     * @throws NullPointerException if {@code answers} is {@code null}
     */
    @Override
    public int countCorrect(char[] answers) {
        if (answers == null) {
            throw new NullPointerException("Answers array cannot be null.");
        }

        int count = 0;
        for (char answer : answers) {
            if (isACorrectAnswer(answer)) {
                count++;
            }
        }

        return count;
    }
    
    /**
     * Retrieves the ordinal character corresponding to the specified answer.
     * <p>
     * For example, if the answer is the first in the list, it returns 'A'; if
     * it is the second, 'B', and so on.
     * </p>
     *
     * @param answer the answer to find the ordinal for; must not be
     *               {@code null}
     * @return the ordinal character ('A', 'B', 'C', etc.) of the answer, or
     *         {@code 0} if not found
     * @throws NullPointerException if {@code answer} is {@code null}
     */
    @Override
    public char getOrdinalOf(String answer) {
        if (answer == null) {
            throw new NullPointerException("Answer cannot be null.");
        }

        char alpha = 'A';
        for (String uAnswer : choices) {
            if (answer.equalsIgnoreCase(uAnswer)) {
                return alpha;
            }
            
            alpha++;
        }

        return 0;
    }
    
    /**
     * Returns a string representation of the {@code QuestionData} object,
     * including the ordinal, question text, code snippet (if any), user
     * answers, and correct answers.
     *
     * @return a formatted string containing the question details
     */
    @Override
    public String toString() {
        return """
               %d.\t%s
               
               %s
               
               %s
               answers: %s
               """.formatted(ordinal, questionText, StringUtil.formatCode(codeSnippet), formatUserAnswers(), Arrays.toString(answers));
    }

    // ============================[ Helper Methods ]============================ \\
    /**
     * Formats the user answers for display, listing each answer on a new line.
     *
     * @return a formatted string of user answers
     */
    private String formatUserAnswers() {
        StringBuilder formattedAnswers = new StringBuilder();

        for (String answer : choices) {
            formattedAnswers.append(answer).append(System.lineSeparator());
        }

        return formattedAnswers.toString();
    }
    
    private boolean isACorrectAnswer(char answer) {
        for (char cAnswer : answers) {
            if (Character.toUpperCase(answer) == cAnswer) {
                return true;
            }
        }

        return false;
    }

}