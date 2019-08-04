package com.framgia.gotosalon.screen.rate;

import android.net.Uri;

import com.framgia.gotosalon.data.model.Salon;

public interface RateContract {
    interface View {
        void onUpdateProgress();

        void onUpdatehSalonSuccess();

        void onUpdatehSalonFailed();
    }

    interface Presenter<View> {
        void setView(RateContract.View view);

        boolean validateUpdateSalon(Salon salon);

        void updateSalon(String child, Salon salon);
    }
}
