package com.example.facerace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestActivity extends AppCompatActivity {

    private DatabaseReference myRef;

    private ArrayList<Face> usedFaces;

    private ArrayList<Face> allFaces;
    private FaceAdapter mFaceAdapter;
    private Context myContext;
    private RecyclerView recyclerView;
    private static final int NUMBER_OF_FACES = 5;
    private static final int NUMBER_OF_SELECTED_FACES = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ClearAll();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.recyclerView_test);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference();
        usedFaces = new ArrayList<>();
        allFaces = new ArrayList<>();

        getDataFromFirebase();

        Button button_test_continue = findViewById(R.id.button_test_continue);
        button_test_continue.setOnClickListener(view ->
                startActivity(new Intent(TestActivity.this, GameActivity.class)));

    }

    private HashMap<String, Face> selectRandomFaces(int randomSampleSize) {
        HashMap<String, Face> faces = new HashMap<>();
        Random r = new Random();
        for (int i = 0; i < randomSampleSize; i++) {
            final int arraySize = usedFaces.size();
            Face item = usedFaces.remove(r.nextInt(arraySize));
            faces.put(item.getName(), item);
        }

        return faces;
    }

    private void getDataFromFirebase() {
        Query query = myRef.child("Bogus");
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();

                int totalSize = (int) dataSnapshot.getChildrenCount();
                ArrayList<Integer> indexes = IntStream.range(0, totalSize).boxed()
                        .collect(Collectors.toCollection(ArrayList::new));
                Collections.shuffle(indexes);
                System.out.println("indexes size=" + indexes.size());
                for (int i = 0; i < 10; i++) {
                    String newPerson = "Person" + indexes.remove(0);
                    String image = dataSnapshot.child(newPerson).child("image")
                            .getValue(String.class);
                    String name = dataSnapshot.child(newPerson).child("name")
                            .getValue(String.class);

                    Face face = new Face();
                    face.setImageUrl(image);
                    face.setName(name);

                    allFaces.add(face);
                }
                for (int i = 0; i < NUMBER_OF_FACES; i++) {

                    usedFaces.add(allFaces.remove(0));
                }
                mFaceAdapter = new FaceAdapter(getApplicationContext(), usedFaces, false);
                recyclerView.setAdapter(mFaceAdapter);
                mFaceAdapter.notifyDataSetChanged();


                Button button_test_continue = findViewById(R.id.button_test_continue);
                button_test_continue.setOnClickListener(view -> {
                    HashMap<String, Face> selectedFaces =
                            selectRandomFaces(NUMBER_OF_SELECTED_FACES);



                    Intent intent = new Intent(TestActivity.this, GameActivity.class);

                    intent.putExtra("selectedFaces", selectedFaces);
                    intent.putExtra("remainingFaces", allFaces);


                    startActivity(intent);
                    finish();

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ClearAll() {
        if (usedFaces != null) {
            usedFaces.clear();

            if (mFaceAdapter != null) {
                mFaceAdapter.notifyDataSetChanged();
            }
        }

        usedFaces = new ArrayList<>();
    }
}