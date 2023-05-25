package ObjectClass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopgiay.R;

import java.util.ArrayList;
import java.util.List;

import database.DataBase;

public class DataArrayListProduct {
    private ArrayList<Product> data;

    public DataArrayListProduct(DataBase dataBase) {
        this.data = setArrayListProduct(dataBase);
    }

    public ArrayList<Product> getData() {
        return data;
    }

    private ArrayList<Product> setArrayListProduct(DataBase dataBase) {

        ArrayList<Image> listImage = new ArrayList<Image>();

        ArrayList<Product> products = new ArrayList<>();
        Cursor data = dataBase.getData("SELECT * FROM Product");
        if (data != null) {
            if (data.moveToFirst()) {
                do {
                    Cursor dataImg = dataBase.getData("SELECT img FROM LinkImg_Prod WHERE idProd = '"+data.getString(0)+"'");
                    if (dataImg != null) {
                        if (dataImg.moveToFirst()) {
                            do {
                                listImage.add(new Image(dataImg.getBlob(0)));
                            } while (dataImg.moveToNext());
                        }
                    }
                    products.add(new Product(data.getString(0), listImage, data.getString(1), data.getString(2), Integer.parseInt(data.getString(3)), data.getString(4), false));
                    listImage = new ArrayList<Image>();
                } while (data.moveToNext());
            }
        }
//        products.add(new Product("id1",listImage,
//                "1 Giày AF1","Nike",400000,false));
//        products.add(new Product("id2",listImage,
//                "2 Giày X18","Adidas",400000,false));
//        products.add(new Product("id3",listImage,
//                "3 Giay AF1","Nike",400000,false));
//        products.add(new Product("id4",listImage,
//                "4 Giay AF1","Nike",400000,false));
//        products.add(new Product("id5",listImage,
//                "5 Giay AF1","Nike",400000,false));
//        products.add(new Product("id6",listImage,
//                "6 Giay AF1","Adidas",400000,false));
//        products.add(new Product("id7",listImage,
//                "7 Giay AF1","Mizuno",400000,false));
//        products.add(new Product("id8",listImage,
//                "8 Giay AF1","Adidas",400000,true));
//        products.add(new Product("id9",listImage,
//                "9 Giay AF1","Nike",400000,false));
//        products.add(new Product("id10",listImage,
//                "10 Giay AF1","Nike",400000,false));
//        products.add(new Product("id11",listImage,
//                "11 Giay AF1","Nike",400000,false));
//        products.add(new Product("id12",listImage,
//                "12 Giay AF1","Puma",400000,false));
        return products;
    }

}
