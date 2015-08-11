package com.yashdalfthegray.songaday.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yashdalfthegray.songaday.Models.DrawerItem;
import com.yashdalfthegray.songaday.R;

import java.util.List;

/**
 * Created by Yash Kulshrestha on 8/10/2015.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private String mTitle;

    private Context mContext;

    private int mIcons[];

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_bar_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType);

            return vhItem;
        }
        else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);

            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NavDrawerAdapter.ViewHolder holder, int position) {
        if(holder.holderId == 1) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position -1]);
        }
        else{
            holder.title.setText(mTitle);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;

        TextView textView;
        ImageView imageView;
        TextView title;

        public ViewHolder(View itemView,int ViewType) {
            super(itemView);

            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderId = 1;
            }
            else{
                title = (TextView) itemView.findViewById(R.id.title);
                holderId = 0;
            }
        }
    }

    public NavDrawerAdapter(List<DrawerItem> dataList, Context context, String title){
        mNavTitles = new String[dataList.size()];
        mIcons = new int[dataList.size()];

        for (int i = 0; i < dataList.size(); i++){
            mNavTitles[i] = dataList.get(i).getItemName();
            mIcons[i] = dataList.get(i).getImgResId();
        }

        mContext = context;
        mTitle = title;
    }
}
