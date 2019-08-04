package com.framgia.gotosalon.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.framgia.gotosalon.screen.manager.ManagerActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener, HomeContract.View {
    private static final String INTENT_KEY = "USER_ID";
    private String mUserId;
    private ImageView mImageMenu;
    private EditText mEditTextSearch;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initComponent() {
        ViewPager viewPager = findViewById(R.id.view_pager_home);
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(), getApplicationContext());
        TabLayout tabLayout = findViewById(R.id.tab_home);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        mEditTextSearch = findViewById(R.id.edit_search);
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.image_search).setOnClickListener(this);
        mImageMenu = findViewById(R.id.image_menu);
        mImageMenu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mUserId = getIntent().getStringExtra(INTENT_KEY);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        HomeContract.Presenter presenter = new HomePresenter(SalonRepository.getInstance(SalonRemoteDataSource.getInstance(databaseReference, storageReference)));
        presenter.setView(this);
        presenter.checkUserId(mUserId);
        if (mUserId == null){
            mImageMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_menu:
                if (mUserId != null){
                    Intent intent = new Intent(this, ManagerActivity.class);
                    intent.putExtra(INTENT_KEY, mUserId);
                    startActivity(intent);
                    break;
                }
                Toast.makeText(this, "Sign up to use this function", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_search:
                Bundle bundle = new Bundle();
                bundle.putString("data", mEditTextSearch.getText().toString().trim());
                NewFragment newFragment = new NewFragment();
                HotFragment hotFragment = new HotFragment();
                newFragment.setArguments(bundle);
                hotFragment.setArguments(bundle);
                break;
        }
    }

    @Override
    public void onGetDataSucced(List<Salon> salons) {

    }

    @Override
    public void onGetDataFailed() {

    }

    @Override
    public void onProgress() {

    }

    @Override
    public void onCheckUserIdFailed() {
        mUserId = null;
    }
}
