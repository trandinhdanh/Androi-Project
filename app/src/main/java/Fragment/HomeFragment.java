package Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopgiay.InfomationProductActivity;
import com.example.shopgiay.MainFragActivity;
import com.example.shopgiay.R;
import com.example.shopgiay.SearchActivity;
import Adapter.ProductAdapterAtHome;
import MyInterface.IOnClickItemProductHome;
import ObjectClass.Product;
import database.DataOfUser;

public class HomeFragment extends Fragment {

    private RecyclerView rcvProductAtHome;
    private View mView;
    private MainFragActivity mainFragActivity;
    private TextView tvTypeNike, tvTypeAdidas, tvTypeMizuno, tvTypeOther,tvSearch;
    private ProductAdapterAtHome productAdapter;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,container,false);
        mainFragActivity = (MainFragActivity) getActivity();

        anhXa();
        actionClickTvBrand();
        displayListAdapter();
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainFragActivity, SearchActivity.class));
            }
        });
        return mView;
    }

    private void anhXa() {
        rcvProductAtHome = mView.findViewById(R.id.rcv_ProductAtHome);
        tvTypeNike = mView.findViewById(R.id.textView_typeNike);
        tvTypeAdidas = mView.findViewById(R.id.textView_typeAdidas);
        tvTypeMizuno = mView.findViewById(R.id.textView_typeMizuno);
        tvTypeOther = mView.findViewById(R.id.textView_typeKhac);
        tvSearch = mView.findViewById(R.id.textView_searchAtHome);
    }
    //Hiển thị danh danh theo LOẠI khi nhấn vào các textView cho mỗi loại tương ứng
    private void actionClickTvBrand(){
        tvTypeNike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTypeNike.setTextColor(Color.parseColor("#FF0CD7E2"));
                tvTypeAdidas.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeMizuno.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeOther.setTextColor(Color.parseColor("#6a6a6c"));

                tvTypeNike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvTypeAdidas.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeMizuno.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeOther.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvTypeNike.setBackgroundResource(R.drawable.textlines);
                tvTypeAdidas.setBackground(null);
                tvTypeMizuno.setBackground(null);
                tvTypeOther.setBackground(null);

                rcvProductAtHome.setAdapter(null);
                productAdapter.setData(DataOfUser.getProductsByBrand(tvTypeNike.getText().toString()));
                rcvProductAtHome.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

            }
        });

        tvTypeAdidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvTypeNike.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeAdidas.setTextColor(Color.parseColor("#FF0CD7E2"));
                tvTypeMizuno.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeOther.setTextColor(Color.parseColor("#6a6a6c"));

                tvTypeNike.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeAdidas.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvTypeMizuno.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeOther.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvTypeAdidas.setBackgroundResource(R.drawable.textlines);
                tvTypeNike.setBackground(null);
                tvTypeMizuno.setBackground(null);
                tvTypeOther.setBackground(null);

                rcvProductAtHome.setAdapter(null);
                productAdapter.setData(DataOfUser.getProductsByBrand(tvTypeAdidas.getText().toString()));
                rcvProductAtHome.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

            }
        });

        tvTypeMizuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvTypeNike.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeAdidas.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeMizuno.setTextColor(Color.parseColor("#FF0CD7E2"));
                tvTypeOther.setTextColor(Color.parseColor("#6a6a6c"));

                tvTypeMizuno.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvTypeNike.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeAdidas.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeOther.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvTypeMizuno.setBackgroundResource(R.drawable.textlines);
                tvTypeNike.setBackground(null);
                tvTypeAdidas.setBackground(null);
                tvTypeOther.setBackground(null);

                rcvProductAtHome.setAdapter(null);
                productAdapter.setData(DataOfUser.getProductsByBrand(tvTypeMizuno.getText().toString()));
                rcvProductAtHome.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

            }
        });

        tvTypeOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvTypeNike.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeAdidas.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeMizuno.setTextColor(Color.parseColor("#6a6a6c"));
                tvTypeOther.setTextColor(Color.parseColor("#FF0CD7E2"));

                tvTypeOther.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvTypeNike.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeAdidas.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTypeMizuno.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvTypeOther.setBackgroundResource(R.drawable.textlines);
                tvTypeNike.setBackground(null);
                tvTypeMizuno.setBackground(null);
                tvTypeAdidas.setBackground(null);

                rcvProductAtHome.setAdapter(null);
                productAdapter.setData(DataOfUser.getProductsByBrand("other"));
                rcvProductAtHome.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

            }
        });
    }

    //Hiển thị danh sách đổ từ ADAPTER và sự kiện trên mỗi ITEM
    private void displayListAdapter(){
        productAdapter = new ProductAdapterAtHome(null, new IOnClickItemProductHome() {
            @Override
            public void onClickItemProduct(Product product) {
                Intent intent = new Intent(mainFragActivity, InfomationProductActivity.class);
                intent.putExtra("idProdClicked",product.getProductID());
                startActivity(intent);
            }
        },mainFragActivity);


        gridLayoutManager = new GridLayoutManager(mainFragActivity,2);
        rcvProductAtHome.setLayoutManager(gridLayoutManager);

        productAdapter.setData(DataOfUser.getProductsByBrand("Nike"));
        rcvProductAtHome.setAdapter(productAdapter);
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
