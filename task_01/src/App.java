import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        String input = "res" + java.io.File.separator + "long_input.txt";

        int sum = solveUsingInitialIdea(input).stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
    }

    public static List<Integer> solveUsingInitialIdea(String input){
        List<Integer> values = new ArrayList<>();
        try{
            List<String> lines = Files.lines(Paths.get(input)).collect(Collectors.toList());
            for(String line : lines){
                boolean firstDigitFound = false;
                boolean secondDigitFound = false;
                char firstDigit = 0;
                char secondDigit = 0;
                for(int i = 0; i < line.length(); i++){
                    if(!firstDigitFound){
                        if(Character.isDigit(line.charAt(i))){
                            firstDigit = line.charAt(i);
                            firstDigitFound = true;
                        }
                    }
                    if(!secondDigitFound){
                        if(Character.isDigit(line.charAt(line.length() - 1 - i))){
                            secondDigit = line.charAt(line.length() - 1 - i);
                            secondDigitFound = true;
                        }
                    }
                    if(firstDigitFound && secondDigitFound){
                        values.add(Integer.parseInt(firstDigit + "" + secondDigit));
                        break;
                    }
                }
                if(!firstDigitFound || !secondDigitFound){
                    throw new IllegalArgumentException("No digit found in the line" + line);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;         
    }
}
