package com.guyi.class25a_ands_1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationPermissionHelper {

    private final AppCompatActivity activity;
    private boolean enableOpenSettingsDialog = true;

    private final ActivityResultLauncher<String> requestPermissionLauncher;


    public NotificationPermissionHelper(AppCompatActivity activity, boolean enableOpenSettingsDialog) {
        this.activity = activity;
        this.enableOpenSettingsDialog = enableOpenSettingsDialog;

        // Initialize the permission launcher
        requestPermissionLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Permission granted
                        onPermissionGranted();
                    } else {
                        // Permission denied, show a dialog or redirect to settings
                        if (enableOpenSettingsDialog) {
                            showPermissionDeniedDialog();
                        }
                    }
                }
        );
    }

    // Method to check and request permission
    public void checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (activity.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted
                onPermissionGranted();
            } else {
                // Request permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            // Notifications don't need permission on Android 12 or lower
            onPermissionGranted();
        }
    }

    // Handle permission granted case
    private void onPermissionGranted() {
        // Insert your notification display logic here
        // Example: NotificationManager.notify()
    }

    // Show dialog if permission is denied
    private void showPermissionDeniedDialog() {
        new AlertDialog.Builder(activity)
                .setTitle("Notification Permission Needed")
                .setMessage("To receive updates, please enable notifications in settings.")
                .setPositiveButton("Go to Settings", (dialog, which) -> openAppSettings())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Open app settings for the user to enable notifications
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}
