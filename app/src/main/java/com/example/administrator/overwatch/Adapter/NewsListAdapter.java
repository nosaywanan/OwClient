package com.example.administrator.overwatch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.overwatch.R;

import java.util.List;

import ow.bean.OwNewsItem;

public class NewsListAdapter extends BaseAdapter
{
    Context context;
    List<OwNewsItem> items;
    LayoutInflater mInflater;

    public NewsListAdapter(Context context, List<OwNewsItem> items)
    {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;

    }

    @Override
    public int getCount()
    {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.new_list_item, parent, false);
        ViewHolder holder = null;

        holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.news_title);
        holder.date = (TextView) convertView.findViewById(R.id.news_date);
        holder.content = (TextView) convertView.findViewById(R.id.news_content);
        holder.imageView = (ImageView) convertView.findViewById(R.id.news_img);
        if (items.get(position).getImgBitmap() != null)
            holder.imageView.setImageBitmap(items.get(position).getImgBitmap());
        holder.content.setText(items.get(position).getContent());
        holder.title.setText(items.get(position).getTitle());
        holder.date.setText(items.get(position).getDate());
        //convertView.setTag(holder);


        return convertView;
    }

    class ViewHolder
    {
        ImageView imageView;
        TextView title;
        TextView content;
        TextView date;
    }
}
