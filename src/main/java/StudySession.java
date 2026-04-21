import java.util.*;

public class StudySession {

    private List<Flashcard> cards;
    private CardOrganizer organizer;

    public StudySession(List<Flashcard> cards, CardOrganizer organizer) {
        this.cards = cards;
        this.organizer = organizer;
    }

    public void start(int repetitions, boolean invert) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            boolean anyMistake = false;
            int mistakeOrder = 0;

            List<Flashcard> currentRound = organizer.organize(cards);

            for (Flashcard card : cards) {
                card.setLastRoundWrong(false);
            }

            for (Flashcard card : currentRound) {

                String question = invert ? card.getAnswer() : card.getQuestion();
                String answer = invert ? card.getQuestion() : card.getAnswer();
                String questionText = "Translate \"" + question + "\" into English";

                int correctInRow = 0;

                while (correctInRow < repetitions) {

                    System.out.println("Question: " + questionText);
                    System.out.print("> ");
                    String user = scanner.nextLine().trim();

                    if (user.equalsIgnoreCase(answer)) {
                        System.out.println("Correct!");
                        card.recordAttempt(true);
                        correctInRow++;
                    } else {
                        System.out.println("Wrong! Correct answer: " + answer);
                        card.recordAttempt(false);
                        correctInRow = 0;

                        card.setLastRoundWrong(true);
                        card.setLastWrongOrder(mistakeOrder++);
                        anyMistake = true;
                    }
                }
            }

            if (!anyMistake) {
                System.out.println("\nAll cards answered correctly!");
                break;
            } else {
                System.out.println("\n--- You have mistakes. Restarting full round ---\n");
            }
        }

        checkAchievements();
    }

    private void checkAchievements() {

        boolean allCorrect = true;
        boolean repeatAchieved = false;
        boolean confidentAchieved = false;

        for (Flashcard card : cards) {
            if (card.getCorrectCount() == 0) {
                allCorrect = false;
            }
            if (card.getAttempts() > 5) {
                repeatAchieved = true;
            }
            if (card.getCorrectCount() >= 3) {
                confidentAchieved = true;
            }
        }

        if (allCorrect) {
            System.out.println("Achievement: CORRECT");
        } else {
            if (repeatAchieved) {
                System.out.println("Achievement: REPEAT");
            }
            if (confidentAchieved) {
                System.out.println("Achievement: CONFIDENT");
            }
        }
    }
}