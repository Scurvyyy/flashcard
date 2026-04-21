
public class Flashcard {
    private String question;
    private String answer;

    private int attempts = 0;
    private int correctCount = 0;
    private boolean wasWrong = false;
    private boolean lastRoundWrong = false;
    private int lastWrongOrder = 0;

    public Flashcard(String q, String a) {
        this.question = q;
        this.answer = a;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }

    
    public void recordAttempt(boolean correct) {
        attempts++;
        if (correct) {
            correctCount++;
            wasWrong = false;
        } else {
            wasWrong = true;
        }
    }

    public int getAttempts() { return attempts; }
    public int getCorrectCount() { return correctCount; }

    public boolean wasWrong() { return wasWrong; }

    public void setLastRoundWrong(boolean value) {
        this.lastRoundWrong = value;
    }

    public boolean wasWrongLastRound() {
        return lastRoundWrong;
    }
    public void setLastWrongOrder(int order) {
        this.lastWrongOrder = order;
    }
    public int getLastWrongOrder() {
        return lastWrongOrder;
    }
    
}