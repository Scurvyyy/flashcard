import java.util.*;

public class WorstFirstSorter implements CardOrganizer {

    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {

        List<Flashcard> sorted = new ArrayList<>(cards);

        sorted.sort((a, b) -> {
            int mistakesA = a.getAttempts() - a.getCorrectCount();
            int mistakesB = b.getAttempts() - b.getCorrectCount();

            return Integer.compare(mistakesB, mistakesA); // DESC
        });

        return sorted;
    }
}