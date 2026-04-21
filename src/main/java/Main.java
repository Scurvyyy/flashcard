import java.util.*;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("flashcard <cards-file> [options]");
            return;
        }

        for (String arg : args) {
            if (arg.equals("--help")) {
                printHelp();
                return;
            }
        }

        String fileName = args[0];

        String order = "random";
        int repetitions = 1;
        boolean invert = false;
        boolean addMode = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {

                case "--order":
                    if (i + 1 >= args.length) {
                        System.out.println("Missing order value!");
                        return;
                    }
                    order = args[++i];
                    break;

                case "--repetitions":
                    if (i + 1 >= args.length) {
                        System.out.println("Missing repetitions value!");
                        return;
                    }
                    repetitions = Integer.parseInt(args[++i]);
                    break;

                case "--invertCards":
                    invert = true;
                    break;

                case "--add":
                    addMode = true;
                    break;

                // Bug 1 fixed: removed the stray "worst-first" case that
                // referenced an undeclared variable. It is handled below.

                default:
                    System.out.println("Unknown option: " + args[i]);
                    return;
            }
        }

        if (addMode) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter question: ");
            String q = scanner.nextLine();

            System.out.print("Enter answer: ");
            String a = scanner.nextLine();

            // Bug 4 fix (partial): validate before writing
            if (q.contains("|") || a.contains("|")) {
                System.out.println("Error: question and answer cannot contain '|'");
                return;
            }

            Deck deck = new Deck();
            deck.addCardToFile(fileName, q, a);

            System.out.println("Card added!");
            return;
        }

        Deck deck = new Deck();
        deck.loadFromFile(fileName);

        // Bug 5 fix: guard against empty/missing file
        if (deck.getCards().isEmpty()) {
            System.out.println("No cards found in file: " + fileName);
            return;
        }

        CardOrganizer organizer;

        switch (order) {
            case "recent-mistakes-first":
                organizer = new RecentMistakesFirstSorter();
                break;

            // Bug 2 fixed: worst-first now uses WorstFirstSorter
            case "worst-first":
                organizer = new WorstFirstSorter();
                break;

            case "random":
                organizer = new RandomSorter();
                break;

            default:
                System.out.println("Invalid order type!");
                return;
        }

        // Bug 3 fix (partial): pass organizer into StudySession
        // No longer pre-organizing here; StudySession re-organizes each round
        StudySession session = new StudySession(deck.getCards(), organizer);
        session.start(repetitions, invert);
    }

    public static void printHelp() {
        System.out.println("flashcard <cards-file> [options]");
        System.out.println("--help");
        System.out.println("--order random | worst-first | recent-mistakes-first");
        System.out.println("--repetitions <num>");
        System.out.println("--invertCards");
        System.out.println("--add");
    }
}