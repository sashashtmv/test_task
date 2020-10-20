package com.sashashtmv.myshop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sashashtmv.myshop.R;
import com.sashashtmv.myshop.helper.NetworkConnection;
import com.sashashtmv.myshop.model.ModelGoods;

public class BuyFragment extends Fragment {

    private ImageView pImage;
    private TextView name;
    private TextView price;
    private TextView Desc_text;
    private Button bt_buy;
    private String pName;
    private static ModelGoods mItem;

    @Override
    public void onStart() {
        super.onStart();
        NetworkConnection networkConnection = new NetworkConnection();
        if (networkConnection.isConnectedToInternet(getActivity())
                || networkConnection.isConnectedToMobileNetwork(getActivity())
                || networkConnection.isConnectedToWifi(getActivity())) {

        } else {
            networkConnection.showNoInternetAvailableErrorDialog(getActivity());
            return;
        }
    }

    public static BuyFragment newInstance(ModelGoods item) {
        BuyFragment fragment = new BuyFragment();
        mItem = item;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy, container, false);
        name = (TextView) v.findViewById(R.id.product_name);
        price = (TextView) v.findViewById(R.id.product_price);
        bt_buy = (Button) v.findViewById(R.id.bt_buy);
        pImage = (ImageView) v.findViewById(R.id.product_image);
        Desc_text = (TextView) v.findViewById(R.id.Description);
        pName = mItem.getName();
        String pImageUrl = mItem.getImageUrl();
        String pPrice = mItem.getPrice();
        String desc = mItem.getDesc();
        name.setText(pName);
        price.setText(pPrice + " $");
        Desc_text.setText(desc);

        if (pImageUrl != null) {
            String photoUrl = pImageUrl;
            Glide.with(this)
                    .load(photoUrl)
                    .into(pImage);
        }

        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.question_in_dialog);
                builder.setMessage(R.string.description_dialog);

                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), R.string.order_in_bucket, Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
