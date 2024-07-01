package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import java.util.Random;


public class GameFrame extends JFrame implements KeyListener, ActionListener {
    //initialize an array
    private int[][] data = new int[3][3];

    //initialize the position of the default block
    int x = 1;
    int y = 1;

    //initialize the goal array
    int[][] win = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};

    //initialize the step count
    int count = 0;

    //create the items in the menu
    JMenuItem replay = new JMenuItem("Replay");
    JMenuItem reenter = new JMenuItem("Reenter");
    JMenuItem exit = new JMenuItem("Exit");
    JMenuItem about = new JMenuItem("About");

    JMenuItem writer1 = new JMenuItem("Writer 1");
    JMenuItem writer2 = new JMenuItem("Writer 2");
    JMenuItem writer3 = new JMenuItem("Writer 3");


    public GameFrame() {

        //randomize the array
        initData();

        //initialize the menu
        initJFrame();

        //create the menu
        createMenu();

        //initialize the image
        initImage();

        //set the canvas visible
        this.setVisible(true);
    }

    private void initData() {
        //1. create a one-dimension array
        int[] tempArr = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        //2. mess up the index of the items in the array
        //switch every number with a random number in the array
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int randomIndex = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[randomIndex];
            tempArr[randomIndex] = temp;
        }

        //3. create a two-dimension array
        int index = 0;

        for (int t = 0; t < data.length; t++) {
            for (int i = 0; i < data[t].length; i++) {
                data[i][t] = tempArr[index];
                index++;
            }
        }
    }

    //initialize the image
    private void initImage() {

        //clear all the pics
        this.getContentPane().removeAll();

        //if the goal is realized appear the victory pic
        if (victory()) {
            JLabel victory = new JLabel(new ImageIcon("Image/victory.png"));
            victory.setBounds(170, 170, 100, 100);
            this.getContentPane().add(victory);
        }

        //initialize the step count
        JLabel steps = new JLabel("Steps: " + count);
        steps.setBounds(20, 0, 70, 50);
        this.getContentPane().add(steps);

        //initialize png number
        for (int t = 0; t < 3; t++) {
            for (int i = 0; i < 3; i++) {
                int number = data[i][t];
                //create a jlabel object
                JLabel label = new JLabel(new ImageIcon("Image/Willa Cather/" + number + ".png"));

                //set the position of jlabel
                label.setBounds(105 * t + 65, 105 * i + 65, 105, 105);

                //set the border
                label.setBorder(new BevelBorder(BevelBorder.LOWERED));

                //add the jlabel to the canvas
                this.getContentPane().add(label);
            }
        }

        //refresh the pics
        this.getContentPane().repaint();
    }

    private void initJFrame() {
        //set the canvas size
        this.setSize(450, 500);

        //set title
        this.setTitle("Puzzle Game: Guess who is she?");

        //set the canvas always on top
        //this.setAlwaysOnTop(true);

        //set the canvas in the middle
        this.setLocationRelativeTo(null);

        //set the close mode
        this.setDefaultCloseOperation(3);

        //cancel the default layout
        this.setLayout(null);

        //add the eventlistener for the change of position of pieces
        this.addKeyListener(this);
    }

    public void createMenu() {
        //create the whole menubar
        JMenuBar menuBar = new JMenuBar();

        //create three menus
        JMenu function = new JMenu("Function");
        JMenu About = new JMenu("About Game");
        JMenu change = new JMenu("ChangeImage");

        //attach the items into the menu
        function.add(replay);
        function.add(reenter);
        function.add(exit);
        function.add(change);

        // not gonna write the whole function this time, could be completed later
        change.add(writer1);
        change.add(writer2);
        change.add(writer3);

        About.add(about);

        //attach the menu to the menu bar
        menuBar.add(function);
        menuBar.add(About);

        //set the menubar for the canvas
        this.setJMenuBar(menuBar);

        //add the action listener for the menu items
        replay.addActionListener(this);
        reenter.addActionListener(this);
        exit.addActionListener(this);
        about.addActionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == 65) {
            //clear the whole page
            this.getContentPane().removeAll();

            //upload the whole pic
            JLabel whole = new JLabel(new ImageIcon("Image/Willa Cather/whole.png"));
            whole.setBounds(70, 40, 315, 360);
            this.getContentPane().add(whole);

            //refresh the whole page
            this.getContentPane().repaint();
        } else if (code == 86) {
            data = new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
            initImage();
        } else if (code == 81) {
            JDialog quote = new JDialog();
            JLabel quot = new JLabel("There are some things you learn best in calm, and some in storm.");
            dialogFormat(quote, quot);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //if the game is won, the player can't change the position
        if (victory()) {
            //end the whole method
            return;
        }
        // get the key pressed
        int code = e.getKeyCode();

        //move left
        if (code == KeyEvent.VK_LEFT) {
            if (y > 0) {
                swap(x, y, x, y - 1);
                y--;
                count++;
            }
        //move right
        } else if (code == KeyEvent.VK_RIGHT) {
            if (y < 2) {
                swap(x, y, x, y + 1);
                y++;
                count++;
            }
        //move up
        } else if (code == KeyEvent.VK_UP) {
            if (x > 0) {
                swap(x, y, x - 1, y);
                x--;
                count++;
            }
        //move down
        } else if (code == KeyEvent.VK_DOWN) {
            if (x < 2) {
                swap(x, y, x + 1, y);
                x++;
                count++;
            }
        }
        initImage();
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int temp = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = temp;
    }


    //to assess if the array align with the goal array
    //if yes, return true else return false
    private boolean victory() {
        for (int t = 0; t < data.length; t++) {
            for (int i = 0; i < data[t].length; i++) {
                if (data[t][i] != win[t][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == replay) {
            //clear the steps
            count = 0;
            //initialize the data
            initData();
            //initialize the images
            initImage();
        } else if (source == reenter) {
            //close the current frame
            this.setVisible(false);
            //open the login in frame
            new LoginFrame();
        } else if (source == exit) {
            System.exit(0);
        } else if (source == about) {
            //create a pop-up
            JDialog dialog = new JDialog();
            //create a jlabel
            JLabel label = new JLabel("<html>Welcome to the puzzle game: Guess who is she? " + "<br><br>Hints:<br>1. press 'A' to see the whole picture.<br>2. press 'Q' to see the quote from the writer.<br>3. Want to give up? Press 'V'.</html>");
            dialogFormat(dialog, label);
        }
    }

    private static void dialogFormat(JDialog dialog, JLabel label) {
        dialog.getContentPane().add(label);
        //set the size of pop up
        dialog.setBounds(10, 10, 450, 500);
        //set the pop up on top
        dialog.setAlwaysOnTop(true);
        //set the pop up in the middle
        dialog.setLocationRelativeTo(null);
        //can't operate on the game unless close the pop up
        dialog.setModal(true);
        //let the pop up visible
        dialog.setVisible(true);
    }
}





