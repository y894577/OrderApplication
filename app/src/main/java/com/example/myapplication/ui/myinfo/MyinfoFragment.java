package com.example.myapplication.ui.myinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.ui.notifications.NotificationsViewModel;

public class MyinfoFragment extends Fragment {
    private MyinfoViewModel myinfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myinfoViewModel =
                ViewModelProviders.of(this).get(MyinfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);
        final TextView textView = root.findViewById(R.id.text_myinfo);
        myinfoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
