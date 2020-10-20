package com.sashashtmv.myshop.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.myshop.R;

public class OneFragment extends Fragment {
    TextView textTitle;
    TextView textDescription;
    ImageView imageView;


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        textTitle = view.findViewById(R.id.text_title);
        textDescription = view.findViewById(R.id.text_description);
        imageView = view.findViewById(R.id.iv_image);
        return view;
    }

}
