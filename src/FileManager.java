import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    public FileManager() {
    }

    public void makeFile(String file_name){
        try {
            File file = new File(file_name);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error making file!");
            e.printStackTrace();
        }

    }

    public void writeToFile_Single(String file_name, String data_to_input){
        try {
            FileWriter fileWriter = new FileWriter(file_name);
            fileWriter.write(data_to_input);
            fileWriter.close();
            System.out.println("Successfully wrote to file!");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String file_name, String[] data_to_input){
        try {
            FileWriter fileWriter = new FileWriter(file_name);
            for (int i = 0; i < data_to_input.length; i++){
                fileWriter.write(data_to_input[i]);
            }
            fileWriter.close();
            System.out.println("Successfully wrote to file!");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean doesFileExist(String file_name){
        try {
            File file = new File(file_name);
            return file.isFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> readFromFile(String file_name){
        List<String> s = new ArrayList<>();
        try {
            File file = new File(file_name);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                s.add(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading file!");
            e.printStackTrace();
        }

        return s;

    }
}
