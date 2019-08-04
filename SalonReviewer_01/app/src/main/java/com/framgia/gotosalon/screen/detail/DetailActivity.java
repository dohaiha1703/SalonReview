package com.framgia.gotosalon.screen.detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.framgia.gotosalon.screen.rate.RateSalon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends BaseActivity implements View.OnClickListener, DetailContract.View {
    private static final String INTENT_KEY = "SALON_INTENT";
    TextView textSalonName;
    TextView textSalonAddress;
    TextView textSalonOpen;
    TextView textSalonClose;
    TextView textSalonViews;
    TextView textSalonEmail;
    TextView textSalonPhone;
    TextView textSalonDescription;
    ImageView imageView;
    private Salon mSalon;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail_salon;
    }

    @Override
    protected void initComponent() {
        mSalon = new Salon();
        imageView = findViewById(R.id.image_salon);
        textSalonName = findViewById(R.id.text_salon_name);
        textSalonAddress = findViewById(R.id.text_salon_address);
        textSalonOpen = findViewById(R.id.text_salon_open);
        textSalonClose = findViewById(R.id.text_salon_close);
        textSalonViews = findViewById(R.id.text_salon_views);
        textSalonEmail = findViewById(R.id.text_salon_email);
        textSalonPhone = findViewById(R.id.text_salon_phone);
        textSalonDescription = findViewById(R.id.text_salon_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.text_navigate_address).setOnClickListener(this);
        findViewById(R.id.text_navigate_email).setOnClickListener(this);
        findViewById(R.id.text_navigate_phone).setOnClickListener(this);
        findViewById(R.id.textViewRate).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        mSalon = (Salon) getIntent().getSerializableExtra(INTENT_KEY);
        DetailContract.Presenter presenter = new DetailPresenter(SalonRepository.getInstance(
                SalonRemoteDataSource.getInstance(databaseReference, storageReference)));
        presenter.setView(this);
        presenter.checkSalonExist(mSalon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_navigate_address:
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + mSalon.getSalonAddress());
                Intent intentMap = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intentMap.setPackage("com.google.android.apps.maps");
                startActivity(intentMap);
                break;
            case R.id.text_navigate_email:
                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse("mailto:"));
                String[] adrress = {mSalon.getSalonEmail()};
                intentEmail.putExtra(Intent.EXTRA_EMAIL, adrress);
                if (intentEmail.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentEmail);
                }
                break;
            case R.id.text_navigate_phone:
                Intent intentPhone = new Intent(Intent.ACTION_DIAL);
                intentPhone.setData(Uri.parse("tel:" + mSalon.getSalonPhone()));
                if (intentPhone.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentPhone);
                }
                break;
            case R.id.textViewRate:
                Intent intent = new Intent(DetailActivity.this, RateSalon.class);
                intent.putExtra("salon", mSalon);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckExistSucced() {
        Glide.with(this).load(mSalon.getImageUrl()).into(imageView);
        textSalonDescription.setText(mSalon.getSalonDescription());
        textSalonPhone.setText(mSalon.getSalonPhone());
        textSalonEmail.setText(mSalon.getSalonEmail());
        textSalonOpen.setText(mSalon.getSalonOpenTime());
        textSalonClose.setText(mSalon.getSalonCloseTime());
        textSalonName.setText(mSalon.getSalonName());
        textSalonViews.setText(mSalon.getSalonView() + getResources().getString(R.string.title_views));
        textSalonAddress.setText(mSalon.getSalonAddress());
    }
}
