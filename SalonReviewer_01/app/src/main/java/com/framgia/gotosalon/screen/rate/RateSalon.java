package com.framgia.gotosalon.screen.rate;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.screen.base.BaseActivity;

public class RateSalon extends BaseActivity {

    private Salon mSalon;
    private ImageView star1, star2, star3, star4, star5;
    private int rating;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_rating;
    }

    @Override
    protected void initComponent() {
        star1 = findViewById(R.id.imageView1);
        star2 = findViewById(R.id.imageView2);
        star3 = findViewById(R.id.imageView3);
        star4 = findViewById(R.id.imageView4);
        star5 = findViewById(R.id.imageView5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStars(view);
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStars(view);
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStars(view);
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStars(view);
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStars(view);
            }
        });
    }

    @Override
    protected void initData() {
//        mSalon = (Salon) getIntent().getSerializableExtra("salon");
//        Log.d("ha123", mSalon.getSalonName());

    }

    private void setStars(View view) {
        switch (view.getId()) {
            case R.id.imageView1:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 1;
                Log.d("ha123", rating +"");
                break;
            case R.id.imageView2:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 2;
                Log.d("ha123", rating +"");
                break;
            case R.id.imageView3:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 3;
                Log.d("ha123", rating +"");
                break;
            case R.id.imageView4:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 4;
                Log.d("ha123", rating +"");
                break;
            case R.id.imageView5:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_orange_24dp);
                rating = 5;
                Log.d("ha123", rating +"");
                break;
        }
    }
}
