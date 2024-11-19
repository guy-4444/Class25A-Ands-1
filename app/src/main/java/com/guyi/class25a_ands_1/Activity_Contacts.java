package com.guyi.class25a_ands_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.guyi.class25a_ands_1.databinding.ActivityMainBinding;

public class Activity_Contacts extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String PERMISSION = Manifest.permission.READ_CONTACTS;
    private static final int CONTACTS_PERMISSION_RQ = 1233;


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
        binding.BTNAction1.setOnClickListener(v -> action1());

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

    private void action1() {
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, CONTACTS_PERMISSION_RQ);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CONTACTS_PERMISSION_RQ: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("pttt", "ok");
                } else {
                    Log.d("pttt", "not ok");
                    //Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                updateInfo();
                return;
            }
        }
    }
}