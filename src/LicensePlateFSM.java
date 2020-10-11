import java.util.Scanner;
import java.util.stream.IntStream;

public class LicensePlateFSM {


    private int currentState;
    private int startState;
    private Scanner scanner;
    private int[] endStates;
    private String letters;
    private int[] digits;
    private boolean invalid;

    public LicensePlateFSM() {
        scanner = new Scanner(System.in);
        startState = 0;
        endStates = new int[]{4, 5, 6, 7};
        letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ";
        digits = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        startFSM();
    }

    public static void main(String[] args) {
        new LicensePlateFSM();
    }

    public void startFSM() {
        // resets current state to startState
        currentState = startState;

        // Starts the console interface
        System.out.println("Enter word to check \n Type '/quit' or '/q' to quit");
        String input = scanner.next();
        try {
            if (checkWord(input)) {
                System.out.println("Word is part of language");
            } else if (input.equals("/quit") || input.equals("/q")) {
                System.exit(0);
            } else {
                System.out.println("Word is not part of language");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Error - no word entered");
        }
        startFSM();
    }

    public boolean checkWord(String word) {
        // Returns true if word is part of language

        invalid = false;
        for (int i = 0; i < word.length(); i++) {
            if (!invalid) {
                // Go through every character of the given word unless invalid entry has already been made
                changeState(word.charAt(i));
            }
        }
        // Checks if currentState is any of the end states
        return !invalid && IntStream.of(endStates).anyMatch(x -> x == currentState);


    }

    public boolean isLetter(char c) {
        return letters.indexOf(c) >= 0;
    }

    public boolean isDigit(int i) {
        return IntStream.of(digits).anyMatch(x -> x == i);
    }

    public boolean isDigitNZ(int i) {
        // NZ = No zero
        return i != 0 && isDigit(i);
    }

    public void changeState(char c) {
        // Check if/how the state changes, depending on current char and current state
        switch (currentState) {
            case 0:
            case 2:
                if (isLetter(c)) {
                    currentState++;
                } else {
                    invalid = true;
                }
                break;
            case 1:
                if (c == '-') {
                    currentState = 2;
                } else if (isLetter(c)) {
                    currentState = 8;
                } else {
                    invalid = true;
                }
                break;
            case 3:
                if (isDigitNZ(Character.getNumericValue(c))) {
                    currentState = 4;
                } else if (isLetter(c)) {
                    currentState = 10;
                } else {
                    invalid = true;
                }
                break;
            case 4:
            case 5:
            case 6:
                if (isDigit(Character.getNumericValue(c))) {
                    currentState++;
                } else {
                    invalid = true;
                }
                break;
            case 8:
                if (c == '-') {
                    currentState = 2;
                } else if (isLetter(c)) {
                    currentState = 9;
                } else {
                    invalid = true;
                }
                break;
            case 9:
                if (c == '-') {
                    currentState = 2;
                } else {
                    invalid = true;
                }
                break;

            case 10:
                if (isDigitNZ(Character.getNumericValue(c))) {
                    currentState = 4;
                } else {
                    invalid = true;
                }
                break;
        }
    }
}



