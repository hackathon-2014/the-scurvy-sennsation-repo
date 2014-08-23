package com.example.bootywithfriends;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SplashyActivity extends Activity {
    
    public static final String USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashy);

        Typeface piratey = Typeface.createFromAsset(getAssets(), "fonts/pirate_font.ttf");
        ((TextView) findViewById(R.id.app_title)).setTypeface(piratey);
        ((Button) findViewById(R.id.button1)).setTypeface(piratey);
        ((Button) findViewById(R.id.button2)).setTypeface(piratey);
    }
    
    
    public void dropBootyActivity(View ignored){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USERNAME, name());
        startActivity(intent);
    }
    
    public void pickUpBootyActivity(View ignored){
        Intent intent = new Intent(this, ShowTreasuresActivity.class);
        intent.putExtra(USERNAME, name());
        startActivity(intent);
    }
    
    private String name(){
        Spinner spinner = (Spinner) findViewById(R.id.select_user_name);
        return spinner.getSelectedItem().toString();
    }

}
