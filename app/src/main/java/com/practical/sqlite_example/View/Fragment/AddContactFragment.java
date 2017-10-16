package com.practical.sqlite_example.View.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.practical.sqlite_example.R;

import com.practical.sqlite_example.Utils.BitmapUtility;
import com.practical.sqlite_example.Utils.utils;
import com.practical.sqlite_example.permission.MultiplePermissionCallback;
import com.practical.sqlite_example.permission.Permission;
import com.practical.sqlite_example.Utils.AlertUtils;
import com.practical.sqlite_example.Utils.ImageSelectUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Jay on 10/15/2017.
 */

public class AddContactFragment extends BaseFragment implements View.OnClickListener {

    View rootView;
    Button buttonSave;
    EditText editTextName,editTextMobileNo,editTextAge,editTextEmailid;
    TextInputLayout textInputName,textInputMobileNo,textInputAge,textInputEmailId;
    Spinner spinnerCategory;
    ImageView imageViewProfile;
    String selectedImage;
    Bitmap mBitmap;

    BitmapUtility bitmapUtility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);

        bitmapUtility = new BitmapUtility();

        initView();

        return rootView;
    }


    public void initView()
    {
        editTextAge = (EditText) rootView.findViewById(R.id.editTextAge);
        editTextMobileNo = (EditText) rootView.findViewById(R.id.editTextNumber);
        editTextEmailid = (EditText) rootView.findViewById(R.id.editTextEmail);
        editTextName = (EditText) rootView.findViewById(R.id.editTextName);

        textInputAge = (TextInputLayout) rootView.findViewById(R.id.textInputAge);
        textInputMobileNo = (TextInputLayout) rootView.findViewById(R.id.textInputNumber);
        textInputEmailId = (TextInputLayout) rootView.findViewById(R.id.textInputEmail);
        textInputName = (TextInputLayout) rootView.findViewById(R.id.textInputName);

        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        spinnerCategory = (Spinner) rootView.findViewById(R.id.spinnerCategory);

        imageViewProfile = (ImageView) rootView.findViewById(R.id.imageViewProfPicDriver);

        imageViewProfile.setOnClickListener(this);

      /*  Glide.with(getActivity())
                .load()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(imageViewProfile);*/
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageViewProfPicDriver)
        {
            getPhoto();
        }
    }

    public void getPhoto()
    {
        if (ContextCompat.checkSelfPermission(getParentActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getParentActivity(),
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            Permission[] permissions = {Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, new MultiplePermissionCallback() {
                @Override
                public void onPermissionGranted(boolean allPermissionsGranted, List<Permission> grantedPermissions) {

                    imageSelectUtils.selectImage(new ImageSelectUtils.OnImageSelectListener() {
                        @Override
                        public void onSelect(Bitmap bitmap, byte[] bytes, String filePath) {
                            mBitmap = bitmapUtility.maskingRoundBitmap(bitmap);
                           imageViewProfile.setImageBitmap(mBitmap);

                            mBitmap = bitmap;
                            selectedImage = filePath;
                            File file = new File(selectedImage);
                            Uri imageUri = Uri.fromFile(file);

                            Glide.with(getParentActivity())
                                    .load(imageUri)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .into(imageViewProfile);
                        }

                        @Override
                        public void onError() {
                            AlertUtils.showToast(getContext(), "");
                        }
                    });
                }

                @Override
                public void onPermissionDenied(List<Permission> deniedPermissions, List<Permission> foreverDeniedPermissions) {

                }
            });
        } else {
            imageSelectUtils.selectImage(new ImageSelectUtils.OnImageSelectListener() {
                @Override
                public void onSelect(Bitmap bitmap, byte[] bytes, String filePath) {

                    mBitmap = bitmap;
                    selectedImage = filePath;
                    File file = new File(selectedImage);
                    Uri imageUri = Uri.fromFile(file);

                    Glide.with(getParentActivity())
                            .load(imageUri)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(imageViewProfile);
                }

                @Override
                public void onError() {
                    AlertUtils.showToast(getContext(), "");
                }
            });
        }
    }
}
