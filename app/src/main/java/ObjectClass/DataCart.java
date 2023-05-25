package ObjectClass;

import com.example.shopgiay.R;

import java.util.ArrayList;

public class DataCart {
    private ArrayList<CartItem> cartItems;

    public DataCart() {
        this.cartItems = getArrayListCart();
    }


    public ArrayList<CartItem> getDataConfirmation(){
        ArrayList<CartItem> result = new ArrayList<CartItem>();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getStatus().equalsIgnoreCase("Chờ xác nhận")){
                result.add(cartItem);
            }
        }
        return result;
    }

    public ArrayList<CartItem> getDataDelivering(){
        ArrayList<CartItem> result = new ArrayList<CartItem>();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getStatus().equalsIgnoreCase("Đang giao")){
                result.add(cartItem);
            }
        }
        return result;
    }

    public ArrayList<CartItem> getDataHistory(){
        ArrayList<CartItem> result = new ArrayList<CartItem>();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getStatus().equalsIgnoreCase("Đã mua")){
                result.add(cartItem);
            }
        }
        return result;
    }

    private ArrayList<CartItem> getArrayListCart(){
        ArrayList<Image> listImage =new ArrayList<Image>();
//        listImage.add(new Image(R.drawable.img_background));
//        listImage.add(new Image(R.drawable.img_cua_hang));
//        listImage.add(new Image(R.drawable.img_avata));

        ArrayList<CartItem> cartItems = new ArrayList<>();
//        cartItems.add(new CartItem("c01",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Chờ xác nhận"));
//        cartItems.add(new CartItem("c02",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Chờ xác nhận"));
//        cartItems.add(new CartItem("c03",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Chờ xác nhận"));
//        cartItems.add(new CartItem("c04",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Đã mua"));
//        cartItems.add(new CartItem("c05",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Đã mua"));
//        cartItems.add(new CartItem("c06",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Đã mua"));
//        cartItems.add(new CartItem("c07",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Chờ xác nhận"));
//        cartItems.add(new CartItem("c08",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Đang giao"));
//        cartItems.add(new CartItem("c09",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Chờ xác nhận"));
//        cartItems.add(new CartItem("c10",new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,"",false),
//                3,41,"Pink","Đang giao"));
        return cartItems;
    }
}
