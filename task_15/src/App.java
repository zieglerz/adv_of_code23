import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws Exception {

        String inputFilePath = "res" + System.getProperty("file.separator") + "long_input.txt";

        File f = new File(inputFilePath);
        if (!f.exists()) {
            System.out.println("Invalid path: " + f.getCanonicalPath());
            return;
        }

        String input;
        try{
            input = loadFile(inputFilePath);
        } catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (input.isBlank()) {
            System.out.println("Empty input");
            return;
        }

        String[] inputParts = input.split(",");

        int result = 0;
        for(String s : inputParts){
            result += hash(s);
        }

        System.out.println("Result: " + result);
    }

     private static String loadFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static int hash(String string){
        int hash = 0;
        
        for(char c : string.toCharArray()){
            int ascii = (int) c;
            hash += ascii;
            hash = (hash * 17) % 256;
        }

        return hash;
    }
}
