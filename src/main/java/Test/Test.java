package Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        //set the canvas size
        frame.setSize(603, 608);

        //set title
        frame.setTitle("Puzzle Game: Guess who is she?");

        //set the canvas always on top
        //this.setAlwaysOnTop(true);

        //set the canvas in the middle
        frame.setLocationRelativeTo(null);

        //set the close mode
        frame.setDefaultCloseOperation(3);

        //cancel the default layout
        frame.setLayout(null);

        //create a button object
        JButton button = new JButton("press");
        //set the position and size
        button.setBounds(0, 0, 100, 50);
        //what the event is
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button pressed");
            }
        });

        //add the button to the canvas
        frame.getContentPane().add(button);

        //set the canvas visible
        frame.setVisible(true);
    }
}
