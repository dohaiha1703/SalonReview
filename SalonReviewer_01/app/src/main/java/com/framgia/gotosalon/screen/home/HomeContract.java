package com.framgia.gotosalon.screen.home;

import com.framgia.gotosalon.data.model.Salon;

import java.util.List;

public interface HomeContract {
    interface View {
        void onGetDataSucced(List<Salon> salons);

        void onGetDataFailed();

        void onProgress();

        void onCheckUserIdFailed();
    }

    interface Presenter<View> {
        void setView(HomeContract.View view);

        void getNewSalonFromServer();

        void getHotSalonFromServer();

        void checkUserId(String userId);
    }
}
