package Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopgiay.MainFragActivity;
import com.example.shopgiay.R;

import java.util.ArrayList;

import Adapter.ChatRVAdapter;
import MyInterface.RetrofitAPI;
import ObjectClass.ChatsModal;
import ObjectClass.MsgModal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SupportFragment extends Fragment {

    private View mView;
    private MainFragActivity mainFragActivity;
    private RecyclerView chatsRV;
    private ImageButton sendMsgFAB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_support,container,false);
        mainFragActivity = (MainFragActivity) getActivity();
        anhXa();

        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList, mainFragActivity);
        LinearLayoutManager manager = new LinearLayoutManager(mainFragActivity);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(mainFragActivity, "Xin hãy nhập ", Toast.LENGTH_LONG).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }

        });

        return mView;
    }

    private void getResponse(String message) {
        chatsModalArrayList.add(new ChatsModal(message, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=168319&key=y5SlaWvj2IEcnGMJ&uid=[uid]&msg=" + message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                MsgModal modal = response.body();
                chatsModalArrayList.add(new ChatsModal(modal.getCnt(), BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question " + t, BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }

    private void anhXa() {
        chatsRV = mView.findViewById(R.id.idRVChats);
        userMsgEdt = mView.findViewById(R.id.idEdtMessage);
        sendMsgFAB = mView.findViewById(R.id.idIBSend);
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
