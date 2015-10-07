package com.example.gridview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGridViewAdapter extends ArrayAdapter<Item> {
    Context context;
    int layoutResourceId;
    ArrayList<Item> data = new ArrayList<>();

    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final RecordHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.progressBar);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        Item item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
//        Picasso.Builder builder = new Picasso.Builder(getContext());
//        builder.listener(new Picasso.Listener() {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
//                Log.e("Picasso Error", uri.toString() + "\r\n" + e.toString());
//            }
//        });
//        Picasso picasso = builder.build();
//        picasso.load(item.getImageUrl())
//                .placeholder(R.drawable.ic_action_android)
//                .into(holder.imageItem);
        Picasso.with(context)
                .load(item.getImageUrl())
                .into(holder.imageItem, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        Log.e("GridView", "Picasso Error");
                    }
                });

        return row;
    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        ProgressBar progressBar;
    }
}
