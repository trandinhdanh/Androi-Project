package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import database.DataBase;
import database.DataOfUser;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextView tvCreateAcc,tvUserName,tvPassword;
    private DataBase dataBase;
    private CheckBox checkBoxRemember;
    private SharedPreferences sharedPreferences;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataBase = new DataBase(this,"shopgiay.sqlite",null,1);
        mediaPlayer = MediaPlayer.create(LoginActivity.this, R.raw.dang_nhap_thanh_cong);
        map();
        login();
        tvCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void map(){
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        tvCreateAcc = findViewById(R.id.textview_createAccount);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
    }

    private void login(){
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        tvUserName.setText(sharedPreferences.getString("taikhoan",""));
        tvPassword.setText(sharedPreferences.getString("matkhau",""));
        checkBoxRemember.setChecked(sharedPreferences.getBoolean("checked",false));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = tvUserName.getText().toString();
                String pass = tvPassword.getText().toString();

                Cursor data = dataBase.getData("SELECT * FROM Account");

                while(data.moveToNext()){
                    if(userName.equals(data.getString(0)) && pass.equals(data.getString(1))){
                        if(data.getInt(2) == 0){

                            //Khởi tạo dữ liệu cho user
                            DataOfUser.setIdUser(userName);
                            DataOfUser.setDataBase(dataBase);

                            if (checkBoxRemember.isChecked()){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("taikhoan",userName);
                                editor.putString("matkhau",pass);
                                editor.putBoolean("checked",true);
                                editor.commit();
                            }
                            else{
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("taikhoan");
                                editor.remove("matkhau");
                                editor.remove("checked");
                                editor.commit();
                            }

                            sharedPreferences = getSharedPreferences("isLogin",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("idUser",userName);
                            editor.commit();
                            //chuyển màn hình user
                            Intent intent = new Intent(LoginActivity.this,MainFragActivity.class);
                            startActivity(intent);
                            mediaPlayer.start();
                            Toast.makeText(LoginActivity.this, "Bạn đã đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        }else{
                            //chuyển màn hình admin
                        }
                        return;
                    }
                }

                if(!data.moveToNext()){
                    Toast.makeText(LoginActivity.this, "Tài khoảng hoặc mật khẩu không đúng!!! vui lòng nhập lại", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
