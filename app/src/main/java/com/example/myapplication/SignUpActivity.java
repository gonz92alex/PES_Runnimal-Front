package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView ImageViewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_act);
        Button ButtonCamera = (Button) findViewById(R.id.buttonCameraEdit);
        ImageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        ButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST){
            Bitmap bitmapPhoto = (Bitmap)data.getExtras().get("data");
            ImageViewProfile.setImageBitmap(bitmapPhoto);
        }
    }

    public void BackEv(View view) {
        Intent BackIntent = new Intent(this, MainActivity.class);
        startActivity(BackIntent);
    }


    public void BackLoginEv(View view) {
        Intent BackLoginIntent = new Intent(this, LogInActivity.class);
        startActivity(BackLoginIntent);
    }

    public void preEnterEv(View view) {
        Intent GodIntent = new Intent(this, GodActivity.class);
        startActivity(GodIntent);
    }
}
