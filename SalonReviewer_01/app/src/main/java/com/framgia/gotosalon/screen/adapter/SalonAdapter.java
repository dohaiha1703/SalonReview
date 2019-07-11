package com.framgia.gotosalon.screen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.screen.detail.DetailActivity;

import java.io.Serializable;
import java.util.List;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.ViewHolder> {
    private static final String EXTRA_SALON_KEY = "SALON_INTENT";
    private static final int START_ITEM = 0;
    private List<Salon> mSalons;
    private Context mContext;

    public SalonAdapter(List<Salon> salons, Context context) {
        mSalons = salons;
        mContext = context;
    }

    public void addData(List<Salon> salons) {
        mSalons.clear();
        mSalons.addAll(salons);
        notifyItemRangeChanged(START_ITEM, mSalons.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_salon, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Salon salon = mSalons.get(i);
        Glide.with(mContext).load(salon.getImageUrl()).into(viewHolder.imageSalon);
        if (salon.getSalonView() > 1) {
            viewHolder.textSalonView.setText(salon.getSalonView() +
                    mContext.getResources().getString(R.string.title_views));
        } else {
            viewHolder.textSalonView.setText(salon.getSalonView() +
                    mContext.getResources().getString(R.string.title_view));
        }

        viewHolder.textSalonAddress.setText(salon.getSalonAddress());
        viewHolder.textSalonNane.setText(salon.getSalonName());
        viewHolder.imageSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(getDetailIntent(mContext, mSalons.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSalons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageSalon;
        public TextView textSalonNane;
        public TextView textSalonView;
        public TextView textSalonAddress;

        public ViewHolder(View view) {
            super(view);
            imageSalon = view.findViewById(R.id.imageSalon);
            textSalonNane = view.findViewById(R.id.text_salon_name);
            textSalonAddress = view.findViewById(R.id.text_salon_address);
            textSalonView = view.findViewById(R.id.text_view_number);
        }
    }

    public static Intent getDetailIntent(Context context, Salon salon) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_SALON_KEY, (Serializable) salon);
        return intent;
    }
}
