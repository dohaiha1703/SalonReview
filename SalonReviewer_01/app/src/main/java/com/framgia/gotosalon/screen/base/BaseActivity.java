package com.framgia.gotosalon.screen.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.framgia.gotosalon.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String MSG_PLEASE_WAIT = "please wait few second";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initComponent();
        initData();
    }

    abstract protected int getLayoutResource();

    abstract protected void initComponent();

    abstract protected void initData();

    protected void navigateScreen(Context context, Class<?> activity) {
        startActivity(new Intent(context, activity));
    }

    protected void showProgressDialog(ProgressDialog dialog) {
        dialog.setCancelable(true);
        dialog.setMessage(this.getResources().getString(R.string.msg_please_wait_few_second));
        dialog.show();
    }

}
