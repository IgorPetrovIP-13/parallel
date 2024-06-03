package tpo4.t4;

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String ROOT = "C:\\Users\\igorp\\IdeaProjects\\tpo1\\src\\tpo4\\t4\\books";
    private static final List<String> KEYWORDS = Arrays.asList("concurrency", "c", "c++", "programming", "parallel",
            "cuda", "distributed");

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        DocumentSearchTask task = new DocumentSearchTask(Paths.get(ROOT), KEYWORDS);
        List<Path> result = pool.invoke(task);
        pool.shutdown();
        System.out.println("\nMatching files paths: \n");
        result.forEach(System.out::println);
    }
}