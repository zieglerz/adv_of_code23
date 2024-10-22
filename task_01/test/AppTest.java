import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;

@TestMethodOrder(OrderAnnotation.class)
public class AppTest {

    private static final Logger logger = Logger.getLogger(AppTest.class.getName());

    static {
        try {
            // Ensure the log directory exists
            Files.createDirectories(Paths.get("log"));
            FileHandler fileHandler = new FileHandler("log/log-test.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger handler.", e);
        }
    }

    @Test
    @Order(1)
    public void testSolveUsingInitialIdeaShort() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingInitialIdea("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingInitialIdea runtime: " + (endTime - startTime) + "ms");
        assertEquals(List.of(12, 38, 15, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }

    @Test
    @Order(2)
    public void testSolveUsingStreamAPIShort() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingStreamAPI("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingStreamAPI runtime: " + (endTime - startTime) + "ms");
        assertEquals(List.of(12, 38, 15, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }

    @Test
    @Order(3)
    public void testSolveUsingStringAPIWithoutSBShort() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingStringAPIWithoutSB("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingStringAPIWithoutSB runtime: " + (endTime - startTime) + "ms");
        assertEquals(List.of(12, 38, 15, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }

    @Test
    @Order(4)
    public void testSolveUsingStreamAPIReducedShort() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingStreamAPIReduced("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingStreamAPIReduced runtime: " + (endTime - startTime) + "ms");
        assertEquals(List.of(12, 38, 15, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }

    @Test
    @Order(5)
    public void testSolveUsingRegexShort() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingRegex("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingRegex runtime: " + (endTime - startTime) + "ms");
        assertEquals(List.of(12, 38, 15, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }

    @Test
    @Order(6)
    public void testSolveUsingRegex2Short() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingRegex2("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingRegex2 runtime: " + (endTime - startTime) + "ms");
        assertEquals(List.of(12, 38, 15, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }

    @Test
    @Order(7)
    public void testSolveUsingParallelismShort() {
        long startTime = System.currentTimeMillis();
        List<Integer> result = App.solveUsingParallelism("res" + java.io.File.separator + "short_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingParallelism runtime: " + (endTime - startTime) + "ms");
        result.sort(Integer::compareTo);
        assertEquals(List.of(12, 15, 38, 77), result);
        int sum = result.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 142);
    }
    
    @Test
    @Order(8)
    public void testSolveUsingInitialIdeaLong() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingInitialIdea("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingInitialIdea runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }

    @Test
    @Order(9)
    public void testSolveUsingStreamAPILong() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingStreamAPI("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingStreamAPI runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }

    @Test
    @Order(10)
    public void testSolveUsingStringAPIWithoutSBLong() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingStringAPIWithoutSB("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingStringAPIWithoutSB runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }

    @Test
    @Order(11)
    public void testSolveUsingStreamAPIReducedLong() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingStreamAPIReduced("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingStreamAPIReduced runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }

    @Test
    @Order(12)
    public void testSolveUsingRegexLong() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingRegex("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingRegex runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }

    @Test
    @Order(13)
    public void testSolveUsingRegex2Long() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingRegex2("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingRegex2 runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }

    @Test
    @Order(14)
    public void testSolveUsingParallelismLong() {
        long startTime = System.currentTimeMillis();
        List<Integer> list = App.solveUsingParallelism("res" + java.io.File.separator + "long_input.txt");
        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "solveUsingParallelism runtime: " + (endTime - startTime) + "ms");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        assertEquals(sum, 54388);
    }
}