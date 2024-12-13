import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static void saveListToFile(List<String> list, String filename) throws IOException {
        Path filePath = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
        }
    }

    public static List<String> loadListFromFile(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        List<String> list = new ArrayList<>();
        if (Files.exists(filePath)) {
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
            }
        } else {
            throw new FileNotFoundException("The file " + filename + " does not exist.");
        }
        return list;
    }
}
