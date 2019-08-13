package com.framgia.gotosalon.screen.rate;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RateSalonActivity extends BaseActivity implements RateContract.View {

    private Salon mSalon;
    private ImageView star1, star2, star3, star4, star5;
    private int rating;
    private RateContract.Presenter mPresenter;

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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        mPresenter = new RatePresenter(SalonRepository.getInstance(SalonRemoteDataSource.
                getInstance(databaseReference, storageReference)));
        mPresenter.setView(this);

        findViewById(R.id.button_submit_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double allRatePoint = mSalon.getCountRate() * mSalon.getRating();
                Double avgRate = (allRatePoint + rating) / (mSalon.getCountRate() + 1);
                mSalon.setRating(avgRate);
                mSalon.setCountRate(mSalon.getCountRate() + 1);
                mPresenter.updateSalon(mSalon.getSalonId(), mSalon);
            }
        });

        findViewById(R.id.button_cancel_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        mSalon = (Salon) getIntent().getSerializableExtra("salon");

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
                Log.d("ha123", rating + "");
                break;
            case R.id.imageView2:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 2;
                Log.d("ha123", rating + "");
                break;
            case R.id.imageView3:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_border_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 3;
                Log.d("ha123", rating + "");
                break;
            case R.id.imageView4:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_border_orange_24dp);
                rating = 4;
                Log.d("ha123", rating + "");
                break;
            case R.id.imageView5:
                star1.setImageResource(R.drawable.ic_star_orange_24dp);
                star2.setImageResource(R.drawable.ic_star_orange_24dp);
                star3.setImageResource(R.drawable.ic_star_orange_24dp);
                star4.setImageResource(R.drawable.ic_star_orange_24dp);
                star5.setImageResource(R.drawable.ic_star_orange_24dp);
                rating = 5;
                Log.d("ha123", rating + "");
                break;
        }
    }

    @Override
    public void onUpdateProgress() {

    }

    @Override
    public void onUpdatehSalonSuccess() {
        Toast.makeText(this, "rate success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdatehSalonFailed() {
        Toast.makeText(this, "rate failed", Toast.LENGTH_SHORT).show();
    }
}
