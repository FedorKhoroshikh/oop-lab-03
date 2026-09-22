package lab3;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Demonstration: loads a dictionary from a file and translates whatever the
 * user types, one line at a time, until an empty line ends the session.
 */
public final class Main {

    private static final Path DEFAULT_DICTIONARY = Path.of("dictionary.txt");

    /** Where the log goes. The coursework will plug a text area in here. */
    private static final Consumer<String> OUT = System.out::println;

    public static void main(String[] args) {
        Path dictionaryPath = args.length > 0 ? Path.of(args[0]) : DEFAULT_DICTIONARY;

        Dictionary dictionary;
        try {
            dictionary = Dictionary.loadFrom(dictionaryPath);
        } catch (FileReadException | InvalidFileFormatException e) {
            OUT.accept("Could not load the dictionary: " + e.getMessage());
            return;
        }

        Translator translator = new Translator(dictionary);
        OUT.accept("=== Lab 3: a phrase translator ===");
        OUT.accept("Dictionary loaded from " + dictionaryPath.toAbsolutePath());
        OUT.accept("Type a line to translate it, or press Enter on an empty line to exit.");

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = readLine(in);
            if (line == null || line.isBlank()) {
                break;
            }
            OUT.accept(translator.translate(line));
        }
        OUT.accept("Done.");
    }

    private static String readLine(Scanner in) {
        try {
            return in.nextLine();
        } catch (NoSuchElementException e) {
            return null;                  // input is over (Ctrl+Z or a pipe)
        }
    }
}
