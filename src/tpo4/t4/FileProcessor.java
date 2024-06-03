package tpo4.t4;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class FileProcessor {
    public static boolean containsKeywords(Path filePath, List<String> keywords) {
        try {
            String content = Files.readString(filePath);
            content = content.toLowerCase();
            for (String keyword : keywords) {
                if (!content.contains(keyword.toLowerCase())) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error while reading file: " + filePath);
            e.printStackTrace();
            return false;
        }
    }
}