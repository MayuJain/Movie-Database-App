package com.example.homework04;


/**
 *
 * MainActivity.java
 * Group 29 , Homework 4
 * Mayuri Jain, Narendra Pahuja
 *
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAddMovie;
    Button btnEditMovie;
    Button btndeleteMovie;
    Button btnlistbyRating;
    Button btnlistbyyear;

    static final String ACTION_TIMETRAVEL = "com.example.homework04.intent.action.TIMETRAVEL";
    static final int AddMovieRequest_REQUEST = 0001;
    static final int EditMovieRequest_REQUEST = 0002;
    //static final String MovieDeleteKey = "Main_MovieDeleteKey";
    static final String MAIN_MOVIE_EDIT_KEY = "Main_MovieEditKey";
    static final String MAIN_MOVIE_LIST_RATING_KEY = "Main_MovieListRatingKey";
    static final String MAIN_MOVIE_LIST_YEAR_KEY = "Main_MovieListYearKey";
    ArrayList<Movie> MovieList = new ArrayList<>();
    int indexEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddMovie = findViewById(R.id.btnAddMovie);
        btnEditMovie = findViewById(R.id.btnEditMovie);
        btndeleteMovie = findViewById(R.id.btndeleteMovie);
        btnlistbyRating = findViewById(R.id.btnlistbyRating);
        btnlistbyyear = findViewById(R.id.btnlistbyyear);

        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addmovieIntent = new Intent(MainActivity.this,
                        AddMovieActivity.class);
                startActivityForResult(addmovieIntent, AddMovieRequest_REQUEST);
            }
        });

        btnEditMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MovieList.size() > 0 ){
                    final ArrayList<String> movieNameList = new ArrayList<>();
                    for (Movie m : MovieList) {
                        movieNameList.add(m.name);
                    }

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(MainActivity.this);
                    editDialog.setTitle(getString(R.string.editDialogTitle)).setCancelable(true)
                            .setItems(movieNameList.toArray(new CharSequence[movieNameList.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Movie m = MovieList.get(i);
                                    indexEdit = i;
                                    Intent editMovieIntent = new Intent(MainActivity.this, EditActivity.class);
                                    editMovieIntent.putExtra(MAIN_MOVIE_EDIT_KEY, m);
                                    startActivityForResult(editMovieIntent, EditMovieRequest_REQUEST);
                                }
                            });

                    AlertDialog dialog = editDialog.create();
                    dialog.show();
                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.NoMovieEditError), Toast.LENGTH_SHORT).show();
                }
                }
                
        });

        btndeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MovieList.size() > 0 ){

                    final ArrayList<String> movieNameList = new ArrayList<>();
                    for (Movie m : MovieList) {
                        movieNameList.add(m.name);
                    }

                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                    deleteDialog.setTitle(getString(R.string.deleteDialogTitle)).setCancelable(true)
                            .setItems(movieNameList.toArray(new CharSequence[movieNameList.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String findName = movieNameList.get(i);
                                    MovieList.remove(i);
                                    Toast.makeText(MainActivity.this, findName + " movie is deleted", Toast.LENGTH_SHORT).show();
                                }
                            });

                    AlertDialog dialog = deleteDialog.create();
                    dialog.show();
                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.NoMovieDeleteError), Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnlistbyRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MovieList.size() > 0 ){

                    Intent listByRatingIntent = new Intent("Rating");
                    Collections.sort(MovieList, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie1, Movie movie2) {
                            if (movie1.rating < movie2.rating) return 1;
                            if (movie1.rating > movie2.rating) return -1;
                            else return 0;
                        }
                    });
                    listByRatingIntent.putExtra(MAIN_MOVIE_LIST_RATING_KEY, MovieList);
                    startActivity(listByRatingIntent);
                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.NoMovieAvailable), Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnlistbyyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MovieList.size() > 0 ){
                    Intent listByYearIntent = new Intent("Year");
                    Collections.sort(MovieList, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie1, Movie movie2) {
                            if (movie1.year < movie2.year) return -1;
                            if (movie1.year > movie2.year) return 1;
                            else return 0;
                        }
                    });
                    listByYearIntent.putExtra(MAIN_MOVIE_LIST_YEAR_KEY, MovieList);
                    startActivity(listByYearIntent);
                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.NoMovieAvailable), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.AddMovieRequest_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Movie m = (Movie) data.getExtras().getSerializable(AddMovieActivity.ADD_MOVIE_ADD_KEY);
                Log.d("demo", "about to add to list"+ m.toString());
                MovieList.add(m);

            }
        } else if (requestCode == MainActivity.EditMovieRequest_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Movie m = (Movie) data.getExtras().getSerializable(EditActivity.EDIT_MOVIE_EDIT_KEY);
                Log.d("demo", "about to add to list after editing" + m.toString());
                MovieList.set(indexEdit, m);

            }
        }

        for (Movie m : MovieList) {
            Log.d("demo1", m.toString());
        }

    }
}

