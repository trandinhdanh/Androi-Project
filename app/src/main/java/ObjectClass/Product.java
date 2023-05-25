package ObjectClass;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private String productID;
    private ArrayList<Image> reSourceListImage;
    private String nameProduct;
    private String brand;
    private int price;
    private String describe;
    private boolean favorite;

    public Product(String productID, ArrayList<Image> reSourceListImage, String nameProduct, String brand, int price, String describe, boolean favorite) {
        this.productID = productID;
        this.reSourceListImage = reSourceListImage;
        this.nameProduct = nameProduct;
        this.brand = brand;
        this.price = price;
        this.describe = describe;
        this.favorite = favorite;
    }


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public ArrayList<Image> getReSourceListImage() {
        return reSourceListImage;
    }

    public void setReSourceListImage(ArrayList<Image> reSourceListImage) {
        this.reSourceListImage = reSourceListImage;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (price != product.price) return false;
        if (favorite != product.favorite) return false;
        if (!productID.equals(product.productID)) return false;
        if (!reSourceListImage.equals(product.reSourceListImage)) return false;
        if (!nameProduct.equals(product.nameProduct)) return false;
        if (!brand.equals(product.brand)) return false;
        return describe.equals(product.describe);
    }

    @Override
    public int hashCode() {
        int result = productID.hashCode();
        result = 31 * result + reSourceListImage.hashCode();
        result = 31 * result + nameProduct.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + price;
        result = 31 * result + describe.hashCode();
        result = 31 * result + (favorite ? 1 : 0);
        return result;
    }
}
