package com.slinky.mockmate.util;

/**
 * A utility class providing various string manipulation methods used throughout
 * the application.
 *
 * <p>
 * These methods assist in formatting strings, generating headings, and handling
 * code snippets.
 * </p>
 *
 * @author Kheagen Haskins
 */
public final class StringUtil {
    
    // =============================[ Constructors ]============================= \\
    private StringUtil() {}
    
    /**
     * Creates a formatted heading for use in source code.
     * 
     * <p>
     * The heading consists of a centred text with surrounding equals signs,
     * formatted for display within source code comments.
     * </p>
     * 
     * @param text   the text to display as the heading
     * @param length the total length of the heading, including padding and
     *               formatting
     * @return a string representing the formatted heading
     */
    public static String makeHeading(String text, int length) {
        final String line = "=".repeat((length - text.length() - 8) / 2);
        
        return "// %s[ %s ]%s%s \\\\".formatted(line, text, line, "=".repeat(text.length() % 2));
    }

    /**
     * Formats a given answer choice with a specified tab space for alignment.
     *
     * <p>
     * This method splits the answer choice into lines, extracts any ordinal
     * prefix from the first line, and applies tab spaces for alignment.
     * Additional lines are indented consistently.
     * </p>
     * 
     * @param answerChoice the answer choice text to format, potentially 
     *                     spanning multiple lines
     * @param tabSpace     the number of spaces for tab alignment
     * @return a string representing the formatted answer choice
     */
    public static String formatChoice(String answerChoice, int tabSpace) {
        // Split the answer choice per lines 
        String[] lines = answerChoice.split("/n");
        // Determine the index immediately after the first period (".") in the first line.
        int adjPeriodIndex = lines[0].indexOf(".") + 1;
        // Extract the ordinal prefix (e.g., "1.", "2.") from the first line.
        String ordinalPrefix = lines[0].substring(0, adjPeriodIndex);

            // Configure first line: //
        // Create the tab space for the first line (considering the ordinal prefix)
        String tab = " ".repeat(tabSpace - ordinalPrefix.length());
        // Construct the first line with the ordinal prefix and adjusted tab space.
        String formattedAnswer = ordinalPrefix + tab;
        // Adjust the tab to the full specified tab space for subsequent lines.
        tab = " ".repeat(tabSpace);

        // Process any additional lines in the answer choice.
        for (int j = 1; j < lines.length; j++) {
            // Append each line with the appropriate tab space and text.
            formattedAnswer += "\n%s%s".formatted(tab, lines[j]);
        }

        return formattedAnswer;
    }

    /**
     * Formats a code snippet for display.
     * 
     * <p>
     * If the provided code is {@code null}, a placeholder message is returned.
     * Otherwise, the method splits the code into lines, preserving meaningful
     * content while reformatting lines that begin with numeric prefixes
     * followed by a colon.
     * </p>
     * 
     * @param code the code snippet to format, or {@code null} if no code is
     *             provided
     * @return a string representing the formatted code snippet
     */
    public static String formatCode(String code) {
        if (code == null || code.isBlank()) {
            return "<< No code snippet provided >>";
        }

        StringBuilder formattedCode = new StringBuilder();
        String[] codeLines = code.split("\n");
        for (String line : codeLines) {
            if (line.isEmpty()) {
                continue;
            }

            if (Character.isDigit(line.charAt(0))) {
                int colonIndex = line.indexOf(":");
                if (colonIndex != -1) {
                    formattedCode.append(line, 0, colonIndex + 1).append("\t")
                                 .append(line.substring(colonIndex + 1))
                                 .append("\n");
                    continue;
                }
            }

            formattedCode.append(line).append(System.lineSeparator());
        }

        return formattedCode.toString();
    }
    
}