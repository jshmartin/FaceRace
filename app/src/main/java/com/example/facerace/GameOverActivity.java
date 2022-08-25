package com.example.facerace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOverActivity extends AppCompatActivity {

    private int score = 0;
    private int maxScore = 0;

    private void tallyScore(HashMap<String, Face> validFaces) {

        for (Face face : validFaces.values()) {
            if (face.isSelected()) {
                score++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle data = getIntent().getExtras();
        HashMap<String, Face> selectedFaces = (HashMap<String, Face>) data.get("selectedFaces");
        maxScore = selectedFaces.size();
        tallyScore(selectedFaces);

        for(String key : selectedFaces.keySet()){
            System.out.println(key +" "+selectedFaces.get(key).isSelected());
        }

        TextView leftScore = findViewById(R.id.left_score);
        TextView rightScore = findViewById(R.id.right_score);
        leftScore.setText(Integer.toString(this.score));
        rightScore.setText("/"+maxScore+"!");

        Button button = findViewById(R.id.button_game_over);

        button.setOnClickListener(view -> {
            finish();
        });
    }
}