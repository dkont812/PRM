package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    private Button startButton;
    private Button endButton;
    private TextView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        timerView = (TextView) findViewById(R.id.timer);

        Intent intent = getIntent();
        String TIMER = intent.getStringExtra("TIMER");

        timerView.setText("Your time:\t" +TIMER);

        startButton = findViewById(R.id.start);
        endButton = findViewById(R.id.end);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGame.this, MainActivity.class);
                startActivity(intent);
            }

        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EndGame.this);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("Yes. Exit now!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        finishAffinity(); // Close all activites
                        System.exit(0);  // Releasing resources
                    }
                });
                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
