package com.example.ounk.magine;

import android.graphics.Bitmap;
import java.util.ArrayList;

public class Movie {

    private String title;
    private String subtitle;
    private String studio;
    private ArrayList <String> sources;
    private String thumb;
    private String image_small;
    private String image_big;
    private Bitmap bmpIcon;

    Movie(String title, String subtitle, String studio, ArrayList <String> sources,
          String thumb, String image_small, String image_big, Bitmap bmpIcon) {
        this.title = title;
        this.subtitle = subtitle;
        this.studio = studio;
        this.sources = sources;
        this.thumb = thumb;
        this.image_small = image_small;
        this.image_big = image_big;
        this.bmpIcon = bmpIcon;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getStudio() {
        return studio;
    }

    public ArrayList <String> getSources() {
        return sources;
    }

    public String getThumb() {
        return thumb;
    }

    public String getImage_small() {
        return image_small;
    }

    public String getImage_big() {
        return image_big;
    }

    public Bitmap getBmpIcon() {
        return bmpIcon;
    }

    public void setBmpIcon (Bitmap bmpIcon) {
        this.bmpIcon=bmpIcon;
    }
}

