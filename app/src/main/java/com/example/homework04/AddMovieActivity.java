package com.example.homework04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddMovieActivity extends AppCompatActivity {
    static final String ADD_MOVIE_ADD_KEY = "Add_MovieAddKey";

    Button Addmovie;
    EditText etmoviename;
    EditText etIMDB;
    EditText etmoviedescription;
    EditText etYear;
    SeekBar ratingSeekbar;
    Spinner genredropdown;
    TextView tv_showRating;

    String moviename;
    String IMDB;
    String movieDescription;
    int ratingvalue;
    int year;
    String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<Movie> movieArrayList = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        etmoviename = findViewById(R.id.etmoviename);
        etmoviedescription = findViewById(R.id.etmoviedescription);
        etIMDB = findViewById(R.id.etIMDB);
        etYear = findViewById(R.id.etYear);
        genredropdown = findViewById(R.id.genredropdown);
        ratingSeekbar = findViewById(R.id.ratingseekbar);
        tv_showRating = findViewById(R.id.tv_showRating);
        Addmovie = findViewById(R.id.btnAddnewMovie);

        ratingSeekbar.setMax(5);
        ratingSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_showRating.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.movie_genre, R.layout.support_simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genredropdown.setAdapter(dataAdapter);
        genredropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genre = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayList<Movie> finalMovieArrayList = movieArrayList;
        Addmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;

                movieDescription = etmoviedescription.getText().toString();
                moviename = etmoviename.getText().toString();
                IMDB = etIMDB.getText().toString();
                ratingvalue = ratingSeekbar.getProgress();
                if(etYear.getText().toString().isEmpty() || Integer.parseInt(etYear.getText().toString()) == 0 || Integer.parseInt(etYear.getText().toString()) < 1000){
                    etYear.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }else {
                    year = Integer.parseInt(etYear.getText().toString());
                }


                if(moviename.isEmpty()){
                    etmoviename.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }
                if(movieDescription.isEmpty()){
                    etmoviedescription.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }
                if(IMDB.isEmpty()){
                    etIMDB.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }
                 if(!isError){
                     Movie newmovie = new Movie(moviename, movieDescription, genre, year, ratingvalue, IMDB);
                     Log.d("demo","before adding on click of add"+ newmovie.toString());
                     Intent intent = new Intent();
                     intent.putExtra(ADD_MOVIE_ADD_KEY, newmovie);
                     setResult(Activity.RESULT_OK, intent);
                     finish();
                 }else{
                     Toast.makeText(AddMovieActivity.this, getResources().getString(R.string.invalidInput), Toast.LENGTH_SHORT).show();
                 }

            }
        });

    }
}
