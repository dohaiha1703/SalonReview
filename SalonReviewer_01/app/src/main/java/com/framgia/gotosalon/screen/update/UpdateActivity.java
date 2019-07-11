package com.framgia.gotosalon.screen.update;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateActivity extends BaseActivity implements UpdateContract.View, View.OnClickListener {
    private static final int PICK_IMG_REQUEST = 1996;
    private static final String EXTRA_SALON_KEY = "SALON_INTENT";
    private static final String IMAGE_MIME_TYPE = "image/*";
    private EditText mEditTextSalonName;
    private EditText mEditTextSalonAddress;
    private EditText mEditTextSalonPhone;
    private EditText mEditTextSalonEmail;
    private EditText mEditTextSalonDescription;
    private Spinner mSpinnerSalonOpen;
    private Spinner mSpinnerSalonClose;
    private ImageView mImageView;
    private Salon mSalon;
    private Uri mUri;
    private UpdateContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update;
    }

    @Override
    protected void initComponent() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditTextSalonAddress = findViewById(R.id.edit_salon_address);
        mEditTextSalonName = findViewById(R.id.edit_salon_name);
        mEditTextSalonDescription = findViewById(R.id.edit_salon_description);
        mEditTextSalonEmail = findViewById(R.id.edit_salon_email);
        mEditTextSalonPhone = findViewById(R.id.edit_salon_phone);
        mImageView = findViewById(R.id.image_choose);

        mSpinnerSalonOpen = findViewById(R.id.spinner_open);
        mSpinnerSalonOpen = findViewById(R.id.spinner_open);
        mSpinnerSalonOpen.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spiner_time)));

        mSpinnerSalonClose = findViewById(R.id.spinner_close);
        mSpinnerSalonClose = findViewById(R.id.spinner_close);
        mSpinnerSalonClose.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spiner_time)));

        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.image_update).setOnClickListener(this);
        mDialog = new ProgressDialog(this);
    }

    @Override
    protected void initData() {
        if (getIntent().hasExtra(EXTRA_SALON_KEY)) {
            mSalon = (Salon) getIntent().getSerializableExtra(EXTRA_SALON_KEY);
            mEditTextSalonPhone.setText(mSalon.getSalonPhone());
            mEditTextSalonEmail.setText(mSalon.getSalonEmail());
            mEditTextSalonDescription.setText(mSalon.getSalonDescription());
            mEditTextSalonName.setText(mSalon.getSalonName());
            mEditTextSalonAddress.setText(mSalon.getSalonAddress());
            Glide.with(this).load(mSalon.getImageUrl()).into(mImageView);
        } else {
            Toast.makeText(this, "can't update", Toast.LENGTH_SHORT).show();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        mPresenter = new UpdatePresenter(SalonRepository.getInstance(SalonRemoteDataSource.
                getInstance(databaseReference, storageReference)));
        mPresenter.setView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUri = data.getData();
            mImageView.setImageURI(mUri);
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_spick_image), Toast.LENGTH_SHORT).show();
        }
    }

    private void openfileChooser() {
        Intent intent = new Intent();
        intent.setType(IMAGE_MIME_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMG_REQUEST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                mSalon.setSalonName(mEditTextSalonName.getText().toString().trim());
                mSalon.setSalonAddress(mEditTextSalonAddress.getText().toString().trim());
                mSalon.setSalonPhone(mEditTextSalonPhone.getText().toString().trim());
                mSalon.setSalonEmail(mEditTextSalonEmail.getText().toString().trim());
                mSalon.setSalonDescription(mEditTextSalonDescription.getText().toString().trim());
                mSalon.setSalonOpenTime(mSpinnerSalonOpen.getSelectedItem().toString().trim());
                mSalon.setSalonCloseTime(mSpinnerSalonClose.getSelectedItem().toString().trim());
                mPresenter.updateSalon(mUri, mSalon.getSalonId(), mSalon);
                break;

            case R.id.image_update:
                openfileChooser();
                break;
        }
    }

    @Override
    public void onUpdateProgress() {
        showProgressDialog(mDialog);
    }

    @Override
    public void onUpdateImageSuccess() {
        mDialog.hide();
        Toast.makeText(this, "Update success 50%", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateImageFailed() {
        mDialog.hide();
        Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdatehSalonSuccess() {
        mDialog.hide();
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdatehSalonFailed() {
        mDialog.hide();
        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonName() {
        Toast.makeText(this, "error name", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonAddress() {
        Toast.makeText(this, "error address", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidateSalonEmail() {
        Toast.makeText(this, "error email", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInvalidEmailForm() {
        Toast.makeText(this, "error emailform", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidateSalonPhone() {
        Toast.makeText(this, "error phone", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidateSalonTime() {
        Toast.makeText(this, "error time", Toast.LENGTH_SHORT).show();
    }
}
