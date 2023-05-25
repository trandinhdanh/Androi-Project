package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.shopgiay.R;

import java.util.ArrayList;

import ObjectClass.Image;

public class ImageSlideAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Image> images;

    public ImageSlideAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slide_photo,container,false);
        ImageView imgImage = view.findViewById(R.id.imageView_slidePhoto);
        Image image = images.get(position);
        if (image!=null){
            Glide.with(context).load(image.getImageResource()).into(imgImage);
        }
        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        if (images!=null){
            return images.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
