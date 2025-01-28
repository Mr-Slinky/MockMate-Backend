package com.slinky.mockmate.data;

/**
 * Represents an Exam interface that provides methods for loading and
 * interacting with exam data.
 *
 * <p>
 * This interface defines methods for loading exam details, accessing chapters,
 * and retrieving metadata such as the exam code and the Java version required.
 * </p>
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 * Exam exam = Exam.loadExam(ExamCode.EXAM_CODE_1Z0_829);
 * for (int i = 0; i < exam.getChapterCount(); i++) {
 *     Chapter chapter = exam.loadChapter(i + 1);
 *     for (Question question : chapter.getAllQuestions()) {
 *         System.out.println(question.ordinal());
 *         System.out.println(question.questionText());
 *         System.out.println(question.codeSnippet());
 *         // ...
 *     }
 * }}</pre>
 *
 * @author Kheagen Haskins
 */
public interface Exam {

    // ================================[ Static ]================================ \\
    /**
     * Loads an exam using the given {@link ExamCode}.
     *
     * @param examCode the {@link ExamCode} representing the specific exam to be
     *                 loaded.
     * @return an instance of {@link Exam} containing the loaded exam data.
     */
    static Exam loadExam(ExamCode examCode) {
        return new ExamData().load(examCode);
    }

    // =============================[ API Methods ]============================== \\
    /**
     * Retrieves the code of the exam.
     *
     * @return a {@code String} representing the exam code.
     */
    String getExamCode();

    /**
     * Retrieves the Java version being tested in this exam.
     *
     * @return a {@code String} representing the Java version.
     */
    String getJavaVersion();

    /**
     * Loads a specific chapter of the exam based on its number.
     *
     * @param chapterNumber the number of the chapter to load.
     * @return a {@link Chapter} object representing the loaded chapter.
     */
    Chapter loadChapter(int chapterNumber);

    /**
     * Retrieves the total number of chapters in the exam.
     *
     * @return an {@code int} representing the number of chapters.
     */
    int getChapterCount();

}