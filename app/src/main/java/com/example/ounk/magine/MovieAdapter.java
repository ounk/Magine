package com.example.ounk.magine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movieList;

    MovieAdapter(@NonNull Context context, ArrayList<Movie> list) {
        super(context, 0, list);
        this.context=context;
        movieList=list;
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);

        Movie movie=movieList.get (position);

        TextView textView=listItem.findViewById(R.id.title);
        textView.setText(movie.getTitle());

        textView=listItem.findViewById(R.id.studio);
        textView.setText(movie.getStudio());

        ImageView imageView=listItem.findViewById(R.id.thumb);
        imageView.setImageBitmap(movie.getBmpIcon());

        return listItem;
    }
}
