package com.example.shopgiay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ObjectClass.Date;
import ObjectClass.User;

public class EditCustomerActivity extends AppCompatActivity {
    private ListView lVEditCustomer;
    private ArrayList<String> listEditCustomer;
    private ArrayAdapter adapter;
    private User user;
    private ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        lVEditCustomer = findViewById(R.id.listView_editCustomer);
        buttonBack = findViewById(R.id.button_backAtEditUser);


        user = new User("Nguyễn Văn Hiếu",new Date(27,4,2001),"0798631497","Nam","60/21/32 đường số 4," +
                " phường Trường Thọ, thành phố Thủ Đức, thành phố Hồ Chí Minh");
        listEditCustomer = new ArrayList<String>();
        listEditCustomer.add("Tên"+"   >  "+user.getName());
        listEditCustomer.add("Giới tính"+"   >  "+user.getGender());
        listEditCustomer.add("Ngày sinh"+"   >  "+user.getBirthDay().toString());
        listEditCustomer.add("Sô điện thoại"+"   >  "+user.getNumberPhone());
        listEditCustomer.add("Địa chỉ"+"   >  "+user.getAddress());

        adapter = new ArrayAdapter(EditCustomerActivity.this, android.R.layout.simple_list_item_1, listEditCustomer);
        lVEditCustomer.setAdapter(adapter);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lVEditCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i la chi Index, bat dau = 0
                switch (i){
                    case 0:
                        dialogEdit(0);
                        break;
                    case 1:
                        dialogEdit(1);
                        break;
                    case 2:
                        dialogEdit(2);
                        break;
                    case 3:
                        dialogEdit(3);
                        break;
                    case 4:
                        dialogEdit(4);
                        break;
                    default:
                        break;
                }

            }
        });
    }
    private void dialogEdit(int number){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_customer);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvTitle = dialog.findViewById(R.id.textView_dialog_title);
        EditText etEdit = dialog.findViewById(R.id.editText_dialog_inputEdit);
        Button buttonSave = dialog.findViewById(R.id.button_dialog_save);
        ImageButton buttonExit =dialog.findViewById(R.id.button_dialog_exit);
        dialog.show();
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        switch (number){
            case 0:
                tvTitle.setText("Đổi tên");
                etEdit.setText(""+user.getName());
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = etEdit.getText()+"";
                        user.setName(name);
                        listEditCustomer.set(0,"Tên"+"   >  "+user.getName());
                        lVEditCustomer.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                break;
            case 1:
                tvTitle.setText("Giới tính");
                etEdit.setText(""+user.getGender());
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String gender = etEdit.getText() + "";
                        if ("Nam".equalsIgnoreCase(gender)||"Nữ".equalsIgnoreCase(gender)||"Nu".equalsIgnoreCase(gender)){
                            user.setGender(gender);
                            listEditCustomer.set(1, "Giới tính" + "   >  " + user.getGender());
                            lVEditCustomer.setAdapter(adapter);
                            dialog.dismiss();
                        }else {
                            Toast.makeText(EditCustomerActivity.this,"Nhập sai rồi",Toast.LENGTH_SHORT).show();
                            etEdit.setText("");
                            etEdit.setHint("Giới tính là nam hoặc nữ");
                    }
                }
                });
                break;
            case 2:
                tvTitle.setText("Ngày sinh");
                etEdit.setText(""+user.getBirthDay().toString());
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = etEdit.getText()+"";
                        user.setName(name);
                        listEditCustomer.set(2,"Tên"+"   >  "+user.getBirthDay());
                        lVEditCustomer.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                break;
            case 3:
                etEdit.setInputType(number);
                tvTitle.setText("Số điện thoại");
                etEdit.setText(""+user.getNumberPhone());
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String numberPhone = etEdit.getText()+"";
                        user.setNumberPhone(numberPhone);
                        listEditCustomer.set(3,"Sô điện thoại"+"   >  "+user.getNumberPhone());
                        lVEditCustomer.setAdapter(adapter);
                        dialog.dismiss();

                    }
                });
                break;
            case 4:
                tvTitle.setText("Địa chỉ");
                etEdit.setText(""+user.getAddress());
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String address = etEdit.getText()+"";
                        user.setAddress(address);
                        listEditCustomer.set(4,"Địa chỉ"+"   >  "+user.getAddress());
                        lVEditCustomer.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }
}