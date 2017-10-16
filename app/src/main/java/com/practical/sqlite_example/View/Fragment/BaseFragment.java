package com.practical.sqlite_example.View.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.practical.sqlite_example.Utils.ImageSelectUtils;
import com.practical.sqlite_example.Utils.ProgressUtils;
import com.practical.sqlite_example.View.Activity.BaseActivity;
import com.practical.sqlite_example.View.Activity.MainActivity;
import com.practical.sqlite_example.permission.MultiplePermissionCallback;
import com.practical.sqlite_example.permission.Permission;
import com.practical.sqlite_example.permission.PermissionUtils;
import com.practical.sqlite_example.permission.SinglePermissionCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sumeet on 16/3/17.
 */

public class BaseFragment extends Fragment {
    public ImageSelectUtils imageSelectUtils;
    ProgressUtils progressUtils;


    private static final int PERMISSION_REQUEST_CODE = 10 << 1;
    private List<String> permissionsToRequest = new ArrayList<>();
    private List<Permission> grantedPermissions = new ArrayList<>();
    private List<Permission> deniedPermissions = new ArrayList<>();
    private List<Permission> foreverDeniedPermissions = new ArrayList<>();
    private MultiplePermissionCallback multiplePermissionCallback;
    private SinglePermissionCallback singlePermissionCallback;
    private boolean isMultiplePermissionRequested = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageSelectUtils = new ImageSelectUtils(getParentActivity());
        progressUtils=new ProgressUtils(getParentActivity());
    }

    /**
     * this method To access all BASEActivity methods which are
     * created for support application
     *
     * @return
     */
    public BaseActivity getParentActivity() {

        return ((BaseActivity) getActivity());
    }

    /**
     * For check multiple permission
     * @param permissions
     * @param multiplePermissionCallback
     */

    public void requestPermissions(Permission[] permissions, MultiplePermissionCallback multiplePermissionCallback) {
        if (PermissionUtils.isMarshmallowOrHigher()) {
            isMultiplePermissionRequested = true;
            this.multiplePermissionCallback = multiplePermissionCallback;

            for (Permission permission : permissions) {
                if (!PermissionUtils.isGranted(getParentActivity(), permission)) {
                    permissionsToRequest.add(permission.toString());
                }
            }

            if (!permissionsToRequest.isEmpty()) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * For check single permission
     * @param permission
     * @param singlePermissionCallback
     */

    public void requestPermission(Permission permission, SinglePermissionCallback singlePermissionCallback) {
        if (PermissionUtils.isMarshmallowOrHigher()) {
            isMultiplePermissionRequested = false;
            this.singlePermissionCallback = singlePermissionCallback;
            permissionsToRequest.add(permission.toString());
            if (!permissionsToRequest.isEmpty()) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void onPermissionsResult(int requestCode, String[] permissions,
                                    int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            grantedPermissions.clear();
            deniedPermissions.clear();
            foreverDeniedPermissions.clear();

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    if (permissionsToRequest.contains(permissions[i])) {
                        grantedPermissions.add(Permission.stringToPermission(permissions[i]));
                    }
                } else {
                    boolean permissionsDeniedForever =
                            false;
                    if (PermissionUtils.isMarshmallowOrHigher()) {
                        permissionsDeniedForever = shouldShowRequestPermissionRationale(permissions[i]);
                    }
                    if (permissionsToRequest.contains(permissions[i])) {
                        if (!permissionsDeniedForever) {
                            foreverDeniedPermissions.add(Permission.stringToPermission(permissions[i]));
                        }
                        deniedPermissions.add(Permission.stringToPermission(permissions[i]));
                    }
                }
            }

            boolean allPermissionsGranted = deniedPermissions.isEmpty();
            if (isMultiplePermissionRequested) {
                multiplePermissionCallback.onPermissionGranted(allPermissionsGranted, grantedPermissions);
                multiplePermissionCallback.onPermissionDenied(deniedPermissions, foreverDeniedPermissions);
            } else {
                boolean permissionsDeniedForever = false;
                if (PermissionUtils.isMarshmallowOrHigher()) {
                    permissionsDeniedForever = shouldShowRequestPermissionRationale(
                            permissionsToRequest.get(0));
                }
                if (allPermissionsGranted)
                    permissionsDeniedForever = true;
                singlePermissionCallback.onPermissionResult(allPermissionsGranted, !permissionsDeniedForever);
            }
            permissionsToRequest.clear();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setActionBarTitle(String title) {
        getParentActivity().getSupportActionBar().setTitle(title);
    }

}
