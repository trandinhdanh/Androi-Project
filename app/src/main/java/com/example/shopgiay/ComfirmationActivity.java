package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import Adapter.ProductAdapterConfirmation;
import MyInterface.IOnClickItemProductCart;
import MyInterface.StateOrderCode;
import ObjectClass.CartItem;
import database.DataOfUser;

public class ComfirmationActivity extends AppCompatActivity {

    private RecyclerView rcvProduct;
    private ImageButton buttonBack;
    private ProductAdapterConfirmation adapterConfirmation;
    private GridLayoutManager gridLayoutManager;
    private TextView tvStatusCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        anhXa();
        Map<String,ArrayList<CartItem>> waitComfirmOrders = DataOfUser.getOrdersByState(StateOrderCode.STATE_WAIT_COMFIRM);

        if (waitComfirmOrders.size() == 0) {
            tvStatusCart.setVisibility(View.VISIBLE);
        } else {
            tvStatusCart.setVisibility(View.GONE);
            adapterConfirmation = new ProductAdapterConfirmation(this, new ArrayList<CartItem>(),"", new IOnClickItemProductCart() {
                @Override
                public void onClickItemProduct(CartItem cartItem) {
                    onClickGoToActivityInfo(cartItem);
                }
            });
            gridLayoutManager = new GridLayoutManager(this, 1);
            rcvProduct.setLayoutManager(gridLayoutManager);
            rcvProduct.setAdapter(adapterConfirmation);

            for (Map.Entry<String,ArrayList<CartItem>> entry : waitComfirmOrders.entrySet()){
                String idOrder =  entry.getKey();
                adapterConfirmation.addData(entry.getValue(),idOrder);
            }
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComfirmationActivity.this, MainFragActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickGoToActivityInfo(CartItem cartItem) {
        Intent intent = new Intent(ComfirmationActivity.this, InfomationProductActivity.class);
        startActivity(intent);
    }

    private void anhXa() {
        rcvProduct = findViewById(R.id.rcv_productConfirmation);
        buttonBack = findViewById(R.id.button_back);
        tvStatusCart = findViewById(R.id.textView_statusFragment);
    }
}