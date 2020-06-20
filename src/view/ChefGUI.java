package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Restaurant;

public class ChefGUI extends JFrame implements Observer {

    private JLabel titleLabel;

    private JButton backButton;
    private Restaurant restaurant;

    public ChefGUI(UserInterface userInterface, Restaurant restaurant) {

        this.restaurant = restaurant;

        restaurant.addObserver(this);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
        this.getContentPane().setLayout(null);

        // use a bigger font
        Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font hugeFont = new Font("Times New Roman", Font.PLAIN, 32);

        titleLabel = new JLabel("Chef operations !");
        titleLabel.setFont(hugeFont);
        titleLabel.setBounds(300, 50, 450, 50);
        getContentPane().add(titleLabel);

        backButton = new JButton("Back");
        backButton.setFont(biggerFont);
        backButton.setBounds(750, 550, 100, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                userInterface.setVisible(true);

            }
        });
        getContentPane().add(backButton);
    }

    @Override
    public void update(Observable o, Object arg) {

        this.setVisible(true);
        System.out.println(arg.toString());
        int x = JOptionPane.showConfirmDialog(null, arg, "Notification !", 2);
        if (x == 0) {
            System.out.println("Chef will cook!");
            this.setVisible(false);
        } else {
            System.out.println("Chef is busy, but will still cook later!");
            this.setVisible(false);
        }
        // System.out.println(x);
        // JOptionPane.showMessageDialog(null, arg);

    }
}