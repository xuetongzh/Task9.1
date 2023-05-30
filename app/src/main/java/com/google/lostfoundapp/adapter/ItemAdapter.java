package com.google.lostfoundapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.lostfoundapp.R;
import com.google.lostfoundapp.RemoveActivity;
import com.google.lostfoundapp.bean.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Item> data = new ArrayList<>();
    private List<Item> mFilterList = new ArrayList<>();

    public ItemAdapter(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
        this.mFilterList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mFilterList.get(position);

        holder.infoBean = item;
        holder.itemName.setText(item.getType() + " " + item.getName());

        // 为每个Item添加点击事件
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("itemId", item.getItemId());
            Intent intent = new Intent(context, RemoveActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mFilterList == null) {
            return 0;
        }
        return mFilterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mFilterList = data;
                } else {
                    List<Item> filteredList = new ArrayList<>();
                    for (Item infoBean : data) {
                        //这里根据需求，添加匹配规则
                        if (infoBean.getName().contains(charString)) {
                            filteredList.add(infoBean);
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<Item>) filterResults.values;
                //刷新数据
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView itemName;

        public Item infoBean;

        public ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.itemName = itemView.findViewById(R.id.itemName);
        }
    }
}