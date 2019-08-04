package com.framgia.gotosalon.screen.rate;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class RatePresenter implements RateContract.Presenter {
    private RateContract.View mView;
    private SalonRepository mRepository;

    public RatePresenter(SalonRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(RateContract.View view) {
        mView = view;
    }

    @Override
    public boolean validateUpdateSalon(Salon salon) {
        return false;
    }

    @Override
    public void updateSalon(String child, Salon salon) {
        mRepository.publishSalons(salon, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                mView.onUpdatehSalonSuccess();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onUpdatehSalonFailed();
            }
        });

    }

}
