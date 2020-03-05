// Jun's algorithm
// This model is based on how a human player plays.

import java.util.ArrayList;
import java.util.Random;

public class AI {
    String playerMark, aiMark;
    String[][] board;
    int turn = 0;
    public int[] calc(TicTacToe game, String aiMark) {
        this.aiMark = aiMark;
        board = game.getBoard();
        turn = countTurn(board); // Counting how many turns were played.

        if (aiMark.equals("X"))
            playerMark = "O";
        else
            playerMark = "X";

        if (board[1][1].equals("-")) // Return middle point if there is nothing on it.
            return new int[]{1, 1};

        // Checking must winning point.
        // Condition: Two spaces consist of AI's markers and one space is empty.
        int r = 0;
        for (String[] row : board) { // Row line check
            if (mustWin(aiMark, 2, row)) // if it is winning condition.
                return getWinLoc(new int[][] {{r,0}, {r,1}, {r,2}}); // Return winning location.
            r++;
        }
        for (int col = 0; col < 3; col++) { // Column line check
            String arr[] = new String[]{board[0][col], board[1][col], board[2][col]};
            if (mustWin(aiMark, 2, arr))
                return getWinLoc(new int[][] {{0, col}, {1, col}, {2, col}});
        }
        // Diagonal line check
        if (mustWin(aiMark, 2, new String[]{board[0][0], board[1][1], board[2][2]}))
            return getWinLoc(new int[][] {{0, 0}, {1, 1}, {2, 2}});
        if (mustWin(aiMark, 2, new String[]{board[2][0], board[1][1], board[0][2]}))
            return getWinLoc(new int[][] {{2, 0}, {1, 1}, {0, 2}});

        // Blocking defeat point.
        // Condition: Two spaces consist of the player's markers, one empty.
        r = 0;
        for (String[] row : board) {
            if (mustWin(playerMark, 2, row))
                return getWinLoc(new int[][] {{r,0}, {r,1}, {r,2}});
            r++;
        }
        for (int col = 0; col < 3; col++) {
            String arr[] = new String[]{board[0][col], board[1][col], board[2][col]};
            if (mustWin(playerMark, 2, arr))
                return getWinLoc(new int[][] {{0, col}, {1, col}, {2, col}});
        }
        if (mustWin(playerMark, 2, new String[]{board[0][0], board[1][1], board[2][2]}))
            return getWinLoc(new int[][] {{0, 0}, {1, 1}, {2, 2}});
        if (mustWin(playerMark, 2, new String[]{board[2][0], board[1][1], board[0][2]}))
            return getWinLoc(new int[][] {{2, 0}, {1, 1}, {0, 2}});


        // Making most winning point.
        // Condition: One space consists of AI's markers and the other is blank.
        if (turn != 1) { // The first turn is skipped as it is meaningless at the beginning.
            r = 0;
            for (String[] row : board) {
                if (mustWin(aiMark, 1, row)) // Check the condition.
                    return getWinLoc(new int[][]{{r, 0}, {r, 1}, {r, 2}});
                r++;
            }
            for (int col = 0; col < 3; col++) {
                String arr[] = new String[]{board[0][col], board[1][col], board[2][col]};
                if (mustWin(aiMark, 1, arr))
                    return getWinLoc(new int[][]{{0, col}, {1, col}, {2, col}});
            }
            if (mustWin(aiMark, 1, new String[]{board[0][0], board[1][1], board[2][2]}))
                return getWinLoc(new int[][]{{0, 0}, {1, 1}, {2, 2}});
            if (mustWin(aiMark, 1, new String[]{board[2][0], board[1][1], board[0][2]}))
                return getWinLoc(new int[][]{{2, 0}, {1, 1}, {0, 2}});

            // Blocking most winning point of human player.
            // Condition: One space consists of player's markers and the other is blank.
            r = 0;
            for (String[] row : board) {
                if (mustWin(playerMark, 1, row))
                    return getWinLoc(new int[][]{{r, 0}, {r, 1}, {r, 2}});
                r++;
            }
            for (int col = 0; col < 3; col++) {
                String arr[] = new String[]{board[0][col], board[1][col], board[2][col]};
                if (mustWin(playerMark, 1, arr))
                    return getWinLoc(new int[][]{{0, col}, {1, col}, {2, col}});
            }
            if (mustWin(playerMark, 1, new String[]{board[0][0], board[1][1], board[2][2]}))
                return getWinLoc(new int[][]{{0, 0}, {1, 1}, {2, 2}});
            if (mustWin(playerMark, 1, new String[]{board[2][0], board[1][1], board[0][2]}))
                return getWinLoc(new int[][]{{2, 0}, {1, 1}, {0, 2}});
        }

        // Random corners place.
        ArrayList<Integer[]> rdInt1 = new ArrayList<Integer[]>();
        // Add corner locations in arraylist
        rdInt1.add(new Integer[] {0, 0});
        rdInt1.add(new Integer[] {2, 0});
        rdInt1.add(new Integer[] {0, 2});
        rdInt1.add(new Integer[] {2, 2});
        for (int i = rdInt1.size() - 1; i >= 0; i--){
            // Remove corners if corner is not empty.
            if (!board[rdInt1.get(i)[0].intValue()][rdInt1.get(i)[1].intValue()].equals("-"))
                rdInt1.remove(i);
        }
        Random rd = new Random();
        int rdNum = rd.nextInt(rdInt1.size()); // Range: 0 ~ rdInt1.size() - 1
        if (rdInt1.size() > 0) // check if there is any available corner.
            return new int[]{rdInt1.get(rdNum)[0], rdInt1.get(rdNum)[1]};

        // Random placing.
        // In case all of the above conditions are insufficient.
        ArrayList<Integer[]> rdInt2 = new ArrayList<Integer[]>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].equals("-"))
                    rdInt2.add(new Integer[] {row, col});
            }
        }
        rdNum = rd.nextInt(rdInt2.size()); //Range: 0 ~ rdInt2.size() - 1
        return new int[] {rdInt2.get(rdNum)[0], rdInt2.get(rdNum)[1]};
    }

    // Condition check
    public boolean mustWin(String player, int num, String[] arr) {
        // player: a player that looking for the winning condition.
        // num: number of markers that condition looking for.
        // arr: Array that containing inspection conditions.
        String against; // Enemy marker
        if (player.equals("X"))
            against = "O";
        else
            against = "X";

        int c = 0;
        for (String str : arr) {
            if (str.equals(against))
                return false; // if it is not following the condition.
            else if (str.equals(player))
                c++; // Count how much markers that line has.
        }
        return c == num; // Return total number of markers that line has.
    }

    // Return the locations of a blank space.
    public int[] getWinLoc(int[][] arr) {
        for (int i = 0; i < 3; i++){
            if (board[arr[i][0]][arr[i][1]].equals("-"))
                return arr[i]; // Return empty space
        }
        return new int[] {-1}; // This never going to happen.
    }

    // Count how many turns that both players played.
    public int countTurn(String[][] b){
        int c = 0;
        for (String[] row : b)
            for (String col : row)
                if (col.equals("X") || col.equals("O"))
                    c++;
        return c;
    }
}
