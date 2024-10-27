import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App {

    private static int BOX_COUNT = 256;
    private static String ADD_OPERATOR = "=";
    private static String REMOVE_OPERATOR = "-";

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
        HolidayASCIIStringHelperManualArrangementProcedure hashmap = new HolidayASCIIStringHelperManualArrangementProcedure(BOX_COUNT);

        // part one
        int partOneResult = 0;
        for(String s : inputParts){
            partOneResult += hashmap.getHash(s);
        }
        System.out.println("Part One Result: " + partOneResult);
        System.out.println();
        

        // part two
        for(String inputPart : inputParts){
            if(inputPart.contains(ADD_OPERATOR)){
                String[] inputPartSplits = inputPart.split(ADD_OPERATOR);
                hashmap.addLensToBox(new Lens(inputPartSplits[0], Integer.parseInt(inputPartSplits[1])));
            }
            else if(inputPart.contains(REMOVE_OPERATOR)){
                hashmap.removeLensFromBox(inputPart.substring(0, inputPart.length() - 1));
            }
            else{
                System.out.println("Invalid input \"" + inputPart + "\" inside file: " + inputFilePath);
                return;
            }
        }

        hashmap.printBoxex(false);

        int partTwoResult = hashmap.calcTotalFocusPower(false);
        System.out.println();
        System.out.println("Pat Two Result: " + partTwoResult);

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
}
