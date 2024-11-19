package com.guyi.class25a_ands_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.guyi.class25a_ands_1.databinding.ActivityMainBinding;

import java.net.URI;

public class Activity_SMS extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String PERMISSION = Manifest.permission.READ_SMS;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                Log.d("pttt", "isGranted= " + isGranted);
                if (isGranted) {
                    readSms();
                } else {
                    boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS);
                    if (shouldShow) {
                        openPermissionInfo();
                        // last time before don't ask me again
                    } else {
                        // can't show any request 3+++
                        openSettingsInfo();
                    }
                }
                updateInfo();
            });

    private ActivityResultLauncher<Intent> manuallyPermissionResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    readSms();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.BTNInfo.setOnClickListener(v -> updateInfo());
        binding.BTNAction1.setOnClickListener(v -> readSms());
        binding.BTNAction1.setText("Read SMS.");
    }

    private void readSms() {
        boolean isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
        if (isGranted) {
            Toast.makeText(this, "Sms Collected", Toast.LENGTH_LONG).show();
        } else {
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS);
            if (shouldShow) {
                // last time before don't ask me again
                openPermissionInfo();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_SMS);
            }
        }
    }

    private void updateInfo() {
        String str = "";

        int res = checkSelfPermission(PERMISSION);
        //int res = checkCallingOrSelfPermission(PERMISSION);
        boolean isGranted = res == PackageManager.PERMISSION_GRANTED;
        str += PERMISSION + ": " + isGranted;

        boolean needToDisplayDialog = shouldShowRequestPermissionRationale(PERMISSION);
        str += "\nneedToDisplayDialog: " + needToDisplayDialog;


        binding.LBLInfo.setText(str);
    }

    private void openPermissionInfo() {
        new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle("SMS")
                .setMessage("We need it for...")
                .setPositiveButton("Got It", (dialog, which) -> requestPermissionLauncher.launch(Manifest.permission.READ_SMS))
                .setNegativeButton("No", null)
                .show();
    }

    private void openSettingsInfo() {
        new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setTitle("SMS")
                .setMessage("Settings -> Permissions -> SMS -> Allow all the time")
                .setPositiveButton("Got It", (dialog, which) -> openSettings())
                .setNegativeButton("No", null)
                .show();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        manuallyPermissionResultLauncher.launch(intent);
    }
}