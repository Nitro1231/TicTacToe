// Jun Park

import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome to Tic Tac Toe game!\n");
        System.out.println("1. Single");
        System.out.println("2. AI (hard)");
        System.out.println("3. Online Match");

        int selectMode = 0;
        boolean modeCheck = false, guiCheck = false, markCheck = false;
        String aiMark = ".", gui = ".";
        while (true) { // Repeat until correct value is received.
            try {
                if (!modeCheck) { // Game mode selection.
                    System.out.print("Please select the Mode: ");
                    selectMode = sc.nextInt();
                    if (selectMode >= 1 && selectMode <= 3) {
                        modeCheck = true;
                        sc.nextLine();
                    }
                }

                if (!guiCheck && modeCheck) { // GUI selection.
                    System.out.print("\nGUI? (Y/N): ");
                    gui = sc.nextLine().toUpperCase(); // Support lower case too.
                    if (gui.equals("Y") || gui.equals("N")) {
                        guiCheck = true;
                    }
                }

                if (selectMode == 2 && guiCheck) {
                    if (!markCheck) {
                        System.out.print("\nPlease select the AI's mark (X will play first): ");
                        aiMark = sc.nextLine().toUpperCase(); // Set AI's marker.
                        if (aiMark.equals("O") || aiMark.equals("X"))
                            markCheck = true;
                    }
                } else { markCheck = true; } // If they did not chose AI mode, skip this.

                if (modeCheck && guiCheck && markCheck) // If all of options all received in correct form.
                    break;

            } catch (Exception e) { // If the input value has a different data types.
                System.out.println("Please input the valid input.\n");
                sc.nextLine();
            }
        }

        //==================================================

        switch(selectMode) {
            case 1: // Single Play
                if (gui.equals("N")) {
                    Console singleConsole = new Console();
                    singleConsole.singleConsole();
                } else {
                    GUI singleGUI = new GUI(false, aiMark);
                }
                break;
            case 2: // AI play
                if (gui.equals("N")) {
                    Console aiConsole = new Console();
                    aiConsole.playAI(aiMark);
                } else {
                    GUI aiGUI = new GUI(true, aiMark);
                }
                break;
            case 3: // Online Mode
                    // Since I spend last few hours on new AI programming, it is not supported yet.
                    System.out.println("THis is not ready yet.");
                break;
        }
        System.out.println("end of program.");
    }
}