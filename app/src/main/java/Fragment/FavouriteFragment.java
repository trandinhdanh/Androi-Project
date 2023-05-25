package Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import Adapter.ProductAdapterAtLove;
import MyInterface.IOnClickItemProductHome;
import ObjectClass.DataArrayListProduct;
import ObjectClass.ListProduct;
import ObjectClass.Product;
import Util.UtilInfomationProduct;
import database.DataBase;
import database.DataOfUser;

public class FavouriteFragment extends Fragment {

    private RecyclerView rcvProductAtLove;
    private View mView;
    private MainFragActivity mainFragActivity;
    private ProductAdapterAtLove productAdapter;
    private GridLayoutManager gridLayoutManager;
    private TextView tvStatus;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favourite,container,false);
        mainFragActivity = (MainFragActivity) getActivity();
        anhXa();
        displayListAdapter();
        return mView;
    }

    private void displayListAdapter() {
        if (DataOfUser.getFavorProduct().size()==0){
            tvStatus.setVisibility(View.VISIBLE);
        }else{
            tvStatus.setVisibility(View.GONE);
            productAdapter = new ProductAdapterAtLove(DataOfUser.getFavorProduct(), new IOnClickItemProductHome() {
            @Override
            public void onClickItemProduct(Product product) {
                Intent intent = new Intent(mainFragActivity, InfomationProductActivity.class);
                intent.putExtra("idProdClicked",product.getProductID());
                startActivity(intent);
            }
        });

        gridLayoutManager = new GridLayoutManager(mainFragActivity,2);
        rcvProductAtLove.setLayoutManager(gridLayoutManager);

        productAdapter.setData(DataOfUser.getFavorProduct()
        );
        rcvProductAtLove.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        }
    }

    private void anhXa() {
        rcvProductAtLove = mView.findViewById(R.id.rcv_ProductAtLove);
        tvStatus = mView.findViewById(R.id.textView_statusFragment);
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
