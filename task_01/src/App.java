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

        int sum;
        // PART ONE
        // sum = partOneSolveUsingInitialIdea(input).stream().mapToInt(Integer::intValue).sum();
        sum = partOneSolveUsingStreamAPI(input).stream().mapToInt(Integer::intValue).sum();
        System.out.println("Part One: " + sum);

        // PART TWO
        // sum = partTwoSolveUsingInitialIdea(input).stream().mapToInt(Integer::intValue).sum();
        sum = partTwoSolveUsingStreamAPI(input).stream().mapToInt(Integer::intValue).sum();
        System.out.println("Part Two: " + sum);
    }

    /* === PART ONE === */

    public static List<Integer> partOneSolveUsingInitialIdea(String input){
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
                    throw new IllegalArgumentException("No digit found in the line " + line);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;         
    }

    public static List<Integer> partOneSolveUsingStreamAPI(String input) {
        List<Integer> values = new ArrayList<>();
        try{
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    char firstDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));

                    char secondDigit = new StringBuilder(line).reverse().chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));
                        
                    return Integer.parseInt(firstDigit + "" + secondDigit);
                })
                .collect(Collectors.toList());
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;
    }

    /* === PART TWO === */

    public static List<Integer> partTwoSolveUsingInitialIdea(String input){
        List<Integer> values = new ArrayList<>();
        try{
            List<String> lines = Files.lines(Paths.get(input)).collect(Collectors.toList());
            for(String line : lines){
                line = handleNumberNames(line);
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
                    throw new IllegalArgumentException("No number found in the line " + line);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;         
    }

    public static List<Integer> partTwoSolveUsingStreamAPI(String input) {
        List<Integer> values = new ArrayList<>();
        try{
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    String curedLine = handleNumberNames(line);
                    char firstDigit = curedLine.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));

                    char secondDigit = new StringBuilder(curedLine).reverse().chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));
                        
                    return Integer.parseInt(firstDigit + "" + secondDigit);
                })
                .collect(Collectors.toList());
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;
    }

    private static String handleNumberNames(String inputString){
        return inputString.replaceAll("one", "o1e")
                          .replaceAll("two", "t2o")
                          .replaceAll("three", "t3ree")
                          .replaceAll("four", "f4ur")
                          .replaceAll("five", "f5ve")   
                          .replaceAll("six", "s6x")
                          .replaceAll("seven", "s7ven")
                          .replaceAll("eight", "e8ght")
                          .replaceAll("nine", "n9ne");

     // 326sevenfivenseven1kctgmnqtwonefq -> if I would replace "two" with 2 I would lose the "one" => wrong result
    }









    /* === REST OF PART ONE === */
    /* Done for learning // testing purposes */

    public static List<Integer> partOneSolveUsingStringAPIWithoutSB(String input) {
        List<Integer> values = new ArrayList<>();
        try{
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    char firstDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line "+line));

                    char secondDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .reduce((first, second) -> second)
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));
                        
                    return Integer.parseInt(firstDigit + "" + secondDigit);
                })
                .collect(Collectors.toList());
        } catch(IOException e){
            e.printStackTrace();
        }
        return values;
    }

    public static List<Integer> partOneSolveUsingStreamAPIReduced(String input) {
        List<Integer> values = new ArrayList<>();
        try {
            values = Files.lines(Paths.get(input))
                .map(line -> {
                    List<Character> digitsList = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .collect(Collectors.toList());

                    if (digitsList.size() < 1) {
                        throw new IllegalArgumentException("Not enough digits in the line " + line);
                    }

                    return Integer.parseInt(digitsList.get(0) + "" + digitsList.get(digitsList.size() - 1));
                })
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    public static List<Integer> partOneSolveUsingRegex(String input) {
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

    public static List<Integer> partOneSolveUsingRegex2(String input){
        List<Integer> values = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d");

        try {
            values = Files.lines(Paths.get(input))
            .map(line -> {
                Matcher matcher = pattern.matcher(line);
                if (!matcher.find()) {
                    throw new IllegalArgumentException("No digit found in the line " + line);
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

    public static List<Integer> partOneSolveUsingParallelism(String input){
        CopyOnWriteArrayList<Integer> values = new CopyOnWriteArrayList<>();
        try {
            Files.lines(Paths.get(input))
                .parallel()
                .forEach(line -> {
                    char firstDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));

                    char secondDigit = line.chars()
                        .mapToObj(c -> (char) c)
                        .filter(Character::isDigit)
                        .reduce((first, second) -> second)
                        .orElseThrow(() -> new IllegalArgumentException("No digit found in the line " + line));

                    values.add(Integer.parseInt(firstDigit + "" + secondDigit));
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}
