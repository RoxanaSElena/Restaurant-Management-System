package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.spec.DESedeKeySpec;
//import javax.jws.soap.SOAPBinding.Use;

import org.w3c.dom.UserDataHandler;

import model.Restaurant;
import view.AdministratorGUI;
import view.ChefGUI;
import view.CompositeItemGUI;
import view.UserInterface;
import view.WaiterGUI;

public class MainController {

    private WaiterGUI waiterGUI;
    private AdministratorGUI adminGUI;
    private ChefGUI chefGUI;
    private UserInterface userInterface;
    private Restaurant restaurant;
    private CompositeItemGUI compositeItemGUI;

    public void start()
    {
        //RestaurantSerializator.serialize(restaurant);
        //restaurant = new Restaurant();
        restaurant=RestaurantSerializator.DEserialize();
        userInterface = new UserInterface();

        waiterGUI = new WaiterGUI(userInterface,restaurant);
        chefGUI = new ChefGUI(userInterface,restaurant);

        compositeItemGUI = new CompositeItemGUI(restaurant);
        adminGUI = new AdministratorGUI(userInterface,restaurant,compositeItemGUI);


        userInterface.setVisible(true);

        initializeUserInterfaceButtons();
    }

    public void initializeUserInterfaceButtons()
    {
        userInterface.addToWaiterButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.setVisible(false);
                waiterGUI.setVisible(true);
            }
        });

        userInterface.addToAdminButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.setVisible(false);
                adminGUI.setVisible(true);
            }
        });

        userInterface.addToChefButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userInterface.setVisible(false);
                chefGUI.setVisible(true);
            }
        });
    }
}
