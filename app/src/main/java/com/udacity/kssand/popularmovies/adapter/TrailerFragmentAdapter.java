package com.udacity.kssand.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.kssand.popularmovies.R;
import com.udacity.kssand.popularmovies.model.Trailer;
import com.udacity.kssand.popularmovies.util.Constants;

import java.util.List;

/**
 * Created by kssand on 24-Jan-16.
 */
public class TrailerFragmentAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Trailer> trailerList;

    public TrailerFragmentAdapter(Context context, List<Trailer> trailerList) {
        this.mContext = context;
        this.trailerList = trailerList;
    }

    @Override
    public int getCount() {
        return trailerList.size();
    }

    @Override
    public Object getItem(int i) {
        return trailerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.trailer_list_item, parent, false);
            holder = new ViewHolder();
            holder.videoIV = (ImageView) convertView.findViewById(R.id.videoIV);
            holder.trailerTV = (TextView) convertView.findViewById(R.id.trailerTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.trailerTV.setText("Trailer " + (i+1));

        if(trailerList.get(i).getSite().equalsIgnoreCase("YouTube")) {

            Glide.with(mContext)
                    .load(Constants.YOU_TUBE_IMAGE_BASE_URL + trailerList.get(i).getKey() + "/0.jpg")
                    .fitCenter()
                    .placeholder(R.color.trailer_color)
                    .into(holder.videoIV);
        }

        return convertView;
    }

    static class ViewHolder{
        ImageView videoIV;
        TextView trailerTV;

    }
}
