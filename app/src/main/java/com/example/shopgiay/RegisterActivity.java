package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import database.DataBase;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout til_RegisUser,til_RegisPhone,til_RegisRePass,til_RegisPass;
    private TextInputEditText tvRegisUser, tvRegisPass, tvRegisRePass, tvRegisFullName, tvRegisPhone;
    private TextView tvBack;
    private Button regisBtn;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dataBase = new DataBase(this, "shopgiay.sqlite", null, 1);
        map();
        action();


        //Chạm ngoài thì ẩn bàn phím
        findViewById(R.id.frame).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });
    }

    private void map() {
        tvBack = findViewById(R.id.textview_backActivityLogin);
        tvRegisUser = findViewById(R.id.regisUser);
        tvRegisPass = findViewById(R.id.regisPass);
        tvRegisRePass = findViewById(R.id.regisRePass);
        tvRegisFullName = findViewById(R.id.regisFullName);
        tvRegisPhone = findViewById(R.id.regisPhone);
        regisBtn = findViewById(R.id.regisBtn);
        til_RegisUser = findViewById(R.id.til_regisUserName);
        til_RegisPhone = findViewById(R.id.til_RegisPhone);
        til_RegisRePass = findViewById(R.id.til_RegisRePass);
        til_RegisPass = findViewById(R.id.til_RegisPass);

    }

    private void action() {
        //Action back
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Action Regis btn
        regisBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String userName = tvRegisUser.getText().toString();
                String pass = tvRegisPass.getText().toString();
                String rePass = tvRegisRePass.getText().toString();
                String fullname = tvRegisFullName.getText().toString();
                String phone = tvRegisPhone.getText().toString();
                String linkAvatarDefaul = "";
                String emailDefaul = "";
                String addressDefaul = "";
                int isFullValid = 0; // =0 thì không có lỗi nào.

                if (userName.equals("")) {
                    isFullValid++;
                    tvRegisUser.setError("Không được bỏ trống!!!");
                }

                if (fullname.equals("")) {
                    isFullValid++;
                    tvRegisFullName.setError("Không được bỏ trống!!!");
                }

                if (phone.equals("")) {
                    isFullValid++;
                    tvRegisPhone.setError("Không được bỏ trống!!!");
                } else if (!(phone.length() == 10) || !TextUtils.isDigitsOnly(phone)) {
                    isFullValid++;
                    til_RegisPhone.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    tvRegisPhone.setError("Sai định dạng!!!");
                }

                if (pass.equals("")) {
                    isFullValid++;
                    til_RegisPass.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    tvRegisPass.setError("Không được bỏ trống!!!");
                }

                if (rePass.equals("")) {
                    isFullValid++;
                    til_RegisRePass.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    tvRegisRePass.setError("Không được bỏ trống!!!");
                }

                if (isExistUserName(userName)) {
                    isFullValid++;
                    til_RegisUser.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    tvRegisUser.setError("User Name đã tồn tại. Hãy chọn User Name khác!!!");
                }


                if (!pass.equals(rePass)) {
                    isFullValid++;
                    Toast.makeText(RegisterActivity.this, "Hai mật khẩu không trùng khớp!!", Toast.LENGTH_SHORT).show();
                }


                if (isFullValid == 0) {
                    dataBase.queryData("INSERT INTO Account VALUES('" + userName + "', '" + pass + "', 0)");
                    dataBase.queryData
                            ("INSERT INTO Customer VALUES('" + userName + "', '" + linkAvatarDefaul +
                                    "','" + fullname + "','" + phone + "','" + emailDefaul + "','" + addressDefaul + "' )");
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        tvRegisUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    til_RegisUser.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tvRegisPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                til_RegisPhone.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tvRegisPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!tvRegisPass.getText().toString().equals(""))
                    til_RegisPass.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tvRegisRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!tvRegisRePass.getText().toString().equals(""))
                    til_RegisRePass.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean isExistUserName(String userName) {
        Cursor data = dataBase.getData("SELECT userName FROM Account");
        while (data.moveToNext()) {
            if (data.getString(0).equals(userName)) return true;
        }
        return false;
    }


}