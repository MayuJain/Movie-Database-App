package com.example.homework04;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Movie implements Serializable{

    String name;
    String description;
    String genre;
    int  year;
    int  rating;
    String imdb;

    public Movie(String name, String description, String genre, int year, int rating, String imdb) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.imdb = imdb;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", imdb='" + imdb + '\'' +
                '}';
    }
}
