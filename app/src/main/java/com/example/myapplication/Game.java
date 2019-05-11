package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity implements View.OnClickListener {

    private int numberOfElements;

    private MemoryButton[] buttons;

    private int[] buttonGraphicLocations;
    private int[] buttonImageID;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;

    private int seconds, numColums, numRows;
    private boolean running = true;
    private String timer;

    private static int gameFinish;

    int layoutId = R.layout.activity_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            layoutId = savedInstanceState.getInt("layoutId", R.layout.activity_game);
        }
        setContentView(R.layout.activity_game);

        GridLayout gridLayout = findViewById(R.id.grid_layout_uniwersal);

        Intent intent = getIntent();
        numRows = intent.getIntExtra("ROWS", 0);
        numColums = intent.getIntExtra("COLUMNS", 0);
        numberOfElements = intent.getIntExtra("NOE", 0);

        gridLayout.setColumnCount(numColums);
        gridLayout.setRowCount(numRows);


        int numOfPairs = numberOfElements / 2;

        buttonImageID = new int[numOfPairs];


        int imageCounter = 0;
        for (int i = 0; i < numOfPairs; i++) {
            if (imageCounter == 0) {
                buttonImageID[i] = R.drawable.ananas;
            }
            else if (imageCounter == 1) {
                buttonImageID[i] = R.drawable.ananas2;
            }
            else if (imageCounter == 2) {
                buttonImageID[i] = R.drawable.banana;
            }
            else if (imageCounter == 3) {
                buttonImageID[i] = R.drawable.coconut;
            }
            else if (imageCounter == 4) {
                buttonImageID[i] = R.drawable.lime;
            }
            else if (imageCounter == 5) {
                buttonImageID[i] = R.drawable.orange;
            }
            else if (imageCounter == 6) {
                buttonImageID[i] = R.drawable.pear;
            }
            else if (imageCounter == 7) {
                buttonImageID[i] = R.drawable.pepper;
            }
            else if (imageCounter == 8) {
                buttonImageID[i] = R.drawable.pumpkin;
            }
            else if (imageCounter == 9) {
                buttonImageID[i] = R.drawable.raspberry;
            }
            else if (imageCounter == 10) {
                buttonImageID[i] = R.drawable.raspberrypi;
            }
            else if (imageCounter == 11) {
                buttonImageID[i] = R.drawable.slices;
            }
            else if (imageCounter == 12) {
                buttonImageID[i] = R.drawable.something;
            }
            else if (imageCounter == 13) {
                buttonImageID[i] = R.drawable.watermelon;
                imageCounter = 0;
            }

            imageCounter++;
        }


        buttonGraphicLocations = new int[numberOfElements];
        Random rand = new Random();
        for (int i = 0; i < numOfPairs; i++) {
            buttonGraphicLocations[i] = buttonImageID[i];
            buttonGraphicLocations[numOfPairs+i]= buttonImageID[i];
        }
        for (int i = 0; i < numberOfElements; i++) {
            int tmp = buttonGraphicLocations[i];

            int swapLocation = rand.nextInt(numberOfElements);
            buttonGraphicLocations[i] = buttonGraphicLocations[swapLocation];
            buttonGraphicLocations[swapLocation] = tmp;

        }


        int density = (int)getResources().getDisplayMetrics().density;
        int cellSizePx;
        if (16*numColums >= 9*numRows){
            cellSizePx = (int)(((float)getResources().getDisplayMetrics().widthPixels-((40+12*numColums)*density))/numColums);
        }else {
            cellSizePx = (int)(((float)getResources().getDisplayMetrics().heightPixels-((100+12*numRows)*density))/numRows);
        }

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColums; c++) {
                MemoryButton tempButton = new MemoryButton(this, r, c, cellSizePx,  buttonGraphicLocations[r * numColums + c]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                gridLayout.addView(tempButton);
            }
        }
        runTimer();
    }


    @Override
    public void onClick(View v) {
        if (isBusy)
            return;
        MemoryButton button = (MemoryButton) v;
        if (button.isMatched)
            return;
        if (selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }
        if (selectedButton1.getId() == button.getId()) {
            return;
        }
        if (selectedButton1.getFrontDrawableId() == button.getFrontDrawableId()) {
            button.flip();
            button.setMatched(true);
            selectedButton1.setMatched(true);
            selectedButton1.setEnabled(false);
            button.setEnabled(false);
            selectedButton1 = null;
            gameFinish = gameFinish + 2;
            if (gameFinish == numberOfElements) {
                gameFinish=0;
                running = false;
                endGame();
            }
            return;
        } else {
            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    isBusy = false;
                }
            }, 500);
        }
    }


    private void runTimer() {
        final TextView timer_test = findViewById(R.id.timer_text);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int h = seconds / 3600;
                int m = (seconds % 3600) / 60;
                int s = seconds % 60;
                if (running) {
                    seconds++;
                    timer = String.format("%d:%02d:%02d", h, m, s);
                    timer_test.setText(timer);
                }
                handler.postDelayed(this, 1000);
            }

        });
    }


    private void endGame() {
        Intent intent = new Intent(Game.this, EndGame.class);
        intent.putExtra("TIMER", timer);
        startActivity(intent);
    }
}
