package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import Adapter.ProductAdapterDelivering;
import MyInterface.IOnClickItemProductCart;
import ObjectClass.CartItem;
import ObjectClass.DataCart;
import Util.UtilCart;
import Util.UtilInfomationProduct;

public class DeliveringActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private ImageButton buttonBack;
    private ProductAdapterDelivering adapterDelivering;
    private DataCart dataCart;
    private GridLayoutManager gridLayoutManager;
    private TextView tVStatusCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivering);
        dataCart = new DataCart();
        anhXa();
        if (dataCart.getDataConfirmation().size()==0){
            tVStatusCart.setVisibility(View.VISIBLE);
        }else {
            tVStatusCart.setVisibility(View.GONE);
            adapterDelivering = new ProductAdapterDelivering(this, dataCart.getDataDelivering(), new IOnClickItemProductCart() {
                @Override
                public void onClickItemProduct(CartItem cartItem) {
                    onClickGoToActivityInfo(cartItem);
                }
            });
            gridLayoutManager = new GridLayoutManager(this, 1);
            rcvProduct.setLayoutManager(gridLayoutManager);

            adapterDelivering.setData(dataCart.getDataDelivering());
            rcvProduct.setAdapter(adapterDelivering);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void onClickGoToActivityInfo(CartItem cartItem){
        Intent intent = new Intent(DeliveringActivity.this,InfomationProductActivity.class);
//        UtilCart.cartItem.setId(cartItem.getId());
//        UtilCart.cartItem.setProduct(cartItem.getProduct());
//        UtilCart.cartItem.setQuantity(cartItem.getQuantity());
//        UtilCart.cartItem.setSize(cartItem.getSize());
//        UtilCart.cartItem.setColor(cartItem.getColor());
//        UtilCart.cartItem.setStatus(cartItem.getStatus());
//        UtilInfomationProduct.product.setProductID(cartItem.getProduct().getProductID());
//        UtilInfomationProduct.product.setNameProduct(cartItem.getProduct().getNameProduct());
//        UtilInfomationProduct.product.setReSourceListImage(cartItem.getProduct().getReSourceListImage());
//        UtilInfomationProduct.product.setBrand(cartItem.getProduct().getBrand());
//        UtilInfomationProduct.product.setPrice(cartItem.getProduct().getPrice());
//        UtilInfomationProduct.product.setFavorite(cartItem.getProduct().getFavorite());
        startActivity(intent);
    }

    private void anhXa(){
        rcvProduct = findViewById(R.id.rcv_productDelivering);
        buttonBack = findViewById(R.id.button_back);
        tVStatusCart = findViewById(R.id.textView_statusFragment);
    }
}