// Jun Park
import java.util.Scanner;

public class Console {
    public static TicTacToe game;
    public Console() {
        game = new TicTacToe();
        game.initializeBoard();
    }

    public static void singleConsole() {
        Scanner sc = new Scanner(System.in);
        while (!game.isBoardFull() && !game.checkForWin()) {
            System.out.println("\n=====================================\n");
            game.printBoard(); // Print board.

            System.out.println("This is " + game.getCurrentPlayerMark() + "'s turn.");
            boolean check = true; // Repeat until correct value is received.
            while (check) {
                try {
                    System.out.print("Please enter the column: ");
                    int col = sc.nextInt(); // Get Column.

                    System.out.print("Please enter the row: ");
                    int row = sc.nextInt(); // Get Row.

                    check = !game.placeMark(row, col); // Try to place the marker, if false returned, repeat the loop
                } catch (Exception e) { // If the input value has a different data types.
                    check = true;
                    System.out.println("Please input the valid input.\n");
                    sc.nextLine();
                }
            }
            checkWin();
        }
    }

    // Jun's Algorithm
    public static void playAI(String aimark){ // AI game.
        Scanner sc = new Scanner(System.in);
        AI ai = new AI(); // Set up AI class.
        String markAI = aimark;

        while (!game.isBoardFull() && !game.checkForWin()) {
            System.out.println("\n=====================================\n");
            game.printBoard();

            System.out.println("This is " + game.getCurrentPlayerMark() + "'s turn.");
            if (game.getCurrentPlayerMark().equals(markAI)) { // if it is AI's turn
                int[] aiCalc = ai.calc(game, aimark);
                game.placeMark(aiCalc[0], aiCalc[1]);
            } else {
                boolean check = true; // Repeat until correct value is received.
                while (check) {
                    try {
                        System.out.print("Please enter the column: ");
                        int col = sc.nextInt(); // Get Column.

                        System.out.print("Please enter the row: ");
                        int row = sc.nextInt(); // Get Row.

                        check = !game.placeMark(row, col); // Try to place the marker, if false returned, repeat the loop
                    } catch (Exception e) { // If the input value has a different data types.
                        check = true;
                        System.out.println("Please input the valid input.\n");
                        sc.nextLine();
                    }
                }
            }
            checkWin();
        }
    }

    // Check win or tie
    public static void checkWin() {
        if (game.checkForWin()) {
            System.out.println("=====================================\n");
            game.printBoard();
            System.out.println("Winner Winner Chicken Dinner!");
            System.out.println("Winner is " + game.getCurrentPlayerMark() + "!");
        } else if (game.isBoardFull()) {
            System.out.println("=====================================\n");
            game.printBoard();
            System.out.println("Tie");
        }
        game.changePlayer();
    }
}
