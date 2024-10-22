import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        String input = "res" + java.io.File.separator + "long_input.txt";

        //int sum = solveUsingInitialIdea(input).stream().mapToInt(Integer::intValue).sum();
        int sum = solveUsingStreamAPI(input).stream().mapToInt(Integer::intValue).sum();
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

    public static List<Integer> solveUsingStreamAPI(String input) {
        List<Integer> values = new ArrayList<>();
        try{
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    char firstDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line"+line));

                    char secondDigit = new StringBuilder(line).reverse().chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line" + line));
                        
                    return Integer.parseInt(firstDigit + "" + secondDigit);
                })
                .collect(Collectors.toList());
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;
    }

    public static List<Integer> solveUsingStringAPIWithoutSB(String input) {
        List<Integer> values = new ArrayList<>();
        try{
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    char firstDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line"+line));

                    char secondDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .reduce((first, second) -> second)
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line" + line));
                        
                    return Integer.parseInt(firstDigit + "" + secondDigit);
                })
                .collect(Collectors.toList());
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;
    }

    public static List<Integer> solveUsingStreamAPIReduced(String input) {
        List<Integer> values = new ArrayList<>();
        try {
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    List<Character> digitsList = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .collect(Collectors.toList());

                    if (digitsList.size() < 1) {
                        throw new IllegalArgumentException("Not enough digits in the line" + line);
                    }

                    return Integer.parseInt(digitsList.get(0) + "" + digitsList.get(digitsList.size() - 1));
                })
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    public static List<Integer> solveUsingRegex(String input) {
        List<Integer> values = new ArrayList<>();
        try {
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    String lineWithoutNonDigits = line.replaceAll("\\D", "");
                    String firstDigit = lineWithoutNonDigits.substring(0, 1);
                    String secondDigit = lineWithoutNonDigits.substring(lineWithoutNonDigits.length() - 1);
                    return Integer.parseInt(firstDigit + secondDigit);
                })
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();  
        }  
        return values;
    }

    public static List<Integer> solveUsingRegex2(String input){
        List<Integer> values = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d");

        try {
            values = Files.lines(Paths.get(input))
            .map(line -> {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.find()) {
                    throw new IllegalArgumentException("No digit found in the line" + line);
                }
                char firstDigit = matcher.group().charAt(0);

                // Find the last digit by iterating through all matches
                char secondDigit = firstDigit;
                while (matcher.find()) {
                    secondDigit = matcher.group().charAt(0);
                }

                return Integer.parseInt(firstDigit + "" + secondDigit);
            })
            .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    public static List<Integer> solveUsingParallelism(String input){
        CopyOnWriteArrayList<Integer> values = new CopyOnWriteArrayList<>();
        try {
            Files.lines(Paths.get(input))
                .parallel()
                .forEach(line -> {
                    char firstDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line" + line));

                    char secondDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .reduce((first, second) -> second)
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line" + line));

                    values.add(Integer.parseInt(firstDigit + "" + secondDigit));
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}
