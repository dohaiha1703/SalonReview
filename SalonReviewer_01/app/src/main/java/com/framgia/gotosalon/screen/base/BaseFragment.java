package com.framgia.gotosalon.screen.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    private static final String MSG_PLEASE_WAIT = "please wait few second";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        initComponent(view);
        initData();
        return view;
    }

    abstract protected int getLayoutResource();

    abstract protected void initComponent(View view);

    abstract protected void initData();

    protected void showProgressDialog(ProgressDialog dialog) {
        dialog.setCancelable(true);
        dialog.setMessage(MSG_PLEASE_WAIT);
        dialog.show();
    }

    protected String getDataSearch(Fragment fragment, String key) {
        return fragment.getArguments().getString(key);
    }
}
