package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import ObjectClass.CartItem;
import ObjectClass.DataCart;

public class AmountUsedActivity extends AppCompatActivity {
    ProgressBar loadingMain;
    TextView textView;
    DataCart dataCart;
    ImageButton buttonBack;
    private int sumAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_used);

        loadingMain =  findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView_amount);
        buttonBack = findViewById(R.id.button_back);
        dataCart = new DataCart();

        for (CartItem c:dataCart.getDataConfirmation()) {
            sumAmount+=c.getQuantity()*c.getPrice();
        }

        CountDownTimer countDownTimer = new CountDownTimer(2000,15) {
            @Override
            //Sau 100ms thì làm gì đó
            public void onTick(long l) {
                int startPB = loadingMain.getProgress();
                loadingMain.setProgress(startPB+1);
                for (int i=0; i<5; i++) {
                    Random rand = new Random();
                    int ranNum = rand.nextInt(5000000)+1;
                    textView.setText(ranNum+"");
                }

            }
            @Override
            //Khi chạy xong 2000ms thì làm gì
            public void onFinish() {
                Integer integer = Integer.parseInt(sumAmount+"");
                String str = String.format("%,d", integer);
                textView.setText(str+".đ");
                buttonBack.setVisibility(View.VISIBLE);
                loadingMain.setVisibility(View.GONE);

            }
        };
        countDownTimer.start();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}