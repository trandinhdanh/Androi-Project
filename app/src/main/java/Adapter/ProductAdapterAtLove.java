package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.R;

import java.util.ArrayList;

import MyInterface.IOnClickItemProductHome;
import ObjectClass.Product;
import database.DataOfUser;

public class ProductAdapterAtLove extends RecyclerView.Adapter<ProductAdapterAtLove.ProductViewHolder> {

    private Context mContext;
    private ArrayList<Product> mProductArrayList;
    private IOnClickItemProductHome iOnClickItemProductHome;
    private boolean isLoveBtnOn;

    public ProductAdapterAtLove(ArrayList<Product> products, IOnClickItemProductHome listener) {
        this.mProductArrayList = products;
        this.iOnClickItemProductHome = listener;
    }

    public void setData(ArrayList<Product> products) {
        this.mProductArrayList = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_home, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = mProductArrayList.get(position);

        if (product == null) {
            return;
        }

        if (product.getFavorite()) {
            holder.buttonLove.setImageResource(R.drawable.icon_love_on_red);
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getReSourceListImage().get(0).getImageResource(), 0, product.getReSourceListImage().get(0).getImageResource().length);
        holder.imgProduct.setImageBitmap(bitmap);
//        holder.imgProduct.setImageResource(product.getReSourceListImage().get(0).getImageResource());
        holder.tvNameProduct.setText(product.getNameProduct());
        holder.tvBrand.setText(product.getBrand());
        holder.tvPrice.setText(product.getPrice() + "");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemProductHome.onClickItemProduct(product);
            }
        });
        holder.buttonLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isLoveBtnOn = product.getFavorite();
                if (isLoveBtnOn) {
                    holder.buttonLove.setImageResource(R.drawable.icon_love_off);
                    isLoveBtnOn = false;
                    //del
                    Log.e("delete",product.getProductID());
                    DataOfUser.setFavorProduct(product.getProductID(), false);
                    setData(DataOfUser.getFavorProduct());
                } else {
                    isLoveBtnOn = true;
                    holder.buttonLove.setImageResource(R.drawable.icon_love_on_red);
                    //insert
                    Log.e("insert",product.getProductID());
                    DataOfUser.setFavorProduct(product.getProductID(), true);
                    setData(DataOfUser.getFavorProduct());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mProductArrayList != null) {
            return mProductArrayList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvNameProduct, tvPrice, tvBrand;
        private CardView cardView;
        private ImageButton buttonLove;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imageView_imgProductAtHome);
            tvNameProduct = itemView.findViewById(R.id.textView_nameProductAtHome);
            tvPrice = itemView.findViewById(R.id.textView_priceProductAtHome);
            tvBrand = itemView.findViewById(R.id.textView_BrandProductAtHome);
            cardView = itemView.findViewById(R.id.viewItemHome);
            buttonLove = itemView.findViewById(R.id.button_love_yes_no);
        }
    }
}
