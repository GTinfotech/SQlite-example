package com.practical.sqlite_example.View.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.practical.sqlite_example.Utils.ImageSelectUtils;
import com.practical.sqlite_example.permission.PermissionActivity;


/**
 * Created by sumeet on 5/10/17.
 */

public class BaseActivity extends AppCompatActivity {

    public ImageSelectUtils imageSelectUtils;
    PermissionActivity permissionActivity ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageSelectUtils = new ImageSelectUtils(BaseActivity.this);
        permissionActivity = new PermissionActivity(BaseActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionActivity.onPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
