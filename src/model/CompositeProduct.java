package model;

import java.util.ArrayList;
import java.util.Iterator;

public class CompositeProduct extends MenuItem {

    private ArrayList<MenuItem> compProd ;

    public CompositeProduct(String name,ArrayList<MenuItem> compProd) {
        this.name=name;
        this.compProd=compProd;
    }

    @Override
    public int compuntePrice() {
        int price=0;
        Iterator<MenuItem> myIterator = compProd.iterator();
        while(myIterator.hasNext())
        {
            MenuItem curentItem = myIterator.next();
            price+=curentItem.getPrice();
        }
        return price;
    }

}
