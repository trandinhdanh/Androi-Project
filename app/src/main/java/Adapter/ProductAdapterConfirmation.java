package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.R;

import java.util.ArrayList;

import MyInterface.IOnClickItemProductCart;
import ObjectClass.CartItem;
import database.DataOfUser;

public class ProductAdapterConfirmation extends RecyclerView.Adapter<ProductAdapterConfirmation.ProductViewHolder> {

    private ArrayList<CartItem> waitComfirmOrders;
    private IOnClickItemProductCart iOnClickItemProductCart;
    private String idOrder = "";
    private ArrayList<String> idOrders = new ArrayList<>();

    private Context mContext;

    public ProductAdapterConfirmation(Context mContext, ArrayList<CartItem> waitComfirmOrders,String idOrder, IOnClickItemProductCart listener) {
        this.mContext = mContext;
        this.waitComfirmOrders = waitComfirmOrders;
        this.idOrder = idOrder;
        this.iOnClickItemProductCart = listener;
    }

    public void setData(ArrayList<CartItem> cartItems) {
        this.waitComfirmOrders = cartItems;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CartItem> cartItems,String idOrder) {
        this.waitComfirmOrders.addAll(cartItems);
        this.idOrder = idOrder;
        for(CartItem c : cartItems) idOrders.add(idOrder);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_confirmation, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int indexItemClicked = position;
        CartItem cartItem = waitComfirmOrders.get(position);
        if (cartItem == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(cartItem.getImages().get(0).getImageResource(), 0, cartItem.getImages().get(0).getImageResource().length);
        holder.imgProduct.setImageBitmap(bitmap);
//        holder.imgProduct.setImageResource(cartItem.getImages().get(0).getImageResource());
        holder.tvNameProduct.setText(cartItem.getName() + "");
        holder.tvQuality.setText("X" + cartItem.getQuantity());
        holder.tvSize.setText(cartItem.getSize() + "");
        holder.tvPrice.setText(cartItem.getPrice() + ".đ");
        holder.tvColor.setText(cartItem.getColor().toString());
        holder.tvSumPrice.setText((cartItem.getPrice() * cartItem.getQuantity()) + ".đ");
        holder.tvIdOrder.setText(idOrders.get(position));
//        holder.tvStatus.setText(cartItem.getStatus()+"");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItemProductCart.onClickItemProduct(cartItem);
            }
        });
        holder.buttonCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("Thông báo");
                dialog.setMessage("Bạn có chắc muốn hủy đơn hàng này!!!");

                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataOfUser.deleteOrderWaitComfirm( idOrders.remove(indexItemClicked),waitComfirmOrders.remove(indexItemClicked));
                        Toast.makeText(mContext, "Đã hủy", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (waitComfirmOrders != null) {
            return waitComfirmOrders.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvQuality, tvPrice, tvSumPrice, tvNameProduct, tvSize, tvColor, tvStatus,tvIdOrder;
        private CardView cardView;
        private Button buttonCancelOrder;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imageView_imgProductMyAtUser);
            tvQuality = itemView.findViewById(R.id.textView_qualityProductAtMyUser);
            tvPrice = itemView.findViewById(R.id.textView_priceProductAtMyUser);
            tvSumPrice = itemView.findViewById(R.id.textView_sumPriceProductAtMyUser);
            tvNameProduct = itemView.findViewById(R.id.textView_nameProductMyAtUser);
            tvSize = itemView.findViewById(R.id.textView_sizeProductMyAtUser);
            tvColor = itemView.findViewById(R.id.textView_colorProductMyAtUser);
            tvStatus = itemView.findViewById(R.id.textView_status);
            cardView = itemView.findViewById(R.id.view_itemHistory);
            buttonCancelOrder = itemView.findViewById(R.id.button_cancel_order);
            tvIdOrder = itemView.findViewById(R.id.textView_idOrder);
        }
    }
}
