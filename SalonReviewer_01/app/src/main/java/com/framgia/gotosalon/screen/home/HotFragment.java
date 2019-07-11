package com.framgia.gotosalon.screen.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.adapter.SalonAdapter;
import com.framgia.gotosalon.screen.base.BaseFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HotFragment extends BaseFragment implements HomeContract.View {
    private static final String TAG = "HotFragment";
    private List<Salon> mSalons;
    private SalonAdapter mAdapter;
    private ProgressDialog mDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initComponent(View view) {
        mSalons = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SalonAdapter(mSalons, getContext());
        recyclerView.setAdapter(mAdapter);
        mDialog = new ProgressDialog(getContext());
    }

    @Override
    protected void initData() {
        Log.d(TAG, "onCreateView");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        HomeContract.Presenter presenter = new HomePresenter(SalonRepository.getInstance(
                SalonRemoteDataSource.getInstance(databaseReference, storageReference)));
        presenter.setView(this);
        presenter.getHotSalonFromServer();
    }


    @Override
    public void onGetDataSucced(List<Salon> salons) {
        mAdapter.addData(salons);
        mDialog.hide();
    }

    @Override
    public void onGetDataFailed() {
        mDialog.hide();
    }

    @Override
    public void onProgress() {
        showProgressDialog(mDialog);
    }

    @Override
    public void onCheckUserIdFailed() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}
