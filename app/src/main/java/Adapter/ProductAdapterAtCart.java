package Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.R;

import java.util.ArrayList;

import ObjectClass.CartItem;
import database.DataOfUser;

public class ProductAdapterAtCart extends RecyclerView.Adapter<ProductAdapterAtCart.ProductViewHolder> {

    private Context mContext;
    private ArrayList<CartItem> cartItemProdArrayList;
    private ArrayList<Boolean> itemsChecked = new ArrayList<>();
    private ArrayList<CartItem> idItemsChecked = new ArrayList<>();

    private TextView tvTotalCost;
    private CheckBox cbAll;
    private boolean isSelectAllCheck = false;
    private int totalCost = 0;

    public ProductAdapterAtCart(Context mContext, TextView totalCost, CheckBox cbAll) {
        this.mContext = mContext;
        this.tvTotalCost = totalCost;
        this.cbAll = cbAll;
    }

    public void setData(ArrayList<CartItem> listCartItem) {
        this.cartItemProdArrayList = listCartItem;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_mybag, parent, false);
        if(itemsChecked.isEmpty())
        for (int i = 0; i < cartItemProdArrayList.size(); i++) {
            itemsChecked.add(isSelectAllCheck);
        }
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int indexItemChecked = position;
        if (cartItemProdArrayList.isEmpty()) {
            return;
        }
        CartItem cartItem = cartItemProdArrayList.get(position);

        Bitmap bitmap = BitmapFactory.decodeByteArray(cartItem.getImages().get(0).getImageResource(), 0, cartItem.getImages().get(0).getImageResource().length);
        holder.imgUser.setImageBitmap(bitmap);
        holder.tvCartName.setText(cartItem.getName());
        holder.tvBrand.setText(cartItem.getBrand());
        String str = String.format("%,d", cartItem.getPrice() * cartItem.getQuantity());
        holder.tvPrice.setText(str + "");
        holder.tvColor.setText(cartItem.getColor());
        holder.tvSize.setText(cartItem.getSize() + "");
        holder.tvQuantity.setText(cartItem.getQuantity() + "");

        if (isSelectAllCheck)
            totalCost += cartItem.getPrice() * Integer.parseInt(holder.tvQuantity.getText() + "");
        else totalCost = 0;
        tvTotalCost.setText(String.valueOf(totalCost));

        holder.buttonCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.tvQuantity.getText() + "");
                count++;
                holder.tvQuantity.setText(count + "");
                String s = holder.tvPrice.getText() + "";
                String result = s.replaceAll("[\\-\\+\\.\\^:,]", "");
                Integer priceStart = Integer.parseInt(result + "");
                int priceResult = (priceStart / (count - 1)) * count;
                String str = String.format("%,d", priceResult);
                holder.tvPrice.setText(str + "");
                DataOfUser.updateCart(count, DataOfUser.getIdProductsDetail(cartItem.getIdItem(), cartItem.getSize(), cartItem.getColor()));

                if (holder.cbProductAtBag.isChecked()) {
                    totalCost += cartItem.getPrice();
                    tvTotalCost.setText(String.valueOf(totalCost));
                }
            }
        });

        holder.buttonTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.tvQuantity.getText() + "");
                if (count == 1) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("Thông báo");
                    dialog.setMessage("Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng");

                    dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            totalCost = 0;
                            cartItemProdArrayList.remove(cartItem);
                            if (cartItemProdArrayList.size() == 0) tvTotalCost.setText("0");
                            itemsChecked.remove(indexItemChecked);
                            DataOfUser.deleteItemCart(cartItem);
                            notifyDataSetChanged();
                        }
                    });
                    dialog.show();
                } else {
                    count--;
                    holder.tvQuantity.setText(count + "");
                    String s = holder.tvPrice.getText() + "";
                    String result = s.replaceAll("[\\-\\+\\.\\^:,]", "");
                    Integer priceStart = Integer.parseInt(result + "");
                    int priceResult = (priceStart / (count + 1)) * count;
                    String str = String.format("%,d", priceResult);
                    holder.tvPrice.setText(str + "");
                    DataOfUser.updateCart(count, DataOfUser.getIdProductsDetail(cartItem.getIdItem(), cartItem.getSize(), cartItem.getColor()));

                    if (holder.cbProductAtBag.isChecked()) {
                        totalCost -= cartItem.getPrice();
                        tvTotalCost.setText(String.valueOf(totalCost));
                    }
                }
            }
        });

        holder.cbProductAtBag.setChecked(itemsChecked.get(position));

        holder.cbProductAtBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());


                if (holder.cbProductAtBag.isChecked() == true) {
                    totalCost += cartItem.getPrice() * quantity;
                    idItemsChecked.add(cartItem);
                    tvTotalCost.setText(String.valueOf(totalCost));
                    itemsChecked.set(indexItemChecked, true);
                } else {
                    totalCost -= cartItem.getPrice() * quantity;
                    tvTotalCost.setText(String.valueOf(totalCost));
                    idItemsChecked.remove(cartItem);
                    itemsChecked.set(indexItemChecked, false);
                }
                if (!itemsChecked.contains(false)) cbAll.setChecked(true);
                else cbAll.setChecked(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartItemProdArrayList != null) {
            return cartItemProdArrayList.size();
        }
        return 0;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView tvCartName, tvPrice, tvBrand, tvQuantity, tvTotalMoney, tvSize, tvColor;
        private ImageButton buttonYNLove;
        private Button buttonTru, buttonCong;
        private CheckBox cbProductAtBag;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonYNLove = itemView.findViewById(R.id.button_love_yes_no);
            imgUser = itemView.findViewById(R.id.imageView_imgProductAtBag);
            buttonTru = itemView.findViewById(R.id.button_truAtBag);
            buttonCong = itemView.findViewById(R.id.button_congAtBag);
            cbProductAtBag = itemView.findViewById(R.id.checkBoxProduct);

            tvCartName = itemView.findViewById(R.id.textview_nameProductAtBag);
            tvBrand = itemView.findViewById(R.id.textView_TheFirmProductAtBag);
            tvSize = itemView.findViewById(R.id.textView_SizeProductAtBag);
            tvColor = itemView.findViewById(R.id.textView_ColorProductAtBag);
            tvQuantity = itemView.findViewById(R.id.textView_numberProductAtBag);
            tvPrice = itemView.findViewById(R.id.textView_priceProductAtBag);

            tvTotalMoney = itemView.findViewById(R.id.textView_totalMoney);
        }
    }

    public void setIsSelectAllCheck(boolean isSelect) {
        this.isSelectAllCheck = isSelect;
        itemsChecked.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Boolean> getItemsChecked() {
        return this.itemsChecked;
    }

    public ArrayList<CartItem> getIdChecked() {
        this.idItemsChecked.clear();
        for(int i = 0; i < itemsChecked.size();i++){
            if(itemsChecked.get(i)){
                this.idItemsChecked.add(cartItemProdArrayList.get(i));
            }
        }
        return this.idItemsChecked;
    }
}
