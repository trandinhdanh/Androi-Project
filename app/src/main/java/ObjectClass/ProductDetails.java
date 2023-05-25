package ObjectClass;

public class ProductDetails {
    //TABLE ProductDetails(idProdDetails , idProd , size , color, quantity ));
    private String idProdDetails;
    private String idProd;
    private int size;
    private String color;
    private int quality;

    public ProductDetails(String idProdDetails, String idProd, int size, String color, int quality) {
        this.idProdDetails = idProdDetails;
        this.idProd = idProd;
        this.size = size;
        this.color = color;
        this.quality = quality;
    }


    public String getIdProdDetails() {
        return idProdDetails;
    }

    public void setIdProdDetails(String idProdDetails) {
        this.idProdDetails = idProdDetails;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
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

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
