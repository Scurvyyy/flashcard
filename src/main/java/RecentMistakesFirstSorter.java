import java.util.ArrayList;
import java.util.List;
/*
    1. Recent Mistakes First: Буруу хариулсан картуудыг эхэнд нь байрлуулна.
 */
public class RecentMistakesFirstSorter implements CardOrganizer {
 
    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {

        List<Flashcard> wrongCards = new ArrayList<>();
        List<Flashcard> correctCards = new ArrayList<>();

        for (Flashcard card : cards) {
            if (card.wasWrongLastRound()) {
                wrongCards.add(card);
            } else {
                correctCards.add(card);
            }
        }

        wrongCards.sort((a, b) -> b.getLastWrongOrder() - a.getLastWrongOrder());

        // Merge (wrong first)
        List<Flashcard> result = new ArrayList<>();
        result.addAll(wrongCards);
        result.addAll(correctCards);

        return result;
    }
}