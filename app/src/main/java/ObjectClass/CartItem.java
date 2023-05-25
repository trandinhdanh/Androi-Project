package ObjectClass;

import java.io.Serializable;
import java.util.ArrayList;

public class CartItem implements Serializable {
    private String idItem;
    private String name;
    private String brand;
    private ArrayList<Image> images;
    private int price;
    private int quantity;
    private int size;
    private String color;
    private String status;

    public CartItem(String idItem, String name, String brand, ArrayList<Image> images,int price, int quantity, int size, String color, String status) {
        this.idItem = idItem;
        this.name = name;
        this.brand = brand;
        this.images = images;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.status = status;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
