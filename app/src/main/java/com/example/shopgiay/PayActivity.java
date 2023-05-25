package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.ProductAdapterPay;
import MyInterface.IOnClickItemProductCart;
import MyInterface.StateOrderCode;
import ObjectClass.CartItem;
import database.DataOfUser;

public class PayActivity extends AppCompatActivity {

    private ImageButton buttonBack;
    private Button buttonPay, buttonEdit;
    private RecyclerView recyclerView;
    private ProductAdapterPay adapterPay;
    private GridLayoutManager gridLayoutManager;
    private TextView tvSumPrice, tvQualityProduct, tvQuality, tvTotal,tvAddress;
    private int sumPrice, qualityProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        anhXa();
        DataOfUser.setDataBase(this);
        sumPrice = 0;
        qualityProduct = 0;

        ArrayList<String> idPayItems = (ArrayList<String>) getIntent().getSerializableExtra("CartItems");
        ArrayList<CartItem> cartItems = DataOfUser.getListProductDetailsCartByIDs(idPayItems);

        for (CartItem c : cartItems) {
            sumPrice += (c.getPrice() * c.getQuantity());
            qualityProduct += c.getQuantity();
        }
        
        String str = String.format("%,d", sumPrice);
        tvSumPrice.setText(str + "");
        tvTotal.setText(str + "");
        tvQualityProduct.setText(qualityProduct + "");
        tvQuality.setText(cartItems.size() + "");

        adapterPay = new ProductAdapterPay(this, cartItems, new IOnClickItemProductCart() {
            @Override
            public void onClickItemProduct(CartItem cartItem) {
            }
        });
        
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterPay.setData(cartItems);
        recyclerView.setAdapter(adapterPay);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayActivity.this, EditCustomerActivity.class));
            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataOfUser.insertOrders(cartItems,sumPrice,tvAddress.getText().toString(), StateOrderCode.STATE_WAIT_COMFIRM);
                DataOfUser.deleteItemsCart(cartItems);
                Intent intent = new Intent(PayActivity.this, ComfirmationActivity.class);
                intent.putExtra("CartItems",idPayItems);
                startActivity(intent);
                Toast.makeText(PayActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void anhXa() {
        buttonPay = findViewById(R.id.button_Pay);
        buttonBack = findViewById(R.id.button_back);
        recyclerView = findViewById(R.id.rcv_productAtPay);
        tvQuality = findViewById(R.id.textView_quality);
        tvQualityProduct = findViewById(R.id.textView_qualityProduct);
        tvSumPrice = findViewById(R.id.textView_priceProductAtPay);
        tvTotal = findViewById(R.id.textView_totalMoney);
        buttonEdit = findViewById(R.id.button_edit);
        tvAddress = findViewById(R.id.textView_addressPay);
    }
}