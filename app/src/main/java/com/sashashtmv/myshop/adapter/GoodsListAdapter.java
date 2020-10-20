package com.sashashtmv.myshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sashashtmv.myshop.R;
import com.sashashtmv.myshop.fragment.BuyFragment;
import com.sashashtmv.myshop.helper.PreferenceHelper;
import com.sashashtmv.myshop.model.ModelGoods;

import java.util.ArrayList;
import java.util.List;


public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder> {

    private List<ModelGoods> mGoods;
    protected ModelGoods item;
    private Context mContext;
    private PreferenceHelper mPreferenceHelper;

    public GoodsListAdapter(Context context, List<ModelGoods> list) {
        mContext = context;
        mGoods = list;
        PreferenceHelper.getInstance().init(mContext);
        mPreferenceHelper = PreferenceHelper.getInstance();
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final GoodsViewHolder holder, final int position) {
        item = mGoods.get(position);

        Glide.with(mContext)
                .load(String.valueOf(item.getImageUrl()))
                .into(holder.imageView);


        holder.price.setText(item.getPrice() + " $");
        holder.nameShop.setText(item.getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = mGoods.get(position);
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, BuyFragment.newInstance(item)).addToBackStack(null).commit();
            }
        });

        if (mPreferenceHelper.getString(item.getName()).equals("true"))
            item.setLove(true);
        if (item.isLove()) {
            holder.love.setImageResource(R.drawable.love_red);
        } else {
            holder.love.setImageResource(R.drawable.love);
        }

        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getPosition();
                item = mGoods.get(pos);
                if (!item.isLove()) {
                    holder.love.setImageResource(R.drawable.love_red);
                    item.setLove(true);
                    mPreferenceHelper.putString(item.getName(), "true");

                } else {
                    holder.love.setImageResource(R.drawable.love);
                    item.setLove(false);
                    mPreferenceHelper.putString(item.getName(), "false");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mGoods != null) {
            return mGoods.size();
        } else return 0;
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameShop;
        TextView price;
        ImageView love;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_id_image);
            love = itemView.findViewById(R.id.iv_like);
            nameShop = itemView.findViewById(R.id.tv_name_goods);
            price = itemView.findViewById(R.id.tv_price);
        }
    }

    public void removeAllItems() {
        if (getItemCount() != 0) {
            mGoods = new ArrayList<>();
            notifyDataSetChanged();

        }
    }

    public void sortList(String title, List<ModelGoods> list) {
        mGoods = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (title.toLowerCase().equals(list.get(i).getCategory().toLowerCase())) {
                mGoods.add(list.get(i));
            }
            if (title.equals("Мои товары") && list.get(i).isLove()) {
                mGoods.add(list.get(i));
            }
            if (title.endsWith("Избранные") && list.get(i).isLove()) {
                mGoods.add(list.get(i));
            }
        }
        if (title.equals("Все товары")) {
            mGoods = list;
        }
        notifyDataSetChanged();
    }

    public void addGoods(List<ModelGoods> list) {
        mGoods = list;
        notifyDataSetChanged();
    }
}
