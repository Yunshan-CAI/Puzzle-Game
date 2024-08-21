package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
    JMenuItem save = new JMenuItem("Save");
    JMenuItem load = new JMenuItem("Load");
    JMenuItem exit = new JMenuItem("Exit");

    JMenuItem scene1 = new JMenuItem("This scene");
    JMenuItem scene2 = new JMenuItem("Scene 2");
    JMenuItem scene3 = new JMenuItem("Scene 3");

    // 默认的图片路径前缀
    private String imagePathPrefix = "/images/taxi_driver/";

    // 标志当前是否是 scene2
    private boolean isScene2 = false; // 标志当前是否是 scene2

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

        //make sure count is 0 when the scene changed
        count = 0;
    }

    //initialize the image
    private void initImage() {

        //clear all the pics
        this.getContentPane().removeAll();

        //if the goal is realized appear the victory pic
        if (victory()) {
            java.net.URL victoryImgURL = getClass().getResource("/images/victory.png");
            JLabel victory = new JLabel(new ImageIcon(victoryImgURL));
            victory.setBounds(540, 375, 100, 100);
            this.getContentPane().add(victory);
        }

        //initialize the step count
        JLabel steps = new JLabel("Steps: " + count);
        steps.setBounds(50, 20, 100, 60);
        steps.setFont(new Font("Arial", Font.BOLD, 18));
        this.getContentPane().add(steps);


        //initialize png number
        for (int t = 0; t < 3; t++) {
            for (int i = 0; i < 3; i++) {
                int number = data[i][t];
                //create a jlabel object
                String imagePath = imagePathPrefix + number + ".png";
                java.net.URL imgURL = getClass().getResource(imagePath);
                if (imgURL != null) {
                    JLabel label = new JLabel(new ImageIcon(imgURL));

                    //set the position of jlabel
                    label.setBounds(250 * t + 220, 250 * i + 50, 250, 250);

                    //set the border
                    label.setBorder(new BevelBorder(BevelBorder.LOWERED));

                    //add the jlabel to the canvas
                    this.getContentPane().add(label);
                } else {
                    System.err.println("Couldn't find file: " + imagePath);
                }
            }
        }

        //refresh the pics
        this.getContentPane().repaint();
    }

    private void initJFrame() {
        //set the canvas size
        this.setSize(1050, 950);

        //set title
        this.setTitle("Puzzle Game: Are you a true cinephile?");

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
        JMenu about = new JMenu("About Game");
        JMenu change = new JMenu("Change Scene");

        //attach the items into the menu
        function.add(replay);
        function.add(save);
        function.add(load);
        function.add(exit);

        // now two scenes, scene 3 is not implemented
        change.add(scene1);
        change.add(scene2);
        change.add(scene3);

        //attach the menu to the menu bar
        menuBar.add(about);
        menuBar.add(function);
        menuBar.add(change);

        //set the menubar for the canvas
        this.setJMenuBar(menuBar);

        //add the action listener for the menu items
        replay.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        load.addActionListener(this);
        scene1.addActionListener(this);
        scene2.addActionListener(this);

        //add the mouse listener for the about menu
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAbout();
            }
        });
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
            String imagePath = imagePathPrefix + "whole.png";
            java.net.URL wholeImgURL = getClass().getResource(imagePath);
            JLabel whole = new JLabel(new ImageIcon(wholeImgURL));
            whole.setBounds(220, 50, 750, 750);
            this.getContentPane().add(whole);

            //refresh the whole page
            this.getContentPane().repaint();
        } else if (code == 86) {
            data = new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
            initImage();
        } else if (code == 81) {
            // Create a JDialog for quote
            JDialog quoteDialog = new JDialog(this, "Remember this?", true);

            // Create label and text for the quote based on the current scene
            String quoteText = isScene2
                    ? "Il est très difficile d'être seul avec quelqu'un."
                    : "I’m God’s Lonely Man."; // Default quote for other scenes

            JLabel quoteLabel = new JLabel("<html><p style='text-align:center;'>"
                    + quoteText
                    + "</p></html>");
            quoteLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Set layout
            quoteDialog.setLayout(new BorderLayout());
            quoteDialog.add(quoteLabel, BorderLayout.CENTER);

            // Set JDialog's size
            // Set the width and height of the dialog
            quoteDialog.setSize(300, 200);
            // Center the dialog relative to the parent window
            quoteDialog.setLocationRelativeTo(this);

            // Display the dialog
            quoteDialog.setVisible(true);
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
            //initialize the data
            initData();
            //initialize the images
            initImage();
        } else if (source == save) {
            saveGame();

        } else if (source == exit) {
            System.exit(0);
        } else if (source == load) {
            loadGame();
        } else if (source == scene2) {
            //switch to scene 2
            switchToScene("/images/l'amour/");

        } else if (source == scene1) {
            // Switch back to scene 1
            switchToScene("/images/taxi_driver/");

        }
    }

    private void switchToScene(String imagePathPrefix) {
        // 设置新的图片目录
        this.imagePathPrefix = imagePathPrefix;
        // 更新场景标志
        this.isScene2 = imagePathPrefix.equals("/images/l'amour/");
        // 重新初始化数据和图片
        initData();
        initImage();
    }


    private static void dialogFormat(JDialog dialog, JLabel label) {

        dialog.getContentPane().add(label);

        // 使用 HTML 标签确保文本居中
        label.setText("<html><div style='text-align: center;'>" + label.getText() + "</div></html>");

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

    //specific method for the about game page
    private void showAbout() {
        // 创建弹出框
        JDialog dialog = new JDialog();
        // 创建 JLabel
        JLabel label = new JLabel("<html>Are you a true cinephile? Prove it by piecing together iconic moments from the greatest films ever made. Each puzzle unveils a legendary scene—will you recognize it before the final piece falls into place? Dive into the magic of cinema and let the challenge begin! " +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br><br>Hints:<br>1. Use keyboard to navigate." +
                "<br>2. Press 'A' to see the whole picture." +
                "<br>3. Press 'Q' to see the quote from the movie." +
                "<br>4. Want victory without effort? Press 'V'.</html>");
        dialogFormat(dialog, label);
    }

    //add a method which saves game state
    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            oos.writeObject(data);
            oos.writeInt(x);
            oos.writeInt(y);
            oos.writeInt(count);
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save the game.");
        }
    }

    //load the game state saved before
    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            data = (int[][]) ois.readObject();
            x = ois.readInt();
            y = ois.readInt();
            count = ois.readInt();
            initImage();
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load the game.");
        }
    }

}





