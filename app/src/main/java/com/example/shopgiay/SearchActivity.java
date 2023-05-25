package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import Adapter.ProductAdapterAtHome;
import MyInterface.IOnClickItemProductHome;
import ObjectClass.DataArrayListProduct;
import ObjectClass.ListProduct;
import ObjectClass.Product;
import Util.UtilInfomationProduct;
import database.DataBase;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rcvProductAtSearch;
    private ImageButton buttonBack;
    private EditText eTSearch;
    private TextWatcher textWatcher = null;
    private ProductAdapterAtHome productAdapter;
    private GridLayoutManager gridLayoutManager;
    private ListProduct listProductMethor;
    private DataArrayListProduct dataProduct;
    private DataBase dataBase;
    private TextView tVStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dataBase = new DataBase(SearchActivity.this,"shopgiay.sqlite",null,1);
        dataProduct = new DataArrayListProduct(dataBase);
        listProductMethor = new ListProduct(dataProduct.getData());
        anhXa();
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (listProductMethor.getListFavorite().size()==0){
//                    tVStatus.setVisibility(View.VISIBLE);
//                }else{
//                    tVStatus.setVisibility(View.GONE);
                displayListAdapter(eTSearch.getText().toString());
                for (Product p:listProductMethor.findByName(eTSearch.getText().toString())) {
                    listProductMethor.findByName(eTSearch.getText().toString()).remove(p);
                }

            }
//            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        eTSearch.addTextChangedListener(textWatcher);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void displayListAdapter(String key){
        productAdapter = new ProductAdapterAtHome(dataProduct.getData(), new IOnClickItemProductHome() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickGoToActivityInfo(product);
            }
        }, this);

        gridLayoutManager = new GridLayoutManager(SearchActivity.this,2);
        rcvProductAtSearch.setLayoutManager(gridLayoutManager);

        productAdapter.setData(listProductMethor.findByName(key));
        rcvProductAtSearch.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void onClickGoToActivityInfo(Product product){
        Intent intent = new Intent(SearchActivity.this, InfomationProductActivity.class);
        intent.putExtra("idProdClicked",product.getProductID());
        startActivity(intent);
    }

    private void anhXa() {
        rcvProductAtSearch = findViewById(R.id.rcv_ProductAtSearch);
        buttonBack = findViewById(R.id.button_backAtSearch);
        eTSearch = findViewById(R.id.editText_search);
        tVStatus = findViewById(R.id.textView_status);
    }
}