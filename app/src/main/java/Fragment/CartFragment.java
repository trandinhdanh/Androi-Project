package Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.MainFragActivity;
import com.example.shopgiay.PayActivity;
import com.example.shopgiay.R;

import java.io.Serializable;
import java.util.ArrayList;

import Adapter.ProductAdapterAtCart;
import ObjectClass.CartItem;
import database.DataOfUser;


public class CartFragment extends Fragment {

    private RecyclerView rcvProductAtBag;
    private View mView;
    private MainFragActivity mainFragActivity;
    private ProductAdapterAtCart productAdapter;
    private Button buttonPay;
    private CheckBox cbAll;
    private TextView tvTotal;
    private int sumPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        mainFragActivity = (MainFragActivity) getActivity();
        anhXa();
        clickToPay();
        displayListAdapter();
        sumPrice = 0;
        String str = String.format("%,d", sumPrice);
        tvTotal.setText(str + "");

        return mView;
    }


    private void displayListAdapter() {
        productAdapter = new ProductAdapterAtCart(mainFragActivity, tvTotal, cbAll);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainFragActivity, 1);
        rcvProductAtBag.setLayoutManager(gridLayoutManager);
        productAdapter.setData(DataOfUser.getListProductDetailsCart());
        productAdapter.notifyDataSetChanged();
        rcvProductAtBag.setAdapter(productAdapter);

        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbAll.isChecked()) {
                    productAdapter.setIsSelectAllCheck(true);
                    productAdapter.setData(DataOfUser.getListProductDetailsCart());
                    rcvProductAtBag.setAdapter(productAdapter);
                } else {
                    productAdapter.setIsSelectAllCheck(false);
                    productAdapter.setData(DataOfUser.getListProductDetailsCart());
                    rcvProductAtBag.setAdapter(productAdapter);
                }
            }
        });
    }

    private void clickToPay() {
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productAdapter.getItemsChecked().contains(true)) {

                    ArrayList<String> itemPays = new ArrayList<>();
                    for (CartItem c : productAdapter.getIdChecked()) {
                        itemPays.add(DataOfUser.getIdProductsDetail(c.getIdItem(), c.getSize(), c.getColor()));
                    }

                    Intent intent = new Intent(mainFragActivity, PayActivity.class);
                    intent.putExtra("CartItems",itemPays);
                    startActivity(intent);
                } else {
                    Toast.makeText(mainFragActivity, "Bạn chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void anhXa() {
        rcvProductAtBag = mView.findViewById(R.id.rcv_productAtBag);
        buttonPay = mView.findViewById(R.id.button_PayAtCart);
        cbAll = mView.findViewById(R.id.checkBoxAllProduct);
        tvTotal = mView.findViewById(R.id.textView_totalMoney);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
