package com.example.administrator.overwatch.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.overwatch.R;

import java.util.List;

import ow.bean.OwNewsItem;

/**
 * Created by Administrator on 2016-07-25.
 */

public class MyRevAdapter extends RecyclerView.Adapter<MyRevAdapter.MyViewHolder> {
    Context context;
    List<OwNewsItem> items;
    LayoutInflater mInflater;

    public MyRevAdapter(Context context, List<OwNewsItem> items) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viType) {
        View view = mInflater.inflate(R.layout.new_list_item, parent, false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (items.get(position).getImgBitmap() != null)
            holder.imageView.setImageBitmap(items.get(position).getImgBitmap());
        holder.content.setText(items.get(position).getContent());
        holder.title.setText(items.get(position).getTitle());
        holder.date.setText(items.get(position).getDate());
    }
    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
    private  OnRevItemClickListener mListener=null;
    public  void setOnItemClickListener(OnRevItemClickListener listener){
        mListener=listener;
    }
    public  interface OnRevItemClickListener{
        public void OnClick(View view,int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title;
        TextView content;
        TextView date;
        OnRevItemClickListener mItemClickListener;
        public MyViewHolder(View itemView,OnRevItemClickListener itemClickListener) {
            super(itemView);
            mItemClickListener=itemClickListener;
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.news_title);
            date = (TextView) itemView.findViewById(R.id.news_date);
            content = (TextView) itemView.findViewById(R.id.news_content);
            imageView = (ImageView) itemView.findViewById(R.id.news_img);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener!=null)
                mItemClickListener.OnClick(v,getLayoutPosition());
        }
    }
}
