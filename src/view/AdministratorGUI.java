package view;

import java.awt.Font;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.IRestaurantProcessing;
import controller.RestaurantSerializator;
import model.BaseProduct;
import model.MenuItem;
import model.Order;
import model.Restaurant;

public class AdministratorGUI extends JFrame implements IRestaurantProcessing {

    private JLabel titleLabel;
    private Restaurant restaurant;
    private CompositeItemGUI compositeItemGUI;

    private JButton backButton;
    private JButton createItemButton;
    private JButton deleteItemButton;
    private JButton editItemButton;
    private JButton showItemsButton;

    private JLabel itemNameLabel;
    private JTextField itemNameField;

    private JLabel itemPriceLabel;
    private JTextField itemPriceField;

    public AdministratorGUI(UserInterface userInterface, Restaurant restaurant,CompositeItemGUI compositeItemGUI) {

        this.restaurant = restaurant;
        this.compositeItemGUI = compositeItemGUI;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
        this.getContentPane().setLayout(null);

        // use a bigger font
        Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font hugeFont = new Font("Times New Roman", Font.PLAIN, 32);

        titleLabel = new JLabel("Administrator operations !");
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

        createItemButton = new JButton("Create item");
        createItemButton.setFont(biggerFont);
        createItemButton.setBounds(50, 550, 125, 50);
        getContentPane().add(createItemButton);
        createItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = getNameField();
                String itemPrice = getPriceField();
                if (itemPrice.compareTo("") == 0) {
                    System.out.println("Composite !");
                    compositeItemGUI.setVisible(true);
                    compositeItemGUI.fillBox();
                    restaurant.setCompositeName(itemName);

                } else {

                    int itemP = 0;
                    try {
                        itemP = Integer.parseInt(itemPrice);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Invalid price !");
                    }

                    BaseProduct newBaseProduct = new BaseProduct(itemName, itemP);
                    createMenuItem(newBaseProduct);
                    RestaurantSerializator.serialize(restaurant);
                    JOptionPane.showMessageDialog(null, "Item added successfully !");
                }

            }
        });

        deleteItemButton = new JButton("Delete item");
        deleteItemButton.setFont(biggerFont);
        deleteItemButton.setBounds(200, 550, 125, 50);
        getContentPane().add(deleteItemButton);
        deleteItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String iname = getNameField();
                restaurant.deleteMenuItem(findByName(iname));
                RestaurantSerializator.serialize(restaurant);

                JOptionPane.showMessageDialog(null, "Successfully deleted the item !");

            }
        });

        editItemButton = new JButton("Edit item");
        editItemButton.setFont(biggerFont);
        editItemButton.setBounds(350, 550, 125, 50);
        getContentPane().add(editItemButton);
        editItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String iname = getNameField();
                String iprice = getPriceField();
                int price=0;
                try {
                    price = Integer.parseInt(iprice);

                    BaseProduct newProduct = new BaseProduct(iname, price);
                    MenuItem oldProduct = findByName(iname);
                    if(oldProduct instanceof BaseProduct)
                    {
                        oldProduct.setPrice(price);
                        JOptionPane.showMessageDialog(null, "Item edit finished successfully!");
                        editMenuItem(oldProduct);
                        RestaurantSerializator.serialize(restaurant);

                    }
                    else JOptionPane.showMessageDialog(null, "Can't edit composite products , need more skill !");

                }catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Invalid price !");
                }

            }
        });

        showItemsButton = new JButton("Show items");
        showItemsButton.setFont(biggerFont);
        showItemsButton.setBounds(550, 550, 125, 50);
        getContentPane().add(showItemsButton);
        showItemsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String columns[] = { "Item Name", "Item Price" };
                ArrayList<MenuItem> list = restaurant.getMenu();
                DefaultTableModel myModel = new DefaultTableModel();
                myModel.setColumnIdentifiers(columns);
                Object[] obj = new Object[2];
                Iterator<MenuItem> it = list.iterator();
                while (it.hasNext()) {
                    MenuItem curentItem = it.next();
                    System.out.println(curentItem.getName() + " " + curentItem.getPrice());

                    obj[0] = curentItem.getName();
                    obj[1] = curentItem.getPrice();
                    myModel.addRow(obj);
                }

                JTable myTable = new JTable(myModel);
                JScrollPane myScrollPane = new JScrollPane();
                myScrollPane.setBounds(250, 100, 600, 400);
                myScrollPane.setViewportView(myTable);
                getContentPane().add(myScrollPane);

            }
        });

        itemNameLabel = new JLabel("Name");
        itemNameLabel.setFont(biggerFont);
        itemNameLabel.setBounds(50, 150, 50, 40);
        getContentPane().add(itemNameLabel);

        itemNameField = new JTextField();
        itemNameField.setFont(biggerFont);
        itemNameField.setBounds(100, 150, 100, 30);
        getContentPane().add(itemNameField);

        itemPriceLabel = new JLabel("Price");
        itemPriceLabel.setFont(biggerFont);
        itemPriceLabel.setBounds(50, 200, 50, 40);
        getContentPane().add(itemPriceLabel);

        itemPriceField = new JTextField();
        itemPriceField.setFont(biggerFont);
        itemPriceField.setBounds(100, 200, 100, 30);
        getContentPane().add(itemPriceField);

    }

    public String getPriceField() {
        return this.itemPriceField.getText();
    }

    public String getNameField() {
        return this.itemNameField.getText();
    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItem> menuItem) {
        System.out.println("Not qualified to create orders !");

    }

    @Override
    public int computeOrderPrice(Order order) {
        System.out.println("Not qualified to compute order prices !");
        return 0;

    }

    @Override
    public void generateBill(String whatToPrint) {
        System.out.println("Not qualified to generate bills !");

    }

    @Override
    public void createMenuItem(MenuItem item) {
        //System.out.println("I am here " + item.getName() + " " + item.getPrice());
        restaurant.createMenuItem(item);

    }

    @Override
    public void deleteMenuItem(MenuItem item) {
        restaurant.deleteMenuItem(item);

    }

    @Override
    public void editMenuItem(MenuItem item) {
        restaurant.editMenuItem(item);

    }

    public MenuItem findByName(String name)
    {
        ArrayList<MenuItem> items = restaurant.getMenu();
        Iterator<MenuItem> it = items.iterator();
        while(it.hasNext())
        {
            MenuItem curentItem = it.next();
            if(curentItem.getName().compareTo(name)==0) return curentItem;
        }
        return null;
    }
}
