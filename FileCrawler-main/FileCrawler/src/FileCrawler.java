import java.io.*;

public class FileCrawler {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a search term:");
        try {
            String searchTerm = reader.readLine();
            boolean found = searchFiles(new File("."), searchTerm);
            if (!found) {
                System.out.println("Search term not found.");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static boolean searchFiles(File directory, String searchTerm) {
        if (!directory.canRead()) {
            System.err.println("Cannot read directory: " + directory.getAbsolutePath());
            return false;
        }
        boolean found = false;
        File[] files = directory.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                found |= searchFiles(file, searchTerm);
            } else if (file.isFile()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(searchTerm)) {
                            System.out.println("The word found in file: " + file.getAbsolutePath());
                            found = true;
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Cannot read file: " + file.getAbsolutePath());
                }
            }
        }
        return found;
    }
}
