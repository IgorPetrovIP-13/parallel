package tpo4.t4;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.RecursiveTask;


public class DocumentSearchTask extends RecursiveTask<List<Path>> {
    private final Path directoryPath;
    private final List<String> keywords;

    public DocumentSearchTask(Path directoryPath, List<String> keywords) {
        this.directoryPath = directoryPath;
        this.keywords = keywords;
    }

    @Override
    protected List<Path> compute() {
        List<DocumentSearchTask> subTasks = new ArrayList<>();
        List<Path> result = new ArrayList<>();

        try {
            Files.list(directoryPath).forEach(path -> {
                if (Files.isDirectory(path)) {
                    DocumentSearchTask task = new DocumentSearchTask(path, keywords);
                    task.fork();
                    subTasks.add(task);
                } else if (path.toString().endsWith(".txt")) {
                    if (FileProcessor.containsKeywords(path, keywords)) {
                        result.add(path);
                    }
                }
            });

            for (DocumentSearchTask task : subTasks) {
                result.addAll(task.join());
            }
        } catch (IOException e) {
            System.err.println("Error while processing directory: " + directoryPath);
            e.printStackTrace();
        }

        return result;
    }
}
