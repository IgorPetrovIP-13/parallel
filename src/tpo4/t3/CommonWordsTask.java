package tpo4.t3;
import java.util.concurrent.RecursiveTask;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonWordsTask extends RecursiveTask<Set<String>> {
    private List<String> documents;
    private int start;
    private int end;

    public CommonWordsTask(List<String> documents, int start, int end) {
        this.documents = documents;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Set<String> compute() {
        if (end - start == 1) {
            return extractWords(documents.get(start));
        } else {
            int mid = (start + end) / 2;
            CommonWordsTask task1 = new CommonWordsTask(documents, start, mid);
            CommonWordsTask task2 = new CommonWordsTask(documents, mid, end);
            invokeAll(task1, task2);
            return mergeResults(task1.join(), task2.join());
        }
    }

    private Set<String> extractWords(String text) {
        Set<String> words = new HashSet<>();
        for (String word : text.split("\\W+")) {
            words.add(word.toLowerCase());
        }
        return words;
    }

    private Set<String> mergeResults(Set<String> set1, Set<String> set2) {
        set1.retainAll(set2);
        return set1;
    }
}