package com.framgia.gotosalon.screen.manager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.adapter.SalonAdapter;
import com.framgia.gotosalon.screen.adapter.SalonManagerAdapter;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.framgia.gotosalon.screen.detail.DetailActivity;
import com.framgia.gotosalon.screen.publish.PublishActivity;
import com.framgia.gotosalon.screen.update.UpdateActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends BaseActivity implements View.OnClickListener, ManagerContract.View, SalonManagerAdapter.OnItemClickListener {
    private static final String EXTRA_USER_KEY = "USER_ID";
    private static final String EXTRA_SALON_KEY = "SALON_INTENT";
    private String mUserId;
    private List<Salon> mSalons, mSalonsSearch;
    private SalonManagerAdapter mAdapter;
    private ManagerContract.Presenter mPresenter;
    private EditText mEditTextSearch;
    private RecyclerView mRecyclerView;
    private ProgressDialog mDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initComponent() {
        findViewById(R.id.float_button).setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                }catch (Exception e){
                    Log.d("aaa", "back failed");
                }
            }
        });
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.image_search).setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mEditTextSearch = findViewById(R.id.edit_search);

        mSalons = new ArrayList<>();
        mSalonsSearch = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ManagerActivity.this));
        mAdapter = new SalonManagerAdapter(ManagerActivity.this, mSalons);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mUserId = getIntent().getStringExtra(EXTRA_USER_KEY);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        mPresenter = new ManagerPresenter(SalonRepository.getInstance(SalonRemoteDataSource.getInstance(reference, storageReference)));
        mPresenter.setView(this);
        mPresenter.getSalons(mUserId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button:
                Intent intent = new Intent(this, PublishActivity.class);
                intent.putExtra(EXTRA_USER_KEY, mUserId);
                startActivity(intent);
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.image_search:
                mSalons.clear();
                String data = mEditTextSearch.getText().toString().trim();
                for (Salon x : mSalonsSearch) {
                    if (x.getSalonName().contains(data)) {
                        mSalons.add(x);
                        Log.d("aaa", "onClick: ");
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onGetDataSucced(List<Salon> salons) {
        mAdapter.setData(salons);
        mSalonsSearch = salons;
        mDialog.hide();
    }

    @Override
    public void onGetDataFailed() {
        Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
        mDialog.hide();
    }

    @Override
    public void onProgress() {
        showProgressDialog(mDialog);
    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View itemView, final int position) {
        switch (itemView.getId()) {
            case R.id.image_salon:
                startActivity(SalonAdapter.getDetailIntent(ManagerActivity.this, mSalons.get(position)));
                break;
            case R.id.image_edit:
                Intent intentEdit = new Intent(ManagerActivity.this, UpdateActivity.class);
                intentEdit.putExtra(EXTRA_SALON_KEY, (Serializable) mSalons.get(position));
                startActivity(intentEdit);
                break;
            case R.id.image_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(ManagerActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("This action will remove your salon on guest network");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StorageReference storageReferenceUrl = FirebaseStorage.getInstance().
                                getReferenceFromUrl(mSalons.get(position).getImageUrl());
                        try {
                            mPresenter.deleteSalon(storageReferenceUrl, mSalons.get(position));
                            Log.d("aaa", "delete " + position);
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.d("aaa", " delete failed" + position);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ManagerActivity.this, "You choose no", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
        }
    }
}
