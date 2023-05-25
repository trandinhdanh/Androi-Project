package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.R;

import java.util.ArrayList;

import MyInterface.IOnClickItemProductCart;
import ObjectClass.CartItem;

public class ProductAdapterPay extends RecyclerView.Adapter<ProductAdapterPay.ProductViewHolder> {

    private ArrayList<CartItem> cartItemArrayList;
    private IOnClickItemProductCart iOnClickItemProductCart;
    private Context mContext;
    private boolean yesNo = false;

    public ProductAdapterPay(Context mContext, ArrayList<CartItem> cartItems, IOnClickItemProductCart listener) {
        this.mContext = mContext;
        this.cartItemArrayList = cartItems;
        this.iOnClickItemProductCart = listener;
    }

    public void setData(ArrayList<CartItem> cartItems) {
        this.cartItemArrayList = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_pay, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        CartItem cartItem = cartItemArrayList.get(position);
        if (cartItem == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(cartItem.getImages().get(0).getImageResource(), 0, cartItem.getImages().get(0).getImageResource().length);
        holder.imgProduct.setImageBitmap(bitmap);
//        holder.imgProduct.setImageResource(cartItem.getImages().get(0).getImageResource());
        holder.tVNameProduct.setText(cartItem.getName().toString());
        holder.tvQuality.setText("X" + cartItem.getQuantity());
        holder.tvSize.setText(cartItem.getSize() + "");
        holder.tVPrice.setText(cartItem.getPrice() + ".đ");
        holder.tvColor.setText(cartItem.getColor() + "");
        holder.tVSumPrice.setText((cartItem.getPrice() * cartItem.getQuantity()) + ".đ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemProductCart.onClickItemProduct(cartItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartItemArrayList != null) {
            return cartItemArrayList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvQuality, tVPrice, tVSumPrice, tVNameProduct, tvSize, tvColor;
        private CardView cardView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imageView_imgProductMyAtPay);
            tvQuality = itemView.findViewById(R.id.textView_qualityProductAtMyPay);
            tVPrice = itemView.findViewById(R.id.textView_priceProductAtMyPay);
            tVSumPrice = itemView.findViewById(R.id.textView_sumPriceProductAtMyPay);
            tVNameProduct = itemView.findViewById(R.id.textView_nameProductMyAtPay);
            tvSize = itemView.findViewById(R.id.textView_sizeProductMyAtPay);
            tvColor = itemView.findViewById(R.id.textView_colorProductMyAtPay);
            cardView = itemView.findViewById(R.id.view_itemPay);
        }
    }
}
