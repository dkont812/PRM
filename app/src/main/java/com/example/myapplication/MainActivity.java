package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button createGameButton ,endButton;
    private String rows, columns;
    private EditText rowsInput, columnsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createGameButton = findViewById(R.id.createGame);
        endButton = findViewById(R.id.end);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rowsInput = (EditText) findViewById(R.id.rows);
                columnsInput = (EditText) findViewById(R.id.columns);

                rows = rowsInput.getText().toString();
                columns = columnsInput.getText().toString();

                int r = Integer.valueOf(rows);
                int c = Integer.valueOf(columns);

                int numOfElements = r*c;
                if (rows.isEmpty() || columns.isEmpty() ||
                        r<1 || r>10 || c<1 || c>10 ||
                        numOfElements%2==1 ) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Input data are not correct!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Game.class);
                    intent.putExtra("ROWS", r);
                    intent.putExtra("COLUMNS", c);
                    intent.putExtra("NOE", numOfElements);
                    startActivity(intent);
                }
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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