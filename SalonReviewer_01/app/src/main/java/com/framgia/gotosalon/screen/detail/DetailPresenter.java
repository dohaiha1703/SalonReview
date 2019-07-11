package com.framgia.gotosalon.screen.detail;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    private SalonRepository mRepository;

    public DetailPresenter(SalonRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void checkSalonExist(Salon salon) {
        if (salon != null) {
            mView.onCheckExistSucced();
            mRepository.addViewsSalon(salon);
        }
    }
}
