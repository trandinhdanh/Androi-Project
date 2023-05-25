package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class IntroduceActivity extends AppCompatActivity {

    private ImageButton buttonBack;
    private TextView tvNumberPhone,tvToPhone,tvMapAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        anhXa();
        callToShop();
        tvMapAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                 Uri location = Uri.parse("geo:10.833918078412527, 106.76177499443993");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
                Toast.makeText(IntroduceActivity.this,"Hello",Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void anhXa(){
        buttonBack = findViewById(R.id.button_backAtIntroduce);
        tvNumberPhone = findViewById(R.id.textView_numberPhoneShop);
        tvToPhone = findViewById(R.id.textView_toPhone);
        tvMapAddress = findViewById(R.id.textView_addressToGoogleMap);

    }
    private void callToShop(){
        tvToPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                String numberPhone =tvNumberPhone.getText()+"";
                intent.setData(Uri.parse("tel:"+numberPhone));
                startActivity(intent);
            }
        });
    }
}