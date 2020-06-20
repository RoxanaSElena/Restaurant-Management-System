package controller;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import model.MenuItem;
import model.Order;

public interface IRestaurantProcessing {

    /**
     * @pre item != null
     *  list.size@pre == list.size@post -1
     */
    public void createMenuItem(MenuItem item);

    /**
     * @param item
     *	@pre item!=null
     *  @post list.size = list.size@pre +1
     */
    public void deleteMenuItem(MenuItem item);

    /**
     * @pre item!=null
     * @post item@pre.price != item@post.price
     * @param item
     */
    public void editMenuItem(MenuItem item);





    /**
     *
     * @param order
     * @param menuItem
     *
     * @pre order != null
     * @pre menuItem != null
     */
    public void createOrder(Order order, ArrayList<MenuItem> menuItem);


    /**
     * @pre order!=null
     */
    public int computeOrderPrice(Order order);


    /**
     *
     * @param whatToPrint
     * @pre whatToPrint != null
     */
    public void generateBill(String whatToPrint);
}
