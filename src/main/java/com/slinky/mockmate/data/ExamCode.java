package com.slinky.mockmate.data;

/**
 * Enum representing Oracle Java certification exam codes and their
 * corresponding Java versions. Each constant corresponds to a specific
 * certification exam and its associated Java version or focus area.
 *
 * <p>
 * This enum provides methods to retrieve both the exam code and the Java version
 * associated with each certification exam.
 * </p>
 *
 * @author Kheagen Haskins
 * @version 0.1
 * @since 2025/01/06
 */
public enum ExamCode {

    // ==============================[ Constants ]=============================== \\
    /**
     * Foundations certification exam.
     */
    EXAM_CODE_1Z0_811("1Z0-811", "Foundations"),

    /**
     * Java SE 8 Programmer I certification exam.
     */
    EXAM_CODE_1Z0_808("1Z0-808", "SE 8"),

    /**
     * Java SE 21 certification exam.
     */
    EXAM_CODE_1Z0_830("1Z0-830", "SE 21"),

    /**
     * Java SE 17 certification exam.
     */
    EXAM_CODE_1Z0_829("1Z0-829", "SE 17"),

    /**
     * Java SE 11 certification exam.
     */
    EXAM_CODE_1Z0_819("1Z0-819", "SE 11"),

    /**
     * Java SE 8 Programmer II certification exam.
     */
    EXAM_CODE_1Z0_809("1Z0-809", "SE 8"),

    /**
     * Java EE 7 certification exam.
     */
    EXAM_CODE_1Z0_900("1Z0-900", "EE 7"),

    /**
     * Java EE 6 Enterprise JavaBeans (EJB) certification exam.
     */
    EXAM_CODE_1Z0_895("1Z0-895", "EE 6 EJB"),

    /**
     * Java EE 6 Web Services certification exam.
     */
    EXAM_CODE_1Z0_897("1Z0-897", "EE 6 Web Services"),

    /**
     * Java EE 6 Java Persistence API (JPA) certification exam.
     */
    EXAM_CODE_1Z0_898("1Z0-898", "EE 6 JPA"),

    /**
     * Java EE 6 Web Component Developer certification exam.
     */
    EXAM_CODE_1Z0_899("1Z0-899", "EE 6 Web Component"),

    /**
     * Java EE 6 Enterprise Architect certification exam.
     */
    EXAM_CODE_1Z0_807("1Z0-807", "EE 6 Enterprise Architect"),

    /**
     * Java EE Enterprise Architect Assignment certification exam.
     */
    EXAM_CODE_1Z0_865("1Z0-865", "EE Enterprise Architect Assignment"),

    /**
     * Java EE Enterprise Architect Essay certification exam.
     */
    EXAM_CODE_1Z0_866("1Z0-866", "EE Enterprise Architect Essay");

    // ================================[ Fields ]================================ \\
    /**
     * The Java version or focus area associated with the exam code.
     */
    private final String javaVersion;

    /**
     * The certification exam code.
     */
    private final String code;

    // =============================[ Constructors ]============================= \\
    /**
     * Constructor to associate an exam code with its corresponding Java version.
     *
     * @param code        the certification exam code as a String.
     * @param javaVersion the Java version or focus area as a String.
     */
    private ExamCode(String code, String javaVersion) {
        this.code = code;
        this.javaVersion = javaVersion;
    }

    // =============================[ API Methods ]============================== \\
    /**
     * Retrieves the Java version or focus area associated with the exam code.
     *
     * @return the Java version as a String.
     */
    public String getJavaVersion() {
        return javaVersion;
    }

    /**
     * Retrieves the certification exam code.
     *
     * @return the exam code as a String.
     */
    public String getCode() {
        return code;
    }
 
}