package Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.shopgiay.AmountUsedActivity;
import com.example.shopgiay.ComfirmationActivity;
import com.example.shopgiay.DeliveringActivity;
import com.example.shopgiay.HistoryActivity;
import com.example.shopgiay.IntroduceActivity;
import com.example.shopgiay.LoginActivity;
import com.example.shopgiay.MainFragActivity;
import com.example.shopgiay.R;
import com.example.shopgiay.UserPageActivity;

import java.util.ArrayList;

import database.DataOfUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {
    private View mView;
    private MainFragActivity mainFragActivity;
    private ListView lvRequestUser,lvRequestUserFake;
    private ArrayList<String> listRequests,listRequestsFake;
    private ArrayAdapter adapter,adapterFake;
    private CircleImageView imgAvata;
    private TextView tvBought, tvDelivering, tvConfirmation,tvCusName,tvCusNumber;
    private SharedPreferences sharedPreferences;
    private DataOfUser dataOfUser;
    private LinearLayout layoutDonHangCuaToi;
    private RelativeLayout layoutThongTinKhachHang,layoutThongTinKhachHangFake;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        mainFragActivity = (MainFragActivity) getActivity();
        sharedPreferences = mainFragActivity.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        dataOfUser = new DataOfUser();
        mediaPlayer = MediaPlayer.create(mainFragActivity, R.raw.coi_bao_dong);
        anhXa();
        if (dataOfUser.idUser==""){
            mediaPlayer.start();
            layoutDonHangCuaToi.setVisibility(View.GONE);
            lvRequestUser.setVisibility(View.GONE);
            layoutThongTinKhachHang.setVisibility(View.GONE);
            layoutThongTinKhachHangFake.setVisibility(View.VISIBLE);
            lvRequestUserFake.setVisibility(View.VISIBLE);
            AlertDialog.Builder dialog = new AlertDialog.Builder(mainFragActivity);
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
                    startActivity(new Intent(mainFragActivity,LoginActivity.class));
                    mediaPlayer.pause();
                }
            });

            dialog.show();
        }else{
            layoutDonHangCuaToi.setVisibility(View.VISIBLE);
            lvRequestUser.setVisibility(View.VISIBLE);
            layoutThongTinKhachHang.setVisibility(View.VISIBLE);
            layoutThongTinKhachHangFake.setVisibility(View.GONE);
            lvRequestUserFake.setVisibility(View.GONE);
            tvCusName.setText("Nguyễn Văn Hiếu");
            tvCusNumber.setText("0794532744");
        }


        listRequests = new ArrayList<String>();
        listRequests.add("Giới thiệu");
        listRequests.add("Tổng giá trị đã sử dụng");
        listRequests.add("Đăng xuất");
        adapter = new ArrayAdapter(mainFragActivity, android.R.layout.simple_list_item_1, listRequests);
        lvRequestUser.setAdapter(adapter);

        lvRequestUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i la chi Index, bat dau = 0
                switch (listRequests.get(i)) {
                    case "Giới thiệu":
                        startActivity(new Intent(mainFragActivity, IntroduceActivity.class));
                        break;
                    case "Tổng giá trị đã sử dụng":
                        startActivity(new Intent(mainFragActivity, AmountUsedActivity.class));
                        break;
                    case "Đăng xuất":
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Toast.makeText(mainFragActivity, "logout", Toast.LENGTH_SHORT).show();
                        editor.putString("idUser", "");
                        editor.commit();
                        startActivity(new Intent(mainFragActivity, LoginActivity.class));
                        mainFragActivity.finish();
                        break;
                    default:
                        break;
                }
            }
        });

        listRequestsFake = new ArrayList<String>();
        listRequestsFake.add("Giới thiệu");
        listRequestsFake.add("Đăng nhập");
        adapterFake = new ArrayAdapter(mainFragActivity, android.R.layout.simple_list_item_1, listRequestsFake);
        lvRequestUserFake.setAdapter(adapterFake);

        lvRequestUserFake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (listRequestsFake.get(i)){
                    case "Giới thiệu":
                        startActivity(new Intent(mainFragActivity, IntroduceActivity.class));
                        break;
                    case "Đăng nhập":
                        startActivity(new Intent(mainFragActivity, LoginActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

        tvBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainFragActivity, HistoryActivity.class));
            }
        });
        tvConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainFragActivity, ComfirmationActivity.class));
            }
        });
        tvDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainFragActivity, DeliveringActivity.class));
            }
        });

        imgAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainFragActivity, UserPageActivity.class));
            }
        });

        return mView;
    }

    private void anhXa() {
        lvRequestUser = mView.findViewById(R.id.listView_requestAtUser);
        imgAvata = mView.findViewById(R.id.imageView_avata);
        tvBought = mView.findViewById(R.id.textView_bought);
        tvConfirmation = mView.findViewById(R.id.textView_confirmation);
        tvDelivering = mView.findViewById(R.id.textView_delivering);
        tvCusName = mView.findViewById(R.id.textView_CusName);
        tvCusNumber = mView.findViewById(R.id.textView_CusNumber);
        layoutDonHangCuaToi=mView.findViewById(R.id.layout_donHangCuaToi);
        lvRequestUserFake=mView.findViewById(R.id.listView_requestAtUserFake);
        layoutThongTinKhachHang=mView.findViewById(R.id.layout_thongTinKhachHang);
        layoutThongTinKhachHangFake=mView.findViewById(R.id.layout_thongTinKhachHangfake);
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
