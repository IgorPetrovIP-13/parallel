package tpo4.t1;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;

public class WordLengthAnalysis {

    static class WordLengthTask extends RecursiveTask<List<Integer>> {
        private static final int THRESHOLD = 10;
        private String[] words;
        private int start;
        private int end;

        WordLengthTask(String[] words, int start, int end) {
            this.words = words;
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<Integer> compute() {
            if (end - start <= THRESHOLD) {
                List<Integer> lengths = new ArrayList<>();
                for (int i = start; i < end; i++) {
                    lengths.add(words[i].length());
                }
                return lengths;
            } else {
                int mid = (start + end) / 2;
                WordLengthTask leftTask = new WordLengthTask(words, start, mid);
                WordLengthTask rightTask = new WordLengthTask(words, mid, end);
                invokeAll(leftTask, rightTask);
                List<Integer> leftResult = leftTask.join();
                List<Integer> rightResult = rightTask.join();
                leftResult.addAll(rightResult);
                return leftResult;
            }
        }
    }

    public static void main(String[] args) {
        try {
            String text = new String(Files.readAllBytes(Paths.get("C:\\Users\\igorp\\IdeaProjects\\tpo1\\src\\tpo4\\t1\\text1.txt")));
            System.out.println("Checking text...");
            String[] words = text.split("\\s+");

            long startTime = System.currentTimeMillis();

            ForkJoinPool pool = new ForkJoinPool();
            WordLengthTask task = new WordLengthTask(words, 0, words.length);

            List<Integer> lengths = pool.invoke(task);

            long endTime = System.currentTimeMillis();

            IntSummaryStatistics stats = lengths.stream().mapToInt(Integer::intValue).summaryStatistics();
            System.out.println("Min length: " + stats.getMin());
            System.out.println("Max length: " + stats.getMax());
            System.out.println("Average length: " + stats.getAverage());
            System.out.println("Word count: " + stats.getCount());

            System.out.println("Elapsed time: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
