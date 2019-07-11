package com.framgia.gotosalon.screen.home;

import android.support.annotation.NonNull;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private SalonRepository mRepository;
    private HomeContract.View mView;

    public HomePresenter(SalonRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void getHotSalonFromServer() {
        mView.onProgress();
        mRepository.getSalons(new ValueEventListener() {
            List<Salon> mSalons = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSalons.clear();
                mView.onProgress();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Salon salon = snapshot.getValue(Salon.class);
                    salon.setSalonId(snapshot.getKey());
                    mSalons.add(salon);
                }
                Collections.sort(mSalons, new SalonViewsComparator());
                mView.onGetDataSucced(mSalons);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetDataFailed();
            }
        });
    }

    @Override
    public void checkUserId(String userId) {
        if (userId == null){
            mView.onCheckUserIdFailed();
        }
    }

    @Override
    public void getNewSalonFromServer() {
        final List<Salon> mSalons = new ArrayList<>();
        mRepository.getSalons(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSalons.clear();
                mView.onProgress();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Salon salon = snapshot.getValue(Salon.class);
                    salon.setSalonId(snapshot.getKey());
                    mSalons.add(salon);
                }
                Collections.reverse(mSalons);
                mView.onGetDataSucced(mSalons);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetDataFailed();
            }
        });
    }
}
