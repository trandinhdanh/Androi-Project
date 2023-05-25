package ObjectClass;

import java.util.ArrayList;

import Util.UtilFavourite;

public class ListProduct {
    private ArrayList<Product>  products;

    public ListProduct(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts(String type){
        ArrayList<Product> result = new ArrayList<Product>();
        for (Product p:this.products) {
            if (type.equalsIgnoreCase(p.getBrand())) {
                result.add(p);
            }
        }
        return result;
    }

    public ArrayList<Product> findByName(String key){
        ArrayList<Product> result = new ArrayList<Product>();
        for (Product p : this.products) {
            if (p.getNameProduct().toLowerCase().contains(key.toLowerCase())||
                    p.getBrand().toLowerCase().contains(key.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

//    public void removeSearch(){
//        findByName()
//    }

    public ArrayList<Product>  getProductsOther(String type){
        ArrayList<Product> result = new ArrayList<Product>();
        for (Product p:this.products){
            if (!"Nike".equalsIgnoreCase(p.getBrand())&&!"Adidas".equalsIgnoreCase(p.getBrand())&&!"Mizuno".equalsIgnoreCase(p.getBrand())){
                result.add(p);
            }
        }
        return result;
    }
// cắm đt dô cái m alo
    public ArrayList<Product> getListFavorite(){
        ArrayList<Product> result = new ArrayList<Product>();

        if (UtilFavourite.listUtil.size()==0){
            for (Product p : products) {
                if (p.getFavorite() == true) {
                    result.add(p);
                }
            }
        }else {
            for (Product p11 : UtilFavourite.listUtil) {
                if (!products.contains(p11)) {
                    products.add(p11);
                }
            }
            for (Product p : products) {
                if (p.getFavorite() == true) {
                    result.add(p);
                }
            }
        }


//        if (UtilFavourite.listUtil.size()==0){
//        }else{
//            for (Product p1: UtilFavourite.listUtil) {
//                for (Product p2 : products) {
//                    if (p1.getProductID().equalsIgnoreCase(p2.getProductID())){
//                    }else{
//                        if (result.size()==0){
//                            result.add(p1);
//                            break;
//                        }else{
//                            for (Product p3 : result) {
//                                if (p1.getProductID().equalsIgnoreCase(p3.getProductID())){
//                                }else {
//                                    result.add(p1);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return result;
    }
}
