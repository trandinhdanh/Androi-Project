package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class UserPageActivity extends AppCompatActivity {
    private Button buttonEdit;
    private ImageButton buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        buttonEdit = findViewById(R.id.button_editCustomer);
        buttonBack = findViewById(R.id.button_backAtUserPage);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPageActivity.this,EditCustomerActivity.class));
            }
        });
    }
}