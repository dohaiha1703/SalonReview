package com.framgia.gotosalon.screen.publish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PublishActivity extends BaseActivity implements View.OnClickListener, PublishContract.View {
    private static final int PICK_IMG_REQUEST = 1996;
    private static final String INTENT_KEY = "USER_ID";
    private static final String IMAGE_MIME_TYPE = "image/*";
    private Spinner mSpinnerOpen;
    private Spinner mSpinnerClose;
    private EditText mEditTextSalonName;
    private EditText mEditTextSalonAddress;
    private EditText mEditTextSalonPhone;
    private EditText mEditTextSalonEmail;
    private EditText mEditTextSalonDescription;
    private ImageView mImageChoose;
    private Button mButtonPublish;
    private Uri mUri;
    private Salon mSalon;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private PublishContract.Presenter mPresenter;
    private ProgressDialog mDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_salon;
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
        mSpinnerOpen = findViewById(R.id.spinner_open);
        mSpinnerOpen.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spiner_time)));
        mSpinnerClose = findViewById(R.id.spinner_close);
        mSpinnerClose.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spiner_time)));
        mEditTextSalonAddress = findViewById(R.id.edit_salon_address);
        mEditTextSalonName = findViewById(R.id.edit_salon_name);
        mEditTextSalonPhone = findViewById(R.id.edit_salon_phone);
        mEditTextSalonEmail = findViewById(R.id.edit_salon_email);
        mEditTextSalonDescription = findViewById(R.id.edit_salon_description);
        mImageChoose = findViewById(R.id.image_choose);
        mButtonPublish = findViewById(R.id.button_publish);
        mButtonPublish.setOnClickListener(this);
        findViewById(R.id.image_choose).setOnClickListener(this);
        mDialog = new ProgressDialog(this);
    }

    @Override
    protected void initData() {
        mSalon = new Salon();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mPresenter = new PublishPresenter(SalonRepository.getInstance(
                SalonRemoteDataSource.getInstance(mDatabaseReference, mStorageReference)));
        mPresenter.setView(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_publish:
                String idSalon = mDatabaseReference.push().getKey();
                mSalon.setSalonId(idSalon);
                mSalon.setOwnerKey(getIntent().getStringExtra(INTENT_KEY));
                mSalon.setSalonName(mEditTextSalonName.getText().toString());
                mSalon.setSalonAddress(mEditTextSalonAddress.getText().toString());
                mSalon.setSalonPhone(mEditTextSalonPhone.getText().toString());
                mSalon.setSalonEmail(mEditTextSalonEmail.getText().toString());
                mSalon.setSalonDescription(mEditTextSalonDescription.getText().toString());
                mSalon.setSalonOpenTime(mSpinnerOpen.getSelectedItem().toString());
                mSalon.setSalonCloseTime(mSpinnerClose.getSelectedItem().toString());
                mPresenter.uploadImageSalon(mUri,
                        idSalon, mSalon);
                break;
            case R.id.image_choose:
                openfileChooser();
                break;
        }
    }

    @Override
    public void onPublishProgress() {
        showProgressDialog(mDialog);
    }

    @Override
    public void onUploadImageSuccess() {
        mDialog.hide();
        Toast.makeText(this, getResources().getString(R.string.msg_please_wait_for_publishing),
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUploadImageFailed() {
        mDialog.hide();
        Toast.makeText(this, getResources().getString(R.string.error_when_publishing),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPublishSalonSuccess() {
        Toast.makeText(this, getResources().getString(R.string.msg_publish_success),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPublishSalonFailed() {
        Toast.makeText(this, getResources().getString(R.string.error_when_publishing),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonName() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_name),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonAddress() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_address),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonEmail() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_email),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmailForm() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_email_form),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonPhone() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_phone),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonTime() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_time),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonImage() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_image),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUri = data.getData();
            mImageChoose.setImageURI(mUri);
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
}
