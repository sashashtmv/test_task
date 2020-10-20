package com.sashashtmv.myshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.myshop.DrawerActivity;
import com.sashashtmv.myshop.LoginActivity;
import com.sashashtmv.myshop.R;
import com.sashashtmv.myshop.SignUpActivity;

public class ThreeFragment extends Fragment {
    TextView signIn;
    TextView signUp;
    TextView skip;
    TextView textDescription;
    ImageView imageView;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        signIn = view.findViewById(R.id.tv_sign_in);
        signUp = view.findViewById(R.id.tv_sign_up);
        skip = view.findViewById(R.id.tv_skip);
        textDescription = view.findViewById(R.id.text_description);
        imageView = view.findViewById(R.id.iv_image);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }


}
