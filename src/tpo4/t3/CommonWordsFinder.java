package tpo4.t3;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class CommonWordsFinder {
    public static void main(String[] args) {
        List<String> fileNames = new ArrayList<>();
        fileNames.add("C:\\Users\\igorp\\IdeaProjects\\tpo1\\src\\tpo4\\t3\\text1.txt");
        fileNames.add("C:\\Users\\igorp\\IdeaProjects\\tpo1\\src\\tpo4\\t3\\text2.txt");

        try {
            List<String> documents = new ArrayList<>();
            for (String fileName : fileNames) {
                documents.add(new String(Files.readAllBytes(Paths.get(fileName))));
            }

            ForkJoinPool pool = new ForkJoinPool();
            CommonWordsTask task = new CommonWordsTask(documents, 0, documents.size());
            Set<String> commonWords = pool.invoke(task);

            System.out.println("Common words in files: " + commonWords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}