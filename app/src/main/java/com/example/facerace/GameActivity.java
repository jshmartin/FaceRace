package com.example.facerace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Face> myArrayList;

    private FaceAdapter mFaceAdapter;
    private Context myContext;
    private RecyclerView recyclerView;
    private HashMap<String, Face> selectedFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        recyclerView = findViewById(R.id.recyclerView_game);
        ClearAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Bundle data = getIntent().getExtras();

        myArrayList = (ArrayList<Face>) data.get("remainingFaces");

        selectedFaces = (HashMap<String, Face>) data.get("selectedFaces");
        Random r = new Random();

        // add selected faces to array
        for (String name : selectedFaces.keySet()) {
            System.out.println(name);
            myArrayList.add(r.nextInt(myArrayList.size() - 1), selectedFaces.get(name));
        }

        mFaceAdapter = new FaceAdapter(getApplicationContext(), myArrayList, true);
        recyclerView.setAdapter(mFaceAdapter);
        mFaceAdapter.notifyDataSetChanged();
        Button button_game_continue = findViewById(R.id.button_game_continue);

        Intent toSelectionPage = new Intent(GameActivity.this, GameOverActivity.class);

        button_game_continue.setOnClickListener(view -> {
            toSelectionPage.putExtra("selectedFaces", selectedFaces);

            startActivity(toSelectionPage);
            finish();
        });

    }

    private void ClearAll() {
        if (myArrayList != null) {
            myArrayList.clear();

            if (mFaceAdapter != null) {
                mFaceAdapter.notifyDataSetChanged();
            }
        }

        myArrayList = new ArrayList<>();
    }
}