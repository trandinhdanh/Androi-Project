package Util;

import java.util.ArrayList;

import ObjectClass.CartItem;
import ObjectClass.Image;
import ObjectClass.Product;

public class UtilCart {
//    public static CartItem cartItem = new CartItem("",new Product("",null,"","",0,"",false)
//    ,0,0,"","");
    public static CartItem cartItem = new CartItem("","","",null,0,0,0,"","");
    public static ArrayList<CartItem> cartItems = new ArrayList<CartItem>();
    public static ArrayList<CartItem> cartItemsPay = new ArrayList<CartItem>();
}
