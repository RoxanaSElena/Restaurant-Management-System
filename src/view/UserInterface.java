package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserInterface extends JFrame {

    private JLabel titleLabel;
    private JButton toWaiterButton;
    private JButton toAdminButton;
    private JButton toChefButton;

    public UserInterface() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
        this.getContentPane().setLayout(null);

        // use a bigger font
        Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font hugeFont = new Font("Times New Roman",Font.PLAIN,32);

        titleLabel = new JLabel("User operations !");
        titleLabel.setFont(hugeFont);
        titleLabel.setBounds(300,50,450,50);
        getContentPane().add(titleLabel);


        toWaiterButton = new JButton("To Waiter!");
        toWaiterButton.setFont(biggerFont);
        toWaiterButton.setBounds(100,350,150,50);
        getContentPane().add(toWaiterButton);

        toAdminButton = new JButton("To Administrator!");
        toAdminButton.setFont(biggerFont);
        toAdminButton.setBounds(300,350,200,50);
        getContentPane().add(toAdminButton);

        toChefButton = new JButton("To Chef!");
        toChefButton.setFont(biggerFont);
        toChefButton.setBounds(550,350,150,50);
        getContentPane().add(toChefButton);
    }

    public void addToWaiterButtonActionListener(ActionListener actionListener)
    {
        toWaiterButton.addActionListener(actionListener);
    }

    public void addToAdminButtonActionListener(ActionListener actionListener)
    {
        toAdminButton.addActionListener(actionListener);
    }

    public void addToChefButtonActionListener(ActionListener actionListener)
    {
        toChefButton.addActionListener(actionListener);
    }
}
