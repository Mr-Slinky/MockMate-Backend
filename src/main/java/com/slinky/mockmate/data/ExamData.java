package com.slinky.mockmate.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.slinky.mockmate.util.FileUtil;

import java.io.IOException;

/**
 * A class that handles I/O operations for reading question and chapter data from storage.
 * <p>
 * This class is designed to manage exam data and can only be loaded once per instance.
 * To handle multiple exams, instantiate multiple instances of {@link ExamData}.
 * </p>
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 * var exam1 = Exam.loadExam(ExamCode.EXAM_CODE_1Z0_829);
 * var exam2 = Exam.loadExam(ExamCode.EXAM_CODE_1Z0_895);
 * }</pre>
 * 
 * <p>
 * This implementation uses JSON files to load chapter and question data, relying on
 * helper utilities and the Jackson library for parsing.
 * </p>
 * 
 * @author Kheagen Haskins
 */
public final class ExamData implements Exam {

    // ================================[ Static ]================================ \\

    /**
     * Directory prefix for exam data files.
     */
    private static final String EXAM_ROOT_DIR = "/json/exam-%s/";

    // ================================[ Fields ]================================ \\

    /**
     * The number of chapters in the exam.
     */
    private int chapterCount;

    /**
     * Flag to indicate whether the exam has been loaded.
     */
    private boolean loaded;

    /**
     * The code representing the exam.
     */
    private ExamCode examCode;

    /**
     * The base URL for exam resources.
     */
    private String resURL;

    // =============================[ Constructors ]============================= \\

    /**
     * Constructs an instance of {@code ExamData}.
     * Initially, the exam is not loaded.
     */
    ExamData() {
        loaded = false;
    }

    // ===========================[ Accessor Methods ]=========================== \\

    /**
     * Retrieves the exam code.
     * 
     * @return a {@code String} representing the exam code.
     */
    @Override
    public String getExamCode() {
        return examCode.getCode();
    }

    /**
     * Retrieves the Java version required for the exam.
     * 
     * @return a {@code String} representing the Java version.
     */
    @Override
    public String getJavaVersion() {
        return examCode.getJavaVersion();
    }

    /**
     * Loads a specific chapter of the exam based on the chapter number.
     * 
     * @param chapterNumber the number of the chapter to load.
     * @return a {@link Chapter} object representing the loaded chapter, or {@code null} if an error occurs.
     */
    @Override
    public Chapter loadChapter(int chapterNumber) {
        if (chapterNumber <= 0 || chapterNumber > chapterCount) {
            throw new IllegalArgumentException();
        }
        
        final Chapter chapter;
        final String chapterDir = getChapterDir(chapterNumber);
        final int questionCount = FileUtil.countFiles(chapterDir) - 1; // Minus one for meta.json file
        ObjectMapper mapper     = new ObjectMapper();

        try {
            chapter = new ChapterData(chapterNumber, getChapterTitle(chapterNumber));
            for (int i = 0; i < questionCount; i++) {
                String path  = chapterDir + "q%d.json".formatted(i + 1);
                var file     = getClass().getResourceAsStream(path);
                var question = mapper.readValue(file, QuestionData.class);
                chapter.addQuestion(question);
            }

            return chapter;
        } catch (IOException ex) {
            throw new RuntimeException("IO Error: " + ex.getMessage());
        }
        
    }

    // =============================[ API Methods ]============================== \\
    /**
     * Retrieves the total number of chapters in the exam.
     * 
     * @return an {@code int} representing the number of chapters.
     */
    @Override
    public int getChapterCount() {
        return chapterCount;
    }

    /**
     * Loads the exam data using the specified {@link ExamCode}.
     * 
     * @param examCode the {@link ExamCode} representing the exam to be loaded.
     * @return the loaded {@link ExamData} instance.
     * @throws IllegalStateException if the exam has already been loaded.
     */
    Exam load(ExamCode examCode) {
        if (loaded) {
            throw new IllegalStateException("Exam has already loaded");
        }

        this.examCode = examCode;
        this.resURL   = EXAM_ROOT_DIR.formatted(examCode.getCode());
        chapterCount  = FileUtil.countSubDir(resURL);
        loaded        = true;
        
        return this;
    }

    // ============================[ Helper Methods ]============================ \\
    /**
     * Retrieves the title of a specific chapter.
     * 
     * @param chapterNumber the number of the chapter.
     * @return a {@code String} representing the chapter title, or "Unknown" if an error occurs.
     */
    private String getChapterTitle(int chapterNumber) {
        String dir = getChapterDir(chapterNumber) + "meta.json";
        var jsonInpStr = getClass().getResourceAsStream(dir);

        try (JsonParser parser = new JsonFactory().createParser(jsonInpStr)) {
            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();
                if (token == JsonToken.FIELD_NAME) {
                    if ("title".equals(parser.currentName())) {
                        parser.nextToken(); // Move to the value
                        return parser.getValueAsString();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Error: " + ex.getMessage());
        }

        return "Unknown";
    }

    /**
     * Constructs the directory path for a specific chapter.
     * 
     * @param chapterNumber the number of the chapter.
     * @return a {@code String} representing the chapter's directory path.
     */
    private String getChapterDir(int chapterNumber) {
        return resURL + "chapter%d/".formatted(chapterNumber);
    }
    
}