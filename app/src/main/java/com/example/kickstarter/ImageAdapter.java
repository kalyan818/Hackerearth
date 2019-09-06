package com.example.kickstarter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kalya on 6/5/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListner mListener;
    public View img;
    Float x,y;

    public ImageAdapter(Context context, List<Upload> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadcurrent = mUploads.get(position);
        holder.Title.setText(uploadcurrent.getTitle());
        holder.Author.setText(uploadcurrent.getBy());
        holder.Country.setText("Country:" + uploadcurrent.getCountry());
        if(uploadcurrent.getCurrency().equals("usd")){
            holder.AmtPledged.setText("Pledged:$"+uploadcurrent.getAmtpledged());
        }
        if (uploadcurrent.getCurrency().equals("cad")){
            holder.AmtPledged.setText("Pledged:C$"+uploadcurrent.getAmtpledged());
        }
        if(uploadcurrent.getCurrency().equals("eur")){
            holder.AmtPledged.setText("Pledged:€"+uploadcurrent.getAmtpledged());
        }
        if (uploadcurrent.getCurrency().equals("gbp")){
            holder.AmtPledged.setText("Pledged:£"+uploadcurrent.getAmtpledged());
        }
        if(uploadcurrent.getCurrency().equals("aud")){
            holder.AmtPledged.setText("Pledged:A$"+uploadcurrent.getAmtpledged());
        }
        if (uploadcurrent.getCurrency().equals("chf")){
            holder.AmtPledged.setText("Pledged:CHf"+uploadcurrent.getAmtpledged());
        }
        if(uploadcurrent.getCurrency().equals("dkk")){
            holder.AmtPledged.setText("Pledged:Kr."+uploadcurrent.getAmtpledged());
        }
        holder.Author.setText(String.valueOf(uploadcurrent.getBy()));


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        public TextView Title,Author,Country,AmtPledged;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.Title);
            Author = (TextView) itemView.findViewById(R.id.Author);
            Country = (TextView) itemView.findViewById(R.id.Country);
            AmtPledged=(TextView)itemView.findViewById(R.id.AmtPledged);


            itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v) {
            if (mListener!= null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position,v);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("select Action");
            MenuItem dowhatever =menu.add(Menu.NONE,1,1,"do what ever");
            MenuItem delete =menu.add(Menu.NONE,2,2,"delete");

            dowhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!= null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhatEverClicked(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListner{

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void onItemClick(int position, View v);

        void onItemClick(int position);

        void onWhatEverClicked(int position);

        void onDeleteClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listener){
        mListener = listener;
    }
}
