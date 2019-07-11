package com.framgia.gotosalon.screen.adapter;

import android.content.Context;
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

import java.util.List;

public class SalonManagerAdapter extends RecyclerView.Adapter<SalonManagerAdapter.ViewHolder> {
    private static final int BASE_COUNT = 0;
    private static final int START_ITEM = 0;
    private List<Salon> mSalons;
    private Context mContext;
    private OnItemClickListener mListener;

    public SalonManagerAdapter(Context context, List<Salon> salons) {
        mSalons = salons;
        mContext = context;
    }

    public void setData(List<Salon> salons) {
        mSalons.clear();
        mSalons.addAll(salons);
        notifyDataSetChanged();
//        notifyItemRangeChanged(START_ITEM, mSalons.size());
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_salon_manager, viewGroup, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mContext, mSalons.get(i));
    }

    @Override
    public int getItemCount() {
        return mSalons != null ? mSalons.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageSalon;
        private ImageView mImageEdit;
        private ImageView mImageDelete;
        private TextView mTextSalonNane;
        private TextView mTextSalonView;
        private TextView mTextSalonAddress;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageSalon = itemView.findViewById(R.id.image_salon);
            mImageEdit = itemView.findViewById(R.id.image_edit);
            mImageDelete = itemView.findViewById(R.id.image_delete);
            mTextSalonNane = itemView.findViewById(R.id.text_salon_name);
            mTextSalonAddress = itemView.findViewById(R.id.text_salon_address);
            mTextSalonView = itemView.findViewById(R.id.text_view_number);

            mImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(mImageDelete, position);
                        }
                    }
                }
            });
            mImageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(mImageEdit, position);
                        }
                    }
                }
            });
            mImageSalon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(mImageSalon, position);
                        }
                    }
                }
            });
        }

        private void bindData(final Context context, final Salon salon) {
            Glide.with(context).load(salon.getImageUrl()).into(mImageSalon);
            if (salon.getSalonView() > 1) {
                mTextSalonView.setText(salon.getSalonView() +
                        context.getResources().getString(R.string.title_views));
            } else {
                mTextSalonView.setText(salon.getSalonView() +
                        context.getResources().getString(R.string.title_view));
            }
            mTextSalonAddress.setText(salon.getSalonAddress());
            mTextSalonNane.setText(salon.getSalonName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
}
