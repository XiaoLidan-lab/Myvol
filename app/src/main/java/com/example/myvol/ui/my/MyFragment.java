package com.example.myvol.ui.my;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myvol.MainActivity;
import com.example.myvol.databinding.FragmentMyBinding;

public class MyFragment extends Fragment {

    private FragmentMyBinding binding;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyViewModel MyViewModel =
                new ViewModelProvider(this).get(MyViewModel.class);

        sp = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        editor = sp.edit();
        String phone = sp.getString("phone", "");
        String username = sp.getString("username", "");
        binding = FragmentMyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.tvUserName.setText(username);
        binding.tvUserPhone.setText(phone);
        binding.rlExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        //我的活动
        binding.rlActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyActivity.class);
                startActivity(intent);
            }
        });
        //我的团队
        binding.rlTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTeamActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}