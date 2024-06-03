package tpo4.t1;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;

public class WordLengthAnalysis {

    static class WordLengthTask extends RecursiveTask<List<Integer>> {
        private static final int THRESHOLD = 1000;
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
        String text = "Starting text to check";
        System.out.println("Checking text: " + text);
        String[] words = text.split("\\s+");

        ForkJoinPool pool = new ForkJoinPool();
        WordLengthTask task = new WordLengthTask(words, 0, words.length);
        List<Integer> lengths = pool.invoke(task);

        IntSummaryStatistics stats = lengths.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("Min length: " + stats.getMin());
        System.out.println("Max length: " + stats.getMax());
        System.out.println("Average length: " + stats.getAverage());
        System.out.println("Word count: " + stats.getCount());
    }
}