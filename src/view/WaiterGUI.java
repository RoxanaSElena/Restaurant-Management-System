package view;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

import controller.IRestaurantProcessing;
import model.IdGenerator;
import model.MenuItem;
import model.MyDate;
import model.Order;
import model.Restaurant;

public class WaiterGUI extends JFrame implements IRestaurantProcessing {

    private JLabel titleLabel;

    private JButton backButton;
    private JButton createOrderButton;
    private JButton addMenuItemButton;
    private JButton fillMenuButton;

    private JButton showOrdersButton;
    private JButton generateBillButton;

    ArrayList<MenuItem> orderedItems = new ArrayList<MenuItem>();
    ArrayList<Order> orderList = new ArrayList<Order>();

    private JLabel orderIDLabel;
    private JTextField orderIDField;
    private JLabel orderTableLabel;
    private JTextField orderTableField;
    private JLabel orderDateLabel;
    private JTextField orderDateField;

    private JLabel chosenItemsLabel;
    private JTextArea chosenItems;

    private JComboBox<MenuItem> menu;

    private Restaurant restaurant;

    public WaiterGUI(UserInterface userInterface, Restaurant restaurant) {
        this.restaurant = restaurant;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 1200, 700);
        this.getContentPane().setLayout(null);

        // use a bigger font
        Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font hugeFont = new Font("Times New Roman", Font.PLAIN, 32);

        titleLabel = new JLabel("Waiter operations !");
        titleLabel.setFont(hugeFont);
        titleLabel.setBounds(300, 50, 450, 50);
        getContentPane().add(titleLabel);

        orderIDLabel = new JLabel("OrderID:");
        orderIDLabel.setFont(biggerFont);
        orderIDLabel.setBounds(50, 110, 80, 30);
        getContentPane().add(orderIDLabel);

        orderIDField = new JTextField();
        orderIDField.setBounds(120, 110, 50, 30);
        getContentPane().add(orderIDField);

        orderTableLabel = new JLabel("Table:");
        orderTableLabel.setFont(biggerFont);
        orderTableLabel.setBounds(50, 150, 50, 30);
        getContentPane().add(orderTableLabel);

        orderTableField = new JTextField();
        orderTableField.setBounds(100, 150, 50, 30);
        getContentPane().add(orderTableField);

        orderDateLabel = new JLabel("Date:");
        orderDateLabel.setFont(biggerFont);
        orderDateLabel.setBounds(50, 200, 50, 30);
        getContentPane().add(orderDateLabel);

        orderDateField = new JTextField();
        orderDateField.setBounds(100, 200, 100, 30);
        getContentPane().add(orderDateField);

        menu = new JComboBox<MenuItem>();
        menu.setBounds(50, 250, 150, 50);
        getContentPane().add(menu);

        chosenItemsLabel = new JLabel("Chosen items");
        chosenItemsLabel.setFont(biggerFont);
        chosenItemsLabel.setBounds(230, 130, 100, 20);
        getContentPane().add(chosenItemsLabel);

        chosenItems = new JTextArea();
        chosenItems.setBounds(230, 150, 100, 200);
        getContentPane().add(chosenItems);

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

        fillMenuButton = new JButton("Show menu!");
        fillMenuButton.setBounds(20, 320, 110, 30);
        getContentPane().add(fillMenuButton);
        fillMenuButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fillMenu();
            }
        });

        addMenuItemButton = new JButton("Add");
        addMenuItemButton.setBounds(140, 320, 80, 30);
        getContentPane().add(addMenuItemButton);
        addMenuItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                MenuItem myItem = (MenuItem) menu.getSelectedItem();
                chosenItems.append(myItem.toString());
                chosenItems.append("\n");

                //orderedItems = new ArrayList<MenuItem>();

                orderedItems.add(myItem);
                System.out.println(myItem.getName());

            }
        });

        createOrderButton = new JButton("Create Order");
        createOrderButton.setFont(biggerFont);
        createOrderButton.setBounds(50, 550, 150, 50);
        getContentPane().add(createOrderButton);
        createOrderButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<MenuItem> copyList =null;

                try {
                    int ordID = IdGenerator.getNextId();
                    int table = Integer.parseInt(orderTableField.getText());
                    String date = orderDateField.getText();
                    if(date.matches("[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]")==false)
                    {
                        System.out.println("Data field introduced wrong");//throw new Exception();
                        return;
                    }
                    System.out.println(ordID + " " + table + " " + date);


                    StringTokenizer st = new StringTokenizer(date, "-");
                    int day = Integer.parseInt(st.nextToken());
                    int month = Integer.parseInt(st.nextToken());
                    int year = Integer.parseInt(st.nextToken());

                    MyDate myDate = new MyDate(day, month, year);
                    Order myOrder = new Order(ordID, myDate, table);


                    copyList = new ArrayList<MenuItem>();

                    Iterator<MenuItem> it = orderedItems.iterator();

                    int i=0;
                    while (it.hasNext()) {
                        i++;
                        copyList.add(it.next());
                    }

                    if(i>0)
                    {
                        createOrder(myOrder, copyList);

                        orderedItems.removeAll(orderedItems);
                        chosenItems.setText("");



                        orderList.add(myOrder);
                        restaurant.addOrderToList(myOrder);


                    }
                    else
                        JOptionPane.showMessageDialog(null, "Nothing to deliver to chef !");

                }catch (Exception e1) {
                    System.out.println(e1);
                    JOptionPane.showMessageDialog(null, "Bad input!");
                }
            }
        });

        showOrdersButton = new JButton("Show Orders");
        showOrdersButton.setFont(biggerFont);
        showOrdersButton.setBounds(550, 550, 150, 50);
        getContentPane().add(showOrdersButton);
        showOrdersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Order, ArrayList<MenuItem>> myMap = restaurant.getOrderMap();

                String columns[] = { "OrderID", "Date", "Table", "OrderedItems" };

                DefaultTableModel myModel = new DefaultTableModel();
                myModel.setColumnIdentifiers(columns);

                Object[] obj = new Object[4];
                Iterator<Order> it = orderList.iterator();
                while (it.hasNext()) {
                    Order curentOrder = it.next();
                    obj[0] = curentOrder.getOrderID();
                    String date = curentOrder.getOrderDate().getDay() + "-" + curentOrder.getOrderDate().getMonth()
                            + "-" + curentOrder.getOrderDate().getYear();
                    obj[1] = date;
                    obj[2] = curentOrder.getTable();
                    ArrayList<MenuItem> listM = myMap.get(curentOrder);
                    StringBuilder sb = new StringBuilder();
                    Iterator<MenuItem> itt = listM.iterator();
                    while (itt.hasNext()) {

                        sb.append(itt.next().toString());
                        if (itt.hasNext())
                            sb.append(" , ");
                    }

                    obj[3] = sb.toString();
                    myModel.addRow(obj);
                }

                JTable myTable = new JTable(myModel);
                JScrollPane myScrollPane = new JScrollPane();
                myScrollPane.setBounds(350, 100, 800, 400);
                myScrollPane.setViewportView(myTable);
                myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                getContentPane().add(myScrollPane);

            }
        });

        generateBillButton = new JButton("Generate Bill !");
        generateBillButton.setBounds(300, 550, 200, 50);
        getContentPane().add(generateBillButton);
        generateBillButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int ordID = Integer.parseInt(orderIDField.getText());
                Order myOrder = findOrderByID(ordID);
                if (myOrder == null)
                    JOptionPane.showMessageDialog(null, "Order not found!");
                else {

                    StringBuilder sBuilder = new StringBuilder();

                    sBuilder.append("Order ID : " + ordID + "\n");
                    sBuilder.append("Date : " + myOrder.getOrderDate().getDay() + "-"
                            + myOrder.getOrderDate().getMonth() + "-" + myOrder.getOrderDate().getYear() + "\n");
                    sBuilder.append("Table : " + myOrder.getTable() + "\n");
                    sBuilder.append("Ordered Items : " + "\n");

                    Map<Order, ArrayList<MenuItem>> myMap = restaurant.getOrderMap();
                    ArrayList<MenuItem> list = myMap.get(myOrder);
                    Iterator<MenuItem> it = list.iterator();
                    while (it.hasNext()) {
                        sBuilder.append(it.next().toString());
                        sBuilder.append("\n");
                    }
                    sBuilder.append("Total price : " + computeOrderPrice(myOrder));

                    generateBill(sBuilder.toString());

                    JOptionPane.showMessageDialog(null, "Bill generated successfully !");

                }
            }
        });
    }

    @Override
    public void createMenuItem(MenuItem item) {
        System.out.println("Not qualified to create menu items");

    }

    @Override
    public void deleteMenuItem(MenuItem item) {
        System.out.println("Not qualified to delete menu items");

    }

    @Override
    public void editMenuItem(MenuItem item) {
        System.out.println("Not qualified to edit menu items");

    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItem> menuItem) {

        restaurant.createOrder(order, menuItem);

    }


    @Override
    public int computeOrderPrice(Order order) {

        return restaurant.computeOrderPrice(order);
    }

    @Override
    public void generateBill(String whatToPrint) {
        restaurant.generateBill(whatToPrint);

    }

    public void fillMenu() {
        menu.removeAllItems();
        ArrayList<MenuItem> list = restaurant.getMenu();
        Iterator<MenuItem> it = list.iterator();


        while (it.hasNext()) {
            MenuItem curentItem = it.next();
            menu.addItem(curentItem);
        }
    }

    public Order findOrderByID(int id) {
        Order myOrder = null;
        Iterator<Order> it = orderList.iterator();
        while (it.hasNext()) {
            Order curOrder = it.next();
            if (curOrder.getOrderID() == id)
                myOrder = curOrder;
        }
        return myOrder;
    }
}
