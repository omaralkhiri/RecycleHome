package com.example.recyclehome.ui.home_Advertiser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.recyclehome.R;
import com.example.recyclehome.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_home,container,false);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}