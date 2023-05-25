package com.example.shopgiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import Fragment.AdapterFragmentNavigation;
import database.DataBase;
import database.DataOfUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private CircleImageView imgAvata,imgFake;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frag);
        DataOfUser.setDataBase(this);

        anhXa();
        if (DataOfUser.idUser==""){
            imgAvata.setVisibility(View.GONE);
            imgFake.setVisibility(View.VISIBLE);
        }else {
            imgAvata.setVisibility(View.VISIBLE);
            imgFake.setVisibility(View.GONE);
        }

//        Nút Avatar đi tới trang cá nhân
        imgAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainFragActivity.this,UserPageActivity.class));
            }
        });

//      Thiết lập viewpager
        AdapterFragmentNavigation adapterFragmentNavigation = new AdapterFragmentNavigation(getSupportFragmentManager(),
//                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT//return 1.
                1);
        viewPager.setAdapter(adapterFragmentNavigation);

        viewPager.setOffscreenPageLimit(0);


//      xử lí cho nó hiện trang nào
        int page = getIntent().getIntExtra("page",0);
        setPageView(page);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_home).setChecked(true);
                        tvTitle.setText("Trang chủ");
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_cart).setChecked(true);
                        tvTitle.setText("Giỏ hàng");
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_favourite).setChecked(true);
                        tvTitle.setText("Mục yêu thích");
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_comment).setChecked(true);
                        tvTitle.setText("Hỗ trợ");
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_user).setChecked(true);
                        tvTitle.setText("Người dùng");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        Sự kiện khi click vào bottom nav
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_tab_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_tab_cart:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_tab_favourite:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.menu_tab_comment:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.menu_tab_user:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

    }

    private void setPageView(int page){
        viewPager.setCurrentItem(page);
        switch (page){
            case 0:
                bottomNavigationView.getMenu().findItem(R.id.menu_tab_home).setChecked(true);
                tvTitle.setText("Home");
                break;
            case 1:
                bottomNavigationView.getMenu().findItem(R.id.menu_tab_cart).setChecked(true);
                tvTitle.setText("Cart");
                break;
            case 2:
                bottomNavigationView.getMenu().findItem(R.id.menu_tab_favourite).setChecked(true);
                tvTitle.setText("Favourite");
                break;
            case 3:
                bottomNavigationView.getMenu().findItem(R.id.menu_tab_comment).setChecked(true);
                tvTitle.setText("Comment");
                break;
            case 4:
                bottomNavigationView.getMenu().findItem(R.id.menu_tab_user).setChecked(true);
                tvTitle.setText("User");
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void anhXa(){
        viewPager = findViewById(R.id.layout_viewPager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        imgAvata = findViewById(R.id.imageView_avata);
        imgFake = findViewById(R.id.imageView_avataFake);
        tvTitle = findViewById(R.id.textView_title);
    }
}