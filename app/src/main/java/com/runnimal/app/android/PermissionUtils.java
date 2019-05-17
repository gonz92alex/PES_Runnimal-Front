package com.runnimal.app.android;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;

public abstract class PermissionUtils {
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int PERMISSIONS_REQUEST = 1338;

    public static void checkLocation(final Activity activity, String errorTitle, String errorDescription) {
        if (! isLocationEnabled(activity)) {
            Fragment fragment = new NoMapFragment();
            ((GodActivity) activity).loadFragment(fragment);
            showAlert(activity, fragment, errorTitle, errorDescription);
        }
        else {
            if (!canAccessLocation(activity)) {
                activity.requestPermissions(INITIAL_PERMS, PERMISSIONS_REQUEST);
            } else {
                ((GodActivity)activity).loadFragment(new MapFragment());
            }
        }
    }

    public static void checkPermissionsResult(Activity activity, int requestCode, String[] permissions,
                          int[] grantResults, String errorTitle, String errorDescription) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!canAccessLocation(activity)) {
                    Fragment fragment = new NoMapFragment();
                    ((GodActivity) activity).loadFragment(fragment);
                    PermissionUtils.showAlert(activity, fragment, errorTitle, errorDescription);
                }
                else {
                    ((GodActivity) activity).loadFragment(new MapFragment());
                }
            }
            else {
                ((GodActivity)activity).loadFragment(new NoMapFragment());
            }
        }
    }

    private static void showAlert(final Activity activity, final Fragment noMapFragment, String messageTitle, String messageDescription) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(messageTitle)
                .setMessage(messageDescription)
                .setPositiveButton(R.string.gps_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        noMapFragment.startActivity(myIntent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        ((GodActivity)activity).loadFragment(noMapFragment);
                    }
                });
        dialog.show();
    }

    private static boolean isLocationEnabled(final Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private static boolean canAccessLocation(final Activity activity) {
        return (hasPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private static boolean hasPermission(final Activity activity, String perm) {
        return (PackageManager.PERMISSION_GRANTED == activity.checkSelfPermission(perm));
    }
}
