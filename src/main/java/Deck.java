import java.io.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Deck {

    private List<Flashcard> cards = new ArrayList<>();

    public void loadFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 2) {
                    cards.add(new Flashcard(parts[0], parts[1]));
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public List<Flashcard> getCards() {
        return cards;
    }

    public void addCardToFile(String fileName, String question, String answer) {
    try (FileWriter fw = new FileWriter(fileName, true)) {
        fw.write(question + "|" + answer + "\n");
    } catch (IOException e) {
        System.out.println("Error writing to file!");
    }
}
}