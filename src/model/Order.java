package model;

import java.util.Date;

public class Order {

    private int OrderID;
    private MyDate OrderDate;
    private int table;

    public Order(int OrderID, MyDate OrderDate, int table)
    {
        this.OrderID=OrderID;
        this.OrderDate=OrderDate;
        this.table=table;
    }

    public int getOrderID() {
        return OrderID;
    }

    public MyDate getOrderDate() {
        return OrderDate;
    }

    public int getTable() {
        return table;
    }

    @Override
    public int hashCode()
    {
        int hashcode = 11;
        hashcode += hashcode*7 + 31*OrderID+3*OrderDate.getDay()+5*table;
        return hashcode;

    }

    @Override
    public boolean equals(Object obj)
    {
        if(this==obj) return true;

        if(obj==null) return false;

        if(this.getClass() != obj.getClass()) return false;

        Order other = (Order) obj;
        if(this.OrderID!=other.OrderID) return false;
        if(this.OrderDate.getDay() != other.OrderDate.getDay()) return false;
        if(this.table != other.table) return false;
        return true;

    }

}
