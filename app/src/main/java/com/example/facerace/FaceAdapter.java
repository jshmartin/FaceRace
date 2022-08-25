package com.example.facerace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FaceAdapter extends RecyclerView.Adapter<FaceAdapter.ViewHolder> {
    private final ArrayList<Face> localDataSet;
    private Context mContext;
    private int mPosition;

    private boolean selectionMode;

    /**
     * Provide a reference to the type of views that you are using
     * This template comes with a TextView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView myImage;
        private final TextView myTextView;
        private final Button toggleSelected;
        private Face face;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            myTextView = view.findViewById(R.id.textView_guess_item);
            myImage = view.findViewById(R.id.item_imageView_guess);
            toggleSelected = view.findViewById(R.id.toggle_but);
            toggleSelected.setBackgroundColor(Color.RED);

        }

        public void setSelectionMode(boolean selectionMode) {
            if (selectionMode) {
                toggleSelected.setOnClickListener(v -> {
                    this.setSelected(!face.isSelected());

                    System.out.println(face.getName() +" "+face.isSelected());
                });
            }else{
                toggleSelected.setVisibility(View.GONE);
            }



        }

        public void setSelected(final boolean value) {
            if (value) {

                toggleSelected.setBackgroundColor(Color.GREEN);

            } else {
                toggleSelected.setBackgroundColor(Color.RED);
            }

            this.face.setSelected(value);
        }

        public void setFace(Face face) {
            this.face = face;
        }

        public Face getFace() {
            return this.face;
        }

        public boolean isSelected() {
            return this.face.isSelected();
        }

        public TextView getMyTextView() {
            return myTextView;
        }

        public ImageView getMyImage() {
            return myImage;
        }
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public FaceAdapter(Context mContext, ArrayList<Face> dataSet, boolean selectionMode) {
        this.localDataSet = dataSet;
        this.mContext = mContext;
        this.selectionMode = selectionMode;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FaceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.guess_item, viewGroup, false); //error here should be expected, this is a template

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int _position) {

        int position = viewHolder.getAdapterPosition();
        viewHolder.setFace(localDataSet.get(position));
        viewHolder.setSelectionMode(selectionMode);
        viewHolder.myTextView.setText(localDataSet.get(position).getName());
        Glide.with(mContext)
                .load(localDataSet.get(position).getImageUrl())
                .into(viewHolder.myImage);

        //uses glide to load image from url
        //glide is an image loading framework for Android


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}