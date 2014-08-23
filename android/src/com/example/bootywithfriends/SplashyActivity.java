package com.example.bootywithfriends;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SplashyActivity extends Activity {
    
    public static final String USERNAME = "USERNAME";
    
    private AlertDialog dialog;
    private String chosenUserName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashy);

        Typeface piratey = Typeface.createFromAsset(getAssets(), "fonts/pirate_font.ttf");
        ((TextView) findViewById(R.id.app_title)).setTypeface(piratey);
        ((Button) findViewById(R.id.button1)).setTypeface(piratey);
        ((Button) findViewById(R.id.button2)).setTypeface(piratey);

        Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prompt_username);
        builder.setSingleChoiceItems(getResources().getStringArray(R.array.default_users), -1, listener());
        dialog = builder.show();
    }
    
    private OnClickListener listener() {
        return new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String names[] = getResources().getStringArray(R.array.default_users);
                chosenUserName = names[which];
                
                TextView welcome = (TextView) findViewById(R.id.welcome_username);
                welcome.setText("Welcome Pirate " + chosenUserName);
                
                dialog.cancel();
            }
        };
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
        return chosenUserName;
    }

}
