package view;

import java.awt.Font;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import controller.RestaurantSerializator;
import model.CompositeProduct;
import model.MenuItem;
import model.Restaurant;

public class CompositeItemGUI extends JFrame {

    private Restaurant restaurant;

    private JLabel titleLabel;
    private JComboBox<MenuItem> menuBox;

    private ArrayList<MenuItem> compositeItem;

    private JButton addItemButton;
    private JButton finishItemButton;

    private JTextArea addedItems;
    private JLabel addedLabel;



    public CompositeItemGUI(Restaurant restaurant)
    {

        compositeItem = new ArrayList<MenuItem>();
        this.restaurant=restaurant;

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
        this.getContentPane().setLayout(null);

        // use a bigger font
        Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font hugeFont = new Font("Times New Roman", Font.PLAIN, 32);

        titleLabel = new JLabel("Composite Item operations !");
        titleLabel.setFont(hugeFont);
        titleLabel.setBounds(300, 50, 450, 50);
        getContentPane().add(titleLabel);

        menuBox= new JComboBox<MenuItem>();
        menuBox.setBounds(100,150,250,50);
        getContentPane().add(menuBox);

        addItemButton = new JButton("Add Item");
        addItemButton.setFont(biggerFont);
        addItemButton.setBounds(50, 550, 125, 50);
        getContentPane().add(addItemButton);
        addItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                MenuItem chosenItem = (MenuItem) menuBox.getSelectedItem();
                compositeItem.add(chosenItem);
                addedItems.append(chosenItem.toString());
                addedItems.append("\n");
            }
        });

        finishItemButton = new JButton("Finish Item");
        finishItemButton.setFont(biggerFont);
        finishItemButton.setBounds(250, 550, 125, 50);
        getContentPane().add(finishItemButton);
        finishItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                restaurant.setCompositeItem(finishItem());
                ArrayList<MenuItem> comp = restaurant.getCompItem();
                String iname = restaurant.getCompositeName();

                CompositeProduct cp = new CompositeProduct(iname, comp);
                int finalPrice=cp.compuntePrice();
                cp.setPrice(finalPrice);

                restaurant.createMenuItem(cp);
                RestaurantSerializator.serialize(restaurant);

                addedItems.setText("");
                JOptionPane.showMessageDialog(null, "Composite item created successfully!");
            }
        });

        addedLabel = new JLabel("Added Items");
        addedLabel.setBounds(400,100,100,50);
        getContentPane().add(addedLabel);

        addedItems = new JTextArea();
        addedItems.setBounds(400,150,400,300);
        addedItems.setEditable(false);
        getContentPane().add(addedItems);


    }

    public void fillBox()
    {
        ArrayList<MenuItem> list = restaurant.getMenu();
        Iterator<MenuItem> it = list.iterator();

        //MenuItem[] arrayItems = new MenuItem[list.size()];
        //int i=0;
        while(it.hasNext())
        {
            MenuItem curentItem = it.next();
            menuBox.addItem(curentItem);

            System.out.println(curentItem.getName());
        }
    }

    public ArrayList<MenuItem> finishItem()
    {
        return compositeItem;
    }
}
