package com.sashashtmv.myshop.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sashashtmv.myshop.R;
import com.sashashtmv.myshop.adapter.GoodsListAdapter;
import com.sashashtmv.myshop.model.ModelGoods;

import java.util.ArrayList;
import java.util.List;

public class ListGoodsFragment extends Fragment {

    static String mTitle;

    private static GoodsListAdapter mAdapter;
    private static List<ModelGoods> mList;
    private SearchView mSearchView;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    TextView tvTitle;
    Toolbar toolbar;


    public ListGoodsFragment() {
        // Required empty public constructor
    }

    public static ListGoodsFragment newInstance(String title, List<ModelGoods> list, GoodsListAdapter adapter) {
        ListGoodsFragment fragment = new ListGoodsFragment();
        mTitle = title;
        mList = list;
        mAdapter = adapter;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_goods, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (toolbar != null) {
            toolbar.setTitle(R.string.title);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setTitle("My title");
        }
        mSearchView = view.findViewById(R.id.search_view);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);

        mRecyclerView = view.findViewById(R.id.rvShopList);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findGoods(newText);
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tvTitle.setText(mTitle);
                return false;
            }
        });
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("");
            }
        });
        return view;
    }

    public void findGoods(String title) {
        if(mAdapter != null && mList != null) {
            mAdapter.removeAllItems();
            List<ModelGoods> goods = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getName().toLowerCase().contains(title.toLowerCase())) {
                    goods.add(mList.get(i));
                }
                mAdapter.addGoods(goods);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter != null)
            mAdapter.sortList(mTitle, mList);
    }
}
