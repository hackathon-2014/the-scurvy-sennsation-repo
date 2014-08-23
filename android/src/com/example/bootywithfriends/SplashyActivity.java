package com.example.bootywithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class SplashyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashy);
    }
    
    
    public void dropBootyActivity(View ignored){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    public void pickUpBootyActivity(View ignored){
        Intent intent = new Intent(this, ShowTreasuresActivity.class);
        startActivity(intent);
    }

}
