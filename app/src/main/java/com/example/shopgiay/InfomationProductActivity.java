
package com.example.shopgiay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import Adapter.ImageSlideAdapter;
import ObjectClass.Product;
import Util.UtilInfomationProduct;
import database.DataOfUser;
import me.relex.circleindicator.CircleIndicator;

public class InfomationProductActivity extends AppCompatActivity {

    private TextView tvName, tvBrand, tvDescrip, tvPrice;
    private ImageButton buttonLove, buttonBack, buttonView, buttonAddToCart;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ImageSlideAdapter slideAdapter;
    private MediaPlayer mediaPlayer;
    private Product product;

    private ArrayList<String> dataColorSpinner, dataSizeSpinner;
    private boolean clickSize = true;
    private int count = 1;
    private String idProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_product);
        DataOfUser.setDataBase(this);
        idProd = getIntent().getStringExtra("idProdClicked");
        Toast.makeText(InfomationProductActivity.this, idProd, Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(InfomationProductActivity.this, R.raw.coi_bao_dong);
        anhXa();

//      set in4 cho chi tiết sản phẩm
        product = DataOfUser.getProductByID(idProd);
        tvName.setText(product.getNameProduct().toString());
        tvBrand.setText(product.getBrand().toString());
        String str = String.format("%,d", product.getPrice());
        tvPrice.setText(str+"");
        tvDescrip.setText(product.getDescribe());


        if (product.getFavorite()) {
            buttonLove.setImageResource(R.drawable.icon_love_on_red);
        } else {
            buttonLove.setImageResource(R.drawable.icon_love_off);
        }

        slideAdapter = new ImageSlideAdapter(this, product.getReSourceListImage());
        viewPager.setAdapter(slideAdapter);

        circleIndicator.setViewPager(viewPager);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                buttonView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(InfomationProductActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                        dialogViewProduct(position);
                    }
                });
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOpenBottomSheetDialog();
            }

        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfomationProductActivity.this, MainFragActivity.class);
                intent.putExtra("page", 0);
                startActivity(intent);
            }
        });

    }

    private void clickOpenBottomSheetDialog() {
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_add_to_cart, null);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();

        Button buttonExit = viewDialog.findViewById(R.id.button_dialog_exit);
        Button buttonAddCart = viewDialog.findViewById(R.id.bt_addCart);
        Button buttonTru = viewDialog.findViewById(R.id.button_tru);
        Button buttonCong = viewDialog.findViewById(R.id.button_cong);
        TextView textViewPrice = viewDialog.findViewById(R.id.textView_bottomSheetPrice);
        TextView textViewQuality = viewDialog.findViewById(R.id.textView_QualityAtAddCart);
        Spinner spinnerSizeProduct = viewDialog.findViewById(R.id.spinner_Size);
        Spinner spinnerColorProduct = viewDialog.findViewById(R.id.spinner_Color);

        textViewPrice.setText(tvPrice.getText() + "");

        dataSizeSpinner = DataOfUser.getSizeExist(idProd, "");
        String defaulSize = dataSizeSpinner.get(0);
        dataColorSpinner = DataOfUser.getColorExist(idProd, Integer.parseInt(defaulSize));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dataSizeSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSizeProduct.setAdapter(arrayAdapter);

        ArrayAdapter arrayAdapterCl = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dataColorSpinner);
        arrayAdapterCl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColorProduct.setAdapter(arrayAdapterCl);

        spinnerSizeProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                actionChangeSpinnerSizeAndColor(spinnerSizeProduct, spinnerColorProduct, Integer.parseInt(dataSizeSpinner.get(i)), "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(textViewQuality.getText() + "");
                count++;
                textViewQuality.setText(count + "");
                String s = textViewPrice.getText() + "";
                String result = s.replaceAll("[\\-\\+\\.\\^:,]", "");
                Integer priceStart = Integer.parseInt(result + "");
                int priceResult = (priceStart / (count - 1)) * count;
                String str = String.format("%,d", priceResult);
                textViewPrice.setText(str + "");
            }
        });

        buttonTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(textViewQuality.getText() + "");
                if (count == 1) {

                } else {
                    count--;
                    textViewQuality.setText(count + "");
                    String s = textViewPrice.getText() + "";
                    String result = s.replaceAll("[\\-\\+\\.\\^:,]", "");
                    Integer priceStart = Integer.parseInt(result + "");
                    int priceResult = (priceStart / (count + 1)) * count;
                    String str = String.format("%,d", priceResult);
                    textViewPrice.setText(str + "");
                }
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        buttonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sizeSelected = Integer.parseInt(spinnerSizeProduct.getSelectedItem().toString());
                String colorSelected = spinnerColorProduct.getSelectedItem().toString();
                int quantitySelected = Integer.parseInt(textViewQuality.getText().toString());

                String idProdDetails = DataOfUser.getIdProductsDetail(idProd, sizeSelected, colorSelected);

                if (idProdDetails == null)
                    Toast.makeText(InfomationProductActivity.this, "Sản phẩm không còn size với số lượng tương tự.Hãy chọn loại khác", Toast.LENGTH_SHORT).show();
                else {
                    if (DataOfUser.idUser.equals("")) {
                        mediaPlayer.start();
                        Toast.makeText(InfomationProductActivity.this, "Bạn chưa đăng nhập!! Vui lòng đăng nhập !!", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(InfomationProductActivity.this);
                        dialog.setTitle("Thông báo");
                        dialog.setCancelable(false);
                        dialog.setMessage("Bạn chưa đăng nhập!! Vui lòng đăng nhập !!");
                        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mediaPlayer.pause();
                            }
                        });

                        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(InfomationProductActivity.this,LoginActivity.class));
                                mediaPlayer.pause();
                            }
                        });

                        dialog.show();
                    } else {
                        //Kiểm tra cái details này tồn tại trong giỏ hàng chưa.nếu chưa tạo mới.nếu rồi tăng số lượng
                        int amountDetailsInCart = DataOfUser.getAmountDetailsInCart(idProdDetails);

                        if (amountDetailsInCart == 0) {//=0 là chưa có trong giỏ hàng nên thêm mới
                            DataOfUser.insertCart(quantitySelected, idProdDetails);
                        } else {
                            DataOfUser.updateCart(quantitySelected + amountDetailsInCart, idProdDetails);
                        }
                        bottomSheetDialog.dismiss();
                        Toast.makeText(InfomationProductActivity.this,"Thêm sản phẩm thành công",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void dialogViewProduct(int position) {
        Dialog dialog = new Dialog(InfomationProductActivity.this);
        dialog.setContentView(R.layout.dialog_view_image_product);
        dialog.setCanceledOnTouchOutside(false);
        ImageView imgProduct = dialog.findViewById(R.id.imageView_dialogImageProduct);
        ImageButton buttonExit = dialog.findViewById(R.id.button_dialog_exit);

        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getReSourceListImage().get(position).getImageResource(), 0, product.getReSourceListImage().get(position).getImageResource().length);
       imgProduct.setImageBitmap(bitmap);

        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            float scale = 1.0f, onScaleStart = 0, onScaleEnd = 0;
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                scale *= scaleGestureDetector.getScaleFactor();
                imgProduct.setScaleX(scale);
                imgProduct.setScaleY(scale);
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                onScaleStart = scale;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                onScaleEnd = scale;
            }
        });

        imgProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void actionChangeSpinnerSizeAndColor(Spinner spnSize, Spinner spnColor, int size, String color) {
        if (size > 0) {
            dataColorSpinner.clear();
            dataColorSpinner.addAll(DataOfUser.getColorExist(idProd, size));
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dataColorSpinner);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnColor.setAdapter(arrayAdapter);
        }

        if (!color.equals("")) {
            dataSizeSpinner.clear();
            dataSizeSpinner.addAll(DataOfUser.getSizeExist(idProd, color));
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dataSizeSpinner);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnSize.setAdapter(arrayAdapter);
        }
    }

    private void anhXa() {
        tvName = findViewById(R.id.textView_nameProductAtInfomation);
        tvBrand = findViewById(R.id.textView_TheFirmAtInfomation);
        tvDescrip = findViewById(R.id.textView_noteAtInfomation);
        tvPrice = findViewById(R.id.textView_priceProductAtInfomation);
        buttonAddToCart = findViewById(R.id.bt_addCartAtInfomation);
        buttonLove = findViewById(R.id.button_love);
        buttonBack = findViewById(R.id.button_back);
        buttonView = findViewById(R.id.button_eye);
        viewPager = findViewById(R.id.viewPager_slideImage);
        circleIndicator = findViewById(R.id.circleIndicator_slideImage);
    }
}
