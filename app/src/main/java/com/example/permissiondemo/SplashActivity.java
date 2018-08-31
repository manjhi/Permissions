package com.example.permissiondemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getPermissions();
               // Move();

    }

    void getPermissions() {
    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CAMERA+Manifest.permission.READ_CONTACTS+Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(SplashActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE},100);
    }else {
        Toast.makeText(SplashActivity.this, "go to setting", Toast.LENGTH_SHORT).show();
    }
    }

    public void Move(){
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:
                boolean camera=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean contact=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                boolean storage=grantResults[2]==PackageManager.PERMISSION_GRANTED;
                if (grantResults.length>0 && camera && contact && storage){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }else if (Build.VERSION.SDK_INT>23 && !shouldShowRequestPermissionRationale(permissions[0])){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("Permissions");
                    builder.setMessage("Permissions are requeired");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Go to the setting for granting permissions", Toast.LENGTH_SHORT).show();
                            boolean sentToSettings = true;
                            Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri=Uri.fromParts("package",SplashActivity.this.getPackageName(),null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                            .create()
                            .show();

                }else {
                    Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }
                break;

                }
        }
    }
