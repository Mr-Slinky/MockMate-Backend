package com.slinky.mockmate.util;

import java.io.IOException;

import java.net.URISyntaxException;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.function.Predicate;

/**
 * Utility class for file and directory operations. Provides methods to count
 * subdirectories and files within a given directory.
 *
 * <p>
 * This class is not instantiable and all methods are static.
 * </p>
 *
 * @author Kheagen Haskins
 */
public final class FileUtil {

    // =============================[ Constructors ]============================= \\
    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private FileUtil() {}

    // =============================[ API Methods ]============================== \\
    /**
     * Counts the number of subdirectories in the specified directory path.
     *
     * @param pathString the path of the directory as a string
     * @return the number of subdirectories in the specified directory, or -1 if
     *         an I/O error occurs
     * @throws IllegalArgumentException if the specified path is not a directory
     */
    public static int countSubDir(String pathString) {
        return countInResourceDirectory(pathString, path -> Files.isDirectory(path));
    }

    /**
     * Counts the number of regular files in the specified directory path.
     *
     * @param pathString the path of the directory as a string
     * @return the number of regular files in the specified directory, or -1 if
     *         an I/O error occurs
     * @throws IllegalArgumentException if the specified path is not a directory
     */
    public static int countFiles(String pathString) {
        return countInResourceDirectory(pathString, path -> Files.isRegularFile(path));
    }
    
    public static int countFiles(String pathString, String fileExtension) {
        return countInResourceDirectory(pathString, path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(fileExtension));
    }

    // ============================[ Helper Methods ]============================ \\
    /**
     * Counts the number of paths in a directory that satisfy a specified
     * predicate.
     *
     * @param pathString the path of the directory as a string
     * @param pathTest   a {@link Predicate} to test each path in the directory
     * @return the count of paths that match the specified condition, or -1 if
     *         an I/O error occurs
     * @throws IllegalArgumentException if the specified path is invalid
     */
    private static int countInResourceDirectory(String pathString, Predicate<Path> pathTest) {
        var res = FileUtil.class.getResource(pathString);
        try {
            Path directory = Paths.get(res.toURI());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                return count(stream, pathTest);
            } catch (IOException e) {
                System.err.println("Error accessing the directory: " + e.getMessage());
                return -1;
            }
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Path error: " + ex.getMessage());
        }
    }

    /**
     * Iterates through the {@link DirectoryStream} and counts the paths that
     * match the given condition.
     *
     * @param stream   the {@link DirectoryStream} to iterate through
     * @param pathTest a {@link Predicate} to test each path
     * @return the count of paths that satisfy the given condition
     */
    private static int count(DirectoryStream<Path> stream, Predicate<Path> pathTest) {
        int count = 0;

        for (Path path : stream) {
            if (pathTest.test(path)) {
                count++;
            }
        }

        return count;
    }

}
// TODO: Add Logging