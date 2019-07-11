package com.framgia.gotosalon.screen.detail;

import com.framgia.gotosalon.data.model.Salon;

public interface DetailContract {
    interface View {
        void onCheckExistSucced();
    }

    interface Presenter<View> {
        void setView(DetailContract.View view);

        void checkSalonExist(Salon salon);
    }
}
