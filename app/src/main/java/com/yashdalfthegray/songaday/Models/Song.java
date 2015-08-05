package com.yashdalfthegray.songaday.Models;

import java.util.Date;

/**
 * Created by yash on 8/3/15.
 */
public class Song {
    String artist, title, link, genre, date;
    Date datePosted;

    public Song () {
    }

    public String getArtist () {
        return artist;
    }

    public String getTitle () {
        return title;
    }

    public String getLink () {
        return link;
    }

    public String getGenre () {
        return genre;
    }

    public Date getDate () {
        datePosted = new Date(Long.parseLong(date) * 1000);
        return datePosted;
    }

    @Override
    public String toString() {
        return getTitle() + " by " + getArtist() + ", " + getGenre() + ", " + getLink() + ". Posted at " + getDate();
    }
}
