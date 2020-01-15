package server;

import model.Board;

import java.util.ArrayList;

public class GomokuProtocol {
    private static final int WAITING = 0;
    private static final int HAVE_ID = 1;
    private static final int CREATE_BOARD = 2;
    private static final int GUEST_IN_BOARD = 3;
    private static final int WIN = 4;
    private static final int LOSE = 5;
//    private static final int SENTKNOCKKNOCK = 1;
//    private static final int SENTCLUE = 2;
//    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?" };

    private RandomMachine randomMachine = new RandomMachine();
    private DatabaseConnector databaseConnector = new DatabaseConnector();

   public GomokuProtocol() {

       ArrayList<Board> boards = databaseConnector.getFreeBoard();
       new JSONController().parseBoardsToJSON(boards);

   }

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = generateRandomNumber();
            state = HAVE_ID;
        }
//        } else if (state == SENTKNOCKKNOCK) {
//            if (theInput.equalsIgnoreCase("Who's there?")) {
//                theOutput = clues[currentJoke];
//                state = SENTCLUE;
//            } else {
//                theOutput = "You're supposed to say \"Who's there?\"! " +
//                        "Try again. Knock! Knock!";
//            }
//        } else if (state == SENTCLUE) {
//            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
//                theOutput = answers[currentJoke] + " Want another? (y/n)";
//                state = ANOTHER;
//            } else {
//                theOutput = "You're supposed to say \"" +
//                        clues[currentJoke] +
//                        " who?\"" +
//                        "! Try again. Knock! Knock!";
//                state = SENTKNOCKKNOCK;
//            }
//        } else if (state == ANOTHER) {
//            if (theInput.equalsIgnoreCase("y")) {
//                theOutput = "Knock! Knock!";
//                if (currentJoke == (NUMJOKES - 1))
//                    currentJoke = 0;
//                else
//                    currentJoke++;
//                state = SENTKNOCKKNOCK;
//            } else {
//                theOutput = "Bye.";
//                state = WAITING;
//            }
//        }
        return theOutput;
    }

    String generateRandomNumber() {
        return randomMachine.nextString();
    }
}
