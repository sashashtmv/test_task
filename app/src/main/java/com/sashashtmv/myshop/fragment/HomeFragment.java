package com.sashashtmv.myshop.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sashashtmv.myshop.R;


public class HomeFragment extends Fragment {

    private Callbacks mCallbacks;
    private Button jewelry;
    private Button beauty;
    private Button electric;
    private Button travel;
    private Button all_for_home;
    private Button entertainment;
    private Button food_delivery;
    private Button services;
    private Button finance;
    private Button closing;
    private Button china_goods;
    private Button foreign_brand;
    private Button all_goods;

    public interface Callbacks{
        void onCreateAllGoodsFragment(String title);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        jewelry = view.findViewById(R.id.bt_jewelry);
        beauty = view.findViewById(R.id.bt_beauty);
        electric = view.findViewById(R.id.bt_electric);
        travel = view.findViewById(R.id.bt_travel);
        all_for_home = view.findViewById(R.id.bt_all_for_home);
        entertainment = view.findViewById(R.id.bt_entertainment);
        food_delivery = view.findViewById(R.id.bt_food_delivery);
        services = view.findViewById(R.id.bt_services);
        finance = view.findViewById(R.id.bt_finance);
        closing = view.findViewById(R.id.bt_closing);
        china_goods = view.findViewById(R.id.bt_china_shop);
        foreign_brand = view.findViewById(R.id.bt_foreign_shop);
        all_goods = view.findViewById(R.id.bt_all_shops);

        jewelry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Украшения и Аксесуары");
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Красота и Здоровье");
            }
        });

        electric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Электроника и Техника");
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Путешествия");
            }
        });

        all_for_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Все для дома");
            }
        });

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Досуг и Развлечения");
            }
        });

        food_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Доставка еды");
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Услуги");
            }
        });

        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Страхование и финансы");
            }
        });

        closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Одежда и Обувь");
            }
        });

        china_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Китайские товары");
            }
        });

        foreign_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Иностранные бренды");
            }
        });

        all_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onCreateAllGoodsFragment("Все товары");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
