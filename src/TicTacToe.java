class TicTacToe {
    private String userMark;
    private String[][] board;
    boolean gameOver = false;
    public static int humants, aits;
    // "X", "O", "-"

    //initialize player mark
    public TicTacToe() {
        board = new String[3][3];
        userMark = "X";
    }

    public String[][] getBoard(){
        return board;
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = "-";
    }

    public boolean isBoardFull() {
        for (String[] row : board)
            for (String col : row)
                if (col.equals("-"))
                    return false;
        gameOver = true;
        return true;
    }

    public void printBoard() {
        System.out.println("  0 1 2");
        for (int row = 0; row < 3; row++) {
            System.out.print(row);
            for (String col : board[row])
                System.out.print(" " + col);

            System.out.println();
        }
        System.out.println();
    }

    public void changePlayer() {
        if (userMark.equals("O")) { userMark = "X"; }
        else userMark = "O";
    }

    //returns the player mark
    public String getCurrentPlayerMark() {
        return userMark;
    }

    public boolean placeMark(int row, int col) {
        try {
            if (board[row][col].equals("-")) {
                board[row][col] = userMark;
                return true;
            } else {
                System.out.println("Please enter different location.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Please input the valid input.");
            return false;
        }
    }

    public boolean checkForWin() {
        String[] checkArr = new String[9];
        gameOver = true;
        int index = 0;
        for (String[] row : board) {
            for (String col : row){
                checkArr[index] = col;
                index++;
            }
        }

        for (int i = 0; i <= 6; i += 3)
            if (checkLine(checkArr, i, i + 1, i + 2))
                return true;
        for (int i = 0; i < 3; i++)
            if (checkLine(checkArr, i, i + 3, i + 6))
                return true;
        if (checkLine(checkArr, 0, 4, 8))
            return true;
        else if (checkLine(checkArr, 2, 4, 6))
            return true;
        gameOver = false;
        return false;
    }

    public boolean checkLine(String[] arr, int p1, int p2, int p3) {
        return !arr[p1].equals("-") && arr[p1].equals(arr[p2]) && arr[p2].equals(arr[p3]);
    }

    public boolean isGameOver () {
        return gameOver;
    }
}