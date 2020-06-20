package model;


import java.io.BufferedWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

/*import javax.annotation.PostConstruct;*/

import controller.IRestaurantProcessing;

public class Restaurant extends Observable implements IRestaurantProcessing,java.io.Serializable {

    private ArrayList<MenuItem> myMenu ;
    private  ArrayList<MenuItem> compositeItem = new ArrayList<MenuItem>();
    private  String compositeName;


    private  Map<Order, ArrayList<MenuItem>> orderMenuMap ;
    private  ArrayList<Order> orderList;

    public Restaurant() {
        myMenu = new ArrayList<MenuItem>();
        orderMenuMap = new HashMap<Order, ArrayList<MenuItem>>();
        orderList= new ArrayList<Order>();

    }

    public ArrayList<MenuItem> getMenu() {
        return myMenu;
    }

    public void setMenu(ArrayList<MenuItem> myMenu) {
        this.myMenu = myMenu;
    }

    public void setCompositeItem(ArrayList<MenuItem> comp) {
        this.compositeItem = comp;
    }

    public void setCompositeName(String name) {
        this.compositeName = name;
    }

    public ArrayList<MenuItem> getCompItem() {
        return this.compositeItem;
    }

    public String getCompositeName() {
        return this.compositeName;
    }

    public void addOrderToList(Order order)
    {
        orderList.add(order);
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("NEW order created : "+"\n\n");
        sBuilder.append("Order ID : " + order.getOrderID() + "\n");
        sBuilder.append("Date : " + order.getOrderDate().getDay() + "-"
                + order.getOrderDate().getMonth() + "-" + order.getOrderDate().getYear() + "\n");
        sBuilder.append("Table : " + order.getTable() + "\n");
        sBuilder.append("Ordered Items : " + "\n");

        Map<Order, ArrayList<MenuItem>> myMap = getOrderMap();
        ArrayList<MenuItem> list = myMap.get(order);
        Iterator<MenuItem> it = list.iterator();
        while (it.hasNext()) {
            sBuilder.append(it.next().toString());
            sBuilder.append("\n");
        }


        setChanged();
        notifyObservers(sBuilder.toString());

    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItem> menuItem) {

        assert order != null;
        assert menuItem != null;

        Order oldOrder = order;

        //System.out.println(order.getOrderID() + " " + menuItem);

        orderMenuMap.put(order, menuItem);


        assert oldOrder.equals(order);
    }


    /// done
    @Override
    public int computeOrderPrice(Order order) {

        assert order != null ;

        int price=0;
        if(this.orderList.contains(order)==true)
        {
            ArrayList<MenuItem> orderedItems = orderMenuMap.get(order);
            Iterator<MenuItem> it = orderedItems.iterator();
            while(it.hasNext())
            {
                price+=it.next().getPrice();
            }
        }
        return price;
    }

    @Override
    public void generateBill(String whatToPrint) {

        assert whatToPrint != null;

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bill.txt")));
            bw.write(whatToPrint);
            bw.close();
        } catch ( IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void createMenuItem(MenuItem item) {
        // System.out.println("I am in restaurant!");

        assert item != null;
        int preSize = myMenu.size();

        myMenu.add(item);

        int postSize = myMenu.size();

        assert preSize+1==postSize;

    }

    @Override
    public void deleteMenuItem(MenuItem item) {

        assert item != null;
        int preSize = myMenu.size();

        myMenu.remove(item);

        int postSize = myMenu.size();

        assert preSize == postSize+1;

    }

    @Override
    public void editMenuItem(MenuItem item) {
        assert item != null;
        int postPrice = item.getPrice();
        int prePrice=0;

        String itemName = item.getName();
        Iterator<MenuItem> myIterator = myMenu.iterator();
        while (myIterator.hasNext()) {
            MenuItem curentItem = myIterator.next();
            if (curentItem.getName() == itemName) {

                curentItem.setPrice(item.getPrice());

                prePrice = curentItem.getPrice();
            }
        }

        myIterator = myMenu.iterator();
        while (myIterator.hasNext()) {
            MenuItem curentItem = myIterator.next();
            curentItem.setPrice(curentItem.compuntePrice());
        }

        assert prePrice == postPrice;
    }

    public Map<Order, ArrayList<MenuItem>> getOrderMap()
    {
        return this.orderMenuMap;
    }
}
