package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.R;

import java.util.ArrayList;

import MyInterface.IOnClickItemProductCart;
import ObjectClass.CartItem;

public class ProductAdapterDelivering extends RecyclerView.Adapter<ProductAdapterDelivering.ProductViewHolder>{

    private ArrayList<CartItem> cartItemArrayList;
    private IOnClickItemProductCart iOnClickItemProductCart;
    private Context mContext;
    private boolean yesNo =false;

    public ProductAdapterDelivering(Context mContext, ArrayList<CartItem> cartItems, IOnClickItemProductCart listener) {
        this.mContext = mContext;
        this.cartItemArrayList = cartItems;
        this.iOnClickItemProductCart =listener;
    }

    public void setData(ArrayList<CartItem> cartItems){
        this.cartItemArrayList = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_delivering,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        CartItem cartItem = cartItemArrayList.get(position);
        if (cartItem == null){
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(cartItem.getImages().get(0).getImageResource(), 0, cartItem.getImages().get(0).getImageResource().length);
        holder.imgProduct.setImageBitmap(bitmap);
//        holder.imgProduct.setImageResource(cartItem.getImages().get(0).getImageResource());
        holder.tvNameProduct.setText(cartItem.getName()+"");
        holder.tvQuality.setText("X"+ cartItem.getQuantity());
        holder.tvSize.setText(cartItem.getSize()+"");
        holder.tvPrice.setText(cartItem.getPrice()+".đ");
        holder.tvSumPrice.setText((cartItem.getPrice()* cartItem.getQuantity())+".đ");
        holder.tvStatus.setText(cartItem.getStatus()+"");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemProductCart.onClickItemProduct(cartItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartItemArrayList != null){
            return cartItemArrayList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView tvQuality, tvPrice, tvSumPrice, tvNameProduct,tvSize,tvColor, tvStatus;
        private CardView cardView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct =itemView.findViewById(R.id.imageView_imgProductMyAtUser);
            tvQuality=itemView.findViewById(R.id.textView_qualityProductAtMyUser);
            tvPrice =itemView.findViewById(R.id.textView_priceProductAtMyUser);
            tvSumPrice =itemView.findViewById(R.id.textView_sumPriceProductAtMyUser);
            tvNameProduct =itemView.findViewById(R.id.textView_nameProductMyAtUser);
            tvSize = itemView.findViewById(R.id.textView_sizeProductMyAtUser);
            tvColor = itemView.findViewById(R.id.textView_colorProductMyAtUser);
            tvStatus = itemView.findViewById(R.id.textView_status);
            cardView = itemView.findViewById(R.id.view_itemHistory);
        }
    }
}
