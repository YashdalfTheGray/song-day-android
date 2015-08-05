package com.yashdalfthegray.songaday.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yash on 8/3/15.
 */
public class Song implements Parcelable{
    String artist, title, link, genre, date;
    Date datePosted;

    public Song () {
    }

    public Song(Parcel in) {
        this.artist = in.readString();
        this.title = in.readString();
        this.link = in.readString();
        this.genre = in.readString();
        this.date = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artist);
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.genre);
        dest.writeString(this.date);
    }

    public static final Creator<Song> CREATOR
            = new Creator<Song>() {

        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
