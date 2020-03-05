//Jun Park
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    TicTacToe game = new TicTacToe();
    JScrollPane scrollPane;
    ImageIcon imgBG, imgO, imgX;
    JOptionPane msgBox;
    JPanel background;

    boolean modeAI = false;
    String aiMark = "0";

    public GUI(boolean AI, String aiMark) {
        modeAI = AI;
        this.aiMark = aiMark;
        game.initializeBoard();

        // Load image files.
        imgBG = new ImageIcon("src/img/BG.png");
        imgO = new ImageIcon("src/img/o.png");
        imgX = new ImageIcon("src/img/x.png");
        msgBox = new JOptionPane();

        // Create a background panel and specify it as a content page.
        background = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(imgBG.getImage(), 0, 0, 601, 601, null); // Image draw.
                setOpaque(false); // Transparency for drawing.
                super.paintComponent(g);
            }
        };

        // Set button absolute position layout.
        background.setLayout(null);

        // Create and initialize buttons.
        JButton[] buttons = new JButton[9];
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(new ImageIcon("")); // blank image
            buttons[i].setSize(200,200);

            // Create Transparent Button.
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setFocusPainted(false);
            buttons[i].setOpaque(false);
        }

        // Add buttons to form and assign event Listeners.
        int i = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++){
                buttons[i].setBounds(c * 200, r * 200, 200, 200); // Set the location on each 200 * 200 pixel.
                buttons[i].setName(r + " " + c); // Set the button name as a location.
                buttons[i].addActionListener(new ActionListener(){ // Add the Listener for the click event.
                    public void actionPerformed(ActionEvent e) {
                        JButton btn = (JButton) e.getSource(); // Get information about the button that the player clicked.
                        if (game.checkForWin() || game.isBoardFull()) // Block the click event when the game is done.
                            btn.setName("false");

                        if (!btn.getName().equals("false")) { // Check if button name is not a false.
                            String[] loc = btn.getName().split(" "); // Based on line 54, get the marker location.
                            if (game.placeMark(Integer.parseInt(loc[0]), Integer.parseInt(loc[1]))) {
                                //Place the marker at the location.
                                if (game.getCurrentPlayerMark().equals(("X")))
                                    btn.setIcon(imgX);
                                else
                                    btn.setIcon(imgO);
                                game.printBoard();

                                btn.setName("false"); // Button deactivation.

                                // Checking the game status.
                                checkWinner();

                                if (!game.gameOver)
                                    game.changePlayer();

                                if (!game.gameOver && modeAI && game.getCurrentPlayerMark().equals(aiMark)) // Play AI
                                    playAI();
                            }
                        }
                    }});
                background.add(buttons[i]); // Add buttons on GUI form.
                i++;
            }
        }

        // Set up Windows
        scrollPane = new JScrollPane(background);
        setContentPane(scrollPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);

        if (game.getCurrentPlayerMark().equals(aiMark))
            playAI();
    }


    public void playAI(){
        AI ai = new AI(); // Setup AI Class
        int[] Loc = ai.calc(game, aiMark); // Calculate the best place for AI
        game.placeMark(Loc[0], Loc[1]);
        for (Component c : background.getComponents()) { // Check every button.
            if (c.getName().equals(Loc[0] + " " + Loc[1])) { // If this button is AI's choice.
                if (game.getCurrentPlayerMark().equals(("X"))) // Change the button's image with AI's marker image.
                    ((JButton)c).setIcon(imgX);
                else
                    ((JButton)c).setIcon(imgO);
                c.setName("false"); // Button deactivation
            }
        }
        game.printBoard();
        checkWinner(); // check winner winner chicken dinner
        game.changePlayer();
    }

    // Check win or tie
    public void checkWinner() {
        if (game.checkForWin()) {
            System.out.println("=====================================\n");
            game.printBoard();
            System.out.println("Winner Winner Chicken Dinner!");
            System.out.println("Winner is " + game.getCurrentPlayerMark() + "!");
            // Popup the msgbox
            msgBox.showMessageDialog(null,"Winner Winner Chicken Dinner!\n\n" + "Winner is " + game.getCurrentPlayerMark() + "!");
        } else if (game.isBoardFull()) {
            System.out.println("=====================================\n");
            game.printBoard();
            System.out.println("Tie");
            // Popup the msgbox
            msgBox.showMessageDialog(null, "Tie.");
        }
    }
}