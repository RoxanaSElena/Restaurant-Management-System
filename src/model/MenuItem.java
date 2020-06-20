package model;

public abstract class MenuItem implements java.io.Serializable {

    protected String name;
    protected int price;

    public abstract int compuntePrice();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return this.name + " "+ this.price;

    }
}
